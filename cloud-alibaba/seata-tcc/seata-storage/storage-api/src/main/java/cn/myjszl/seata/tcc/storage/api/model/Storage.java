package cn.myjszl.seata.tcc.storage.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    private Long id;

    private String name;

    private Long price;

    private Long num;

    private Date createTime;

    private Long productId;

    private Long frozen;

}
