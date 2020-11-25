package com.kalo.easpay.aspect;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.kalo.easpay.service.LogRequestService;
import com.kalo.easpay.utils.http.RequestUtil;
import com.kalo.easpay.utils.obj.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO    日志切面
 * @DateTime 2020年 11月 20日 星期五 16:44:53
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class RequestLogAspect {

    private final LogRequestService logRequestService;

    @Autowired
    public RequestLogAspect(LogRequestService logRequestService) {
        this.logRequestService = logRequestService;
    }

    @Pointcut(value = "execution(* com.kalo.easpay.controller.*.*(..))")
    public void controllerLog() {}

    /**
     * TODO
     * @title   around
     * @author  Panguaxe 
     * @param joinPoint 切入点
     * @return  java.lang.Object
     * @date    2020/11/26 0:17
     */
    @Around("controllerLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //开始时间
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        log.info("-----------------------✂--------------------------- " + request.getRequestURI() + "：请求开始 -----------------------✂---------------------------");
        log.info("请求方式[HTTP Method]  : {}", request.getMethod());
        log.info("传输方式[  Transfer ]  : {}", request.getContentType());
        log.info("客户端IP[Client   IP]  : {}", RequestUtil.getIpAddr(request));
        log.info("请求类名[Class  Name]  : {}", signature.getDeclaringTypeName());
        log.info("请求类名[Method Name]  : {}", signature.getName());
        String Params = "";
        for (Object params : args) {
            if (ObjectUtil.isNotBlank(params)) {
                if (!(params instanceof HttpServletRequest
                        || params instanceof HttpServletResponse
                        || params instanceof BeanPropertyBindingResult
                        || params instanceof PageInfo
                        || params instanceof MultipartFile)) {
                    log.info("请求参数[Request Args] : {}", JSON.toJSONString(params));
                    Params = JSON.toJSONString(params);
                }
            }
        }
        //响应结果
        Object result = joinPoint.proceed();
        //保存日志信息
        logRequestService.addRequestLog(request, result, signature, Params,start,System.currentTimeMillis());
        long endMillis = System.currentTimeMillis();
        BigDecimal time = new BigDecimal(endMillis - start).divide(new BigDecimal(1000), 3, BigDecimal.ROUND_UNNECESSARY);
        log.info("耗时[Time Consuming]   : {} ", endMillis - start + " 毫秒");
        log.info("耗时[Time Consuming]   : {} ", time + " 秒");
        log.info("返回报文[" + signature.getName() + "]：{}", JSON.toJSONString(result));
        log.info("-----------------------✂--------------------------- " + request.getRequestURI() + "：处理完成 -----------------------✂---------------------------");
        return result;
    }
}
