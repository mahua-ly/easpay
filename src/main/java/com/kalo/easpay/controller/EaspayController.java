package com.kalo.easpay.controller;

import com.kalo.easpay.common.enums.ResultCode;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.common.service.AutoNaviMapService;
import com.kalo.easpay.common.service.CommonService;
import com.kalo.easpay.common.vo.SyncDistrictVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Panguaxe
 * @version 1.0.0
 * @date 2020年 11月 25日 星期三 22:20:05
 */
@CrossOrigin
@RestController
public class EaspayController {

    private final CommonService commonService;
    private final AutoNaviMapService autoNaviMapService;

    @Autowired
    public EaspayController(CommonService commonService,AutoNaviMapService autoNaviMapService) {
        this.commonService = commonService;
        this.autoNaviMapService = autoNaviMapService;
    }

    @RequestMapping({"MP_verify_rP7ufkI7M6jA0luU.txt"})
    private String weChatConfig() {
        return "rP7ufkI7M6jA0luU";
    }


    @RequestMapping("syncDistrict")
    public ResponseResult syncDistrict(@Validated SyncDistrictVO request, BindingResult bindingResult) {
        ResponseResult result = commonService.paramVerify(new ResponseResult(), bindingResult);
        if (ResultCode.SUCCESS.getErrcode().equals(result.getErrcode())) {
            result = autoNaviMapService.syncDistrict(result,request.getSubdistrict(),request.getKeywords());
        }
        return result;
    }
}
