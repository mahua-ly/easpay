package com.kalo.easpay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AreaLevelEnum {

    /**
     * 枚举
     */
    COUNTRY(0, "country","国家"),
    PROVINCE(1, "province","省级"),
    CITY(2, "city","市级"),
    DISTRICT(3, "district","区县"),
    STREET(4, "street","乡镇/街道"),
    VILLAGE(5, "village","村级/村委");

    /**
     * 级别
     */
    private Integer level;
    /**
     * 英文级别
     */
    private String levelEn;
    /**描述*/
    private String desc;

    public static Integer level(String levelEn) {
        Integer level = null;
        AreaLevelEnum[] values = values();
        for (AreaLevelEnum areaLevelEnum : values) {
            if (areaLevelEnum.getLevelEn().equals(levelEn)) {
                level = areaLevelEnum.getLevel();
                break;
            }
        }
        return level;
    }

    public static String desc(String levelEn) {
        String desc = "";
        AreaLevelEnum[] values = values();
        for (AreaLevelEnum areaLevelEnum : values) {
            if (areaLevelEnum.getLevelEn().equals(levelEn)) {
                desc = areaLevelEnum.getDesc();
                break;
            }
        }
        return desc;
    }

}
