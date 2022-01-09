package cn.myjszl.seata.tcc.record.api.feign;

import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.record.api.model.TransactionalRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tcc-record",contextId = "transactional-record")
public interface TransactionalRecordFeign {

    @PostMapping("/transaction/record/add")
    Result<TransactionalRecord> add(@SpringQueryMap TransactionalRecord record);

    @PostMapping("/transaction/record/getByXid")
    Result<TransactionalRecord> getByXid(@RequestParam(value = "xid") String xid);
}
