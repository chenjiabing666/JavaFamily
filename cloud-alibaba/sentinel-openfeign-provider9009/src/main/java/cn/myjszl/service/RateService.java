package cn.myjszl.service;

import cn.myjszl.model.CommonResponse;
import cn.myjszl.model.Order;

public interface RateService {

    /**
     * 根据订单ID查订单信息
     * @param id
     * @return
     */
    CommonResponse<Order> queryOrder(Long id);

    /**
     * 创建一个订单
     * @param order
     * @return
     */
    CommonResponse createOrder(Order order);

    CommonResponse createOrder2(Order order);



}
