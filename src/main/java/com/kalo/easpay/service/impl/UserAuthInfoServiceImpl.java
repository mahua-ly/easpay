package com.kalo.easpay.service.impl;

import com.kalo.easpay.common.dto.PullUserInfoDTO;
import com.kalo.easpay.domain.entity.UserAuthInfo;
import com.kalo.easpay.domain.mapper.UserAuthInfoMapper;
import com.kalo.easpay.service.UserAuthInfoService;
import com.kalo.easpay.utils.date.DateUtil;
import com.kalo.easpay.utils.obj.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 16:31:16
 */
@Slf4j
@Service
public class UserAuthInfoServiceImpl implements UserAuthInfoService {

    private final UserAuthInfoMapper userAuthInfoMapper;

    @Autowired
    public UserAuthInfoServiceImpl(UserAuthInfoMapper userAuthInfoMapper) {
        this.userAuthInfoMapper = userAuthInfoMapper;
    }

    /**
     * TODO     查询授权信息
     * @Title   getUserAuthInfo
     * @author  Panguaxe
     * @param authType
     * @param openId
     * @return  com.kalo.easpay.domain.entity.UserAuthInfo
     * @date    2020/11/22 17:34
     */
    @Override
    public UserAuthInfo getUserAuthInfo(Integer authType, String openId) {
        if (StringUtils.isNotBlank(openId)) {
            Example example = new Example(UserAuthInfo.class);
            example.createCriteria()
                    .andEqualTo("authType", authType)
                    .andEqualTo("openid", openId);
            List<UserAuthInfo> userAuthInfos = userAuthInfoMapper.selectByExample(example);
            return userAuthInfos.size() > 0 ? userAuthInfos.get(0) : null;
        }
        return null;
    }

    @Override
    public UserAuthInfo weChatAuth(PullUserInfoDTO pullUserInfoDTO) {
        UserAuthInfo userAuthInfo = getUserAuthInfo(1, pullUserInfoDTO.getOpenid());
        boolean isAdd = ObjectUtil.isBlank(userAuthInfo);
        Date currentDateTime = DateUtil.currentDateTime();
        userAuthInfo = isAdd ? new UserAuthInfo() : userAuthInfo;
        userAuthInfo.setAuthType(1);
        userAuthInfo.setNickName(pullUserInfoDTO.getNickname());
        userAuthInfo.setSex(pullUserInfoDTO.getSex());
        userAuthInfo.setCity(pullUserInfoDTO.getCity());
        userAuthInfo.setProvince(pullUserInfoDTO.getProvince());
        userAuthInfo.setCountry(pullUserInfoDTO.getCountry());
        userAuthInfo.setHeadImg(pullUserInfoDTO.getHeadimgurl());
        //授权信息：不存在-新增; 存在-更新
        if (isAdd) {
            userAuthInfo.setOpenid(pullUserInfoDTO.getOpenid());
            userAuthInfo.setCreateTime(currentDateTime);
            userAuthInfoMapper.insertSelective(userAuthInfo);
        } else {
            userAuthInfo.setUpdateTime(currentDateTime);
            userAuthInfoMapper.updateByPrimaryKeySelective(userAuthInfo);
        }
        return userAuthInfo;
    }
}
