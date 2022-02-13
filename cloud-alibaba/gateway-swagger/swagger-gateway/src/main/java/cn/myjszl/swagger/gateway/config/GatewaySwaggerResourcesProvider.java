package cn.myjszl.swagger.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 网关的swagger的配置类
 */
@Component
@Primary
public class GatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {
    /**
     * swagger3默认的url后缀
     */
    private static final String SWAGGER2URL = "/v2/api-docs";
    /**
     * 网关路由
     */
    @Autowired
    private  RouteLocator routeLocator;
    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;
    /**
     * 对于gateway来说这块比较重要 让swagger能找到对应的服务
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        // 获取所有可用的host：serviceId
        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
                .filter(route -> !self.equals(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));
        // 记录已经添加过的server
        Set<String> dealed = new HashSet<>();
        routeHosts.forEach(instance -> {
            // 拼接url
            String url = "/" + instance.toLowerCase() + SWAGGER2URL;
            if (!dealed.contains(url)) {
                dealed.add(url);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(instance);
                resources.add(swaggerResource);
            }
        });
        return resources;
    }
}