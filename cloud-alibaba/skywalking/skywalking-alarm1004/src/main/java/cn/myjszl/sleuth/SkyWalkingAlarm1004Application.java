package cn.myjszl.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @EnableDiscoveryClient ：开启服务注册和发现
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SkyWalkingAlarm1004Application
{
    public static void main(String[] args) {
        SpringApplication.run(SkyWalkingAlarm1004Application.class, args);
    }
}
