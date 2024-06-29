package com.turing.code.user.pojo;

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
@ApiModel(value="UserBankCard对象", description="用户银行卡表")
@Builder
public class UserBankCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long usId;

    @ApiModelProperty(value = "卡名")
    private String cardName;

    @ApiModelProperty(value = "卡类型（预留）")
    private Integer type;

    @ApiModelProperty(value = "卡号")
    private String cardNum;

    @ApiModelProperty(value = "持卡人")
    private String name;

    @ApiModelProperty(value = "微信银行卡id")
    private Integer bankId;

    @ApiModelProperty(value = "0正常")
    private Integer status;

    private String remark;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public UserBankCard setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public UserBankCard setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public String getCardName() {
        return cardName;
    }

    public UserBankCard setCardName(String cardName) {
        this.cardName = cardName;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public UserBankCard setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getCardNum() {
        return cardNum;
    }

    public UserBankCard setCardNum(String cardNum) {
        this.cardNum = cardNum;
        return this;
    }
    public String getName() {
        return name;
    }

    public UserBankCard setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getBankId() {
        return bankId;
    }

    public UserBankCard setBankId(Integer bankId) {
        this.bankId = bankId;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserBankCard setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserBankCard setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserBankCard setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static final String ID = "user_bank_card.id";
    public static final String US_ID = "user_bank_card.us_id";
    public static final String CARD_NAME = "user_bank_card.card_name";
    public static final String TYPE = "user_bank_card.type";
    public static final String CARD_NUM = "user_bank_card.card_num";
    public static final String NAME = "user_bank_card.name";
    public static final String BANK_ID = "user_bank_card.bank_id";
    public static final String STATUS = "user_bank_card.status";
    public static final String REMARK = "user_bank_card.remark";
    public static final String CREATE_TIME = "user_bank_card.create_time";


    @Override
    public String toString() {
        return "UserBankCard{" +
            "id=" + id +
            ", usId=" + usId +
            ", cardName=" + cardName +
            ", type=" + type +
            ", cardNum=" + cardNum +
            ", name=" + name +
            ", bankId=" + bankId +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserBankCard.ID
            ,UserBankCard.US_ID
            ,UserBankCard.CARD_NAME
            ,UserBankCard.TYPE
            ,UserBankCard.CARD_NUM
            ,UserBankCard.NAME
            ,UserBankCard.BANK_ID
            ,UserBankCard.STATUS
            ,UserBankCard.REMARK
            ,UserBankCard.CREATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}