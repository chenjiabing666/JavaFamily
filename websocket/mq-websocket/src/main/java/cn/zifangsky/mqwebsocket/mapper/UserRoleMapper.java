package cn.zifangsky.mqwebsocket.mapper;

import cn.zifangsky.mqwebsocket.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    /**
     * 通过用户ID和角色ID查询用户角色信息
     * @author zifangsky
     * @date 2018/8/18 11:10
     * @since 1.0.0
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return cn.zifangsky.model.UserRole
     */
    UserRole selectByUserIdRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 通过角色名查询用户角色信息
     * @author zifangsky
     * @date 2018/8/18 11:10
     * @since 1.0.0
     * @param roleName 角色名
     * @return cn.zifangsky.model.UserRole
     */
    UserRole selectByRoleName(@Param("roleName") String roleName);

    /**
     * 通过用户ID查询用户角色信息
     * @author zifangsky
     * @date 2018/8/18 11:10
     * @since 1.0.0
     * @param userId 用户ID
     * @return cn.zifangsky.model.UserRole
     */
    List<UserRole> selectByUserId(@Param("userId") Integer userId);
}