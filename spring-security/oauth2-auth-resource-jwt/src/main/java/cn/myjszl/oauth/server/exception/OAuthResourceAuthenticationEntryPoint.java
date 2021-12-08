package cn.myjszl.oauth.server.exception;

import cn.myjszl.oauth.server.model.ResultCode;
import cn.myjszl.oauth.server.model.ResultMsg;
import cn.myjszl.oauth.server.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 公众号：码猿技术专栏
 *
 * TOKEN失效或者校验失败的处理器
 */
@Component
@Slf4j
public class OAuthResourceAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //TODO token失效提示
        ResponseUtils.result(response,new ResultMsg(ResultCode.TOKEN_INVALID.getCode(),ResultCode.TOKEN_INVALID.getMsg(),null));
    }
}