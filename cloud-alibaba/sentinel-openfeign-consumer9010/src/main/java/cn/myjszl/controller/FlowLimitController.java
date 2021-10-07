package cn.myjszl.controller;

import cn.myjszl.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel/consumer")
public class FlowLimitController {

    @Autowired
    private FlowService flowService;

    /**
     * 支付接口
     * @return
     */
    @GetMapping("/pay")
    public String pay()  {
        return flowService.pay();
    }

    @GetMapping("/testA")
    public String testA()  {
        return flowService.pay();
    }

    @GetMapping("/testB")
    public String testB()  {
        return flowService.pay();
    }
}
