package cn.myjszl.oauth2.cloud.auth.common.utils;

import cn.myjszl.oauth2.cloud.auth.common.model.LoginVal;
import cn.myjszl.oauth2.cloud.auth.common.model.RequestConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.*;

import java.util.Objects;

public class OauthUtils {

    public static LoginVal getCurrentUser(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)){
            HttpServletRequest request = attributes.getRequest();
            return (LoginVal) request.getAttribute(RequestConstant.LOGIN_VAL_ATTRIBUTE);
        }
        return null;
    }
}
