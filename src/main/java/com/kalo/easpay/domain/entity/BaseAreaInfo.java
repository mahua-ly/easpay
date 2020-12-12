/*
*
* BaseAreaInfo.java
* Copyright(C) 2019-10-29 Kalo Tech
* @date 2020-11-27
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
import java.math.BigDecimal;
import java.util.Date;

/**
 * tab_base_area_info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tab_base_area_info")
@Alias("高德基础地区表")
public class BaseAreaInfo implements Serializable {

    /**
     * 地区唯一ID
	* 列名:area_id;类型:BIGINT(19);允许空:true;缺省值:null
    */
    @Id
    @KeySql(genId = SnowFlakeGenId.class)
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 国家标准地区码
	* 列名:area_code;类型:VARCHAR(10);允许空:true;缺省值:
    */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 地区名称
	* 列名:area_name;类型:VARCHAR(32);允许空:true;缺省值:
    */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 父级ID
	* 列名:parent_id;类型:VARCHAR(6);允许空:true;缺省值:
    */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 经度
	* 列名:longitude;类型:DECIMAL(11);允许空:true;缺省值:null
    */
    @Column(name = "longitude")
    private BigDecimal longitude;

    /**
     * 纬度
	* 列名:latitude;类型:DECIMAL(11);允许空:true;缺省值:null
    */
    @Column(name = "latitude")
    private BigDecimal latitude;

    /**
     * 地区级别：0-国家；1-省份；2-城市；3-区县；4-乡镇；5-村级
	* 列名:level;类型:INTEGER(10);允许空:true;缺省值:null
    */
    @Column(name = "level")
    private Integer level;

    /**
     * 地区级别：英文
	* 列名:level_en;类型:VARCHAR(20);允许空:true;缺省值:
    */
    @Column(name = "level_en")
    private String levelEn;

    /**
     * 区号：如 010-北京
	* 列名:area_no;类型:VARCHAR(8);允许空:true;缺省值:null
    */
    @Column(name = "area_no")
    private String areaNo;

    /**
     * 邮编
	* 列名:post_code;类型:VARCHAR(10);允许空:true;缺省值:null
    */
    @Column(name = "post_code")
    private String postCode;

    /**
     * 状态：1 正常；0 禁用
	* 列名:is_usable;类型:INTEGER(10);允许空:true;缺省值:1
    */
    @Column(name = "is_usable")
    private Integer isUsable;

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
     * tab_base_area_info
     */
    private static final long serialVersionUID = 1L;

    /**

     * @return area_id 地区唯一ID
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * 地区唯一ID
     * @param areaId 地区唯一ID
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**

     * @return area_code 国家标准地区码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 国家标准地区码
     * @param areaCode 国家标准地区码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /**

     * @return area_name 地区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 地区名称
     * @param areaName 地区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    /**

     * @return parent_id 父级ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 父级ID
     * @param parentId 父级ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**

     * @return longitude 经度
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 经度
     * @param longitude 经度
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**

     * @return latitude 纬度
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * 纬度
     * @param latitude 纬度
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**

     * @return level 地区级别：0-国家；1-省份；2-城市；3-区县；4-乡镇；5-村级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 地区级别：0-国家；1-省份；2-城市；3-区县；4-乡镇；5-村级
     * @param level 地区级别：0-国家；1-省份；2-城市；3-区县；4-乡镇；5-村级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**

     * @return level_en 地区级别：英文
     */
    public String getLevelEn() {
        return levelEn;
    }

    /**
     * 地区级别：英文
     * @param levelEn 地区级别：英文
     */
    public void setLevelEn(String levelEn) {
        this.levelEn = levelEn == null ? null : levelEn.trim();
    }

    /**

     * @return area_no 区号：如 010-北京
     */
    public String getAreaNo() {
        return areaNo;
    }

    /**
     * 区号：如 010-北京
     * @param areaNo 区号：如 010-北京
     */
    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo == null ? null : areaNo.trim();
    }

    /**

     * @return post_code 邮编
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * 邮编
     * @param postCode 邮编
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    /**

     * @return is_usable 状态：1 正常；0 禁用
     */
    public Integer getIsUsable() {
        return isUsable;
    }

    /**
     * 状态：1 正常；0 禁用
     * @param isUsable 状态：1 正常；0 禁用
     */
    public void setIsUsable(Integer isUsable) {
        this.isUsable = isUsable;
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