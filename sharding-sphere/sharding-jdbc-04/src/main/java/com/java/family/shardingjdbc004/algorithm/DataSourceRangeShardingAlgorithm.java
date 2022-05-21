package com.java.family.shardingjdbc004.algorithm;

import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 范围分片算法，`BETWEEN AND`、`>`、`<`、`>=`、`<=`
 *  针对数据源进行分片，按照店铺ID
 */
public class DataSourceRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        Range<Long> valueRange = shardingValue.getValueRange();
        //范围的高值
        Long upperValue = valueRange.hasUpperBound()?valueRange.upperEndpoint():null;

        //范围的低值
        Long lowerValue = valueRange.hasLowerBound()?valueRange.lowerEndpoint():null;

        //没有高值，则使用的符号是>/>=
        if (!valueRange.hasUpperBound()){
            return lowerValue>DataSourcePreciseShardingAlgorithm.BOUND_VALUE?Sets.newHashSet("ds1"):Sets.newHashSet("ds1","ds2");
        }

        //没有低值，使用的符号为</<=
        if (!valueRange.hasLowerBound()){
            return upperValue<=DataSourcePreciseShardingAlgorithm.BOUND_VALUE?Sets.newHashSet("ds2"):Sets.newHashSet("ds1","ds2");
        }

        //第三种情况：使用BETWEEN AND
        return lowerValue>DataSourcePreciseShardingAlgorithm.BOUND_VALUE?Sets.newHashSet("ds1"):(upperValue<DataSourcePreciseShardingAlgorithm.BOUND_VALUE?Sets.newHashSet("ds2"):Sets.newHashSet("ds1","ds2"));
    }
}
