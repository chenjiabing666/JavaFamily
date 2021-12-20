package cn.myjszl.oauth2.cloud.auth.server.controller;

import cn.myjszl.oauth2.cloud.auth.common.model.LoginVal;
import cn.myjszl.oauth2.cloud.auth.common.model.ResultMsg;
import cn.myjszl.oauth2.cloud.auth.common.model.SysConstant;
import cn.myjszl.oauth2.cloud.auth.common.utils.OauthUtils;
import cn.myjszl.oauth2.cloud.auth.common.utils.RequestContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/logout")
    public ResultMsg logout(){
        LoginVal loginVal = OauthUtils.getCurrentUser();
        log.info("令牌唯一ID：{},过期时间：{}",loginVal.getJti(),loginVal.getExpireIn());
        //这个jti放入redis中，并且过期时间设置为token的过期时间
        stringRedisTemplate.opsForValue().set(SysConstant.JTI_KEY_PREFIX+loginVal.getJti(),"",loginVal.getExpireIn(), TimeUnit.SECONDS);
        return new ResultMsg(200,"注销成功",null);
    }
}
