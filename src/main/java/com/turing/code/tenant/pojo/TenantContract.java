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
 * @since 2024-06-12
 */
@ApiModel(value="TenantContract对象", description="租户-合同模板")
public class TenantContract implements Serializable {

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

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    @ApiModelProperty(value = "合同名称")
    private String name;

    @ApiModelProperty(value = "模板内容")
    private String content;

    public Integer getId() {
        return id;
    }

    public TenantContract setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantContract setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantContract setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantContract setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantContract setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public TenantContract setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public TenantContract setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Integer getTenantId() {
        return tenantId;
    }

    public TenantContract setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantContract setName(String name) {
        this.name = name;
        return this;
    }
    public String getContent() {
        return content;
    }

    public TenantContract setContent(String content) {
        this.content = content;
        return this;
    }

    public static final String ID = "tenant_contract.id";
    public static final String STATUS = "tenant_contract.status";
    public static final String REMARK = "tenant_contract.remark";
    public static final String CREATE_TIME = "tenant_contract.create_time";
    public static final String UPDATE_TIME = "tenant_contract.update_time";
    public static final String TEST_TIME = "tenant_contract.test_time";
    public static final String VERSION = "tenant_contract.version";
    public static final String TENANT_ID = "tenant_contract.tenant_id";
    public static final String NAME = "tenant_contract.name";
    public static final String CONTENT = "tenant_contract.content";


    @Override
    public String toString() {
        return "TenantContract{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", tenantId=" + tenantId +
            ", name=" + name +
            ", content=" + content +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantContract.ID
            ,TenantContract.STATUS
            ,TenantContract.REMARK
            ,TenantContract.CREATE_TIME
            ,TenantContract.UPDATE_TIME
            ,TenantContract.TEST_TIME
            ,TenantContract.VERSION
            ,TenantContract.TENANT_ID
            ,TenantContract.NAME
            ,TenantContract.CONTENT
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}