package cn.myjszl.swagger.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
@Component
@EnableConfigurationProperties
public class SwaggerProperties {
    public static final String PREFIX="spring.swagger";

    //包
    private String basePackage;

    //作者相关信息
    private Author author;

    //API的相关信息
    private ApiInfo apiInfo;

    @Data
    public static class ApiInfo{
        String title;
        String description;
        String version;
        String termsOfServiceUrl;
        String license;
        String licenseUrl;
    }

    @Data
    public static class Author{
        private String name;

        private String email;

        private String url;
    }
}
