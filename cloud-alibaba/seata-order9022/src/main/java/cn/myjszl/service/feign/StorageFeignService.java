package cn.myjszl.service.feign;

import cn.myjszl.model.CommonResponse;
import cn.myjszl.model.Storage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存服务的openFeign接口
 */
@SuppressWarnings("rawtypes")
@FeignClient(value = "seata-storage")
public interface StorageFeignService {

    /**
     * 扣减库存
     * @param id
     * @param num
     * @return
     */
    @PostMapping("/storage/deduct")
    CommonResponse deduct(@RequestParam("id") Long id,@RequestParam("num") Long num);

    /**
     * 获取库存的详细信息
     * @param id
     * @return
     */
    @GetMapping("/storage/{id}")
    CommonResponse<Storage> getById(@PathVariable(value = "id") Long id);
}
