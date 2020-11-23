package com.kalo.easpay.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 22日 星期日 15:17:52
 */
@Slf4j
public class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * TODO     将Object对象以json形式写入response返回
     * @Title   writeResponse
     * @author Panguaxe 
     * @param response
     * @param obj
     * @return  boolean 
     * @date    2020/11/23 10:16
     */
    public static boolean writeResponse(HttpServletResponse response, Object obj) {
        boolean flag = true;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            MAPPER.writeValue(response.getWriter(), obj);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
