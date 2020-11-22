package com.kalo.easpay.common.service;

import com.kalo.easpay.common.result.ResponseResult;
import org.springframework.validation.BindingResult;

public interface CommonService {
    /**
     * @Title paramVerify
     * @Author Panguaxe
     * @param responseResult
     * @param bindingResult
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Time   2020/11/20 17:21
     * @Description      TODO  参数校验
     */
    ResponseResult paramVerify(ResponseResult responseResult, BindingResult bindingResult);
}
