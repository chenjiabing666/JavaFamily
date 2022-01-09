package cn.myjszl.seata.tcc.storage.boot.controller;

import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.storage.boot.dao.StorageRepository;
import cn.myjszl.seata.tcc.storage.boot.model.Storage;
import cn.myjszl.seata.tcc.storage.boot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private StoreService storeService;

    @PostMapping("/getById")
    public Result<Storage> getById(Long productId){
        return Result.resultSuccess("200","请求成功",storageRepository.findByProductId(productId));

    }

    /**
     * 冻结库存
     */
    @PostMapping("/frozen")
    public Result<Void> frozen(Long productId, Long num ){
        return storeService.frozen(productId,num);
    }

    /**
     * 释放库存
     */
    @PostMapping("/cleanFrozen")
    public Result<Void> cleanFrozen(Long productId,Long num){
        return storeService.cleanFrozen(productId,num);
    }

    /**
     * 恢复库存
     */
    @PostMapping("/rollbackFrozen")
    public Result<Void> rollbackFrozen(Long productId,Long num){
        return storeService.rollbackFrozen(productId,num);
    }


}
