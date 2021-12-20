package cn.myjszl.oauth2.cloud.gateway.filter;

import cn.hutool.core.codec.Base64;
import cn.myjszl.oauth2.cloud.auth.common.model.ResultCode;
import cn.myjszl.oauth2.cloud.auth.common.model.ResultMsg;
import cn.myjszl.oauth2.cloud.auth.common.model.SysConstant;
import cn.myjszl.oauth2.cloud.auth.common.model.TokenConstant;
import cn.myjszl.oauth2.cloud.gateway.model.SysParameterConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author 公众号：码猿技术专栏
 * 全局过滤器，对token的拦截，解析token放入header中，便于下游微服务获取用户信息
 * 分为如下几步：
 *  1、白名单直接放行
 *  2、校验token
 *  3、读取token中存放的用户信息
 *  4、重新封装用户信息，加密成功json数据放入请求头中传递给下游微服务
 */
@Component
@Slf4j
public class GlobalAuthenticationFilter implements GlobalFilter, Ordered {
    /**
     * JWT令牌的服务
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 系统参数配置
     */
    @Autowired
    private SysParameterConfig sysConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        //1、白名单放行，比如授权服务、静态资源.....
        if (checkUrls(sysConfig.getIgnoreUrls(),requestUrl)){
            return chain.filter(exchange);
        }

        //2、 检查token是否存在
        String token = getToken(exchange);
        if (StringUtils.isBlank(token)) {
            return invalidTokenMono(exchange);
        }

        //3 判断是否是有效的token
        OAuth2AccessToken oAuth2AccessToken;
        try {
            //解析token，使用tokenStore
            oAuth2AccessToken = tokenStore.readAccessToken(token);
            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            //令牌的唯一ID
            String jti=additionalInformation.get(TokenConstant.JTI).toString();
            /**查看黑名单中是否存在这个jti，如果存在则这个令牌不能用****/
            Boolean hasKey = stringRedisTemplate.hasKey(SysConstant.JTI_KEY_PREFIX + jti);
            if (hasKey)
                return invalidTokenMono(exchange);
            //取出用户身份信息
            String user_name = additionalInformation.get("user_name").toString();
            //获取用户权限
            List<String> authorities = (List<String>) additionalInformation.get("authorities");
            //从additionalInformation取出userId
            String userId = additionalInformation.get(TokenConstant.USER_ID).toString();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(TokenConstant.PRINCIPAL_NAME, user_name);
            jsonObject.put(TokenConstant.AUTHORITIES_NAME,authorities);
            //过期时间，单位秒
            jsonObject.put(TokenConstant.EXPR,oAuth2AccessToken.getExpiresIn());
            jsonObject.put(TokenConstant.JTI,jti);
            //封装到JSON数据中
            jsonObject.put(TokenConstant.USER_ID, userId);
            //将解析后的token加密放入请求头中，方便下游微服务解析获取用户信息
            String base64 = Base64.encode(jsonObject.toJSONString());
            //放入请求头中
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header(TokenConstant.TOKEN_NAME, base64).build();
            ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
            return chain.filter(build);
        } catch (InvalidTokenException e) {
            //解析token异常，直接返回token无效
            return invalidTokenMono(exchange);
        }


    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 对url进行校验匹配
     */
    private boolean checkUrls(List<String> urls,String path){
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String url : urls) {
            if (pathMatcher.match(url,path))
                return true;
        }
        return false;
    }

    /**
     * 从请求头中获取Token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

    /**
     * 无效的token
     */
    private Mono<Void> invalidTokenMono(ServerWebExchange exchange) {
        return buildReturnMono(ResultMsg.builder()
                .code(ResultCode.INVALID_TOKEN.getCode())
                .msg(ResultCode.INVALID_TOKEN.getMsg())
                .build(), exchange);
    }


    private Mono<Void> buildReturnMono(ResultMsg resultMsg, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        byte[] bits = JSON.toJSONString(resultMsg).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset:utf-8");
        return response.writeWith(Mono.just(buffer));
    }
}
