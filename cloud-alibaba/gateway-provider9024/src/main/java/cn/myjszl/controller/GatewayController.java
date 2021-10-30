package cn.myjszl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/gateway/provider")
@Slf4j
public class GatewayController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/port")
    public String getPort(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            log.info("请求头：{}={}",name,request.getHeader(name));
        }

        return "微服务开启端口："+serverPort;
    }
}
