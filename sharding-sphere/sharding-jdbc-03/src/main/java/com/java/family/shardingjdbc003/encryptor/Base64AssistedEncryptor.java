package com.java.family.shardingjdbc003.encryptor;

import cn.hutool.core.codec.Base64;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.shardingsphere.encrypt.strategy.spi.QueryAssistedEncryptor;

import java.util.Date;
import java.util.Properties;

/**
 * @author 不才陈某 公众号：码猿技术专栏
 * 自定义QueryAssistedEncryptor加密器
 */
@Data
public class Base64AssistedEncryptor implements QueryAssistedEncryptor {

    /**
     * 别名，配置时需要
     */
    public final static String ALGORITHM_NAME="Base64_Assisted";

    private Properties properties = new Properties();

    /**
     * 辅助查询列的加密方法
     */
    @Override
    public String queryAssistedEncrypt(String plaintext) {
        if (null == plaintext) {
            return null;
        }
        return Base64.encode(plaintext);
    }

    @Override
    public void init() {

    }

    /**
     * 加密方法
     * 使用时间戳作为变动因子
     */
    @Override
    public String encrypt(final Object plaintext) {
        if (null == plaintext) {
            return null;
        }
        //获取时间戳作为变动因子
        String randomFactor =String.valueOf( new Date().getTime());
        return Base64.encode(plaintext+"_"+randomFactor);
    }

    /**
     * 解密方法
     * Base64是一个可逆的加密算法，因此可以对密文进行解密并且剔除变动因子则为明文
     */
    @SneakyThrows
    @Override
    public Object decrypt(final String ciphertext) {
        if (null == ciphertext) {
            return null;
        }
        return new String(Base64.decode(ciphertext),"UTF-8").split("_")[0];
    }

    @Override
    public String getType() {
        return ALGORITHM_NAME;
    }
}
