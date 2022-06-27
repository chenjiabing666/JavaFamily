package cn.zifangsky.mqwebsocket.model.websocket;

/**
 * Redis中存储WebSocket消息
 *
 * @author zifangsky
 * @date 2018/10/16
 * @since 1.0.0
 */
public class RedisWebsocketMsg<T> {
    /**
     * 消息接收者的username
     */
    private String receiver;
    /**
     * 消息对应的订阅频道的CODE，参考{@link cn.zifangsky.mqwebsocket.enums.WebSocketChannelEnum}的code字段
     */
    private String channelCode;
    /**
     * 消息正文
     */
    private T content;

    public RedisWebsocketMsg() {

    }

    public RedisWebsocketMsg(String receiver, String channelCode, T content) {
        this.receiver = receiver;
        this.channelCode = channelCode;
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Override
    public String toString() {
        return "RedisWebsocketMsg{" +
                "receiver='" + receiver + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", content=" + content +
                '}';
    }
}
