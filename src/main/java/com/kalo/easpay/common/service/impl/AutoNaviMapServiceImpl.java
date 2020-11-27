package com.kalo.easpay.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kalo.easpay.common.dto.DistrictsDTO;
import com.kalo.easpay.common.enums.ResultCode;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.common.service.AutoNaviMapService;
import com.kalo.easpay.service.BaseAreaInfoService;
import com.kalo.easpay.utils.http.HttpUtil;
import com.kalo.easpay.utils.obj.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 27日 星期五 14:12:22
 */
@Slf4j
@Service
@RefreshScope
public class AutoNaviMapServiceImpl implements AutoNaviMapService {

    @Value("${amap.api.key}")
    private String apiKey;

    @Value("${amap.api.district.url}")
    private String districtUrl;

    private final BaseAreaInfoService baseAreaInfoService;

    @Autowired
    public AutoNaviMapServiceImpl(BaseAreaInfoService baseAreaInfoService) {
        this.baseAreaInfoService = baseAreaInfoService;
    }

    /**
     * TODO     拼装URL
     * @Title   buildUrl
     * @author Panguaxe
     * @param subdistrict
     * @param keywords
     * @return  java.lang.String
     * @date    2020/11/27 16:23
     */
    private String buildUrl(Integer subdistrict,String keywords){
        subdistrict = ObjectUtil.isBlank(subdistrict) ? 1 : subdistrict;
        String url = districtUrl + "&key=" + apiKey + "&subdistrict=" + subdistrict;
        if (StringUtils.isNotBlank(keywords)){
            url = url + "&keywords=" + keywords;
        }
        log.info("Request url：  {}",url);
        return url;
    }


    @Override
    public ResponseResult syncDistrict(ResponseResult result,Integer subdistrict,String keywords){
        String url = buildUrl(subdistrict, keywords);
        String httpResponse;
        try {
            httpResponse = HttpUtil.doGetAuth(url);
            log.info("HttpRequest return Info：  {}",httpResponse);
        }catch (Exception exception){
            return result.error(url);
        }
        JSONObject response = JSON.parseObject(httpResponse);
        String returnCode = response.getString("infocode");
        if (!"10000".equals(returnCode)){
            return result.error(response);
        }
        List<DistrictsDTO> districts;
        try {
            districts = JSON.parseArray(response.getString("districts"), DistrictsDTO.class);
        }catch (Exception exception){
            return result.customError(ResultCode.CONVERSION_FAILED_ERROR.getErrcode(),ResultCode.CONVERSION_FAILED_ERROR.getErrmsg(),response.getJSONArray("districts"));
        }
        log.info("行政区划转换DTO结果：{}",JSON.toJSONString(districts));
        //同步数据
        return baseAreaInfoService.syncDistrict(result,subdistrict,districts);
    }



}
