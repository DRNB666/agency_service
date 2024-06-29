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
 * @since 2024-06-25
 */
@ApiModel(value="UserVipFlow对象", description="用户-会员储蓄卡流水")
public class UserVipFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态	1:收入	-1:支出")
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

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "收入来源:1租户充值 2租户开通会员礼包赠送")
    private Integer source;

    @ApiModelProperty(value = "租户id(收入记录)")
    private Integer tenantId;

    @ApiModelProperty(value = "礼包id(收入记录)")
    private Integer vipLevelId;

    @ApiModelProperty(value = "支出流向:1用户购买商品")
    private String flowed;

    @ApiModelProperty(value = "订单编号(支出记录)")
    private Long orderId;

    public Integer getId() {
        return id;
    }

    public UserVipFlow setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserVipFlow setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserVipFlow setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserVipFlow setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserVipFlow setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public UserVipFlow setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public UserVipFlow setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public UserVipFlow setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public UserVipFlow setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    public Integer getSource() {
        return source;
    }

    public UserVipFlow setSource(Integer source) {
        this.source = source;
        return this;
    }
    public Integer getTenantId() {
        return tenantId;
    }

    public UserVipFlow setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Integer getVipLevelId() {
        return vipLevelId;
    }

    public UserVipFlow setVipLevelId(Integer vipLevelId) {
        this.vipLevelId = vipLevelId;
        return this;
    }
    public String getFlowed() {
        return flowed;
    }

    public UserVipFlow setFlowed(String flowed) {
        this.flowed = flowed;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public UserVipFlow setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public static final String ID = "user_vip_flow.id";
    public static final String STATUS = "user_vip_flow.status";
    public static final String REMARK = "user_vip_flow.remark";
    public static final String CREATE_TIME = "user_vip_flow.create_time";
    public static final String UPDATE_TIME = "user_vip_flow.update_time";
    public static final String TEST_TIME = "user_vip_flow.test_time";
    public static final String VERSION = "user_vip_flow.version";
    public static final String US_ID = "user_vip_flow.us_id";
    public static final String AMOUNT = "user_vip_flow.amount";
    public static final String SOURCE = "user_vip_flow.source";
    public static final String TENANT_ID = "user_vip_flow.tenant_id";
    public static final String VIP_LEVEL_ID = "user_vip_flow.vip_level_id";
    public static final String FLOWED = "user_vip_flow.flowed";
    public static final String ORDER_ID = "user_vip_flow.order_id";


    @Override
    public String toString() {
        return "UserVipFlow{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", usId=" + usId +
            ", amount=" + amount +
            ", source=" + source +
            ", tenantId=" + tenantId +
            ", vipLevelId=" + vipLevelId +
            ", flowed=" + flowed +
            ", orderId=" + orderId +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserVipFlow.ID
            ,UserVipFlow.STATUS
            ,UserVipFlow.REMARK
            ,UserVipFlow.CREATE_TIME
            ,UserVipFlow.UPDATE_TIME
            ,UserVipFlow.TEST_TIME
            ,UserVipFlow.VERSION
            ,UserVipFlow.US_ID
            ,UserVipFlow.AMOUNT
            ,UserVipFlow.SOURCE
            ,UserVipFlow.TENANT_ID
            ,UserVipFlow.VIP_LEVEL_ID
            ,UserVipFlow.FLOWED
            ,UserVipFlow.ORDER_ID
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}