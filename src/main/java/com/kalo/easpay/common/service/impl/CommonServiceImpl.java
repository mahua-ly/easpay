package com.kalo.easpay.common.service.impl;

import com.kalo.easpay.common.enums.ResultCode;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 16:47:56
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    /**
     * @Title paramVerify
     * @Author Panguaxe
     * @param responseResult
     * @param bindingResult
     * @return com.kalo.easpay.common.result.ResponseResult
     * @Time   2020/11/20 17:21
     * @Description      TODO  参数校验
     */
    @Override
    public ResponseResult paramVerify(ResponseResult responseResult, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage());
                break;
            }
            return responseResult.customError(ResultCode.BAD_PARAMS_ERROR.getErrcode(),ResultCode.BAD_PARAMS_ERROR.getErrmsg().replace("MSG",sb.toString()),null);
        }
        return responseResult.success(null);
    }
}
