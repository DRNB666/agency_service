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
 * @since 2024-06-25
 */
@ApiModel(value="TenantVipLevel对象", description="租户-会员等级")
public class TenantVipLevel implements Serializable {

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

    @ApiModelProperty(value = "图标标识")
    private String icon;

    @ApiModelProperty(value = "会员等级名称")
    private String name;

    @ApiModelProperty(value = "有效期：单位/天")
    private Integer day;

    @ApiModelProperty(value = "折扣比例 原价*比例 0.1-0.99")
    private Double discountRatio;

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    @ApiModelProperty(value = "普通推广员直推")
    private Double ptDirect;

    @ApiModelProperty(value = "普通推广员间推")
    private Double ptIndirect;

    @ApiModelProperty(value = "黄金推广员直推")
    private Double hjDirect;

    @ApiModelProperty(value = "黄金推广员间推")
    private Double hjIndirect;

    @ApiModelProperty(value = "合伙人直推")
    private Double hhrDirect;

    @ApiModelProperty(value = "合伙人间推")
    private Double hhrIndirect;

    @ApiModelProperty(value = "至尊合伙人直推")
    private Double zzDirect;

    @ApiModelProperty(value = "至尊合伙人间推")
    private Double zzIndirect;

    public Integer getId() {
        return id;
    }

    public TenantVipLevel setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantVipLevel setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantVipLevel setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantVipLevel setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantVipLevel setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public TenantVipLevel setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public TenantVipLevel setVersion(Long version) {
        this.version = version;
        return this;
    }
    public String getIcon() {
        return icon;
    }

    public TenantVipLevel setIcon(String icon) {
        this.icon = icon;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantVipLevel setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getDay() {
        return day;
    }

    public TenantVipLevel setDay(Integer day) {
        this.day = day;
        return this;
    }
    public Double getDiscountRatio() {
        return discountRatio;
    }

    public TenantVipLevel setDiscountRatio(Double discountRatio) {
        this.discountRatio = discountRatio;
        return this;
    }
    public Integer getTenantId() {
        return tenantId;
    }

    public TenantVipLevel setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Double getPtDirect() {
        return ptDirect;
    }

    public TenantVipLevel setPtDirect(Double ptDirect) {
        this.ptDirect = ptDirect;
        return this;
    }
    public Double getPtIndirect() {
        return ptIndirect;
    }

    public TenantVipLevel setPtIndirect(Double ptIndirect) {
        this.ptIndirect = ptIndirect;
        return this;
    }
    public Double getHjDirect() {
        return hjDirect;
    }

    public TenantVipLevel setHjDirect(Double hjDirect) {
        this.hjDirect = hjDirect;
        return this;
    }
    public Double getHjIndirect() {
        return hjIndirect;
    }

    public TenantVipLevel setHjIndirect(Double hjIndirect) {
        this.hjIndirect = hjIndirect;
        return this;
    }
    public Double getHhrDirect() {
        return hhrDirect;
    }

    public TenantVipLevel setHhrDirect(Double hhrDirect) {
        this.hhrDirect = hhrDirect;
        return this;
    }
    public Double getHhrIndirect() {
        return hhrIndirect;
    }

    public TenantVipLevel setHhrIndirect(Double hhrIndirect) {
        this.hhrIndirect = hhrIndirect;
        return this;
    }
    public Double getZzDirect() {
        return zzDirect;
    }

    public TenantVipLevel setZzDirect(Double zzDirect) {
        this.zzDirect = zzDirect;
        return this;
    }
    public Double getZzIndirect() {
        return zzIndirect;
    }

    public TenantVipLevel setZzIndirect(Double zzIndirect) {
        this.zzIndirect = zzIndirect;
        return this;
    }

    public static final String ID = "tenant_vip_level.id";
    public static final String STATUS = "tenant_vip_level.status";
    public static final String REMARK = "tenant_vip_level.remark";
    public static final String CREATE_TIME = "tenant_vip_level.create_time";
    public static final String UPDATE_TIME = "tenant_vip_level.update_time";
    public static final String TEST_TIME = "tenant_vip_level.test_time";
    public static final String VERSION = "tenant_vip_level.version";
    public static final String ICON = "tenant_vip_level.icon";
    public static final String NAME = "tenant_vip_level.name";
    public static final String DAY = "tenant_vip_level.day";
    public static final String DISCOUNT_RATIO = "tenant_vip_level.discount_ratio";
    public static final String TENANT_ID = "tenant_vip_level.tenant_id";
    public static final String PT_DIRECT = "tenant_vip_level.pt_direct";
    public static final String PT_INDIRECT = "tenant_vip_level.pt_indirect";
    public static final String HJ_DIRECT = "tenant_vip_level.hj_direct";
    public static final String HJ_INDIRECT = "tenant_vip_level.hj_indirect";
    public static final String HHR_DIRECT = "tenant_vip_level.hhr_direct";
    public static final String HHR_INDIRECT = "tenant_vip_level.hhr_indirect";
    public static final String ZZ_DIRECT = "tenant_vip_level.zz_direct";
    public static final String ZZ_INDIRECT = "tenant_vip_level.zz_indirect";


    @Override
    public String toString() {
        return "TenantVipLevel{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", icon=" + icon +
            ", name=" + name +
            ", day=" + day +
            ", discountRatio=" + discountRatio +
            ", tenantId=" + tenantId +
            ", ptDirect=" + ptDirect +
            ", ptIndirect=" + ptIndirect +
            ", hjDirect=" + hjDirect +
            ", hjIndirect=" + hjIndirect +
            ", hhrDirect=" + hhrDirect +
            ", hhrIndirect=" + hhrIndirect +
            ", zzDirect=" + zzDirect +
            ", zzIndirect=" + zzIndirect +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantVipLevel.ID
            ,TenantVipLevel.STATUS
            ,TenantVipLevel.REMARK
            ,TenantVipLevel.CREATE_TIME
            ,TenantVipLevel.UPDATE_TIME
            ,TenantVipLevel.TEST_TIME
            ,TenantVipLevel.VERSION
            ,TenantVipLevel.ICON
            ,TenantVipLevel.NAME
            ,TenantVipLevel.DAY
            ,TenantVipLevel.DISCOUNT_RATIO
            ,TenantVipLevel.TENANT_ID
            ,TenantVipLevel.PT_DIRECT
            ,TenantVipLevel.PT_INDIRECT
            ,TenantVipLevel.HJ_DIRECT
            ,TenantVipLevel.HJ_INDIRECT
            ,TenantVipLevel.HHR_DIRECT
            ,TenantVipLevel.HHR_INDIRECT
            ,TenantVipLevel.ZZ_DIRECT
            ,TenantVipLevel.ZZ_INDIRECT
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}