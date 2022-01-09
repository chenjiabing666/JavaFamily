package cn.myjszl.seata.tcc.order.boot.controller;

import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.order.boot.service.OrderService;
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
    public Result<Void> create(String userId, Long productId, Long num){
        orderService.create(userId,productId,num);
        return Result.resultSuccess("200","请求成功！",null);
    }
}
