package com.kalo.easpay.common.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 27日 星期五 17:23:09
 */
@Data
public class SyncDistrictVO implements Serializable {

    @NotNull(message = "subdistrict cannot be null")
    @Range(min = 0, max = 3, message = "subdistrict is error")
    private Integer subdistrict;

    private String keywords;
}
