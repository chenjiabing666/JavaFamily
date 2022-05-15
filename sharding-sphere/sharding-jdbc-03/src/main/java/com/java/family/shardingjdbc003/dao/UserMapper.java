package com.java.family.shardingjdbc003.dao;

import com.java.family.shardingjdbc003.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserMapper {
    @Insert("insert into t_user(user_id, fullname,password,id_card,mobile) value(#{userId},#{fullName},#{cipherPwd},#{idCard},#{mobile})")
    int insertUser(User user);

    @Select("SELECT\n" +
            "\t`password` AS cipherPwd,\n" +
            "\tfullname,\n" +
            "\tuser_type,\n" +
            "\tid_card,\n" +
            "\tmobile\n" +
            "FROM\n" +
            "\tt_user where user_id=#{userId}")
    User selectByUserId(Long userId);

    @Select("SELECT\n" +
            "\t`password` AS cipherPwd,\n" +
            "\tfullname,\n" +
            "\tuser_type,\n" +
            "\tid_card\n" +
            "FROM\n" +
            "\tt_user where user_id=#{userId} and password=#{password} ")
    User selectByUserIdAndPassword(@Param("userId") Long userId,@Param("password") String password);

    @Select("SELECT\n" +
            "\t`password` AS cipherPwd,\n" +
            "\tfullname,\n" +
            "\tuser_type,\n" +
            "\tid_card\n" +
            "FROM\n" +
            "\tt_user where id_card=#{idCard}")
    User selectByIdCard(String idCard);

    @Select("SELECT\n" +
            "\t`password` AS cipherPwd,\n" +
            "\tfullname,\n" +
            "\tuser_type,\n" +
            "\tmobile,\n" +
            "\tid_card\n" +
            "FROM\n" +
            "\tt_user where mobile=#{mobile}")
    List<User> selectByMobile(String mobile);

    @Select("SELECT\n" +
            "\t`password` AS cipherPwd,\n" +
            "\tfullname,\n" +
            "\tuser_type,\n" +
            "\tmobile,\n" +
            "\tid_card\n" +
            "FROM\n" +
            "\tt_user")
    List<User> listAll();

}