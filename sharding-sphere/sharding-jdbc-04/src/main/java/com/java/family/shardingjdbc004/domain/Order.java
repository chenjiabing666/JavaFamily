package com.java.family.shardingjdbc004.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long orderId;

    private Long userId;

    private Long price;

    private Integer status;
}
