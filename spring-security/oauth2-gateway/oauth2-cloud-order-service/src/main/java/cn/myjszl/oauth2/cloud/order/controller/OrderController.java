package cn.myjszl.oauth2.cloud.order.controller;

import cn.myjszl.oauth2.cloud.auth.common.model.LoginVal;
import cn.myjszl.oauth2.cloud.auth.common.model.ResultMsg;
import cn.myjszl.oauth2.cloud.auth.common.model.order.Order;
import cn.myjszl.oauth2.cloud.auth.common.model.product.Product;
import cn.myjszl.oauth2.cloud.auth.common.utils.OauthUtils;
import cn.myjszl.oauth2.cloud.order.feign.product.ProductFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    //TODO 为了演示构造两个订单
    public static List<Order> orders=new ArrayList<>();

    static{
        Order order1 = Order.builder()
                .id(1L)
                .useMoney(10000L)
                .build();
        Order order2 = Order.builder()
                .id(2L)
                .useMoney(10000L)
                .build();

        orders.add(order1);
        orders.add(order2);
    }


    @Autowired
    private ProductFeignService productFeignService;

    @RequestMapping("/login/info")
    public LoginVal info(){
        return OauthUtils.getCurrentUser();
    }

    @RequestMapping("/login/admin")
    public LoginVal admin(){
        return OauthUtils.getCurrentUser();
    }

    @RequestMapping("/info")
    public ResultMsg info(@RequestParam(value = "orderId") Long orderId){
        ResultMsg resultMsg = productFeignService.listByOrderId(orderId);
        Order order = Order.builder()
                .id(orderId)
                .useMoney(10000L)
                .products((List<Product>) resultMsg.getData())
                .build();
        return new ResultMsg(200,"请求成功!",order);
    }

    @PostMapping("/listByUserId")
    public ResultMsg listByUserId(){
        //获取用户的userId
        String userId = OauthUtils.getCurrentUser().getUserId();
        log.info("当前登录用户的userId：{}",userId);

        //TODO 为了演示不从数据库查询了
        for (Order order : orders) {
            ResultMsg resultMsg = productFeignService.listByOrderId(order.getId());
            order.setProducts((List<Product>) resultMsg.getData());
        }
        return new ResultMsg(200,"请求成功",orders);
    }
}
