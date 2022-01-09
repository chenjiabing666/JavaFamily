package cn.myjszl.seata.tcc.storage.api.feign;

import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.storage.api.model.Storage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tcc-storage",contextId = "storage")
public interface StorageFeign {

    @PostMapping("/storage/getById")
    Result<Storage> getById(@RequestParam(value = "productId") Long productId);

    /**
     * 冻结库存
     */
    @PostMapping("/storage/frozen")
    Result<Void> frozen(@RequestParam(name = "productId") Long productId, @RequestParam(name = "num") Long num );

    /**
     * 释放库存
     */
    @PostMapping("/storage/cleanFrozen")
    Result<Void> cleanFrozen(@RequestParam(name = "productId") Long productId, @RequestParam(name = "num") Long num);

    /**
     * 恢复库存
     */
    @PostMapping("/storage/rollbackFrozen")
    Result<Void> rollbackFrozen(@RequestParam(name = "productId") Long productId, @RequestParam(name = "num") Long num);
}
