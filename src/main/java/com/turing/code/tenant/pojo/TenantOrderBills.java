package com.turing.code.tenant.pojo;

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
 * @since 2024-06-20
 */
@ApiModel(value="TenantOrderBills对象", description="租户-订单收款账单")
public class TenantOrderBills implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态	0待审核	1审核通过	-1驳回")
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

    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    @ApiModelProperty(value = "收款金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "收款日期")
    private Date receiptTime;

    @ApiModelProperty(value = "登记人id")
    private Integer registrationId;

    @ApiModelProperty(value = "审核人id")
    private Integer auditId;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "驳回原因")
    private String rejectedReason;

    public Integer getId() {
        return id;
    }

    public TenantOrderBills setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantOrderBills setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantOrderBills setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantOrderBills setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantOrderBills setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public TenantOrderBills setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public TenantOrderBills setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public TenantOrderBills setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public TenantOrderBills setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public Date getReceiptTime() {
        return receiptTime;
    }

    public TenantOrderBills setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
        return this;
    }
    public Integer getRegistrationId() {
        return registrationId;
    }

    public TenantOrderBills setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
        return this;
    }
    public Integer getAuditId() {
        return auditId;
    }

    public TenantOrderBills setAuditId(Integer auditId) {
        this.auditId = auditId;
        return this;
    }
    public Date getAuditTime() {
        return auditTime;
    }

    public TenantOrderBills setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
        return this;
    }
    public String getRejectedReason() {
        return rejectedReason;
    }

    public TenantOrderBills setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
        return this;
    }

    public static final String ID = "tenant_order_bills.id";
    public static final String STATUS = "tenant_order_bills.status";
    public static final String REMARK = "tenant_order_bills.remark";
    public static final String CREATE_TIME = "tenant_order_bills.create_time";
    public static final String UPDATE_TIME = "tenant_order_bills.update_time";
    public static final String TEST_TIME = "tenant_order_bills.test_time";
    public static final String VERSION = "tenant_order_bills.version";
    public static final String ORDER_ID = "tenant_order_bills.order_id";
    public static final String AMOUNT = "tenant_order_bills.amount";
    public static final String RECEIPT_TIME = "tenant_order_bills.receipt_time";
    public static final String REGISTRATION_ID = "tenant_order_bills.registration_id";
    public static final String AUDIT_ID = "tenant_order_bills.audit_id";
    public static final String AUDIT_TIME = "tenant_order_bills.audit_time";
    public static final String REJECTED_REASON = "tenant_order_bills.rejected_reason";


    @Override
    public String toString() {
        return "TenantOrderBills{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", orderId=" + orderId +
            ", amount=" + amount +
            ", receiptTime=" + receiptTime +
            ", registrationId=" + registrationId +
            ", auditId=" + auditId +
            ", auditTime=" + auditTime +
            ", rejectedReason=" + rejectedReason +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantOrderBills.ID
            ,TenantOrderBills.STATUS
            ,TenantOrderBills.REMARK
            ,TenantOrderBills.CREATE_TIME
            ,TenantOrderBills.UPDATE_TIME
            ,TenantOrderBills.TEST_TIME
            ,TenantOrderBills.VERSION
            ,TenantOrderBills.ORDER_ID
            ,TenantOrderBills.AMOUNT
            ,TenantOrderBills.RECEIPT_TIME
            ,TenantOrderBills.REGISTRATION_ID
            ,TenantOrderBills.AUDIT_ID
            ,TenantOrderBills.AUDIT_TIME
            ,TenantOrderBills.REJECTED_REASON
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}