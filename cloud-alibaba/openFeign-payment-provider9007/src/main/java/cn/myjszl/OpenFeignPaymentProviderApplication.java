package cn.myjszl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @EnableDiscoveryClient ：开启服务注册和发现
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OpenFeignPaymentProviderApplication
{
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignPaymentProviderApplication.class, args);
    }
}
