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
@ApiModel(value="TenantProSpec对象", description="商品规格项")
public class TenantProSpec implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "规格组ID")
    private Integer parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "1普通 2秒杀 3团购")
    private Integer type;

    @ApiModelProperty(value = "状态 1 商家商品  2:管理员商品")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "商品id")
    private Long proId;

    @ApiModelProperty(value = "规格组名")
    private String parentName;

    public Integer getId() {
        return id;
    }

    public TenantProSpec setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getParentId() {
        return parentId;
    }

    public TenantProSpec setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantProSpec setName(String name) {
        this.name = name;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantProSpec setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public TenantProSpec setType(Integer type) {
        this.type = type;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantProSpec setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantProSpec setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getProId() {
        return proId;
    }

    public TenantProSpec setProId(Long proId) {
        this.proId = proId;
        return this;
    }
    public String getParentName() {
        return parentName;
    }

    public TenantProSpec setParentName(String parentName) {
        this.parentName = parentName;
        return this;
    }

    public static final String ID = "tenant_pro_spec.id";
    public static final String PARENT_ID = "tenant_pro_spec.parent_id";
    public static final String NAME = "tenant_pro_spec.name";
    public static final String REMARK = "tenant_pro_spec.remark";
    public static final String TYPE = "tenant_pro_spec.type";
    public static final String STATUS = "tenant_pro_spec.status";
    public static final String CREATE_TIME = "tenant_pro_spec.create_time";
    public static final String PRO_ID = "tenant_pro_spec.pro_id";
    public static final String PARENT_NAME = "tenant_pro_spec.parent_name";


    @Override
    public String toString() {
        return "TenantProSpec{" +
            "id=" + id +
            ", parentId=" + parentId +
            ", name=" + name +
            ", remark=" + remark +
            ", type=" + type +
            ", status=" + status +
            ", createTime=" + createTime +
            ", proId=" + proId +
            ", parentName=" + parentName +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantProSpec.ID
            ,TenantProSpec.PARENT_ID
            ,TenantProSpec.NAME
            ,TenantProSpec.REMARK
            ,TenantProSpec.TYPE
            ,TenantProSpec.STATUS
            ,TenantProSpec.CREATE_TIME
            ,TenantProSpec.PRO_ID
            ,TenantProSpec.PARENT_NAME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}