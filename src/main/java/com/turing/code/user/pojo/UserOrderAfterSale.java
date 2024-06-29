package com.turing.code.user.pojo;

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
 * @since 2024-06-13
 */
@ApiModel(value="UserOrderAfterSale对象", description="用户-订单售后")
public class UserOrderAfterSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private Date testTime;

    @ApiModelProperty(value = "版本号")
    @Version
    private Long version;

    @ApiModelProperty(value = "用户id")
    private Long usId;

    @ApiModelProperty(value = "订单编号")
    private Integer orderId;

    @ApiModelProperty(value = "1：售后申请中	2：拒绝售后	3：售后完成")
    private Integer afterSellStatus;

    @ApiModelProperty(value = "售后类型：1：仅退款 2：退货退款")
    private Integer afterSellType;

    @ApiModelProperty(value = "售后种类：1：部分售后 2：全部售后")
    private Integer afterSellCategory;

    @ApiModelProperty(value = "售后原因")
    private String reason;

    @ApiModelProperty(value = "照片")
    private String image;

    @ApiModelProperty(value = "商品id")
    private Long proId;

    public Integer getId() {
        return id;
    }

    public UserOrderAfterSale setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserOrderAfterSale setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserOrderAfterSale setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserOrderAfterSale setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserOrderAfterSale setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public UserOrderAfterSale setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public UserOrderAfterSale setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public UserOrderAfterSale setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public UserOrderAfterSale setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }
    public Integer getAfterSellStatus() {
        return afterSellStatus;
    }

    public UserOrderAfterSale setAfterSellStatus(Integer afterSellStatus) {
        this.afterSellStatus = afterSellStatus;
        return this;
    }
    public Integer getAfterSellType() {
        return afterSellType;
    }

    public UserOrderAfterSale setAfterSellType(Integer afterSellType) {
        this.afterSellType = afterSellType;
        return this;
    }
    public Integer getAfterSellCategory() {
        return afterSellCategory;
    }

    public UserOrderAfterSale setAfterSellCategory(Integer afterSellCategory) {
        this.afterSellCategory = afterSellCategory;
        return this;
    }
    public String getReason() {
        return reason;
    }

    public UserOrderAfterSale setReason(String reason) {
        this.reason = reason;
        return this;
    }
    public String getImage() {
        return image;
    }

    public UserOrderAfterSale setImage(String image) {
        this.image = image;
        return this;
    }
    public Long getProId() {
        return proId;
    }

    public UserOrderAfterSale setProId(Long proId) {
        this.proId = proId;
        return this;
    }

    public static final String ID = "user_order_after_sale.id";
    public static final String STATUS = "user_order_after_sale.status";
    public static final String REMARK = "user_order_after_sale.remark";
    public static final String CREATE_TIME = "user_order_after_sale.create_time";
    public static final String UPDATE_TIME = "user_order_after_sale.update_time";
    public static final String TEST_TIME = "user_order_after_sale.test_time";
    public static final String VERSION = "user_order_after_sale.version";
    public static final String US_ID = "user_order_after_sale.us_id";
    public static final String ORDER_ID = "user_order_after_sale.order_id";
    public static final String AFTER_SELL_STATUS = "user_order_after_sale.after_sell_status";
    public static final String AFTER_SELL_TYPE = "user_order_after_sale.after_sell_type";
    public static final String AFTER_SELL_CATEGORY = "user_order_after_sale.after_sell_category";
    public static final String REASON = "user_order_after_sale.reason";
    public static final String IMAGE = "user_order_after_sale.image";
    public static final String PRO_ID = "user_order_after_sale.pro_id";


    @Override
    public String toString() {
        return "UserOrderAfterSale{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", usId=" + usId +
            ", orderId=" + orderId +
            ", afterSellStatus=" + afterSellStatus +
            ", afterSellType=" + afterSellType +
            ", afterSellCategory=" + afterSellCategory +
            ", reason=" + reason +
            ", image=" + image +
            ", proId=" + proId +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserOrderAfterSale.ID
            ,UserOrderAfterSale.STATUS
            ,UserOrderAfterSale.REMARK
            ,UserOrderAfterSale.CREATE_TIME
            ,UserOrderAfterSale.UPDATE_TIME
            ,UserOrderAfterSale.TEST_TIME
            ,UserOrderAfterSale.VERSION
            ,UserOrderAfterSale.US_ID
            ,UserOrderAfterSale.ORDER_ID
            ,UserOrderAfterSale.AFTER_SELL_STATUS
            ,UserOrderAfterSale.AFTER_SELL_TYPE
            ,UserOrderAfterSale.AFTER_SELL_CATEGORY
            ,UserOrderAfterSale.REASON
            ,UserOrderAfterSale.IMAGE
            ,UserOrderAfterSale.PRO_ID
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}