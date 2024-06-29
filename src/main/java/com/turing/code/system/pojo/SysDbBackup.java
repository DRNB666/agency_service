package com.turing.code.system.pojo;

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
 * @author turing generator
 * @since 2021-11-03
 */
@ApiModel(value = "SysDbBackup对象", description = "系统-数据库备份")
public class SysDbBackup implements Serializable {

    public static final String ID = "sys_db_backup.id";
    public static final String PATH = "sys_db_backup.path";
    public static final String RESTORE = "sys_db_backup.restore";
    public static final String REMARK = "sys_db_backup.remark";
    public static final String STATUS = "sys_db_backup.status";
    public static final String UPDATE_TIME = "sys_db_backup.update_time";
    public static final String CREATE_TIME = "sys_db_backup.create_time";
    public static final String VERSION = "sys_db_backup.version";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "sql文件路径")
    private String path;
    @ApiModelProperty(value = "还原次数")
    private Integer restore;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "1: 备份成功    -1: 备份失败")
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
                SysDbBackup.ID
                , SysDbBackup.PATH
                , SysDbBackup.RESTORE
                , SysDbBackup.REMARK
                , SysDbBackup.STATUS
                , SysDbBackup.UPDATE_TIME
                , SysDbBackup.CREATE_TIME
                , SysDbBackup.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }

    public Integer getId() {
        return id;
    }

    public SysDbBackup setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public SysDbBackup setPath(String path) {
        this.path = path;
        return this;
    }

    public Integer getRestore() {
        return restore;
    }

    public SysDbBackup setRestore(Integer restore) {
        this.restore = restore;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SysDbBackup setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public SysDbBackup setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SysDbBackup setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SysDbBackup setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public SysDbBackup setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "SysDbBackup{" +
                "id=" + id +
                ", path=" + path +
                ", restore=" + restore +
                ", remark=" + remark +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", version=" + version +
                "}";
    }
}