package com.kalo.easpay.service;

import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.common.vo.AppLoginVO;
import com.kalo.easpay.domain.entity.UserInfo;

public interface UserInfoService {
    /**
     * @Title findUserInfo
     * @Author Panguaxe 
     * @param userName
     * @return com.kalo.easpay.domain.entity.UserInfo 
     * @Time   2020/11/21 18:07
     * @Description      TODO  根据账号查找用户信息
     */
    UserInfo findUserInfo(String userName);

    /**
     * @Title getUserInfo
     * @Author Panguaxe 
     * @param mobile
     * @return com.kalo.easpay.domain.entity.UserInfo 
     * @Time   2020/11/21 18:07
     * @Description      TODO  根据手机号查找用户信息
     */
    UserInfo getUserInfo(String mobile);
    /**
     * @Title registerUser
     * @Author Panguaxe 
     * @param mobile
     * @return com.kalo.easpay.domain.entity.UserInfo 
     * @Time   2020/11/21 18:07
     * @Description      TODO  用户注册
     */
    UserInfo registerUser(String mobile,String clientIp);
    /**
     * @Title login
     * @Author Panguaxe
     * @param result
     * @param request
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Time   2020/11/21 18:06
     * @Description      TODO  登陆：手机号验证码、账号密码
     */
    ResponseResult login(ResponseResult result, AppLoginVO request);
}
