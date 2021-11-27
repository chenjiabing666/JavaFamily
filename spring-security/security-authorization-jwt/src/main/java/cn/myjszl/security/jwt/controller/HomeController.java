package cn.myjszl.security.jwt.controller;

import cn.myjszl.common.base.constant.SecurityConstant;
import cn.myjszl.common.base.model.LoginToken;
import cn.myjszl.common.base.model.ResultMsg;
import cn.myjszl.common.base.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class HomeController {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 刷新令牌
     * @return
     */
    @PostMapping("/refreshToken")
    public ResultMsg refreshToken(HttpServletRequest request){
        //从请求头中获取refreshToken
        String oldRefreshToken = request.getHeader(SecurityConstant.REFRESH_TOKEN_HEADER);
        //校验refreshToken，如果令牌没有过期
        if (jwtUtils.isTokenExpired(oldRefreshToken)){
            return ResultMsg.builder().code(200).msg("刷新令牌已过期，请重新登录！").build();
        }

        //解析refreshToken
        String username = jwtUtils.getUsernameFromToken(oldRefreshToken);
        //生成新的accessToken
        String newAccessToken = jwtUtils.createToken(username);
        String newRefreshToken = jwtUtils.refreshToken(newAccessToken);
        return ResultMsg.builder().code(200).msg("令牌请求成功！").data(LoginToken.builder().accessToken(newAccessToken).refreshToken(newRefreshToken).build()).build();
    }

}
