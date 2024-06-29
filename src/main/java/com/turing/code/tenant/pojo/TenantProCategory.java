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
 * @since 2024-06-11
 */
@ApiModel(value="TenantProCategory对象", description="租户-商品分类表")
public class TenantProCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "排名，越大越靠前")
    private Integer sort;

    private String remark;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "一级分类为-1")
    private Long parentId;

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    @ApiModelProperty(value = "分类封面图")
    private String cover;

    public Long getId() {
        return id;
    }

    public TenantProCategory setId(Long id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantProCategory setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getSort() {
        return sort;
    }

    public TenantProCategory setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantProCategory setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantProCategory setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantProCategory setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantProCategory setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getParentId() {
        return parentId;
    }

    public TenantProCategory setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }
    public Integer getTenantId() {
        return tenantId;
    }

    public TenantProCategory setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public String getCover() {
        return cover;
    }

    public TenantProCategory setCover(String cover) {
        this.cover = cover;
        return this;
    }

    public static final String ID = "tenant_pro_category.id";
    public static final String NAME = "tenant_pro_category.name";
    public static final String SORT = "tenant_pro_category.sort";
    public static final String REMARK = "tenant_pro_category.remark";
    public static final String STATUS = "tenant_pro_category.status";
    public static final String UPDATE_TIME = "tenant_pro_category.update_time";
    public static final String CREATE_TIME = "tenant_pro_category.create_time";
    public static final String PARENT_ID = "tenant_pro_category.parent_id";
    public static final String TENANT_ID = "tenant_pro_category.tenant_id";
    public static final String COVER = "tenant_pro_category.cover";


    @Override
    public String toString() {
        return "TenantProCategory{" +
            "id=" + id +
            ", name=" + name +
            ", sort=" + sort +
            ", remark=" + remark +
            ", status=" + status +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", parentId=" + parentId +
            ", tenantId=" + tenantId +
            ", cover=" + cover +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantProCategory.ID
            ,TenantProCategory.NAME
            ,TenantProCategory.SORT
            ,TenantProCategory.REMARK
            ,TenantProCategory.STATUS
            ,TenantProCategory.UPDATE_TIME
            ,TenantProCategory.CREATE_TIME
            ,TenantProCategory.PARENT_ID
            ,TenantProCategory.TENANT_ID
            ,TenantProCategory.COVER
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}