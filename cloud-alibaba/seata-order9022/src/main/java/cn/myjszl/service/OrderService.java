package cn.myjszl.service;

import cn.myjszl.model.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    Order create(String userId, Long productId, Long num);
}
