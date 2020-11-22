package com.kalo.easpay.utils.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 19日 星期四 23:44:47
 */
@Slf4j
public class AESUtil {

    public static void main(String[] args) throws Exception {
        String key = getAesKey("18300665808");
        log.info("AesKey[key]:{}", key);
        String encrypt = encrypt("123456", key);
        log.info("AesKey[Encrypt]:{}", encrypt);
        String decrypt = decrypt(encrypt, key);
        log.info("AesKey[Decrypt]:{}", decrypt);
    }

    /**
     * @Title getAesKey
     * @Author Panguaxe
     * @param key
     * @return java.lang.String
     * @Time   2020/11/20 11:39
     * @Description      TODO  截取md5后的32位串 的10-26位字符串作为AESKey
     */
    public static String getAesKey(String key) {
        return StringUtils.isBlank(key) ? null : MD5Util.MD5(key).substring(10, 26);
    }
    /**
     * @Title encrypt
     * @Author Panguaxe
     * @param plaintext 加密内容
     * @param key       秘钥，必须为16位
     * @return java.lang.String
     * @Time   2020/11/20 11:45
     * @Description      TODO  AES加密
     */
    public static String encrypt(String plaintext, String key) {
        if (StringUtils.isBlank(plaintext) || StringUtils.isBlank(key) || 16 != key.length()) {
            return "";
        }
        //1、对字符串进行翻转
        StringBuilder sb = new StringBuilder(plaintext);
        String reverse = sb.reverse().toString();
        //2、字符串进行左位移
        int moveNum = getMoveIndex(reverse);//左位移个数
        reverse = leftMove(reverse, moveNum);
        try {
            //3、对加密的key进行处理
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, key);
            //4、字符串进行加密
            byte[] resultByte = cipher.doFinal(reverse.getBytes(StandardCharsets.UTF_8));
            return new BASE64Encoder().encode(resultByte);
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * @Title getMoveIndex
     * @Author Panguaxe
     * @param content   需要位移的字符串内容
     * @return int      字符串需要位移个数
     * @Time   2020/11/20 11:46
     * @Description      TODO  字符串需要位移个数
     */
    private static int getMoveIndex(String content) {
        return content.length() / 2;//左位移个数
    }
    /**
     * @Title decrypt
     * @Author Panguaxe
     * @param ciphertext    待解密的密文
     * @param key           秘钥，必须为16位
     * @return java.lang.String
     * @Time   2020/11/20 11:46
     * @Description      TODO  AES解密
     */
    public static String decrypt(String ciphertext, String key) {
        if (StringUtils.isBlank(ciphertext) || StringUtils.isBlank(key) || 16 != key.length()) {
            return "";
        }
        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE, key);
            //1、对字符串进行解密
            byte[] inputByte = (new BASE64Decoder()).decodeBuffer(ciphertext);
            byte[] resultByte = cipher.doFinal(inputByte);
            String tContent = new String(resultByte, StandardCharsets.UTF_8);
            //2、对字符串进行右位移（对加密内容进行左移n位（n值取决于字符串长度/2））
            int moveNum = getMoveIndex(tContent);//右位移个数
            tContent = rightMove(tContent, moveNum);
            //3、对字符串进行翻转
            StringBuilder sb = new StringBuilder(tContent);
            return sb.reverse().toString();
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * @Title getCipher
     * @Author Panguaxe
     * @param mode  加密方式
     * @param key   秘钥，必须为16位
     * @return javax.crypto.Cipher
     * @Time   2020/11/20 11:48
     * @Description      TODO  Cipher初始化：AES算法
     */
    private static Cipher getCipher(int mode, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        //偏移量同秘钥相同
        IvParameterSpec ivParameterSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
        //算法/模式/补码方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKeySpec, ivParameterSpec);
        return cipher;
    }
    /**
     * @Title leftMove
     * @Author Panguaxe
     * @param content   需要左位移字符串
     * @param index     位移个数
     * @return java.lang.String
     * @Time   2020/11/20 11:49
     * @Description      TODO  对字符串进行左位移
     */
    private static String leftMove(String content, int index) {
        String first = reChange(content.substring(0, index));
        String second = reChange(content.substring(index));
        content = first + second;
        return reChange(content);
    }
    /**
     * @Title rightMove
     * @Author Panguaxe
     * @param content   需要右位移字符串
     * @param index     位移个数
     * @return java.lang.String
     * @Time   2020/11/20 11:51
     * @Description      TODO  对字符串进行右位移
     */
    private static String rightMove(String content, int index) {
        content = reChange(content);
        String first = reChange(content.substring(0, index));
        String second = reChange(content.substring(index));
        return first + second;
    }
    /**
     * @Title reChange
     * @Author Panguaxe
     * @param content   需要处理的字符串
     * @return java.lang.String
     * @Time   2020/11/20 11:52
     * @Description      TODO  对字符串进行处理
     */
    private static String reChange(String content) {
        char[] contents = content.toCharArray();
        int length = contents.length;
        for (int i = 0; i < length / 2; i++) {
            char temp = contents[i];
            contents[i] = contents[length - 1 - i];
            contents[length - 1 - i] = temp;
        }
        return String.valueOf(contents);
    }

}
