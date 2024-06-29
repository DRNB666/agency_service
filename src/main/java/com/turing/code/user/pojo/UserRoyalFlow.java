package com.turing.code.user.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2024-05-31
 */
@ApiModel(value="UserRoyalFlow对象", description="推广佣金流水")
@Builder
public class UserRoyalFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "审核id")
    private Long auditId;

    @ApiModelProperty(value = "受益人用户id")
    private Long userId;

    @ApiModelProperty(value = "0不计算推广业绩    1计算推广业绩 ")
    private Integer status;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "佣金余额(每笔交易后入库，报表校验可能会用到)")
    private BigDecimal royal;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    @ApiModelProperty(value = "推荐类型:1直推 2：间推")
    private Integer pushType;

    @ApiModelProperty(value = "类型：1：支出 2：收入")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "推广来源：1集体，2市级运营中心 , 3分销应用，11拼团应用，12秒杀应用，13优惠券  14开通vip(4留空)")
    private Integer sourceType;

    private Integer cityCode;

    @ApiModelProperty(value = "来源id")
    private Long sourceId;

    public Integer getId() {
        return id;
    }

    public UserRoyalFlow setId(Integer id) {
        this.id = id;
        return this;
    }
    public Long getAuditId() {
        return auditId;
    }

    public UserRoyalFlow setAuditId(Long auditId) {
        this.auditId = auditId;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public UserRoyalFlow setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserRoyalFlow setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserRoyalFlow setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserRoyalFlow setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public BigDecimal getRoyal() {
        return royal;
    }

    public UserRoyalFlow setRoyal(BigDecimal royal) {
        this.royal = royal;
        return this;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public UserRoyalFlow setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public UserRoyalFlow setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public UserRoyalFlow setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public Integer getPushType() {
        return pushType;
    }

    public UserRoyalFlow setPushType(Integer pushType) {
        this.pushType = pushType;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public UserRoyalFlow setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserRoyalFlow setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getSourceType() {
        return sourceType;
    }

    public UserRoyalFlow setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
        return this;
    }
    public Integer getCityCode() {
        return cityCode;
    }

    public UserRoyalFlow setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public Long getSourceId() {
        return sourceId;
    }

    public UserRoyalFlow setSourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public static final String ID = "user_royal_flow.id";
    public static final String AUDIT_ID = "user_royal_flow.audit_id";
    public static final String USER_ID = "user_royal_flow.user_id";
    public static final String STATUS = "user_royal_flow.status";
    public static final String UPDATE_TIME = "user_royal_flow.update_time";
    public static final String CREATE_TIME = "user_royal_flow.create_time";
    public static final String ROYAL = "user_royal_flow.royal";
    public static final String AMOUNT = "user_royal_flow.amount";
    public static final String ORDER_AMOUNT = "user_royal_flow.order_amount";
    public static final String ORDER_ID = "user_royal_flow.order_id";
    public static final String PUSH_TYPE = "user_royal_flow.push_type";
    public static final String TYPE = "user_royal_flow.type";
    public static final String REMARK = "user_royal_flow.remark";
    public static final String SOURCE_TYPE = "user_royal_flow.source_type";
    public static final String CITY_CODE = "user_royal_flow.city_code";
    public static final String SOURCE_ID = "user_royal_flow.source_id";


    @Override
    public String toString() {
        return "UserRoyalFlow{" +
            "id=" + id +
            ", auditId=" + auditId +
            ", userId=" + userId +
            ", status=" + status +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", royal=" + royal +
            ", amount=" + amount +
            ", orderAmount=" + orderAmount +
            ", orderId=" + orderId +
            ", pushType=" + pushType +
            ", type=" + type +
            ", remark=" + remark +
            ", sourceType=" + sourceType +
            ", cityCode=" + cityCode +
            ", sourceId=" + sourceId +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserRoyalFlow.ID
            ,UserRoyalFlow.AUDIT_ID
            ,UserRoyalFlow.USER_ID
            ,UserRoyalFlow.STATUS
            ,UserRoyalFlow.UPDATE_TIME
            ,UserRoyalFlow.CREATE_TIME
            ,UserRoyalFlow.ROYAL
            ,UserRoyalFlow.AMOUNT
            ,UserRoyalFlow.ORDER_AMOUNT
            ,UserRoyalFlow.ORDER_ID
            ,UserRoyalFlow.PUSH_TYPE
            ,UserRoyalFlow.TYPE
            ,UserRoyalFlow.REMARK
            ,UserRoyalFlow.SOURCE_TYPE
            ,UserRoyalFlow.CITY_CODE
            ,UserRoyalFlow.SOURCE_ID
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}