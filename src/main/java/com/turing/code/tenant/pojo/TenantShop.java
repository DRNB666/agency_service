package com.turing.code.tenant.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author turing generator
 * @since 2024-06-01
 */

@ApiModel(value="TenantShop对象", description="租户-店铺信息表")
public class TenantShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商家id")
    private Long tenantId;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "维度")
    private String latitude;

    @ApiModelProperty(value = "店铺地址")
    private String address;

    @ApiModelProperty(value = "店铺详情")
    private String content;

    @ApiModelProperty(value = "店铺头像")
    private String shopPhoto;

    @ApiModelProperty(value = "封面")
    private String banner;

    @ApiModelProperty(value = "简介")
    private String overview;

    @ApiModelProperty(value = "店铺名")
    private String name;

    @ApiModelProperty(value = "关注量")
    private Integer attention;

    @ApiModelProperty(value = "点赞量")
    @TableField(value = "`like`")
    private Integer like;

    @ApiModelProperty(value = "浏览量")
    private Integer pv;

    @ApiModelProperty(value = "状态：1开店，2闭店，10已删除")
    private Integer status;

    private String remark;

    private Date createTime;

    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "店铺开始营业时间")
    private String startTime;

    @ApiModelProperty(value = "店铺打样时间")
    private String endTime;

    @ApiModelProperty(value = "店铺 热线电话")
    private String phone;

    @ApiModelProperty(value = "店铺地址图标")
    private String icon;

    @ApiModelProperty(value = "评分最大10分，两位小数")
    private BigDecimal score;

    public Long getId() {
        return id;
    }

    public TenantShop setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public TenantShop setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public String getLongitude() {
        return longitude;
    }

    public TenantShop setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }
    public String getLatitude() {
        return latitude;
    }

    public TenantShop setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }
    public String getAddress() {
        return address;
    }

    public TenantShop setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getContent() {
        return content;
    }

    public TenantShop setContent(String content) {
        this.content = content;
        return this;
    }
    public String getShopPhoto() {
        return shopPhoto;
    }

    public TenantShop setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
        return this;
    }
    public String getBanner() {
        return banner;
    }

    public TenantShop setBanner(String banner) {
        this.banner = banner;
        return this;
    }
    public String getOverview() {
        return overview;
    }

    public TenantShop setOverview(String overview) {
        this.overview = overview;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantShop setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getAttention() {
        return attention;
    }

    public TenantShop setAttention(Integer attention) {
        this.attention = attention;
        return this;
    }
    public Integer getLike() {
        return like;
    }

    public TenantShop setLike(Integer like) {
        this.like = like;
        return this;
    }
    public Integer getPv() {
        return pv;
    }

    public TenantShop setPv(Integer pv) {
        this.pv = pv;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantShop setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantShop setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantShop setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public TenantShop setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }
    public String getStartTime() {
        return startTime;
    }

    public TenantShop setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }
    public String getEndTime() {
        return endTime;
    }

    public TenantShop setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public TenantShop setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public String getIcon() {
        return icon;
    }

    public TenantShop setIcon(String icon) {
        this.icon = icon;
        return this;
    }
    public BigDecimal getScore() {
        return score;
    }

    public TenantShop setScore(BigDecimal score) {
        this.score = score;
        return this;
    }

    public static final String ID = "tenant_shop.id";
    public static final String TENANT_ID = "tenant_shop.tenant_id";
    public static final String LONGITUDE = "tenant_shop.longitude";
    public static final String LATITUDE = "tenant_shop.latitude";
    public static final String ADDRESS = "tenant_shop.address";
    public static final String CONTENT = "tenant_shop.content";
    public static final String SHOP_PHOTO = "tenant_shop.shop_photo";
    public static final String BANNER = "tenant_shop.banner";
    public static final String OVERVIEW = "tenant_shop.overview";
    public static final String NAME = "tenant_shop.name";
    public static final String ATTENTION = "tenant_shop.attention";
    public static final String LIKE = "tenant_shop.like";
    public static final String PV = "tenant_shop.pv";
    public static final String STATUS = "tenant_shop.status";
    public static final String REMARK = "tenant_shop.remark";
    public static final String CREATE_TIME = "tenant_shop.create_time";
    public static final String CATEGORY_ID = "tenant_shop.category_id";
    public static final String START_TIME = "tenant_shop.start_time";
    public static final String END_TIME = "tenant_shop.end_time";
    public static final String PHONE = "tenant_shop.phone";
    public static final String ICON = "tenant_shop.icon";
    public static final String SCORE = "tenant_shop.score";


    @Override
    public String toString() {
        return "TenantShop{" +
            "id=" + id +
            ", tenantId=" + tenantId +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            ", address=" + address +
            ", content=" + content +
            ", shopPhoto=" + shopPhoto +
            ", banner=" + banner +
            ", overview=" + overview +
            ", name=" + name +
            ", attention=" + attention +
            ", like=" + like +
            ", pv=" + pv +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", categoryId=" + categoryId +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", phone=" + phone +
            ", icon=" + icon +
            ", score=" + score +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantShop.ID
            ,TenantShop.TENANT_ID
            ,TenantShop.LONGITUDE
            ,TenantShop.LATITUDE
            ,TenantShop.ADDRESS
            ,TenantShop.CONTENT
            ,TenantShop.SHOP_PHOTO
            ,TenantShop.BANNER
            ,TenantShop.OVERVIEW
            ,TenantShop.NAME
            ,TenantShop.ATTENTION
            ,TenantShop.LIKE
            ,TenantShop.PV
            ,TenantShop.STATUS
            ,TenantShop.REMARK
            ,TenantShop.CREATE_TIME
            ,TenantShop.CATEGORY_ID
            ,TenantShop.START_TIME
            ,TenantShop.END_TIME
            ,TenantShop.PHONE
            ,TenantShop.ICON
            ,TenantShop.SCORE
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}