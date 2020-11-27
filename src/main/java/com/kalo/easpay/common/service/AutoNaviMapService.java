package com.kalo.easpay.common.service;

import com.kalo.easpay.common.result.ResponseResult;

public interface AutoNaviMapService {

    ResponseResult syncDistrict(ResponseResult result, Integer subdistrict, String keywords);
}
