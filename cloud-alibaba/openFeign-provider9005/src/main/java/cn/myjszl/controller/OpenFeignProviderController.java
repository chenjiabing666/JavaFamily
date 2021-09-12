package cn.myjszl.controller;

import cn.myjszl.model.Order;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/openfeign/provider")
public class OpenFeignProviderController {

    @GetMapping("/test/{id}")
    public String test(@PathVariable("id")Integer id){
        return "accept one msg id="+id;
    }

    @PostMapping("/order1")
    public Order createOrder1(Order order){
        return order;
    }

    @PostMapping("/order2")
    public Order createOrder2(@RequestBody Order order){
        return order;
    }

    @PostMapping("/order3")
    public String batchOrder(@RequestBody List<Order> orders)  {
        //造出异常
        System.out.println(1/0);
        return orders.toString();
    }

    @PostMapping("/test2")
    public String test2(String id,String name) throws InterruptedException {
        Thread.sleep(3000);
        return MessageFormat.format("accept on msg id={0}，name={1}",id,name);
    }

}
