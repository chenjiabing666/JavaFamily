package cn.myjszl.oauth2.cloud.auth.server.service.impl;

import cn.myjszl.oauth2.cloud.auth.common.model.SecurityUser;
import cn.myjszl.oauth2.cloud.auth.common.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author 公众号：码猿技术专栏
 * 从数据库中根据用户名查询用户的详细信息，包括权限
 * 数据库设计：角色、用户、权限、角色<->权限、用户<->角色 总共五张表，遵循RBAC设计
 *
 * <br>集成RBAC之后，该类废弃，详情看JwtTokenUserDetailsServiceV2</br>
 */
@Service
@Deprecated
public class JwtTokenUserDetailsService implements UserDetailsService {
    public static List<SecurityUser> users=new ArrayList<>();
    static {
        SecurityUser admin = SecurityUser.builder()
                .userId(UUID.randomUUID().toString().replaceAll("-",""))
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .authorities(AuthorityUtils.createAuthorityList("ROLE_user", "ROLE_admin"))
                .build();

        SecurityUser user = SecurityUser.builder()
                .userId(UUID.randomUUID().toString().replaceAll("-",""))
                .username("user")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .authorities(AuthorityUtils.createAuthorityList("ROLE_user"))
                .build();
        users.add(admin);
        users.add(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中查询
        List<SecurityUser> list = users.stream()
                .filter(p -> username.equals(p.getUsername())).limit(1)
                .collect(Collectors.toList());
        //用户不存在直接抛出UsernameNotFoundException，security会捕获抛出BadCredentialsException
        if (Objects.isNull(list.get(0)))
            throw new UsernameNotFoundException("用户不存在！");
        return list.get(0);
    }
}
