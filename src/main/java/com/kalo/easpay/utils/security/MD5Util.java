package com.kalo.easpay.utils.security;

import com.kalo.easpay.common.constant.Chars;
import com.kalo.easpay.common.enums.AlgorithmEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 19日 星期四 23:44:47
 */
@Slf4j
public class MD5Util {

    public static void main(String[] args) {
        String pwd = "123456";
        log.info("密码MD5加密加盐密文：{}", encryptSalt(pwd));
    }

    /**
     * @param plaintextPwd 明文
     * @return java.lang.String
     * @Title encryptSalt
     * @Author Panguaxe
     * @Time 2020/11/20 11:21
     * @Description TODO  MD5加密加盐
     */
    public static String encryptSalt(String plaintextPwd) {
        byte[] bytes = plaintextPwd.getBytes(StandardCharsets.UTF_8);
        //加盐
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i] + i);
        }
        bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest digest = MessageDigest.getInstance(AlgorithmEnum.MD5.getAlgorithm());
            bytes = digest.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5加密加盐[No Such Algorithm Exception]异常：{}", e.getMessage());
            return "";
        }
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hexStr = byteToHexString(bytes[i]);
            ret.append(padRight(hexStr, 2, '0'));
        }
        String password = padRight(ret.toString(), 32, '0');
        return password.toUpperCase();
    }

    /**
     * @param b
     * @return java.lang.String
     * @Title byteToHexString
     * @Author Panguaxe
     * @Time 2020/11/20 11:23
     * @Description TODO  转16进制字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        return Chars.HEXDIGITS[n / 16] + Chars.HEXDIGITS[n % 16];
    }

    /**
     * @param str 字符串
     * @param len 满足长度
     * @param ch  补充字符
     * @return java.lang.String
     * @Title padRight
     * @Author Panguaxe
     * @Time 2020/11/20 11:23
     * @Description TODO  右对齐
     */
    public static String padRight(String str, int len, char ch) {
        if (StringUtils.isNotBlank(str)) {
            return str;
        }
        if (0 >= len - str.length()) {
            return str;
        }
        char[] charr = new char[len];
        System.arraycopy(str.toCharArray(), 0, charr, len - str.length(), str.length());
        for (int i = 0; i < len - str.length(); i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    /**
     * @param plaintext 明文
     * @return java.lang.String
     * @Title MD5
     * @Author Panguaxe
     * @Time 2020/11/20 11:26
     * @Description TODO  MD5
     */
    public static String MD5(String plaintext) {
        byte[] btInput = plaintext.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance(AlgorithmEnum.MD5.getAlgorithm()); // 获得MD5摘要算法的MessageDigest
            mdInst.update(btInput);// 使用指定的字节更新摘要
            byte[] md = mdInst.digest();// 获得密文
            char[] str = new char[md.length * 2];
            int k = 0;
            for (int i = 0; i < md.length; i++) {
                byte byte0 = md[i];
                str[k++] = Chars.HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = Chars.HEX_DIGITS[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param origin
     * @param charsetname
     * @return java.lang.String
     * @Title MD5Encode
     * @Author Panguaxe
     * @Time 2020/11/20 11:30
     * @Description TODO
     */
    public static String MD5Encode(String origin, String charsetname) {
        try {
            MessageDigest md = MessageDigest.getInstance(AlgorithmEnum.MD5.getAlgorithm());
            if (StringUtils.isBlank(charsetname)) {
                origin = byteArrayToHexString(md.digest(origin.getBytes()));
            } else {
                origin = byteArrayToHexString(md.digest(origin.getBytes(charsetname)));
            }
        } catch (Exception exception) {
            return origin;
        }
        return origin;
    }

    /**
     * @param b
     * @return java.lang.String
     * @Title byteArrayToHexString
     * @Author Panguaxe
     * @Time 2020/11/20 11:31
     * @Description TODO
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder buffer = new StringBuilder();
        for (byte byt : b) {
            buffer.append(byteToHexString(byt));
        }
        return buffer.toString();
    }
}
