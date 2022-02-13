package cn.myjszl.swagger.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SwaggerGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerGatewayApplication.class, args);
    }
}
