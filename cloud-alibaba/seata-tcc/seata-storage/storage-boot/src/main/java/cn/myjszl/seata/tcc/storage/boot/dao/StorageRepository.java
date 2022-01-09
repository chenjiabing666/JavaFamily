package cn.myjszl.seata.tcc.storage.boot.dao;

import cn.myjszl.seata.tcc.storage.boot.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage,Long> {
    Storage findByProductId(Long productId);

}
