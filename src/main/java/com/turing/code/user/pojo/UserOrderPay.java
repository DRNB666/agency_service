package com.turing.code.user.pojo;

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
 * @author turing generator
 * @since 2024-06-13
 */
@ApiModel(value="UserOrderPay对象", description="支付订单")
public class UserOrderPay implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "团购商品id")
    private Long groupProId;

    private Date createTime;

    @ApiModelProperty(value = "冗余商品信息(商品头图，标题，规格名) avatar,title,specName")
    private String proInfo;

    @ApiModelProperty(value = "秒杀id")
    private Long spikeId;

    @ApiModelProperty(value = "团队id")
    private Long teamId;

    @ApiModelProperty(value = "商家id ")
    private Long tenantId;

    @ApiModelProperty(value = "预留手机号")
    private String phone;

    @ApiModelProperty(value = "商品id")
    private Long proId;

    @ApiModelProperty(value = "店铺id ")
    private Long shopId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    private String remark;

    @ApiModelProperty(value = "订单状态：	0：待支付	1：待使用(团购自提待使用，物流订单待发货)	2：待评价(已使用、团购待评价，物流订单待收货) 	3：已评价(团购已评价，物流订单待评价)	4: 物流订单已完成	5：团购待成团		10：待发货	11：待收货	-3: 团购未成团	-1：已退款	-2：已取消		前端显示查询状态   ↓↓↓	20：已申请退款	21：已申请退货退款	22：已退款	23：退款失败		负数为未成功状态，数据统计 只统计状态大于0的")
    private Integer status;

    private Date updateTime;

    @ApiModelProperty(value = "1普通订单 2秒杀订单 3团购订单 4物流订单")
    private Integer type;

    @ApiModelProperty(value = "优惠券抵消金额")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "实价(单个价格)")
    private BigDecimal price;

    @ApiModelProperty(value = "原价(单个价格)")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "商品总价（实价*数量）")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "应付金额（加上运费，减去优惠券）")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "核销二维码")
    private String qrCode;

    @ApiModelProperty(value = "优惠券领取id")
    private Long couponUserId;

    private BigDecimal couponAmount;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "销量")
    private Integer sales;

    @ApiModelProperty(value = "核销时间")
    private Date dealTime;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "快递单号")
    private String expressNum;

    @ApiModelProperty(value = "快递公司")
    private String express;

    @ApiModelProperty(value = "1自提 2外送")
    private Integer carriage;

    @ApiModelProperty(value = "配送地址")
    private String address;

    @ApiModelProperty(value = "收货人")
    private String consignee;

    @ApiModelProperty(value = "售后状态：-2商品不能售后 -1未支付  0：未售后 1：申请中 2：商家同意退货(用户待退货)	3：退货运输中 4：商家拒绝|退货退款 5:商家拒绝|用户退货后 7：商家拒绝|仅退款 	8：用户主动取消售后	10：售后完成 11：售后失败	12：售后过期(解冻各平台抽佣，分销员佣金)")
    private Integer afterSellStatus;

    @ApiModelProperty(value = "售后类型：1：仅退款 2：退货退款")
    private Integer afterSellType;

    @ApiModelProperty(value = "cityCode")
    private Integer cityCode;

    @ApiModelProperty(value = "用户备注")
    private String userRemark;

    @ApiModelProperty(value = "最新运送状态	0-暂无轨迹信息 1-已揽收 2-在途中 3-签收 4-问题件 5-转寄 6-清关  ")
    private Integer shippingState;

    @ApiModelProperty(value = "0:未打单 1:已打单")
    private Integer billingStatus;

    @ApiModelProperty(value = "0:未订阅 1:已订阅")
    private Integer subStatus;

    @ApiModelProperty(value = "0:未预约 1:已预约")
    private Integer prePickUp;

    @ApiModelProperty(value = "0:未打印 1:已打印 3:不支持")
    private Integer printStatus;

    @ApiModelProperty(value = "主订单号")
    private Integer masterOrder;

    @ApiModelProperty(value = "运单模板html")
    private String printTemplate;

    @ApiModelProperty(value = "网点id")
    private Integer webPointId;

    @ApiModelProperty(value = "快递鸟订单号")
    private String kdnOrder;

    @ApiModelProperty(value = "物流下单模式	0:默认	1:普通	2:电子	3:预约")
    private Integer kdnType;

    @ApiModelProperty(value = "地图轨迹url")
    private String mapUrl;

    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    public Long getId() {
        return id;
    }

    public UserOrderPay setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getGroupProId() {
        return groupProId;
    }

    public UserOrderPay setGroupProId(Long groupProId) {
        this.groupProId = groupProId;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public UserOrderPay setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getProInfo() {
        return proInfo;
    }

    public UserOrderPay setProInfo(String proInfo) {
        this.proInfo = proInfo;
        return this;
    }
    public Long getSpikeId() {
        return spikeId;
    }

    public UserOrderPay setSpikeId(Long spikeId) {
        this.spikeId = spikeId;
        return this;
    }
    public Long getTeamId() {
        return teamId;
    }

    public UserOrderPay setTeamId(Long teamId) {
        this.teamId = teamId;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public UserOrderPay setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public UserOrderPay setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public Long getProId() {
        return proId;
    }

    public UserOrderPay setProId(Long proId) {
        this.proId = proId;
        return this;
    }
    public Long getShopId() {
        return shopId;
    }

    public UserOrderPay setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }
    public Integer getUserId() {
        return userId;
    }

    public UserOrderPay setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public UserOrderPay setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserOrderPay setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public UserOrderPay setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public UserOrderPay setType(Integer type) {
        this.type = type;
        return this;
    }
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public UserOrderPay setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
        return this;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public UserOrderPay setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public UserOrderPay setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public UserOrderPay setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public UserOrderPay setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
        return this;
    }
    public BigDecimal getFreight() {
        return freight;
    }

    public UserOrderPay setFreight(BigDecimal freight) {
        this.freight = freight;
        return this;
    }
    public String getQrCode() {
        return qrCode;
    }

    public UserOrderPay setQrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }
    public Long getCouponUserId() {
        return couponUserId;
    }

    public UserOrderPay setCouponUserId(Long couponUserId) {
        this.couponUserId = couponUserId;
        return this;
    }
    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public UserOrderPay setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
        return this;
    }
    public Date getPayTime() {
        return payTime;
    }

    public UserOrderPay setPayTime(Date payTime) {
        this.payTime = payTime;
        return this;
    }
    public Integer getSales() {
        return sales;
    }

    public UserOrderPay setSales(Integer sales) {
        this.sales = sales;
        return this;
    }
    public Date getDealTime() {
        return dealTime;
    }

    public UserOrderPay setDealTime(Date dealTime) {
        this.dealTime = dealTime;
        return this;
    }
    public Integer getCount() {
        return count;
    }

    public UserOrderPay setCount(Integer count) {
        this.count = count;
        return this;
    }
    public String getExpressNum() {
        return expressNum;
    }

    public UserOrderPay setExpressNum(String expressNum) {
        this.expressNum = expressNum;
        return this;
    }
    public String getExpress() {
        return express;
    }

    public UserOrderPay setExpress(String express) {
        this.express = express;
        return this;
    }
    public Integer getCarriage() {
        return carriage;
    }

    public UserOrderPay setCarriage(Integer carriage) {
        this.carriage = carriage;
        return this;
    }
    public String getAddress() {
        return address;
    }

    public UserOrderPay setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getConsignee() {
        return consignee;
    }

    public UserOrderPay setConsignee(String consignee) {
        this.consignee = consignee;
        return this;
    }
    public Integer getAfterSellStatus() {
        return afterSellStatus;
    }

    public UserOrderPay setAfterSellStatus(Integer afterSellStatus) {
        this.afterSellStatus = afterSellStatus;
        return this;
    }
    public Integer getAfterSellType() {
        return afterSellType;
    }

    public UserOrderPay setAfterSellType(Integer afterSellType) {
        this.afterSellType = afterSellType;
        return this;
    }
    public Integer getCityCode() {
        return cityCode;
    }

    public UserOrderPay setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
        return this;
    }
    public String getUserRemark() {
        return userRemark;
    }

    public UserOrderPay setUserRemark(String userRemark) {
        this.userRemark = userRemark;
        return this;
    }
    public Integer getShippingState() {
        return shippingState;
    }

    public UserOrderPay setShippingState(Integer shippingState) {
        this.shippingState = shippingState;
        return this;
    }
    public Integer getBillingStatus() {
        return billingStatus;
    }

    public UserOrderPay setBillingStatus(Integer billingStatus) {
        this.billingStatus = billingStatus;
        return this;
    }
    public Integer getSubStatus() {
        return subStatus;
    }

    public UserOrderPay setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
        return this;
    }
    public Integer getPrePickUp() {
        return prePickUp;
    }

    public UserOrderPay setPrePickUp(Integer prePickUp) {
        this.prePickUp = prePickUp;
        return this;
    }
    public Integer getPrintStatus() {
        return printStatus;
    }

    public UserOrderPay setPrintStatus(Integer printStatus) {
        this.printStatus = printStatus;
        return this;
    }
    public Integer getMasterOrder() {
        return masterOrder;
    }

    public UserOrderPay setMasterOrder(Integer masterOrder) {
        this.masterOrder = masterOrder;
        return this;
    }
    public String getPrintTemplate() {
        return printTemplate;
    }

    public UserOrderPay setPrintTemplate(String printTemplate) {
        this.printTemplate = printTemplate;
        return this;
    }
    public Integer getWebPointId() {
        return webPointId;
    }

    public UserOrderPay setWebPointId(Integer webPointId) {
        this.webPointId = webPointId;
        return this;
    }
    public String getKdnOrder() {
        return kdnOrder;
    }

    public UserOrderPay setKdnOrder(String kdnOrder) {
        this.kdnOrder = kdnOrder;
        return this;
    }
    public Integer getKdnType() {
        return kdnType;
    }

    public UserOrderPay setKdnType(Integer kdnType) {
        this.kdnType = kdnType;
        return this;
    }
    public String getMapUrl() {
        return mapUrl;
    }

    public UserOrderPay setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
        return this;
    }
    public Long getOrderId() {
        return orderId;
    }

    public UserOrderPay setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public static final String ID = "user_order_pay.id";
    public static final String GROUP_PRO_ID = "user_order_pay.group_pro_id";
    public static final String CREATE_TIME = "user_order_pay.create_time";
    public static final String PRO_INFO = "user_order_pay.pro_info";
    public static final String SPIKE_ID = "user_order_pay.spike_id";
    public static final String TEAM_ID = "user_order_pay.team_id";
    public static final String TENANT_ID = "user_order_pay.tenant_id";
    public static final String PHONE = "user_order_pay.phone";
    public static final String PRO_ID = "user_order_pay.pro_id";
    public static final String SHOP_ID = "user_order_pay.shop_id";
    public static final String USER_ID = "user_order_pay.user_id";
    public static final String REMARK = "user_order_pay.remark";
    public static final String STATUS = "user_order_pay.status";
    public static final String UPDATE_TIME = "user_order_pay.update_time";
    public static final String TYPE = "user_order_pay.type";
    public static final String DISCOUNT_PRICE = "user_order_pay.discount_price";
    public static final String PRICE = "user_order_pay.price";
    public static final String ORIGINAL_PRICE = "user_order_pay.original_price";
    public static final String TOTAL_AMOUNT = "user_order_pay.total_amount";
    public static final String PAY_AMOUNT = "user_order_pay.pay_amount";
    public static final String FREIGHT = "user_order_pay.freight";
    public static final String QR_CODE = "user_order_pay.qr_code";
    public static final String COUPON_USER_ID = "user_order_pay.coupon_user_id";
    public static final String COUPON_AMOUNT = "user_order_pay.coupon_amount";
    public static final String PAY_TIME = "user_order_pay.pay_time";
    public static final String SALES = "user_order_pay.sales";
    public static final String DEAL_TIME = "user_order_pay.deal_time";
    public static final String COUNT = "user_order_pay.count";
    public static final String EXPRESS_NUM = "user_order_pay.express_num";
    public static final String EXPRESS = "user_order_pay.express";
    public static final String CARRIAGE = "user_order_pay.carriage";
    public static final String ADDRESS = "user_order_pay.address";
    public static final String CONSIGNEE = "user_order_pay.consignee";
    public static final String AFTER_SELL_STATUS = "user_order_pay.after_sell_status";
    public static final String AFTER_SELL_TYPE = "user_order_pay.after_sell_type";
    public static final String CITY_CODE = "user_order_pay.city_code";
    public static final String USER_REMARK = "user_order_pay.user_remark";
    public static final String SHIPPING_STATE = "user_order_pay.shipping_state";
    public static final String BILLING_STATUS = "user_order_pay.billing_status";
    public static final String SUB_STATUS = "user_order_pay.sub_status";
    public static final String PRE_PICK_UP = "user_order_pay.pre_pick_up";
    public static final String PRINT_STATUS = "user_order_pay.print_status";
    public static final String MASTER_ORDER = "user_order_pay.master_order";
    public static final String PRINT_TEMPLATE = "user_order_pay.print_template";
    public static final String WEB_POINT_ID = "user_order_pay.web_point_id";
    public static final String KDN_ORDER = "user_order_pay.kdn_order";
    public static final String KDN_TYPE = "user_order_pay.kdn_type";
    public static final String MAP_URL = "user_order_pay.map_url";
    public static final String ORDER_ID = "user_order_pay.order_id";


    @Override
    public String toString() {
        return "UserOrderPay{" +
            "id=" + id +
            ", groupProId=" + groupProId +
            ", createTime=" + createTime +
            ", proInfo=" + proInfo +
            ", spikeId=" + spikeId +
            ", teamId=" + teamId +
            ", tenantId=" + tenantId +
            ", phone=" + phone +
            ", proId=" + proId +
            ", shopId=" + shopId +
            ", userId=" + userId +
            ", remark=" + remark +
            ", status=" + status +
            ", updateTime=" + updateTime +
            ", type=" + type +
            ", discountPrice=" + discountPrice +
            ", price=" + price +
            ", originalPrice=" + originalPrice +
            ", totalAmount=" + totalAmount +
            ", payAmount=" + payAmount +
            ", freight=" + freight +
            ", qrCode=" + qrCode +
            ", couponUserId=" + couponUserId +
            ", couponAmount=" + couponAmount +
            ", payTime=" + payTime +
            ", sales=" + sales +
            ", dealTime=" + dealTime +
            ", count=" + count +
            ", expressNum=" + expressNum +
            ", express=" + express +
            ", carriage=" + carriage +
            ", address=" + address +
            ", consignee=" + consignee +
            ", afterSellStatus=" + afterSellStatus +
            ", afterSellType=" + afterSellType +
            ", cityCode=" + cityCode +
            ", userRemark=" + userRemark +
            ", shippingState=" + shippingState +
            ", billingStatus=" + billingStatus +
            ", subStatus=" + subStatus +
            ", prePickUp=" + prePickUp +
            ", printStatus=" + printStatus +
            ", masterOrder=" + masterOrder +
            ", printTemplate=" + printTemplate +
            ", webPointId=" + webPointId +
            ", kdnOrder=" + kdnOrder +
            ", kdnType=" + kdnType +
            ", mapUrl=" + mapUrl +
            ", orderId=" + orderId +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            UserOrderPay.ID
            ,UserOrderPay.GROUP_PRO_ID
            ,UserOrderPay.CREATE_TIME
            ,UserOrderPay.PRO_INFO
            ,UserOrderPay.SPIKE_ID
            ,UserOrderPay.TEAM_ID
            ,UserOrderPay.TENANT_ID
            ,UserOrderPay.PHONE
            ,UserOrderPay.PRO_ID
            ,UserOrderPay.SHOP_ID
            ,UserOrderPay.USER_ID
            ,UserOrderPay.REMARK
            ,UserOrderPay.STATUS
            ,UserOrderPay.UPDATE_TIME
            ,UserOrderPay.TYPE
            ,UserOrderPay.DISCOUNT_PRICE
            ,UserOrderPay.PRICE
            ,UserOrderPay.ORIGINAL_PRICE
            ,UserOrderPay.TOTAL_AMOUNT
            ,UserOrderPay.PAY_AMOUNT
            ,UserOrderPay.FREIGHT
            ,UserOrderPay.QR_CODE
            ,UserOrderPay.COUPON_USER_ID
            ,UserOrderPay.COUPON_AMOUNT
            ,UserOrderPay.PAY_TIME
            ,UserOrderPay.SALES
            ,UserOrderPay.DEAL_TIME
            ,UserOrderPay.COUNT
            ,UserOrderPay.EXPRESS_NUM
            ,UserOrderPay.EXPRESS
            ,UserOrderPay.CARRIAGE
            ,UserOrderPay.ADDRESS
            ,UserOrderPay.CONSIGNEE
            ,UserOrderPay.AFTER_SELL_STATUS
            ,UserOrderPay.AFTER_SELL_TYPE
            ,UserOrderPay.CITY_CODE
            ,UserOrderPay.USER_REMARK
            ,UserOrderPay.SHIPPING_STATE
            ,UserOrderPay.BILLING_STATUS
            ,UserOrderPay.SUB_STATUS
            ,UserOrderPay.PRE_PICK_UP
            ,UserOrderPay.PRINT_STATUS
            ,UserOrderPay.MASTER_ORDER
            ,UserOrderPay.PRINT_TEMPLATE
            ,UserOrderPay.WEB_POINT_ID
            ,UserOrderPay.KDN_ORDER
            ,UserOrderPay.KDN_TYPE
            ,UserOrderPay.MAP_URL
            ,UserOrderPay.ORDER_ID
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}