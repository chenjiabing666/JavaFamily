package cn.myjszl.sleuth.controller;

import cn.myjszl.model.Order;
import cn.myjszl.model.Product;
import cn.myjszl.sleuth.service.feign.ProductService;
import com.google.common.collect.Lists;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/order")
public class OrderController {
    //调用feign的接口
    @Autowired
    private ProductService productService;

    @GetMapping("/get/{id}")
    public Order listByIds(@PathVariable(value = "id") Long id){
        //todo 未整合db层，造数据
        return new Order(id,20201000L,"bccm",productService.listByIds(Lists.newArrayList(1L,2L,3L,4L)));
    }
}
