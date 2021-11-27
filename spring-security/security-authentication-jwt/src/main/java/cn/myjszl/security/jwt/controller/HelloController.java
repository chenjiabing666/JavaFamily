package cn.myjszl.security.jwt.controller;

import cn.myjszl.common.base.dao.UserRepository;
import cn.myjszl.common.base.model.SecurityUser;
import cn.myjszl.common.base.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping
    public String hello() throws JsonProcessingException {
        List<User> all = userRepository.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user= (SecurityUser) authentication.getPrincipal();
        return new ObjectMapper().writeValueAsString(user);
    }
}
