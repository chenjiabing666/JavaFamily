package cn.myjszl.controller;

import cn.myjszl.model.Order;
import cn.myjszl.model.Payment;
import cn.myjszl.service.OpenFeignPaymentService;
import cn.myjszl.service.OpenFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openfeign/order")
@Slf4j
public class OrderController {

    @Autowired
    private OpenFeignService openFeignService;

    @Autowired
    private OpenFeignPaymentService paymentService;

    @PostMapping
    public String create(@RequestBody Order order){
        //1. 调用生成订单服务
        Order order2 = openFeignService.createOrder2(order);
        log.info("下单成功：{}",order2.toString());

        Payment payment = Payment.builder()
                .id(1200L)
                .money(10000L)
                .build();
        String res = paymentService.pay(payment);
        log.info("付款成功：{}",res);
        return "success";
    }

}
