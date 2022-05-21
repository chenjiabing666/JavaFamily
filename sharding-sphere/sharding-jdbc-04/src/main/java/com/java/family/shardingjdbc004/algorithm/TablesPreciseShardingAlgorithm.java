package com.java.family.shardingjdbc004.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * 精确分片策略，适用于通过分片键的精确查找：=,in
 * 泛型：指定分片键的类型
 */
public class TablesPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * @param availableTargetNames 可用的数据源或者表名称集合
     * @param shardingValue 分片键的值
     * @return 定位到的真实表的名称
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        //逻辑表名称
        String logicTableName = shardingValue.getLogicTableName();
        //分片键的值，这里则是 product_base
        Long value = shardingValue.getValue();
        //表的节点个数
        int size = availableTargetNames.size();
        //对于分片键取模，这里的算法还是product_base_$->{1..2}
        return MessageFormat.format(logicTableName+"{0}"+(value% size +1),"_");
    }
}
