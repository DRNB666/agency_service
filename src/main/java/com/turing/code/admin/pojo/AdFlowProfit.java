package com.turing.code.admin.pojo;

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
@ApiModel(value="AdFlowProfit对象", description="平台收益流水")
public class AdFlowProfit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联订单id")
    private Long orderId;

    @ApiModelProperty(value = "1：收入，2：支出  ，3：冻结中   4：退款")
    private Integer type;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderMoney;

    @ApiModelProperty(value = "微信千分之六手续费 状态：	1商品交易 	3商家入驻保证金 	7市级应用 	8集体应用 	9用户购买升级礼包  	10集体/市级代理的活动报名费	11商家余额充值	31拼团应用 	32秒杀应用 	33优惠券应用 	34分销应用 	35广告竞价 	36同城优选	37vip")
    private Integer status;

    private String origin;

    @ApiModelProperty(value = "1商品交易 	3商家入驻保证金 	7市级应用 	8集体应用 	9用户购买升级礼包  	31拼团应用 	32秒杀应用 	33优惠券应用 	34分销应用 	35广告竞价 	36同城优选	37vip佣金	40用户提现手续费	41商家提现手续费	42集体提现手续费	43市级代理提现手续费	44微信千分之六手续费")
    private Integer originType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public AdFlowProfit setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public AdFlowProfit setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public AdFlowProfit setType(Integer type) {
        this.type = type;
        return this;
    }
    public BigDecimal getMoney() {
        return money;
    }

    public AdFlowProfit setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }
    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public AdFlowProfit setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public AdFlowProfit setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getOrigin() {
        return origin;
    }

    public AdFlowProfit setOrigin(String origin) {
        this.origin = origin;
        return this;
    }
    public Integer getOriginType() {
        return originType;
    }

    public AdFlowProfit setOriginType(Integer originType) {
        this.originType = originType;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public AdFlowProfit setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static final String ID = "ad_flow_profit.id";
    public static final String ORDER_ID = "ad_flow_profit.order_id";
    public static final String TYPE = "ad_flow_profit.type";
    public static final String MONEY = "ad_flow_profit.money";
    public static final String ORDER_MONEY = "ad_flow_profit.order_money";
    public static final String STATUS = "ad_flow_profit.status";
    public static final String ORIGIN = "ad_flow_profit.origin";
    public static final String ORIGIN_TYPE = "ad_flow_profit.origin_type";
    public static final String CREATE_TIME = "ad_flow_profit.create_time";


    @Override
    public String toString() {
        return "AdFlowProfit{" +
            "id=" + id +
            ", orderId=" + orderId +
            ", type=" + type +
            ", money=" + money +
            ", orderMoney=" + orderMoney +
            ", status=" + status +
            ", origin=" + origin +
            ", originType=" + originType +
            ", createTime=" + createTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            AdFlowProfit.ID
            ,AdFlowProfit.ORDER_ID
            ,AdFlowProfit.TYPE
            ,AdFlowProfit.MONEY
            ,AdFlowProfit.ORDER_MONEY
            ,AdFlowProfit.STATUS
            ,AdFlowProfit.ORIGIN
            ,AdFlowProfit.ORIGIN_TYPE
            ,AdFlowProfit.CREATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}