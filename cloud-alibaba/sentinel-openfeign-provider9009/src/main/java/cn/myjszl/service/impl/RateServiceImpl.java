package cn.myjszl.service.impl;

import cn.myjszl.handler.CommonHandler;
import cn.myjszl.handler.FallbackHandler;
import cn.myjszl.model.CommonResponse;
import cn.myjszl.model.Order;
import cn.myjszl.service.RateService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@SuppressWarnings("rawtypes")
@Service
@Slf4j
public class RateServiceImpl implements RateService {

    /**
     * 使用@SentinelResource定义一个资源，限流兜底方法为handler
     * @param id
     * @return
     */
    @Override
    @SentinelResource(value = "QueryOrder",blockHandlerClass = CommonHandler.class,blockHandler = "handler")
    public CommonResponse<Order> queryOrder(Long id) {
        return CommonResponse.<Order>builder()
                .code("200")
                .message("请求成功")
                .data(Order.builder()
                        .id(id)
                        .money(200)
                        .name("码猿技术专栏")
                        .num("100")
                        .build())
                .build();
    }

    /**
     * 这个QueryOrder资源的兜底方法
     */
//    public CommonResponse<Order> handler(Long id, BlockException exception) {
//        log.error("哎，又被限流了，异常消息：{}", JSON.toJSONString(exception));
//        return CommonResponse.<Order>builder()
//                .code("1001")
//                .message("限流异常了o(╥﹏╥)o")
//                .build();
//    }


    /**
     * 创建订单，fallbackClass指定异常降级兜底的类，fallback指定兜底方法
     */
    @Override
    @SentinelResource(value = "createOrder",
            blockHandlerClass = CommonHandler.class,blockHandler = "handlerOrder",
            fallbackClass = FallbackHandler.class,fallback = "fallbackHandler")
    public CommonResponse createOrder(Order order) {
        log.debug("订单创建成功：{}", JSON.toJSONString(order));
        System.out.println(1/0);
        return CommonResponse.builder()
                .code("200")
                .message("订单创建成功")
                .build();
    }

    /**
     * 创建订单，fallbackClass指定异常降级兜底的类，defaultFallback指定默认的兜底方法
     */
    @Override
    @SentinelResource(value = "createOrder2",fallbackClass = FallbackHandler.class,defaultFallback = "defaultFallbackHandler")
    public CommonResponse createOrder2(Order order) {
        log.debug("订单创建成功：{}", JSON.toJSONString(order));
        System.out.println(1/0);
        return CommonResponse.builder()
                .code("200")
                .message("订单创建成功")
                .build();
    }
}
