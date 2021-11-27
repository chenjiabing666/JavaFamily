package cn.myjszl.common.base.service.impl;

import cn.myjszl.common.base.constant.LoginConstant;
import cn.myjszl.common.base.dao.*;
import cn.myjszl.common.base.model.*;
import cn.myjszl.common.base.service.LoginService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleAuthRepository roleAuthRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthRepository authRepository;

    //加密算法，在配置类中注入IOC，这里直接使用
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nullable
    @Override
    public SecurityUser loadByUsername(String username) {
        //获取用户信息
        User user = userRepository.findByUsernameAndStatus(username, LoginConstant.USER_USED);
        if (Objects.nonNull(user)){
            SecurityUser securityUser = new SecurityUser();
            securityUser.setUsername(username);
            securityUser.setPassword(passwordEncoder.encode(user.getPassword()));
            //查询该用户的角色
            List<UserRole> userRoles = userRoleRepository.findByUserIdAndStatus(user.getUserId(), LoginConstant.USER_ROLE_USED);
            Collection<? extends GrantedAuthority> authorities = merge(userRoles);
            securityUser.setAuthorities(authorities);
            return securityUser;
        }
        return null;
    }

    /**
     * 查询角色
     */
    private List<String> listRoles(List<UserRole> userRoles){
        List<String> list=new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRoles)){
            userRoles.forEach(param-> roleRepository.findById(param.getRoleId()).ifPresent(o-> list.add(o.getName())));
        }
        return list;
    }

    private List<String> listAuths(List<UserRole> userRoles){
        List<String> list=new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRoles)){
            userRoles.forEach(param->{
                List<RoleAuth> roleAuths = roleAuthRepository.findByRoleIdAndStatus(param.getRoleId(), LoginConstant.ROLE_AUTH_USED);
                //查询权限
                if (!CollectionUtils.isEmpty(roleAuths)){
                    roleAuths.forEach(o-> authRepository.findById(o.getAuthId()).ifPresent(auth->list.add(auth.getUrl())));
                }
            });
        }
        return list;
    }

    private Collection<? extends GrantedAuthority> merge(List<UserRole> userRoles){
        List<String> roles = listRoles(userRoles);
        List<String> auths = listAuths(userRoles);
        String[] a={};
        roles.addAll(auths);
        return AuthorityUtils.createAuthorityList(roles.toArray(a));
    }

}
