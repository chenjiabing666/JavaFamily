package cn.zifangsky.mqwebsocket.interceptor.websocket;

import cn.zifangsky.mqwebsocket.common.Constants;
import cn.zifangsky.mqwebsocket.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.Principal;
import java.text.MessageFormat;

/**
 * 自定义{@link org.springframework.messaging.support.ChannelInterceptor}，实现断开连接的处理
 *
 * @author zifangsky
 * @date 2018/10/10
 * @since 1.0.0
 */
@Component
public class MyChannelInterceptor implements ChannelInterceptor{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        //用户已经断开连接
        if(StompCommand.DISCONNECT.equals(command)){
            String user = "";
            Principal principal = accessor.getUser();
            if(principal != null && StringUtils.isNoneBlank(principal.getName())){
                user = principal.getName();

                //从Redis中移除用户
                redisService.removeFromSet(Constants.REDIS_WEBSOCKET_USER_SET, user);
            }else{
                user = accessor.getSessionId();
            }

            logger.debug(MessageFormat.format("用户{0}的WebSocket连接已经断开", user));
        }
    }

}
