package com.kalo.easpay.common.service;

import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.common.vo.AppLoginVO;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    /**
     * @Title login
     * @Author Panguaxe
     * @param result
     * @param request
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Time   2020/11/21 18:06
     * @Description      TODO  登陆
     */
    ResponseResult login(HttpServletResponse response, ResponseResult result, AppLoginVO request);
}
