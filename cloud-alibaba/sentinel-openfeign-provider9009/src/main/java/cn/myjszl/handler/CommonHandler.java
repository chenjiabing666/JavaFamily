package cn.myjszl.handler;

import cn.myjszl.model.CommonResponse;
import cn.myjszl.model.Order;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * 公共的兜底类
 */
@SuppressWarnings("rawtypes")
@Slf4j
public class CommonHandler {

    /**
     * 兜底方法，必须是public，必须是static方法，返回类型必须和原业务方法相同
     */
    public static CommonResponse<Order> handler(Long id, BlockException exception){
        log.error("哎，又被限流了，异常消息：{}", JSON.toJSONString(exception));
        return CommonResponse.<Order>builder()
                .code("1001")
                .message("限流异常了o(╥﹏╥)o")
                .build();
    }

    /**
     * 创建订单的限流兜底方法，一旦触发限流，这个兜底方法将会被调用
     */
    public static CommonResponse handlerOrder(Order order,BlockException exception){
        log.error("哎，又被限流了，异常消息：{}", JSON.toJSONString(exception));
        return CommonResponse.builder()
                .code("1001")
                .message("限流异常了o(╥﹏╥)o")
                .build();
    }
}
