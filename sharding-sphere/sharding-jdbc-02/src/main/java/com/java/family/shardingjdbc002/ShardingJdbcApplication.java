package com.java.family.shardingjdbc002;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.java.family.shardingjdbc002.dao")
public class ShardingJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class,args);
    }
}
