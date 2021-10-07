package cn.myjszl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "sentinel-openfeign-provider")
public interface FlowService {
    /**
     * 支付接口
     * @return
     */
    @GetMapping("/sentinel/provider/pay")
    String pay();

    @GetMapping("/sentinel/order")
    public String order();
}
