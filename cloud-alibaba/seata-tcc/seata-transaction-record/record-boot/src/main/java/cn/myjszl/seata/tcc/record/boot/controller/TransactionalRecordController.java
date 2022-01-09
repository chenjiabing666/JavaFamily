package cn.myjszl.seata.tcc.record.boot.controller;

import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.record.boot.model.TransactionalRecord;
import cn.myjszl.seata.tcc.record.boot.service.TransactionalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction/record/")
public class TransactionalRecordController {
    @Autowired
    private TransactionalRecordService service;

    @PostMapping("/add")
    Result<TransactionalRecord> add(TransactionalRecord record){
        return service.add(record);
    }

    @PostMapping("/getByXid")
    Result<TransactionalRecord> getByXid(String xid){
        return service.getByXid(xid);
    }
}
