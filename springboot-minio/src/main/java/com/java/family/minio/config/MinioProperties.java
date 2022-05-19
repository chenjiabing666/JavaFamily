package com.java.family.minio.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 公众号：码猿技术专栏
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * 是一个URL，域名，IPv4或者IPv6地址")
     */
    private String endpoint;

    /**
     *     //"TCP/IP端口号"
     */
    private Integer port;

    /**
     *     //"accessKey类似于用户ID，用于唯一标识你的账户"
     */
    private String accessKey;

    /**
     *     //"secretKey是你账户的密码"
     */
    private String secretKey;

    /**
     *     //"如果是true，则用的是https而不是http,默认值是true"
     */
    private boolean secure;

    /**
     *     //"默认存储桶"
     */
    private String bucketName;

    /**
     * 图片的最大大小
     */
    private long imageSize;

    /**
     * 其他文件的最大大小
     */
    private long fileSize;


    /**
     * 官网给出的 构造方法，我只是去爬了一下官网 （狗头保命）
     * 此类是 客户端进行操作的类
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient =
                MinioClient.builder()
                        .credentials(accessKey, secretKey)
                        .endpoint(endpoint,port,secure)
                        .build();
        return minioClient;
    }
}
