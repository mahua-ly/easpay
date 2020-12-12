package com.kalo.easpay.service.impl;

import com.kalo.easpay.domain.entity.AreaInfo;
import com.kalo.easpay.domain.mapper.AreaInfoMapper;
import com.kalo.easpay.service.AreaInfoService;
import com.kalo.easpay.utils.date.DateUtil;
import com.kalo.easpay.utils.test.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Panguaxe
 * @version 1.0.0
 * @date 2020年 12月 12日 星期六 19:45:37
 */
@Slf4j
@Service
@RefreshScope
public class AreaInfoServiceImpl implements AreaInfoService {

    @Value("${national.stats.gov.baseUrl}")
    private String baseUrl;

    private final AreaInfoMapper areaInfoMapper;

    @Autowired
    public AreaInfoServiceImpl(AreaInfoMapper areaInfoMapper) {
        this.areaInfoMapper = areaInfoMapper;
    }

    @Override
    public int syncAreaInfo(String areaPrefix) throws IOException, InterruptedException {
        int i = 0;
        List<Province> provinces = GetAddress.getProvince(baseUrl, areaPrefix);
        for (Province province : provinces) {
            String provinceCode = province.getCode() + "0000000000";
            List<City> citys = province.getCities();
            AreaInfo areaProvince = buildAreaInfo(provinceCode, province.getName(), "100000000000", 1);
            int p = areaInfoMapper.insertSelective(areaProvince);
            if (1 == p){
                i = i + 1;
            }
            for (City city : citys) {
                String cityCode = city.getCode();
                List<County> counties = city.getCounties();
                AreaInfo areaCity = buildAreaInfo(cityCode, city.getName(), provinceCode, 2);
                int c = areaInfoMapper.insertSelective(areaCity);
                if (1 == c){
                    i = i + 1;
                }
                for (County county : counties) {
                    String countyCode = county.getCode();
                    List<Country> towns = county.getCountries();//乡镇、街道
                    AreaInfo areaCounty = buildAreaInfo(countyCode, county.getName(), cityCode, 3);
                    int y = areaInfoMapper.insertSelective(areaCounty);
                    if (1 == y){
                        i = i + 1;
                    }
                    for (Country town : towns) {
                        AreaInfo areaTown = buildAreaInfo(town.getCode(), town.getName(), countyCode, 4);
                        int t = areaInfoMapper.insertSelective(areaTown);
                        if (1 == t){
                            i = i + 1;
                        }
                    }
                }
            }
        }
        return i;
    }

    private AreaInfo buildAreaInfo(String code,String name,String parentId,Integer level){
        String areaCode = 4 == level ? code.substring(0,9) : code.substring(0,6);
        AreaInfo areaInfo = new AreaInfo();
        areaInfo.setAreaCode(code);
        areaInfo.setAreaName(name);
        areaInfo.setParentId(parentId);
        areaInfo.setLevel(level);
        areaInfo.setAreaNo(areaCode);
        areaInfo.setCreateTime(DateUtil.currentDateTime());
        return areaInfo;
    }
}
