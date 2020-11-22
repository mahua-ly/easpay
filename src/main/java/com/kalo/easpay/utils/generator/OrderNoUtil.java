package com.kalo.easpay.utils.generator;

import com.kalo.easpay.common.constant.Chars;
import com.kalo.easpay.common.constant.Format;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 19日 星期四 23:44:47
 */
@Slf4j
public class OrderNoUtil {

    private static final AtomicInteger atomicInteger = new AtomicInteger(1000000);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            log.info("生成订单号[yyyyMMddHHmmssSSS]:{}",nextOrderNo());
        }
        for (int i = 0; i < 10; i++) {
            log.info("生成订单号[RandomUUID]:{}",randomUUIDOrderNo("665808"));
        }
        log.info("随机字符串[nextRandomStr]:{}",nextRandomStr(16));


    }
    /**
     * @Title   nextOrderNo
     * @Author  Panguaxe
     * @return  java.lang.String
     * @Time    2020/11/19 23:56
     * @Desc    TODO  生成订单号：yyyyMMddHHmmssSSS+自增序列
     */
    public synchronized static String nextOrderNo() {
        atomicInteger.getAndIncrement();//
        return Format.YYYYMMDDHHMMSS.format(new Date()) + atomicInteger.get();
    }
    /**
     * @Title   randomUUIDOrderNo
     * @Author  Panguaxe
     * @param stamp 自定义前缀
     * @return  java.lang.String    唯一的、不连续订单号
     * @Time    2020/11/19 23:59
     * @Desc    TODO  创建不连续的订单号
     */
    public static synchronized String randomUUIDOrderNo(String stamp) {
        Integer uuidHashCode = UUID.randomUUID().toString().hashCode();
        if (uuidHashCode < 0) {
            uuidHashCode = uuidHashCode * (-1);
        }
        String uuidCode = Format.YYYYMMDDHHMMSS.format(new Date()) + uuidHashCode;
        return StringUtils.isNotBlank(stamp) ? stamp + uuidCode : uuidCode;
    }
    /**
     * @Title   nextRandomStr
     * @Author  Panguaxe
     * @param length
     * @return  java.lang.String
     * @Time    2020/11/20 0:00
     * @Desc    TODO  随机字符串
     */
    public static String nextRandomStr(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(Chars.RANDOMSTR_CHARS.length());
            sb.append(Chars.RANDOMSTR_CHARS.charAt(number));
        }
        return sb.toString();
    }
}
