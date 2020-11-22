package com.kalo.easpay.utils.obj;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 12:40:35
 */
@Slf4j
public class ObjectUtil {

    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static void main(String[] args) {
        log.info("是否相同：{}", isIdenticalDigit("aaaa"));
        log.info("是否连续递增：{}", isIncremental("56789"));
        log.info("是否连续递减：{}", isContinuityDecline("65432"));
    }

    /**
     * @Title genInviteCode
     * @Author Panguaxe 
     * @return java.lang.String 
     * @Time   2020/11/21 17:59
     * @Description      TODO  生成8位邀请码
     */
    public static String genInviteCode() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString().toUpperCase();
    }
    /**
     * @Title isBlank
     * @Author Panguaxe
     * @param obj
     * @return boolean
     * @Time   2020/11/20 14:15
     * @Description      TODO  判断对象是否为空
     */
    public static boolean isBlank(Object obj) {
        if (obj == null || obj.equals("undefined")) {
            return true;
        } else if (obj instanceof String && (obj.equals(""))) {
            return true;
        } else if (obj instanceof Collection && ((Collection<?>) obj).size() == 0) {
            return true;
        } else if (obj instanceof Map && ((Map<?, ?>) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;
        }
        return false;
    }
    /**
     * @Title isNotBlank
     * @Author Panguaxe
     * @param obj
     * @return boolean
     * @Time   2020/11/20 14:16
     * @Description      TODO  判断对象是否不为空
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }
    /**
     * @param content
     * @return boolean
     * @Title isIdenticalDigit
     * @Author Panguaxe
     * @Time 2020/11/20 12:42
     * @Description TODO  判断是否全部相同的字符：全部相同返回true【如：000000、111111、aaaaaa】
     */
    public static boolean isIdenticalDigit(String content) {
        boolean flag = true;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(0) != content.charAt(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * @param content
     * @return boolean
     * @Title isIncremental
     * @Author Panguaxe
     * @Time 2020/11/20 12:42
     * @Description TODO  判断是佛连续递增数字：连续数字返回true 【如：123456、12345678】
     */
    public static boolean isIncremental(String content) {
        //判断是否数字
        boolean isNumeric = isNumeric(content);
        if (!isNumeric) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < content.length(); i++) {
            if (i > 0) {
                if (Integer.parseInt(content.charAt(i) + "") != Integer.parseInt(content.charAt(i - 1) + "") + 1) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * @param content
     * @return boolean
     * @Title isContinuityDecline
     * @Author Panguaxe
     * @Time 2020/11/20 12:42
     * @Description TODO  判断是佛连续递减数字：连续数字返回true 【如：987654、876543】
     */
    public static boolean isContinuityDecline(String content) {
        //判断是否数字
        boolean isNumeric = isNumeric(content);
        if (!isNumeric) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < content.length(); i++) {
            if (i > 0) {
                if (Integer.parseInt(content.charAt(i) + "") != Integer.parseInt(content.charAt(i - 1) + "") - 1) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * @param content
     * @return boolean
     * @Title isNumeric
     * @Author Panguaxe
     * @Time 2020/11/20 12:49
     * @Description TODO  判断是否数字
     */
    public static boolean isNumeric(String content) {
        boolean isNumeric = true;
        for (int i = 0; i < content.length(); i++) {
            if (!Character.isDigit(content.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        return isNumeric;
    }

}
