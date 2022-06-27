package cn.zifangsky.mqwebsocket.interceptor;

import cn.zifangsky.mqwebsocket.common.Constants;
import cn.zifangsky.mqwebsocket.common.SpringContextUtils;
import cn.zifangsky.mqwebsocket.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 定义一些页面需要做登录检查
 *
 * @author zifangsky
 * @date 2018/7/26
 * @since 1.0.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

    /**
     * 检查是否已经登录
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        //获取session中存储的token
        User user = (User) session.getAttribute(Constants.SESSION_USER);

        if(user != null){
            return true;
        }else{
            //如果token不存在，则跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/login?redirectUri=" + SpringContextUtils.getRequestUrl(request));

            return false;
        }
    }
}
