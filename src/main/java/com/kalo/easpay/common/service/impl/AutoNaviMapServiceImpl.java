package com.kalo.easpay.common.service.impl;

import com.kalo.easpay.common.service.AutoNaviMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

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

    @Value("${}")
    private String apiKey;

    @Value("${}")
    private String districtUrl;



}
