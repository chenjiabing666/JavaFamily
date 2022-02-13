package cn.myjszl.order.boot.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Order {
    @ApiModelProperty(value = "订单ID")
    private Long id;

    @ApiModelProperty(value = "用户唯一ID")
    private String userId;

    @ApiModelProperty(value = "商品ID")
    private Long productId;

    @ApiModelProperty(value = "商品数量")
    private Long num;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ApiModelProperty(value = "下单时间")
    private Date createTime;
}
