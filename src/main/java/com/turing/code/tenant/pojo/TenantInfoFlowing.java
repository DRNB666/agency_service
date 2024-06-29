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
 * @since 2024-06-05
 */
@ApiModel(value="TenantInfoFlowing对象", description="商家 流水信息表")
public class TenantInfoFlowing implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商家Id")
    private Long tenantId;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    private Long orderId;

    @ApiModelProperty(value = "交易类型  1：收入，2：支出  3冻结中  4退款 ")
    private Integer type;

    @ApiModelProperty(value = "交易描述")
    private String descr;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态  1用户购买商品  2平台抽佣支出  3业务员直推间推支出  4余额充值  5提现  9竞拍  11拼团 12秒杀 13优惠券 14分销")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public TenantInfoFlowing setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public TenantInfoFlowing setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public TenantInfoFlowing setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public TenantInfoFlowing setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public TenantInfoFlowing setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getDescr() {
        return descr;
    }

    public TenantInfoFlowing setDescr(String descr) {
        this.descr = descr;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantInfoFlowing setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantInfoFlowing setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantInfoFlowing setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static final String ID = "tenant_info_flowing.id";
    public static final String TENANT_ID = "tenant_info_flowing.tenant_id";
    public static final String AMOUNT = "tenant_info_flowing.amount";
    public static final String ORDER_ID = "tenant_info_flowing.order_id";
    public static final String TYPE = "tenant_info_flowing.type";
    public static final String DESCR = "tenant_info_flowing.descr";
    public static final String REMARK = "tenant_info_flowing.remark";
    public static final String STATUS = "tenant_info_flowing.status";
    public static final String CREATE_TIME = "tenant_info_flowing.create_time";


    @Override
    public String toString() {
        return "TenantInfoFlowing{" +
            "id=" + id +
            ", tenantId=" + tenantId +
            ", amount=" + amount +
            ", orderId=" + orderId +
            ", type=" + type +
            ", descr=" + descr +
            ", remark=" + remark +
            ", status=" + status +
            ", createTime=" + createTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantInfoFlowing.ID
            ,TenantInfoFlowing.TENANT_ID
            ,TenantInfoFlowing.AMOUNT
            ,TenantInfoFlowing.ORDER_ID
            ,TenantInfoFlowing.TYPE
            ,TenantInfoFlowing.DESCR
            ,TenantInfoFlowing.REMARK
            ,TenantInfoFlowing.STATUS
            ,TenantInfoFlowing.CREATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}