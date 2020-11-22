/*
*
* UserInfo.java
* Copyright(C) 2019-10-29 Kalo Tech
* @date 2020-11-21
*/
package com.kalo.easpay.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * tab_user_info用户信息表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tab_user_info")
@Alias("用户信息表")
public class UserInfo implements Serializable {
    /**
     * ID
	* 列名:id;类型:INTEGER(10);允许空:false;缺省值:null
    */
    @JsonIgnore
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 用户ID
	* 列名:user_id;类型:BIGINT(19);允许空:false;缺省值:null
    */
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
	* 列名:user_name;类型:VARCHAR(32);允许空:true;缺省值:null
    */
    @Column(name = "user_name")
    private String userName;

    /**
     * 昵称
	* 列名:nick_name;类型:VARCHAR(32);允许空:true;缺省值:null
    */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 邀请码
	* 列名:invite_code;类型:VARCHAR(10);允许空:true;缺省值:null
    */
    @Column(name = "invite_code")
    private String inviteCode;

    /**
     * 手机
	* 列名:mobile;类型:VARCHAR(16);允许空:true;缺省值:null
    */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 积分
	* 列名:score;类型:INTEGER(10);允许空:true;缺省值:0
    */
    @Column(name = "score")
    private Integer score;

    /**
     * 性别：1 男；2 女；0 未知
	* 列名:sex;类型:INTEGER(10);允许空:true;缺省值:0
    */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 是否实名：0-未实名；1-已实名；默认0
	* 列名:is_auth;类型:INTEGER(10);允许空:true;缺省值:0
    */
    @Column(name = "is_auth")
    private Integer isAuth;

    /**
     * 状态：1 正常；-1-注销；0-冻结；
	* 列名:is_usable;类型:INTEGER(10);允许空:true;缺省值:1
    */
    @Column(name = "is_usable")
    private Integer isUsable;

    /**
     * 是否超管：0-非；1-是；
	* 列名:is_admin;类型:INTEGER(10);允许空:true;缺省值:0
    */
    @Column(name = "is_admin")
    private Integer isAdmin;

    /**
     * 来源：1-一键注册(手机号)；2-微信；3-QQ……
	* 列名:source;类型:INTEGER(10);允许空:true;缺省值:null
    */
    @JsonIgnore
    @Column(name = "source")
    private Integer source;

    /**
     * 头像
	* 列名:head_img;类型:VARCHAR(150);允许空:true;缺省值:null
    */
    @Column(name = "head_img")
    private String headImg;

    /**
     * 密码
	* 列名:password;类型:VARCHAR(32);允许空:true;缺省值:null
    */
    @JsonIgnore
    @Column(name = "password")
    private String password;

    /**
     * 盐值
	* 列名:salt;类型:VARCHAR(32);允许空:true;缺省值:null
    */
    @JsonIgnore
    @Column(name = "salt")
    private String salt;

    /**
     * 最后登录IP
	* 列名:last_login_ip;类型:VARCHAR(50);允许空:true;缺省值:null
    */
    @JsonIgnore
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 最后登录时间
	* 列名:last_login_time;类型:TIMESTAMP(19);允许空:true;缺省值:null
    */
    @JsonIgnore
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 创建时间
	* 列名:create_time;类型:TIMESTAMP(19);允许空:true;缺省值:null
    */
    @JsonIgnore
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
	* 列名:update_time;类型:TIMESTAMP(19);允许空:true;缺省值:null
    */
    @JsonIgnore
    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private String token;

    @Transient
    private String openid;

    /**
     * tab_user_info
     */
    private static final long serialVersionUID = 1L;

    /**

     * @return id ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(Integer id) {
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

     * @return user_name 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
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

     * @return invite_code 邀请码
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * 邀请码
     * @param inviteCode 邀请码
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    /**

     * @return mobile 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**

     * @return score 积分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 积分
     * @param score 积分
     */
    public void setScore(Integer score) {
        this.score = score;
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

     * @return is_auth 是否实名：0-未实名；1-已实名；默认0
     */
    public Integer getIsAuth() {
        return isAuth;
    }

    /**
     * 是否实名：0-未实名；1-已实名；默认0
     * @param isAuth 是否实名：0-未实名；1-已实名；默认0
     */
    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    /**

     * @return is_usable 状态：1 正常；-1-注销；0-冻结；
     */
    public Integer getIsUsable() {
        return isUsable;
    }

    /**
     * 状态：1 正常；-1-注销；0-冻结；
     * @param isUsable 状态：1 正常；-1-注销；0-冻结；
     */
    public void setIsUsable(Integer isUsable) {
        this.isUsable = isUsable;
    }

    /**

     * @return is_admin 是否超管：0-非；1-是；
     */
    public Integer getIsAdmin() {
        return isAdmin;
    }

    /**
     * 是否超管：0-非；1-是；
     * @param isAdmin 是否超管：0-非；1-是；
     */
    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**

     * @return source 来源：1-一键注册(手机号)；2-微信；3-QQ……
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 来源：1-一键注册(手机号)；2-微信；3-QQ……
     * @param source 来源：1-一键注册(手机号)；2-微信；3-QQ……
     */
    public void setSource(Integer source) {
        this.source = source;
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

     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**

     * @return salt 盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 盐值
     * @param salt 盐值
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**

     * @return last_login_ip 最后登录IP
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 最后登录IP
     * @param lastLoginIp 最后登录IP
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    /**

     * @return last_login_time 最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 最后登录时间
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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