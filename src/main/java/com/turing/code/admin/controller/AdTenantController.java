package com.turing.code.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.admin.pojo.AdInfo;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.config.KeyConfig;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.SortWay;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.service.TenantInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author turing generator
 * @since 2021-02-03
 */
@Api(tags = "管理员-租户相关操作")
@RestController
@RequestMapping("admin/tat")
public class AdTenantController {

    @Resource
    private TenantInfoService tenantInfoService;
    @Resource
    private BCryptPasswordEncoder encoder;

    @ApiOperation("租户列表")
    @PostMapping("tatList")
    public String tatList(PageInfo pageInfo, String account,Integer status) {
        pageInfo.setTimeScreen("create_time");
        pageInfo.setDefaultSort("create_time", SortWay.DESC);
        //执行分页辅助工具
        PageResult<TenantInfo> pageResult = new PageUtil<TenantInfo>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(TenantInfo.ACCOUNT, account);
            queryWrapper.eq(TenantInfo.STATUS, status);
            tenantInfoService.page(page, queryWrapper);
        });
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("添加主租户")
    @PostMapping("addTat")
    public String addTat(@RequestBody JSONObject tentInfo) {
        if (!CommUtil.checkNullJSON(tentInfo, true, TenantInfo.PASSWORD, TenantInfo.ACCOUNT)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        TenantInfo tenantInfo = JSONObject.parseObject(tentInfo.toJSONString(), TenantInfo.class);
        QueryWrapper<TenantInfo> eq = new QueryWrapper<TenantInfo>().eq(TenantInfo.ACCOUNT, tenantInfo.getAccount());
        if (CommUtil.checkNull(tenantInfoService.getOne(eq))){
            return ReturnBody.error("账号已存在");
        }
        tenantInfo.setPassword(encoder.encode(tenantInfo.getPassword() + KeyConfig.KEY_PWD));
        tenantInfo.setRoleId(2);
        if (!tenantInfoService.save(tenantInfo)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }

    @ApiOperation("编辑主租户")
    @PostMapping("updateTat")
    public String updateTat(@RequestBody JSONObject tentInfo) {
        if (!CommUtil.checkNullJSON(tentInfo, true, TenantInfo.ID)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        TenantInfo tenantInfo = JSONObject.parseObject(tentInfo.toJSONString(), TenantInfo.class);
        if (!tenantInfoService.updateById(tenantInfo)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success(tenantInfo);
    }

    @ApiOperation("删除主租户")
    @PostMapping("removeTat")
    public String removeTat(@RequestBody JSONObject tentInfo) {
        if (!CommUtil.checkNullJSON(tentInfo, false, "id")) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        TenantInfo tenantInfo = JSONObject.parseObject(tentInfo.toJSONString(), TenantInfo.class);
        if (!tenantInfoService.removeById(tenantInfo)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }
}
