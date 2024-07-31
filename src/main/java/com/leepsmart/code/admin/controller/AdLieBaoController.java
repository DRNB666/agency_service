package com.leepsmart.code.admin.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.liebao.service.LieBaoService;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.pojo.SortWay;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import com.leepsmart.code.system.service.SysLieBaoAccountService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Api(tags = "后台-猎豹相关接口")
@ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 401, message = "无用户权限"), @ApiResponse(code = 403, message = "无资源权限"), @ApiResponse(code = 404, message = "找不到接口"),})
@RestController
@RequestMapping(value = "admin/lieBao", produces = "text/plain;charset=utf-8")
public class AdLieBaoController {

    @Resource
    private SysLieBaoAccountService sysLieBaoAccountService;
    @Resource
    private LieBaoService lieBaoService;

    @ApiOperation("获取账户列表")
    @PostMapping("getLieBaoAccount")
    @ApiImplicitParams({@ApiImplicitParam(name = "mediaType", value = "媒体选择 1:meta", required = false), @ApiImplicitParam(name = "status", value = "状态选择0:全部 1:激活 2:禁用", required = true), @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = false),})
    public String getLieBaoAccount(PageInfo pageInfo, Integer mediaType, Integer status, String keyword) {
        pageInfo.setTimeScreen("create_time");
        pageInfo.setDefaultSort("create_time", SortWay.DESC);
        //执行分页辅助工具
        PageResult<SysLieBaoAccount> pageResult = new PageResult<>();
        Page<SysLieBaoAccount> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<SysLieBaoAccount> iPage = sysLieBaoAccountService.accountList(page, new HashMap<String, Object>() {{
            put("status", status);
            put("keyword", keyword);
        }});
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());

        return ReturnBody.success(pageResult);
    }

    @ApiOperation("绑定BM")
    @PostMapping("bindBm")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId", value = "猎豹账户id", required = true), @ApiImplicitParam(name = "bmId", value = "bmId", required = true), @ApiImplicitParam(name = "type", value = "绑定的权限类型 1查看广告表现 2管理广告系列", required = true),})
    @ParameterVerify(notNull = {"accountId", "bmId", "type"})
    public String bindBm(Long accountId, String bmId, Integer type) {
        //查询账户列表是否存在该账户
        QueryWrapper<SysLieBaoAccount> eq = new QueryWrapper<SysLieBaoAccount>().eq(SysLieBaoAccount.ACCOUNT_ID, accountId);
        SysLieBaoAccount sysLieBaoAccount = sysLieBaoAccountService.getOne(eq);
        if (!CommUtil.checkNull(sysLieBaoAccount)) {
            return ReturnBody.error("账户不存在");
        }
        if (lieBaoService.facebookAccountGrant(String.valueOf(accountId), bmId, type)) {
            return ReturnBody.success("绑定bm成功");
        } else {
            return ReturnBody.error("绑定bm失败");
        }
    }

    @ApiOperation("查广告账户绑定的BM")
    @PostMapping("bmList")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId", value = "猎豹账户id", required = true),})
    @ParameterVerify(notNull = "accountId")
    public String bmList(Long accountId) {
        //查询账户列表是否存在该账户
        QueryWrapper<SysLieBaoAccount> eq = new QueryWrapper<SysLieBaoAccount>().eq(SysLieBaoAccount.ACCOUNT_ID, accountId);
        SysLieBaoAccount sysLieBaoAccount = sysLieBaoAccountService.getOne(eq);
        if (!CommUtil.checkNull(sysLieBaoAccount)) {
            return ReturnBody.error("账户不存在");
        }
        String result = lieBaoService.businessAccountBindings(String.valueOf(accountId));
        return ReturnBody.success(JSONArray.parseArray(result));
    }

    @ApiIgnore
    @ApiOperation("同步账户列表")
    @PostMapping("syncAccountList")
    public String syncAccountList(Integer agentType) {
        String accounts = lieBaoService.fbAccountList();
        JSONArray accountArray = JSONArray.parseArray(accounts);
        List<SysLieBaoAccount> sysLieBaoAccounts = new ArrayList<>();
        accountArray.forEach(item -> {
            JSONObject itemObj = JSONObject.parseObject(item.toString());
            SysLieBaoAccount sysLieBaoAccount = new SysLieBaoAccount();
            sysLieBaoAccount.setAccountId(itemObj.getLong("account_id"));
            sysLieBaoAccount.setAccountStatus(itemObj.getInteger("account_status"));
            sysLieBaoAccount.setAccountName(itemObj.getString("account_name"));
            sysLieBaoAccount.setSpendCap(itemObj.getBigDecimal("spend_cap"));
            sysLieBaoAccount.setAmountSpent(itemObj.getBigDecimal("amount_spent"));
            sysLieBaoAccount.setTotalAmountSpent(itemObj.getBigDecimal("total_amount_spent"));
            sysLieBaoAccount.setDisableReason(itemObj.getInteger("disable_reason"));
            sysLieBaoAccount.setCreateTime(new Date());
            sysLieBaoAccount.setUpdateTime(new Date());
            sysLieBaoAccount.setCreatedTime(itemObj.getLong("created_time"));
            sysLieBaoAccount.setCurrency(itemObj.getString("currency"));
            sysLieBaoAccount.setTimezoneOffsetHoursUtc(itemObj.getBigDecimal("timezone_offset_hours_utc"));
            sysLieBaoAccount.setBalance(itemObj.getBigDecimal("balance"));
            sysLieBaoAccount.setPromotablePageIds(itemObj.getString("promotable_page_ids"));
            sysLieBaoAccount.setPromotableUrls(itemObj.getString("promotable_urls"));
            sysLieBaoAccount.setCompanyCn(itemObj.getString("company_cn"));
            sysLieBaoAccount.setCompanyEn(itemObj.getString("company_en"));
            sysLieBaoAccount.setFundingSourceDetail(itemObj.getInteger("funding_source_detail"));
            sysLieBaoAccount.setBusinessRegistrationId(itemObj.getString("business_registration_id"));
            sysLieBaoAccount.setRequestId(itemObj.getString("request_id"));
            sysLieBaoAccount.setDisabledTransferAmount(itemObj.getBigDecimal("disabled_transfer_amount"));
            sysLieBaoAccounts.add(sysLieBaoAccount);
        });
        sysLieBaoAccountService.syncAccountList(sysLieBaoAccounts);
        return ReturnBody.success();
    }

    @ApiOperation("充值")
    @PostMapping("recharge")
    @ApiImplicitParams({@ApiImplicitParam(name = "amount", value = "金额", required = true), @ApiImplicitParam(name = "accountId", value = "猎豹账户id", required = true),})
    @ParameterVerify(notNull = {"amount", "accountId"})
    public String recharge(BigDecimal amount, Long accountId) {
        if (lieBaoService.facebookAccountRecharge(String.valueOf(accountId), amount)) {
            return ReturnBody.success("充值成功");
        } else {
            return ReturnBody.error("充值错误，请联系平台");
        }
    }


}
