package cn.myjszl.oauth2.cloud.auth.server.sms;

import cn.myjszl.oauth2.cloud.auth.server.sms.service.SmsCodeUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SmsCodeUserDetailService userDetailService;

    /**
     * 短信验证码配置器
     *  所有的配置都可以移步到WebSecurityConfig
     *  builder.authenticationProvider() 相当于 auth.authenticationProvider();
     *  使用外部配置必须要在WebSecurityConfig中用http.apply(smsCodeSecurityConfig)将配置注入进去
     */
    @Override
    public void configure(HttpSecurity builder) {
        //注入SmsCodeAuthenticationProvider
        MobilePasswordAuthenticationProvider smsCodeAuthenticationProvider = new MobilePasswordAuthenticationProvider(userDetailService,passwordEncoder);
        builder.authenticationProvider(smsCodeAuthenticationProvider);
    }
}