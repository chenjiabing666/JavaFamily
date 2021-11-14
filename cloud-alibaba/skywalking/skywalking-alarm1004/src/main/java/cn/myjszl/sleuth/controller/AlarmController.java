package cn.myjszl.sleuth.controller;

import cn.myjszl.sleuth.model.AlarmMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 不才陈某  微信公众号：码猿技术专栏
 */
@RestController
@RequestMapping("/alarm")
@Slf4j
public class AlarmController {

    /**
     * skywalking回调方法，一旦触发告警，则会调用这个接口
     * @param list 指定的参数，必须按照规则定义
     */
    @PostMapping("/receive")
    public void receive(@RequestBody List<AlarmMessage> list){
        //todo 仅仅打印JSON，至于发送邮件、微信、钉钉自己根据业务需求完成
        log.info("------------------------");
        log.info(JSON.toJSONString(list));
    }
}
