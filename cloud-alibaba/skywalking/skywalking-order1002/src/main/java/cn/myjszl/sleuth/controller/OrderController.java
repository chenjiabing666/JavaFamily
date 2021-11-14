package cn.myjszl.sleuth.controller;

import cn.myjszl.model.Order;
import cn.myjszl.sleuth.service.feign.ProductService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/order")
public class OrderController {
    //调用feign的接口
    @Autowired
    private ProductService productService;

    @GetMapping("/get/{id}")
    public Order listByIds(@PathVariable(value = "id") Long id) throws InterruptedException {
        //todo 未整合db层，造数据
        return new Order(id,20201000L,"bccm",productService.listByIds(Lists.newArrayList(1L,2L,3L,4L)));
    }

    @GetMapping("/list")
    public List<Order> list() throws InterruptedException {

        Thread.sleep(2000);

        //todo 未整合db层，造数据
        return LongStream.of(1,2,3).mapToObj(id-> new Order(id,20201000L,"bccm",productService.listByIds(Lists.newArrayList(1L,2L,3L,4L)))).collect(Collectors.toList());
    }
}
