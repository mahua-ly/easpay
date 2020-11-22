package com.kalo.easpay.handler;

import com.alibaba.fastjson.JSON;
import com.kalo.easpay.common.enums.ResultCode;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.service.LogRequestService;
import com.kalo.easpay.utils.http.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 21日 星期六 11:00:23
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private LogRequestService logRequestService;

    @ExceptionHandler(Exception.class)
    public ResponseResult exception(HttpServletRequest request, Exception exception) {
        ResponseResult result = new ResponseResult();
        long beginTime = System.currentTimeMillis();
        Integer errcode;
        String errmsg;
        if (exception instanceof NoHandlerFoundException) {
            //404
            errcode = ResultCode.NOT_FOUND_ERROR.getErrcode();
            errmsg = ResultCode.NOT_FOUND_ERROR.getErrmsg();
        } else if (exception instanceof NullPointerException) {
            //空指针
            errcode = ResultCode.NULL_POINTER_ERROR.getErrcode();
            errmsg = ResultCode.NULL_POINTER_ERROR.getErrmsg();
        } else if (exception instanceof IllegalArgumentException) {
            //非法参数
            errcode = ResultCode.ILLEGAL_ARGUMENT_ERROR.getErrcode();
            errmsg = ResultCode.ILLEGAL_ARGUMENT_ERROR.getErrmsg();
        } else if (exception instanceof IllegalStateException) {
            //非法状态
            errcode = ResultCode.ILLEGAL_STATE_ERROR.getErrcode();
            errmsg = ResultCode.ILLEGAL_STATE_ERROR.getErrmsg();
        } else if (exception instanceof HttpMessageNotWritableException) {
            //Http消息不可写
            errcode = ResultCode.HTTP_MSG_NOT_WRITABLE_ERROR.getErrcode();
            errmsg = ResultCode.HTTP_MSG_NOT_WRITABLE_ERROR.getErrmsg();
        } else if (exception instanceof HttpMessageNotReadableException) {
            //Http消息不可读
            errcode = ResultCode.HTTP_MSG_NOT_READABLE_ERROR.getErrcode();
            errmsg = ResultCode.HTTP_MSG_NOT_READABLE_ERROR.getErrmsg();
        } else if (exception instanceof ConversionFailedException) {
            //转换失败
            errcode = ResultCode.CONVERSION_FAILED_ERROR.getErrcode();
            errmsg = ResultCode.CONVERSION_FAILED_ERROR.getErrmsg();
        } else if (exception instanceof BindException) {
            //TODO 绑定/约束异常
            errcode = ResultCode.NOT_FOUND_ERROR.getErrcode();
            errmsg = ResultCode.NOT_FOUND_ERROR.getErrmsg();
        } else if (exception instanceof MethodArgumentNotValidException) {
            //TODO 方法参数无效
            errcode = ResultCode.NOT_FOUND_ERROR.getErrcode();
            errmsg = ResultCode.NOT_FOUND_ERROR.getErrmsg();
        } else if (exception instanceof DataIntegrityViolationException) {
            //TODO 数据完整性
            errcode = ResultCode.NOT_FOUND_ERROR.getErrcode();
            errmsg = ResultCode.NOT_FOUND_ERROR.getErrmsg();
        } else if (exception instanceof UnauthorizedException) {
            //Authorization未认证
            errcode = ResultCode.TOKEN_INVALID.getErrcode();
            errmsg = ResultCode.TOKEN_INVALID.getErrmsg();
        } else if (exception instanceof ArithmeticException){
            //0 不能做除数
            errcode = ResultCode.BY_ZERO_ERROR.getErrcode();
            errmsg = ResultCode.BY_ZERO_ERROR.getErrmsg();
        }else {
            errcode = ResultCode.SERVER_ERROR.getErrcode();
            errmsg = ResultCode.SERVER_ERROR.getErrmsg();
        }
        //响应data
        Map<String, Object> data = buildData(request, exception);
        result.customError(errcode, errmsg, data);
        //保存日志信息
        Map<String, String> params = RequestUtil.getParamsMap(request);
        long endTime = System.currentTimeMillis();
        logRequestService.addRequestLog(request, result, null, JSON.toJSONString(params),beginTime,endTime);
        return result;
    }

    private Map<String, Object> buildData(HttpServletRequest request,Exception exception){
        String className = "";
        String methodName = "";
        Integer lineNumber = 0;
        StackTraceElement[] stackTrace = exception.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (stackTraceElement.getClassName().startsWith("com.kalo.easpay")) {
                className = stackTraceElement.getClassName();
                methodName = stackTraceElement.getMethodName();
                lineNumber = stackTraceElement.getLineNumber();
                break;
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("errorMsg", exception.getMessage());
        data.put("className", className);
        data.put("methodName", methodName);
        data.put("lineNum", lineNumber);
        data.put("location", request.getRequestURL());
        return data;
    }
}
