package cn.zifangsky.mqwebsocket.mq;

import cn.zifangsky.mqwebsocket.enums.WebSocketChannelEnum;
import cn.zifangsky.mqwebsocket.model.websocket.RedisWebsocketMsg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * Redis中的WebSocket消息的处理者
 *
 * @author zifangsky
 * @date 2018/10/16
 * @since 1.0.0
 */
@Component
public class MessageReceiver {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    /**
     * 处理WebSocket消息
     */
    public void receiveMessage(RedisWebsocketMsg redisWebsocketMsg) {
        logger.info(MessageFormat.format("Received Message: {0}", redisWebsocketMsg));
        //1. 取出用户名并判断是否连接到当前应用节点的WebSocket
        SimpUser simpUser = userRegistry.getUser(redisWebsocketMsg.getReceiver());

        if(simpUser != null && StringUtils.isNoneBlank(simpUser.getName())){
            //2. 获取WebSocket客户端的订阅地址
            WebSocketChannelEnum channelEnum = WebSocketChannelEnum.fromCode(redisWebsocketMsg.getChannelCode());

            if(channelEnum != null){
                //3. 给WebSocket客户端发送消息
                messagingTemplate.convertAndSendToUser(redisWebsocketMsg.getReceiver(), channelEnum.getSubscribeUrl(), redisWebsocketMsg.getContent());
            }
        }

    }
}
