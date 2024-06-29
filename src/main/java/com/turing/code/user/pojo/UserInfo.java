package com.turing.code.user.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.turing.code.admin.pojo.vo.UserInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author turing generator
 * @since 2024-06-25
 */
@ApiModel(value="UserInfo对象", description="用户信息")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商家id -1普通用户")
    private Long tenantId;

    @ApiModelProperty(value = "微信绑定")
    private String openId;

    @ApiModelProperty(value = "昵称")
    private String nick;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别：0：未知，1：男，2：女")
    private Integer sex;

    @ApiModelProperty(value = "业务员余额")
    private BigDecimal royal;

    @ApiModelProperty(value = "邀请人id")
    private Integer inviterId;

    @ApiModelProperty(value = "邀请码")
    private String inviterCode;

    @ApiModelProperty(value = "注册方式：1：微信注册 2：导入注册")
    private Integer registType;

    private Date bindTime;

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;

    @ApiModelProperty(value = "经度(可更新)")
    private String longitude;

    @ApiModelProperty(value = "个人位置(可更新)")
    private String address;

    @ApiModelProperty(value = "维度(可更新)")
    private String latitude;

    private String remark;

    private String name;

    @ApiModelProperty(value = "等级:0普通用户 1业务员")
    private Integer level;

    @ApiModelProperty(value = "状态：1正常 -1冻结 -2注销")
    private Integer status;

    @ApiModelProperty(value = "最近登陆时间")
    private Date loginTime;

    @ApiModelProperty(value = "成为推广员时间")
    private Date promoteTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "临时用户id")
    private Long temporaryUser;

    @ApiModelProperty(value = "会员储蓄卡余额")
    private BigDecimal vipSavingsCard;

    /** 显示/隐藏商家入口*/
    @TableField(exist = false)
    private String isMerchant;
    /** 等级图标*/
    @TableField(exist = false)
    private String icon;
    @TableField(exist = false)
    private UserInfoVo userInfoVo;//关联关系

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    public String getIsMerchant() {
        return isMerchant;
    }

    public void setIsMerchant(String isMerchant) {
        this.isMerchant = isMerchant;
    }

    public UserInfoVo getUserInfoVo() {
        return userInfoVo;
    }

    public void setUserInfoVo(UserInfoVo userInfoVo) {
        this.userInfoVo = userInfoVo;
    }

    public Long getId() {
        return id;
    }

    public UserInfo setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public UserInfo setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public String getOpenId() {
        return openId;
    }

    public UserInfo setOpenId(String openId) {
        this.openId = openId;
        return this;
    }
    public String getNick() {
        return nick;
    }

    public UserInfo setNick(String nick) {
        this.nick = nick;
        return this;
    }
    public String getAvatar() {
        return avatar;
    }

    public UserInfo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
    public String getMobile() {
        return mobile;
    }

    public UserInfo setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }
    public String getAccount() {
        return account;
    }

    public UserInfo setAccount(String account) {
        this.account = account;
        return this;
    }
    public String getPassword() {
        return password;
    }

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }
    public Integer getSex() {
        return sex;
    }

    public UserInfo setSex(Integer sex) {
        this.sex = sex;
        return this;
    }
    public BigDecimal getRoyal() {
        return royal;
    }

    public UserInfo setRoyal(BigDecimal royal) {
        this.royal = royal;
        return this;
    }
    public Integer getInviterId() {
        return inviterId;
    }

    public UserInfo setInviterId(Integer inviterId) {
        this.inviterId = inviterId;
        return this;
    }
    public String getInviterCode() {
        return inviterCode;
    }

    public UserInfo setInviterCode(String inviterCode) {
        this.inviterCode = inviterCode;
        return this;
    }
    public Integer getRegistType() {
        return registType;
    }

    public UserInfo setRegistType(Integer registType) {
        this.registType = registType;
        return this;
    }
    public Date getBindTime() {
        return bindTime;
    }

    public UserInfo setBindTime(Date bindTime) {
        this.bindTime = bindTime;
        return this;
    }
    public Integer getVersion() {
        return version;
    }

    public UserInfo setVersion(Integer version) {
        this.version = version;
        return this;
    }
    public String getLongitude() {
        return longitude;
    }

    public UserInfo setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }
    public String getAddress() {
        return address;
    }

    public UserInfo setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getLatitude() {
        return latitude;
    }

    public UserInfo setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }
    public Integer getLevel() {
        return level;
    }

    public UserInfo setLevel(Integer level) {
        this.level = level;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getLoginTime() {
        return loginTime;
    }

    public UserInfo setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
        return this;
    }
    public Date getPromoteTime() {
        return promoteTime;
    }

    public UserInfo setPromoteTime(Date promoteTime) {
        this.promoteTime = promoteTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getTemporaryUser() {
        return temporaryUser;
    }

    public UserInfo setTemporaryUser(Long temporaryUser) {
        this.temporaryUser = temporaryUser;
        return this;
    }
    public BigDecimal getVipSavingsCard() {
        return vipSavingsCard;
    }

    public UserInfo setVipSavingsCard(BigDecimal vipSavingsCard) {
        this.vipSavingsCard = vipSavingsCard;
        return this;
    }

    public static final String ID = "user_info.id";
    public static final String TENANT_ID = "user_info.tenant_id";
    public static final String OPEN_ID = "user_info.open_id";
    public static final String NICK = "user_info.nick";
    public static final String AVATAR = "user_info.avatar";
    public static final String MOBILE = "user_info.mobile";
    public static final String ACCOUNT = "user_info.account";
    public static final String PASSWORD = "user_info.password";
    public static final String SEX = "user_info.sex";
    public static final String ROYAL = "user_info.royal";
    public static final String INVITER_ID = "user_info.inviter_id";
    public static final String INVITER_CODE = "user_info.inviter_code";
    public static final String REGIST_TYPE = "user_info.regist_type";
    public static final String BIND_TIME = "user_info.bind_time";
    public static final String VERSION = "user_info.version";
    public static final String LONGITUDE = "user_info.longitude";
    public static final String ADDRESS = "user_info.address";
    public static final String LATITUDE = "user_info.latitude";
    public static final String REMARK = "user_info.remark";
    public static final String NAME = "user_info.name";
    public static final String LEVEL = "user_info.level";
    public static final String STATUS = "user_info.status";
    public static final String LOGIN_TIME = "user_info.login_time";
    public static final String PROMOTE_TIME = "user_info.promote_time";
    public static final String UPDATE_TIME = "user_info.update_time";
    public static final String CREATE_TIME = "user_info.create_time";
    public static final String TEMPORARY_USER = "user_info.temporary_user";
    public static final String VIP_SAVINGS_CARD = "user_info.vip_savings_card";


    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + id +
            ", tenantId=" + tenantId +
            ", openId=" + openId +
            ", nick=" + nick +
            ", avatar=" + avatar +
            ", mobile=" + mobile +
            ", account=" + account +
            ", password=" + password +
            ", sex=" + sex +
            ", royal=" + royal +
            ", inviterId=" + inviterId +
            ", inviterCode=" + inviterCode +
            ", registType=" + registType +
            ", bindTime=" + bindTime +
            ", version=" + version +
            ", longitude=" + longitude +
            ", address=" + address +
            ", latitude=" + latitude +
            ", remark=" + remark +
            ", name=" + name +
            ", level=" + level +
            ", status=" + status +
            ", loginTime=" + loginTime +
            ", promoteTime=" + promoteTime +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", temporaryUser=" + temporaryUser +
            ", vipSavingsCard=" + vipSavingsCard +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserInfo.ID
            ,UserInfo.TENANT_ID
            ,UserInfo.OPEN_ID
            ,UserInfo.NICK
            ,UserInfo.AVATAR
            ,UserInfo.MOBILE
            ,UserInfo.ACCOUNT
            ,UserInfo.PASSWORD
            ,UserInfo.SEX
            ,UserInfo.ROYAL
            ,UserInfo.INVITER_ID
            ,UserInfo.INVITER_CODE
            ,UserInfo.REGIST_TYPE
            ,UserInfo.BIND_TIME
            ,UserInfo.VERSION
            ,UserInfo.LONGITUDE
            ,UserInfo.ADDRESS
            ,UserInfo.LATITUDE
            ,UserInfo.REMARK
            ,UserInfo.NAME
            ,UserInfo.LEVEL
            ,UserInfo.STATUS
            ,UserInfo.LOGIN_TIME
            ,UserInfo.PROMOTE_TIME
            ,UserInfo.UPDATE_TIME
            ,UserInfo.CREATE_TIME
            ,UserInfo.TEMPORARY_USER
            ,UserInfo.VIP_SAVINGS_CARD
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}