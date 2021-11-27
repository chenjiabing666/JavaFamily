package cn.myjszl.security.jwt.controller;

import cn.myjszl.common.base.dao.UserRepository;
import cn.myjszl.common.base.model.SecurityUser;
import cn.myjszl.common.base.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private UserRepository userRepository;

//    @Secured(value = "ROLE_user")
    @PreAuthorize(value = "hasRole('ROLE_admin') or hasAuthority('user')")
    @PostMapping("/hello1")
    public String hello1() throws JsonProcessingException {
        log.info("执行hello方法................");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user= (SecurityUser) authentication.getPrincipal();
        return new ObjectMapper().writeValueAsString(user);
    }

    @PostAuthorize(value = "denyAll()")
    @PostMapping("/hello2")
    public String hello2() throws JsonProcessingException {
        log.info("执行hello方法................");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user= (SecurityUser) authentication.getPrincipal();
        return new ObjectMapper().writeValueAsString(user);
    }


    /**
     * filterTarget 指定需要过滤的参数
     */
    @PreFilter(value = "filterObject>1",filterTarget = "ids")
    @PostMapping("/hello3")
    public String hello3(@RequestParam(value = "ids") List<Integer> ids, @RequestParam(value = "usernames") List<String> usernames) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ids) +"\n"+ mapper.writeValueAsString(usernames);
    }

    /**
     *  @PostFilter：对返回的集合进行过滤，支持SpEL表达式
     *  authentication.principal.username：获取当前登录的username
     */
    @PostFilter(value = "filterObject != authentication.principal.username")
    @PostMapping("/hello4")
    public List<String> hello4(@RequestParam(value = "ids") List<Integer> ids, @RequestParam(value = "usernames") List<String> usernames)  {
        return usernames;
    }


    @Secured(value = {"ROLE_admin","ROLE_user"})
    @PostMapping("/hello5")
    public String hello5() throws JsonProcessingException {
        log.info("执行hello方法................");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user= (SecurityUser) authentication.getPrincipal();
        return new ObjectMapper().writeValueAsString(user);
    }

    @RolesAllowed({"admin"})
    @PostMapping("/hello6")
    public String hello6() throws JsonProcessingException {
        log.info("执行hello方法................");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user= (SecurityUser) authentication.getPrincipal();
        return new ObjectMapper().writeValueAsString(user);
    }

//    @Secured({"ROLE_user"})
    @PreAuthorize(value = "hasRole('ROLE_user')")
    @PostMapping("/hello7")
    public String hello7() throws JsonProcessingException {
        log.info("执行hello方法................");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user= (SecurityUser) authentication.getPrincipal();
        return new ObjectMapper().writeValueAsString(user);
    }


    @PostMapping("/hello8")
    public List<User> hello8() throws JsonProcessingException {
        return userRepository.findAll();
    }


    @PostMapping("/admin")
    public String admin(){
        return "admin";
    }

    @PostMapping("/user")
    public String user(){
        return "user";
    }
}
