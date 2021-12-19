package cn.myjszl.oauth2.cloud.auth.common.model.order;

import cn.myjszl.oauth2.cloud.auth.common.model.product.Product;
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

    private List<Product> products;

    private Long useMoney;
}
