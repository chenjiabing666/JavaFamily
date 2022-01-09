package cn.myjszl.seata.tcc.record.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionalRecord {
    private Long id;

    private String xid;

    private Integer status;
}
