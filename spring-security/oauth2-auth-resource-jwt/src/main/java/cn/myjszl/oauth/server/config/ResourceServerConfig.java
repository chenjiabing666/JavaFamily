package cn.myjszl.oauth.server.config;

import cn.myjszl.oauth.server.exception.OAuthResourceAuthenticationEntryPoint;
import cn.myjszl.oauth.server.exception.RequestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author 公众号：码猿技术专栏
 * OAuth2.0 资源服务的配置类
 * `@EnableResourceServer`：该注解标记这是一个资源服务
 * `@EnableGlobalMethodSecurity`：该注解开启注解校验权限
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true,securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private RequestAccessDeniedHandler requestAccessDeniedHandler;

    @Autowired
    private OAuthResourceAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 令牌服务的配置
     */
    @Bean
    public ResourceServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        //配置令牌存储策略，使用AccessTokenConfig配置的JwtTokenStore
        services.setTokenStore(tokenStore);
        //令牌的增强JwtAccessTokenConverter
        services.setTokenEnhancer(jwtAccessTokenConverter);
        return services;
    }

    /**
     * 配置资源id和令牌校验服务
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources)  {
        //配置唯一资源id
        resources.resourceId("res1")
                //定制令牌失效的提示信息
                .authenticationEntryPoint(authenticationEntryPoint)
                //定制权限不足的提示信息
                .accessDeniedHandler(requestAccessDeniedHandler)
                //配置令牌校验服务
                .tokenServices(tokenServices());
    }

    /**
     * 配置security的安全机制
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //#oauth2.hasScope()校验客户端的权限，这个all是在客户端中的scope
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .anyRequest().authenticated();
    }
}