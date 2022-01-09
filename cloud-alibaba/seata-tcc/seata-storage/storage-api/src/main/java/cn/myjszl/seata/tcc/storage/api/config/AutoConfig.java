package cn.myjszl.seata.tcc.storage.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "cn.myjszl.seata.tcc.storage.api.feign")
public class AutoConfig {
}
