package com.java.family.shardingjdbc004.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 精确范围分片算法，=/in
 */
public class DataSourcePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    public final static long BOUND_VALUE=50;
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long value = shardingValue.getValue();
        return value>DataSourcePreciseShardingAlgorithm.BOUND_VALUE?"ds1":"ds2";
    }
}
