package cn.myjszl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel")
public class FlowLimitController {

    @GetMapping("/test")
    public String test() throws InterruptedException {
        Thread.sleep(3000);
        return "接收到一条消息--------";
    }


    /**
     * 下单接口
     * @return
     */
    @GetMapping("/order")
    public String order()  {
        return "下单成功..........";
    }

    /**
     * 支付接口
     * @return
     */
    @GetMapping("/pay")
    public String pay()  {
        return "支付成功..........";
    }
}
