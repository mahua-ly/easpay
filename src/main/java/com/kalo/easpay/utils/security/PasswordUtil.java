package com.kalo.easpay.utils.security;

import com.kalo.easpay.common.enums.AlgorithmEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 09月 27日 星期日 10:41:34
 */
@Slf4j
public class PasswordUtil {

    private static final RandomNumberGenerator RANDOM_NUMBER_GENERATOR = new SecureRandomNumberGenerator();

    public static void main(String[] args) {
        String content = "18300665808";

        String salt = genSalt();
        log.info("生成盐值：{}", salt);

        String simple = encryptSimple("665808", salt);
        log.info("常规密码加密[SimpleHash]：{}", simple);
        log.info("常规校验密码[SimpleHash]：{}", checkPwd("665808", salt, simple));

        String md5 = encryptMd5("665808", salt);
        log.info("常规密码加密[Md5Hash]：{}", md5);
        log.info("常规校验密码[Md5Hash]：{}", checkPwd("665808", salt, md5));

        String simplePwd = encryptSimpleHash(content, "123456", salt);
        log.info("密码加密[SimpleHash]：{}", simplePwd);
        log.info("检查密码[SimpleHash]：{}", checkPassword(content, "123456", salt, simplePwd));

        String md5Pwd = encryptMd5Hash(content, "123456", salt);
        log.info("密码加密[Md5Hash]：{}", md5Pwd);
        log.info("检查密码[Md5Hash] ：{}", checkPassword(content, "123456", salt, md5Pwd));

        String defaultPwd = defaultPwd(content, salt);
        log.info("密码加密[默认密码：后6位]：{}", defaultPwd);
        log.info("检查密码[默认密码：后6位]：{}", checkPassword(content, "665808", salt, defaultPwd));

    }

    /**
     * @Title genSalt
     * @Author Panguaxe
     * @return java.lang.String
     * @Time   2020/11/20 12:57
     * @Description      TODO  生成密码盐值
     */
    public static String genSalt() {
        return RANDOM_NUMBER_GENERATOR.nextBytes().toHex();
    }


    /**
     * @Title encryptSimple
     * @Author Panguaxe
     * @param password
     * @param salt
     * @return java.lang.String
     * @Time   2020/11/20 12:58
     * @Description      TODO  常规加密：SimpleHash
     */
    public static String encryptSimple(String password, String salt) {
        return new SimpleHash(AlgorithmEnum.MD5.getAlgorithm(), password, ByteSource.Util.bytes(salt), 2).toHex();
    }

    /**
     * @Title encryptMd5
     * @Author Panguaxe
     * @param password
     * @param salt
     * @return java.lang.String
     * @Time   2020/11/20 12:58
     * @Description      TODO  常规加密：Md5Hash
     */
    public static String encryptMd5(String password, String salt) {
        return new Md5Hash(password, salt, 2).toHex();
    }

    /**
     * @Title checkPwd
     * @Author Panguaxe
     * @param plaintextPwd      明文密码
     * @param salt              盐值
     * @param ciphertextPwd     密文密码
     * @return boolean
     * @Time   2020/11/20 12:59
     * @Description      TODO  常规：校验密码是否正确
     */
    public static boolean checkPwd(String plaintextPwd, String salt, String ciphertextPwd) {
        String password_cipherText = new Md5Hash(plaintextPwd, salt, 2).toHex();
        return ciphertextPwd.equals(password_cipherText);
    }

    /**
     * @Title encryptSimpleHash
     * @Author Panguaxe
     * @param telephone
     * @param password
     * @param salt
     * @return java.lang.String
     * @Time   2020/11/20 13:01
     * @Description      TODO  密码加密【SimpleHash】
     */
    public static String encryptSimpleHash(String telephone, String password, String salt) {
        return new SimpleHash(AlgorithmEnum.MD5.getAlgorithm(), password, ByteSource.Util.bytes(telephone + salt), 2).toHex();
    }

    /**
     * @Title encryptMd5Hash
     * @Author Panguaxe
     * @param telephone
     * @param password
     * @param salt
     * @return java.lang.String
     * @Time   2020/11/20 13:01
     * @Description      TODO  密码加密【Md5Hash】
     */
    public static String encryptMd5Hash(String telephone, String password, String salt) {
        return new Md5Hash(password, telephone + salt, 2).toHex();
    }

    /**
     * @Title checkPwd
     * @Author Panguaxe
     * @param username          用户名/手机号
     * @param plaintextPwd      明文密码
     * @param salt              盐值
     * @param ciphertextPwd     密文密码
     * @return boolean
     * @Time   2020/11/20 12:59
     * @Description      TODO  常规：校验密码是否正确
     */
    public static boolean checkPassword(String username, String plaintextPwd, String salt, String ciphertextPwd) {
        // 组合username,两次迭代，对密码进行加密
        String password_cipherText = new Md5Hash(plaintextPwd, username + salt, 2).toHex();
        return ciphertextPwd.equals(password_cipherText);
    }

    /**
     * @Title defaultPwd
     * @Author Panguaxe 
     * @param content
     * @param salt
     * @return java.lang.String 
     * @Time   2020/11/20 13:02
     * @Description      TODO  默认密码：手机号后6位
     */
    public static String defaultPwd(String content, String salt) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(salt)) {
            return "";
        }
        String password = content.substring(5);
        return encryptSimpleHash(content, password, salt);
    }
}
