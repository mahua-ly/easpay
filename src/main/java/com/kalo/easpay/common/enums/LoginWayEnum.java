package com.kalo.easpay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 09月 10日 星期四 11:50:16
 */
@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum LoginWayEnum {

    /**
     * 枚举
     */
    MOBILE_SMS_CODE(1, "手机号验证码登录"),
    USER_NAME(2, "账号密码登录"),
    WX_CHAT(3, "微信登录");

    /**
     * 类型
     */
    private Integer type;
    /**
     * 描述
     */
    private String desc;

    public static String desc(Integer type) {
        String desc = "";
        LoginWayEnum[] values = values();
        for (LoginWayEnum wayEnum : values) {
            if (wayEnum.getType().equals(type)) {
                desc = wayEnum.getDesc();
                break;
            }
        }
        return desc;
    }

    /**
     * @Title   ways
     * @Author  Panguaxe 
     * @return  java.util.List<java.lang.Integer> 
     * @Time    2020/11/20 22:22
     * @Desc    TODO  支持的登录方式
     */
    public static List<Integer> ways() {
        List<Integer> ways = new ArrayList<>();
        LoginWayEnum[] values = values();
        for (LoginWayEnum wayEnum : values) {
            ways.add(wayEnum.getType());
        }
        return ways;
    }
}
