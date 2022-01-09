package cn.myjszl.seata.tcc.order.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OrderBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderBootApplication.class,args);
    }
}
