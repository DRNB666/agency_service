package com.turing.code.system.pojo;

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
@ApiModel(value="SysBank对象", description="系统参数 设置银行卡")
@Builder
public class SysBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "银行")
    private String bank;

    @ApiModelProperty(value = "银行ID")
    private Integer bankId;

    @ApiModelProperty(value = "是否可选 1可选 2不可选")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public SysBank setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getBank() {
        return bank;
    }

    public SysBank setBank(String bank) {
        this.bank = bank;
        return this;
    }
    public Integer getBankId() {
        return bankId;
    }

    public SysBank setBankId(Integer bankId) {
        this.bankId = bankId;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public SysBank setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public SysBank setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static final String ID = "sys_bank.id";
    public static final String BANK = "sys_bank.bank";
    public static final String BANK_ID = "sys_bank.bank_id";
    public static final String STATUS = "sys_bank.status";
    public static final String CREATE_TIME = "sys_bank.create_time";


    @Override
    public String toString() {
        return "SysBank{" +
            "id=" + id +
            ", bank=" + bank +
            ", bankId=" + bankId +
            ", status=" + status +
            ", createTime=" + createTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            SysBank.ID
            ,SysBank.BANK
            ,SysBank.BANK_ID
            ,SysBank.STATUS
            ,SysBank.CREATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}