package com.kalo.easpay.service;

import org.aspectj.lang.Signature;

import javax.servlet.http.HttpServletRequest;

/**
 * Demo class
 *
 * @author keriezhang
 * @date 2016/10/31
 */
public interface LogRequestService {

    /**
     * TODO     保存日志信息
     * @Title   addRequestLog
     * @author  Panguaxe
     * @param request       请求对象
     * @param result        响应对象
     * @param signature     切面签名
     * @param params        请求参数
     * @param beginTime     开始时间
     * @param endTime       结束时间
     * @date    2020/11/21 21:54
     */
    void addRequestLog(HttpServletRequest request, Object result, Signature signature, String params,long beginTime,long endTime);
}
