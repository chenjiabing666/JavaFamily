package cn.myjszl.seata.tcc.record.boot.service;

import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.record.boot.model.TransactionalRecord;

public interface TransactionalRecordService {
    Result<TransactionalRecord> add(TransactionalRecord record);

    Result<TransactionalRecord> getByXid(String xid);

}
