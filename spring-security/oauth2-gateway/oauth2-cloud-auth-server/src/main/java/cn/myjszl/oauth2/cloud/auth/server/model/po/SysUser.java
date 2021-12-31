package cn.myjszl.oauth2.cloud.auth.server.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
@Entity
@Table(name = "sys_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long id;

    private String userId;

    private String username;

    private String nickname;

    private Integer gender;

    private String avatar;

    private String password;

    private String mobile;

    private String email;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
