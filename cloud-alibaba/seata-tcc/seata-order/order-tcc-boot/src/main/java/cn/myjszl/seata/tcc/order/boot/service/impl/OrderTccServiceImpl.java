package cn.myjszl.seata.tcc.order.boot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.myjszl.seata.tcc.core.model.RequestStatusConstant;
import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.core.utils.IdempotentUtil;
import cn.myjszl.seata.tcc.order.boot.dao.OrderRepository;
import cn.myjszl.seata.tcc.order.boot.model.Order;
import cn.myjszl.seata.tcc.order.boot.service.OrderTccService;
import cn.myjszl.seata.tcc.record.api.feign.TransactionalRecordFeign;
import cn.myjszl.seata.tcc.record.api.model.TransactionalRecord;
import cn.myjszl.seata.tcc.storage.api.feign.StorageFeign;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * @author 公众号：码猿技术专栏
 * 订单的TCC事务模式
 */
@Service
@Slf4j
public class OrderTccServiceImpl implements OrderTccService {

    @Autowired
    private StorageFeign storageFeign;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionalRecordFeign transactionalRecordFeign;

    /**
     * TCC的第一阶段try
     * @Transactional 该注解开启了本地事务，try阶段的全部事务将会回滚
     */
    @Transactional
    @Override
    public boolean tryCreate(BusinessActionContext businessActionContext, String userId,String orderId, Long productId, Long num) {
        log.info("--------开始第一阶段的事务，事务组XID：{}---------",businessActionContext.getXid());
        //判断cancel阶段是否已经执行过--事务日志
        Result<TransactionalRecord> result1 = transactionalRecordFeign.getByXid(businessActionContext.getXid());
        TransactionalRecord record = result1.getData();
        if (!StrUtil.equals(result1.getCode(),RequestStatusConstant.SUCCESS_CODE))
            throw new RuntimeException("事务日志查询失败！");
        //1. try  2 commit 3 cancel
        if (Objects.nonNull(record)&&Integer.valueOf(3).equals(record.getStatus()))
            throw new RuntimeException("已经进入了TCC第二阶段的cancel阶段，不允许try阶段！");

        //冻结库存
        Result<Void> result2 = storageFeign.frozen(productId, num);
        if (!StrUtil.equals(result2.getCode(), RequestStatusConstant.SUCCESS_CODE))
            throw new RuntimeException("冻结库存失败！");

        //生成订单
        Order order = Order.builder()
                .orderId(orderId)
                .userId(userId)
                .productId(productId)
                .num(num)
                .status(2)
                .createTime(new Date())
                .build();
        saveOrder(order);
        //保证幂等性
        IdempotentUtil.add(getClass(),businessActionContext.getXid(),System.currentTimeMillis());
        return true;
    }

    @Transactional
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        //校验幂等性，防止多次提交
        if (Objects.isNull(IdempotentUtil.get(getClass(), businessActionContext.getXid()))) {
            return true;
        }

        log.info("--------开始第二阶段的commit事务，事务组XID：{}---------",businessActionContext.getXid());
        long productId = Long.parseLong(businessActionContext.getActionContext("productId").toString());
        long num = Long.parseLong(businessActionContext.getActionContext("num").toString());
        String orderId = businessActionContext.getActionContext("orderId").toString();

        //释放冻结的库存
        Result<Void> result = storageFeign.cleanFrozen(productId, num);
        if (!StrUtil.equals(result.getCode(), RequestStatusConstant.SUCCESS_CODE))
            return false;

        //修改订单的状态为已完成
        Order order = Order.builder()
                .orderId(orderId)
                //已完成状态
                .status(3)
                .build();
        saveOrder(order);
        //提交成功，则移出
        IdempotentUtil.remove(getClass(),businessActionContext.getXid());
        return true;
    }

    @Transactional
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        //防止悬挂，只要cancel插入一条事务记录
        TransactionalRecord record = TransactionalRecord.builder()
                .xid(businessActionContext.getXid())
                //cancel阶段
                .status(2)
                .build();
        //这个操作也必须保证幂等，添加多次和添加一次效果相同
        Result<TransactionalRecord> result1 = transactionalRecordFeign.add(record);
        if (!StrUtil.equals(result1.getCode(),RequestStatusConstant.SUCCESS_CODE))
            return false;
        //校验幂等性、空回滚
        if (Objects.isNull(IdempotentUtil.get(getClass(), businessActionContext.getXid()))) {
            return true;
        }
        log.info("--------开始第二阶段的rollback事务，事务组XID：{}---------",businessActionContext.getXid());
        long productId = Long.parseLong(businessActionContext.getActionContext("productId").toString());
        long num = Long.parseLong(businessActionContext.getActionContext("num").toString());
        long orderId = Long.parseLong(businessActionContext.getActionContext("orderId").toString());
        //恢复冻结的库存
        Result<Void> result = storageFeign.rollbackFrozen(productId, num);
        if (!StrUtil.equals(result.getCode(), RequestStatusConstant.SUCCESS_CODE))
            return false;
        //删除该笔订单--逻辑删除
        Order order = Order.builder()
                .id(orderId)
                //删除
                .status(5)
                .build();
        orderRepository.save(order);
        //回滚成功，则移出
        IdempotentUtil.remove(getClass(),businessActionContext.getXid());
        return true;
    }


    private Order saveOrder(Order order){
        Order order1 = orderRepository.findByOrderId(order.getOrderId());
        if (Objects.isNull(order1))
            return orderRepository.save(order);
        else{
            order.setId(order1.getId());
            return orderRepository.save(order);
        }
    }
}
