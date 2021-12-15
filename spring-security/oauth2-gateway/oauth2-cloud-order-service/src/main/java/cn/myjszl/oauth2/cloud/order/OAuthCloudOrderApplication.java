package cn.myjszl.oauth2.cloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"cn.myjszl.oauth2.cloud.auth.common.security.filter","cn.myjszl.oauth2.cloud.order.*"})
public class OAuthCloudOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthCloudOrderApplication.class);
    }
}
