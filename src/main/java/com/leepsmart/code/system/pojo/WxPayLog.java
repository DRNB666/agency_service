package com.leepsmart.code.system.pojo;

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
 * @author leepsmart generator
 * @since 2024-06-23
 */
@Builder
@ApiModel(value="WxPayLog对象", description="微信支付记录")
public class WxPayLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    private String openId;

    @ApiModelProperty(value = "类型	1：普通订单	2：创建集体	3：拼团	4：秒杀订单	5：商家入驻保证金	6：激活试用商会	7：商家解冻	8：业务员升级礼包	9：升级至尊合伙人	10：试用商会	11：商家服务应用	12：活动报名费	13：商家余额充值	14：商家开通同城推送服务")
    private Integer type;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "商会入驻ID")
    private Long promoterAuditId;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal money;

    @ApiModelProperty(value = "支付备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private Integer status;

    private Date createTime;

    @ApiModelProperty(value = "微信交易id，用于查询订单和退款")
    private Long outTradeNo;

    public Long getId() {
        return id;
    }

    public WxPayLog setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public WxPayLog setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public String getOpenId() {
        return openId;
    }

    public WxPayLog setOpenId(String openId) {
        this.openId = openId;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public WxPayLog setType(Integer type) {
        this.type = type;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public WxPayLog setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public Long getPromoterAuditId() {
        return promoterAuditId;
    }

    public WxPayLog setPromoterAuditId(Long promoterAuditId) {
        this.promoterAuditId = promoterAuditId;
        return this;
    }
    public BigDecimal getMoney() {
        return money;
    }

    public WxPayLog setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public WxPayLog setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public WxPayLog setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public WxPayLog setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getOutTradeNo() {
        return outTradeNo;
    }

    public WxPayLog setOutTradeNo(Long outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public static final String ID = "wx_pay_log.id";
    public static final String USER_ID = "wx_pay_log.user_id";
    public static final String OPEN_ID = "wx_pay_log.open_id";
    public static final String TYPE = "wx_pay_log.type";
    public static final String ORDER_ID = "wx_pay_log.order_id";
    public static final String PROMOTER_AUDIT_ID = "wx_pay_log.promoter_audit_id";
    public static final String MONEY = "wx_pay_log.money";
    public static final String REMARK = "wx_pay_log.remark";
    public static final String STATUS = "wx_pay_log.status";
    public static final String CREATE_TIME = "wx_pay_log.create_time";
    public static final String OUT_TRADE_NO = "wx_pay_log.out_trade_no";


    @Override
    public String toString() {
        return "WxPayLog{" +
            "id=" + id +
            ", userId=" + userId +
            ", openId=" + openId +
            ", type=" + type +
            ", orderId=" + orderId +
            ", promoterAuditId=" + promoterAuditId +
            ", money=" + money +
            ", remark=" + remark +
            ", status=" + status +
            ", createTime=" + createTime +
            ", outTradeNo=" + outTradeNo +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            WxPayLog.ID
            ,WxPayLog.USER_ID
            ,WxPayLog.OPEN_ID
            ,WxPayLog.TYPE
            ,WxPayLog.ORDER_ID
            ,WxPayLog.PROMOTER_AUDIT_ID
            ,WxPayLog.MONEY
            ,WxPayLog.REMARK
            ,WxPayLog.STATUS
            ,WxPayLog.CREATE_TIME
            ,WxPayLog.OUT_TRADE_NO
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}