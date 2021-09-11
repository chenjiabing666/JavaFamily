package cn.myjszl.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Data
public class DynamicConfigEntity {

    //直接读取nacos中config.version的配置
    @Value("${config.version}")
    private String version;
}
