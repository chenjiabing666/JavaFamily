package cn.myjszl.controller;

import cn.myjszl.model.CommonResponse;
import cn.myjszl.model.Order;
import cn.myjszl.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RequestMapping("/sentinel/rate")
@RestController
public class RateController {

    @Autowired
    private RateService rateService;

    /**
     * 查询订单
     * @param id
     * @return
     */
    @GetMapping("/order/query")
    public CommonResponse<Order> queryOrder(Long id){
        return rateService.queryOrder(id);
    }


    /**
     * 创建订单
     * @param order
     * @return
     */
    @PostMapping("/order/create")
    public CommonResponse createOrder(@RequestBody Order order){
        return rateService.createOrder(order);
    }

    /**
     * 创建订单
     * @param order
     * @return
     */
    @PostMapping("/order/create2")
    public CommonResponse createOrder2(@RequestBody Order order){
        return rateService.createOrder(order);
    }
}
