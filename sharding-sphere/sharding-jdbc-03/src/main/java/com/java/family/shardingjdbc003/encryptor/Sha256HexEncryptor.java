package com.java.family.shardingjdbc003.encryptor;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shardingsphere.encrypt.strategy.spi.Encryptor;

import java.util.Properties;

/**
 * @author 不才陈某 公众号：码猿技术专栏
 * 自定义的加密解密算法，基于sha256
 */
@Data
public class Sha256HexEncryptor implements Encryptor {

    /**
     * 别名，配置时需要
     */
    public final static String ALGORITHM_NAME="SHA256";

    private Properties properties = new Properties();

    @Override
    public void init() {

    }

    /**
     * 加密
     * INSERT, DELETE, UPDATE时会调用该方法进行加密存储到数据库中
     * @param plaintext 明文
     * @return  加密后的密文
     */
    @Override
    public String encrypt(final Object plaintext) {
        if (null == plaintext) {
            return null;
        }
        return DigestUtils.sha256Hex(String.valueOf(plaintext));
    }

    /**
     * 解密
     * 在SELECT 查询会调用该方法进行解密
     * @param ciphertext 密文
     * @return 由于sha256是一种不可逆的算法，因此直接返回密文
     */
    @Override
    public String decrypt(final String ciphertext) {
        return ciphertext;
    }

    /**
     * 别名，在配置中指定的名称
     */
    @Override
    public String getType() {
        return Sha256HexEncryptor.ALGORITHM_NAME;
    }
}
