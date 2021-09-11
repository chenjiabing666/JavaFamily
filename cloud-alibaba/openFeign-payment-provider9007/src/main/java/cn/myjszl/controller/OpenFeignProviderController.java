package cn.myjszl.controller;

import cn.myjszl.model.Payment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openfeign/payment")
public class OpenFeignProviderController {

    @PostMapping("/pay")
    public String pay(@RequestBody Payment payment) throws InterruptedException {
        Thread.sleep(3000);
        return payment.toString();
    }

}
