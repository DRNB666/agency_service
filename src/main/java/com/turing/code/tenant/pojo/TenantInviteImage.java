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
 * @since 2024-06-01
 */
@ApiModel(value="TenantInviteImage对象", description="分销员-分享商品小程序码")
public class TenantInviteImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "邀请码")
    private String image;

    @ApiModelProperty(value = "1普通商品 2秒杀 3团购")
    private Integer type;

    @ApiModelProperty(value = "商品id")
    private Long proId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public TenantInviteImage setId(Long id) {
        this.id = id;
        return this;
    }
    public String getImage() {
        return image;
    }

    public TenantInviteImage setImage(String image) {
        this.image = image;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public TenantInviteImage setType(Integer type) {
        this.type = type;
        return this;
    }
    public Long getProId() {
        return proId;
    }

    public TenantInviteImage setProId(Long proId) {
        this.proId = proId;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public TenantInviteImage setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantInviteImage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantInviteImage setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "tenant_invite_image.id";
    public static final String IMAGE = "tenant_invite_image.image";
    public static final String TYPE = "tenant_invite_image.type";
    public static final String PRO_ID = "tenant_invite_image.pro_id";
    public static final String USER_ID = "tenant_invite_image.user_id";
    public static final String CREATE_TIME = "tenant_invite_image.create_time";
    public static final String UPDATE_TIME = "tenant_invite_image.update_time";


    @Override
    public String toString() {
        return "TenantInviteImage{" +
            "id=" + id +
            ", image=" + image +
            ", type=" + type +
            ", proId=" + proId +
            ", userId=" + userId +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantInviteImage.ID
            ,TenantInviteImage.IMAGE
            ,TenantInviteImage.TYPE
            ,TenantInviteImage.PRO_ID
            ,TenantInviteImage.USER_ID
            ,TenantInviteImage.CREATE_TIME
            ,TenantInviteImage.UPDATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}