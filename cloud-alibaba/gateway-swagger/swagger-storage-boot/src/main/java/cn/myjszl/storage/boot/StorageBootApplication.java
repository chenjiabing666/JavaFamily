package cn.myjszl.storage.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StorageBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageBootApplication.class,args);
    }
}
