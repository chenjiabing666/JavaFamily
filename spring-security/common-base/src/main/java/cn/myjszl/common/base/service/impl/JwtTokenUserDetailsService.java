package cn.myjszl.common.base.service.impl;

import cn.myjszl.common.base.model.SecurityUser;
import cn.myjszl.common.base.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 公众号：码猿技术专栏
 * 从数据库中根据用户名查询用户的详细信息，包括权限
 *
 * 数据库设计：角色、用户、权限、角色<->权限、用户<->角色 总共五张表，遵循RBAC设计
 */
@Service
public class JwtTokenUserDetailsService implements UserDetailsService {

    /**
     * 查询用户详情的service
     */
    @Autowired
    private LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中查询
        SecurityUser securityUser = loginService.loadByUsername(username);
        //用户不存在直接抛出UsernameNotFoundException，security会捕获抛出BadCredentialsException
        if (Objects.isNull(securityUser))
            throw new UsernameNotFoundException("用户不存在！");
        return securityUser;
    }
}
