package com.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author chenan
 * date 2020-06-01
 */
@SpringBootApplication
public class JobApplication {

    private static final String ACTIVE_PROFILES = "spring.profiles.active";

    public static void main(String[] args) {

        //如果没有设置启动参数，将自动分配9999或者9998
        if (StringUtils.isEmpty(System.getProperty(ACTIVE_PROFILES))) {
            autoSetProfiles();
        }
        SpringApplication.run(JobApplication.class, args);
    }

    private static void autoSetProfiles() {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            //能成功连接9999，说明9999已经被占用了.
            System.setProperty(ACTIVE_PROFILES, "9998");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //抛异常，表示9999端口还未被使用.
            System.setProperty(ACTIVE_PROFILES, "9999");
        }
    }
}
