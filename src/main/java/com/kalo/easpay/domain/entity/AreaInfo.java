/*
*
* AreaInfo.java
* Copyright(C) 2019-10-29 Kalo Tech
* @date 2020-12-12
*/
package com.kalo.easpay.domain.entity;

import com.kalo.easpay.utils.generator.SnowFlakeGenId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * area_info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "area_info")
@Alias("")
public class AreaInfo implements Serializable {
    /**
     * ID
	* 列名:area_id;类型:BIGINT(19);允许空:false;缺省值:null
    */
    @Id
    @KeySql(genId = SnowFlakeGenId.class)
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 地区码
	* 列名:area_code;类型:VARCHAR(16);允许空:true;缺省值:
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
	* 列名:parent_id;类型:VARCHAR(16);允许空:true;缺省值:
    */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 地区级别：0-国家；1-省份；2-城市；3-区县；4-乡镇/街道；5-村庄
	* 列名:level;类型:INTEGER(10);允许空:true;缺省值:null
    */
    @Column(name = "level")
    private Integer level;

    /**
     * 状态：1 正常；0 禁用
	* 列名:is_usable;类型:INTEGER(10);允许空:true;缺省值:1
    */
    @Column(name = "is_usable")
    private Integer isUsable;

    /**
     * 区号：如 010-北京
	* 列名:area_no;类型:VARCHAR(10);允许空:true;缺省值:
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
     * area_info
     */
    private static final long serialVersionUID = 1L;

    /**

     * @return area_id ID
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * ID
     * @param areaId ID
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**

     * @return area_code 地区码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 地区码
     * @param areaCode 地区码
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

     * @return level 地区级别：0-国家；1-省份；2-城市；3-区县；4-乡镇/街道；5-村庄
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 地区级别：0-国家；1-省份；2-城市；3-区县；4-乡镇/街道；5-村庄
     * @param level 地区级别：0-国家；1-省份；2-城市；3-区县；4-乡镇/街道；5-村庄
     */
    public void setLevel(Integer level) {
        this.level = level;
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