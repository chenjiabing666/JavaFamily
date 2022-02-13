package cn.myjszl.swagger.starter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * @author 公众号：码猿技术局专栏
 * Swagger的自动配置类
 */
@ComponentScan
@Configuration
@EnableSwagger2
@Profile(value = {"dev","test"})
public class SwaggerAutoConfig implements WebMvcConfigurer {

    @Autowired
    private SwaggerProperties properties;


    //配置了Swagger的Docket的bean实例
    //enable是否启动swagger，如果为False则Swagger不能在浏览器访问
    @Bean
    public Docket docket() {
        Set<String> set = new HashSet<>();
        set.add("https");
        set.add("http");
        return new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .enable(true)//定义是否开启swagger，false为关闭，可以通过变量控制
                // 微信关注开发者技术前线：定义是否开启swagger，false为关闭，可以通过变量控制
                .apiInfo(apiInfo())//将api的元信息设置为包含在json ResourceListing响应中。
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                //paths()过滤什么路径
                .paths(PathSelectors.any())
                .build()
                .protocols(set)// 支持的通讯协议集合
                .securitySchemes(securitySchemes())// 授权信息设置，必要的header token等认证信息
                .securityContexts(securityContexts());// 授权信息全局应用
    }

    //配置Swagger 信息 = ApiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact(properties.getAuthor().getName(),properties.getAuthor().getUrl(),properties.getAuthor().getEmail());
        return new ApiInfo(properties.getApiInfo().getTitle(),
                properties.getApiInfo().getDescription(),
                properties.getApiInfo().getVersion(),
                properties.getApiInfo().getTermsOfServiceUrl(),
                contact,
                properties.getApiInfo().getLicense(),
                properties.getApiInfo().getLicenseUrl(),
                new ArrayList<>());
    }
    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes()
    {
       List<ApiKey> result = new ArrayList<>();
       //添加OAuth2的令牌认证
       ApiKey apiKey = new ApiKey("Authorization","Authorization" ,"Header" );
       result.add(apiKey);
       return  Collections.singletonList(apiKey);
    }
    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("Authorization",
                                new AuthorizationScope[]{new AuthorizationScope("global", "Authorization")})))
                        .build()
        );
    }

}
