package com.kalo.easpay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author  Panguaxe
 * @Time    2020/11/20 19:56
 * @Desc    TODO  状态码
 */
@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode {

    /**错误码、描述*/
    SUCCESS(10000, "处理成功"),
    FAIL(10001, "处理失败[MSG]"),
    REDIRECT_ERROR(30001, "Redirect Error"),
    BAD_PARAMS_ERROR(40000, "请求参数错误[MSG]"),
    TOKEN_INVALID(40001, "Unauthorized[TOKEN INVALID]"),
    HTTP_MSG_NOT_WRITABLE_ERROR(40002, "Http消息不可写"),
    HTTP_MSG_NOT_READABLE_ERROR(40003, "Http消息不可读"),
    NOT_FOUND_ERROR(40004, "404 Not Found[有时迷路并不是那么糟糕]"),
    NULL_POINTER_ERROR(40005, "服务出错[Null Pointer]"),
    CONVERSION_FAILED_ERROR(40006, "Conversion Failed[转换失败]"),
    ILLEGAL_ARGUMENT_ERROR(40022, "Unprocessable Entity[非法参数]"),
    ILLEGAL_STATE_ERROR(40023, "Illegal State[非法状态]"),
    BY_ZERO_ERROR(40024, "by zero[0 不能做除数]"),
    SERVER_ERROR(50000, "未知错误,请稍后再试");

    /**错误码*/
    private Integer errcode;
    /**错误信息*/
    private String errmsg;


}
