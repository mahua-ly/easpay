package com.kalo.easpay.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 27日 星期五 15:31:23
 */
@Data
public class DistrictsDTO implements Serializable {

    /**
     * 城市编码/区号
     */
    private String citycode;
    /**
     * 区域编码/地区码
     */
    private String adcode;
    /**
     * 行政区名称
     */
    private String name;
    /**
     * 区域中心点/经纬度中心点
     */
    private String center;
    /**
     * 行政区划级别 country:国家; province:省份（直辖市会在province和city显示）; city:市（直辖市会在province和city显示）; district:区县; street:街道
     */
    private String level;
    /**
     * 下级行政区列表，包含district元素
     */
    private List<DistrictsDTO> districts;
    /**
     * 行政区边界坐标点 当一个行政区范围，由完全分隔两块或者多块的地块组 成，每块地的 polyline 坐标串以 | 分隔 。如北京 的 朝阳区
     */
    private String polyline;

}
