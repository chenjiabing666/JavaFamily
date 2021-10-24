package cn.myjszl.handler;

import cn.myjszl.model.CommonResponse;
import cn.myjszl.model.Order;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("rawtypes")
@Slf4j
public class FallbackHandler {

    /**
     * 异常降级的兜底方法，参数签名必须和原业务方法一致，可以多个Throwable
     */
    public static CommonResponse fallbackHandler(Order order, Throwable ex){
        log.error("进入了指定降级兜底方法，业务处理出现了运行时异常：{}",ex.getMessage());
        return CommonResponse.<Order>builder()
                .code("1001")
                .message("创建订单失败，出现异常了.......")
                .build();
    }

    /**
     * 默认的异常降级处理方法，适用于所有服务，参数签名必须为空，可以多个Throwable
     */
    public static CommonResponse defaultFallbackHandler(Throwable ex){
        log.error("进入了默认异常降级兜底方法，业务处理出现了运行时异常：{}",ex.getMessage());
        return CommonResponse.<Order>builder()
                .code("1001")
                .message("创建订单失败，出现异常了.......")
                .build();
    }
}
