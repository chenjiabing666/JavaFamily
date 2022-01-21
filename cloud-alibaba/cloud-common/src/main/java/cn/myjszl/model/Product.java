package cn.myjszl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;

    private String name;

    private Long price;

    //库存
    private Long total;
}
