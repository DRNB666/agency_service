package com.turing.code.tenant.pojo;

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
 * @since 2024-05-28
 */

@ApiModel(value="TenantInfo对象", description="租户-信息")
public class TenantInfo implements Serializable {

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

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "所属主租户id，主租户账号为-1")
    private Integer parentId;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public TenantInfo setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public TenantInfo setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public TenantInfo setVersion(Long version) {
        this.version = version;
        return this;
    }
    public String getAccount() {
        return account;
    }

    public TenantInfo setAccount(String account) {
        this.account = account;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantInfo setName(String name) {
        this.name = name;
        return this;
    }
    public String getAvatar() {
        return avatar;
    }

    public TenantInfo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    public TenantInfo setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
    public String getPassword() {
        return password;
    }

    public TenantInfo setPassword(String password) {
        this.password = password;
        return this;
    }
    public Integer getParentId() {
        return parentId;
    }

    public TenantInfo setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public TenantInfo setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public static final String ID = "tenant_info.id";
    public static final String STATUS = "tenant_info.status";
    public static final String REMARK = "tenant_info.remark";
    public static final String CREATE_TIME = "tenant_info.create_time";
    public static final String UPDATE_TIME = "tenant_info.update_time";
    public static final String TEST_TIME = "tenant_info.test_time";
    public static final String VERSION = "tenant_info.version";
    public static final String ACCOUNT = "tenant_info.account";
    public static final String NAME = "tenant_info.name";
    public static final String AVATAR = "tenant_info.avatar";
    public static final String BALANCE = "tenant_info.balance";
    public static final String PASSWORD = "tenant_info.password";
    public static final String PARENT_ID = "tenant_info.parent_id";
    public static final String ROLE_ID = "tenant_info.role_id";


    @Override
    public String toString() {
        return "TenantInfo{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", account=" + account +
            ", name=" + name +
            ", avatar=" + avatar +
            ", balance=" + balance +
            ", password=" + password +
            ", parentId=" + parentId +
            ", roleId=" + roleId +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantInfo.ID
            ,TenantInfo.STATUS
            ,TenantInfo.REMARK
            ,TenantInfo.CREATE_TIME
            ,TenantInfo.UPDATE_TIME
            ,TenantInfo.TEST_TIME
            ,TenantInfo.VERSION
            ,TenantInfo.ACCOUNT
            ,TenantInfo.NAME
            ,TenantInfo.AVATAR
            ,TenantInfo.BALANCE
            ,TenantInfo.PASSWORD
            ,TenantInfo.PARENT_ID
            ,TenantInfo.ROLE_ID
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}