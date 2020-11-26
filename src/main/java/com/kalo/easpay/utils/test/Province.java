package com.kalo.easpay.utils.test;

import lombok.Data;

import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 26日 星期四 16:30:01
 */
@Data
public class Province {

    //code
    String code;
    //name
    String name;
    //市
    List<City> cities;
}
