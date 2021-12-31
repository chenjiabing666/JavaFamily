package cn.myjszl.oauth2.cloud.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"cn.myjszl.oauth2.cloud.auth.common.security.filter","cn.myjszl.oauth2.cloud.product.*"})
public class OAuthCloudProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthCloudProductApplication.class);
    }
}