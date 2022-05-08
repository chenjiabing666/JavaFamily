package com.java.family.shardingjdbc001;

import com.java.family.shardingjdbc001.dao.ProductMapper;
import com.java.family.shardingjdbc001.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShardingTest01 {

    @Autowired
    private ProductMapper productMapper;


    @Test
    public void test1(){
        Product product = Product.builder()
                .name("Spring Cloud Alibaba实战课程")
                .price(159L)
                .originAddress("码猿技术专栏")
                .shopId(1L)
                .build();
        productMapper.insertProductBase(product);
    }


    @Test
    public void test2(){
        Product product = productMapper.selectByProductId(730145430020554752L);
        System.out.println(product);
    }

    @Test
    public void test3(){
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .name("Spring Cloud Alibaba实战课程")
                    .price(159L)
                    .originAddress("码猿技术专栏")
                    .shopId(1L)
                    .build();
            productMapper.insertProductBase(product);
            productMapper.insertProductDescribe(product.getProductId(),"内容",product.getShopId());
        }
    }

    @Test
    public void test4(){
        for (int i = 0; i < 10; i++) {

            Product product = Product.builder()
                    .name("Spring Cloud Alibaba实战课程")
                    .price(159L)
                    .originAddress("码猿技术专栏")
                    .shopId((long)(new Random().nextInt(100)+1))
                    .build();
            productMapper.insertProductBase(product);
            productMapper.insertProductDescribe(product.getProductId(),"内容",product.getShopId());
        }
    }
}
