package com.leepsmart.code.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.admin.pojo.AdInfo;
import com.leepsmart.code.admin.service.AdInfoService;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.config.KeyConfig;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import com.leepsmart.code.system.service.SysLieBaoAccountService;
import com.leepsmart.code.user.pojo.UserInfo;
import com.leepsmart.code.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;


@Api(tags = "后台-用户相关操作")
@RestController
@RequestMapping("admin/userManager")
public class AdUserController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private AdInfoService adInfoService;
    @Resource
    private BCryptPasswordEncoder encoder;
    @Resource
    private SysLieBaoAccountService sysLieBaoAccountService;

    @ApiOperation("用户列表")
    @PostMapping("list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字、邮箱，账户名", required = true),
    })
    public String list(PageInfo pageInfo, String keyword) {
        PageResult<UserInfo> pageResult = new PageResult<>();
        Page<UserInfo> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<UserInfo> iPage = userInfoService.userList(page, new HashMap<String, Object>() {{
            put("keyword", keyword);
        }});
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        return ReturnBody.success(pageResult);
    }



    @ApiOperation("设置用户为后台账号")
    @PostMapping("setUserByAccount")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true),
    })
    public String setUserByAccount(String account) {
        QueryWrapper<AdInfo> eq = new QueryWrapper<AdInfo>().eq(AdInfo.ACCOUNT, account);
        AdInfo adInfo = adInfoService.getOne(eq);
        if (CommUtil.checkNull(adInfo)) {
            return ReturnBody.error("该账号已设置为后台账号");
        }
        QueryWrapper<UserInfo> userEq = new QueryWrapper<UserInfo>().eq(UserInfo.ACCOUNT, account);
        UserInfo userInfo = userInfoService.getOne(userEq);
        if (!CommUtil.checkNull(userInfo)) {
            return ReturnBody.error("用户账号不存在");
        }
        //角色默认管理员
        AdInfo newAd = new AdInfo().setAccount(account).setPassword(encoder.encode("123123" + KeyConfig.KEY_PWD)).setEmail(userInfo.getEmail()).setRoleId(2);
        if (!adInfoService.save(newAd)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }

    @ApiOperation("绑定猎豹账户所属用户")
    @PostMapping("bingLieBaoAcUser")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "usId", value = "用户id", required = true),
             @ApiImplicitParam(name = "accountId", value = "猎豹账户id", required = true),
     })
    @ParameterVerify(notNull = {"usId","accountId"})
    public String bingLieBaoAcUser(Long usId,Long accountId){
        QueryWrapper<SysLieBaoAccount> eq = new QueryWrapper<SysLieBaoAccount>().eq(SysLieBaoAccount.ACCOUNT_ID, accountId).isNull(SysLieBaoAccount.USER_ID);
        SysLieBaoAccount sysLieBaoAccount = sysLieBaoAccountService.getOne(eq);
        if (!CommUtil.checkNull(sysLieBaoAccount)){
            return ReturnBody.error("账户已绑定或不存在");
        }
        sysLieBaoAccount.setUserId(usId);
        if (!sysLieBaoAccountService.updateById(sysLieBaoAccount)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }

    @ApiOperation("取消绑定猎豹账户所属用户")
    @PostMapping("cancelBingLieBaoAcUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "猎豹账户id", required = true),
    })
    @ParameterVerify(notNull = "accountId")
    public String cancelBingLieBaoAcUser(Long accountId){
        QueryWrapper<SysLieBaoAccount> eq = new QueryWrapper<SysLieBaoAccount>().eq(SysLieBaoAccount.ACCOUNT_ID, accountId).isNull(SysLieBaoAccount.USER_ID);
        SysLieBaoAccount sysLieBaoAccount = sysLieBaoAccountService.getOne(eq);
        if (!CommUtil.checkNull(sysLieBaoAccount)){
            return ReturnBody.error("账户已绑定或不存在");
        }
        sysLieBaoAccount.setUserId(null);
        if (!sysLieBaoAccountService.updateById(sysLieBaoAccount)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }



}
