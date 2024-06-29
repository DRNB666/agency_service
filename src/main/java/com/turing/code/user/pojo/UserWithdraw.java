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
 * @since 2024-06-04
 */
@ApiModel(value="UserWithdraw对象", description="业务员推广提现表")
@Builder
public class UserWithdraw implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类型0普通推广员 1市级运营商 2商户 3集体  ")
    private Integer usType;

    private Long usId;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal money;

    @ApiModelProperty(value = "微信银行卡id")
    private Integer bankId;

    @ApiModelProperty(value = "持卡人")
    private String name;

    @ApiModelProperty(value = "卡号")
    private String cardNum;

    @ApiModelProperty(value = "银行名称")
    private String cardName;

    @ApiModelProperty(value = "0审核中 1提现成功 -1拒绝(提现失败)  2微信提现中")
    private Integer status;

    @ApiModelProperty(value = "拒绝原因")
    private String refusedReason;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "审核人（操作人姓名）")
    private String auditBy;

    @ApiModelProperty(value = "1提现到微信余额；2提现到银行卡")
    private Integer type;

    private String remark;

    private Date createTime;

    @ApiModelProperty(value = "实际扣除的提现手续费")
    private BigDecimal serviceCharge;

    @ApiModelProperty(value = "微信提现手续费")
    private BigDecimal wxServiceCharge;

    public Long getId() {
        return id;
    }

    public UserWithdraw setId(Long id) {
        this.id = id;
        return this;
    }
    public Integer getUsType() {
        return usType;
    }

    public UserWithdraw setUsType(Integer usType) {
        this.usType = usType;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public UserWithdraw setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public BigDecimal getMoney() {
        return money;
    }

    public UserWithdraw setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }
    public Integer getBankId() {
        return bankId;
    }

    public UserWithdraw setBankId(Integer bankId) {
        this.bankId = bankId;
        return this;
    }
    public String getName() {
        return name;
    }

    public UserWithdraw setName(String name) {
        this.name = name;
        return this;
    }
    public String getCardNum() {
        return cardNum;
    }

    public UserWithdraw setCardNum(String cardNum) {
        this.cardNum = cardNum;
        return this;
    }
    public String getCardName() {
        return cardName;
    }

    public UserWithdraw setCardName(String cardName) {
        this.cardName = cardName;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserWithdraw setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRefusedReason() {
        return refusedReason;
    }

    public UserWithdraw setRefusedReason(String refusedReason) {
        this.refusedReason = refusedReason;
        return this;
    }
    public Date getAuditTime() {
        return auditTime;
    }

    public UserWithdraw setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
        return this;
    }
    public String getAuditBy() {
        return auditBy;
    }

    public UserWithdraw setAuditBy(String auditBy) {
        this.auditBy = auditBy;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public UserWithdraw setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserWithdraw setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserWithdraw setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public UserWithdraw setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
        return this;
    }
    public BigDecimal getWxServiceCharge() {
        return wxServiceCharge;
    }

    public UserWithdraw setWxServiceCharge(BigDecimal wxServiceCharge) {
        this.wxServiceCharge = wxServiceCharge;
        return this;
    }

    public static final String ID = "user_withdraw.id";
    public static final String US_TYPE = "user_withdraw.us_type";
    public static final String US_ID = "user_withdraw.us_id";
    public static final String MONEY = "user_withdraw.money";
    public static final String BANK_ID = "user_withdraw.bank_id";
    public static final String NAME = "user_withdraw.name";
    public static final String CARD_NUM = "user_withdraw.card_num";
    public static final String CARD_NAME = "user_withdraw.card_name";
    public static final String STATUS = "user_withdraw.status";
    public static final String REFUSED_REASON = "user_withdraw.refused_reason";
    public static final String AUDIT_TIME = "user_withdraw.audit_time";
    public static final String AUDIT_BY = "user_withdraw.audit_by";
    public static final String TYPE = "user_withdraw.type";
    public static final String REMARK = "user_withdraw.remark";
    public static final String CREATE_TIME = "user_withdraw.create_time";
    public static final String SERVICE_CHARGE = "user_withdraw.service_charge";
    public static final String WX_SERVICE_CHARGE = "user_withdraw.wx_service_charge";


    @Override
    public String toString() {
        return "UserWithdraw{" +
            "id=" + id +
            ", usType=" + usType +
            ", usId=" + usId +
            ", money=" + money +
            ", bankId=" + bankId +
            ", name=" + name +
            ", cardNum=" + cardNum +
            ", cardName=" + cardName +
            ", status=" + status +
            ", refusedReason=" + refusedReason +
            ", auditTime=" + auditTime +
            ", auditBy=" + auditBy +
            ", type=" + type +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", serviceCharge=" + serviceCharge +
            ", wxServiceCharge=" + wxServiceCharge +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserWithdraw.ID
            ,UserWithdraw.US_TYPE
            ,UserWithdraw.US_ID
            ,UserWithdraw.MONEY
            ,UserWithdraw.BANK_ID
            ,UserWithdraw.NAME
            ,UserWithdraw.CARD_NUM
            ,UserWithdraw.CARD_NAME
            ,UserWithdraw.STATUS
            ,UserWithdraw.REFUSED_REASON
            ,UserWithdraw.AUDIT_TIME
            ,UserWithdraw.AUDIT_BY
            ,UserWithdraw.TYPE
            ,UserWithdraw.REMARK
            ,UserWithdraw.CREATE_TIME
            ,UserWithdraw.SERVICE_CHARGE
            ,UserWithdraw.WX_SERVICE_CHARGE
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}