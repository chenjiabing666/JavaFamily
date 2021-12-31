package cn.myjszl.oauth2.cloud.auth.server.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色实体类
 */
@Data
@Entity
@Table(name = "sys_role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long id;

    private String name;

    private String code;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
