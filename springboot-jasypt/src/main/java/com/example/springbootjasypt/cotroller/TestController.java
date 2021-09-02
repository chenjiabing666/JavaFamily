package com.example.springbootjasypt.cotroller;

import com.example.springbootjasypt.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public Person test(){
        Person user = new Person();

        user.setRealName("不才陈某");
        user.setPhoneNumber("19796328206");
        user.setAddress("浙江省杭州市温州市....");
        user.setIdCard("4333333333334334333");
        return user;
    }
}
