package com.example.springbootjasypt.log4j2;


import java.util.HashMap;
import java.util.Map;

/**
 * 现在拦截加密的日志有三类:
 * 1，身份证
 * 2，姓名
 * 3，身份证号
 * 加密的规则后续可以优化在配置文件中
 **/
public class Log4j2Rule {

    /**
     * 正则匹配 关键词 类别
     */
    public static Map<String, String> regularMap = new HashMap<>();
    /**
     * TODO  可配置
     * 此项可以后期放在配置项中
     */
    public static final String USER_NAME_STR = "Name,name,联系人,姓名";
    public static final String USER_IDCARD_STR = "empCard,idCard,身份证,证件号";
    public static final String USER_PHONE_STR = "mobile,Phone,phone,电话,手机";

    /**
     * 正则匹配，自己根据业务要求自定义
     */
    private static String IDCARD_REGEXP = "(\\d{17}[0-9Xx]|\\d{14}[0-9Xx])";
    private static String USERNAME_REGEXP = "[\\u4e00-\\u9fa5]{2,4}";
    private static String PHONE_REGEXP = "(?<!\\d)(?:(?:1[3456789]\\d{9})|(?:861[356789]\\d{9}))(?!\\d)";

    static {
        regularMap.put(USER_NAME_STR, USERNAME_REGEXP);
        regularMap.put(USER_IDCARD_STR, IDCARD_REGEXP);
        regularMap.put(USER_PHONE_STR, PHONE_REGEXP);
    }

}


