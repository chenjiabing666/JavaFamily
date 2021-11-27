package cn.myjszl.security.jwt.config;

import cn.myjszl.common.base.handler.EntryPointUnauthorizedHandler;
import cn.myjszl.common.base.handler.RequestAccessDeniedHandler;
import cn.myjszl.security.jwt.dynamic.authorization.DynamicAccessDecisionManager;
import cn.myjszl.security.jwt.dynamic.authorization.DynamicSecurityMetadataSource;
import cn.myjszl.security.jwt.filter.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 公众号：码猿技术专栏
 * @EnableGlobalMethodSecurity 开启权限校验的注解
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    private RequestAccessDeniedHandler requestAccessDeniedHandler;

    @Autowired
    private DynamicAccessDecisionManager dynamicAccessDecisionManager;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //禁用表单登录，前后端分离用不上
                .disable()
                //应用登录过滤器的配置，配置分离
                .apply(jwtAuthenticationSecurityConfig)

                .and()
                // 设置URL的授权
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <T extends FilterSecurityInterceptor> T postProcess(T o) {
                        //SecurityMetadataSource的实现类
                        o.setSecurityMetadataSource(dynamicSecurityMetadataSource);
                        //投票器的实现类
                        o.setAccessDecisionManager(dynamicAccessDecisionManager);
                        return o;
                    }
                })
                // 这里需要将登录页面放行,permitAll()表示不再拦截，/login 登录的url，/refreshToken刷新token的url
                //TODO 此处正常项目中放行的url还有很多，比如swagger相关的url，druid的后台url，一些静态资源
                .antMatchers("/login", "/refreshToken")
                .permitAll()
                //hasRole()表示需要指定的角色才能访问资源
//                .antMatchers("/hello7").hasRole("user")
                // anyRequest() 所有请求   authenticated() 必须被认证
                .anyRequest()
                .authenticated()

                //处理异常情况：认证失败和权限不足
                .and()
                .exceptionHandling()
                //认证未通过，不允许访问异常处理器
                .authenticationEntryPoint(entryPointUnauthorizedHandler)
                //认证通过，但是没权限处理器
                .accessDeniedHandler(requestAccessDeniedHandler)

                .and()
                //禁用session，JWT校验不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                //将TOKEN校验过滤器配置到过滤器链中，否则不生效，放到UsernamePasswordAuthenticationFilter之前
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                // 关闭csrf
                .csrf().disable();
    }

    // 自定义的Jwt Token校验过滤器
    @Bean
    public TokenAuthenticationFilter authenticationTokenFilterBean() {
        return new TokenAuthenticationFilter();
    }

    /**
     * 加密算法
     *
     * @return
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    @Bean
//    public RoleHierarchy roleHierarchy(){
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        String hierarchy = "ROLE_admin > ROLE_user";
//        roleHierarchy.setHierarchy(hierarchy);
//        return roleHierarchy;
//    }




















}
