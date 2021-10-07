package cn.myjszl.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求来源解析类
 */
@Component
public class RequestOrigin implements RequestOriginParser {
    /**
     * 解析请求来源
     * @param httpServletRequest 该参数能够获取请求的一切信息，方便开发者解析
     * @return 返回的就是请求来源名称
     */
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRemoteAddr();
    }
}
