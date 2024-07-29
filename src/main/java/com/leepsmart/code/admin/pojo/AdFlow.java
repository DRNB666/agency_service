package com.leepsmart.code.admin.pojo;

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
 * @author leepsmart generator
 * @since 2024-06-05
 */
@ApiModel(value="AdFlow对象", description="平台流水")
public class AdFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "1：收入，2：支出  3冻结中  4退款")
    private Integer type;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "订单类型  1商品交易 ；	37vip；	44微信千分之六手续费 ；")
    private Integer orderType;

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public AdFlow setId(Long id) {
        this.id = id;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public AdFlow setType(Integer type) {
        this.type = type;
        return this;
    }
    public BigDecimal getMoney() {
        return money;
    }

    public AdFlow setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public AdFlow setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getOrderType() {
        return orderType;
    }

    public AdFlow setOrderType(Integer orderType) {
        this.orderType = orderType;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public AdFlow setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public AdFlow setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public AdFlow setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static final String ID = "ad_flow.id";
    public static final String TYPE = "ad_flow.type";
    public static final String MONEY = "ad_flow.money";
    public static final String STATUS = "ad_flow.status";
    public static final String ORDER_TYPE = "ad_flow.order_type";
    public static final String ORDER_ID = "ad_flow.order_id";
    public static final String REMARK = "ad_flow.remark";
    public static final String CREATE_TIME = "ad_flow.create_time";


    @Override
    public String toString() {
        return "AdFlow{" +
            "id=" + id +
            ", type=" + type +
            ", money=" + money +
            ", status=" + status +
            ", orderType=" + orderType +
            ", orderId=" + orderId +
            ", remark=" + remark +
            ", createTime=" + createTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            AdFlow.ID
            ,AdFlow.TYPE
            ,AdFlow.MONEY
            ,AdFlow.STATUS
            ,AdFlow.ORDER_TYPE
            ,AdFlow.ORDER_ID
            ,AdFlow.REMARK
            ,AdFlow.CREATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}