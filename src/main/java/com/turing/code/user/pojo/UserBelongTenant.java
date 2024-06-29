package com.turing.code.user.pojo;

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
 * @since 2024-06-18
 */
@ApiModel(value="UserBelongTenant对象", description="用户-所属租户店铺")
public class UserBelongTenant implements Serializable {

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

    @ApiModelProperty(value = "用户id")
    private Long usId;

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    public Integer getId() {
        return id;
    }

    public UserBelongTenant setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserBelongTenant setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserBelongTenant setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserBelongTenant setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserBelongTenant setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public UserBelongTenant setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public UserBelongTenant setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public UserBelongTenant setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public Integer getTenantId() {
        return tenantId;
    }

    public UserBelongTenant setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Long getShopId() {
        return shopId;
    }

    public UserBelongTenant setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public static final String ID = "user_belong_tenant.id";
    public static final String STATUS = "user_belong_tenant.status";
    public static final String REMARK = "user_belong_tenant.remark";
    public static final String CREATE_TIME = "user_belong_tenant.create_time";
    public static final String UPDATE_TIME = "user_belong_tenant.update_time";
    public static final String TEST_TIME = "user_belong_tenant.test_time";
    public static final String VERSION = "user_belong_tenant.version";
    public static final String US_ID = "user_belong_tenant.us_id";
    public static final String TENANT_ID = "user_belong_tenant.tenant_id";
    public static final String SHOP_ID = "user_belong_tenant.shop_id";


    @Override
    public String toString() {
        return "UserBelongTenant{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", usId=" + usId +
            ", tenantId=" + tenantId +
            ", shopId=" + shopId +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserBelongTenant.ID
            ,UserBelongTenant.STATUS
            ,UserBelongTenant.REMARK
            ,UserBelongTenant.CREATE_TIME
            ,UserBelongTenant.UPDATE_TIME
            ,UserBelongTenant.TEST_TIME
            ,UserBelongTenant.VERSION
            ,UserBelongTenant.US_ID
            ,UserBelongTenant.TENANT_ID
            ,UserBelongTenant.SHOP_ID
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}