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
@ApiModel(value="SysManufacturerModels对象", description="系统-厂家和车型表")
public class SysManufacturerModels implements Serializable {

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

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "十六进制")
    private String value;

    @ApiModelProperty(value = "类型1A，2b")
    private Integer manufacturersType;

    @ApiModelProperty(value = "1厂家 2车型")
    private Integer type;

    @ApiModelProperty(value = "设备长度")
    private Integer devLength;

    public Integer getId() {
        return id;
    }

    public SysManufacturerModels setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public SysManufacturerModels setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public SysManufacturerModels setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public SysManufacturerModels setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public SysManufacturerModels setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public SysManufacturerModels setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public SysManufacturerModels setVersion(Long version) {
        this.version = version;
        return this;
    }
    public String getName() {
        return name;
    }

    public SysManufacturerModels setName(String name) {
        this.name = name;
        return this;
    }
    public String getValue() {
        return value;
    }

    public SysManufacturerModels setValue(String value) {
        this.value = value;
        return this;
    }
    public Integer getManufacturersType() {
        return manufacturersType;
    }

    public SysManufacturerModels setManufacturersType(Integer manufacturersType) {
        this.manufacturersType = manufacturersType;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public SysManufacturerModels setType(Integer type) {
        this.type = type;
        return this;
    }
    public Integer getDevLength() {
        return devLength;
    }

    public SysManufacturerModels setDevLength(Integer devLength) {
        this.devLength = devLength;
        return this;
    }

    public static final String ID = "sys_manufacturer_models.id";
    public static final String STATUS = "sys_manufacturer_models.status";
    public static final String REMARK = "sys_manufacturer_models.remark";
    public static final String CREATE_TIME = "sys_manufacturer_models.create_time";
    public static final String UPDATE_TIME = "sys_manufacturer_models.update_time";
    public static final String TEST_TIME = "sys_manufacturer_models.test_time";
    public static final String VERSION = "sys_manufacturer_models.version";
    public static final String NAME = "sys_manufacturer_models.name";
    public static final String VALUE = "sys_manufacturer_models.value";
    public static final String MANUFACTURERS_TYPE = "sys_manufacturer_models.manufacturers_type";
    public static final String TYPE = "sys_manufacturer_models.type";
    public static final String DEV_LENGTH = "sys_manufacturer_models.dev_length";


    @Override
    public String toString() {
        return "SysManufacturerModels{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", name=" + name +
            ", value=" + value +
            ", manufacturersType=" + manufacturersType +
            ", type=" + type +
            ", devLength=" + devLength +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            SysManufacturerModels.ID
            ,SysManufacturerModels.STATUS
            ,SysManufacturerModels.REMARK
            ,SysManufacturerModels.CREATE_TIME
            ,SysManufacturerModels.UPDATE_TIME
            ,SysManufacturerModels.TEST_TIME
            ,SysManufacturerModels.VERSION
            ,SysManufacturerModels.NAME
            ,SysManufacturerModels.VALUE
            ,SysManufacturerModels.MANUFACTURERS_TYPE
            ,SysManufacturerModels.TYPE
            ,SysManufacturerModels.DEV_LENGTH
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}