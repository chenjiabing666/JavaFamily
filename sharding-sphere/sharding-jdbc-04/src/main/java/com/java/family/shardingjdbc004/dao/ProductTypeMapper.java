package com.java.family.shardingjdbc004.dao;

import com.java.family.shardingjdbc004.domain.ProductType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ProductTypeMapper {
    @Insert(value = "INSERT INTO `product_type`(type_id,type_name) VALUE(#{typeId}, #{typeName});")
    int insert(ProductType productType);
}
