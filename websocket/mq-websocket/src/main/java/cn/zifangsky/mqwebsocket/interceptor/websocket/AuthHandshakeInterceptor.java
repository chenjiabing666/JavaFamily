package cn.zifangsky.mqwebsocket.interceptor.websocket;

import cn.zifangsky.mqwebsocket.common.Constants;
import cn.zifangsky.mqwebsocket.common.SpringContextUtils;
import cn.zifangsky.mqwebsocket.model.User;
import cn.zifangsky.mqwebsocket.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 自定义{@link org.springframework.web.socket.server.HandshakeInterceptor}，实现“需要登录才允许连接WebSocket”
 *
 * @author zifangsky
 * @date 2018/10/11
 * @since 1.0.0
 */
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        HttpSession session = SpringContextUtils.getSession();
        User loginUser = (User) session.getAttribute(Constants.SESSION_USER);

        if(redisService.isSetMember(Constants.REDIS_WEBSOCKET_USER_SET, loginUser.getUsername())){
            logger.error("同一个用户不准建立多个连接WebSocket");
            return false;
        }else if(loginUser == null || StringUtils.isBlank(loginUser.getUsername())){
            logger.error("未登录系统，禁止连接WebSocket");
            return false;
        }else{
            logger.debug(MessageFormat.format("用户{0}请求建立WebSocket连接", loginUser.getUsername()));
            return true;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        
    }

}
