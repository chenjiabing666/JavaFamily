package cn.myjszl.oauth2.cloud.gateway.service.impl;

import cn.myjszl.oauth2.cloud.auth.common.model.SysConstant;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 用于初始化uir的权限到redis中
 * TODO 实际开发中需要自己维护，此处只是为了演示方便
 * 详情见 cn.myjszl.oauth2.cloud.auth.server.service.impl.LoadRolePermissionService
 */
//@Service
@Deprecated
public class InitService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void init(){
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/order/login/info", Lists.newArrayList("ROLE_admin","ROLE_user"));
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/order/login/admin", Lists.newArrayList("ROLE_admin"));
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/order/info", Lists.newArrayList("ROLE_admin","ROLE_user"));
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/order/listByUserId", Lists.newArrayList("ROLE_admin","ROLE_user"));
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/oauth/logout", Lists.newArrayList("ROLE_admin","ROLE_user"));
    }

}
