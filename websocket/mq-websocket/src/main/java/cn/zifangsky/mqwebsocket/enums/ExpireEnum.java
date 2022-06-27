package cn.zifangsky.mqwebsocket.enums;

import java.util.concurrent.TimeUnit;

/**
 * 过期时间相关枚举
 *
 * @author zifangsky
 * @date 2018/10/11
 * @since 1.0.0
 */
public enum ExpireEnum {
    //未读消息的有效期为30天
    UNREAD_MSG(30L, TimeUnit.DAYS)
    ;

    /**
     * 过期时间
     */
    private Long time;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit;

    ExpireEnum(Long time, TimeUnit timeUnit) {
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public Long getTime() {
        return time;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
