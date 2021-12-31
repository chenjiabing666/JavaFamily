package cn.myjszl.oauth2.cloud.auth.server.dao;

import cn.myjszl.oauth2.cloud.auth.server.model.po.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole,Long> {
    List<SysUserRole> findByUserId(Long userId);
}
