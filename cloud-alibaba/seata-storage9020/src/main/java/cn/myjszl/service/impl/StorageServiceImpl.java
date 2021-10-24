package cn.myjszl.service.impl;

import cn.myjszl.dao.StorageRepository;
import cn.myjszl.model.Storage;
import cn.myjszl.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageRepository repository;

    @Override
    public Storage getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * 扣减库存业务方法
     * 使用@Transactional开启一个本地事务，和正常流程并无差别
     * @param id 商品Id
     * @param num 扣减的数量
     */
    @Transactional
    @Override
    public void deduct(Long id, Long num) {
        //todo 模拟扣减库存，具体业务逻辑自己完善
        Storage storage = repository.findById(id).orElse(null);
        if (Objects.isNull(storage))
            throw new RuntimeException();

        storage.setNum(storage.getNum()-num);

        repository.save(storage);
    }
}
