package com.kalo.easpay.service;

import java.io.IOException;

/**
 * @author Panguaxe
 * @version 1.0.0
 * @date 2020年 12月 12日 星期六 19:45:26
 */
public interface AreaInfoService {

    int syncAreaInfo(String areaPrefix) throws IOException, InterruptedException;
}
