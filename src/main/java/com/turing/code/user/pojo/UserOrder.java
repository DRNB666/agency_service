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
 * @since 2024-06-29
 */
@ApiModel(value="UserOrder对象", description="用户-订单")
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态	0：未支付	1：待发货	2：待收货	3：已收货	4：已完成（定时器过了售后日期转变为已完成）	-1：已取消	")
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

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付方式 1会员储蓄卡 2线下支付")
    private Integer payType;

    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    @ApiModelProperty(value = "运费")
    private BigDecimal freightAmount;

    @ApiModelProperty(value = "安装费用")
    private BigDecimal installAmount;

    @ApiModelProperty(value = "发货种类：1：部分发货 2：全部发货")
    private Integer deliveryCategory;

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    @ApiModelProperty(value = "收款状态 0：待收款 1：部分收款 2：收款完成")
    private Integer receiptStatus;

    @ApiModelProperty(value = "收货人联系方式")
    private String phone;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "订单类型：0：普通订单 1：会员礼包订单")
    private Integer type;

    @ApiModelProperty(value = "收货地址")
    private String address;

    @ApiModelProperty(value = "收货人")
    private String consignee;

    public Integer getId() {
        return id;
    }

    public UserOrder setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserOrder setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserOrder setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserOrder setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserOrder setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public UserOrder setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public UserOrder setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public UserOrder setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public UserOrder setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
    public Date getPayTime() {
        return payTime;
    }

    public UserOrder setPayTime(Date payTime) {
        this.payTime = payTime;
        return this;
    }
    public Integer getPayType() {
        return payType;
    }

    public UserOrder setPayType(Integer payType) {
        this.payType = payType;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public UserOrder setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public BigDecimal getFreightAmount() {
        return freightAmount;
    }

    public UserOrder setFreightAmount(BigDecimal freightAmount) {
        this.freightAmount = freightAmount;
        return this;
    }
    public BigDecimal getInstallAmount() {
        return installAmount;
    }

    public UserOrder setInstallAmount(BigDecimal installAmount) {
        this.installAmount = installAmount;
        return this;
    }
    public Integer getDeliveryCategory() {
        return deliveryCategory;
    }

    public UserOrder setDeliveryCategory(Integer deliveryCategory) {
        this.deliveryCategory = deliveryCategory;
        return this;
    }
    public Integer getTenantId() {
        return tenantId;
    }

    public UserOrder setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Integer getReceiptStatus() {
        return receiptStatus;
    }

    public UserOrder setReceiptStatus(Integer receiptStatus) {
        this.receiptStatus = receiptStatus;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public UserOrder setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public Integer getCount() {
        return count;
    }

    public UserOrder setCount(Integer count) {
        this.count = count;
        return this;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public UserOrder setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public UserOrder setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getAddress() {
        return address;
    }

    public UserOrder setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getConsignee() {
        return consignee;
    }

    public UserOrder setConsignee(String consignee) {
        this.consignee = consignee;
        return this;
    }

    public static final String ID = "user_order.id";
    public static final String STATUS = "user_order.status";
    public static final String REMARK = "user_order.remark";
    public static final String CREATE_TIME = "user_order.create_time";
    public static final String UPDATE_TIME = "user_order.update_time";
    public static final String TEST_TIME = "user_order.test_time";
    public static final String VERSION = "user_order.version";
    public static final String US_ID = "user_order.us_id";
    public static final String TOTAL_AMOUNT = "user_order.total_amount";
    public static final String PAY_TIME = "user_order.pay_time";
    public static final String PAY_TYPE = "user_order.pay_type";
    public static final String ORDER_ID = "user_order.order_id";
    public static final String FREIGHT_AMOUNT = "user_order.freight_amount";
    public static final String INSTALL_AMOUNT = "user_order.install_amount";
    public static final String DELIVERY_CATEGORY = "user_order.delivery_category";
    public static final String TENANT_ID = "user_order.tenant_id";
    public static final String RECEIPT_STATUS = "user_order.receipt_status";
    public static final String PHONE = "user_order.phone";
    public static final String COUNT = "user_order.count";
    public static final String PAY_AMOUNT = "user_order.pay_amount";
    public static final String TYPE = "user_order.type";
    public static final String ADDRESS = "user_order.address";
    public static final String CONSIGNEE = "user_order.consignee";


    @Override
    public String toString() {
        return "UserOrder{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", usId=" + usId +
            ", totalAmount=" + totalAmount +
            ", payTime=" + payTime +
            ", payType=" + payType +
            ", orderId=" + orderId +
            ", freightAmount=" + freightAmount +
            ", installAmount=" + installAmount +
            ", deliveryCategory=" + deliveryCategory +
            ", tenantId=" + tenantId +
            ", receiptStatus=" + receiptStatus +
            ", phone=" + phone +
            ", count=" + count +
            ", payAmount=" + payAmount +
            ", type=" + type +
            ", address=" + address +
            ", consignee=" + consignee +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserOrder.ID
            ,UserOrder.STATUS
            ,UserOrder.REMARK
            ,UserOrder.CREATE_TIME
            ,UserOrder.UPDATE_TIME
            ,UserOrder.TEST_TIME
            ,UserOrder.VERSION
            ,UserOrder.US_ID
            ,UserOrder.TOTAL_AMOUNT
            ,UserOrder.PAY_TIME
            ,UserOrder.PAY_TYPE
            ,UserOrder.ORDER_ID
            ,UserOrder.FREIGHT_AMOUNT
            ,UserOrder.INSTALL_AMOUNT
            ,UserOrder.DELIVERY_CATEGORY
            ,UserOrder.TENANT_ID
            ,UserOrder.RECEIPT_STATUS
            ,UserOrder.PHONE
            ,UserOrder.COUNT
            ,UserOrder.PAY_AMOUNT
            ,UserOrder.TYPE
            ,UserOrder.ADDRESS
            ,UserOrder.CONSIGNEE
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}