package com.turing.code.user.pojo;

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
@ApiModel(value="UserRoyalFlowPro对象", description="推广商品佣金流水")
public class UserRoyalFlowPro implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "下单用户id")
    private Long userId;

    @ApiModelProperty(value = "上级(受益人)用户id")
    private Long agentId;

    @ApiModelProperty(value = "商品id")
    private Long proId;

    @ApiModelProperty(value = "商户id")
    private Long tenantId;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "(直推+间推)总金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "直推/间推金额")
    private BigDecimal secAmount;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "推荐类型:1直推 2：间推")
    private Integer type;

    @ApiModelProperty(value = "0待付款   1冻结中   2已发放   3已退款")
    private Integer status;

    @ApiModelProperty(value = "cityCode")
    private Integer cityCode;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public UserRoyalFlowPro setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public UserRoyalFlowPro setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public Long getAgentId() {
        return agentId;
    }

    public UserRoyalFlowPro setAgentId(Long agentId) {
        this.agentId = agentId;
        return this;
    }
    public Long getProId() {
        return proId;
    }

    public UserRoyalFlowPro setProId(Long proId) {
        this.proId = proId;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public UserRoyalFlowPro setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Long getShopId() {
        return shopId;
    }

    public UserRoyalFlowPro setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public UserRoyalFlowPro setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public UserRoyalFlowPro setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public BigDecimal getSecAmount() {
        return secAmount;
    }

    public UserRoyalFlowPro setSecAmount(BigDecimal secAmount) {
        this.secAmount = secAmount;
        return this;
    }
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public UserRoyalFlowPro setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public UserRoyalFlowPro setType(Integer type) {
        this.type = type;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserRoyalFlowPro setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getCityCode() {
        return cityCode;
    }

    public UserRoyalFlowPro setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserRoyalFlowPro setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserRoyalFlowPro setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserRoyalFlowPro setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "user_royal_flow_pro.id";
    public static final String USER_ID = "user_royal_flow_pro.user_id";
    public static final String AGENT_ID = "user_royal_flow_pro.agent_id";
    public static final String PRO_ID = "user_royal_flow_pro.pro_id";
    public static final String TENANT_ID = "user_royal_flow_pro.tenant_id";
    public static final String SHOP_ID = "user_royal_flow_pro.shop_id";
    public static final String ORDER_ID = "user_royal_flow_pro.order_id";
    public static final String AMOUNT = "user_royal_flow_pro.amount";
    public static final String SEC_AMOUNT = "user_royal_flow_pro.sec_amount";
    public static final String ORDER_AMOUNT = "user_royal_flow_pro.order_amount";
    public static final String TYPE = "user_royal_flow_pro.type";
    public static final String STATUS = "user_royal_flow_pro.status";
    public static final String CITY_CODE = "user_royal_flow_pro.city_code";
    public static final String REMARK = "user_royal_flow_pro.remark";
    public static final String CREATE_TIME = "user_royal_flow_pro.create_time";
    public static final String UPDATE_TIME = "user_royal_flow_pro.update_time";


    @Override
    public String toString() {
        return "UserRoyalFlowPro{" +
            "id=" + id +
            ", userId=" + userId +
            ", agentId=" + agentId +
            ", proId=" + proId +
            ", tenantId=" + tenantId +
            ", shopId=" + shopId +
            ", orderId=" + orderId +
            ", amount=" + amount +
            ", secAmount=" + secAmount +
            ", orderAmount=" + orderAmount +
            ", type=" + type +
            ", status=" + status +
            ", cityCode=" + cityCode +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserRoyalFlowPro.ID
            ,UserRoyalFlowPro.USER_ID
            ,UserRoyalFlowPro.AGENT_ID
            ,UserRoyalFlowPro.PRO_ID
            ,UserRoyalFlowPro.TENANT_ID
            ,UserRoyalFlowPro.SHOP_ID
            ,UserRoyalFlowPro.ORDER_ID
            ,UserRoyalFlowPro.AMOUNT
            ,UserRoyalFlowPro.SEC_AMOUNT
            ,UserRoyalFlowPro.ORDER_AMOUNT
            ,UserRoyalFlowPro.TYPE
            ,UserRoyalFlowPro.STATUS
            ,UserRoyalFlowPro.CITY_CODE
            ,UserRoyalFlowPro.REMARK
            ,UserRoyalFlowPro.CREATE_TIME
            ,UserRoyalFlowPro.UPDATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}