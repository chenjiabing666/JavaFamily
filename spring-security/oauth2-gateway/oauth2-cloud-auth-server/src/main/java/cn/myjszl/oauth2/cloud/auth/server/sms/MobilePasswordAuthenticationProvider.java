package cn.myjszl.oauth2.cloud.auth.server.sms;

import cn.myjszl.oauth2.cloud.auth.server.sms.service.SmsCodeUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 公众号：码猿技术专栏
 * 自定义AuthenticationProvider，处理手机号。密码登录
 * 主要的逻辑：根据userDetailService从数据库中查询用户，对比密码
 */
public class MobilePasswordAuthenticationProvider implements AuthenticationProvider {

    private final SmsCodeUserDetailService userDetailService;

    private final PasswordEncoder passwordEncoder;

    public MobilePasswordAuthenticationProvider(SmsCodeUserDetailService userDetailService, PasswordEncoder passwordEncoder){
        this.userDetailService=userDetailService;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        MobilePasswordAuthenticationToken authenticationToken = (MobilePasswordAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();
        //查询数据库，加载用户详细信息
        UserDetails user = userDetailService.loadUserByMobile(mobile);
        if (user == null) {
            throw new InternalAuthenticationServiceException("手机号或密码错误");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("手机号或密码错误");
        }
        MobilePasswordAuthenticationToken authenticationResult = new MobilePasswordAuthenticationToken(user, password, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * Spring Security会根据这个方法判断当前Provider是否支持处理
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobilePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}