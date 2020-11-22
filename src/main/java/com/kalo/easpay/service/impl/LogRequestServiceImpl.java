package com.kalo.easpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.domain.entity.LogRequest;
import com.kalo.easpay.domain.mapper.LogRequestMapper;
import com.kalo.easpay.service.LogRequestService;
import com.kalo.easpay.utils.date.DateUtil;
import com.kalo.easpay.utils.http.RequestUtil;
import com.kalo.easpay.utils.obj.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 20:24:53
 */
@Slf4j
@Service
public class LogRequestServiceImpl implements LogRequestService {

    @Autowired
    private LogRequestMapper logRequestMapper;
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
    @Override
    public void addRequestLog(HttpServletRequest request, Object result, Signature signature, String params,long beginTime,long endTime) {
        LogRequest log = new LogRequest();
        log.setClientIp(RequestUtil.getIpAddr(request));
        log.setClassName(ObjectUtil.isNotBlank(signature) ? signature.getDeclaringTypeName().substring(signature.getDeclaringTypeName().lastIndexOf(".") + 1) : "");
        log.setMethodName(ObjectUtil.isNotBlank(signature) ? signature.getName() : "");
        log.setMethod(request.getMethod());
        log.setLocation(StringUtils.isNotBlank(request.getRequestURL()) ? request.getRequestURL().toString() : "");
        log.setTransferMode(request.getContentType());
        if (result instanceof ResponseResult) {
            log.setErrCode(((ResponseResult) result).getErrcode());
            log.setErrMsg(((ResponseResult) result).getMessage());
        }
        log.setParams(params);
        log.setResult(JSON.toJSONString(result));
        log.setBeginTime(DateUtil.timeStampConvertDate(beginTime));
        log.setEndTime(DateUtil.timeStampConvertDate(endTime));
        logRequestMapper.insertSelective(log);
    }
}
