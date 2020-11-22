package com.kalo.easpay.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 22日 星期日 16:53:34
 */
@Data
public class PullUserInfoDTO implements Serializable {

    /**用户的唯一标识*/
    private String openid;
    /**用户昵称*/
    private String nickname;
    /**用户的性别，值为1时是男性，值为2时是女性，值为0时是未知*/
    private Integer sex;
    /**语言，如中国为zh_CN*/
    private String language;
    /**普通用户个人资料填写的城市*/
    private String city;
    /**用户个人资料填写的省份*/
    private String province;
    /**国家，如中国为CN*/
    private String country;
    /**用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。*/
    private String headimgurl;
    /**只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。*/
    private String unionid;
    /**用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）*/
    private List<String> privilege;

    /**用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息*/
    private Integer subscribe;
    /**用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间*/
    private Long subscribe_time;
    /**关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_OTHERS 其他*/
    private String subscribe_scene;
}
