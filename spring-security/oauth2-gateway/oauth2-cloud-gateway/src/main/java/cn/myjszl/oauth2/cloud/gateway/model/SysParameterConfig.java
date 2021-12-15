package cn.myjszl.oauth2.cloud.gateway.model;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author  公众号：码猿技术专栏
 */
@ConfigurationProperties(prefix = "oauth2.cloud.sys.parameter")
@Data
public class SysParameterConfig {
    /**
     * 白名单
     */
    private List<String> ignoreUrls;
}
