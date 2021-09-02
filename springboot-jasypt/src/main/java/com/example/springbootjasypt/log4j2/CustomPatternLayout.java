package com.example.springbootjasypt.log4j2;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * log4j2 脱敏插件
 * 继承AbstractStringLayout
 **/
@Plugin(name = "CustomPatternLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class CustomPatternLayout extends AbstractStringLayout {


    public final static Logger logger = LoggerFactory.getLogger(CustomPatternLayout.class);
    private PatternLayout patternLayout;


    protected CustomPatternLayout(Charset charset, String pattern) {
        super(charset);
        patternLayout = PatternLayout.newBuilder().withPattern(pattern).build();
        initRule();
    }

    /**
     * 要匹配的正则表达式map
     */
    private static Map<String, Pattern> REG_PATTERN_MAP = new HashMap<>();
    private static Map<String, String> KEY_REG_MAP = new HashMap<>();


    private void initRule() {
        try {
            if (MapUtils.isEmpty(Log4j2Rule.regularMap)) {
                return;
            }
            Log4j2Rule.regularMap.forEach((a, b) -> {
                if (StringUtils.isNotBlank(a)) {
                    Map<String, String> collect = Arrays.stream(a.split(",")).collect(Collectors.toMap(c -> c, w -> b, (key1, key2) -> key1));
                    KEY_REG_MAP.putAll(collect);
                }
                Pattern compile = Pattern.compile(b);
                REG_PATTERN_MAP.put(b, compile);
            });

        } catch (Exception e) {
            logger.info(">>>>>> 初始化日志脱敏规则失败 ERROR：{}", e);
        }

    }

    /**
     * 处理日志信息，进行脱敏
     * 1.判断配置文件中是否已经配置需要脱敏字段
     * 2.判断内容是否有需要脱敏的敏感信息
     * 2.1 没有需要脱敏信息直接返回
     * 2.2 处理: 身份证 ,姓名,手机号敏感信息
     */
    public String hideMarkLog(String logStr) {
        try {
            //1.判断配置文件中是否已经配置需要脱敏字段
            if (StringUtils.isBlank(logStr) || MapUtils.isEmpty(KEY_REG_MAP) || MapUtils.isEmpty(REG_PATTERN_MAP)) {
                return logStr;
            }
            //2.判断内容是否有需要脱敏的敏感信息
            Set<String> charKeys = KEY_REG_MAP.keySet();
            for (String key : charKeys) {
                if (logStr.contains(key)) {
                    String regExp = KEY_REG_MAP.get(key);
                    logStr = matchingAndEncrypt(logStr, regExp, key);
                }
            }
            return logStr;
        } catch (Exception e) {
            logger.info(">>>>>>>>> 脱敏处理异常 ERROR:{}", e);
            //如果抛出异常为了不影响流程，直接返回原信息
            return logStr;
        }
    }

    /**
     * 正则匹配对应的对象。
     *
     * @param msg
     * @param regExp
     * @return
     */
    private static String matchingAndEncrypt(String msg, String regExp, String key) {
        Pattern pattern = REG_PATTERN_MAP.get(regExp);
        if (pattern == null) {
            logger.info(">>> logger 没有匹配到对应的正则表达式 ");
            return msg;
        }
        Matcher matcher = pattern.matcher(msg);
        int length = key.length() + 5;
        boolean contains = Log4j2Rule.USER_NAME_STR.contains(key);
        String hiddenStr = "";
        while (matcher.find()) {
            String originStr = matcher.group();
            if (contains) {
                // 计算关键词和需要脱敏词的距离小于5。
                int i = msg.indexOf(originStr);
                if (i < 0) {
                    continue;
                }
                int span = i - length;
                int startIndex = span >= 0 ? span : 0;
                String substring = msg.substring(startIndex, i);
                if (StringUtils.isBlank(substring) ||  !substring.contains(key)) {
                    continue;
                }
                hiddenStr = hideMarkStr(originStr);
                msg = msg.replace(originStr, hiddenStr);
            } else {
                hiddenStr = hideMarkStr(originStr);
                msg = msg.replace(originStr, hiddenStr);
            }

        }
        return msg;
    }

    /**
     * 标记敏感文字规则
     *
     * @param needHideMark
     * @return
     */
    private static String hideMarkStr(String needHideMark) {
        if (StringUtils.isBlank(needHideMark)) {
            return "";
        }
        int startSize = 0, endSize = 0, mark = 0, length = needHideMark.length();

        StringBuffer hideRegBuffer = new StringBuffer("(\\S{");
        StringBuffer replaceSb = new StringBuffer("$1");

        if (length > 4) {
            int i = length / 3;
            startSize = i;
            endSize = i;
        } else {
            startSize = 1;
            endSize = 0;
        }

        mark = length - startSize - endSize;
        for (int i = 0; i < mark; i++) {
            replaceSb.append("*");
        }
        hideRegBuffer.append(startSize).append("})\\S*(\\S{").append(endSize).append("})");
        replaceSb.append("$2");
        needHideMark = needHideMark.replaceAll(hideRegBuffer.toString(), replaceSb.toString());
        return needHideMark;
    }


    /**
     * 创建插件
     */
    @PluginFactory
    public static Layout createLayout(@PluginAttribute(value = "pattern") final String pattern,
                                      @PluginAttribute(value = "charset") final Charset charset) {
        return new CustomPatternLayout(charset, pattern);
    }


    @Override
    public String toSerializable(LogEvent event) {
        return hideMarkLog(patternLayout.toSerializable(event));
    }

}


