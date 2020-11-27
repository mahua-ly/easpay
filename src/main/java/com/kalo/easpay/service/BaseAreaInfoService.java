package com.kalo.easpay.service;

import com.kalo.easpay.common.dto.DistrictsDTO;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.domain.entity.BaseAreaInfo;

import java.util.List;

public interface BaseAreaInfoService {

    BaseAreaInfo getAreaInfo(String areaCode);

    ResponseResult syncDistrict(ResponseResult result,Integer subdistrict, List<DistrictsDTO> districts);
}
