package com.turing.code.tenant.pojo;

import java.math.BigDecimal;
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
 * @since 2024-06-01
 */
@ApiModel(value="TenantInvite对象", description="分销员-商户店铺关系表")
public class TenantInvite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "商户id")
    private Long tenantId;

    @ApiModelProperty(value = "累计佣金")
    private BigDecimal amount;

    @ApiModelProperty(value = "累计个人业绩")
    private BigDecimal proAmount;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "上级userid  -1为没有上级(弃用字段)")
    private Long agentId;

    @ApiModelProperty(value = "等级 0普通  1黄金  2合伙人  3至尊合伙人")
    private Integer grade;

    @ApiModelProperty(value = "真实名字")
    private String realName;

    @ApiModelProperty(value = "是否内部员工  1是   0否")
    private Integer isInside;

    @ApiModelProperty(value = "不通过原因")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public TenantInvite setId(Long id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantInvite setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Long getShopId() {
        return shopId;
    }

    public TenantInvite setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public TenantInvite setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public TenantInvite setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public BigDecimal getProAmount() {
        return proAmount;
    }

    public TenantInvite setProAmount(BigDecimal proAmount) {
        this.proAmount = proAmount;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public TenantInvite setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public Long getAgentId() {
        return agentId;
    }

    public TenantInvite setAgentId(Long agentId) {
        this.agentId = agentId;
        return this;
    }
    public Integer getGrade() {
        return grade;
    }

    public TenantInvite setGrade(Integer grade) {
        this.grade = grade;
        return this;
    }
    public String getRealName() {
        return realName;
    }

    public TenantInvite setRealName(String realName) {
        this.realName = realName;
        return this;
    }
    public Integer getIsInside() {
        return isInside;
    }

    public TenantInvite setIsInside(Integer isInside) {
        this.isInside = isInside;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantInvite setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantInvite setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantInvite setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "tenant_invite.id";
    public static final String STATUS = "tenant_invite.status";
    public static final String SHOP_ID = "tenant_invite.shop_id";
    public static final String TENANT_ID = "tenant_invite.tenant_id";
    public static final String AMOUNT = "tenant_invite.amount";
    public static final String PRO_AMOUNT = "tenant_invite.pro_amount";
    public static final String USER_ID = "tenant_invite.user_id";
    public static final String AGENT_ID = "tenant_invite.agent_id";
    public static final String GRADE = "tenant_invite.grade";
    public static final String REAL_NAME = "tenant_invite.real_name";
    public static final String IS_INSIDE = "tenant_invite.is_inside";
    public static final String REMARK = "tenant_invite.remark";
    public static final String CREATE_TIME = "tenant_invite.create_time";
    public static final String UPDATE_TIME = "tenant_invite.update_time";


    @Override
    public String toString() {
        return "TenantInvite{" +
            "id=" + id +
            ", status=" + status +
            ", shopId=" + shopId +
            ", tenantId=" + tenantId +
            ", amount=" + amount +
            ", proAmount=" + proAmount +
            ", userId=" + userId +
            ", agentId=" + agentId +
            ", grade=" + grade +
            ", realName=" + realName +
            ", isInside=" + isInside +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantInvite.ID
            ,TenantInvite.STATUS
            ,TenantInvite.SHOP_ID
            ,TenantInvite.TENANT_ID
            ,TenantInvite.AMOUNT
            ,TenantInvite.PRO_AMOUNT
            ,TenantInvite.USER_ID
            ,TenantInvite.AGENT_ID
            ,TenantInvite.GRADE
            ,TenantInvite.REAL_NAME
            ,TenantInvite.IS_INSIDE
            ,TenantInvite.REMARK
            ,TenantInvite.CREATE_TIME
            ,TenantInvite.UPDATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}