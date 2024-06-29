package com.turing.code.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author turing generator
 * @since 2021-11-03
 */
@ApiModel(value = "AdInfo对象", description = "管理员-用户信息")
public class AdInfo implements Serializable {

    public static final String ID = "ad_info.id";
    public static final String ACCOUNT = "ad_info.account";
    public static final String PASSWORD = "ad_info.password";
    public static final String ROLE_ID = "ad_info.role_id";
    public static final String REMARK = "ad_info.remark";
    public static final String STATUS = "ad_info.status";
    public static final String LOGIN_TIME = "ad_info.login_time";
    public static final String CREATE_TIME = "ad_info.create_time";
    public static final String UPDATE_TIME = "ad_info.update_time";
    public static final String VERSION = "ad_info.version";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
    @ApiModelProperty(value = "状态")
    private String remark;
    @ApiModelProperty(value = "-1: 冻结   1: 正常   ")
    private Integer status;
    @ApiModelProperty(value = "登录时间")
    private Date loginTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "版本")
    @Version
    private Integer version;

    public final static String[] getFields(String... noField) {
        List<String> list = new ArrayList<>(Arrays.asList(
                AdInfo.ID
                , AdInfo.ACCOUNT
                , AdInfo.PASSWORD
                , AdInfo.ROLE_ID
                , AdInfo.REMARK
                , AdInfo.STATUS
                , AdInfo.LOGIN_TIME
                , AdInfo.CREATE_TIME
                , AdInfo.UPDATE_TIME
                , AdInfo.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }

    public Integer getId() {
        return id;
    }

    public AdInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public AdInfo setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AdInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public AdInfo setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AdInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AdInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public AdInfo setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AdInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AdInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public AdInfo setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "AdInfo{" +
                "id=" + id +
                ", account=" + account +
                ", password=" + password +
                ", roleId=" + roleId +
                ", remark=" + remark +
                ", status=" + status +
                ", loginTime=" + loginTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                "}";
    }
}