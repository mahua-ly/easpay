package com.kalo.easpay.service;

import com.kalo.easpay.common.dto.PullUserInfoDTO;
import com.kalo.easpay.domain.entity.UserAuthInfo;

public interface UserAuthInfoService {

    /**
     * TODO     查询授权信息
     * @Title   getUserAuthInfo
     * @author  Panguaxe
     * @param authType
     * @param openId
     * @return  com.kalo.easpay.domain.entity.UserAuthInfo
     * @date    2020/11/22 17:34
     */
    UserAuthInfo getUserAuthInfo(Integer authType,String openId);

    UserAuthInfo weChatAuth(PullUserInfoDTO pullUserInfoDTO);
}
