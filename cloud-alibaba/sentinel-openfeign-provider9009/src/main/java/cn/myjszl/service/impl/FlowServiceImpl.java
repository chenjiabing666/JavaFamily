package cn.myjszl.service.impl;

import cn.myjszl.service.FlowService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Slf4j
public class FlowServiceImpl implements FlowService {

    /**
     * @SentinelResource的value属性指定了资源名，一定要唯一
     * blockHandler属性指定了兜底方法
     */
    @Override
    @SentinelResource(value = "OrderQuery",blockHandler = "handlerQuery")
    public String query(String p1, String p2) {
        log.info("查询商品，p1：{}，p2：{}",p1,p2);
        return "查询商品：success";
    }

    /**
     * 对应得兜底方法，一旦被限流将会调用这个方法来处理
     */
    public String handlerQuery(@RequestParam(value = "p1",required = false) String p1,
                               @RequestParam(value = "p2",required = false)String p2,
                               BlockException exception){
        log.info("查询商品，p1：{}，p2：{}",p1,p2);
        return "查询商品：熔断了......";
    }
}
