package cn.myjszl.controller;

import cn.myjszl.model.Account;
import cn.myjszl.model.CommonResponse;
import cn.myjszl.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/getByUserId")
    public CommonResponse<Account> getByUserId(String userId){
        return CommonResponse.<Account>builder()
                .code("200")
                .message("请求成功")
                .data(accountService.getByUserId(userId))
                .build();
    }


    /**
     * 扣余额
     * @param userId
     * @param money
     * @return
     */
    @PostMapping("/deduct")
    public CommonResponse deduct(String userId,Long money) throws InterruptedException {
//        Thread.sleep(2000);

        //扣钱
        accountService.deduct(userId,money);

        return CommonResponse.builder()
                .code("200")
                .message("请求成功")
                .build();
    }

}
