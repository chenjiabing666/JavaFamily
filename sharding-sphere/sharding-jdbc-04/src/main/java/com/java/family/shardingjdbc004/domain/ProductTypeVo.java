package com.java.family.shardingjdbc004.domain;

import lombok.Data;

@Data
public class ProductTypeVo {
    private Long productId;
    private String name;
    private Long price;
    private Long shopId;
    private String originAddress;
    private String content;
    private String typeName;
}
