package cn.myjszl.security.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"cn.myjszl.security.jwt.**",
        "cn.myjszl.common.base.service.impl",
        "cn.myjszl.common.base.utils",
        "cn.myjszl.common.base.handler"})
@EnableJpaRepositories(basePackages = { "cn.myjszl.common.base.dao" })
@EntityScan(basePackages = { "cn.myjszl.common.base.model" })
@EnableAspectJAutoProxy
public class SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class);
    }
}
