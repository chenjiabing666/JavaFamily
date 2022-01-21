package cn.myjszl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;

    //总金额
    private Long useMoney;

    private String userId;

    private List<Product> products;
}
