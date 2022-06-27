package cn.zifangsky.mqwebsocket.utils;

import com.alibaba.fastjson.JSON;

/**
 * JSON相关公共方法（通过Fastjson实现）
 *
 * @author zifangsky
 * @date 2017/5/2
 * @since 1.0.0
 */
public class JsonUtils {

    /**
     * 将对象转化为json字符串
     * @param source Java对象
     * @return java.lang.String
     */
    public static <K> String toJson(K source){
        return JSON.toJSON(source).toString();
    }

    /**
     * 将json字符串还原为目标对象
     * @param source json字符串
     * @return K
     */
    public static <T> T fromJson(String source, Class<T> clazz){
        return JSON.parseObject(source, clazz);
    }

}
