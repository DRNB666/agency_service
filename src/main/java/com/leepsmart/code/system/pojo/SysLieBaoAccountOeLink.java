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
 * @since 2024-07-26
 */
@ApiModel(value="SysLieBaoAccountOeLink对象", description="系统-猎豹OE开户申请链接")
public class SysLieBaoAccountOeLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "版本号")
    @Version
    private Long version;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private Date testTime;

    @ApiModelProperty(value = "oe开户链接")
    private String oeLink;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "开户token")
    private String token;

    public Integer getId() {
        return id;
    }

    public SysLieBaoAccountOeLink setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public SysLieBaoAccountOeLink setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public SysLieBaoAccountOeLink setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public SysLieBaoAccountOeLink setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public SysLieBaoAccountOeLink setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public SysLieBaoAccountOeLink setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public SysLieBaoAccountOeLink setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public String getOeLink() {
        return oeLink;
    }

    public SysLieBaoAccountOeLink setOeLink(String oeLink) {
        this.oeLink = oeLink;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public SysLieBaoAccountOeLink setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public String getToken() {
        return token;
    }

    public SysLieBaoAccountOeLink setToken(String token) {
        this.token = token;
        return this;
    }

    public static final String ID = "sys_lie_bao_account_oe_link.id";
    public static final String STATUS = "sys_lie_bao_account_oe_link.status";
    public static final String REMARK = "sys_lie_bao_account_oe_link.remark";
    public static final String CREATE_TIME = "sys_lie_bao_account_oe_link.create_time";
    public static final String VERSION = "sys_lie_bao_account_oe_link.version";
    public static final String UPDATE_TIME = "sys_lie_bao_account_oe_link.update_time";
    public static final String TEST_TIME = "sys_lie_bao_account_oe_link.test_time";
    public static final String OE_LINK = "sys_lie_bao_account_oe_link.oe_link";
    public static final String USER_ID = "sys_lie_bao_account_oe_link.user_id";
    public static final String TOKEN = "sys_lie_bao_account_oe_link.token";


    @Override
    public String toString() {
        return "SysLieBaoAccountOeLink{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", version=" + version +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", oeLink=" + oeLink +
            ", userId=" + userId +
            ", token=" + token +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            SysLieBaoAccountOeLink.ID
            ,SysLieBaoAccountOeLink.STATUS
            ,SysLieBaoAccountOeLink.REMARK
            ,SysLieBaoAccountOeLink.CREATE_TIME
            ,SysLieBaoAccountOeLink.VERSION
            ,SysLieBaoAccountOeLink.UPDATE_TIME
            ,SysLieBaoAccountOeLink.TEST_TIME
            ,SysLieBaoAccountOeLink.OE_LINK
            ,SysLieBaoAccountOeLink.USER_ID
            ,SysLieBaoAccountOeLink.TOKEN
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}