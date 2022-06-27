package cn.zifangsky.mqwebsocket.controller;

import cn.zifangsky.mqwebsocket.common.Constants;
import cn.zifangsky.mqwebsocket.common.SpringContextUtils;
import cn.zifangsky.mqwebsocket.enums.ExpireEnum;
import cn.zifangsky.mqwebsocket.enums.WebSocketChannelEnum;
import cn.zifangsky.mqwebsocket.model.User;
import cn.zifangsky.mqwebsocket.model.websocket.HelloMessage;
import cn.zifangsky.mqwebsocket.model.websocket.RedisWebsocketMsg;
import cn.zifangsky.mqwebsocket.service.RedisService;
import cn.zifangsky.mqwebsocket.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试{@link org.springframework.messaging.simp.SimpMessagingTemplate}类的基本用法
 * @author zifangsky
 * @date 2018/10/10
 * @since 1.0.0
 */
@Controller
@RequestMapping(("/wsTemplate"))
public class RedisMessageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.redis.message.topic-name}")
    private String topicName;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    /**
     * 给指定用户发送WebSocket消息
     */
    @PostMapping("/sendToUser")
    @ResponseBody
    public String chat(HttpServletRequest request) {
        //消息接收者
        String receiver = request.getParameter("receiver");
        //消息内容
        String msg = request.getParameter("msg");
        HttpSession session = SpringContextUtils.getSession();
        User loginUser = (User) session.getAttribute(Constants.SESSION_USER);

        HelloMessage resultData = new HelloMessage(MessageFormat.format("{0} say: {1}", loginUser.getUsername(), msg));
        this.sendToUser(loginUser.getUsername(), receiver, WebSocketChannelEnum.CHAT.getSubscribeUrl(), JsonUtils.toJson(resultData));

        return "ok";
    }

    /**
     * 给指定用户发送消息，并处理接收者不在线的情况
     * @param sender 消息发送者
     * @param receiver 消息接收者
     * @param destination 目的地
     * @param payload 消息正文
     */
    private void sendToUser(String sender, String receiver, String destination, String payload){
        SimpUser simpUser = userRegistry.getUser(receiver);

        //如果接收者存在，则发送消息
        if(simpUser != null && StringUtils.isNoneBlank(simpUser.getName())){
            messagingTemplate.convertAndSendToUser(receiver, destination, payload);
        }
        //如果接收者在线，则说明接收者连接了集群的其他节点，需要通知接收者连接的那个节点发送消息
        else if(redisService.isSetMember(Constants.REDIS_WEBSOCKET_USER_SET, receiver)){
            RedisWebsocketMsg<String> redisWebsocketMsg = new RedisWebsocketMsg<>(receiver, WebSocketChannelEnum.CHAT.getCode(), payload);

            redisService.convertAndSend(topicName, redisWebsocketMsg);
        }
        //否则将消息存储到redis，等用户上线后主动拉取未读消息
        else{
            //存储消息的Redis列表名
            String listKey = Constants.REDIS_UNREAD_MSG_PREFIX + receiver + ":" + destination;
            logger.info(MessageFormat.format("消息接收者{0}还未建立WebSocket连接，{1}发送的消息【{2}】将被存储到Redis的【{3}】列表中", receiver, sender, payload, listKey));

            //存储消息到Redis中
            redisService.addToListRight(listKey, ExpireEnum.UNREAD_MSG, payload);
        }

    }


    /**
     * 拉取指定监听路径的未读的WebSocket消息
     * @param destination 指定监听路径
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("/pullUnreadMessage")
    @ResponseBody
    public Map<String, Object> pullUnreadMessage(String destination){
        Map<String, Object> result = new HashMap<>();
        try {
            HttpSession session = SpringContextUtils.getSession();
            //当前登录用户
            User loginUser = (User) session.getAttribute(Constants.SESSION_USER);

            //存储消息的Redis列表名
            String listKey = Constants.REDIS_UNREAD_MSG_PREFIX + loginUser.getUsername() + ":" + destination;
            //从Redis中拉取所有未读消息
            List<Object> messageList = redisService.rangeList(listKey, 0, -1);

            result.put("code", "200");
            if(messageList !=null && messageList.size() > 0){
                //删除Redis中的这个未读消息列表
                redisService.delete(listKey);
                //将数据添加到返回集，供前台页面展示
                result.put("result", messageList);
            }
        }catch (Exception e){
            result.put("code", "500");
            result.put("msg", e.getMessage());
        }

        return result;
    }



}
