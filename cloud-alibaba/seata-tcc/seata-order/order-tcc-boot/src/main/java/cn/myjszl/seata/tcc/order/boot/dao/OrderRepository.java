package cn.myjszl.seata.tcc.order.boot.dao;

import cn.myjszl.seata.tcc.order.boot.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByOrderId(String orderId);
}
