package cn.myjszl.seata.tcc.order.boot.service.impl;

import cn.myjszl.seata.tcc.order.boot.service.OrderService;
import cn.myjszl.seata.tcc.order.boot.service.OrderTccService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderTccService orderTccService;

    /**
     * 下订单的业务接口，其中完成了扣减库存-》扣减余额-》创建订单的流程
     * 使用@GlobalTransactional这个注解开启一个全局的分布式事务
     * @param userId  用于唯一Id
     * @param productId  商品Id
     * @param num  购买数量
     */
    @GlobalTransactional
    @Override
    public void create(String userId, Long productId, Long num) {
        //TODO 全局发号器生成订单唯一ID，为了演示这里直接使用UUID
        orderTccService.tryCreate(null,userId, UUID.randomUUID().toString(),productId,num);
    }
}
