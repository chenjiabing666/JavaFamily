package cn.zifangsky.mqwebsocket.interceptor.websocket;

import cn.zifangsky.mqwebsocket.common.Constants;
import cn.zifangsky.mqwebsocket.common.SpringContextUtils;
import cn.zifangsky.mqwebsocket.model.User;
import cn.zifangsky.mqwebsocket.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 自定义{@link org.springframework.web.socket.server.support.DefaultHandshakeHandler}，实现“生成自定义的{@link java.security.Principal}”
 *
 * @author zifangsky
 * @date 2018/10/11
 * @since 1.0.0
 */
@Component
public class MyHandshakeHandler extends DefaultHandshakeHandler{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpSession session = SpringContextUtils.getSession();
        User loginUser = (User) session.getAttribute(Constants.SESSION_USER);

        if(loginUser != null){
            logger.debug(MessageFormat.format("WebSocket连接开始创建Principal，用户：{0}", loginUser.getUsername()));
            //1. 将用户名存到Redis中
            redisService.addToSet(Constants.REDIS_WEBSOCKET_USER_SET, loginUser.getUsername());

            //2. 返回自定义的Principal
            return new MyPrincipal(loginUser.getUsername());
        }else{
            logger.error("未登录系统，禁止连接WebSocket");
            return null;
        }
    }

}
