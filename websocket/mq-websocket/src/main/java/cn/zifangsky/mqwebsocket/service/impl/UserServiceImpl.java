package cn.zifangsky.mqwebsocket.service.impl;

import cn.zifangsky.mqwebsocket.mapper.RoleMapper;
import cn.zifangsky.mqwebsocket.mapper.UserMapper;
import cn.zifangsky.mqwebsocket.mapper.UserRoleMapper;
import cn.zifangsky.mqwebsocket.model.Role;
import cn.zifangsky.mqwebsocket.model.User;
import cn.zifangsky.mqwebsocket.model.UserRole;
import cn.zifangsky.mqwebsocket.service.UserService;
import cn.zifangsky.mqwebsocket.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zifangsky
 * @date 2018/8/3
 * @since 1.0.0
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User selectByUserId(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean register(User user) {
        if(StringUtils.isNoneBlank(user.getUsername()) && StringUtils.isNoneBlank(user.getPassword())){
            Date current = new Date();

            //密码加密存储
            user.setPassword(EncryptUtils.sha256Crypt(user.getPassword(),null));
            user.setCreateTime(current);
            user.setUpdateTime(current);
            user.setStatus(1);

            userMapper.insertSelective(user);
            logger.info("用户注册 --> " + user);

            return true;
        }

        return false;
    }

    @Override
    public Map<String,Object> checkLogin(String username, String password) {
        //返回信息
        Map<String,Object> result = new HashMap<>(2);
        logger.info(MessageFormat.format("用户登录 --> username:{0},password:{1}",username,password));

        User correctUser = userMapper.selectByUsername(username);
        result.put("result", EncryptUtils.checkSha256Crypt(password, correctUser.getPassword()));
        result.put("user", correctUser);

        return result;
    }

    @Override
    public void addUserRole(Integer userId, String roleName) {
        if(userId != null && StringUtils.isNoneBlank(roleName)){
            //1. 查询角色ID
            Role role = roleMapper.selectByRoleName(roleName);

            if(role != null){
                UserRole savedUserRole = userRoleMapper.selectByUserIdRoleId(userId, role.getId());

                if(savedUserRole == null){
                    //2. 给用户添加角色信息
                    UserRole userRole = new UserRole(userId, role.getId());
                    userRoleMapper.insert(userRole);
                }
            }
        }
    }

}
