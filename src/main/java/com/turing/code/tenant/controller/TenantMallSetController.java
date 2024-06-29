package com.turing.code.tenant.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.utils.ImgUrlEnum;
import com.turing.code.common.utils.encrypt.Base64Util;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantShop;
import com.turing.code.tenant.service.TenantShopService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.turing.code.tenant.service.TenantMallSetService;
import com.turing.code.tenant.pojo.TenantMallSet;

import javax.servlet.http.HttpServletRequest;

import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

/**
 * @author turing generator
 * @since 2024-06-12
 */
@Api(tags = "租户-商城配置")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantMallSet", produces = "text/plain;charset=utf-8")
public class TenantMallSetController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantMallSetService tenantMallSetService;
    @Resource
    private TenantShopService tenantShopService;


    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<TenantMallSet> pageResult = new PageUtil<TenantMallSet>(pageInfo).startPage((page, queryWrapper) -> tenantMallSetService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(TenantMallSet tenantMallSet) {
        tenantMallSet.setCreateTime(null).setStatus(null).setUpdateTime(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(tenantMallSet.getId())) {
                return ReturnBody.error();
            }
        }
        if (!tenantMallSetService.saveOrUpdate(tenantMallSet)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return tenantMallSetService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

    @ApiOperation("获取商城配置和商户信息")
    @PostMapping("getMallSetAndShopInfo")
    public String getMallSet() {
        JSONObject result = new JSONObject(true);
        Integer rId = (Integer) request.getAttribute("pId");
        QueryWrapper<TenantMallSet> eq = new QueryWrapper<TenantMallSet>().eq(TenantMallSet.TENANT_ID, rId);
        TenantMallSet tenantMallSet = tenantMallSetService.getOne(eq);
        result.put("tenantMallSet", tenantMallSet);
        QueryWrapper<TenantShop> Eq = new QueryWrapper<TenantShop>().eq(TenantShop.TENANT_ID, rId);
        TenantShop tenantShop = tenantShopService.getOne(Eq);
        result.put("tenantShop", tenantShop);
        return ReturnBody.success(result);
    }

    @ApiOperation("设置商户信息")
    @PostMapping("setShopInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopPhoto", value = "店铺封面", required = true),
            @ApiImplicitParam(name = "shopName", value = "店铺名称", required = true),
            @ApiImplicitParam(name = "phone", value = "联系电话", required = true),
            @ApiImplicitParam(name = "content", value = "店铺介绍", required = true),
    })
    @ParameterVerify(notNull = {"shopPhoto","shopName","phone","content"})
    public String setShopInfo(String shopPhoto, String shopName, String phone, String content) {
        Integer rId = (Integer) request.getAttribute("pId");
        QueryWrapper<TenantShop> eq = new QueryWrapper<TenantShop>().eq(TenantShop.TENANT_ID, rId);
        TenantShop tenantShop = tenantShopService.getOne(eq);
        if (CommUtil.checkNull(shopPhoto) && shopPhoto.contains("data:image")) {
            shopPhoto = Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), shopPhoto, false);
        }
        tenantShop.setShopPhoto(shopPhoto).setName(shopName).setPhone(phone).setContent(content);
        if (!tenantShopService.updateById(tenantShop)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }
    
    @ApiOperation("设置商城配置")
    @PostMapping("setMall")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "afterSaleDay", value = "自动确认收货天数", required = true),
             @ApiImplicitParam(name = "unit", value = "商城单位1积分 2人民币", required = true),
             @ApiImplicitParam(name = "allowWithdrawal", value = "是否允许提现1允许 0不允许", required = true),
             @ApiImplicitParam(name = "vipSales", value = "是否开启会员卡分销1开启 0不开启", required = true),
             @ApiImplicitParam(name = "payType", value = "支付方式1会员储蓄卡 2线下支付", required = true),
     })
    @ParameterVerify(notNull = {"afterSaleDay","unit","allowWithdrawal","vipSales","payType"})
    public String setMall(Integer afterSaleDay,Integer unit,Integer allowWithdrawal,Integer vipSales,Integer payType){
        Integer rId = (Integer) request.getAttribute("pId");
        QueryWrapper<TenantMallSet> eq = new QueryWrapper<TenantMallSet>().eq(TenantMallSet.TENANT_ID, rId);
        TenantMallSet tenantMallSet = tenantMallSetService.getOne(eq);
        tenantMallSet.setAfterSaleDay(afterSaleDay).setUnit(unit).setAllowWithdrawal(allowWithdrawal).setVipSales(vipSales).setPayType(payType);
        if (!tenantMallSetService.updateById(tenantMallSet)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }
    

}
