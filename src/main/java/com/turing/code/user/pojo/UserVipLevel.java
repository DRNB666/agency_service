package com.turing.code.user.pojo;

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
 * @since 2024-06-25
 */
@ApiModel(value="UserVipLevel对象", description="用户-会员等级")
public class UserVipLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态1正常 0过期")
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

    @ApiModelProperty(value = "用户id")
    private Long usId;

    @ApiModelProperty(value = "会员等级id")
    private Integer vipLevelId;

    @ApiModelProperty(value = "到期时间")
    private Date dueTime;

    @ApiModelProperty(value = "商家id")
    private Long tenantId;

    @ApiModelProperty(value = "开通时间")
    private Date openTime;

    public Integer getId() {
        return id;
    }

    public UserVipLevel setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserVipLevel setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserVipLevel setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserVipLevel setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserVipLevel setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public UserVipLevel setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public UserVipLevel setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public UserVipLevel setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public Integer getVipLevelId() {
        return vipLevelId;
    }

    public UserVipLevel setVipLevelId(Integer vipLevelId) {
        this.vipLevelId = vipLevelId;
        return this;
    }
    public Date getDueTime() {
        return dueTime;
    }

    public UserVipLevel setDueTime(Date dueTime) {
        this.dueTime = dueTime;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public UserVipLevel setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Date getOpenTime() {
        return openTime;
    }

    public UserVipLevel setOpenTime(Date openTime) {
        this.openTime = openTime;
        return this;
    }

    public static final String ID = "user_vip_level.id";
    public static final String STATUS = "user_vip_level.status";
    public static final String REMARK = "user_vip_level.remark";
    public static final String CREATE_TIME = "user_vip_level.create_time";
    public static final String UPDATE_TIME = "user_vip_level.update_time";
    public static final String TEST_TIME = "user_vip_level.test_time";
    public static final String VERSION = "user_vip_level.version";
    public static final String US_ID = "user_vip_level.us_id";
    public static final String VIP_LEVEL_ID = "user_vip_level.vip_level_id";
    public static final String DUE_TIME = "user_vip_level.due_time";
    public static final String TENANT_ID = "user_vip_level.tenant_id";
    public static final String OPEN_TIME = "user_vip_level.open_time";


    @Override
    public String toString() {
        return "UserVipLevel{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", usId=" + usId +
            ", vipLevelId=" + vipLevelId +
            ", dueTime=" + dueTime +
            ", tenantId=" + tenantId +
            ", openTime=" + openTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserVipLevel.ID
            ,UserVipLevel.STATUS
            ,UserVipLevel.REMARK
            ,UserVipLevel.CREATE_TIME
            ,UserVipLevel.UPDATE_TIME
            ,UserVipLevel.TEST_TIME
            ,UserVipLevel.VERSION
            ,UserVipLevel.US_ID
            ,UserVipLevel.VIP_LEVEL_ID
            ,UserVipLevel.DUE_TIME
            ,UserVipLevel.TENANT_ID
            ,UserVipLevel.OPEN_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}