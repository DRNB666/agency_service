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
@ApiModel(value="TenantProIdentifi对象", description="商品售后标识")
public class TenantProIdentifi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "排序，越大越靠前")
    private Integer sort;

    @ApiModelProperty(value = "售后标识")
    private String name;

    @ApiModelProperty(value = "1正常")
    private Integer status;

    @ApiModelProperty(value = "1普通 2秒杀 3团购")
    private Integer type;

    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public TenantProIdentifi setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getSort() {
        return sort;
    }

    public TenantProIdentifi setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantProIdentifi setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantProIdentifi setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public TenantProIdentifi setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantProIdentifi setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantProIdentifi setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantProIdentifi setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "tenant_pro_identifi.id";
    public static final String SORT = "tenant_pro_identifi.sort";
    public static final String NAME = "tenant_pro_identifi.name";
    public static final String STATUS = "tenant_pro_identifi.status";
    public static final String TYPE = "tenant_pro_identifi.type";
    public static final String REMARK = "tenant_pro_identifi.remark";
    public static final String CREATE_TIME = "tenant_pro_identifi.create_time";
    public static final String UPDATE_TIME = "tenant_pro_identifi.update_time";


    @Override
    public String toString() {
        return "TenantProIdentifi{" +
            "id=" + id +
            ", sort=" + sort +
            ", name=" + name +
            ", status=" + status +
            ", type=" + type +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantProIdentifi.ID
            ,TenantProIdentifi.SORT
            ,TenantProIdentifi.NAME
            ,TenantProIdentifi.STATUS
            ,TenantProIdentifi.TYPE
            ,TenantProIdentifi.REMARK
            ,TenantProIdentifi.CREATE_TIME
            ,TenantProIdentifi.UPDATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}