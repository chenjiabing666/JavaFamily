package cn.myjszl.oauth2.cloud.auth.server.sms.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义的UserDetailService，从数据库中
 */
public interface SmsCodeUserDetailService  {

    /**
     * 根据手机号查询用户信息
     * @param mobile  手机号码
     */
    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;
}
