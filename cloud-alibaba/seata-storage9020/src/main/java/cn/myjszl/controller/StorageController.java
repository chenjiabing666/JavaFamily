package cn.myjszl.controller;

import cn.myjszl.model.CommonResponse;
import cn.myjszl.model.Storage;
import cn.myjszl.service.StorageService;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/{id}")
    public CommonResponse<Storage> getById(@PathVariable(value = "id") Long id){

        return CommonResponse.<Storage>builder()
                .code("200")
                .message("请求成功")
                .data(storageService.getById(id))
                .build();
    }

    /**
     * 扣减库存
     * @param id 商品ID
     * @param num 扣减的数量
     * @return
     */
    @PostMapping("/deduct")
    public CommonResponse deduct(Long id,Long num) {
        //执行扣减库存
        storageService.deduct(id,num);
        
        return CommonResponse.builder()
                .code("200")
                .message("请求成功")
                .build();
    }
}
