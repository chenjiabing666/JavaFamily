package cn.myjszl.oauth2.cloud.order.controller;

import cn.myjszl.oauth2.cloud.auth.common.model.LoginVal;
import cn.myjszl.oauth2.cloud.auth.common.utils.OauthUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/login/info")
    public LoginVal info(){
        return OauthUtils.getCurrentUser();
    }

    @RequestMapping("/login/admin")
    public LoginVal admin(){
        return OauthUtils.getCurrentUser();
    }
}
