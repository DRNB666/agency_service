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
@ApiModel(value = "SysTimerConfig对象", description = "系统-定时器")
public class SysTimerConfig implements Serializable {

    public static final String ID = "sys_timer_config.id";
    public static final String CRON = "sys_timer_config.cron";
    public static final String CYCLE = "sys_timer_config.cycle";
    public static final String CYCLE_NAME = "sys_timer_config.cycle_name";
    public static final String REMARK = "sys_timer_config.remark";
    public static final String JOB_NAME = "sys_timer_config.job_name";
    public static final String JOB_GROUP = "sys_timer_config.job_group";
    public static final String JOB_CLASS = "sys_timer_config.job_class";
    public static final String STATUS = "sys_timer_config.status";
    public static final String LAST_TIME = "sys_timer_config.last_time";
    public static final String UPDATE_TIME = "sys_timer_config.update_time";
    public static final String CREATE_TIME = "sys_timer_config.create_time";
    public static final String VERSION = "sys_timer_config.version";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "该定时器的cron表达式")
    private String cron;
    @ApiModelProperty(value = "执行周期")
    private Integer cycle;
    @ApiModelProperty(value = "执行周期单位  (分钟, 时, 天, 月)")
    private String cycleName;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "任务名")
    private String jobName;
    @ApiModelProperty(value = "任务组名")
    private String jobGroup;
    @ApiModelProperty(value = "该项 job 执行定时任务时调用的类 (包名.类名)")
    private String jobClass;
    @ApiModelProperty(value = "1: 正常运行    -1 停止")
    private Integer status;
    @ApiModelProperty(value = "上一次执行时间")
    private Date lastTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "版本")
    @Version
    private Integer version;

    public final static String[] getFields(String... noField) {
        List<String> list = new ArrayList<>(Arrays.asList(
                SysTimerConfig.ID
                , SysTimerConfig.CRON
                , SysTimerConfig.CYCLE
                , SysTimerConfig.CYCLE_NAME
                , SysTimerConfig.REMARK
                , SysTimerConfig.JOB_NAME
                , SysTimerConfig.JOB_GROUP
                , SysTimerConfig.JOB_CLASS
                , SysTimerConfig.STATUS
                , SysTimerConfig.LAST_TIME
                , SysTimerConfig.UPDATE_TIME
                , SysTimerConfig.CREATE_TIME
                , SysTimerConfig.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }

    public Integer getId() {
        return id;
    }

    public SysTimerConfig setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public SysTimerConfig setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public Integer getCycle() {
        return cycle;
    }

    public SysTimerConfig setCycle(Integer cycle) {
        this.cycle = cycle;
        return this;
    }

    public String getCycleName() {
        return cycleName;
    }

    public SysTimerConfig setCycleName(String cycleName) {
        this.cycleName = cycleName;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SysTimerConfig setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public SysTimerConfig setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public SysTimerConfig setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        return this;
    }

    public String getJobClass() {
        return jobClass;
    }

    public SysTimerConfig setJobClass(String jobClass) {
        this.jobClass = jobClass;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public SysTimerConfig setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public SysTimerConfig setLastTime(Date lastTime) {
        this.lastTime = lastTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SysTimerConfig setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SysTimerConfig setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public SysTimerConfig setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "SysTimerConfig{" +
                "id=" + id +
                ", cron=" + cron +
                ", cycle=" + cycle +
                ", cycleName=" + cycleName +
                ", remark=" + remark +
                ", jobName=" + jobName +
                ", jobGroup=" + jobGroup +
                ", jobClass=" + jobClass +
                ", status=" + status +
                ", lastTime=" + lastTime +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", version=" + version +
                "}";
    }
}