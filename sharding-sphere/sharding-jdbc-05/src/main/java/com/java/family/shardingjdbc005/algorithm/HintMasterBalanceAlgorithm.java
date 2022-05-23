package com.java.family.shardingjdbc005.algorithm;

import lombok.Data;
import org.apache.shardingsphere.spi.masterslave.MasterSlaveLoadBalanceAlgorithm;

import java.util.List;
import java.util.Properties;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 从节点的负载均衡算法-强制查主库
 */
@Data
public class HintMasterBalanceAlgorithm implements MasterSlaveLoadBalanceAlgorithm {
    private Properties properties = new Properties();

    public static final String ALGORITHM_TYPE="HINT-MASTER";

    /**
     * 获取数据源名称
     * @param name 逻辑数据源，比如配置中定义的m1、m2
     * @param masterDataSourceName 主数据源
     * @param slaveDataSourceNames 从节点集合
     */
    @Override
    public String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames) {
        return masterDataSourceName;
    }

    @Override
    public String getType() {
        return ALGORITHM_TYPE;
    }
}
