package cn.myjszl.oauth2.cloud.auth.server.dao;

import cn.myjszl.oauth2.cloud.auth.server.model.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    SysUser findByUsernameAndStatus(String username,Integer status);
}
