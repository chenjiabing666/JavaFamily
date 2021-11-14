package cn.myjszl.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @EnableDiscoveryClient ：开启服务注册和发现
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SkyWalkingOrder1002Application
{
    public static void main(String[] args) {
        SpringApplication.run(SkyWalkingOrder1002Application.class, args);
    }
}
