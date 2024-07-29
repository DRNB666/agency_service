package com.leepsmart.code.system.pojo;

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
 * @author leepsmart generator
 * @since 2024-05-31
 */
@ApiModel(value="SysParams对象", description="系统参数")
public class SysParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    @ApiModelProperty(value = "短文本内容存值")
    private String value;

    @ApiModelProperty(value = "长文本内容存值")
    private String blodValue;

    private String remark;

    @ApiModelProperty(value = "0:系统参数 1:集体参数  2:通用参数  3:隐藏参数")
    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public SysParams setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public SysParams setName(String name) {
        this.name = name;
        return this;
    }
    public String getValue() {
        return value;
    }

    public SysParams setValue(String value) {
        this.value = value;
        return this;
    }
    public String getBlodValue() {
        return blodValue;
    }

    public SysParams setBlodValue(String blodValue) {
        this.blodValue = blodValue;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public SysParams setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public SysParams setType(Integer type) {
        this.type = type;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public SysParams setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public SysParams setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public SysParams setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "sys_params.id";
    public static final String NAME = "sys_params.name";
    public static final String VALUE = "sys_params.value";
    public static final String BLOD_VALUE = "sys_params.blod_value";
    public static final String REMARK = "sys_params.remark";
    public static final String TYPE = "sys_params.type";
    public static final String STATUS = "sys_params.status";
    public static final String CREATE_TIME = "sys_params.create_time";
    public static final String UPDATE_TIME = "sys_params.update_time";


    @Override
    public String toString() {
        return "SysParams{" +
            "id=" + id +
            ", name=" + name +
            ", value=" + value +
            ", blodValue=" + blodValue +
            ", remark=" + remark +
            ", type=" + type +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            SysParams.ID
            ,SysParams.NAME
            ,SysParams.VALUE
            ,SysParams.BLOD_VALUE
            ,SysParams.REMARK
            ,SysParams.TYPE
            ,SysParams.STATUS
            ,SysParams.CREATE_TIME
            ,SysParams.UPDATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}