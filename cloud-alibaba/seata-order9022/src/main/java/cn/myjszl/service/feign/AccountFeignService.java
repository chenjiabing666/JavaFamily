package cn.myjszl.service.feign;

import cn.myjszl.model.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 账户服务的openFeign接口
 */
@SuppressWarnings("rawtypes")
@FeignClient(value = "seata-account")
public interface AccountFeignService {
    /**
     * 扣减余额
     * @param userId
     * @param money
     * @return
     */
    @PostMapping("/account/deduct")
    CommonResponse deduct(@RequestParam(value = "userId") String userId, @RequestParam(value = "money") Long money);
}
