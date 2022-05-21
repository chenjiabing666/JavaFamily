package com.java.family.shardingjdbc004.dao;

import com.google.common.collect.Lists;
import com.java.family.shardingjdbc004.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mockito.internal.matchers.Or;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
@Component
@Mapper
public interface OrderMapper {

    @Insert(value = "insert into t_order(order_id,price,user_id,status) value(#{orderId},#{price},#{userId},#{status})")
    int insert(Order order);


    @Insert("insert into t_order(order_id,price,user_id,status) values(1,2,3,1),(2,2,3,4)")
    int insertBatch(@Param("orders") List<Order> orders);

    @Select("select * from t_order where order_id=#{orderId}")
    Order selectByOrderId(Long orderId);

    @Select("select * from t_order where user_id=#{userId}")
    List<Order> selectByUserId(Long userId);


}
