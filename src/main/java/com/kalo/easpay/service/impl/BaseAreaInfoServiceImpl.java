package com.kalo.easpay.service.impl;

import com.kalo.easpay.common.dto.DistrictsDTO;
import com.kalo.easpay.common.enums.AreaLevelEnum;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.domain.entity.BaseAreaInfo;
import com.kalo.easpay.domain.mapper.BaseAreaInfoMapper;
import com.kalo.easpay.service.BaseAreaInfoService;
import com.kalo.easpay.utils.date.DateUtil;
import com.kalo.easpay.utils.obj.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 27日 星期五 16:10:05
 */
@Slf4j
@Service
@RefreshScope
public class BaseAreaInfoServiceImpl implements BaseAreaInfoService {

    private final BaseAreaInfoMapper baseAreaInfoMapper;

    @Autowired
    public BaseAreaInfoServiceImpl(BaseAreaInfoMapper baseAreaInfoMapper) {
        this.baseAreaInfoMapper = baseAreaInfoMapper;
    }

    @Override
    public BaseAreaInfo getAreaInfo(String areaCode) {
        Example example = new Example(BaseAreaInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("areaCode", areaCode);
        criteria.andEqualTo("isUsable", 1);
        List<BaseAreaInfo> areaInfos = baseAreaInfoMapper.selectByExample(example);
        return areaInfos.size() > 0 ? areaInfos.get(0) : null;
    }

    @Override
    public ResponseResult syncDistrict(ResponseResult result, Integer subdistrict, List<DistrictsDTO> districts) {
        BaseAreaInfo baseAreaInfo;
        int i = 0;
//        districts = new LinkedList<>(districts);
        for (DistrictsDTO firstFloorDistricts : districts) {
            //1.子级行政区   0：不返回下级行政区；1：返回下一级行政区；2：返回下两级行政区；3：返回下三级行政区；最多三层 即 最多循环四层
            String firstCode = firstFloorDistricts.getAdcode();
            baseAreaInfo = getAreaInfo(firstCode);
            if (ObjectUtil.isBlank(baseAreaInfo)) {
                saveDistricts(new BaseAreaInfo(), firstCode, firstFloorDistricts.getName(), firstFloorDistricts.getLevel(), firstFloorDistricts.getCitycode(), firstFloorDistricts.getCenter(), "");
            }
            //2.获取下级行政区划
            List<DistrictsDTO> districts2 = firstFloorDistricts.getDistricts();
            if (districts2.size() > 0) {
                for (DistrictsDTO twoFloorDistricts : districts2) {
                    String twoCode = twoFloorDistricts.getAdcode();
                    baseAreaInfo = getAreaInfo(twoCode);
                    if (ObjectUtil.isBlank(baseAreaInfo)) {
                        saveDistricts(new BaseAreaInfo(),twoFloorDistricts.getAdcode(),twoFloorDistricts.getName(),twoFloorDistricts.getLevel(),twoFloorDistricts.getCitycode(),twoFloorDistricts.getCenter(),firstCode);
                    }
                    //3.获取下级行政区划
                    List<DistrictsDTO> districts3 = twoFloorDistricts.getDistricts();
                    if (districts3.size() > 0) {
                        for (DistrictsDTO threeFloorDistricts : districts3) {
                            String threeCode = threeFloorDistricts.getAdcode();
                            baseAreaInfo = getAreaInfo(threeFloorDistricts.getAdcode());
                            if (ObjectUtil.isBlank(baseAreaInfo)) {
                                saveDistricts(new BaseAreaInfo(),threeFloorDistricts.getAdcode(),threeFloorDistricts.getName(),threeFloorDistricts.getLevel(),threeFloorDistricts.getCitycode(),threeFloorDistricts.getCenter(),twoCode);
                            }
                            //3.获取下级行政区划
                            List<DistrictsDTO> districts4 = threeFloorDistricts.getDistricts();
                            if (districts4.size() > 0){
                                for (DistrictsDTO fourFloorDistricts : districts4) {
                                    saveDistricts(new BaseAreaInfo(),fourFloorDistricts.getAdcode(),fourFloorDistricts.getName(),fourFloorDistricts.getLevel(),fourFloorDistricts.getCitycode(),fourFloorDistricts.getCenter(),threeCode);
                                }
                            }
                        }
                    }
                }
            }
        }
        return result.success("同步数据共计：" + i);
    }

    private int saveDistricts(BaseAreaInfo baseAreaInfo, String areaCode, String areaName, String levelEn, String cityCode, String center,String parentId) {
        baseAreaInfo.setAreaCode(areaCode);
        baseAreaInfo.setAreaName(areaName);
        baseAreaInfo.setParentId(parentId);
        if (StringUtils.isNotBlank(center)) {
            List<String> lonLat = Arrays.asList(center.split(","));
            if (lonLat.size() == 2) {
                baseAreaInfo.setLongitude(new BigDecimal(lonLat.get(0)));
                baseAreaInfo.setLatitude(new BigDecimal(lonLat.get(1)));
            }
        }
        Integer level = AreaLevelEnum.level(levelEn);
        baseAreaInfo.setLevel(level);
        baseAreaInfo.setLevelEn(levelEn);
        baseAreaInfo.setAreaNo("[]".equals(cityCode) ? "" : cityCode);
        baseAreaInfo.setCreateTime(DateUtil.currentDateTime());
        return baseAreaInfoMapper.insertSelective(baseAreaInfo);
    }
}
