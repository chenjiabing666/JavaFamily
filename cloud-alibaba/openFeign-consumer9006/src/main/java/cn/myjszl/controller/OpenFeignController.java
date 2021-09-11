package cn.myjszl.controller;

import cn.myjszl.model.Order;
import cn.myjszl.service.OpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/openfeign")
public class OpenFeignController {
    @Autowired
    private OpenFeignService openFeignService;

    @GetMapping("/test/{id}")
    public String test(@PathVariable("id")Integer id){
        return openFeignService.get(id);
    }

    @RequestMapping("/order1")
    public Order createOrder1(Order order){
        return openFeignService.createOrder1(order);
    }

    @RequestMapping("/order2")
    public Order createOrder2(@RequestBody Order order){
        return openFeignService.createOrder2(order);
    }

    @RequestMapping("/order3")
    public String batchOrder(@RequestBody List<Order> order){
        return openFeignService.batchOrder(order);
    }

    @PostMapping("/test2")
    public String test(String id,String name){
        return openFeignService.test(id,name);
    }



}
