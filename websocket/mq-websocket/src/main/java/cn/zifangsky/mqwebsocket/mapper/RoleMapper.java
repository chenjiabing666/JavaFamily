package cn.zifangsky.mqwebsocket.mapper;

import cn.zifangsky.mqwebsocket.model.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Role selectByRoleName(@Param("roleName") String roleName);

}