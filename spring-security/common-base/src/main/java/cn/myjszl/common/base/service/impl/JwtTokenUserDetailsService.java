package cn.myjszl.security.jwt.service;

import cn.myjszl.common.base.model.SecurityUser;
import cn.myjszl.common.base.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author 公众号：码猿技术专栏
 * 从数据库中根据用户名查询用户的详细信息，包括权限
 *
 * 数据库设计：角色、用户、权限、角色<->权限、用户<->角色 总共五张表，遵循RBAC设计
 */
@Service
public class JwtTokenUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginService loginService;

    //加密算法，在配置类中注入IOC，这里直接使用
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //todo 此处自己从数据库中查询，陈某这里手动构造数据
        //由于使用BCryptPasswordEncoder加密，因此需要将明文加密才能匹配
        return new SecurityUser(username,
                //由于密码是加密的，因此需要对明文密码进行加密才能匹配
                passwordEncoder.encode("12345"),
                //权限+角色的集合，角色要ROLE_ 开头
                AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    }
}
