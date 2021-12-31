package cn.myjszl.oauth2.cloud.gateway.config.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.myjszl.oauth2.cloud.auth.common.model.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author 公众号：码猿技术专栏
 * 鉴权管理器 用于认证成功之后对用户的权限进行鉴权
 * 第二个版本，集成RBAC，实现动态权限校验
 * 前面文章请看 JwtAccessManager
 * @Date 2021/12/31
 */
@Slf4j
@Component
@Primary
public class JwtAccessManagerV2 implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //匹配url
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        //请求方法 POST,GET
        String method = authorizationContext.getExchange().getRequest().getMethodValue();
        /**
         * TODO 为了适配restful接口，比如 GET:/api/.... POST:/api/....  *:/api/.....  星号匹配所有
         */
        String restFulPath = method + SysConstant.METHOD_SUFFIX + uri.getPath();
        //获取所有的uri->角色对应关系
        Map<String, List<String>> entries = redisTemplate.opsForHash().entries(SysConstant.OAUTH_URLS);
        //角色集合
        List<String> authorities = new ArrayList<>();
        entries.forEach((path, roles) -> {
            //路径匹配则添加到角色集合中
            if (antPathMatcher.match(path, restFulPath)) {
                authorities.addAll(roles);
            }
        });
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                //判断是否认证成功
                .filter(Authentication::isAuthenticated)
                //获取认证后的全部权限
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                //如果权限包含则判断为true
                .any(authority->{
                    //超级管理员直接放行
                    if (StrUtil.equals(SysConstant.ROLE_ROOT_CODE,authority))
                        return true;
                    //其他必须要判断角色是否存在交集
                    return CollectionUtil.isNotEmpty(authorities) && authorities.contains(authority);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
