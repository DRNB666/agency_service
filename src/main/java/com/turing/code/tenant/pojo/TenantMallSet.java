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
 * @since 2024-06-12
 */
@ApiModel(value="TenantMallSet对象", description="租户-商城配置")
public class TenantMallSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private Date testTime;

    @ApiModelProperty(value = "版本号")
    @Version
    private Long version;

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    @ApiModelProperty(value = "自动确认收货天数")
    private Integer afterSaleDay;

    @ApiModelProperty(value = "商城单位1积分 2人民币")
    private Integer unit;

    @ApiModelProperty(value = "是否允许提现1允许 0不允许")
    private Integer allowWithdrawal;

    @ApiModelProperty(value = "是否开启会员卡分销1开启 0不开启")
    private Integer vipSales;

    @ApiModelProperty(value = "支付方式1会员储蓄卡 2线下支付")
    private Integer payType;

    public Integer getId() {
        return id;
    }

    public TenantMallSet setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantMallSet setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantMallSet setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantMallSet setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantMallSet setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public TenantMallSet setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public TenantMallSet setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Integer getTenantId() {
        return tenantId;
    }

    public TenantMallSet setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Integer getAfterSaleDay() {
        return afterSaleDay;
    }

    public TenantMallSet setAfterSaleDay(Integer afterSaleDay) {
        this.afterSaleDay = afterSaleDay;
        return this;
    }
    public Integer getUnit() {
        return unit;
    }

    public TenantMallSet setUnit(Integer unit) {
        this.unit = unit;
        return this;
    }
    public Integer getAllowWithdrawal() {
        return allowWithdrawal;
    }

    public TenantMallSet setAllowWithdrawal(Integer allowWithdrawal) {
        this.allowWithdrawal = allowWithdrawal;
        return this;
    }
    public Integer getVipSales() {
        return vipSales;
    }

    public TenantMallSet setVipSales(Integer vipSales) {
        this.vipSales = vipSales;
        return this;
    }
    public Integer getPayType() {
        return payType;
    }

    public TenantMallSet setPayType(Integer payType) {
        this.payType = payType;
        return this;
    }

    public static final String ID = "tenant_mall_set.id";
    public static final String STATUS = "tenant_mall_set.status";
    public static final String REMARK = "tenant_mall_set.remark";
    public static final String CREATE_TIME = "tenant_mall_set.create_time";
    public static final String UPDATE_TIME = "tenant_mall_set.update_time";
    public static final String TEST_TIME = "tenant_mall_set.test_time";
    public static final String VERSION = "tenant_mall_set.version";
    public static final String TENANT_ID = "tenant_mall_set.tenant_id";
    public static final String AFTER_SALE_DAY = "tenant_mall_set.after_sale_day";
    public static final String UNIT = "tenant_mall_set.unit";
    public static final String ALLOW_WITHDRAWAL = "tenant_mall_set.allow_withdrawal";
    public static final String VIP_SALES = "tenant_mall_set.vip_sales";
    public static final String PAY_TYPE = "tenant_mall_set.pay_type";


    @Override
    public String toString() {
        return "TenantMallSet{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", tenantId=" + tenantId +
            ", afterSaleDay=" + afterSaleDay +
            ", unit=" + unit +
            ", allowWithdrawal=" + allowWithdrawal +
            ", vipSales=" + vipSales +
            ", payType=" + payType +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantMallSet.ID
            ,TenantMallSet.STATUS
            ,TenantMallSet.REMARK
            ,TenantMallSet.CREATE_TIME
            ,TenantMallSet.UPDATE_TIME
            ,TenantMallSet.TEST_TIME
            ,TenantMallSet.VERSION
            ,TenantMallSet.TENANT_ID
            ,TenantMallSet.AFTER_SALE_DAY
            ,TenantMallSet.UNIT
            ,TenantMallSet.ALLOW_WITHDRAWAL
            ,TenantMallSet.VIP_SALES
            ,TenantMallSet.PAY_TYPE
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}