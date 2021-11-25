package cn.myjszl.security.jwt.controller;

import cn.myjszl.security.jwt.model.SecurityUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping
    public String hello() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user= (SecurityUser) authentication.getPrincipal();
        return new ObjectMapper().writeValueAsString(user);
    }
}
