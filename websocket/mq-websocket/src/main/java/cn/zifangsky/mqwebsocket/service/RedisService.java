package cn.zifangsky.mqwebsocket.service;

import cn.zifangsky.mqwebsocket.enums.ExpireEnum;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RedisService
 *
 * @author zifangsky
 * @date 2018/7/30
 * @since 1.0.0
 */
public interface RedisService {
    
    /**
     * 向Redis中存储键值对
     * @author zifangsky
     * @date 2018/7/30 17:02
     * @since 1.0.0
     * @param key KEY
     * @param value VALUE
     */
    void set(String key, Object value);

    /**
     * 向Redis中存储键值对，并设置过期时间
     * @author zifangsky
     * @date 2018/7/30 17:02
     * @since 1.0.0
     * @param key KEY
     * @param value VALUE
     * @param time 过期时间
     * @param timeUnit 时间单位
     */
    void setWithExpire(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 从Redis中获取键值对
     * @author zifangsky
     * @date 2018/7/30 17:04
     * @since 1.0.0
     * @param key KEY
     * @return K
     */
    <K> K get(String key);

    /**
     * 删除Redis中的某个KEY
     * @author zifangsky
     * @date 2018/7/30 17:10
     * @since 1.0.0
     * @param key KEY
     * @return boolean
     */
    boolean delete(String key);

    /**
     * 将数据添加到Redis的list中（从左边添加）
     * @author zifangsky
     * @date 2018/10/12 10:13
     * @since 1.0.0
     * @param listKey LIST的key
     * @param expireEnum 有效期的枚举类
     * @param values 待添加的数据
     */
    void addToListLeft(String listKey, ExpireEnum expireEnum, Object... values);

    /**
     * 将数据添加到Redis的list中（从右边添加）
     * @author zifangsky
     * @date 2018/10/12 10:13
     * @since 1.0.0
     * @param listKey LIST的key
     * @param expireEnum 有效期的枚举类
     * @param values 待添加的数据
     */
    void addToListRight(String listKey, ExpireEnum expireEnum, Object... values);

    /**
     * 根据起始结束序号遍历Redis中的list
     * @author zifangsky
     * @date 2018/10/12 10:15
     * @since 1.0.0
     * @param listKey LIST的key
     * @param start 起始序号
     * @param end 结束序号
     */
    List<Object> rangeList(String listKey, long start, long end);

    /**
     * 将数据添加Redis的set中
     * @author zifangsky
     * @date 2018/10/16 19:18
     * @since 1.0.0
     * @param setKey SET的key
     * @param values 待添加的数据
     */
    void addToSet(String setKey, Object... values);

    /**
     * 判断指定数据是否在Redis的set中
     * @author zifangsky
     * @date 2018/10/16 19:18
     * @since 1.0.0
     * @param setKey SET的key
     * @param value 待判断的数据
     * @return java.lang.Boolean
     */
    Boolean isSetMember(String setKey, Object value);

    /**
     * 从Redis的set中移除数据
     * @author zifangsky
     * @date 2018/10/16 19:18
     * @since 1.0.0
     * @param setKey SET的key
     * @param values 待移除的数据
     */
    void removeFromSet(String setKey, Object... values);

    /**
     * 使用Redis的消息队列
     * @author zifangsky
     * @date 2018/10/16 19:18
     * @since 1.0.0
     * @param channel topic name
     * @param message 消息内容
     */
    void convertAndSend(String channel, Object message);
}
