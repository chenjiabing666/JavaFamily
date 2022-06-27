package cn.zifangsky.mqwebsocket.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.codec.digest.Sha2Crypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Random;

/**
 * 加密相关公共方法
 *
 * @author zifangsky
 * @date 2018/7/31
 * @since 1.0.0
 */
public class EncryptUtils {

    private static final String CHARS_1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String CHARS_2 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 获取长度为num的随机数（0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz）
     * @param num 生成的字符串长度
     * */
    public static String getRandomStr1(final int num) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            stringBuilder.append(CHARS_1.charAt(new Random().nextInt(CHARS_1.length())));
        }
        return stringBuilder.toString();
    }

    /**
     * 获取长度为num的随机数（./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz）
     * @param num 生成的字符串长度
     * */
    public static String getRandomStr2(final int num) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            stringBuilder.append(CHARS_2.charAt(new Random().nextInt(CHARS_2.length())));
        }
        return stringBuilder.toString();
    }

    /**
     * Base64 encode
     * */
    public static String base64Encode(final String data){
        return Base64.encodeBase64String(data.getBytes());
    }

    /**
     * Base64 decode
     * @throws UnsupportedEncodingException
     * */
    public static String base64Decode(final String data) throws UnsupportedEncodingException {
        return new String(Base64.decodeBase64(data.getBytes()),"utf-8");
    }

    /**
     * md5
     * */
    public static String md5Hex(final String data){
        return DigestUtils.md5Hex(data);
    }

    /**
     * sha1
     * */
    public static String sha1Hex(final String data){
        return DigestUtils.sha1Hex(data);
    }

    /**
     * sha256
     * */
    public static String sha256Hex(final String data){
        return DigestUtils.sha256Hex(data);
    }

    /**
     * sha512Hex
     * */
    public static String sha512Hex(final String data){
        return DigestUtils.sha512Hex(data);
    }

    /**
     * <p><b>基于哈希的消息验证代码（HMAC_MD5）</b></p>
     * <p>在发送方和接收方共享密钥的前提下，HMAC可用于确定通过不安全信道发送的消息是否已被篡改。</p>
     * <p>发送方计算原始数据的哈希值，并将原始数据和哈希值放在一个消息中同时传送。接收方重新计算所接收消息的哈希值，并检查计算所得的 HMAC 是否与传送的 HMAC 匹配。
     * 因为更改消息和重新生成正确的哈希值需要密钥，所以对数据或哈希值的任何更改都会导致不匹配。因此，如果原始的哈希值与计算得出的哈希值相匹配，则消息通过身份验证。</p>
     * @param key 秘钥
     * @param value 待加密的数据
     * @return java.lang.String
     */
    public static String hmacMd5Hex(final String key, final String value){
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmacHex(value);
    }

    /**
     * HMAC_SHA_1
     * */
    public static String hmacSha1(final String key, final String value){
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmacHex(value);
    }

    /**
     * HMAC_SHA_256
     * */
    public static String hmacSha256(final String key, final String value){
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(value);
    }

    /**
     * HMAC_SHA_512
     * */
    public static String hmacSha512(final String key, final String value){
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, key).hmacHex(value);
    }

    /**
     * Md5Crypt加密，使用示例：
     * <p>String encrypted1 = Md5Crypt.md5Crypt(key.getBytes());</p>
     * <p>String encrypted2 = Md5Crypt.md5Crypt(key.getBytes(),encrypted1);</p>
     * <p>System.out.println(encrypted1.equals(encrypted2)); //true</p>
     * @param value 原始数据
     * @param salt 盐，如果为空则会生成一个随机的8位字符串
     * @return java.lang.String
     * */
    public static String md5Crypt(final String value, final String salt){
        return Md5Crypt.md5Crypt(value.getBytes(),salt);
    }

    /**
     * 用于检验使用Md5Crypt.md5Crypt之后的数据是否正确（一般用于登录校验用户密码）
     * @param value 待校验的原始数据
     * @param encryptedStr 已有的加密之后的字符串
     * @return boolean 待校验的原始数据和加密字符串的原始数据是否相同
     */
    public static boolean checkMd5Crypt(final String value,final String encryptedStr){
        String newEncryptedStr = md5Crypt(value,encryptedStr);

        return newEncryptedStr.equals(encryptedStr);
    }

    /**
     * Sha2Crypt加密，类似于Md5Crypt
     * @param value 原始数据
     * @param salt 盐，如果为空则会生成一个随机的8位字符串
     * @return java.lang.String
     * */
    public static String sha256Crypt(final String value, final String salt){
        return Sha2Crypt.sha256Crypt(value.getBytes(),salt);
    }

    /**
     * 用于检验使用Sha2Crypt.sha256Crypt之后的数据是否正确（一般用于登录校验用户密码）
     * @param value 待校验的原始数据
     * @param encryptedStr 已有的加密之后的字符串
     * @return boolean 待校验的原始数据和加密字符串的原始数据是否相同
     */
    public static boolean checkSha256Crypt(final String value,final String encryptedStr){
        String newEncryptedStr = sha256Crypt(value,encryptedStr);

        return newEncryptedStr.equals(encryptedStr);
    }

    /**
     * Sha2Crypt加密，类似于Md5Crypt
     * @param value 原始数据
     * @param salt 盐，如果为空则会生成一个随机的8位字符串
     * @return java.lang.String
     * */
    public static String sha512Crypt(final String value, final String salt){
        return Sha2Crypt.sha512Crypt(value.getBytes(),salt);
    }

    /**
     * 用于检验使用Sha2Crypt.sha512Crypt之后的数据是否正确（一般用于登录校验用户密码）
     * @param value 待校验的原始数据
     * @param encryptedStr 已有的加密之后的字符串
     * @return boolean 待校验的原始数据和加密字符串的原始数据是否相同
     */
    public static boolean checkSha512Crypt(final String value,final String encryptedStr){
        String newEncryptedStr = sha512Crypt(value,encryptedStr);

        return newEncryptedStr.equals(encryptedStr);
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        String secret = "secret";
        String value = "a=1&b=2";

//        System.out.println("sha256Hex:" + sha256Hex(value)); //8e85be58c1c372ac29fe7bfa80d8ddcbd04a4032c7b51c1c026d67c55b1ab23f
//        System.out.println("sha512Hex:" + sha512Hex(value)); //ab6c1ec7849e98af8d50f0bdb6221e3063fea57f8184b98acbe0b21190201032608b8c7f140a7f10699d39c21b6026bb8609668c98b73dc4e723c8d67625a698
//        System.out.println();
//
//        System.out.println("hmacSha256:" + hmacSha256(secret,value)); //604fe97c66c6393ff22e3cae366eee1131e351ebc736bf12f5d62e1755b7a233
//        System.out.println("hmacSha512:" + hmacSha512(secret,value)); //785d7084675f5b7fa7222b1aed28705aa6868ca4b654418f05cbfdf24f6b815d92e5ac964ae579e72eedbe48ac144dd3b5e852787a00d5c0479ce7767a192d38
//        System.out.println();
//
//        System.out.println("sha256Crypt:" + sha256Crypt(value,null)); //$5$yDFqXV2l$KoNVx2GncH3mudhy0p7XFj76U1r/ZCIgZgRcNpLLaRD
//        System.out.println("sha512Crypt:" + sha512Crypt(value,null)); //$6$Z3Kab83Y$buAShiexlMZsTC1dnw.ogPE9ig/6fGo2gMlzO36u2haOKKXl1fA0RpH//3/tNoudSJv25nmbWHACcQZYdSUji1

//        System.out.println(sha256Crypt("admin",null));
//        System.out.println(sha256Crypt("123456",null));
//        System.out.println(hmacMd5Hex("8e85be58c1c372ac29fe7bfa","e3c"));

    }

}
