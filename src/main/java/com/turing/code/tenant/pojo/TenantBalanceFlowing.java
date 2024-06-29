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
 * @since 2024-06-22
 */
@ApiModel(value="TenantBalanceFlowing对象", description="商家 余额流水信息表")
public class TenantBalanceFlowing implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    @ApiModelProperty(value = "店铺名")
    private String shopName;

    @ApiModelProperty(value = "商家Id")
    private Long tenantId;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "交易类型  1：收入  2支出  3 收入冻结中  4退款")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态   ")
    private Integer status;

    @ApiModelProperty(value = "cityCode")
    private Integer cityCode;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public TenantBalanceFlowing setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public TenantBalanceFlowing setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public String getShopName() {
        return shopName;
    }

    public TenantBalanceFlowing setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public TenantBalanceFlowing setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public TenantBalanceFlowing setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public TenantBalanceFlowing setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantBalanceFlowing setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantBalanceFlowing setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getCityCode() {
        return cityCode;
    }

    public TenantBalanceFlowing setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantBalanceFlowing setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static final String ID = "tenant_balance_flowing.id";
    public static final String ORDER_ID = "tenant_balance_flowing.order_id";
    public static final String SHOP_NAME = "tenant_balance_flowing.shop_name";
    public static final String TENANT_ID = "tenant_balance_flowing.tenant_id";
    public static final String AMOUNT = "tenant_balance_flowing.amount";
    public static final String TYPE = "tenant_balance_flowing.type";
    public static final String REMARK = "tenant_balance_flowing.remark";
    public static final String STATUS = "tenant_balance_flowing.status";
    public static final String CITY_CODE = "tenant_balance_flowing.city_code";
    public static final String CREATE_TIME = "tenant_balance_flowing.create_time";


    @Override
    public String toString() {
        return "TenantBalanceFlowing{" +
            "id=" + id +
            ", orderId=" + orderId +
            ", shopName=" + shopName +
            ", tenantId=" + tenantId +
            ", amount=" + amount +
            ", type=" + type +
            ", remark=" + remark +
            ", status=" + status +
            ", cityCode=" + cityCode +
            ", createTime=" + createTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantBalanceFlowing.ID
            ,TenantBalanceFlowing.ORDER_ID
            ,TenantBalanceFlowing.SHOP_NAME
            ,TenantBalanceFlowing.TENANT_ID
            ,TenantBalanceFlowing.AMOUNT
            ,TenantBalanceFlowing.TYPE
            ,TenantBalanceFlowing.REMARK
            ,TenantBalanceFlowing.STATUS
            ,TenantBalanceFlowing.CITY_CODE
            ,TenantBalanceFlowing.CREATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}