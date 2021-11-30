package cn.myjszl.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan({"cn.myjszl.oauth.server.**",
//        "cn.myjszl.common.base.service.impl",
//        "cn.myjszl.common.base.utils",
//        "cn.myjszl.common.base.handler"})
//@EnableJpaRepositories(basePackages = { "cn.myjszl.common.base.dao" })
//@EntityScan(basePackages = { "cn.myjszl.common.base.model" })
@EnableAspectJAutoProxy
public class AuthResourceInMemoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthResourceInMemoryApplication.class);
    }
}
