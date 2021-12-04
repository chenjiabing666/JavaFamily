package cn.myjszl.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AuthServerJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerJwtApplication.class);
    }
}
