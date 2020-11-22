package com.kalo.easpay.utils.date;

import com.kalo.easpay.common.enums.CalendarUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 20:33:47
 */
@Slf4j
public class DateUtil {

    public static final DateTimeFormatter YYYYMMDDHHMMSS_SSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static final DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    public static void main(String[] args) throws ParseException {
        String currentDateTime = getCurrentDateTime();
        log.info("字符串转Date ：{}", parseDateTime(currentDateTime));
        log.info("Date转字符串 ：{}", parseDateTime(parseDateTime(currentDateTime)));
        log.info("昨天此时 ：{}", afterOrBeforeTime(-1));
        log.info("明天此时 ：{}", afterOrBeforeTime(1));
        log.info("昨天此时 ：{}", getYesterday());
        log.info("时间差 [YEARS-年] ：{}", differTime(afterOrBeforeTime(-369), currentDateTime, ChronoUnit.YEARS));
        log.info("时间差 [MONTHS-月] ：{}", differTime(afterOrBeforeTime(-62), currentDateTime, ChronoUnit.MONTHS));
        log.info("时间差 [DAYS-日] ：{}", differTime(afterOrBeforeTime(7), currentDateTime, ChronoUnit.DAYS));
        log.info("时间差 [HOURS-时] ：{}", differTime(getYesterday(), currentDateTime, ChronoUnit.HOURS));
        log.info("时间差 [MILLIS-分] ：{}", differTime("2020-09-25 15:08:49", currentDateTime, ChronoUnit.MINUTES));
        log.info("时间差 [SECONDS-秒] ：{}", differTime("2020-09-25 15:08:49", currentDateTime, ChronoUnit.SECONDS));
        log.info("获取当前 yyyy-MM-dd HH:mm:ss ：{}", getCurrentDateTime());
        log.info("获取当前 yyyy-MM-dd HH:mm:ss ：{}", currentDateTime());
        log.info("获取当前 yyyy-MM-dd ：{}", getCurrentDate());
        log.info("获取当前 yyyy-MM-dd ：{}", currentDate());
        log.info("获取当前 yyyyMMddHHmmss ：{}", getCurrentLongTime());
        log.info("获取当前 yyyyMMddHHmmssSSS ：{}", getCurrentSSSTime());
        log.info("获取当前 HH:mm:ss ：{}", currentTime());
        log.info("昨天小于当前 ：{}", lt(getYesterday(), currentDateTime));
        log.info("明天大于当前 ：{}", gt(afterOrBeforeTime(1), currentDateTime));
        log.info("某时间是否在两个时间之间 ：{}", isBetween("2020-09-25 16:00:49", "2020-09-25 15:08:49", currentDateTime));
        log.info("某时间是否在两个时间之间 ：{}", isBetween("2021-09-25 14:09:49", "2020-09-25 14:08:49", currentDateTime));
        Date year = afterOrBeforeTime(currentDateTime(), 1, CalendarUnit.YEAR.getUnit());
        Date month = afterOrBeforeTime(currentDateTime(), 1, CalendarUnit.MONTH.getUnit());
        Date date = afterOrBeforeTime(currentDateTime(), 1, CalendarUnit.DATE.getUnit());
        Date hour = afterOrBeforeTime(currentDateTime(), 1, CalendarUnit.HOUR.getUnit());
        Date minute = afterOrBeforeTime(currentDateTime(), 1, CalendarUnit.MINUTE.getUnit());
        Date second = afterOrBeforeTime(currentDateTime(), 1, CalendarUnit.SECOND.getUnit());
        log.info("当前时间1年后 ：{}", parseDateTime(year));
        log.info("当前时间1月后 ：{}", parseDateTime(month));
        log.info("当前时间1天后 ：{}", parseDateTime(date));
        log.info("当前时间1小时后 ：{}", parseDateTime(hour));
        log.info("当前时间1分钟后 ：{}", parseDateTime(minute));
        log.info("当前时间1秒后 ：{}", parseDateTime(second));
        log.info("当前时间5秒前 ：{}", parseDateTime(afterOrBeforeTime(currentDateTime(), -5, CalendarUnit.SECOND.getUnit())));
        log.info("当前时间5分钟前 ：{}", parseDateTime(afterOrBeforeTime(currentDateTime(), -5, CalendarUnit.MINUTE.getUnit())));
        log.info("当前时间时间戳 ：{}", currentTimeStamp());
        log.info("时间转时间戳 ：{}", parseTimeStamp(currentDateTime));

    }

    /**
     * @Title   timeStampConvertDate
     * @Author  Panguaxe
     * @param timeStamp
     * @return  java.util.Date
     * @Time    2020/11/21 21:26
     * @Desc    TODO  时间戳转时间
     */
    public static Date timeStampConvertDate(long timeStamp){
        return new Date(timeStamp);
    }

    /**
     * @Method afterOrBeforeTime
     * @Author Panguaxe
     * @param: amount               当前时间之前：-负整数；之后正整数
     * @Return java.lang.String
     * @Desc TODO                                    当前时间 之前几天 OR 之后几天
     * @Time 2020-09-25 15:04
     */
    public static String afterOrBeforeTime(Integer amount) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amount);
        Date time = cal.getTime();
        return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).format(time);
    }

    /**
     * @Method abSomeTime
     * @Author Panguaxe
     * @param: date
     * @param: num
     * @param: unit：0-年；1-月；2-日；3-分
     * @Return java.util.Date
     * @Desc TODO                                 当前时间 之前 OR 之后  指定时间
     * @Time 2020-09-25 17:23
     */
    public static Date afterOrBeforeTime(Date date, Integer num, Integer unit) {
        if (null != num && null != unit) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            Integer field = CalendarUnit.field(unit);
            calendar.add(field, num);
            return calendar.getTime();
        }
        return date;
    }

    /**
     * @Method getCurrentDateTime
     * @Author Panguaxe
     * @Return java.lang.String
     * @Desc TODO                                   获取当前时间：yyyy-MM-dd HH:mm:ss
     * @Time 2020-09-25 16:12
     */
    public static String getCurrentDateTime() {
        LocalTime localtime = LocalTime.now();
        LocalDateTime localDateTime = localtime.atDate(LocalDate.now());
        return localDateTime.format(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @Method getCurrentDate
     * @Author Panguaxe
     * @Return java.lang.String
     * @Desc TODO                                 获取当前时间：yyyy-MM-dd
     * @Time 2020-09-25 16:14
     */
    public static String getCurrentDate() {
        LocalTime localtime = LocalTime.now();
        LocalDateTime localDateTime = localtime.atDate(LocalDate.now());
        return localDateTime.format(YYYY_MM_DD);
    }

    /**
     * @Method currentTime
     * @Author Panguaxe
     * @Return java.lang.String
     * @Desc TODO                             当前时间
     * @Time 2020-09-25 16:34
     */
    public static String currentTime() {
        LocalTime localtime = LocalTime.now();
        LocalDateTime localDateTime = localtime.atDate(LocalDate.now());
        return localDateTime.format(HH_MM_SS);
    }

    /**
     * @Method getCurrentLongTime
     * @Author Panguaxe
     * @Return java.lang.String
     * @Desc TODO                                   获取当前时间：YYYYMMDDHHMMSS
     * @Time 2020-09-25 16:00
     */
    public static String getCurrentLongTime() {
        LocalTime localtime = LocalTime.now();
        LocalDateTime localDateTime = localtime.atDate(LocalDate.now());
        return localDateTime.format(YYYYMMDDHHMMSS);
    }

    /**
     * @Method getCurrentSSSTime
     * @Author Panguaxe
     * @Return java.lang.String
     * @Desc TODO                                     获取当前时间：yyyyMMddHHmmssSSS
     * @Time 2020-09-25 16:31
     */
    public static String getCurrentSSSTime() {
        LocalTime localtime = LocalTime.now();
        LocalDateTime localDateTime = localtime.atDate(LocalDate.now());
        return localDateTime.format(YYYYMMDDHHMMSS_SSS);
    }

    /**
     * @Method getYesterday
     * @Author Panguaxe
     * @Return java.lang.String
     * @Desc TODO                                     获取昨天此时
     * @Time 2020-09-25 15:09
     */
    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date time = cal.getTime();
        return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).format(time);
    }

    /**
     * @Method parseDateTime
     * @Author Panguaxe
     * @param: date
     * @Return java.lang.String
     * @Desc TODO                                     时间格式化：Date To String
     * @Time 2020-09-25 16:03
     */
    public static String parseDateTime(Date date) {
        DateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        return format.format(date);
    }

    /**
     * @Method differTime
     * @Author Panguaxe
     * @param: startTime    开始时间
     * @param: endTime      结束时间
     * @param: unit         单位：ChronoUnit.YEARS[年]、ChronoUnit.MONTHS[月]、ChronoUnit.DAYS[日]、ChronoUnit.HOURS[时]、ChronoUnit.SECONDS[分]、ChronoUnit.MILLIS[秒]
     * @Return long         相差时长：年、月、日、时、分、秒
     * @Desc TODO                                 计算两个时间 相差时长
     * @Time 2020-09-25 15:18
     */
    public static Long differTime(String startTime, String endTime, ChronoUnit unit) {
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }
        Long differ = null;
        if (Arrays.asList(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS).contains(unit)) {
            LocalDate beginDate = LocalDate.parse(startTime, YYYY_MM_DD_HH_MM_SS);
            LocalDate endDate = LocalDate.parse(endTime, YYYY_MM_DD_HH_MM_SS);
            differ = unit.between(beginDate, endDate);
        } else if (Arrays.asList(ChronoUnit.HOURS, ChronoUnit.SECONDS, ChronoUnit.MINUTES).contains(unit)) {
            LocalDateTime beginTime = LocalDateTime.parse(startTime, YYYY_MM_DD_HH_MM_SS);
            LocalDateTime overTime = LocalDateTime.parse(endTime, YYYY_MM_DD_HH_MM_SS);
            differ = unit.between(beginTime, overTime);
        }
        return differ;
    }

    /**
     * @Method currentTimeStamp
     * @Author Panguaxe
     * @Return java.lang.Long
     * @Desc TODO                                 当前时间时间戳
     * @Time 2020-09-25 18:01
     */
    public static Long currentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * @Method strToTimeStamp
     * @Author Panguaxe
     * @param: dateTime
     * @Return java.lang.Long
     * @Desc TODO                                 时间转时间戳
     * @Time 2020-09-25 18:05
     */
    public static Long parseTimeStamp(String dateTime) throws ParseException {
        if (StringUtils.isBlank(dateTime)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date parse = simpleDateFormat.parse(dateTime);
        return parse.getTime() / 1000;
    }

    /**
     * @Method currentDateTime
     * @Author Panguaxe
     * @Return java.util.Date
     * @Desc TODO                                 获取当前时间：yyyy-MM-dd HH:mm:ss
     * @Time 2020-09-25 16:16
     */
    public static Date currentDateTime() {
        String dateTime = getCurrentDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        try {
            return simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @Method currentDate
     * @Author Panguaxe
     * @Return java.util.Date
     * @Desc TODO                                 获取当前时间
     * @Time 2020-09-25 16:19
     */
    public static Date currentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd);
        try {
            return simpleDateFormat.parse(getCurrentDate());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @Method parseDateTime
     * @Author Panguaxe
     * @param: dateTime
     * @Return java.util.Date
     * @Desc TODO                                     String Parse DateTime
     * @Time 2020-09-25 15:01
     */
    public static Date parseDateTime(String dateTime) {
        if (StringUtils.isBlank(dateTime)) {
            return null;
        }
        try {
            return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @Method parseDate
     * @Author Panguaxe
     * @param: date
     * @Return java.time.LocalDateTime
     * @Desc TODO
     * @Time 2020-09-25 16:24
     */
    public static LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date.replaceAll("\\.", "-").trim(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @Method isBetween
     * @Author Panguaxe
     * @param: date
     * @param: a
     * @param: b
     * @Return java.lang.Boolean
     * @Desc TODO                                 a《 date《 b
     * @Time 2020-09-25 16:26
     */
    public static Boolean isBetween(String date, String a, String b) {
        Boolean tag = false;
        if (gt(date, a) && lt(date, b)) {
            tag = true;
        }
        return tag;
    }

    /**
     * @Method lt
     * @Author Panguaxe
     * @param: a
     * @param: b
     * @Return java.lang.Boolean
     * @Desc TODO                                 a小于b
     * @Time 2020-09-25 16:27
     */
    public static Boolean lt(String a, String b) {
        return lt(parseDate(a), parseDate(b));
    }

    /**
     * @Method lt
     * @Author Panguaxe
     * @param: a
     * @param: b
     * @Return java.lang.Boolean
     * @Desc TODO                                 a小于b
     * @Time 2020-09-25 16:27
     */
    public static Boolean lt(LocalDateTime a, LocalDateTime b) {
        return a.isBefore(b);
    }

    /**
     * @Method gt
     * @Author Panguaxe
     * @param: a
     * @param: b
     * @Return java.lang.Boolean
     * @Desc TODO                                 a大于b
     * @Time 2020-09-25 16:27
     */
    public static Boolean gt(String a, String b) {
        return gt(parseDate(a), parseDate(b));
    }

    /**
     * @Method gt
     * @Author Panguaxe
     * @param: a
     * @param: b
     * @Return java.lang.Boolean
     * @Desc TODO                                 a大于b
     * @Time 2020-09-25 16:29
     */
    public static Boolean gt(LocalDateTime a, LocalDateTime b) {
        return a.isAfter(b);
    }
}
