package cn.myjszl.seata.tcc.storage.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "storage")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long id;

    @Column
    private String name;

    @Column
    private Long price;

    @Column
    private Long num;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "product_id")
    private Long productId;

    private Long frozen;

}
