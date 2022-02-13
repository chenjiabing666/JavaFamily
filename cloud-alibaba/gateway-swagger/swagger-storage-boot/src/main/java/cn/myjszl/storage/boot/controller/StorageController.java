package cn.myjszl.storage.boot.controller;

import cn.myjszl.storage.boot.model.ResultMsg;
import cn.myjszl.storage.boot.model.Storage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "库存接口")
@RequestMapping("/storage")
@RestController
public class StorageController {

    @ApiOperation("获取商品库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", defaultValue = "1", value = "商品ID", paramType = "path", dataType = "String"),
    })
    @GetMapping("/{id}")
    public ResultMsg<Storage> get(@PathVariable(value = "id") Long id){
        return new ResultMsg<>();
    }
}
