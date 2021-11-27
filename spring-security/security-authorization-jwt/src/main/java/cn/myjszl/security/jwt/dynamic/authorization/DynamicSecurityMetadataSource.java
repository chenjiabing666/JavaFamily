package cn.myjszl.security.jwt.dynamic.authorization;

import cn.myjszl.common.base.utils.AuthorizationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author 公众号：码猿技术专栏
 * 动态权限数据源，主要功能是根据请求的url从数据库中查询出这个url所需要的角色集合
 */
@Component
@Slf4j
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
 
    @Autowired
    AuthorizationUtils authorizationUtils;

    /**
     * 获取对应url的角色集合
     * @param object FilterInvocation对象
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取url
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        // 获取拥有url的角色集合（从数据库中加载）
        List<String> roles = authorizationUtils.listRoles(requestUrl);
        log.info("{} 对应的角色。{}",requestUrl,roles);
        //如果角色集合为空，则返回null
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }
        // 自定义角色信息 --> Security的权限格式
        String[] attributes = roles.toArray(new String[0]);
        return SecurityConfig.createList(attributes);
    }
 
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
 
    // 是否能为 Class 提供 Collection<ConfigAttribute>
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
