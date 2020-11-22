package com.kalo.easpay.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.kalo.easpay.common.dto.AccessTokenDTO;
import com.kalo.easpay.common.service.WeChatService;
import com.kalo.easpay.utils.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 22日 星期日 21:38:10
 */
@Slf4j
@Service
@RefreshScope
public class WeChatServiceImpl implements WeChatService {

    /**
     * 微信开发者APPID
     */
    @Value("${wechat.pub.plat.AppID}")
    private String AppID;
    /**
     * 微信开发者AppSecret
     */
    @Value("${wechat.pub.plat.AppSecret}")
    private String AppSecret;
    /**
     * 获取 access_token URL
     */
    @Value("${weChat.pub.plat.getAccessTokenURL}")
    private String getAccessTokenURL;
    /**
     * TODO     获取 AccessToken
     * @Title   getAccessToken
     * @author  Panguaxe
     * @return  com.kalo.easpay.common.dto.AccessTokenDTO
     * @date    2020/11/22 21:47
     */
    @Override
    public AccessTokenDTO getAccessToken() {
        //1. 拼装获取 AccessToken 请求地址
        String accessTokenURL = getAccessTokenURL.replace("APPID", AppID).replace("APPSECRET", AppSecret);
        log.info("获取 AccessToken 请求URL：{}",accessTokenURL);
        //2. 发送Http请求获取 AccessToken
        String response = HttpUtil.doGetAuth(accessTokenURL);
        //3. 返回AccessToken返回对象
        return JSON.parseObject(response, AccessTokenDTO.class);
    }
}
