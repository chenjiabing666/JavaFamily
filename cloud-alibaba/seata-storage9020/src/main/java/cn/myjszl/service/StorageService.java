package cn.myjszl.service;


import cn.myjszl.model.Storage;

public interface StorageService {
    Storage getById(Long id);

    /**
     * 扣减库存
     * @param id 商品Id
     * @param num 扣减的数量
     */
    void deduct(Long id,Long num);
}
