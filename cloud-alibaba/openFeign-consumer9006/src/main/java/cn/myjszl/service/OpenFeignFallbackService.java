package cn.myjszl.service;

import cn.myjszl.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenFeignFallbackService implements OpenFeignService {
    @Override
    public String get(Integer id) {
        return null;
    }

    @Override
    public Order createOrder1(Order order) {
        return null;
    }

    @Override
    public Order createOrder2(Order order) {
        return null;
    }

    @Override
    public String test(String arg1, String arg2) {
        return null;
    }

    @Override
    public String batchOrder(List<Order> orders) {
        return "批量下单服务降级........";
    }
}
