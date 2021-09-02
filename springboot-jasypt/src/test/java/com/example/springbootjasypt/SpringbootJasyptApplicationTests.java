package com.example.springbootjasypt;

import com.example.springbootjasypt.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SpringbootJasyptApplicationTests {

    /**
     * 注入加密方法
     */
    @Autowired
    private StringEncryptor encryptor;

    /**
     * 手动生成密文，此处演示了url，user，password
     */
    @Test
    public void encrypt() {
        String url = encryptor.encrypt("jdbc\\:mysql\\://127.0.0.1\\:3306/test?useUnicode\\=true&characterEncoding\\=UTF-8&zeroDateTimeBehavior\\=convertToNull&useSSL\\=false&allowMultiQueries\\=true&serverTimezone=Asia/Shanghai");
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt("123456");
        System.out.println("database url: " + url);
        System.out.println("database name: " + name);
        System.out.println("database password: " + password);
        Assert.assertTrue(url.length() > 0);
        Assert.assertTrue(name.length() > 0);
        Assert.assertTrue(password.length() > 0);
    }


    @Test
    public void test() throws JsonProcessingException {
        Person user = new Person();

        user.setRealName("张三丰");
        user.setPhoneNumber("13333333333");
        user.setAddress("湖北省十堰市丹江口市武当山");
        user.setIdCard("4333333333334334333");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(user);

        System.out.println(json);
    }

    @Test
    public void test3(){
        log.debug("身份证：{}，姓名：{}，电话：{}","320829112334566767","不才陈某","19896327106");
    }


}
