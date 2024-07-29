package com.leepsmart.code.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author leepsmart generator
 * @since 2021-11-03
 */
@ApiModel(value = "AdProfit对象", description = "管理员-平台收益")
public class AdProfit implements Serializable {

    public static final String ID = "ad_profit.id";
    public static final String MONEY = "ad_profit.money";
    public static final String ORIGIN = "ad_profit.origin";
    public static final String REMARK = "ad_profit.remark";
    public static final String STATUS = "ad_profit.status";
    public static final String UPDATE_TIME = "ad_profit.update_time";
    public static final String CREATE_TIME = "ad_profit.create_time";
    public static final String VERSION = "ad_profit.version";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "收益金额")
    private BigDecimal money;
    @ApiModelProperty(value = "收益来源")
    private String origin;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "版本")
    @Version
    private Integer version;

    public final static String[] getFields(String... noField) {
        List<String> list = new ArrayList<>(Arrays.asList(
                AdProfit.ID
                , AdProfit.MONEY
                , AdProfit.ORIGIN
                , AdProfit.REMARK
                , AdProfit.STATUS
                , AdProfit.UPDATE_TIME
                , AdProfit.CREATE_TIME
                , AdProfit.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }

    public Integer getId() {
        return id;
    }

    public AdProfit setId(Integer id) {
        this.id = id;
        return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public AdProfit setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public AdProfit setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AdProfit setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AdProfit setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AdProfit setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AdProfit setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public AdProfit setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "AdProfit{" +
                "id=" + id +
                ", money=" + money +
                ", origin=" + origin +
                ", remark=" + remark +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", version=" + version +
                "}";
    }
}