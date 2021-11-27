package cn.myjszl.security.jwt.controller;

import cn.myjszl.common.base.model.LoginToken;
import cn.myjszl.common.base.constant.SecurityConstant;
import cn.myjszl.common.base.model.ResultMsg;
import cn.myjszl.common.base.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class HomeController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


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

    @PostMapping("/login2")
    public ResultMsg login(String username,String password){
        // 生成一个包含账号密码的认证信息
        Authentication token = new UsernamePasswordAuthenticationToken(username, password);
        // AuthenticationManager校验这个认证信息，返回一个已认证的Authentication
        Authentication authentication = authenticationManager.authenticate(token);
        // 将返回的Authentication存到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResultMsg.builder().code(200).msg("登录成功").build();
    }


}
