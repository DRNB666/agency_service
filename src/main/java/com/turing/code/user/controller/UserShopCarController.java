package com.turing.code.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.tenant.pojo.*;
import com.turing.code.tenant.service.TenantProService;
import com.turing.code.tenant.service.TenantProSkuService;
import com.turing.code.tenant.service.TenantProSpecService;
import com.turing.code.tenant.service.TenantVipLevelService;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.pojo.UserVipLevel;
import com.turing.code.user.pojo.vo.TenantProSkuVo;
import com.turing.code.user.pojo.vo.UserShopCarVo;
import com.turing.code.user.service.UserVipLevelService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.turing.code.user.service.UserShopCarService;
import com.turing.code.user.pojo.UserShopCar;

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
import java.util.Date;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-07
 */
@Api(tags = "用户-购物车")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "user/userShopCar", produces = "text/plain;charset=utf-8")
public class UserShopCarController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserShopCarService userShopCarService;
    @Resource
    private TenantProService tenantProService;
    @Resource
    private TenantProSpecService tenantProSpecService;
    @Resource
    private TenantProSkuService tenantProSkuService;
    @Resource
    private UserVipLevelService userVipLevelService;
    @Resource
    private TenantVipLevelService tenantVipLevelService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        Long rId = (Long) request.getAttribute("id");
        List<UserShopCarVo> list = new ArrayList<>();
        PageResult<UserShopCar> pageResult = new PageUtil<UserShopCar>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(UserShopCar.USER_ID, rId);
            userShopCarService.page(page, queryWrapper);
        });
        pageResult.getList().forEach(item -> {
            UserShopCarVo userShopCarVo = new UserShopCarVo();
            BeanUtils.copyProperties(item, userShopCarVo);
            //查询商品信息
            TenantPro pro = tenantProService.getById(item.getProId());
            if (CommUtil.checkNull(pro)) {
                userShopCarVo.setProCover(pro.getCover());
                userShopCarVo.setProName(pro.getName());
            }
            //查询规格信息
            TenantProSku tenantProSku = tenantProSkuService.getById(item.getSkuId());
            if (CommUtil.checkNull(tenantProSku)) {
                userShopCarVo.setSkuName(tenantProSku.getSpec());
                userShopCarVo.setPrice(tenantProSku.getCurPrice());
                //计算会员等级，实际支付价格
                QueryWrapper<UserVipLevel> eq = new QueryWrapper<UserVipLevel>().eq(UserVipLevel.US_ID, rId).eq(UserVipLevel.TENANT_ID,
                        pro.getTenantId()).ge(UserVipLevel.DUE_TIME, new Date());
                UserVipLevel userVipLevel = userVipLevelService.getOne(eq);
                if (CommUtil.checkNull(userVipLevel)) {
                    //计算原价
                    TenantVipLevel tenantVipLevel = tenantVipLevelService.getById(userVipLevel.getVipLevelId());
                    //计算折扣，实际价格
                    BigDecimal vipSale = tenantProSku.getCurPrice().multiply(BigDecimal.valueOf(tenantVipLevel.getDiscountRatio()))
                            .setScale(2, RoundingMode.DOWN);
                   userShopCarVo.setVipPrice(vipSale);
                }
            }
            list.add(userShopCarVo);
        });
        PageResult<UserShopCarVo> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setList(list);
        return ReturnBody.success(result);
    }

    @ApiOperation("添加商品到购物车")
    @PostMapping("addShopCar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proId", value = "商品id", required = true),
            @ApiImplicitParam(name = "skuId", value = "规格id", required = true),
    })
    @ParameterVerify(notNull = {"proId", "skuId"})
    public String addShopCar(Long proId, Long skuId) {
        Long rId = (Long) request.getAttribute("id");
        QueryWrapper<UserShopCar> eq = new QueryWrapper<UserShopCar>().eq(UserShopCar.PRO_ID, proId)
                .eq(UserShopCar.SKU_ID, skuId).eq(UserShopCar.USER_ID, rId);
        if (userShopCarService.count(eq) == 0) {
            UserShopCar userShopCar = new UserShopCar().setCount(1).setProId(proId).setSkuId(skuId).setUserId(rId.intValue());
            if (!userShopCarService.save(userShopCar)) {
                return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
            }
        }
        return ReturnBody.success();
    }

    @ApiOperation("编辑购物车")
    @PostMapping("editShopCar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "购物车id", required = true),
            @ApiImplicitParam(name = "count", value = "数量(为0将从购物车删除)", required = true),
    })
    @ParameterVerify(notNull = {"id", "count"})
    public String editShopCar(Integer id, Integer count) {
        UserShopCar userShopCar = userShopCarService.getById(id);
        if (!CommUtil.checkNull(userShopCar)){
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (count <= 0) {
            if (!userShopCarService.removeById(userShopCar)) {
                return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
            }
        } else {
            userShopCar.setCount(count);
            if (!userShopCarService.updateById(userShopCar)) {
                return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
            }
        }
        return ReturnBody.success();
    }
    
    @ApiOperation("商品详情")
    @PostMapping("proDetail")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "proId", value = "商品id", required = true),
     })
    @ParameterVerify(notNull = "proId")
    public String proDetail(Long proId){
        Long rId = (Long) request.getAttribute("id");
        JSONObject result = new JSONObject(true);
        //查询商品信息
        TenantPro tenantPro = tenantProService.getById(proId);
        if (!CommUtil.checkNull(tenantPro)){
            return ReturnBody.error("商品不存在");
        }
        if (tenantPro.getStatus()!=1){
            return ReturnBody.error("商品已下架");
        }
        result.put("pro",tenantPro);
        //查询规格sku信息
        List<TenantProSku> skuList = tenantProSkuService.list(new QueryWrapper<TenantProSku>().eq(TenantProSku.PRO_ID, proId));
        //查询会员等级
        QueryWrapper<UserVipLevel> eq = new QueryWrapper<UserVipLevel>().eq(UserVipLevel.US_ID, rId).eq(UserVipLevel.TENANT_ID,
                tenantPro.getTenantId()).eq(UserVipLevel.STATUS,1);
        UserVipLevel userVipLevel = userVipLevelService.getOne(eq);
        if (CommUtil.checkNull(userVipLevel)){
            List<TenantProSkuVo> tenantProSkuVos=new ArrayList<>();
            TenantVipLevel tenantVipLevel = tenantVipLevelService.getById(userVipLevel.getVipLevelId());
            //计算折扣
            skuList.forEach(item->{
                TenantProSkuVo tenantProSkuVo = new TenantProSkuVo();
                BeanUtils.copyProperties(item,tenantProSkuVo);
                BigDecimal vipSale = item.getCurPrice().multiply(BigDecimal.valueOf(tenantVipLevel.getDiscountRatio()))
                        .setScale(2, RoundingMode.DOWN);
                tenantProSkuVo.setVipPrice(vipSale);
                tenantProSkuVos.add(tenantProSkuVo);
            });
            result.put("skuList",tenantProSkuVos);
        }else {
            //无会员等级，返回默认商品规格
            result.put("skuList",skuList);
        }
        //查询规格spec信息
        List<TenantProSpec> specList = tenantProSpecService.list(new QueryWrapper<TenantProSpec>().eq(TenantProSpec.PRO_ID, proId));
        result.put("specList",specList);
        return ReturnBody.success(result);
    }

}
