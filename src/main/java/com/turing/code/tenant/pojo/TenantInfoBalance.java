package com.turing.code.tenant.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2024-06-22
 */
@Builder
@ApiModel(value="TenantInfoBalance对象", description="商家 余额表")
public class TenantInfoBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商户Id")
    private Long tenantId;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "历史总额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "冻结金额")
    private BigDecimal frozenAmount;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    public Long getId() {
        return id;
    }

    public TenantInfoBalance setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public TenantInfoBalance setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    public TenantInfoBalance setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public TenantInfoBalance setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public TenantInfoBalance setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantInfoBalance setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantInfoBalance setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public static final String ID = "tenant_info_balance.id";
    public static final String TENANT_ID = "tenant_info_balance.tenant_id";
    public static final String BALANCE = "tenant_info_balance.balance";
    public static final String TOTAL_AMOUNT = "tenant_info_balance.total_amount";
    public static final String FROZEN_AMOUNT = "tenant_info_balance.frozen_amount";
    public static final String STATUS = "tenant_info_balance.status";
    public static final String REMARK = "tenant_info_balance.remark";


    @Override
    public String toString() {
        return "TenantInfoBalance{" +
            "id=" + id +
            ", tenantId=" + tenantId +
            ", balance=" + balance +
            ", totalAmount=" + totalAmount +
            ", frozenAmount=" + frozenAmount +
            ", status=" + status +
            ", remark=" + remark +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantInfoBalance.ID
            ,TenantInfoBalance.TENANT_ID
            ,TenantInfoBalance.BALANCE
            ,TenantInfoBalance.TOTAL_AMOUNT
            ,TenantInfoBalance.FROZEN_AMOUNT
            ,TenantInfoBalance.STATUS
            ,TenantInfoBalance.REMARK
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}