package com.kalo.easpay.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 22日 星期日 15:17:52
 */
@Slf4j
public class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static T parseObj(Object obj, Class<T> clazz){
        return JSON.parseObject(JSON.toJSONString(obj), clazz);
    }
}
