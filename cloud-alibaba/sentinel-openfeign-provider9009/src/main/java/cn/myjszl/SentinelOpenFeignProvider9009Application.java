package cn.myjszl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @EnableDiscoveryClient ：开启服务注册和发现
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelOpenFeignProvider9009Application
{
    public static void main(String[] args) {
        SpringApplication.run(SentinelOpenFeignProvider9009Application.class, args);
    }
}
