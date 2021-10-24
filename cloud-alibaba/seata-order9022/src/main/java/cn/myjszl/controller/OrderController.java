package cn.myjszl.controller;

import cn.myjszl.model.CommonResponse;
import cn.myjszl.model.Order;
import cn.myjszl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public CommonResponse<Order> create(String userId, Long productId, Long num){
        return CommonResponse.<Order>builder()
                .code("200")
                .message("请求成功")
                .data(orderService.create(userId,productId,num))
                .build();
    }


}
