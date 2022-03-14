package cn.myjszl.oauth2.cloud.auth.server.config;

import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

/**
 * @author 公众号：码猿技术专栏
 * 令牌转换器，将身份认证信息转换后存储在令牌内
 */
public class JwtEnhanceAccessTokenConverter extends DefaultAccessTokenConverter {
    public JwtEnhanceAccessTokenConverter(){
        super.setUserTokenConverter(new JwtEnhanceUserAuthenticationConverter());
    }
}
