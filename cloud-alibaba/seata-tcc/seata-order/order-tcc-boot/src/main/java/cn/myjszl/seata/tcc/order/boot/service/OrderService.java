package cn.myjszl.seata.tcc.order.boot.service;


import cn.myjszl.seata.tcc.order.boot.model.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    void create(String userId,Long productId, Long num);
}
