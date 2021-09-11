package cn.myjszl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class NacosConfigApplication
{
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }
}
