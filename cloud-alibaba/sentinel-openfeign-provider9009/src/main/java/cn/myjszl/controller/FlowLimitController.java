package cn.myjszl.controller;

import cn.myjszl.service.FlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel/provider")
@Slf4j
public class FlowLimitController {

    @Autowired
    private FlowService flowService;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        Thread.sleep(3000);
        log.info("收到一条消息----test");
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

    @GetMapping("/order/query")
    public String query(@RequestParam(value = "p1",required = false) String p1, @RequestParam(value = "p2",required = false)String p2){
        return flowService.query(p1,p2);
    }


}
