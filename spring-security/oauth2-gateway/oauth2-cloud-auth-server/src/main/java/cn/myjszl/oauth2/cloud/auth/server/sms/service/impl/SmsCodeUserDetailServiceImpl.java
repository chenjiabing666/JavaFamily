package cn.myjszl.oauth2.cloud.auth.server.sms.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.myjszl.oauth2.cloud.auth.common.model.SecurityUser;
import cn.myjszl.oauth2.cloud.auth.common.model.SysConstant;
import cn.myjszl.oauth2.cloud.auth.server.dao.SysRoleRepository;
import cn.myjszl.oauth2.cloud.auth.server.dao.SysUserRepository;
import cn.myjszl.oauth2.cloud.auth.server.dao.SysUserRoleRepository;
import cn.myjszl.oauth2.cloud.auth.server.model.po.SysUser;
import cn.myjszl.oauth2.cloud.auth.server.model.po.SysUserRole;
import cn.myjszl.oauth2.cloud.auth.server.sms.service.SmsCodeUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * userDetailService实现类（手机号+密码授权类型）
 */
@Service
public class SmsCodeUserDetailServiceImpl implements SmsCodeUserDetailService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        SysUser user = sysUserRepository.findByMobileAndStatus(mobile, 1);
        if (Objects.isNull(user))
            throw new UsernameNotFoundException("账号不存在！");
        //角色
        List<SysUserRole> sysUserRoles = sysUserRoleRepository.findByUserId(user.getId());
        //该用户的所有权限（角色）
        List<String> roles=new ArrayList<>();
        for (SysUserRole userRole : sysUserRoles) {
            sysRoleRepository.findById(userRole.getRoleId()).ifPresent(o-> roles.add(SysConstant.ROLE_PREFIX+o.getCode()));
        }
        return SecurityUser.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                //将角色放入authorities中
                .authorities(AuthorityUtils.createAuthorityList(ArrayUtil.toArray(roles,String.class)))
                .build();
    }
}
