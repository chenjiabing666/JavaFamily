package cn.myjszl.seata.tcc.record.api.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.myjszl.seata.tcc.record.api.feign")
public class AutoConfig {
}
