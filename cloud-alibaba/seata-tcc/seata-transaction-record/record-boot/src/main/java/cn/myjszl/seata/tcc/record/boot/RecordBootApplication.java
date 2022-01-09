package cn.myjszl.seata.tcc.record.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RecordBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecordBootApplication.class,args);
    }
}
