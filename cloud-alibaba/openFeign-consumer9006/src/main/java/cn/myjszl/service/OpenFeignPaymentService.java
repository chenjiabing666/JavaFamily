package cn.myjszl.service;

import cn.myjszl.model.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "openFeign-payment-provider")
public interface OpenFeignPaymentService {

    /**
     * 付款的服务
     * @param payment
     * @return
     */
    @PostMapping("/openfeign/payment/pay")
    String pay(Payment payment);
}
