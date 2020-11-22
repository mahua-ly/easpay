package com.kalo.easpay.common.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 17:19:02
 */
@Data
public class AppLoginVO implements Serializable {

    @NotNull(message = "type cannot be null")
    @Range(min = 1, max = 6, message = "type is error")
    private Integer type;
    private String mobile;
    private String username;
    private String password;
    private String code;
    private String clientIp;
}
