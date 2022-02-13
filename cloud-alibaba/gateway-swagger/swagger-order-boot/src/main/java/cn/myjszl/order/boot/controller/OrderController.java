package cn.myjszl.order.boot.controller;

import cn.myjszl.order.boot.model.Order;
import cn.myjszl.order.boot.model.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "订单接口")
@RequestMapping("/order")
@RestController
public class OrderController {

    @ApiOperation("获取订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", defaultValue = "1", value = "订单号", paramType = "path", dataType = "String"),
    })
    @GetMapping("/{id}")
    public ResultMsg<Order> get(@PathVariable(value = "id") Long id){
        return new ResultMsg<>();
    }
}
