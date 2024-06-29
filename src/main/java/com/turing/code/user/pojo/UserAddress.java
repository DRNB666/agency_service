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
 * @since 2024-06-14
 */
@ApiModel(value="UserAddress对象", description="用户收货地址表")
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long usId;

    @ApiModelProperty(value = "收货人姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "省-市-区")
    private String region;

    @ApiModelProperty(value = "详细地址")
    private String address;

    private Integer status;

    @ApiModelProperty(value = "1默认")
    private Integer isDefault;

    private String remark;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public UserAddress setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getUsId() {
        return usId;
    }

    public UserAddress setUsId(Long usId) {
        this.usId = usId;
        return this;
    }
    public String getName() {
        return name;
    }

    public UserAddress setName(String name) {
        this.name = name;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public UserAddress setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public String getRegion() {
        return region;
    }

    public UserAddress setRegion(String region) {
        this.region = region;
        return this;
    }
    public String getAddress() {
        return address;
    }

    public UserAddress setAddress(String address) {
        this.address = address;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserAddress setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getIsDefault() {
        return isDefault;
    }

    public UserAddress setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserAddress setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserAddress setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static final String ID = "user_address.id";
    public static final String US_ID = "user_address.us_id";
    public static final String NAME = "user_address.name";
    public static final String PHONE = "user_address.phone";
    public static final String REGION = "user_address.region";
    public static final String ADDRESS = "user_address.address";
    public static final String STATUS = "user_address.status";
    public static final String IS_DEFAULT = "user_address.is_default";
    public static final String REMARK = "user_address.remark";
    public static final String CREATE_TIME = "user_address.create_time";


    @Override
    public String toString() {
        return "UserAddress{" +
            "id=" + id +
            ", usId=" + usId +
            ", name=" + name +
            ", phone=" + phone +
            ", region=" + region +
            ", address=" + address +
            ", status=" + status +
            ", isDefault=" + isDefault +
            ", remark=" + remark +
            ", createTime=" + createTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserAddress.ID
            ,UserAddress.US_ID
            ,UserAddress.NAME
            ,UserAddress.PHONE
            ,UserAddress.REGION
            ,UserAddress.ADDRESS
            ,UserAddress.STATUS
            ,UserAddress.IS_DEFAULT
            ,UserAddress.REMARK
            ,UserAddress.CREATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}