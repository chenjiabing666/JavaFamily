package cn.myjszl.oauth2.cloud.order.controller;

import cn.myjszl.oauth2.cloud.auth.common.model.LoginVal;
import cn.myjszl.oauth2.cloud.auth.common.model.ResultMsg;
import cn.myjszl.oauth2.cloud.auth.common.model.order.Order;
import cn.myjszl.oauth2.cloud.auth.common.model.product.Product;
import cn.myjszl.oauth2.cloud.auth.common.utils.OauthUtils;
import cn.myjszl.oauth2.cloud.order.feign.product.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

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

    @PostMapping("/info")
    public ResultMsg info(@RequestParam(value = "orderId") Long orderId){
        ResultMsg resultMsg = productFeignService.listByOrderId(orderId);
        Order order = Order.builder()
                .id(orderId)
                .useMoney(10000L)
                .products((List<Product>) resultMsg.getData())
                .build();
        return new ResultMsg(200,"请求成功!",order);
    }
}
