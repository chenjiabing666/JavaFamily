package cn.myjszl.seata.tcc.storage.boot.service;

import cn.myjszl.seata.tcc.core.model.Result;

public interface StoreService {
    Result<Void> frozen(Long productId, Long num );
    Result<Void> cleanFrozen(Long productId,Long num);
    Result<Void> rollbackFrozen(Long productId,Long num);
}
