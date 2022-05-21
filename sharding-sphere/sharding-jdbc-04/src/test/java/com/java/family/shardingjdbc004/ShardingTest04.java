package com.java.family.shardingjdbc004;

import com.java.family.shardingjdbc004.dao.OrderMapper;
import com.java.family.shardingjdbc004.dao.ProductMapper;
import com.java.family.shardingjdbc004.dao.ProductTypeMapper;
import com.java.family.shardingjdbc004.domain.Order;
import com.java.family.shardingjdbc004.domain.Product;
import com.java.family.shardingjdbc004.domain.ProductType;
import com.java.family.shardingjdbc004.domain.ProductTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.text.resources.FormatData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardingTest04 {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    /**
     * 插入数据
     */
    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .name("Spring Cloud Alibaba实战课程")
                    .price(159L)
                    .originAddress("码猿技术专栏")
                    .shopId((long) (new Random().nextInt(100) + 1))
                    .typeId(1L)
                    .build();
            productMapper.insertProductBase(product);
            productMapper.insertProductDescribe(product.getProductId(), "内容", product.getShopId());
        }
    }

    @Test
    public void test2() {
        List<Product> products = productMapper.listByShopId(60L);
        System.out.println(products);
    }


    @Test
    public void test3() {
        List<Product> products = productMapper.listByJoin();
        System.out.println(products);
    }

    public static SnowflakeShardingKeyGenerator snowflake;

    @Autowired
    private OrderMapper orderMapper;

    @Before
    public void before() {
        snowflake = new SnowflakeShardingKeyGenerator();
    }

    @Test
    public void test5() {
        List<Long> userIds = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            userIds.add((Long) snowflake.generateKey());
        }

        for (int i = 0; i < 10; i++) {
            Long userId = userIds.get(i % 2);
            //唯一ID+userId后1位
            Long orderId = Long.valueOf(snowflake.generateKey() + userId.toString().substring(userId.toString().length() - 1));
            Order order = Order.builder()
                    .userId(userId)
                    .orderId(orderId)
                    .price(1000L)
                    .status(1)
                    .build();
            orderMapper.insert(order);
        }
    }

    @Test
    public void test6() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Long userId = (Long) snowflake.generateKey();
            //唯一ID+userId后1位
            Long orderId = ((Long) snowflake.generateKey()) + userId.toString().indexOf(userId.toString().length() - 1);
            Order order = Order.builder()
                    .userId(userId)
                    .orderId(orderId)
                    .price(1000L)
                    .status(1)
                    .build();
            orders.add(order);
        }
        orderMapper.insertBatch(orders);
    }


    @Test
    public void test7() {
        Order order = orderMapper.selectByOrderId(7348203119202795535L);
        System.out.println(order);
    }

    @Test
    public void test8() {
        List<Order> orders = orderMapper.selectByUserId(734820309563080704L);
        System.out.println(orders);
    }

    @Test
    public void test9() {
        // 清除掉上一次的规则，否则会报错
        HintManager.clear();
        // HintManager API 工具类实例
        HintManager hintManager = HintManager.getInstance();
        // 直接指定对应具体的数据库
        hintManager.addDatabaseShardingValue("ds", 1);
        // 设置表的分片健
        hintManager.addTableShardingValue("t_order", 1);
//        hintManager.addTableShardingValue("t_order", 2);
        // 在读写分离数据库中，Hint 可以强制读主库
        hintManager.setMasterRouteOnly();

        List<Long> userIds = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            userIds.add((Long) snowflake.generateKey());
        }

        for (int i = 0; i < 10; i++) {
            Long userId = userIds.get(i % 2);
            //唯一ID+userId后1位
            Long orderId = Long.valueOf(snowflake.generateKey() + userId.toString().substring(userId.toString().length() - 1));
            Order order = Order.builder()
                    .userId(userId)
                    .orderId(orderId)
                    .price(1000L)
                    .status(1)
                    .build();
            orderMapper.insert(order);
        }
    }

}
