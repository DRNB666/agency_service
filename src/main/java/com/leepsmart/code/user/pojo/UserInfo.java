package com.leepsmart.code.user.pojo;

import java.math.BigDecimal;
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
 * @since 2024-07-30
 */
@ApiModel(value="UserInfo对象", description="用户信息")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty(value = "总余额")
    private BigDecimal totalAmount;

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

    @ApiModelProperty(value = "状态：1正常 -1冻结 -2注销")
    private Integer status;

    @ApiModelProperty(value = "最近登陆时间")
    private Date loginTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "邮箱（注册账号时必填）")
    private String email;

    @ApiModelProperty(value = "猎豹钱包")
    private BigDecimal lieBaoAmount;

    @ApiModelProperty(value = "飞书钱包")
    private BigDecimal sinoclickAmount;

    @ApiModelProperty(value = "维卓钱包")
    private BigDecimal wezoAmount;

    @ApiModelProperty(value = "登录态 1:在线 0:下线")
    private Integer loginStatus;

    @ApiModelProperty(value = "注册ip")
    private String registerIp;

    @ApiModelProperty(value = "登录ip")
    private String loginIp;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    public Long getId() {
        return id;
    }

    public UserInfo setId(Long id) {
        this.id = id;
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
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public UserInfo setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
    public String getEmail() {
        return email;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }
    public BigDecimal getLieBaoAmount() {
        return lieBaoAmount;
    }

    public UserInfo setLieBaoAmount(BigDecimal lieBaoAmount) {
        this.lieBaoAmount = lieBaoAmount;
        return this;
    }
    public BigDecimal getSinoclickAmount() {
        return sinoclickAmount;
    }

    public UserInfo setSinoclickAmount(BigDecimal sinoclickAmount) {
        this.sinoclickAmount = sinoclickAmount;
        return this;
    }
    public BigDecimal getWezoAmount() {
        return wezoAmount;
    }

    public UserInfo setWezoAmount(BigDecimal wezoAmount) {
        this.wezoAmount = wezoAmount;
        return this;
    }
    public Integer getLoginStatus() {
        return loginStatus;
    }

    public UserInfo setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
        return this;
    }
    public String getRegisterIp() {
        return registerIp;
    }

    public UserInfo setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
        return this;
    }
    public String getLoginIp() {
        return loginIp;
    }

    public UserInfo setLoginIp(String loginIp) {
        this.loginIp = loginIp;
        return this;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public UserInfo setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public static final String ID = "user_info.id";
    public static final String OPEN_ID = "user_info.open_id";
    public static final String NICK = "user_info.nick";
    public static final String AVATAR = "user_info.avatar";
    public static final String MOBILE = "user_info.mobile";
    public static final String ACCOUNT = "user_info.account";
    public static final String PASSWORD = "user_info.password";
    public static final String SEX = "user_info.sex";
    public static final String TOTAL_AMOUNT = "user_info.total_amount";
    public static final String VERSION = "user_info.version";
    public static final String LONGITUDE = "user_info.longitude";
    public static final String ADDRESS = "user_info.address";
    public static final String LATITUDE = "user_info.latitude";
    public static final String REMARK = "user_info.remark";
    public static final String NAME = "user_info.name";
    public static final String STATUS = "user_info.status";
    public static final String LOGIN_TIME = "user_info.login_time";
    public static final String UPDATE_TIME = "user_info.update_time";
    public static final String CREATE_TIME = "user_info.create_time";
    public static final String EMAIL = "user_info.email";
    public static final String LIE_BAO_AMOUNT = "user_info.lie_bao_amount";
    public static final String SINOCLICK_AMOUNT = "user_info.sinoclick_amount";
    public static final String WEZO_AMOUNT = "user_info.wezo_amount";
    public static final String LOGIN_STATUS = "user_info.login_status";
    public static final String REGISTER_IP = "user_info.register_ip";
    public static final String LOGIN_IP = "user_info.login_ip";
    public static final String ROLE_ID = "user_info.role_id";


    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + id +
            ", openId=" + openId +
            ", nick=" + nick +
            ", avatar=" + avatar +
            ", mobile=" + mobile +
            ", account=" + account +
            ", password=" + password +
            ", sex=" + sex +
            ", totalAmount=" + totalAmount +
            ", version=" + version +
            ", longitude=" + longitude +
            ", address=" + address +
            ", latitude=" + latitude +
            ", remark=" + remark +
            ", name=" + name +
            ", status=" + status +
            ", loginTime=" + loginTime +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", email=" + email +
            ", lieBaoAmount=" + lieBaoAmount +
            ", sinoclickAmount=" + sinoclickAmount +
            ", wezoAmount=" + wezoAmount +
            ", loginStatus=" + loginStatus +
            ", registerIp=" + registerIp +
            ", loginIp=" + loginIp +
            ", roleId=" + roleId +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserInfo.ID
            ,UserInfo.OPEN_ID
            ,UserInfo.NICK
            ,UserInfo.AVATAR
            ,UserInfo.MOBILE
            ,UserInfo.ACCOUNT
            ,UserInfo.PASSWORD
            ,UserInfo.SEX
            ,UserInfo.TOTAL_AMOUNT
            ,UserInfo.VERSION
            ,UserInfo.LONGITUDE
            ,UserInfo.ADDRESS
            ,UserInfo.LATITUDE
            ,UserInfo.REMARK
            ,UserInfo.NAME
            ,UserInfo.STATUS
            ,UserInfo.LOGIN_TIME
            ,UserInfo.UPDATE_TIME
            ,UserInfo.CREATE_TIME
            ,UserInfo.EMAIL
            ,UserInfo.LIE_BAO_AMOUNT
            ,UserInfo.SINOCLICK_AMOUNT
            ,UserInfo.WEZO_AMOUNT
            ,UserInfo.LOGIN_STATUS
            ,UserInfo.REGISTER_IP
            ,UserInfo.LOGIN_IP
            ,UserInfo.ROLE_ID
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}