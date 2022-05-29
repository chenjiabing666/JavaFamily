package com.sample.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chenan
 * date 2020-06-01
 */
@Component
@Slf4j
public class AnnotationJob {




    @Value("${server.port}")
    private int port;


    /**
     * 1、注解任务
     */
    @XxlJob(value = "myJobAnnotationHandler",init = "init", destroy = "destroy")
    public ReturnT<String> myJobAnnotationHandler(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB-ANNOTATION, myJobAnnotationHandler.");

        log.info("my first annotation job run, param: {},port:{}",param,port);

        return ReturnT.SUCCESS;
    }

    public void init(){
        log.info("my annotation job  init");
    }

    public void destroy(){
        log.info("my job annotation job destory");
    }


    /**
     * 2、分片任务
     */
    @XxlJob("myShardJobAnnotationHandler")
    public ReturnT<String> myShardJobAnnotationHandler(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB-ANNOTATION, myShardJobAnnotationHandler.");

        log.info("my shard job run, param: {}",param);
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        log.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());

        // 业务逻辑
        for (int i = 0; i < shardingVO.getTotal(); i++) {
            if (i == shardingVO.getIndex()) {
                log.info("第 {} 片, 命中分片开始处理", i);
            } else {
                log.info("第 {} 片, 忽略", i);
            }
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 3、子任务
     */
    @XxlJob("myChildJobAnnotationHandler")
    public ReturnT<String> myChildJobAnnotationHandler(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB-ANNOTATION, myChildJobAnnotationHandler.");

        log.info("my child job run, param: {}",param);

        return ReturnT.SUCCESS;
    }
}
