package com.leepsmart.code.admin.pojo;

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
 * @author leepsmart generator
 * @since 2021-11-03
 */
@ApiModel(value = "AdRole对象", description = "管理员-角色")
public class AdRole implements Serializable {

    public static final String ID = "ad_role.id";
    public static final String ROLE_NAME = "ad_role.role_name";
    public static final String ACCOUNT_NUM = "ad_role.account_num";
    public static final String REMARK = "ad_role.remark";
    public static final String STATUS = "ad_role.status";
    public static final String UPDATE_TIME = "ad_role.update_time";
    public static final String CREATE_TIME = "ad_role.create_time";
    public static final String VERSION = "ad_role.version";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "账号数量")
    private Integer accountNum;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "-1:  冻结   1: 正常")
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
                AdRole.ID
                , AdRole.ROLE_NAME
                , AdRole.ACCOUNT_NUM
                , AdRole.REMARK
                , AdRole.STATUS
                , AdRole.UPDATE_TIME
                , AdRole.CREATE_TIME
                , AdRole.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }

    public Integer getId() {
        return id;
    }

    public AdRole setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public AdRole setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public Integer getAccountNum() {
        return accountNum;
    }

    public AdRole setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AdRole setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public AdRole setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AdRole setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AdRole setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public AdRole setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "AdRole{" +
                "id=" + id +
                ", roleName=" + roleName +
                ", accountNum=" + accountNum +
                ", remark=" + remark +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", version=" + version +
                "}";
    }
}