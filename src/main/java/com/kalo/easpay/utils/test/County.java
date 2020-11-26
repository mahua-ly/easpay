package com.kalo.easpay.utils.test;

import lombok.Data;

import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 26日 星期四 16:32:22
 */
@Data
public class County {

    //code
    String code;
    //name
    String name;
    //镇
    List<Country> countries;
}
