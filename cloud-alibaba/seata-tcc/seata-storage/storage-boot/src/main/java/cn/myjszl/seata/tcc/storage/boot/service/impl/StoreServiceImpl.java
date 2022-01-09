package cn.myjszl.seata.tcc.storage.boot.service.impl;

import cn.myjszl.seata.tcc.core.model.Result;
import cn.myjszl.seata.tcc.storage.boot.dao.StorageRepository;
import cn.myjszl.seata.tcc.storage.boot.model.Storage;
import cn.myjszl.seata.tcc.storage.boot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StorageRepository storageRepository;

    @Transactional
    @Override
    public Result<Void> frozen(Long productId, Long num) {
        Storage storage = storageRepository.findByProductId(productId);
        if (Objects.isNull(storage))
            return Result.resultFail("1001","该商品无库存信息",null);
        if (storage.getNum()<num)
            return Result.resultFail("1002","该商品库存不足",null);
        storage.setNum(storage.getNum()-num);
        storage.setFrozen(num);
        storageRepository.save(storage);
        return Result.resultSuccess("200","请求成功",null);
    }

    @Override
    public Result<Void> cleanFrozen(Long productId, Long num) {
        Storage storage = storageRepository.findByProductId(productId);
        if (Objects.isNull(storage))
            return Result.resultFail("1001","该商品无库存信息",null);
        //释放库存
        storage.setFrozen(storage.getFrozen()-num);
        storageRepository.save(storage);
        return Result.resultSuccess("200","请求成功",null);
    }

    @Override
    public Result<Void> rollbackFrozen(Long productId, Long num) {
        Storage storage = storageRepository.findByProductId(productId);
        if (Objects.isNull(storage))
            return Result.resultFail("1001","该商品无库存信息",null);
        storage.setNum(storage.getNum()+num);
        storage.setFrozen(storage.getFrozen()-num);
        storageRepository.save(storage);
        return Result.resultSuccess("200","请求成功",null);
    }
}
