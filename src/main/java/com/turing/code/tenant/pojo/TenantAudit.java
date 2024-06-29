package com.turing.code.tenant.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author turing generator
 * @since 2024-06-23
 */
@ApiModel(value="TenantAudit对象", description="商家入驻集体审核表")
public class TenantAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long usId;

    private String account;

    private String password;

    @ApiModelProperty(value = "店铺经度")
    private String longitude;

    @ApiModelProperty(value = "店铺维度")
    private String latitude;

    @ApiModelProperty(value = "商家姓名")
    private String name;

    @ApiModelProperty(value = "店铺名")
    private String shopName;

    private Long industryId;

    @ApiModelProperty(value = "行业名称")
    private String industryName;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "市级行政代码")
    private String cityCode;

    @ApiModelProperty(value = "区名称")
    private String areaName;

    @ApiModelProperty(value = "区级行政代码")
    private String areaCode;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "6位邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "0审核中 1已通过 -1拒绝")
    private Integer status;

    @ApiModelProperty(value = "拒绝原因")
    private String reason;

    @ApiModelProperty(value = "审核人")
    private String auditBy;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "0未支付 1已支付 2邀请码入驻")
    private Integer payStatus;

    private String remark;

    @ApiModelProperty(value = "手机号")
    private String phone;

    public Long getId() {
        return id;
    }

    public TenantAudit setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public TenantAudit setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public String getAccount() {
        return account;
    }

    public TenantAudit setAccount(String account) {
        this.account = account;
        return this;
    }
    public String getPassword() {
        return password;
    }

    public TenantAudit setPassword(String password) {
        this.password = password;
        return this;
    }
    public String getLongitude() {
        return longitude;
    }

    public TenantAudit setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }
    public String getLatitude() {
        return latitude;
    }

    public TenantAudit setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantAudit setName(String name) {
        this.name = name;
        return this;
    }
    public String getShopName() {
        return shopName;
    }

    public TenantAudit setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }
    public Long getIndustryId() {
        return industryId;
    }

    public TenantAudit setIndustryId(Long industryId) {
        this.industryId = industryId;
        return this;
    }
    public String getIndustryName() {
        return industryName;
    }

    public TenantAudit setIndustryName(String industryName) {
        this.industryName = industryName;
        return this;
    }
    public String getCityName() {
        return cityName;
    }

    public TenantAudit setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }
    public String getCityCode() {
        return cityCode;
    }

    public TenantAudit setCityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getAreaName() {
        return areaName;
    }

    public TenantAudit setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }
    public String getAreaCode() {
        return areaCode;
    }

    public TenantAudit setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }
    public String getAddress() {
        return address;
    }

    public TenantAudit setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getInviteCode() {
        return inviteCode;
    }

    public TenantAudit setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantAudit setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getReason() {
        return reason;
    }

    public TenantAudit setReason(String reason) {
        this.reason = reason;
        return this;
    }
    public String getAuditBy() {
        return auditBy;
    }

    public TenantAudit setAuditBy(String auditBy) {
        this.auditBy = auditBy;
        return this;
    }
    public Date getAuditTime() {
        return auditTime;
    }

    public TenantAudit setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
        return this;
    }
    public Date getPayTime() {
        return payTime;
    }

    public TenantAudit setPayTime(Date payTime) {
        this.payTime = payTime;
        return this;
    }
    public Integer getPayStatus() {
        return payStatus;
    }

    public TenantAudit setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantAudit setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public TenantAudit setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public static final String ID = "tenant_audit.id";
    public static final String US_ID = "tenant_audit.us_id";
    public static final String ACCOUNT = "tenant_audit.account";
    public static final String PASSWORD = "tenant_audit.password";
    public static final String LONGITUDE = "tenant_audit.longitude";
    public static final String LATITUDE = "tenant_audit.latitude";
    public static final String NAME = "tenant_audit.name";
    public static final String SHOP_NAME = "tenant_audit.shop_name";
    public static final String INDUSTRY_ID = "tenant_audit.industry_id";
    public static final String INDUSTRY_NAME = "tenant_audit.industry_name";
    public static final String CITY_NAME = "tenant_audit.city_name";
    public static final String CITY_CODE = "tenant_audit.city_code";
    public static final String AREA_NAME = "tenant_audit.area_name";
    public static final String AREA_CODE = "tenant_audit.area_code";
    public static final String ADDRESS = "tenant_audit.address";
    public static final String INVITE_CODE = "tenant_audit.invite_code";
    public static final String STATUS = "tenant_audit.status";
    public static final String REASON = "tenant_audit.reason";
    public static final String AUDIT_BY = "tenant_audit.audit_by";
    public static final String AUDIT_TIME = "tenant_audit.audit_time";
    public static final String PAY_TIME = "tenant_audit.pay_time";
    public static final String PAY_STATUS = "tenant_audit.pay_status";
    public static final String REMARK = "tenant_audit.remark";
    public static final String PHONE = "tenant_audit.phone";


    @Override
    public String toString() {
        return "TenantAudit{" +
            "id=" + id +
            ", usId=" + usId +
            ", account=" + account +
            ", password=" + password +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            ", name=" + name +
            ", shopName=" + shopName +
            ", industryId=" + industryId +
            ", industryName=" + industryName +
            ", cityName=" + cityName +
            ", cityCode=" + cityCode +
            ", areaName=" + areaName +
            ", areaCode=" + areaCode +
            ", address=" + address +
            ", inviteCode=" + inviteCode +
            ", status=" + status +
            ", reason=" + reason +
            ", auditBy=" + auditBy +
            ", auditTime=" + auditTime +
            ", payTime=" + payTime +
            ", payStatus=" + payStatus +
            ", remark=" + remark +
            ", phone=" + phone +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantAudit.ID
            ,TenantAudit.US_ID
            ,TenantAudit.ACCOUNT
            ,TenantAudit.PASSWORD
            ,TenantAudit.LONGITUDE
            ,TenantAudit.LATITUDE
            ,TenantAudit.NAME
            ,TenantAudit.SHOP_NAME
            ,TenantAudit.INDUSTRY_ID
            ,TenantAudit.INDUSTRY_NAME
            ,TenantAudit.CITY_NAME
            ,TenantAudit.CITY_CODE
            ,TenantAudit.AREA_NAME
            ,TenantAudit.AREA_CODE
            ,TenantAudit.ADDRESS
            ,TenantAudit.INVITE_CODE
            ,TenantAudit.STATUS
            ,TenantAudit.REASON
            ,TenantAudit.AUDIT_BY
            ,TenantAudit.AUDIT_TIME
            ,TenantAudit.PAY_TIME
            ,TenantAudit.PAY_STATUS
            ,TenantAudit.REMARK
            ,TenantAudit.PHONE
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}