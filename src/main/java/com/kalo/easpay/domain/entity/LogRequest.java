/*
*
* LogRequest.java
* Copyright(C) 2019-10-29 Kalo Tech
* @date 2020-11-20
*/
package com.kalo.easpay.domain.entity;

import com.kalo.easpay.utils.generator.SnowFlakeGenId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO    请求日志
 * @DateTime 2020年 11月 20日 星期五 16:44:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tab_log_request")
@Alias("请求日志")
public class LogRequest implements Serializable {

    /**
     * tab_log_request
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
	* 列名:id;类型:BIGINT(19);允许空:false;缺省值:null
    */
    @Id
    @KeySql(genId = SnowFlakeGenId.class)
    @Column(name = "id")
    private Long id;

    /**
     * 客户端IP
	* 列名:client_ip;类型:VARCHAR(64);允许空:true;缺省值:null
    */
    @Column(name = "client_ip")
    private String clientIp;

    /**
     * 全路径类名
	* 列名:class_name;类型:VARCHAR(128);允许空:true;缺省值:null
    */
    @Column(name = "class_name")
    private String className;

    /**
     * 接口方法名
	* 列名:method_name;类型:VARCHAR(128);允许空:true;缺省值:null
    */
    @Column(name = "method_name")
    private String methodName;

    /**
     * 接口地址
	* 列名:location;类型:VARCHAR(128);允许空:true;缺省值:null
    */
    @Column(name = "location")
    private String location;

    /**
     * 请求方式：GET/POST……
	* 列名:method;类型:VARCHAR(32);允许空:true;缺省值:null
    */
    @Column(name = "method")
    private String method;

    /**
     * 传输方式：json、form-data……
	* 列名:transfer_mode;类型:VARCHAR(255);允许空:true;缺省值:null
    */
    @Column(name = "transfer_mode")
    private String transferMode;

    /**
     * 状态码
	* 列名:err_code;类型:INTEGER(10);允许空:true;缺省值:null
    */
    @Column(name = "err_code")
    private Integer errCode;

    /**
     * 错误信息
	* 列名:err_msg;类型:VARCHAR(255);允许空:true;缺省值:null
    */
    @Column(name = "err_msg")
    private String errMsg;

    /**
     * 请求参数
     * 列名:params;类型:LONGVARCHAR(65535);允许空:true;缺省值:null
     */
    @Column(name = "params")
    private String params;

    /**
     * 处理结果
     * 列名:result;类型:LONGVARCHAR(65535);允许空:true;缺省值:null
     */
    @Column(name = "result")
    private String result;

    /**
     * 请求开始时间
	* 列名:begin_time;类型:TIMESTAMP(19);允许空:true;缺省值:null
    */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 请求完成时间
	* 列名:end_time;类型:TIMESTAMP(19);允许空:true;缺省值:null
    */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * @return id ID
     */
    public Long getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return client_ip 客户端IP
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * 客户端IP
     * @param clientIp 客户端IP
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    /**
     * @return class_name 全路径类名
     */
    public String getClassName() {
        return className;
    }

    /**
     * 全路径类名
     * @param className 全路径类名
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    /**
     * @return method_name 接口方法名
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 接口方法名
     * @param methodName 接口方法名
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    /**
     * @return location 接口地址
     */
    public String getLocation() {
        return location;
    }

    /**
     * 接口地址
     * @param location 接口地址
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    /**
     * @return method 请求方式：GET/POST……
     */
    public String getMethod() {
        return method;
    }

    /**
     * 请求方式：GET/POST……
     * @param method 请求方式：GET/POST……
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * @return transfer_mode 传输方式：json、form-data……
     */
    public String getTransferMode() {
        return transferMode;
    }

    /**
     * 传输方式：json、form-data……
     * @param transferMode 传输方式：json、form-data……
     */
    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode == null ? null : transferMode.trim();
    }

    /**
     * @return err_code 状态码
     */
    public Integer getErrCode() {
        return errCode;
    }

    /**
     * 状态码
     * @param errCode 状态码
     */
    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    /**
     * @return err_msg 错误信息
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * 错误信息
     * @param errMsg 错误信息
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }

    /**
     * @return params 请求参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 请求参数
     * @param params 请求参数
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * @return result 处理结果
     */
    public String getResult() {
        return result;
    }

    /**
     * 处理结果
     * @param result 处理结果
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }
    /**

     * @return begin_time 请求开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 请求开始时间
     * @param beginTime 请求开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @return end_time 请求完成时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 请求完成时间
     * @param endTime 请求完成时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}