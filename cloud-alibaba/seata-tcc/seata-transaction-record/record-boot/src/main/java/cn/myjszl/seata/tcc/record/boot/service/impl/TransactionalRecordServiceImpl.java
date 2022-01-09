package cn.myjszl.seata.tcc.record.boot.service.impl;

import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.record.boot.dao.TransactionalRecordRepository;
import cn.myjszl.seata.tcc.record.boot.model.TransactionalRecord;
import cn.myjszl.seata.tcc.record.boot.service.TransactionalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TransactionalRecordServiceImpl implements TransactionalRecordService {

    @Autowired
    private TransactionalRecordRepository recordRepository;

    @Transactional
    @Override
    public Result<TransactionalRecord> add(TransactionalRecord record) {
        //保证幂等性
        TransactionalRecord res = recordRepository.findByXid(record.getXid());
        if (Objects.nonNull(res))
            return Result.resultSuccess("200","请求成功",res);
        return Result.resultSuccess("200","请求成功",recordRepository.save(record));
    }

    @Override
    public Result<TransactionalRecord> getByXid(String xid) {
        return Result.resultSuccess("200","请求成功",recordRepository.findByXid(xid));
    }
}
