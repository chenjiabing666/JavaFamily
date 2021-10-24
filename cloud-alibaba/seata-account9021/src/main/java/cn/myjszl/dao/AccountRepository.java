package cn.myjszl.dao;

import cn.myjszl.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findAccountByUserId(String userId);
}
