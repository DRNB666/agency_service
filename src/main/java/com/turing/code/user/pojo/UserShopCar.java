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
 * @since 2024-06-13
 */
@ApiModel(value="UserShopCar对象", description="用户-购物车")
public class UserShopCar implements Serializable {

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

    @ApiModelProperty(value = "商品id")
    private Long proId;

    @ApiModelProperty(value = "规格id")
    private Long skuId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "数量")
    private Integer count;

    public Integer getId() {
        return id;
    }

    public UserShopCar setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserShopCar setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserShopCar setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserShopCar setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserShopCar setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public UserShopCar setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public UserShopCar setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Long getProId() {
        return proId;
    }

    public UserShopCar setProId(Long proId) {
        this.proId = proId;
        return this;
    }
    public Long getSkuId() {
        return skuId;
    }

    public UserShopCar setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }
    public Integer getUserId() {
        return userId;
    }

    public UserShopCar setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
    public Integer getCount() {
        return count;
    }

    public UserShopCar setCount(Integer count) {
        this.count = count;
        return this;
    }

    public static final String ID = "user_shop_car.id";
    public static final String STATUS = "user_shop_car.status";
    public static final String REMARK = "user_shop_car.remark";
    public static final String CREATE_TIME = "user_shop_car.create_time";
    public static final String UPDATE_TIME = "user_shop_car.update_time";
    public static final String TEST_TIME = "user_shop_car.test_time";
    public static final String VERSION = "user_shop_car.version";
    public static final String PRO_ID = "user_shop_car.pro_id";
    public static final String SKU_ID = "user_shop_car.sku_id";
    public static final String USER_ID = "user_shop_car.user_id";
    public static final String COUNT = "user_shop_car.count";


    @Override
    public String toString() {
        return "UserShopCar{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", proId=" + proId +
            ", skuId=" + skuId +
            ", userId=" + userId +
            ", count=" + count +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserShopCar.ID
            ,UserShopCar.STATUS
            ,UserShopCar.REMARK
            ,UserShopCar.CREATE_TIME
            ,UserShopCar.UPDATE_TIME
            ,UserShopCar.TEST_TIME
            ,UserShopCar.VERSION
            ,UserShopCar.PRO_ID
            ,UserShopCar.SKU_ID
            ,UserShopCar.USER_ID
            ,UserShopCar.COUNT
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}