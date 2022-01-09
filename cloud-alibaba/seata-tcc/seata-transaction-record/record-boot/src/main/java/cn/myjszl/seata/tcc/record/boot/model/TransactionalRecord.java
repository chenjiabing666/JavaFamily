package cn.myjszl.seata.tcc.record.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transactional_record")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long id;

    private String xid;

    private Integer status;
}
