package cn.myjszl.oauth2.cloud.gateway.config.security;

import cn.hutool.core.convert.Convert;
import cn.myjszl.oauth2.cloud.auth.common.model.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @author 公众号：码猿技术专栏
 * 鉴权管理器 用于认证成功之后对用户的权限进行鉴权
 * TODO 此处的逻辑：从redis中获取对应的uri的权限，与当前用户的token的携带的权限进行对比，如果包含则鉴权成功
 *      企业中可能有不同的处理逻辑，可以根据业务需求更改鉴权的逻辑
 *
 */
@Slf4j
@Component
@Deprecated
public class JwtAccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        Object value = redisTemplate.opsForHash().get(SysConstant.OAUTH_URLS, uri.getPath());
        List<String> authorities = Convert.toList(String.class,value);
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                //判断是否认证成功
                .filter(Authentication::isAuthenticated)
                //获取认证后的全部权限
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                //如果权限包含则判断为true
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}