package com.kalo.easpay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @Author  Panguaxe
 * @Time    2020/11/20 19:56
 * @Desc    TODO  状态码
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AlgorithmEnum {
    /**枚举*/
    MD5("MD5");
    /**算法*/
    private String algorithm;
}
