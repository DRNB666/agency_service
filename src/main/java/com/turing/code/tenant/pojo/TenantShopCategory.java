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
 * @since 2024-06-06
 */
@ApiModel(value="TenantShopCategory对象", description="租户-店铺分类表")
public class TenantShopCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "排名，越大越靠前")
    private Integer sort;

    @ApiModelProperty(value = "状态")
    private Integer status;

    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public TenantShopCategory setId(Long id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantShopCategory setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getSort() {
        return sort;
    }

    public TenantShopCategory setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantShopCategory setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantShopCategory setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantShopCategory setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantShopCategory setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "tenant_shop_category.id";
    public static final String NAME = "tenant_shop_category.name";
    public static final String SORT = "tenant_shop_category.sort";
    public static final String STATUS = "tenant_shop_category.status";
    public static final String REMARK = "tenant_shop_category.remark";
    public static final String CREATE_TIME = "tenant_shop_category.create_time";
    public static final String UPDATE_TIME = "tenant_shop_category.update_time";


    @Override
    public String toString() {
        return "TenantShopCategory{" +
            "id=" + id +
            ", name=" + name +
            ", sort=" + sort +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantShopCategory.ID
            ,TenantShopCategory.NAME
            ,TenantShopCategory.SORT
            ,TenantShopCategory.STATUS
            ,TenantShopCategory.REMARK
            ,TenantShopCategory.CREATE_TIME
            ,TenantShopCategory.UPDATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}