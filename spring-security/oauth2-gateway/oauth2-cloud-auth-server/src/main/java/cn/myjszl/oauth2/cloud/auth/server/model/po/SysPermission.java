package cn.myjszl.oauth2.cloud.auth.server.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限实体类
 */
@Data
@Entity
@Table(name = "sys_permission")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long id;

    private String name;

    private String url;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
