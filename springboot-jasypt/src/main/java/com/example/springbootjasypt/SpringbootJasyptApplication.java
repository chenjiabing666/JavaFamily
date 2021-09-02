package com.example.springbootjasypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringbootJasyptApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJasyptApplication.class, args);
    }

}
