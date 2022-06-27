package cn.zifangsky.mqwebsocket.mapper;

import cn.zifangsky.mqwebsocket.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);

    User selectByUsername(@Param("username") String username);

}