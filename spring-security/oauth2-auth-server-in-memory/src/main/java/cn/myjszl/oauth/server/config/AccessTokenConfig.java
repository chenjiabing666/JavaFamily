package cn.myjszl.oauth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author  公众号：码猿技术专栏
 * 令牌的配置
 */
@Configuration
public class AccessTokenConfig {

    /**
     * 令牌的存储策略
     */
    @Bean
    TokenStore tokenStore() {
        //todo 方便测试，使用内存存储策略，一旦服务重启令牌失效，后续可以使用数据库存储或者JWT
        return new InMemoryTokenStore();
    }
}