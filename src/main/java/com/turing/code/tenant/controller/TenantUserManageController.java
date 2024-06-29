package com.turing.code.tenant.controller;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.tenant.pojo.TenantInfoFlowing;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.tenant.pojo.TenantProSku;
import com.turing.code.tenant.pojo.TenantVipLevel;
import com.turing.code.tenant.pojo.vo.TenantUserVo;
import com.turing.code.tenant.pojo.vo.UserSearchVo;
import com.turing.code.tenant.service.TenantProService;
import com.turing.code.tenant.service.TenantProSkuService;
import com.turing.code.tenant.service.TenantVipLevelService;
import com.turing.code.user.pojo.*;
import com.turing.code.user.service.UserBelongTenantService;
import com.turing.code.user.service.UserInfoService;
import com.turing.code.user.service.UserOrderService;
import com.turing.code.user.service.UserVipLevelService;
import io.swagger.annotations.*;
import jodd.util.ArraysUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author turing generator
 * @since 2024-06-01
 */
@Api(tags = "租户-用户管理")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/userManage", produces = "text/plain;charset=utf-8")
public class TenantUserManageController {
    @Resource
    private UserBelongTenantService userBelongTenantService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private TenantVipLevelService tenantVipLevelService;
    @Resource
    private TenantProService tenantProService;
    @Resource
    private UserVipLevelService userVipLevelService;
    @Resource
    private TenantProSkuService tenantProSkuService;
    @Resource
    private UserOrderService userOrderService;


    @ApiOperation("用户列表")
    @PostMapping("userList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键词搜索", required = false),
            @ApiImplicitParam(name = "level", value = "分销等级", required = false),
            @ApiImplicitParam(name = "startTime", value = "注册开始时间", required = false),
            @ApiImplicitParam(name = "endTime", value = "注册结束时间", required = false),
    })
    public String userList(PageInfo pageInfo, String keyword, Integer level, String startTime, String endTime) {
        Integer pId = (Integer) request.getAttribute("pId");
        PageResult<TenantUserVo> pageResult = new PageResult<>();
        Page<TenantUserVo> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize(),false);
        //这里是自定义sql
        IPage<TenantUserVo> iPage = userBelongTenantService.userList(page, new HashMap<String, Object>() {{
            put("keyword", keyword);
            put("pId", pId);
            put("level", level);
            put("startTime", startTime);
            put("endTime", endTime);
        }});
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("充值余额")
    @PostMapping("recharge")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "money", value = "金额", required = true),
    })
    @ParameterVerify(notNull = "money")
    public String recharge(BigDecimal money, Long usId) {
        Integer pId = (Integer) request.getAttribute("pId");
        if (money.compareTo(BigDecimal.ZERO) == 0) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        //添加用户储蓄卡流水
        UserVipFlow userVipFlow = new UserVipFlow().setAmount(money).setStatus(1).setUsId(usId).setSource(1).setTenantId(pId);
        //记录商家收入流水
        TenantInfoFlowing flowS =
                new TenantInfoFlowing().setTenantId(pId.longValue()).setAmount(money).setDescr("租户充值用户储蓄卡余额").setType(1).setStatus(16);
        //记录商家支出流水
        TenantInfoFlowing flowZ =
                new TenantInfoFlowing().setTenantId(pId.longValue()).setAmount(money).setDescr("租户充值用户储蓄卡余额").setType(2).setStatus(16);
        //增加用户储蓄卡余额
        UserInfo userInfo = userInfoService.getById(usId);
        if (!CommUtil.checkNull(userInfo)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        userInfo.setVipSavingsCard(userInfo.getVipSavingsCard().add(money));
        userInfoService.recharge(userVipFlow, userInfo, flowS, flowZ);
        return ReturnBody.success();
    }

    @ApiOperation("开通礼包")
    @PostMapping("openedVip")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proId", value = "会员礼包id", required = true),
            @ApiImplicitParam(name = "skuId", value = "规格id", required = true),
            @ApiImplicitParam(name = "usId", value = "用户id", required = true),
            @ApiImplicitParam(name = "openTime", value = "开通时间", required = true),
    })
    @ParameterVerify(notNull = {"proId", "usId", "openTime", "skuId"})
    public String openedVip(Integer proId, Long usId, Long openTime, Long skuId) {
        Integer pId = (Integer) request.getAttribute("pId");
        TenantPro tenantPro = tenantProService.getById(proId);
        if (!CommUtil.checkNull(tenantPro) || tenantPro.getVipPro() == 0) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        TenantInfoFlowing flowS = null;
        TenantInfoFlowing flowZ = null;
        UserVipFlow userVipFlow = null;
        UserVipLevel rawUserVip = null;
        UserOrder userOrder;

        //赠送金额处理
        UserInfo userInfo = userInfoService.getById(usId);
        if (CommUtil.checkNull(tenantPro.getGiftAmount()) && tenantPro.getGiftAmount().compareTo(BigDecimal.ZERO) != 0) {
            //添加用户储蓄卡流水
            userVipFlow = new UserVipFlow().setAmount(tenantPro.getGiftAmount()).setStatus(1).setUsId(usId).setSource(2).setTenantId(pId);
            //记录商家收入流水
            flowS = new TenantInfoFlowing().setTenantId(pId.longValue()).setAmount(tenantPro.getGiftAmount())
                    .setDescr("租户充值用户储蓄卡余额").setType(1).setStatus(16);
            //记录商家支出流水
            flowZ = new TenantInfoFlowing().setTenantId(pId.longValue()).setAmount(tenantPro.getGiftAmount())
                    .setDescr("租户充值用户储蓄卡余额").setType(2).setStatus(16);
            //修改用户储蓄卡余额
            userInfo.setVipSavingsCard(userInfo.getVipSavingsCard().add(tenantPro.getGiftAmount()));
        }
        //会员等级处理
        if (CommUtil.checkNull(tenantPro.getGiftVipLevel())) {
            //覆盖用户会员等级,删除原会员等级，添加新等级
            rawUserVip = new UserVipLevel().setVipLevelId(tenantPro.getGiftVipLevel()).setOpenTime(new Date(openTime)).setUsId(usId)
                    .setTenantId(pId.longValue());
            //查询会员等级，计算到期时间
            TenantVipLevel tenantVipLevel = tenantVipLevelService.getById(tenantPro.getGiftVipLevel());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(rawUserVip.getOpenTime());
            calendar.add(Calendar.DAY_OF_MONTH, tenantVipLevel.getDay());
            rawUserVip.setDueTime(calendar.getTime());
        }
        //推广等级处理
        if (CommUtil.checkNull(tenantPro.getGiftPromoteLeve())) {
            //覆盖用户推广等级
            userInfo.setLevel(tenantPro.getGiftPromoteLeve());
        }
        //查询规格库存
        TenantProSku tenantProSku = tenantProSkuService.getById(skuId);
        if (!CommUtil.checkNull(tenantProSku)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (!tenantProSku.getProId().equals(proId.longValue())){
            return ReturnBody.error("商品规格和所选商品不一致，请检查");
        }
        if (tenantProSku.getStock() < 1) {
            return ReturnBody.error("规格库存不足");
        }
        //生成新订单
        while (true) {
            String orderNum = CommUtil.createRandom(true, 10);
            QueryWrapper<UserOrder> eq = new QueryWrapper<UserOrder>().eq(UserOrder.ORDER_ID, orderNum);
            int queryOrder = userOrderService.count(eq);
            if (queryOrder == 0) {
                userOrder = new UserOrder().setType(1).setUsId(usId).setTotalAmount(tenantProSku.getCurPrice()).setCount(1)
                        .setOrderId(Long.parseLong(orderNum)).setTenantId(pId).setPayAmount(tenantProSku.getCurPrice())
                        .setFreightAmount(tenantPro.getFreight()).setPayType(2);
                break;
            }
        }
        //封装商品信息
        JSONObject proInfoObj = new JSONObject(true);
        proInfoObj.put("avatar", tenantPro.getCover());
        proInfoObj.put("title", tenantPro.getName());
        proInfoObj.put("specName", tenantProSku.getSpec());
        proInfoObj.put("specAvatar", tenantProSku.getCover());
        UserOrderPay userOrderPay = new UserOrderPay().setCount(1).setProInfo(proInfoObj.toJSONString()).setPayAmount(tenantProSku.getCurPrice())
                .setTotalAmount(tenantProSku.getCurPrice()).setShopId(tenantPro.getShopId()).setProId(tenantPro.getId())
                .setTenantId(tenantPro.getTenantId()).setPrice(tenantProSku.getCurPrice()).setPrice(tenantProSku.getCurPrice())
                .setOrderId(userOrder.getOrderId());
        //提交事务
        userVipLevelService.openedVip(flowS, flowZ, userVipFlow, userInfo, userOrder,rawUserVip,userOrderPay);
        return ReturnBody.success();
    }

    @ApiOperation("根据手机号获取用户信息")
    @PostMapping("queryUserInfoByPhone")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "关键词搜索", required = true),
    })
    public String queryUserInfoByPhone(String search) {
        if (!CommUtil.checkNull(search)) {
            return ReturnBody.success();
        }
        QueryWrapper<UserInfo> eq = new QueryWrapper<UserInfo>().like(UserInfo.MOBILE, search);
        List<UserInfo> list = userInfoService.list(eq);
        List<UserSearchVo> userSearchVos = new ArrayList<>();
        if (CommUtil.checkNull(list)) {
            list.forEach(item -> {
                UserSearchVo userSearchVo = new UserSearchVo();
                BeanUtils.copyProperties(item, userSearchVo);
                //查询上级，如果有的话
                if (CommUtil.checkNull(item.getInviterId())) {
                    UserInfo userInfo = userInfoService.getById(item.getInviterId());
                    userSearchVo.setParentAvatar(userInfo.getAvatar());
                    userSearchVo.setParentLevel(userInfo.getLevel());
                    userSearchVo.setParentName(userInfo.getName());
                }
                userSearchVo.setNameAndPhone(String.format("%s(%s)", item.getMobile() == null ? "未绑定" : item.getMobile(), "真实姓名".equals(
                        item.getName()) ? item.getNick() : item.getName()));
                userSearchVos.add(userSearchVo);
            });
        }
        return ReturnBody.success(userSearchVos);
    }

    @ApiOperation("获取所有礼包和规格")
    @PostMapping("queryAllVipProAndSku")
    public String queryAllVipProAndSku() {
        JSONObject result = new JSONObject(true);
        Integer rId = (Integer) request.getAttribute("pId");
        List<TenantPro> list =
                tenantProService.list(new QueryWrapper<TenantPro>().eq(TenantPro.VIP_PRO, 1).eq(TenantPro.STATUS, 1).eq(TenantPro.TENANT_ID,rId));
        result.put("proList", list);
        List<TenantProSku> allSkuList=new ArrayList<>();
        list.forEach(item -> {
            List<TenantProSku> skuList = tenantProSkuService.list(new QueryWrapper<TenantProSku>().eq(TenantProSku.PRO_ID, item.getId()));
            allSkuList.addAll(skuList);
        });
        result.put("skuList",allSkuList);
        return ReturnBody.success(result);
    }

}
