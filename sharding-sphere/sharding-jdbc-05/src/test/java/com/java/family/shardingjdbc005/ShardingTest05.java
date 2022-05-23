package com.java.family.shardingjdbc005;

import com.java.family.shardingjdbc005.dao.ProductMapper;
import com.java.family.shardingjdbc005.model.Product;
import com.java.family.shardingjdbc005.model.ProductType;
import com.java.family.shardingjdbc005.model.ProductTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardingTest05 {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void test1(){
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .name("Spring Cloud Alibaba实战课程")
                    .price(159L)
                    .originAddress("码猿技术专栏")
                    .shopId((long)(new Random().nextInt(100)+1))
                    .typeId(1L)
                    .productId((Long) new SnowflakeShardingKeyGenerator().generateKey())
                    .build();
            productMapper.insertProductBase(product);
        }
    }


    @Test
    public void test2(){
        //通过setMasterRouteOnly设置从主库查询，只在当前线程生效
//        HintManager.getInstance().setMasterRouteOnly();
        Product product = productMapper.selectByProductId(735430923050287104L);
        System.out.println(product);
    }

    /**
     * 插入商品类型
     */
    @Test
    public void test3(){
        ProductType productType = new ProductType();
        productType.setTypeId(1L);
        productType.setTypeName("母婴");
//        productTypeMapper.insert(productType);
    }


    @Test
    public void test4(){
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .name("Spring Cloud Alibaba实战课程")
                    .price(159L)
                    .originAddress("码猿技术专栏")
                    .shopId((long)(new Random().nextInt(100)+1))
                    .typeId(1L)
                    .build();
            productMapper.insertProductBaseNoProductId(product);
        }
    }


    @Test
    public void test5(){
        for (int i = 0; i < 10; i++) {
            int j = productMapper.countByShopId();
        }
    }

}
