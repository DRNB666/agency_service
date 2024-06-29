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
@ApiModel(value="TenantInviteConfig对象", description="*分销参数配置")
public class TenantInviteConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer isCheck;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "商户id")
    private Long tenantId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "黄金推广员升级条件(个人业绩")
    private Integer goldPerson;

    @ApiModelProperty(value = "黄金推广员升级条件(团队业绩")
    private Integer goldTeam;

    @ApiModelProperty(value = "合伙人推广员升级条件(个人业绩")
    private Integer partnerPerson;

    @ApiModelProperty(value = "合伙人推广员升级条件(团队业绩")
    private Integer partnerTeam;

    @ApiModelProperty(value = "至尊合伙人推广员升级条件(个人业绩")
    private Integer superPartnerPerson;

    @ApiModelProperty(value = "至尊合伙人推广员升级条件(团队业绩")
    private Integer superPartnerTeam;

    @ApiModelProperty(value = "黄金推广员获得的佣金(直推)单位%")
    private BigDecimal goldDirect;

    @ApiModelProperty(value = "黄金推广员获得的佣金(间推)单位%")
    private BigDecimal goldIndirect;

    @ApiModelProperty(value = "合伙人推广员获得的佣金(直推)单位%")
    private BigDecimal partnerDirect;

    @ApiModelProperty(value = "合伙人推广员获得的佣金(间推)单位%")
    private BigDecimal partnerIndirect;

    @ApiModelProperty(value = "至尊合伙人推广员获得的佣金(直推)单位%")
    private BigDecimal superPartnerDirect;

    @ApiModelProperty(value = "至尊合伙人推广员获得的佣金(间推)单位%")
    private BigDecimal superPartnerIndirect;

    @ApiModelProperty(value = "普通推广员获得的佣金(直推)单位%")
    private BigDecimal userDirect;

    @ApiModelProperty(value = "普通推广员获得的佣金(间推)单位%")
    private BigDecimal userIndirect;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public TenantInviteConfig setId(Long id) {
        this.id = id;
        return this;
    }
    public Integer getIsCheck() {
        return isCheck;
    }

    public TenantInviteConfig setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
        return this;
    }
    public Long getShopId() {
        return shopId;
    }

    public TenantInviteConfig setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public TenantInviteConfig setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantInviteConfig setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getGoldPerson() {
        return goldPerson;
    }

    public TenantInviteConfig setGoldPerson(Integer goldPerson) {
        this.goldPerson = goldPerson;
        return this;
    }
    public Integer getGoldTeam() {
        return goldTeam;
    }

    public TenantInviteConfig setGoldTeam(Integer goldTeam) {
        this.goldTeam = goldTeam;
        return this;
    }
    public Integer getPartnerPerson() {
        return partnerPerson;
    }

    public TenantInviteConfig setPartnerPerson(Integer partnerPerson) {
        this.partnerPerson = partnerPerson;
        return this;
    }
    public Integer getPartnerTeam() {
        return partnerTeam;
    }

    public TenantInviteConfig setPartnerTeam(Integer partnerTeam) {
        this.partnerTeam = partnerTeam;
        return this;
    }
    public Integer getSuperPartnerPerson() {
        return superPartnerPerson;
    }

    public TenantInviteConfig setSuperPartnerPerson(Integer superPartnerPerson) {
        this.superPartnerPerson = superPartnerPerson;
        return this;
    }
    public Integer getSuperPartnerTeam() {
        return superPartnerTeam;
    }

    public TenantInviteConfig setSuperPartnerTeam(Integer superPartnerTeam) {
        this.superPartnerTeam = superPartnerTeam;
        return this;
    }
    public BigDecimal getGoldDirect() {
        return goldDirect;
    }

    public TenantInviteConfig setGoldDirect(BigDecimal goldDirect) {
        this.goldDirect = goldDirect;
        return this;
    }
    public BigDecimal getGoldIndirect() {
        return goldIndirect;
    }

    public TenantInviteConfig setGoldIndirect(BigDecimal goldIndirect) {
        this.goldIndirect = goldIndirect;
        return this;
    }
    public BigDecimal getPartnerDirect() {
        return partnerDirect;
    }

    public TenantInviteConfig setPartnerDirect(BigDecimal partnerDirect) {
        this.partnerDirect = partnerDirect;
        return this;
    }
    public BigDecimal getPartnerIndirect() {
        return partnerIndirect;
    }

    public TenantInviteConfig setPartnerIndirect(BigDecimal partnerIndirect) {
        this.partnerIndirect = partnerIndirect;
        return this;
    }
    public BigDecimal getSuperPartnerDirect() {
        return superPartnerDirect;
    }

    public TenantInviteConfig setSuperPartnerDirect(BigDecimal superPartnerDirect) {
        this.superPartnerDirect = superPartnerDirect;
        return this;
    }
    public BigDecimal getSuperPartnerIndirect() {
        return superPartnerIndirect;
    }

    public TenantInviteConfig setSuperPartnerIndirect(BigDecimal superPartnerIndirect) {
        this.superPartnerIndirect = superPartnerIndirect;
        return this;
    }
    public BigDecimal getUserDirect() {
        return userDirect;
    }

    public TenantInviteConfig setUserDirect(BigDecimal userDirect) {
        this.userDirect = userDirect;
        return this;
    }
    public BigDecimal getUserIndirect() {
        return userIndirect;
    }

    public TenantInviteConfig setUserIndirect(BigDecimal userIndirect) {
        this.userIndirect = userIndirect;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantInviteConfig setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantInviteConfig setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "tenant_invite_config.id";
    public static final String IS_CHECK = "tenant_invite_config.is_check";
    public static final String SHOP_ID = "tenant_invite_config.shop_id";
    public static final String TENANT_ID = "tenant_invite_config.tenant_id";
    public static final String STATUS = "tenant_invite_config.status";
    public static final String GOLD_PERSON = "tenant_invite_config.gold_person";
    public static final String GOLD_TEAM = "tenant_invite_config.gold_team";
    public static final String PARTNER_PERSON = "tenant_invite_config.partner_person";
    public static final String PARTNER_TEAM = "tenant_invite_config.partner_team";
    public static final String SUPER_PARTNER_PERSON = "tenant_invite_config.super_partner_person";
    public static final String SUPER_PARTNER_TEAM = "tenant_invite_config.super_partner_team";
    public static final String GOLD_DIRECT = "tenant_invite_config.gold_direct";
    public static final String GOLD_INDIRECT = "tenant_invite_config.gold_indirect";
    public static final String PARTNER_DIRECT = "tenant_invite_config.partner_direct";
    public static final String PARTNER_INDIRECT = "tenant_invite_config.partner_indirect";
    public static final String SUPER_PARTNER_DIRECT = "tenant_invite_config.super_partner_direct";
    public static final String SUPER_PARTNER_INDIRECT = "tenant_invite_config.super_partner_indirect";
    public static final String USER_DIRECT = "tenant_invite_config.user_direct";
    public static final String USER_INDIRECT = "tenant_invite_config.user_indirect";
    public static final String CREATE_TIME = "tenant_invite_config.create_time";
    public static final String UPDATE_TIME = "tenant_invite_config.update_time";


    @Override
    public String toString() {
        return "TenantInviteConfig{" +
            "id=" + id +
            ", isCheck=" + isCheck +
            ", shopId=" + shopId +
            ", tenantId=" + tenantId +
            ", status=" + status +
            ", goldPerson=" + goldPerson +
            ", goldTeam=" + goldTeam +
            ", partnerPerson=" + partnerPerson +
            ", partnerTeam=" + partnerTeam +
            ", superPartnerPerson=" + superPartnerPerson +
            ", superPartnerTeam=" + superPartnerTeam +
            ", goldDirect=" + goldDirect +
            ", goldIndirect=" + goldIndirect +
            ", partnerDirect=" + partnerDirect +
            ", partnerIndirect=" + partnerIndirect +
            ", superPartnerDirect=" + superPartnerDirect +
            ", superPartnerIndirect=" + superPartnerIndirect +
            ", userDirect=" + userDirect +
            ", userIndirect=" + userIndirect +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantInviteConfig.ID
            ,TenantInviteConfig.IS_CHECK
            ,TenantInviteConfig.SHOP_ID
            ,TenantInviteConfig.TENANT_ID
            ,TenantInviteConfig.STATUS
            ,TenantInviteConfig.GOLD_PERSON
            ,TenantInviteConfig.GOLD_TEAM
            ,TenantInviteConfig.PARTNER_PERSON
            ,TenantInviteConfig.PARTNER_TEAM
            ,TenantInviteConfig.SUPER_PARTNER_PERSON
            ,TenantInviteConfig.SUPER_PARTNER_TEAM
            ,TenantInviteConfig.GOLD_DIRECT
            ,TenantInviteConfig.GOLD_INDIRECT
            ,TenantInviteConfig.PARTNER_DIRECT
            ,TenantInviteConfig.PARTNER_INDIRECT
            ,TenantInviteConfig.SUPER_PARTNER_DIRECT
            ,TenantInviteConfig.SUPER_PARTNER_INDIRECT
            ,TenantInviteConfig.USER_DIRECT
            ,TenantInviteConfig.USER_INDIRECT
            ,TenantInviteConfig.CREATE_TIME
            ,TenantInviteConfig.UPDATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}