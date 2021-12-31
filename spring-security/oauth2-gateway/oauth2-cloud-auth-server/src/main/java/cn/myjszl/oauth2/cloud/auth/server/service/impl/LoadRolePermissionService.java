package cn.myjszl.oauth2.cloud.auth.server.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.myjszl.oauth2.cloud.auth.common.model.SysConstant;
import cn.myjszl.oauth2.cloud.auth.server.model.po.SysRole;
import cn.myjszl.oauth2.cloud.auth.server.model.vo.SysRolePermissionVO;
import cn.myjszl.oauth2.cloud.auth.server.service.PermissionService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author  公众号：码猿技术专栏
 * 在项目启动时从数据库中将url->角色对应关系加载到Redis中
 */
@Service
public class LoadRolePermissionService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private PermissionService permissionService;

    @PostConstruct
    public void init(){
        //获取所有的权限
        List<SysRolePermissionVO> list = permissionService.listRolePermission();
        list.parallelStream().peek(k->{
            List<String> roles=new ArrayList<>();
            if (CollectionUtil.isNotEmpty(k.getRoles())){
                for (SysRole role : k.getRoles()) {
                    roles.add(SysConstant.ROLE_PREFIX+role.getCode());
                }
            }
            //放入Redis中
            redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,k.getUrl(), roles);
        }).collect(Collectors.toList());
    }

}
