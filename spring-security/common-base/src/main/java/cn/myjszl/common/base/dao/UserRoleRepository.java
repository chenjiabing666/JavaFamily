package cn.myjszl.common.base.dao;

import cn.myjszl.common.base.model.Role;
import cn.myjszl.common.base.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
