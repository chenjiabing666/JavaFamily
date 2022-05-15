package com.java.family.shardingjdbc003;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.family.shardingjdbc003.dao.UserMapper;
import com.java.family.shardingjdbc003.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardingTest03 {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertUser() {
        for (int i = 0; i < 10;     i++) {
            User user = new User();
            user.setFullName("不才陈某");
            user.setCipherPwd("abc123");
            user.setIdCard("320829198708012232");
            user.setUserId((long)i);
            user.setMobile("13852331509");
            userMapper.insertUser(user);
        }
    }

    @Test
    public void testList() throws JsonProcessingException {
        List<User> users = userMapper.listAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(users);
        System.out.println(json);
    }

    @Test
    public void test2() {
        User user = userMapper.selectByUserId(1L);
        System.out.println(user);
    }

    @Test
    public void test3() {
        User user = userMapper.selectByUserIdAndPassword(2222L,"abc123");
        System.out.println(user);
    }

    @Test
    public void test4() {
        User user = userMapper.selectByIdCard("320829198708012232");
        System.out.println(user);
    }

    @Test
    public void test5(){
        List<User> users = userMapper.selectByMobile("13852331509");
        System.out.println(users);
    }
}
