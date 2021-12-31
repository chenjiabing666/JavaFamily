package cn.myjszl.oauth2.cloud.auth.server.dao;

import cn.myjszl.oauth2.cloud.auth.server.model.po.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository  extends JpaRepository<SysRole,Long> {
}
