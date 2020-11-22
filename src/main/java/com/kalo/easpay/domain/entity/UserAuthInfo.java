/*
*
* UserAuthInfo.java
* Copyright(C) 2019-10-29 Kalo Tech
* @date 2020-11-22
*/
package com.kalo.easpay.domain.entity;

import com.kalo.easpay.utils.generator.SnowFlakeGenId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tab_user_auth_info用户授权信息表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tab_user_auth_info")
@Alias("用户授权信息表")
public class UserAuthInfo implements Serializable {
    /**
     * ID
	* 列名:id;类型:INTEGER(10);允许空:false;缺省值:null
    */
    @Id
    @KeySql(genId = SnowFlakeGenId.class)
    @Column(name = "id")
    private Long id;

    /**
     * 用户ID
	* 列名:user_id;类型:BIGINT(19);允许空:true;缺省值:null
    */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 授权类型：1-微信；2-QQ；3-支付宝；4-头条；5-抖音……
	* 列名:auth_type;类型:INTEGER(10);允许空:true;缺省值:null
    */
    @Column(name = "auth_type")
    private Integer authType;

    /**
     * 唯一标识
	* 列名:unionid;类型:VARCHAR(32);允许空:true;缺省值:null
    */
    @Column(name = "unionid")
    private String unionid;

    /**
     * openid
	* 列名:openid;类型:VARCHAR(32);允许空:true;缺省值:null
    */
    @Column(name = "openid")
    private String openid;

    /**
     * 昵称
	* 列名:nick_name;类型:VARCHAR(32);允许空:true;缺省值:null
    */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 绑定手机号
	* 列名:bind_mobile;类型:VARCHAR(16);允许空:true;缺省值:null
    */
    @Column(name = "bind_mobile")
    private String bindMobile;

    /**
     * 性别：1 男；2 女；0 未知
	* 列名:sex;类型:INTEGER(10);允许空:true;缺省值:0
    */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 头像
	* 列名:head_img;类型:VARCHAR(150);允许空:true;缺省值:
    */
    @Column(name = "head_img")
    private String headImg;

    /**
     * 关注的渠道来源：ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_ LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
	* 列名:subscribe_scene;类型:VARCHAR(32);允许空:true;缺省值:
    */
    @Column(name = "subscribe_scene")
    private String subscribeScene;

    /**
     * 省份
	* 列名:province;类型:VARCHAR(32);允许空:true;缺省值:
    */
    @Column(name = "province")
    private String province;

    /**
     * 城市
	* 列名:city;类型:VARCHAR(32);允许空:true;缺省值:
    */
    @Column(name = "city")
    private String city;

    /**
     * 国家：CN-中国；
	* 列名:country;类型:VARCHAR(32);允许空:true;缺省值:
    */
    @Column(name = "country")
    private String country;

    /**
     * 创建时间
	* 列名:create_time;类型:TIMESTAMP(19);允许空:true;缺省值:null
    */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
	* 列名:update_time;类型:TIMESTAMP(19);允许空:true;缺省值:null
    */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * tab_user_auth_info
     */
    private static final long serialVersionUID = 1L;

    /**

     * @return id ID
     */
    public Long getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**

     * @return user_id 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**

     * @return auth_type 授权类型：1-微信；2-QQ；3-支付宝；4-头条；5-抖音……
     */
    public Integer getAuthType() {
        return authType;
    }

    /**
     * 授权类型：1-微信；2-QQ；3-支付宝；4-头条；5-抖音……
     * @param authType 授权类型：1-微信；2-QQ；3-支付宝；4-头条；5-抖音……
     */
    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    /**

     * @return unionid 唯一标识
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * 唯一标识
     * @param unionid 唯一标识
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    /**

     * @return openid openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * openid
     * @param openid openid
     */
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    /**

     * @return nick_name 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 昵称
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**

     * @return bind_mobile 绑定手机号
     */
    public String getBindMobile() {
        return bindMobile;
    }

    /**
     * 绑定手机号
     * @param bindMobile 绑定手机号
     */
    public void setBindMobile(String bindMobile) {
        this.bindMobile = bindMobile == null ? null : bindMobile.trim();
    }

    /**

     * @return sex 性别：1 男；2 女；0 未知
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 性别：1 男；2 女；0 未知
     * @param sex 性别：1 男；2 女；0 未知
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**

     * @return head_img 头像
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * 头像
     * @param headImg 头像
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    /**

     * @return subscribe_scene 关注的渠道来源：ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_ LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
     */
    public String getSubscribeScene() {
        return subscribeScene;
    }

    /**
     * 关注的渠道来源：ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_ LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
     * @param subscribeScene 关注的渠道来源：ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_ LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
     */
    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene == null ? null : subscribeScene.trim();
    }

    /**

     * @return province 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 省份
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**

     * @return city 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 城市
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**

     * @return country 国家：CN-中国；
     */
    public String getCountry() {
        return country;
    }

    /**
     * 国家：CN-中国；
     * @param country 国家：CN-中国；
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**

     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**

     * @return update_time 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}