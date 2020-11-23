package com.kalo.easpay.common.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 23日 星期一 11:22:45
 */
@Getter
@Service
@RefreshScope
public class WeChatConfig {

    /**
     * 微信开发者APPID
     */
    @Value("${wechat.pub.plat.AppID}")
    private String appId;
    /**
     * 微信开发者AppSecret
     */
    @Value("${wechat.pub.plat.AppSecret}")
    private String appSecret;
    /**
     * 微信授权登录获取code URL
     */
    @Value("${wechat.pub.plat.authorize.authCodeURL}")
    private String authCodeUrl;
    /**
     * 微信授权登录回调获取信息URL
     */
    @Value("${wechat.pub.plat.authorize.weChatAuthCallBackURL}")
    private String weChatAuthCallBackUrl;
    /**
     * 网页授权 AccessToken 请求URL
     */
    @Value("${wechat.pub.plat.authorize.getAccessTokenURL}")
    private String getAccessTokenUrl;
    /**
     * 拉取用户微信信息URL
     */
    @Value("${wechat.pub.plat.authorize.snsUserinfoURL}")
    private String snsUserinfoUrl;
}
