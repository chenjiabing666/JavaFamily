package cn.myjszl;

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
public class SentinelOpenFeignConsumer9010Application
{
    public static void main(String[] args) {
        SpringApplication.run(SentinelOpenFeignConsumer9010Application.class, args);
    }
}
