package cn.myjszl.seata.dependency.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@ComponentScan(basePackages = {"cn.myjszl.seata.dependency"})
public class AutoConfig {
}
