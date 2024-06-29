package com.turing.code.user.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.*;
import com.turing.code.common.utils.encrypt.Base64Util;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.SortWay;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.returnbody.Code;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.common.wechat.ma.template.WxSubscribeUtils;
import com.turing.code.system.pojo.SysParams;
import com.turing.code.system.service.SysParamsService;
import com.turing.code.tenant.mapper.TenantInviteImageMapper;
import com.turing.code.tenant.mapper.TenantInviteMapper;
import com.turing.code.tenant.mapper.TenantProMapper;
import com.turing.code.tenant.mapper.TenantShopMapper;
import com.turing.code.tenant.pojo.*;
import com.turing.code.tenant.pojo.vo.TenantUserVo;
import com.turing.code.tenant.service.*;
import com.turing.code.user.mapper.UserInfoMapper;
import com.turing.code.user.mapper.UserRoyalFlowMapper;
import com.turing.code.user.mapper.UserRoyalFlowProMapper;
import com.turing.code.user.pojo.*;
import com.turing.code.user.pojo.vo.MyTeamVo;
import com.turing.code.user.pojo.vo.TenantProUserVo;
import com.turing.code.user.service.*;
import io.swagger.annotations.*;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author turing generator
 * @since 2021-12-18
 */
@Api(tags = "用户-用户信息")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "user/userInfo", produces = "text/plain;charset=utf-8")
public class UserInfoController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private SysParamsService sysParamsService;
    @Resource
    private UserRoyalFlowService userRoyalFlowService;
    @Resource
    private UserRoyalFlowMapper userRoyalFlowMapper;
    @Resource
    private TenantProMapper tenantProMapper;
    @Resource
    private TenantInviteImageMapper tenantInviteImageMapper;
    @Resource
    private WxMaService wxMaService;
    @Resource
    private TenantShopMapper tenantShopMapper;
    @Resource
    private UserMerchantInviteService userMerchantInviteService;
    @Resource
    private TenantInviteMapper tenantInviteMapper;
    @Resource
    private UserRoyalFlowProMapper userRoyalFlowProMapper;
    @Resource
    private UserBelongTenantService userBelongTenantService;
    @Resource
    private TenantInfoService tenantInfoService;
    @Resource
    private TenantBannerService tenantBannerService;
    @Resource
    private TenantProService tenantProService;
    @Resource
    private TenantProCategoryService tenantProCategoryService;
    @Resource
    private TenantAuditService tenantAuditService;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private UserVipLevelService userVipLevelService;
    @Resource
    private TenantVipLevelService tenantVipLevelService;

    @Value("${spring.profiles.active}")
    private String env;
    @Value("${callBack}")
    private String callBack;

    @ApiOperation("校验手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "phone", value = "电话号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "code", value = "验证码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifySign", value = "验证签名", required = true)
    })
    @ParameterVerify(notNull = {"phone", "code", "verifySign"})
    @PostMapping("checkPhone")
    public String checkPhone(String phone, String code, String verifySign) {
        //验证码验证
        JSONObject verifyResult = SignDecodeUtil.codeMatches(phone + code, verifySign, 600000);
        if (!verifyResult.getBoolean("isSuccess")) {
            return ReturnBody.error(Code.SMS_CODE_ERROR, verifyResult.getString("errorMessage"));
        }
        return ReturnBody.success(new HashMap<String, Object>(10) {{
            put("checkType", true);
        }});
    }

    @ApiOperation("获取基本信息")
    @PostMapping("getUserInfo")
    public String getUserInfo() {
        JSONObject result = new JSONObject(true);
        UserInfo info = (UserInfo) request.getAttribute("info");
//        result.put("phone", CommUtil.checkNull(info.getPhone()) ? info.getPhone() : "");
//        result.put("matchCode", CommUtil.checkNull(info.getMatchCode()) ? info.getMatchCode() : "");
        return ReturnBody.success(result);
    }

    @ApiOperation("上传头像")
    @PostMapping("uploadAvatar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "file", required = true),
    })
    public String uploadAvatar(MultipartFile file) {
        String path = FileUtil.upload("/user/", file, "image", request);
        return ReturnBody.success(path);
    }

    /**
     * ---------------------------------------------分销推广---------------------------------------------------
     */
    @PostMapping("statistics")
    @ApiOperation("推广统计")
    public String statistics() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        Long id = (Long) request.getAttribute("id");
        UserInfo userInfo = userInfoService.getById(id);
        if (!CommUtil.checkNull(userInfo)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        map.put("userId", id);
        map.put("userStatistics", "");
        //累积收益
        BigDecimal total = userInfoMapper.selectRoayalByDay(map);
        result.put("total", total);

        //今日收益
        map.put("day", 0);
        BigDecimal day = userInfoMapper.selectRoayalByDay(map);
        result.put("day", day);

        //当月收益
        map.put("day", 29);
        BigDecimal month = userInfoMapper.selectRoayalByDay(map);
        result.put("month", month);
        return ReturnBody.success(result);
    }

    @PostMapping("promoteLevel")
    @ApiOperation("推广等级")
    public String promoteLevel() {
        JSONObject jsonObject = new JSONObject();
        Long id = (Long) request.getAttribute("id");
        UserInfo userInfo = userInfoService.getById(id);
        if (!CommUtil.checkNull(userInfo) || userInfo.getLevel() == 0) {
            return ReturnBody.error("您还不是普通会员,无法参与平台推广");
        }

        //普通权限
        SysParams sysParams9 = sysParamsService.getById(CacheConstant.FIRM_DIRECT_RECOMMEND);
        SysParams sysParams10 = sysParamsService.getById(CacheConstant.FIRM_INDIRECT_RECOMMEND);
        SysParams sysParams55 = sysParamsService.getById(CacheConstant.MERCHANT_DIRECT_RECOMMEND);
        SysParams sysParams56 = sysParamsService.getById(CacheConstant.MERCHANT_INDIRECT_RECOMMEND);
        //黄金条件、权限
        SysParams sysParams35 = sysParamsService.getById(CacheConstant.GOLD_MEMBER_CONDITION);
        SysParams sysParams36 = sysParamsService.getById(CacheConstant.GOLD_MEMBER_AUTHO);
        //合伙人条件、权限
        SysParams sysParams37 = sysParamsService.getById(CacheConstant.PARTNER_CONDITION);
        SysParams sysParams38 = sysParamsService.getById(CacheConstant.PARTNER_AUTHO);
        //至尊合伙人条件、权限
        SysParams sysParams39 = sysParamsService.getById(CacheConstant.SUPER_PARTNER_CONDITION);
        SysParams sysParams40 = sysParamsService.getById(CacheConstant.SUPER_PARTNER_AUTHO);

        //会员等级图标
        SysParams sysParams43 = sysParamsService.getById(43);
        SysParams sysParams44 = sysParamsService.getById(44);
        SysParams sysParams45 = sysParamsService.getById(45);

        jsonObject.put("member", sysParams9.getValue() + "," + sysParams10.getValue() + "," + sysParams55.getValue() + "," + sysParams56.getValue());
        jsonObject.put("goldMember", sysParams35.getValue() + "," + sysParams36.getValue());
        jsonObject.put("partner", sysParams37.getValue() + "," + sysParams38.getValue());
        jsonObject.put("superPartner", sysParams39.getValue() + "," + sysParams40.getValue());


        //获取当前直推开单业绩和团队开单业绩（间推）
        int directly = userRoyalFlowMapper.selectCountSelective(UserRoyalFlow.builder().status(1).userId(id).pushType(1).build());
        int inDirectly = directly + userRoyalFlowMapper.selectCountSelective(UserRoyalFlow.builder().status(1).userId(id).pushType(2).build());

        List<String> sys = new ArrayList<>();
        //获取距离下一个等级升级条件
        switch (userInfo.getLevel()) {
            case 1:
                //普通会员，展示黄金会员升级条件
//                String[] split = sysParams35.getValue().split(",");
//                directly = Integer.parseInt(split[0]) - directly;
//                inDirectly = Integer.parseInt(split[1]) - inDirectly;
                jsonObject.put("conditionOne", sysParams35.getValue().split(",")[0]);
                jsonObject.put("conditionTwo", sysParams35.getValue().split(",")[1]);
                //封装黄金会员、合伙人、至尊条件
                sys.add(sysParams35.getValue() + "," + sysParams43.getValue() + ",2");
                sys.add(sysParams37.getValue() + "," + sysParams44.getValue() + ",3");
                sys.add(sysParams39.getValue() + "," + sysParams45.getValue() + ",4");
                jsonObject.put("name", "黄金会员");
                break;
            case 2:
                //黄金会员，展示合伙人升级条件
//                String[] twos = sysParams37.getValue().split(",");
//                directly = Integer.parseInt(twos[0]) - directly;
//                inDirectly = Integer.parseInt(twos[1]) - inDirectly;
                jsonObject.put("conditionOne", sysParams37.getValue().split(",")[0]);
                jsonObject.put("conditionTwo", sysParams37.getValue().split(",")[1]);
                sys.add(sysParams37.getValue() + "," + sysParams44.getValue() + ",3");
                sys.add(sysParams39.getValue() + "," + sysParams45.getValue() + ",4");
                jsonObject.put("name", "合伙人");
                break;
            case 3:
                //合伙人，展示至尊合伙人升级条件
//                String[] threes = sysParams39.getValue().split(",");
//                directly = Integer.parseInt(threes[0]) - directly;
//                inDirectly = Integer.parseInt(threes[1]) - inDirectly;
                jsonObject.put("conditionOne", sysParams39.getValue().split(",")[0]);
                jsonObject.put("conditionTwo", sysParams39.getValue().split(",")[1]);
                sys.add(sysParams39.getValue() + "," + sysParams45.getValue() + ",4");
                jsonObject.put("name", "至尊合伙人");
                break;
            default:
        }
        jsonObject.put("sysList", sys);
        jsonObject.put("directly", directly);
        jsonObject.put("inDirectly", inDirectly);
        jsonObject.put("level", userInfo.getLevel());
        return ReturnBody.success(jsonObject);
    }

    @PostMapping("totalAssets")
    @ApiOperation("个人总资产")
    public String totalAssets() {
        Long id = (Long) request.getAttribute("id");
        SysParams sysParams = sysParamsService.getById(32);
        UserInfo userInfo = userInfoService.getById(id);
        if (userInfo.getLevel() == 0) {
            userInfo.setLevel(1);
            if (!userInfoService.updateById(userInfo)) {
                return ReturnBody.error("升级失败");
            }
        }
        //等级图标
        switch (userInfo.getLevel()) {
            case 1:
                userInfo.setIcon(sysParamsService.getById(42).getValue());
                break;
            case 2:
                userInfo.setIcon(sysParamsService.getById(43).getValue());
                break;
            case 3:
                userInfo.setIcon(sysParamsService.getById(44).getValue());
                break;
            case 4:
                userInfo.setIcon(sysParamsService.getById(45).getValue());
                break;
            default:
                return ReturnBody.error();
        }

        userInfo.setIsMerchant(sysParams.getValue());
        if (!CommUtil.checkNull(userInfo)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        JSONObject result = (JSONObject) JSONObject.toJSON(userInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", id);
        map.put("status", 1);
        map.put("sumMoney", "sec_amount");
        result.put("frozenMoney", userRoyalFlowMapper.selectSumMoneyByUserId(map));
        return ReturnBody.success(result);
    }

    @ApiOperation("我的团队")
    @PostMapping("myTeam")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Long.class, name = "id", value = "用户id", required = false)
    public String myTeam(PageInfo pageInfo, Long id) {
        Long usId = (Long) request.getAttribute("id");
        pageInfo.setTimeScreen("create_time");
        pageInfo.setDefaultSort("create_time", SortWay.DESC);
        //执行分页辅助工具
        Page<UserInfo> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<UserInfo> iPage = userInfoMapper.selectSelective(page, new UserInfo().setInviterId(id == null ? usId.intValue() : id.intValue()));
        List<UserInfo> usInfoList = iPage.getRecords();
        List<MyTeamVo> myTeamVos = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(8);
        Map<String, Object> result = new HashMap<>(8);
        usInfoList.forEach(o -> {
            MyTeamVo myTeamVo = new MyTeamVo();
            myTeamVo.setId(o.getId());
            myTeamVo.setAvatar(o.getAvatar());
            myTeamVo.setName(o.getNick());
            myTeamVo.setCreateTime(o.getCreateTime());
            //累积收益
            BigDecimal reduce =
                    userRoyalFlowMapper.selectSelective(UserRoyalFlow.builder().userId(o.getId()).status(1).type(2).build()).stream().map(UserRoyalFlow::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            myTeamVo.setRoyal(reduce);
            map.put("id", o.getId());
            //累积开单
            myTeamVo.setTotalCount(userInfoMapper.selectRoyalCount(map));
            //今日开单
            map.put("type", 1);
            myTeamVo.setDayCount(userInfoMapper.selectRoyalCount(map));
            //下级直推团队人数(我的间推)
            int i = userInfoMapper.selectCountSelective(new UserInfo().setInviterId(o.getId().intValue()).setStatus(1));
            map.put("teamCount", i);
            //等级图标
            switch (o.getLevel()) {
                case 1:
                    myTeamVo.setIcon(sysParamsService.getById(42).getValue());
                    break;
                case 2:
                    myTeamVo.setIcon(sysParamsService.getById(43).getValue());
                    break;
                case 3:
                    myTeamVo.setIcon(sysParamsService.getById(44).getValue());
                    break;
                case 4:
                    myTeamVo.setIcon(sysParamsService.getById(45).getValue());
                    break;
                default:
                    myTeamVo.setIcon("");
            }
            myTeamVo.setDriectProCount(userInfoMapper.directCount(o.getId()));
            myTeamVo.setIndriectProCount(userInfoMapper.indirectCount(o.getId()));
            myTeamVos.add(myTeamVo);
        });
        if (CommUtil.checkNull(id)) {
            UserInfo userInfo = userInfoService.getById(id);
            if (CommUtil.checkNull(userInfo)) {
                result.put("nick", userInfo.getNick());
                result.put("avatar", userInfo.getAvatar());
            }
        }
        if (pageInfo.getPageNo() == 1) {
            TimeInterval timer = DateUtil.timer();
            //团队总人数 = 所有下级总数 + 自己（加1）
            result.put("totalCount", userInfoService.teamCount(id == null ? usId.intValue() : id.intValue()));
            LogUtil.info("【查询我的团队数量耗时】" + timer.interval());
            //直推人数
            result.put("indriectCount", userInfoMapper.indirectCount(id == null ? usId : id));
            //间推人数
            int directCount =
                    userInfoMapper.selectCountSelective(new UserInfo().setInviterId(id == null ? usId.intValue() : id.intValue()).setStatus(1));
            result.put("directCount", directCount);
        }
        result.put("list", myTeamVos);
        result.put("pages", iPage.getPages());
        result.put("total", iPage.getTotal());
        return ReturnBody.success(result);
    }

    @PostMapping("shareDetails")
    @ApiOperation("商品分享数据(生成分享二维码后调)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Long.class, name = "id", value = "商品id", required = true),
    })
    @ParameterVerify(notNull = "id")
    public String shareDetails(Long id) {
        TenantPro tenantPro = tenantProMapper.selectById(id);
        if (tenantPro == null) {
            return ReturnBody.error("没有数据");
        }
        UserInfo userInfo = userInfoService.getById((Long) request.getAttribute("id"));
        //查询商品邀请码
        Map<String, Object> imageMap = new HashMap<>();
        imageMap.put("proId", tenantPro.getId());
        imageMap.put("userId", userInfo.getId());
        imageMap.put("type", 1);
        List<TenantInviteImage> inviteImageList = tenantInviteImageMapper.selectByMap(imageMap);
        TenantInviteImage inviteImage;
        if (inviteImageList.isEmpty()) {
            inviteImage = new TenantInviteImage().setType(1).setUserId(userInfo.getId()).setProId(tenantPro.getId());
            //生成小程序码并保存
            try {
                //type 1 普通订单 2秒杀 3团购
                String param = userInfo.getId() + "," + tenantPro.getId() + ",1";
                byte[] bytes = wxMaService.getQrcodeService().createWxaCodeUnlimitBytes(param, "pages/goods/detail/detail", true, WxSubscribeUtils.STATE, 200, true, null, false);
                inviteImage.setImage(Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), bytes, false));
            } catch (Exception e) {
                return ReturnBody.error("系统繁忙，请稍后再试");
            }
            if (userInfo.getPromoteTime() == null) {
                userInfo.setPromoteTime(new Date());
            }
            if (tenantInviteImageMapper.insert(inviteImage) != 1) {
                return ReturnBody.error();
            }
        } else {
            inviteImage = inviteImageList.get(0);
        }
        JSONObject result = new JSONObject(true);
        //商品详情
        JSONObject proJson = (JSONObject) JSONObject.toJSON(tenantPro);
        //邀请二维码
        proJson.put("inviteImage", inviteImage.getImage());
        //处理优惠券，查询有没有开通优惠券服务
//        if (baseMerchantAppOpenService.selectCountSelective(MerchantAppOpen.builder().tenantId(merchantPro.gettenantId()).appId(3).status(1).build()) > 0) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("type", 1);
//            map.put("status", 1);
//            map.put("orderBy", "amount desc");
//            map.put("tenantId", merchantPro.gettenantId());
//            map.put("startPage", 0);
//            map.put("pageSize", 1);
//            List<MerchantCoupon> merchantCouponList = baseMerchantCouponService.selectByMap(map);
//            if (!merchantCouponList.isEmpty()) {
//                proJson.put("useAmount", merchantCouponList.get(0).getUseAmount());
//                proJson.put("amount", merchantCouponList.get(0).getAmount());
//            }
//        }


        result.put("merchantPro", proJson);
        //店铺详情
        List<TenantShop> merchantShops =
                tenantShopMapper.selectSelective(new TenantShop().setTenantId(tenantPro.getTenantId()));
        if (merchantShops.size() == 0) {
            return ReturnBody.error();
        }
        result.put("merchantShop", merchantShops.get(0));
        result.put("userInfo", userInfo);
        //  上级用户分销记录
        Map<String, Object> agentMap = new HashMap<>();
        agentMap.put("tenantId", tenantPro.getTenantId());
        agentMap.put("userId", userInfo.getId());
        TenantInvite agentInvite = userMerchantInviteService.selectOneByMap(agentMap);
        if (agentInvite == null) {
            agentInvite = new TenantInvite().setTenantId(tenantPro.getTenantId()).setShopId(tenantPro.getShopId())
                    .setUserId(userInfo.getId()).setProAmount(BigDecimal.ZERO).setAmount(BigDecimal.ZERO).setStatus(1).setGrade(0);
            if (tenantInviteMapper.insert(agentInvite) != 1) {
                throw new ServiceException();
            }
        }
        return ReturnBody.success(result);
    }

    @PostMapping("qrCode")
    @ApiOperation("生成小程序推广码,返回海报")
    @ParameterVerify(notNull = "scene")
    public String qrCode(String page, String scene) {
        Long usId = (Long) request.getAttribute("id");
        UserInfo usInfo = userInfoService.getById(usId);

        if (!CommUtil.checkNull(usInfo.getRemark())) {
            //生成二维码
            try {
                byte[] bytes = wxMaService.getQrcodeService().createWxaCodeUnlimitBytes(scene, "pages/login/login", true, WxSubscribeUtils.STATE, 200, true, null, false);
                usInfo.setRemark(Base64Util.generateImage(request, "/files/invite_QrCode/", bytes, false));
            } catch (WxErrorException e) {
                e.printStackTrace();
                return ReturnBody.error("生成小程序码失败");
            }
            if (usInfo.getPromoteTime() == null) {
                usInfo.setPromoteTime(new Date());
            }
            if (!userInfoService.updateById(usInfo)) {
                return ReturnBody.error("获取二维码失败");
            }
        }
        JSONObject result = new JSONObject();
        result.put("poster", sysParamsService.getById(CacheConstant.POSTER).getValue());
        result.put("qrCode", usInfo.getRemark());
        return ReturnBody.success(result);
    }

    @ApiOperation("分销员的店铺列表")
    @PostMapping("inviteShopList")
    public String inviteShopList() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", request.getAttribute("id"));
        List<Map<String, Object>> result = userMerchantInviteService.selectUserInviteShop(map);
        return ReturnBody.success(result);
    }

    @PostMapping("withdrawList")
    @ApiOperation("收支明细-可用余额")
    public String list(PageInfo pageInfo) {
        pageInfo.setTimeScreen("create_time");
        pageInfo.setDefaultSort("create_time", SortWay.DESC);
        //执行分页辅助工具
        Page<Map<String, Object>> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        Map<String, Object> map = new HashMap<>();
        map.put("usId", request.getAttribute("id"));
        map.put("orderBy", "createTime desc");
        IPage<Map<String, Object>> iPage = userRoyalFlowMapper.selectMap(page, map);
        PageResult<Map<String, Object>> pageResult = new PageResult<>();
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        return ReturnBody.success(pageResult);
/*        paging.setDefaultSort("create_time desc");
        return ReturnBody.success(PageUtil.startPage(paging, () - > userRoyalFlowService.selectSelective(UserRoyalFlow.builder().userId((Long) request.getAttribute("id")).build())));*/
    }

    @PostMapping("frozenList")
    @ApiOperation("收支明细-冻结金额")
    public String frozenList(PageInfo pageInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", request.getAttribute("id"));
        map.put("orderBy", "create_time desc");
        map.put("isPay", "");
        map.put("startTime", pageInfo.getStartTime());
        map.put("endTime", pageInfo.getEndTime());
        PageResult<UserRoyalFlowPro> pageResult = new PageResult<>();
        Page<UserRoyalFlowPro> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<UserRoyalFlowPro> iPage = userRoyalFlowProMapper.selectByMapEx(page, map);
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        pageResult.getList().forEach(e -> {
            //status     1冻结中   2已发放   3已退款
            switch (e.getStatus()) {
                case 1:
                    e.setRemark("佣金冻结中");
                    break;
                case 2:
                    e.setRemark("已转入可用余额");
                    break;
                case 3:
                    e.setRemark("商品退款");
                    break;
                default:
                    break;
            }
        });
        return ReturnBody.success(pageResult);
    }

    @PostMapping(value = "public/serviceBank")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "type", value = "0：用户，1：集体，2：市级代理，其他为商家参数", required = false)
    })
    @ApiOperation("提现到银行卡手续费/最低手续费")
    public String serviceBank(Integer type) {
        Map<String, Object> map = new HashMap<>();
        if (type != null) {
            if (type == 0) {
                map.put("serviceChargeLimit", sysParamsService.getById(CacheConstant.USER_COST_LIMIT).getValue());
                map.put("serviceCharge", sysParamsService.getById(CacheConstant.USER_COST_PROPORTION).getValue());
                return ReturnBody.success(map);
            } else if (type == 1) {
                map.put("serviceChargeLimit", sysParamsService.getById(CacheConstant.FIRM_COST_LIMIT).getValue());
                map.put("serviceCharge", sysParamsService.getById(CacheConstant.FIRM_COST_PROPORTION).getValue());
                return ReturnBody.success(map);
            } else if (type == 2) {
                map.put("serviceChargeLimit", sysParamsService.getById(CacheConstant.CITY_COST_LIMIT).getValue());
                map.put("serviceCharge", sysParamsService.getById(CacheConstant.CITY_COST_PROPORTION).getValue());
                return ReturnBody.success(map);
            } else {
                return ReturnBody.error("参数错误");
            }
        } else {
            map.put("serviceChargeLimit", sysParamsService.getById(CacheConstant.MERCHANT_COST_LIMIT).getValue());
            map.put("serviceCharge", sysParamsService.getById(CacheConstant.MERCHANT_COST_PROPORTION).getValue());
            return ReturnBody.success(map);
        }
    }

    @PostMapping("commissionList")
    @ApiOperation("返佣订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "businessType", value = "1平台 2商品推广", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "type", value = "0 审核中 1通过 -1拒绝 2已付款", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "pushType", value = "1直推 2：间推", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "userId", value = "用户id", required = false),
    })
    public String commissionList(PageInfo pageInfo, Integer businessType, Integer type, Integer pushType, Long userId) {
        Long id = (Long) request.getAttribute("id");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId == null ? id : userId);
        if (CommUtil.checkNull(businessType)) {
            map.put("businessType", businessType);
        }
        if (CommUtil.checkNull(type)) {
            map.put("type", type);
        }
        if (CommUtil.checkNull(pushType)) {
            map.put("pushType", pushType);
        }
        if ("asc".equals(pageInfo.getSortKey())) {
            map.put("orderBy", "create_time");
        } else {
            map.put("orderBy", "create_time desc");
        }
        return userRoyalFlowService.selectFlowWithAllByMap(pageInfo, map);
    }


    /**
     * 商城相关
     */
    @ApiOperation("扫码绑定商家")
    @PostMapping("scanCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商家id", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true),
    })
    @ParameterVerify(notNull = {"id", "shopId"})
    public String scanCode(Integer id, Long shopId) {
        Long rId = (Long) request.getAttribute("id");
        TenantInfo tenantInfo = tenantInfoService.getById(id);
        if (!CommUtil.checkNull(tenantInfo)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        QueryWrapper<UserBelongTenant> eq = new QueryWrapper<UserBelongTenant>().eq(UserBelongTenant.US_ID, rId)
                .eq(UserBelongTenant.TENANT_ID, id).eq(UserBelongTenant.SHOP_ID, shopId);
        UserBelongTenant userBelongTenant = userBelongTenantService.getOne(eq);
        if (!CommUtil.checkNull(userBelongTenant)) {
            userBelongTenant = new UserBelongTenant().setUsId(rId).setTenantId(id).setShopId(shopId);
            if (!userBelongTenantService.save(userBelongTenant)) {
                return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
            }
        }
        return ReturnBody.success();
    }


    @ApiOperation("获取绑定的商家列表")
    @PostMapping("bindTenant")
    public String bindTenant() {
        Long rId = (Long) request.getAttribute("id");
        List<UserBelongTenant> list = userBelongTenantService.list(new QueryWrapper<UserBelongTenant>().eq(UserBelongTenant.US_ID, rId));
        if (!CommUtil.checkNull(list)) {
            //返回默认商家
            list.add(new UserBelongTenant().setShopId(91L).setTenantId(8).setUsId(rId));
        }
        return ReturnBody.success(list);
    }


    @ApiOperation("首页-获取商家基本信息")
    @PostMapping("indexTenantInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infoId", value = "商家id", required = true),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true),
    })
    @ParameterVerify(notNull = {"shopId", "infoId"})
    public String getTenantInfo(Integer infoId, Long shopId) {
        JSONObject result = new JSONObject(true);
        TenantShop tenantShop = tenantShopMapper.selectById(shopId);
        //店铺信息
        result.put("tenantShop", tenantShop);
        //轮播图信息
        List<TenantBanner> list = tenantBannerService.list(new QueryWrapper<TenantBanner>().eq(TenantBanner.TENANT_ID, infoId));
        result.put("banner", list);
        //商家基本配置
        return ReturnBody.success(result);
    }

    @ApiOperation("首页-商品列表")
    @PostMapping("indexProList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true),
            @ApiImplicitParam(name = "infoId", value = "商家id", required = true),
    })
    @ParameterVerify(notNull = {"shopId", "infoId"})
    public String indexProList(PageInfo pageInfo, Long shopId, Integer infoId) {
        Long rId = (Long) request.getAttribute("id");
        pageInfo.setTimeScreen("create_time");
        pageInfo.setDefaultSort("create_time", SortWay.DESC);
        //执行分页辅助工具
        PageResult<TenantPro> pageResult = new PageUtil<TenantPro>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(TenantPro.SHOP_ID, shopId).eq(TenantPro.INDEX_RECOMMEND, 1).eq(TenantPro.STATUS, 1).eq(TenantPro.VIP_PRO, 0);
            tenantProService.page(page, queryWrapper);
        });
        //计算会员最多省多少钱
        BigDecimal highestDiscount=userVipLevelService.highestDiscount(new HashMap<String,Object>(){{
            put("infoId",infoId);
            put("usId",rId);
        }});
        PageResult<TenantProUserVo> userVoPageResult = new PageResult<>();
        BeanUtils.copyProperties(pageResult,userVoPageResult);
        List<TenantProUserVo> tenantProUserVos=new ArrayList<>();
        if (CommUtil.checkNull(highestDiscount)){
            pageResult.getList().forEach(item->{
                TenantProUserVo tenantProUserVo = new TenantProUserVo();
                BeanUtils.copyProperties(item,tenantProUserVo);
                //查出价格最高的规格
                BigDecimal highPrice=tenantProService.highPriceSku(item.getId());
                //计算折扣
                tenantProUserVo.setSalePrice(highPrice.multiply(highestDiscount).setScale(2, RoundingMode.DOWN));
                //标记会员折扣
                tenantProUserVo.setVipSale(1);
                tenantProUserVos.add(tenantProUserVo);
            });
            userVoPageResult.setList(tenantProUserVos);
            return ReturnBody.success(userVoPageResult);
        }else {
            return ReturnBody.success(pageResult);
        }

    }

    @ApiOperation("分类-获取分类列表")
    @PostMapping("indexCategoryList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "infoId", value = "商家id", required = true),
            @ApiImplicitParam(name = "pId", value = "父分类id,一级分类传-1", required = true),
    })
    @ParameterVerify(notNull = {"infoId", "pId"})
    public String indexCategoryList(Integer infoId, Long pId) {
        QueryWrapper<TenantProCategory> eq =
                new QueryWrapper<TenantProCategory>().eq(TenantProCategory.TENANT_ID, infoId).eq(TenantProCategory.PARENT_ID, pId);
        List<TenantProCategory> list = tenantProCategoryService.list(eq);
        return ReturnBody.success(list);
    }

    @ApiOperation("分类-根据分类获取商品")
    @PostMapping("indexProListByCategory")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true),
            @ApiImplicitParam(name = "cId", value = "分类id", required = true),
    })
    @ParameterVerify(notNull = {"shopId", "cId"})
    public String indexProListByCategory(Long cId, Long shopId) {
        QueryWrapper<TenantPro> eq =
                new QueryWrapper<TenantPro>().eq(TenantPro.SHOP_ID, shopId).eq(TenantPro.CATEGORY_ID, cId).eq(TenantPro.STATUS, 1).eq(TenantPro.VIP_PRO, 0);
        List<TenantPro> list = tenantProService.list(eq);
        return ReturnBody.success(list);
    }


}
