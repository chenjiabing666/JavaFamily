package cn.myjszl.security.jwt.dynamic.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 公众号：码猿技术专栏
 * 动态决策器，根据数据库中查询出来的角色集合和当前登录用户的角色做对比
 */
@Component
@Slf4j
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    /**
     * 决策方法
     * @param authentication 当前登录用户的认证信息，其中存储用户的详细信息，包括权限集合
     * @param object FilterInvocation对象，包含请求的url，request等信息
     * @param configAttributes  url对应的角色集合
     * @throws AccessDeniedException 不匹配直接抛出这个异常，会被RequestAccessDeniedHandler这个处理器捕获
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        // 获取用户拥有的权限信息
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 这里判断用户拥有的角色和该url需要的角色是否有匹配
        for (ConfigAttribute configAttribute : configAttributes) {
            String attribute = configAttribute.getAttribute();
            for (GrantedAuthority authority : authorities) {
                if (attribute.equals(authority.getAuthority())) {
                    log.info("匹配成功.");
                    return;
                }
            }
        }
        // 没有匹配就抛出异常
        throw new AccessDeniedException("权限不足,无法访问");
    }
    // 此 AccessDecisionManager 实现是否可以处理传递的 ConfigAttribute
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }
    // 此 AccessDecisionManager 实现是否能够提供该对象类型的访问控制决策。
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
