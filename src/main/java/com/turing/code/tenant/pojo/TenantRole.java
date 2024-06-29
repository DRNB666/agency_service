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
 * @since 2024-05-28
 */
@ApiModel(value="TenantRole对象", description="租户-角色")
public class TenantRole implements Serializable {

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

    public Integer getId() {
        return id;
    }

    public TenantRole setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getRoleName() {
        return roleName;
    }

    public TenantRole setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }
    public Integer getAccountNum() {
        return accountNum;
    }

    public TenantRole setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantRole setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantRole setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantRole setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantRole setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getVersion() {
        return version;
    }

    public TenantRole setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public static final String ID = "tenant_role.id";
    public static final String ROLE_NAME = "tenant_role.role_name";
    public static final String ACCOUNT_NUM = "tenant_role.account_num";
    public static final String REMARK = "tenant_role.remark";
    public static final String STATUS = "tenant_role.status";
    public static final String UPDATE_TIME = "tenant_role.update_time";
    public static final String CREATE_TIME = "tenant_role.create_time";
    public static final String VERSION = "tenant_role.version";


    @Override
    public String toString() {
        return "TenantRole{" +
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

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantRole.ID
            ,TenantRole.ROLE_NAME
            ,TenantRole.ACCOUNT_NUM
            ,TenantRole.REMARK
            ,TenantRole.STATUS
            ,TenantRole.UPDATE_TIME
            ,TenantRole.CREATE_TIME
            ,TenantRole.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}