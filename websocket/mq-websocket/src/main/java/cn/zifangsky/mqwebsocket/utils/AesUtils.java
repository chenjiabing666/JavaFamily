package cn.zifangsky.mqwebsocket.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;

/**
 * AES加解密
 *
 * @author zifangsky
 * @date 2018/8/14
 * @since 1.0.0
 */
public class AesUtils {
    /**
     * AES加解密
     */
    private static final String ALGORITHM = "AES";
    /**
     * 默认的初始化向量值
     */
    private static final String IV_DEFAULT = "g8v20drvOmIx2PuR";
    /**
     * 默认加密的KEY
     */
    private static final String KEY_DEFAULT = "8G5M4Ff9hel8fUA9";
    /**
     * 工作模式：CBC
     */
    private static final String TRANSFORM_CBC_PKCS5 = "AES/CBC/PKCS5Padding";

    /**
     * 工作模式：ECB
     */
    private static final String TRANSFORM_ECB_PKCS5 = "AES/ECB/PKCS5Padding";

    /**
     * 基于CBC工作模式的AES加密
     * @author zifangsky
     * @date 2018/8/14 11:42
     * @since 1.0.0
     * @param value 待加密字符串
     * @param key 秘钥，如果不填则使用默认值
     * @param iv 初始化向量值，如果不填则使用默认值
     * @return java.lang.String
     */
    public static String encryptCbcMode(final String value, String key, String iv){
        if(StringUtils.isNoneBlank(value)){
            //如果key或iv为空，则使用默认值
            if(key == null || key.length() != 16){
                key = KEY_DEFAULT;
            }
            if(iv == null || iv.length() != 16){
                iv = IV_DEFAULT;
            }

            //密码
//            final SecretKeySpec keySpec = new SecretKeySpec(getUTF8Bytes(key),"AES");
            final SecretKeySpec keySpec = getSecretKey(key);

            //初始化向量器
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(getUTF8Bytes(iv));

            try {
                Cipher encipher = Cipher.getInstance(TRANSFORM_CBC_PKCS5);

                //加密模式
                encipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
                //使用AES加密
                byte[] encrypted = encipher.doFinal(getUTF8Bytes(value));
                //然后转成BASE64返回
                return Base64.encodeBase64String(encrypted);
            } catch (Exception e) {
                System.out.println(MessageFormat.format("基于CBC工作模式的AES加密失败,VALUE:{0},KEY:{1}",value,key));
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 基于CBC工作模式的AES解密
     * @author zifangsky
     * @date 2018/8/14 11:42
     * @since 1.0.0
     * @param encryptedStr AES加密之后的字符串
     * @param key 秘钥，如果不填则使用默认值
     * @param iv 初始化向量值，如果不填则使用默认值
     * @return java.lang.String
     */
    public static String decryptCbcMode(final String encryptedStr, String key, String iv){
        if(StringUtils.isNoneBlank(encryptedStr)){
            //如果key或iv为空，则使用默认值
            if(key == null || key.length() != 16){
                key = KEY_DEFAULT;
            }
            if(iv == null || iv.length() != 16){
                iv = IV_DEFAULT;
            }

            //密码
//            final SecretKeySpec keySpec = new SecretKeySpec(getUTF8Bytes(key),"AES");
            final SecretKeySpec keySpec = getSecretKey(key);
//            初始化向量器
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(getUTF8Bytes(iv));

            try {
                Cipher encipher = Cipher.getInstance(TRANSFORM_CBC_PKCS5);

                //加密模式
                encipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
                //先用BASE64解密
                byte[] encryptedBytes = Base64.decodeBase64(encryptedStr);
                //然后再AES解密
                byte[] originalBytes = encipher.doFinal(encryptedBytes);
                //返回字符串
                return new String(originalBytes);
            } catch (Exception e) {
                System.out.println(MessageFormat.format("基于CBC工作模式的AES解密失败,encryptedStr:{0},KEY:{1}",encryptedStr,key));
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * 基于ECB工作模式的AES加密
     * @author zifangsky
     * @date 2018/8/14 11:42
     * @since 1.0.0
     * @param value 待加密字符串
     * @param key 秘钥，如果不填则使用默认值
     * @return java.lang.String
     */
    public static String encryptEcbMode(final String value, String key){
        if(StringUtils.isNoneBlank(value)){
            //如果key为空，则使用默认值
            if(key == null || key.length() != 16){
                key = KEY_DEFAULT;
            }

            //密码
            final SecretKeySpec keySpec = getSecretKey(key);

            try {
                Cipher encipher = Cipher.getInstance(TRANSFORM_ECB_PKCS5);

                //加密模式
                encipher.init(Cipher.ENCRYPT_MODE, keySpec);
                //使用AES加密
                byte[] encrypted = encipher.doFinal(getUTF8Bytes(value));
                //然后转成BASE64返回
                return Base64.encodeBase64String(encrypted);
            } catch (Exception e) {
                System.out.println(MessageFormat.format("基于ECB工作模式的AES加密失败,VALUE:{0},KEY:{1}",value,key));
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 基于ECB工作模式的AES解密
     * @author zifangsky
     * @date 2018/8/14 11:42
     * @since 1.0.0
     * @param encryptedStr AES加密之后的字符串
     * @param key 秘钥，如果不填则使用默认值
     * @return java.lang.String
     */
    public static String decryptEcbMode(final String encryptedStr, String key){
        if(StringUtils.isNoneBlank(encryptedStr)){
            //如果key为空，则使用默认值
            if(key == null || key.length() != 16){
                key = KEY_DEFAULT;
            }

            //密码
            final SecretKeySpec keySpec = getSecretKey(key);

            try {
                Cipher encipher = Cipher.getInstance(TRANSFORM_ECB_PKCS5);

                //加密模式
                encipher.init(Cipher.DECRYPT_MODE, keySpec);
                //先用BASE64解密
                byte[] encryptedBytes = Base64.decodeBase64(encryptedStr);
                //然后再AES解密
                byte[] originalBytes = encipher.doFinal(encryptedBytes);
                //返回字符串
                return new String(originalBytes);
            } catch (Exception e) {
                System.out.println(MessageFormat.format("基于ECB工作模式的AES解密失败,encryptedStr:{0},KEY:{1}",encryptedStr,key));
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 将字符串转化为UTF8格式的byte数组
     *
     * @param input the input string
     * @return UTF8 bytes
     */
    private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 生成加密秘钥
     * @param KEY 明文秘钥
     * @return SecretKeySpec
     */
    private static SecretKeySpec getSecretKey(final String KEY) {
        //生成指定算法密钥
        KeyGenerator generator = null;

        try {
            generator = KeyGenerator.getInstance(ALGORITHM);

            //指定AES密钥长度（128、192、256）
            generator.init(256, new SecureRandom(getUTF8Bytes(KEY)));

            //生成一个密钥
            SecretKey secretKey = generator.generateKey();
            //转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(MessageFormat.format("生成加密秘钥失败,KEY:{0}",KEY));
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String encryptedStr = AesUtils.encryptCbcMode("hello world!", "4Ct7TyXZa19rgQKK", null);
        System.out.println(encryptedStr);

        String originalStr = AesUtils.decryptCbcMode(encryptedStr, "4Ct7TyXZa19rgQKK", null);
        System.out.println(originalStr);

        String encryptedStr2 = AesUtils.encryptEcbMode("{\"username\":\"Tom\",\"password\":\"123456\",\"mobile\":\"12306\",\"email\":\"admin@zifangsky.cn\"}", "4Ct7TyXZa19rgQKK");
        String originalStr2 = AesUtils.decryptEcbMode(encryptedStr2, "4Ct7TyXZa19rgQKK");
        System.out.println(originalStr2);

    }

}
