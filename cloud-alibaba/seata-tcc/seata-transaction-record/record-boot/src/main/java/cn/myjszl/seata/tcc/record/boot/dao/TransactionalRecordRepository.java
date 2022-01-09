package cn.myjszl.seata.tcc.record.boot.dao;

import cn.myjszl.seata.tcc.record.boot.model.TransactionalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionalRecordRepository extends JpaRepository<TransactionalRecord,Long> {
    TransactionalRecord findByXid(String xid);
}
