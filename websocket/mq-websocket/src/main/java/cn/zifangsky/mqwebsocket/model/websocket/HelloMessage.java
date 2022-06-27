package cn.zifangsky.mqwebsocket.model.websocket;

/**
 * Greeting
 *
 * @author zifangsky
 * @date 2018/9/30
 * @since 1.0.0
 */
public class HelloMessage {
    private String content;

    public HelloMessage() {

    }

    public HelloMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
