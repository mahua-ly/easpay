package com.kalo.easpay.common.service.impl;

import com.kalo.easpay.common.conf.WeChatConfig;
import com.kalo.easpay.common.enums.LoginWayEnum;
import com.kalo.easpay.common.enums.ResultCode;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.common.service.LoginService;
import com.kalo.easpay.common.vo.AppLoginVO;
import com.kalo.easpay.service.UserInfoService;
import com.kalo.easpay.utils.http.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 21日 星期六 16:59:52
 */
@Slf4j
@Service
@RefreshScope
public class LoginServiceImpl implements LoginService {

    private final UserInfoService userInfoService;
    private final WeChatConfig weChatConfig;

    @Autowired
    public LoginServiceImpl(UserInfoService userInfoService, WeChatConfig weChatConfig) {
        this.userInfoService = userInfoService;
        this.weChatConfig = weChatConfig;
    }

    /**
     * TODO     登陆
     *
     * @param result
     * @param request
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Title login
     * @author Panguaxe
     * @date 2020/11/21 22:56
     */
    @Override
    public ResponseResult login(HttpServletResponse response, ResponseResult result, AppLoginVO request) {
        result = checkParam(result, request);
        if (ResultCode.SUCCESS.getErrcode().equals(result.getErrcode())) {
            //手机登录、账号登录
            if (LoginWayEnum.MOBILE_SMS_CODE.getType().equals(request.getType()) || LoginWayEnum.USER_NAME.getType().equals(request.getType())) {
                result = userInfoService.login(result, request);
            }
            //微信授权登录
            if (LoginWayEnum.WX_CHAT.getType().equals(request.getType())) {
                //第一步：引导用户进入授权页面同意授权，获取code  授权页面地址
                String url = weChatConfig.getAuthCodeUrl().replace("APPID", weChatConfig.getAppId()).replace("REDIRECT_URI", RequestUtil.URLEncode(weChatConfig.getWeChatAuthCallBackUrl()));
                log.warn("授权重定向[authCodeURL]：{}",url);
                try {
                    //重定向到授权页面
                    response.sendRedirect(url);
                } catch (IOException e) {
                    return result.customError(ResultCode.REDIRECT_ERROR.getErrcode(), ResultCode.REDIRECT_ERROR.getErrmsg(), url);
                }
            }
        }
        return result;
    }
    /**
     * TODO     参数校验
     *
     * @param result
     * @param request
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Title checkParam
     * @author Panguaxe
     * @date 2020/11/21 22:56
     */
    private ResponseResult checkParam(ResponseResult result, AppLoginVO request) {
        Integer type = request.getType();
        if (LoginWayEnum.MOBILE_SMS_CODE.getType().equals(type)) {
            if (StringUtils.isBlank(request.getMobile())) {
                return result.customError(ResultCode.BAD_PARAMS_ERROR.getErrcode(), ResultCode.BAD_PARAMS_ERROR.getErrmsg().replace("MSG", "请输入手机号"), "");
            }
            if (StringUtils.isBlank(request.getCode())) {
                return result.customError(ResultCode.BAD_PARAMS_ERROR.getErrcode(), ResultCode.BAD_PARAMS_ERROR.getErrmsg().replace("MSG", "请输入验证码"), "");
            }
        }
        if (LoginWayEnum.USER_NAME.getType().equals(type)) {
            if (StringUtils.isBlank(request.getUsername())) {
                return result.customError(ResultCode.BAD_PARAMS_ERROR.getErrcode(), ResultCode.BAD_PARAMS_ERROR.getErrmsg().replace("MSG", "请输入账号"), "");
            }
            if (StringUtils.isBlank(request.getPassword())) {
                return result.customError(ResultCode.BAD_PARAMS_ERROR.getErrcode(), ResultCode.BAD_PARAMS_ERROR.getErrmsg().replace("MSG", "请输入密码"), "");
            }
        }
        return result;
    }
}
