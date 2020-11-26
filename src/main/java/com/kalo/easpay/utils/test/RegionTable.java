package com.kalo.easpay.utils.test;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 26日 星期四 16:28:59
 */
@Data
public class RegionTable implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 地区代码
     */
    private String code;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 等级类型
     */
    private String type;

    /**
     * 城乡分类代码
     */
    private String classification;

    /**
     * region_table
     */
    private static final long serialVersionUID = 1L;
}
