package com.turing.code.tenant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.tenant.mapper.TenantInfoFlowingMapper;
import com.turing.code.tenant.mapper.TenantInviteConfigMapper;
import com.turing.code.tenant.mapper.TenantInviteMapper;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantInviteConfig;
import com.turing.code.tenant.pojo.vo.TenantInviteVO;
import com.turing.code.tenant.pojo.vo.UserRoyalFlowProVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.tenant.service.TenantInviteService;
import com.turing.code.tenant.pojo.TenantInvite;
import javax.servlet.http.HttpServletRequest;
import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-01
 */
@Api(tags = "租户-分销员店铺关系")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantInvite", produces = "text/plain;charset=utf-8")
public class TenantInviteController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantInviteService tenantInviteService;
    @Resource
    private TenantInfoFlowingMapper tenantInfoFlowingMapper;
    @Resource
    private TenantInviteConfigMapper tenantInviteConfigMapper;
    @Resource
    private TenantInviteMapper tenantInviteMapper;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<TenantInvite> pageResult = new PageUtil<TenantInvite>(pageInfo).startPage((page, queryWrapper) -> tenantInviteService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(TenantInvite tenantInvite) {
        tenantInvite.setCreateTime(null).setStatus(null).setUpdateTime(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(tenantInvite.getId())) {
                return ReturnBody.error();
            }
        }
        if (!tenantInviteService.saveOrUpdate(tenantInvite)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return tenantInviteService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

    @ApiOperation("分佣记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "type", value = "推荐类型:1直推 2：间推", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "nick", value = "用户昵称", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "realName", value = "真实姓名", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "mobile", value = "手机号", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "isInside", value = "是否内部员工 1是  0否", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "orderId", value = "订单号", required = false),
    })
    @PostMapping(value = "rewardFlow")
    public String rewardFlow(PageInfo pageInfo, Integer type, String nick, String realName, String mobile, Integer isInside, Long orderId) {
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        Map<String,Object> map = new HashMap<>(10);
        map.put("tenantId", merchantInfo.getId());
        if (CommUtil.checkNull(pageInfo.getEndTime())){
            map.put("endTime", pageInfo.getEndTime());
        }
        if (CommUtil.checkNull(pageInfo.getStartTime())){
            map.put("startTime", pageInfo.getStartTime());
        }
        if (CommUtil.checkNull(nick)){
            map.put("nickLike", nick);
        }
        if (CommUtil.checkNull(realName)){
            map.put("realNameLike", realName);
        }
        if (CommUtil.checkNull(type)){
            map.put("type", type);
        }
        if (CommUtil.checkNull(isInside)){
            map.put("isInside", isInside);
        }
        if (CommUtil.checkNull(mobile)){
            map.put("mobile", mobile);
        }
        if (CommUtil.checkNull(orderId)){
            map.put("orderId", orderId);
        }
        map.put("orderBy", "i.create_time desc");
        PageResult<UserRoyalFlowProVO> pageResult = new PageResult<>();
        Page<UserRoyalFlowProVO> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<UserRoyalFlowProVO> iPage = tenantInfoFlowingMapper.selectVoByMap(page,map);
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("分销配置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "goldPerson", value = "黄金推广员升级条件(个人业绩", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "goldTeam", value = "黄金推广员升级条件(团队业绩", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "partnerPerson", value = "合伙人推广员升级条件(个人业绩", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "partnerTeam", value = "合伙人推广员升级条件(团队业绩", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "superPartnerPerson", value = "至尊合伙人推广员升级条件(个人业绩", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "superPartnerTeam", value = "至尊合伙人推广员升级条件(团队业绩", required = true),

            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "goldDirect", value = "黄金推广员获得的佣金(直推)单位%", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "goldIndirect", value = "黄金推广员获得的佣金(间推)单位%", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "partnerDirect", value = "合伙人推广员获得的佣金(直推)单位%", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "partnerIndirect", value = "合伙人推广员获得的佣金(间推)单位%", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "superPartnerDirect", value = "至尊合伙人推广员获得的佣金(直推)单位%", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "superPartnerIndirect", value = "至尊合伙人推广员获得的佣金(间推)单位%", required = true),

            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "userDirect", value = "普通推广员获得的佣金(直推)单位%", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "userIndirect", value = "普通推广员获得的佣金(间推)单位%", required = true),
    })
    @ParameterVerify(notNull = {"goldPerson", "goldTeam", "partnerPerson", "partnerTeam", "superPartnerPerson", "superPartnerTeam", "userIndirect",
            "goldDirect", "goldIndirect", "partnerDirect", "partnerIndirect", "superPartnerDirect", "superPartnerIndirect", "userDirect"})
    @PostMapping(value = "setConfig")
    public String setConfig(Integer goldPerson, Integer goldTeam, Integer partnerPerson, Integer partnerTeam, Integer superPartnerPerson,
                            Integer superPartnerTeam, BigDecimal goldDirect, BigDecimal goldIndirect, BigDecimal partnerDirect, BigDecimal partnerIndirect,
                            BigDecimal superPartnerDirect, BigDecimal superPartnerIndirect, BigDecimal userDirect, BigDecimal userIndirect){

        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        //查询有没有开通服务
        TenantInviteConfig merchantInviteConfig = tenantInviteConfigMapper.selectByMerchantId(merchantInfo.getId());
        TenantInviteConfig inviteConfig = new TenantInviteConfig();
        inviteConfig.setGoldPerson(goldPerson);
        inviteConfig.setGoldTeam(goldTeam);
        inviteConfig.setPartnerPerson(partnerPerson);
        inviteConfig.setPartnerTeam(partnerTeam);
        inviteConfig.setSuperPartnerPerson(superPartnerPerson);
        inviteConfig.setSuperPartnerTeam(superPartnerTeam);
        inviteConfig.setGoldDirect(goldDirect);
        inviteConfig.setGoldIndirect(goldIndirect);
        inviteConfig.setPartnerDirect(partnerDirect);
        inviteConfig.setPartnerIndirect(partnerIndirect);
        inviteConfig.setSuperPartnerDirect(superPartnerDirect);
        inviteConfig.setSuperPartnerIndirect(superPartnerIndirect);
        inviteConfig.setUserDirect(userDirect);
        inviteConfig.setUserIndirect(userIndirect);
        inviteConfig.setTenantId(merchantInfo.getId().longValue());

        if (merchantInviteConfig == null) {
            if (tenantInviteConfigMapper.insert(inviteConfig) != 1) {
                return ReturnBody.error();
            }
        } else {
            inviteConfig.setId(merchantInviteConfig.getId());
            if (tenantInviteConfigMapper.updateById(inviteConfig) != 1) {
                return ReturnBody.error();
            }
        }
        return ReturnBody.success();
    }

    @ApiOperation("分销配置列表")
    @PostMapping(value = "configList")
    public String configList(){
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        TenantInviteConfig inviteConfig = tenantInviteConfigMapper.selectByMerchantId(merchantInfo.getId());
        return ReturnBody.success(inviteConfig == null ? "" : inviteConfig);
    }


    @ApiOperation("分销员列表")
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "shopId", value = "店铺ID", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "name", value = "用户昵称/真实姓名", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "mobile", value = "手机号", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "isInside", value = "是否内部员工 1是  0否", required = false),
    })
    @PostMapping(value = "inviteList")
    public String inviteList(PageInfo pageInfo, Long shopId, String name, String mobile, Integer isInside){
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        Map<String,Object> map = new HashMap<>(10);
        map.put("tenantId", merchantInfo.getId());
        if (CommUtil.checkNull(pageInfo.getEndTime())){
            map.put("endTime", pageInfo.getEndTime());
        }
        if (CommUtil.checkNull(pageInfo.getStartTime())){
            map.put("startTime", pageInfo.getStartTime());
        }
        if (CommUtil.checkNull(shopId)){
            map.put("shopId", shopId);
        }
        if (CommUtil.checkNull(name)){
            map.put("nameLike", name);
        }
        if (CommUtil.checkNull(mobile)){
            map.put("mobile", mobile);
        }
        if (CommUtil.checkNull(isInside)){
            map.put("isInside", isInside);
        }
        map.put("flagCount", "");
        map.put("orderBy", "i.create_time desc");
        PageResult<TenantInviteVO> pageResult = new PageResult<>();
        Page<TenantInviteVO> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<TenantInviteVO> iPage = tenantInviteMapper.selectByMapWithUser(page,map);
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("取消分销员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "id", required = true),
    })
    @ParameterVerify(notNull = "id")
    @PostMapping(value = "cancelInvite")
    public String cancelInvite(Long id){
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        TenantInvite merchantInvite = tenantInviteService.getById(id);
        if (merchantInvite == null || merchantInfo.getId().longValue()!=merchantInvite.getTenantId() || merchantInvite.getStatus() != 1) {
            return ReturnBody.error("没有数据");
        }
        merchantInvite.setStatus(2);
        if (!tenantInviteService.updateById(merchantInvite)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }

    @ApiOperation("恢复分销员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "id", required = true),
    })
    @ParameterVerify(notNull = "id")
    @PostMapping(value = "setInvite")
    public String setInvite(Long id){
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        TenantInvite merchantInvite = tenantInviteService.getById(id);
        if (merchantInvite == null || merchantInfo.getId().longValue()!=merchantInvite.getTenantId() || merchantInvite.getStatus() != 2) {
            return ReturnBody.error("没有数据");
        }
        merchantInvite.setStatus(1);
        if (!tenantInviteService.updateById(merchantInvite)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }

    @ApiOperation("团队列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "分销员ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "name", value = "用户昵称/真实姓名", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "mobile", value = "手机号", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "isInside", value = "是否内部员工 1是  0否", required = false),
    })
    @ParameterVerify(notNull = "id")
    @PostMapping(value = "teamList")
    public String teamList(PageInfo pageInfo, String name, String mobile, Integer isInside, Long id) {
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        Map<String,Object> map = new HashMap<>(10);
        map.put("tenantId", merchantInfo.getId());
        map.put("agentId", id);
        if (CommUtil.checkNull(pageInfo.getEndTime())){
            map.put("endTime", pageInfo.getEndTime());
        }
        if (CommUtil.checkNull(pageInfo.getStartTime())){
            map.put("startTime", pageInfo.getStartTime());
        }
        if (CommUtil.checkNull(name)){
            map.put("nameLike", name);
        }
        if (CommUtil.checkNull(mobile)){
            map.put("mobile", mobile);
        }
        if (CommUtil.checkNull(isInside)){
            map.put("isInside", isInside);
        }
        map.put("orderBy", "i.create_time desc");
        map.put("flagCount", "");
        PageResult<TenantInviteVO> pageResult = new PageResult<>();
        Page<TenantInviteVO> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<TenantInviteVO> iPage = tenantInviteMapper.selectByMapWithUser(page,map);
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("标识操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "isInside", value = "1是内部员工 0不是", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "realName", value = "真实姓名", required = false),
    })
    @ParameterVerify(notNull = "id")
    @PostMapping(value = "markInvite")
    public String markInvite(Integer isInside, String realName, Long id){
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        TenantInvite merchantInvite = tenantInviteService.getById(id);
        if (merchantInvite == null || merchantInfo.getId().longValue()!=merchantInvite.getTenantId()) {
            return ReturnBody.error("没有数据");
        }
        merchantInvite.setIsInside(isInside);
        merchantInvite.setRealName(realName);
        if (!tenantInviteService.updateById(merchantInvite)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "列表ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "grade", value = "等级 0普通  1黄金  2合伙人  3至尊合伙人", required = true),
    })
    @ApiOperation("修改商家业务员等级")
    @PostMapping(value = "changeShopLevel")
    @ParameterVerify(notNull = {"id", "grade"})
    public String changeShopLevel(Long id, Integer grade) {
        TenantInvite merchantInvite = tenantInviteService.getById(id);
        if (merchantInvite == null || grade < 0 || grade > 3) {
            return ReturnBody.error("没有数据");
        }
        merchantInvite.setGrade(grade);
        if (!tenantInviteService.updateById(merchantInvite)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }
}
