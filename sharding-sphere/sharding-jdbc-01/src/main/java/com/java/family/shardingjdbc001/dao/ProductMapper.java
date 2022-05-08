package com.java.family.shardingjdbc001.dao;

import com.java.family.shardingjdbc001.model.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductMapper {
    @Insert(value = "INSERT INTO `product_base`(shop_id,name,price,origin_address) VALUE(#{shopId}, #{name}, #{price}, #{originAddress});")
    @Options(useGeneratedKeys=true, keyProperty="productId", keyColumn="product_id")
    int insertProductBase(Product product);

    @Insert(value = "INSERT INTO `product_description`(product_id,content,shop_id) VALUE(#{productId}, #{content},#{shopId});")
    int insertProductDescribe(@Param("productId")Long productId,@Param("content") String content,@Param("shopId")Long shopId );

    @Select(value = "SELECT\n" +
            "\tpb.*,\n" +
            "\tpd.content\n" +
            "FROM\n" +
            "\tproduct_base pb\n" +
            "\tLEFT JOIN product_description pd ON pd.product_id = pb.product_id")
    Product selectByProductId(Long productId);
}
