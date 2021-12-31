package cn.myjszl.oauth2.cloud.gateway.config.security;

import cn.hutool.core.util.ArrayUtil;
import cn.myjszl.oauth2.cloud.gateway.exception.RequestAccessDeniedHandler;
import cn.myjszl.oauth2.cloud.gateway.exception.RequestAuthenticationEntryPoint;
import cn.myjszl.oauth2.cloud.gateway.filter.CorsFilter;
import cn.myjszl.oauth2.cloud.gateway.model.SysParameterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;

/**
 * @author 公众号：码猿技术专栏
 * 网关的OAuth2.0资源的配置类
 * 由于spring cldou gateway使用的Flux，因此需要使用@EnableWebFluxSecurity注解开启，而不是平常的web应用了
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * JWT的鉴权管理器
     */
    @Autowired
    private ReactiveAuthorizationManager<AuthorizationContext> accessManager;

    /**
     * token过期的异常处理
     */
    @Autowired
    private RequestAuthenticationEntryPoint requestAuthenticationEntryPoint;

    /**
     * 权限不足的异常处理
     */
    @Autowired
    private RequestAccessDeniedHandler requestAccessDeniedHandler;

    /**
     * 系统参数配置
     */
    @Autowired
    private SysParameterConfig sysConfig;

    /**
     * token校验管理器
     */
    @Autowired
    private ReactiveAuthenticationManager tokenAuthenticationManager;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception{
        //认证过滤器，放入认证管理器tokenAuthenticationManager
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());

        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                //白名单直接放行
                .pathMatchers(ArrayUtil.toArray(sysConfig.getIgnoreUrls(),String.class)).permitAll()
                //其他的请求必须鉴权，使用鉴权管理器
                .anyExchange().access(accessManager)
                //鉴权的异常处理，权限不足，token失效
                .and().exceptionHandling()
                .authenticationEntryPoint(requestAuthenticationEntryPoint)
                .accessDeniedHandler(requestAccessDeniedHandler)
                .and()
                // 跨域过滤器
                .addFilterAt(corsFilter, SecurityWebFiltersOrder.CORS)
                //token的认证过滤器，用于校验token和认证
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }
}