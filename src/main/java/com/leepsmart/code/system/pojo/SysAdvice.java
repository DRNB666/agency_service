package com.leepsmart.code.system.pojo;

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
 * @author leepsmart generator
 * @since 2021-11-03
 */
@ApiModel(value = "SysAdvice对象", description = "系统-用户建议反馈")
public class SysAdvice implements Serializable {

    public static final String ID = "sys_advice.id";
    public static final String US_ID = "sys_advice.us_id";
    public static final String TYPE = "sys_advice.type";
    public static final String CONTENT = "sys_advice.content";
    public static final String IMGS = "sys_advice.imgs";
    public static final String REPLY = "sys_advice.reply";
    public static final String IS_READ = "sys_advice.is_read";
    public static final String REMARK = "sys_advice.remark";
    public static final String STATUS = "sys_advice.status";
    public static final String UPDATE_TIME = "sys_advice.update_time";
    public static final String CREATE_TIME = "sys_advice.create_time";
    public static final String VERSION = "sys_advice.version";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer usId;
    @ApiModelProperty(value = "用户类型    1: 用户")
    private Integer type;
    @ApiModelProperty(value = "建议反馈内容")
    private String content;
    @ApiModelProperty(value = "图片, 多个图片逗号分割")
    private String imgs;
    @ApiModelProperty(value = "回复内容")
    private String reply;
    @ApiModelProperty(value = "是否已读   	管理员的未读消息形式:  is_read=0,  status=0 	管理员已读: is_read = 1, status = 0    	用户的未读形式: is_read=0,  status=1	用户已读: is_read = 1, status = 1")
    private Integer isRead;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "0: 未处理   1: 已处理")
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
                SysAdvice.ID
                , SysAdvice.US_ID
                , SysAdvice.TYPE
                , SysAdvice.CONTENT
                , SysAdvice.IMGS
                , SysAdvice.REPLY
                , SysAdvice.IS_READ
                , SysAdvice.REMARK
                , SysAdvice.STATUS
                , SysAdvice.UPDATE_TIME
                , SysAdvice.CREATE_TIME
                , SysAdvice.VERSION
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }

    public Integer getId() {
        return id;
    }

    public SysAdvice setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUsId() {
        return usId;
    }

    public SysAdvice setUsId(Integer usId) {
        this.usId = usId;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public SysAdvice setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SysAdvice setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImgs() {
        return imgs;
    }

    public SysAdvice setImgs(String imgs) {
        this.imgs = imgs;
        return this;
    }

    public String getReply() {
        return reply;
    }

    public SysAdvice setReply(String reply) {
        this.reply = reply;
        return this;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public SysAdvice setIsRead(Integer isRead) {
        this.isRead = isRead;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SysAdvice setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public SysAdvice setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SysAdvice setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SysAdvice setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public SysAdvice setVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "SysAdvice{" +
                "id=" + id +
                ", usId=" + usId +
                ", type=" + type +
                ", content=" + content +
                ", imgs=" + imgs +
                ", reply=" + reply +
                ", isRead=" + isRead +
                ", remark=" + remark +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", version=" + version +
                "}";
    }
}