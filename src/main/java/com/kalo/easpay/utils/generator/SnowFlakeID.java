package com.kalo.easpay.utils.generator;

import com.kalo.easpay.common.constant.Format;
import com.kalo.easpay.utils.obj.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName SnowFlakeID
 * @Description TODO :
 * @Author ：Panguaxe
 * @Date 2020-08-08 15:58
 * @Version V1.0
 */
@Slf4j
public class SnowFlakeID {

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private long workerIdBits = 5L; // 物理节点ID长度
    private long datacenterIdBits = 5L; // 数据中心ID长度
    private long sequenceBits = 12L; // 序列号12位， 4095，同毫秒内生成不同id的最大个数
    private long workerIdShift = sequenceBits; // 机器节点左移12位
    private long datacenterIdShift = sequenceBits + workerIdBits; // 数据中心节点左移17位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; // 时间毫秒数左移22位
    private long maxWorkerId = -1L ^ (-1L << workerIdBits); // 最大支持机器节点数0~31，一共32个
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); // 最大支持数据中心节点数0~31，一共32个
    private long sequenceMask = -1L ^ (-1L << sequenceBits); // 用于和当前时间戳做比较，以获取最新时间
    private long twepoch = 1577808000L; // 2020-01-01 00:00:00 标记时间 用来计算偏移量，距离当前时间不同，得到的数据的位数也不同

    public static void main(String[] args) {
        Long workerId = workerId();
        log.warn("SnowFlakeID：{}", workerId);
        log.warn("SnowFlakeID逆推时间：{}",workerIdReverseTime(workerId));
    }
    /**
     * @Title SnowFlakeID
     * @Author Panguaxe
     * @return
     * @Time   2020/11/20 14:22
     * @Description      TODO  初始化构造，无参构造有参函数，默认节点都是0
     */
    public SnowFlakeID() {
        this(0L, 0L);
    }
    /**
     * @Author Panguaxe
     * @Time   2020/11/20 14:23
     * @Description      TODO  成员类，SnowFlakeUtil的实例对象的保存域
     */
    private static class IdGenHolder {
        private static final SnowFlakeID instance = new SnowFlakeID();
    }
    /**
     * @Title get
     * @Author Panguaxe
     * @return com.kalo.easpay.utils.generator.SnowFlakeID
     * @Time   2020/11/20 14:23
     * @Description      TODO  外部调用获取SnowFlakeUtil的实例对象，确保不可变
     */
    public static SnowFlakeID get() {
        return IdGenHolder.instance;
    }
    /**
     * @Title workerId
     * @Author Panguaxe
     * @return java.lang.Long
     * @Time   2020/11/20 14:23
     * @Description      TODO  获取全局唯一id
     */
    public static Long workerId() {
        return SnowFlakeID.get().nextId();
    }

    /**
     * @Title workerIdReverseTime
     * @Author Panguaxe
     * @param workerId
     * @return java.lang.String
     * @Time   2020/11/20 14:23
     * @Description      TODO  ID逆推时间
     */
    public static String workerIdReverseTime(Long workerId){
        long timestamp = get().timeStampBySnowId(workerId);
        return dateFormat(new Date(timestamp));
    }
    /**
     * @Title timeStampBySnowId
     * @Author Panguaxe 
     * @param snowId
     * @return long 
     * @Time   2020/11/20 14:23
     * @Description      TODO  
     */
    public long timeStampBySnowId(long snowId) {
        return timestamp(snowId) + twepoch;
    }
    /**
     * @Title timestamp
     * @Author Panguaxe
     * @param snowId
     * @return long
     * @Time   2020/11/20 14:24
     * @Description      TODO  从雪花id中计算出规定起始时间戳的时间戳    起始时间戳见EPOCH字段
     */
    public  long timestamp(long snowId) {
        String bid = Long.toBinaryString(snowId);
        Long timestamp = bid.length() - (workerIdBits + datacenterIdBits + sequenceBits);
        String substring = bid.substring(0, timestamp.intValue());
        return Long.parseUnsignedLong(substring, 2);
    }
    /**
     * @Title dateFormat
     * @Author Panguaxe
     * @param date
     * @return java.lang.String
     * @Time   2020/11/20 14:22
     * @Description      TODO  日期格式化为字符串
     */
    public static String dateFormat(Date date) {
        if (ObjectUtil.isBlank(date)){
            return "";
        }
        DateFormat format = new SimpleDateFormat(Format.YYYY_MM_DD_HH_MM_SS);
        return format.format(date);
    }

    /**
     * @Title SnowFlakeID
     * @Author Panguaxe
     * @param workerId
     * @param datacenterId
     * @return
     * @Time   2020/11/20 14:24
     * @Description      TODO  设置机器节点和数据中心节点数，都是 0-31
     */
    public SnowFlakeID(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * @Title nextId
     * @Author Panguaxe
     * @return long
     * @Time   2020/11/20 14:25
     * @Description      TODO  线程安全的id生成方法
     */
    @SuppressWarnings("all")
    public synchronized long nextId() {
        // 获取当前毫秒数
        long timestamp = System.currentTimeMillis();
        // 如果服务器时间有问题(时钟后退) 报错。
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            // sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            // 判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                // 自旋等待到下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加，每个毫秒时间内，都是从0开始计数，最大4095
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // 最后按照规则拼出ID 64位
        // 000000000000000000000000000000000000000000 00000 00000 000000000000
        // 1位固定整数 time datacenterId workerId sequence
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    /**
     * @Title tilNextMillis
     * @Author Panguaxe
     * @param lastTimestamp
     * @return long
     * @Time   2020/11/20 14:25
     * @Description      TODO  比较当前时间和过去时间，防止时钟回退（机器问题），保证给的都是最新时间/最大时间
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }


}
