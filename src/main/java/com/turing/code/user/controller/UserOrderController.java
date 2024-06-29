package com.turing.code.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.tenant.pojo.TenantProSku;
import com.turing.code.tenant.pojo.TenantVipLevel;
import com.turing.code.tenant.service.TenantProService;
import com.turing.code.tenant.service.TenantProSkuService;
import com.turing.code.tenant.service.TenantVipLevelService;
import com.turing.code.user.pojo.*;
import com.turing.code.user.pojo.vo.TenantProSkuVo;
import com.turing.code.user.service.UserAddressService;
import com.turing.code.user.service.UserShopCarService;
import com.turing.code.user.service.UserVipLevelService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.turing.code.user.service.UserOrderService;

import javax.servlet.http.HttpServletRequest;

import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author turing generator
 * @since 2024-06-13
 */
@Api(tags = "用户-订单")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "user/userOrder", produces = "text/plain;charset=utf-8")
public class UserOrderController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private UserShopCarService userShopCarService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private TenantProSkuService tenantProSkuService;
    @Resource
    private TenantProService tenantProService;
    @Resource
    private UserVipLevelService userVipLevelService;
    @Resource
    private TenantVipLevelService tenantVipLevelService;

    @ApiOperation("购物车结算订单")
    @PostMapping("shopCarSettlement")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressId", value = "地址id", required = true),
            @ApiImplicitParam(name = "payType", value = "支付方式1会员储蓄卡 2线下支付", required = true),
    })
    @ParameterVerify(notNull = {"addressId","payType"})
    public String shopCarSettlement(Long addressId,Integer payType) {
        Long rId = (Long) request.getAttribute("id");
        QueryWrapper<UserShopCar> Eq = new QueryWrapper<UserShopCar>().eq(UserShopCar.USER_ID, rId);
        List<UserShopCar> userShopCar = userShopCarService.list(Eq);
        if (!CommUtil.checkNull(userShopCar)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        UserOrder order = new UserOrder().setUsId(rId).setTotalAmount(BigDecimal.ZERO).setPayType(payType);
        while (true) {
            String orderNum = CommUtil.createRandom(true, 10);
            QueryWrapper<UserOrder> eq = new QueryWrapper<UserOrder>().eq(UserOrder.ORDER_ID, orderNum);
            UserOrder userOrder = userOrderService.getOne(eq);
            if (!CommUtil.checkNull(userOrder)) {
                order.setOrderId(Long.parseLong(orderNum));
                break;
            }
        }
        //订单详情
        UserAddress userAddress = userAddressService.getById(addressId);
        if (!CommUtil.checkNull(userAddress)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        List<UserOrderPay> userOrderPays = new ArrayList<>();
        List<TenantProSku> tenantProSkus = new ArrayList<>();
        for (UserShopCar shopCar : userShopCar) {
            TenantPro tenantPro = tenantProService.getById(shopCar.getProId());
            if (!CommUtil.checkNull(tenantPro) || tenantPro.getStatus() != 1) {
                return ReturnBody.error("商品不存在或已下架");
            }
            UserOrderPay userOrderPay = new UserOrderPay().setAddress(String.format("%s %s", userAddress.getRegion(), userAddress.getAddress()))
                    .setPhone(userAddress.getPhone()).setConsignee(userAddress.getName()).setProId(shopCar.getProId())
                    .setShopId(tenantPro.getShopId()).setUserId(rId.intValue()).setStatus(0).setType(1).setCount(shopCar.getCount())
                    .setFreight(tenantPro.getFreight());
            TenantProSku tenantProSku = tenantProSkuService.getById(shopCar.getSkuId());
            if (!CommUtil.checkNull(tenantProSku)) {
                return ReturnBody.error("规格错误，无法下单");
            }
            //校验库存
            if (shopCar.getCount() < tenantProSku.getStock()) {
                return ReturnBody.error(String.format("%s规格库存不足", tenantProSku.getSpec()));
            }
            tenantProSku.setStock(tenantProSku.getStock() - shopCar.getCount());
            tenantProSkus.add(tenantProSku);
            //封装商品信息
            JSONObject proInfoObj = new JSONObject(true);
            proInfoObj.put("avatar", tenantPro.getCover());
            proInfoObj.put("title", tenantPro.getName());
            proInfoObj.put("specName", tenantProSku.getSpec());
            proInfoObj.put("specAvatar", tenantProSku.getCover());
            userOrderPay.setProInfo(proInfoObj.toJSONString());
            //设置价格
            userOrderPay.setPrice(tenantProSku.getCurPrice());

            //计算会员等级，实际支付价格
            QueryWrapper<UserVipLevel> eq = new QueryWrapper<UserVipLevel>().eq(UserVipLevel.US_ID, rId).eq(UserVipLevel.TENANT_ID,
                    tenantPro.getTenantId()).ge(UserVipLevel.DUE_TIME, new Date());
            UserVipLevel userVipLevel = userVipLevelService.getOne(eq);
            if (CommUtil.checkNull(userVipLevel)) {//有会员等级
                //计算原价
                BigDecimal originalPrice = tenantProSku.getCurPrice().multiply(BigDecimal.valueOf(shopCar.getCount()));
                userOrderPay.setOriginalPrice(originalPrice);
                userOrderPay.setTotalAmount(originalPrice);
                TenantVipLevel tenantVipLevel = tenantVipLevelService.getById(userVipLevel.getVipLevelId());
                //计算折扣，实际价格
                BigDecimal vipSale = tenantProSku.getCurPrice().multiply(BigDecimal.valueOf(tenantVipLevel.getDiscountRatio()))
                        .setScale(2, RoundingMode.DOWN);
                BigDecimal price = vipSale.multiply(BigDecimal.valueOf(shopCar.getCount()));
                userOrderPay.setPayAmount(price);
                userOrderPay.setPrice(price);
            } else {//无会员等级
                BigDecimal originalPrice = tenantProSku.getCurPrice().multiply(BigDecimal.valueOf(shopCar.getCount()));
                userOrderPay.setOriginalPrice(originalPrice);
                userOrderPay.setPrice(originalPrice);
                userOrderPay.setTotalAmount(originalPrice);
                userOrderPay.setPayAmount(originalPrice);
            }
            userOrderPays.add(userOrderPay);

            order.setTotalAmount(order.getTotalAmount().add(userOrderPay.getTotalAmount()));
            order.setTenantId(tenantPro.getTenantId().intValue());
            order.setReceiptStatus(0);
            order.setPhone(userAddress.getPhone());
            order.setPayAmount(order.getPayAmount().add(userOrderPay.getPayAmount()));
            order.setFreightAmount(order.getFreightAmount().add(userOrderPay.getFreight()));
        }
        order.setCount(userOrderPays.stream().mapToInt(UserOrderPay::getCount).sum());
        userOrderService.shopCarSettlement(order, userOrderPays, tenantProSkus);
        return ReturnBody.success();
    }

    @ApiOperation("提交订单")
    @PostMapping("submitOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proId", value = "商品id", required = true),
            @ApiImplicitParam(name = "skuId", value = "skuId", required = true),
            @ApiImplicitParam(name = "count", value = "数量", required = true),
            @ApiImplicitParam(name = "payType", value = "支付方式1会员储蓄卡 2线下支付", required = true),
    })
    @ParameterVerify(notNull = {"proId", "skuId", "addressId", "count","payType"})
    public String submitOrder(Long proId, Long skuId, Long addressId, Integer count,Integer payType) {
        Long rId = (Long) request.getAttribute("id");
        UserAddress userAddress = userAddressService.getById(addressId);
        if (!CommUtil.checkNull(userAddress)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        TenantPro tenantPro = tenantProService.getById(proId);
        if (!CommUtil.checkNull(tenantPro) || tenantPro.getStatus() != 1) {
            return ReturnBody.error("商品不存在或已下架");
        }
        UserOrder order = new UserOrder().setUsId(rId).setTotalAmount(BigDecimal.ZERO).setPayType(payType);
        while (true) {
            String orderNum = CommUtil.createRandom(true, 10);
            QueryWrapper<UserOrder> eq = new QueryWrapper<UserOrder>().eq(UserOrder.ORDER_ID, orderNum);
            UserOrder userOrder = userOrderService.getOne(eq);
            if (!CommUtil.checkNull(userOrder)) {
                order.setOrderId(Long.parseLong(orderNum));
                break;
            }
        }
        UserOrderPay userOrderPay = new UserOrderPay().setAddress(String.format("%s %s", userAddress.getRegion(), userAddress.getAddress()))
                .setPhone(userAddress.getPhone()).setConsignee(userAddress.getName()).setProId(proId).setCount(count).setFreight(tenantPro.getFreight())
                .setShopId(tenantPro.getShopId()).setUserId(rId.intValue()).setStatus(0).setType(1);
        TenantProSku tenantProSku = tenantProSkuService.getById(skuId);
        if (!CommUtil.checkNull(tenantProSku)) {
            return ReturnBody.error("规格错误，无法下单");
        }
        //校验库存
        if (tenantProSku.getStock() <= 0) {
            return ReturnBody.error(String.format("%s规格库存不足", tenantProSku.getSpec()));
        }
        tenantProSku.setStock(tenantProSku.getStock() - 1);
        //封装商品信息
        JSONObject proInfoObj = new JSONObject(true);
        proInfoObj.put("avatar", tenantPro.getCover());
        proInfoObj.put("title", tenantPro.getName());
        proInfoObj.put("specName", tenantProSku.getSpec());
        proInfoObj.put("specAvatar", tenantProSku.getCover());
        userOrderPay.setProInfo(proInfoObj.toJSONString());
        //设置价格
        userOrderPay.setPrice(tenantProSku.getCurPrice());
        //计算会员等级，实际支付价格
        QueryWrapper<UserVipLevel> eq = new QueryWrapper<UserVipLevel>().eq(UserVipLevel.US_ID, rId).eq(UserVipLevel.TENANT_ID,
                tenantPro.getTenantId()).ge(UserVipLevel.DUE_TIME, new Date());
        UserVipLevel userVipLevel = userVipLevelService.getOne(eq);
        if (CommUtil.checkNull(userVipLevel)) {
            //计算原价
            BigDecimal originalPrice = tenantProSku.getCurPrice().multiply(BigDecimal.valueOf(count));
            userOrderPay.setOriginalPrice(originalPrice);
            userOrderPay.setTotalAmount(originalPrice);
            TenantVipLevel tenantVipLevel = tenantVipLevelService.getById(userVipLevel.getVipLevelId());
            //计算折扣，实际价格
            BigDecimal vipSale = tenantProSku.getCurPrice().multiply(BigDecimal.valueOf(tenantVipLevel.getDiscountRatio()))
                    .setScale(2, RoundingMode.DOWN);
            BigDecimal price = vipSale.multiply(BigDecimal.valueOf(count));
            userOrderPay.setPayAmount(price);
            userOrderPay.setPrice(price);
        } else {//无会员价格
            BigDecimal originalPrice = tenantProSku.getCurPrice().multiply(BigDecimal.valueOf(count));
            userOrderPay.setOriginalPrice(originalPrice);
            userOrderPay.setPrice(originalPrice);
            userOrderPay.setTotalAmount(originalPrice);
            userOrderPay.setPayAmount(originalPrice);
        }
        order.setTotalAmount(userOrderPay.getTotalAmount());
        order.setTenantId(tenantPro.getTenantId().intValue());
        order.setReceiptStatus(0);
        order.setPhone(userAddress.getPhone());
        order.setCount(count);
        order.setPayAmount(userOrderPay.getPayAmount());
        order.setFreightAmount(userOrderPay.getFreight());
        userOrderService.submitOrder(order, userOrderPay);
        return ReturnBody.success();
    }


}
