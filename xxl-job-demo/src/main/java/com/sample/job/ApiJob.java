package com.sample.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenan
 * date 2020-06-01
 */
@Slf4j
public class ApiJob extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        XxlJobLogger.log("XXL-JOB-API, Hello World.");

        log.info("my job api run, param: {}",param);

        return ReturnT.SUCCESS;
    }
}
