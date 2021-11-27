package cn.myjszl.common.base.dao;

import cn.myjszl.common.base.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    List<UserRole> findByUserIdAndStatus(String userId,Integer status);
}
