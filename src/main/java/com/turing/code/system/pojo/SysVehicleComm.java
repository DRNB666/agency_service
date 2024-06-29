package com.turing.code.system.pojo;

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
 * @since 2024-04-16
 */
@ApiModel(value="SysVehicleComm对象", description="系统-整车通信状态")
public class SysVehicleComm implements Serializable {

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

    @ApiModelProperty(value = "版本号")
    @Version
    private Long version;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "值")
    private String value;

    public Integer getId() {
        return id;
    }

    public SysVehicleComm setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public SysVehicleComm setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public SysVehicleComm setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public SysVehicleComm setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public SysVehicleComm setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public SysVehicleComm setVersion(Long version) {
        this.version = version;
        return this;
    }
    public String getName() {
        return name;
    }

    public SysVehicleComm setName(String name) {
        this.name = name;
        return this;
    }
    public String getValue() {
        return value;
    }

    public SysVehicleComm setValue(String value) {
        this.value = value;
        return this;
    }

    public static final String ID = "sys_vehicle_comm.id";
    public static final String STATUS = "sys_vehicle_comm.status";
    public static final String REMARK = "sys_vehicle_comm.remark";
    public static final String CREATE_TIME = "sys_vehicle_comm.create_time";
    public static final String UPDATE_TIME = "sys_vehicle_comm.update_time";
    public static final String VERSION = "sys_vehicle_comm.version";
    public static final String NAME = "sys_vehicle_comm.name";
    public static final String VALUE = "sys_vehicle_comm.value";


    @Override
    public String toString() {
        return "SysVehicleComm{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", version=" + version +
            ", name=" + name +
            ", value=" + value +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            SysVehicleComm.ID
            ,SysVehicleComm.STATUS
            ,SysVehicleComm.REMARK
            ,SysVehicleComm.CREATE_TIME
            ,SysVehicleComm.UPDATE_TIME
            ,SysVehicleComm.VERSION
            ,SysVehicleComm.NAME
            ,SysVehicleComm.VALUE
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}