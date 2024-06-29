package com.turing.code.tenant.pojo;

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
 * @since 2024-06-27
 */
@ApiModel(value="TenantOrderDelivery对象", description="租户-订单发货")
public class TenantOrderDelivery implements Serializable {

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

    @ApiModelProperty(value = "订单详情id")
    private Long orderPayId;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "发货数量")
    private Integer count;

    @ApiModelProperty(value = "发货人id")
    private Integer operatorId;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号")
    private String logisticsNumber;

    public Integer getId() {
        return id;
    }

    public TenantOrderDelivery setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantOrderDelivery setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantOrderDelivery setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantOrderDelivery setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantOrderDelivery setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public TenantOrderDelivery setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public TenantOrderDelivery setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Long getOrderPayId() {
        return orderPayId;
    }

    public TenantOrderDelivery setOrderPayId(Long orderPayId) {
        this.orderPayId = orderPayId;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public TenantOrderDelivery setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public Integer getCount() {
        return count;
    }

    public TenantOrderDelivery setCount(Integer count) {
        this.count = count;
        return this;
    }
    public Integer getOperatorId() {
        return operatorId;
    }

    public TenantOrderDelivery setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
        return this;
    }
    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public TenantOrderDelivery setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
        return this;
    }
    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public TenantOrderDelivery setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
        return this;
    }

    public static final String ID = "tenant_order_delivery.id";
    public static final String STATUS = "tenant_order_delivery.status";
    public static final String REMARK = "tenant_order_delivery.remark";
    public static final String CREATE_TIME = "tenant_order_delivery.create_time";
    public static final String UPDATE_TIME = "tenant_order_delivery.update_time";
    public static final String TEST_TIME = "tenant_order_delivery.test_time";
    public static final String VERSION = "tenant_order_delivery.version";
    public static final String ORDER_PAY_ID = "tenant_order_delivery.order_pay_id";
    public static final String ORDER_ID = "tenant_order_delivery.order_id";
    public static final String COUNT = "tenant_order_delivery.count";
    public static final String OPERATOR_ID = "tenant_order_delivery.operator_id";
    public static final String LOGISTICS_COMPANY = "tenant_order_delivery.logistics_company";
    public static final String LOGISTICS_NUMBER = "tenant_order_delivery.logistics_number";


    @Override
    public String toString() {
        return "TenantOrderDelivery{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", orderPayId=" + orderPayId +
            ", orderId=" + orderId +
            ", count=" + count +
            ", operatorId=" + operatorId +
            ", logisticsCompany=" + logisticsCompany +
            ", logisticsNumber=" + logisticsNumber +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantOrderDelivery.ID
            ,TenantOrderDelivery.STATUS
            ,TenantOrderDelivery.REMARK
            ,TenantOrderDelivery.CREATE_TIME
            ,TenantOrderDelivery.UPDATE_TIME
            ,TenantOrderDelivery.TEST_TIME
            ,TenantOrderDelivery.VERSION
            ,TenantOrderDelivery.ORDER_PAY_ID
            ,TenantOrderDelivery.ORDER_ID
            ,TenantOrderDelivery.COUNT
            ,TenantOrderDelivery.OPERATOR_ID
            ,TenantOrderDelivery.LOGISTICS_COMPANY
            ,TenantOrderDelivery.LOGISTICS_NUMBER
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}