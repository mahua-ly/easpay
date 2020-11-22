package com.kalo.easpay.common.result;

import com.kalo.easpay.common.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 16:49:33
 */
@Data
public class ResponseResult implements Serializable {

    /**响应码*/
    private Integer errcode = ResultCode.SUCCESS.getErrcode();
    /**响应信息*/
    private String message = ResultCode.SUCCESS.getErrmsg();
    /**业务数据*/
    private Object data;


    /**
     * @Title success
     * @Author Panguaxe
     * @param data  业务数据
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Time   2020/11/20 17:02
     * @Description      TODO  处理成功
     */
    public ResponseResult success(Object data) {
        this.data = data;
        return this;
    }

    /**
     * @Title fail
     * @Author Panguaxe
     * @param errmsg    错误信息
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Time   2020/11/20 16:57
     * @Description      TODO  处理失败
     */
    public ResponseResult fail(String errmsg) {
        this.errcode = ResultCode.FAIL.getErrcode();
        this.message = ResultCode.FAIL.getErrmsg().replace("MSG",errmsg);
        return this;
    }

    /**
     * @Title error
     * @Author Panguaxe
     * @param data  异常信息
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Time   2020/11/20 17:05
     * @Description      TODO  未知错误/异常
     */
    public ResponseResult error(Object data) {
        this.errcode = ResultCode.SERVER_ERROR.getErrcode();
        this.message = ResultCode.SERVER_ERROR.getErrmsg();
        this.data = data;
        return this;
    }

    /**
     * @Title customError
     * @Author Panguaxe
     * @param errcode   错误码
     * @param errmsg    错误信息
     * @param data      业务数据
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Time   2020/11/20 17:07
     * @Description      TODO  自定义错误
     */
    public ResponseResult customError(Integer errcode, String errmsg, Object data) {
        this.errcode = errcode;
        this.message = errmsg;
        this.data = data;
        return this;
    }
}
