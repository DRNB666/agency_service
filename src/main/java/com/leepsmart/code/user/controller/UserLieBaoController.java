package com.leepsmart.code.user.controller;


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
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import com.leepsmart.code.system.pojo.SysLieBaoAccountOeLink;
import com.leepsmart.code.system.service.SysLieBaoAccountOeLinkService;
import com.leepsmart.code.system.service.SysLieBaoAccountService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author leepsmart generator
 */
@Api(tags = "用户-猎豹相关接口")
@ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 401, message = "无用户权限"), @ApiResponse(code = 403, message = "无资源权限"), @ApiResponse(code = 404, message = "找不到接口"),})
@RestController
@RequestMapping(value = "user/lieBao", produces = "text/plain;charset=utf-8")
public class UserLieBaoController {

    @Resource
    private LieBaoService lieBaoService;
    @Resource
    private SysLieBaoAccountService sysLieBaoAccountService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private SysLieBaoAccountOeLinkService sysLieBaoAccountOeLinkService;

    @ApiOperation("获取猎豹accessToken")
    @PostMapping("getLieBaoAc")
    public String getLieBaoAc() {
        String result = lieBaoService.lieBaoAccessToken();
        return ReturnBody.success(result);
    }

    @ApiOperation("生成OE开户链接")
    @PostMapping("OELink")
    public String OELink() {
        Long rId = (Long) request.getAttribute("id");
        SysLieBaoAccountOeLink sysLieBaoAccountOeLink = new SysLieBaoAccountOeLink();
        sysLieBaoAccountOeLink.setUserId(rId);
        String link = lieBaoService.OELink();
        sysLieBaoAccountOeLink.setOeLink(link);
        String[] tokens = link.split("token=");
        if (tokens.length!=2){
            return ReturnBody.error("获取OE开户链接失败");
        }
        sysLieBaoAccountOeLink.setToken(tokens[1]);
        if (!sysLieBaoAccountOeLinkService.save(sysLieBaoAccountOeLink)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success(link);
    }



    @ApiOperation("获取账户列表")
    @PostMapping("getLieBaoAccount")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mediaType", value = "媒体选择 1:meta", required = false),
            @ApiImplicitParam(name = "status", value = "状态选择0:全部 1:激活 2:禁用", required = true),
            @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = false),
    })
    public String getLieBaoAccount(PageInfo pageInfo, Integer mediaType, Integer status,String keyword) {
        Long rId = (Long) request.getAttribute("id");
        //执行分页辅助工具
        PageResult<SysLieBaoAccount> pageResult = new PageResult<>();
        Page<SysLieBaoAccount> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<SysLieBaoAccount> iPage = sysLieBaoAccountService.accountList(page,new HashMap<String, Object>(){{
            put("usId",rId);
            put("status",status);
            put("keyword",keyword);
        }});
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());

        return ReturnBody.success(pageResult);
    }



    
    @ApiOperation("绑定BM")
    @PostMapping("bindBm")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "accountId", value = "猎豹账户id", required = true),
             @ApiImplicitParam(name = "bmId", value = "bmId", required = true),
             @ApiImplicitParam(name = "type", value = "绑定的权限类型 1查看广告表现 2管理广告系列", required = true),
     })
    @ParameterVerify(notNull = {"accountId","bmId","type"})
    public String bindBm(Long accountId,String bmId,Integer type){
        //查询账户列表是否存在该账户
        Long rId = (Long) request.getAttribute("id");
        QueryWrapper<SysLieBaoAccount> eq =
                new QueryWrapper<SysLieBaoAccount>().eq(SysLieBaoAccount.USER_ID,rId).eq(SysLieBaoAccount.ACCOUNT_ID,accountId);
        SysLieBaoAccount sysLieBaoAccount = sysLieBaoAccountService.getOne(eq);
        if (!CommUtil.checkNull(sysLieBaoAccount)){
            return ReturnBody.error("账户不存在");
        }
        if (lieBaoService.facebookAccountGrant(String.valueOf(accountId),bmId,type)) {
            return ReturnBody.success("绑定bm成功");
        }else {
            return ReturnBody.error("绑定bm失败");
        }
    }
    
    @ApiOperation("查广告账户绑定的BM")
    @PostMapping("bmList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "猎豹账户id", required = true),
     })
    @ParameterVerify(notNull = "accountId")
    public String bmList(Long accountId){
        //查询账户列表是否存在该账户
        Long rId = (Long) request.getAttribute("id");
        QueryWrapper<SysLieBaoAccount> eq =
                new QueryWrapper<SysLieBaoAccount>().eq(SysLieBaoAccount.USER_ID,rId).eq(SysLieBaoAccount.ACCOUNT_ID,accountId);
        SysLieBaoAccount sysLieBaoAccount = sysLieBaoAccountService.getOne(eq);
        if (!CommUtil.checkNull(sysLieBaoAccount)){
            return ReturnBody.error("账户不存在");
        }
        String result = lieBaoService.businessAccountBindings(String.valueOf(accountId));
        return ReturnBody.success(JSONArray.parseArray(result));
    }
    

    
    
    


}
