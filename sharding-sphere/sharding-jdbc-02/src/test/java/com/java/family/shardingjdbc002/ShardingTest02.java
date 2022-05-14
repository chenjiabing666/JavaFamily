package com.java.family.shardingjdbc002;

import com.java.family.shardingjdbc002.dao.ProductMapper;
import com.java.family.shardingjdbc002.dao.ProductTypeMapper;
import com.java.family.shardingjdbc002.model.Product;
import com.java.family.shardingjdbc002.model.ProductType;
import com.java.family.shardingjdbc002.model.ProductTypeVo;
import jdk.nashorn.internal.runtime.JSONFunctions;
import lombok.extern.slf4j.Slf4j;
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
public class ShardingTest02 {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Test
    public void test1(){
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .name("Spring Cloud Alibaba实战课程")
                    .price(159L)
                    .originAddress("码猿技术专栏")
                    .shopId((long)(new Random().nextInt(100)+1))
                    .typeId(1L)
                    .build();
            productMapper.insertProductBase(product);
            productMapper.insertProductDescribe(product.getProductId(),"内容",product.getShopId());
        }
    }


    /**
     * 测试JOIN关联查询
     */
    @Test
    public void test2(){
        List<Product> products = productMapper.listByJoin();
        System.out.println(products);
    }

    /**
     * 插入商品类型
     */
    @Test
    public void test3(){
        ProductType productType = new ProductType();
        productType.setTypeId(1L);
        productType.setTypeName("母婴");
        productTypeMapper.insert(productType);
    }


    @Test
    public void test4(){
        List<ProductTypeVo> productTypeVos = productMapper.listType();
        System.out.println(productTypeVos);
    }
}
