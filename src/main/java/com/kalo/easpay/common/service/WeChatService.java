package com.kalo.easpay.common.service;

import com.kalo.easpay.common.dto.AccessTokenDTO;

public interface WeChatService {

    /**
     * TODO     获取 AccessToken
     * @Title   getAccessToken
     * @author  Panguaxe
     * @return  com.kalo.easpay.common.dto.AccessTokenDTO
     * @date    2020/11/22 21:47
     */
    AccessTokenDTO getAccessToken();
}
