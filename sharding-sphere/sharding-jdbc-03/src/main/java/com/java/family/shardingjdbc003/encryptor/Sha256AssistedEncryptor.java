package com.java.family.shardingjdbc003.encryptor;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shardingsphere.encrypt.strategy.spi.QueryAssistedEncryptor;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * 自定义的QueryAssistedEncryptor类型的加密器
 */
@Data
public class Sha256AssistedEncryptor implements QueryAssistedEncryptor {

    /**
     * 别名，配置时需要
     */
    public final static String ALGORITHM_NAME="SHA256_Assisted";

    private Properties properties = new Properties();

    @Override
    public String queryAssistedEncrypt(String plaintext) {
        if (null == plaintext) {
            return null;
        }
        // 原始字符串
        return DigestUtils.sha256Hex(plaintext);
    }

    @Override
    public void init() {

    }

    @Override
    public String encrypt(Object plaintext) {
        if (null == plaintext) {
            return null;
        }
        //获取时间戳
        String randomFactor = LocalDateTime.now().toString();
        return DigestUtils.sha256Hex(randomFactor+plaintext);
    }

    @Override
    public Object decrypt(String ciphertext) {
        return ciphertext;
    }

    @Override
    public String getType() {
        return ALGORITHM_NAME;
    }
}
