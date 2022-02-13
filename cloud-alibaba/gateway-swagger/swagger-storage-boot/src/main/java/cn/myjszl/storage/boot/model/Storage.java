package cn.myjszl.storage.boot.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Storage {
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品价格")
    private Long price;

    @ApiModelProperty(value = "库存")
    private Long num;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "冻结库存")
    private Long frozen;

    @ApiModelProperty(value = "商品ID")
    private Long productId;
}
