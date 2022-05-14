package com.java.family.shardingjdbc002.dao;

import com.java.family.shardingjdbc002.model.Product;
import com.java.family.shardingjdbc002.model.ProductTypeVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductMapper {
    @Insert(value = "INSERT INTO `product_base`(shop_id,name,price,origin_address,type_id) VALUE(#{shopId}, #{name}, #{price}, #{originAddress},#{typeId});")
    @Options(useGeneratedKeys=true, keyProperty="productId", keyColumn="product_id")
    int insertProductBase(Product product);

    @Insert(value = "INSERT INTO `product_description`(product_id,content,shop_id) VALUE(#{productId}, #{content},#{shopId});")
    int insertProductDescribe(@Param("productId")Long productId,@Param("content") String content,@Param("shopId")Long shopId );

    @Select("select * from product_base where product_id=#{productId}")
    Product selectByProductId(Long productId);

    @Select(value = "SELECT\n" +
            "\tpb.* \n" +
            "FROM\n" +
            "\tproduct_base pb\n" +
            "\tJOIN product_description pd \n" +
            "WHERE\n" +
            "\tpb.product_id = pd.product_id")
    List<Product> listByJoin();

    @Select("SELECT\n" +
            "\tpb.*,\n" +
            "\tpt.type_name \n" +
            "FROM\n" +
            "\tproduct_base pb\n" +
            "\tJOIN product_type pt ON pb.type_id = pt.type_id")
    List<ProductTypeVo> listType();
}
