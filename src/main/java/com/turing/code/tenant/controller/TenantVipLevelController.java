package com.turing.code.tenant.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.utils.ImgUrlEnum;
import com.turing.code.common.utils.encrypt.Base64Util;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.tenant.service.TenantProService;
import com.turing.code.user.pojo.UserVipLevel;
import com.turing.code.user.service.UserVipLevelService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.turing.code.tenant.service.TenantVipLevelService;
import com.turing.code.tenant.pojo.TenantVipLevel;

import javax.servlet.http.HttpServletRequest;

import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-23
 */
@Api(tags = "租户-会员等级")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantVipLevel", produces = "text/plain;charset=utf-8")
public class TenantVipLevelController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantVipLevelService tenantVipLevelService;
    @Resource
    private TenantProService tenantProService;
    @Resource
    private UserVipLevelService userVipLevelService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        Integer pId = (Integer) request.getAttribute("pId");
        PageResult<TenantVipLevel> pageResult = new PageUtil<TenantVipLevel>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(TenantVipLevel.STATUS, 1);
            queryWrapper.eq(TenantVipLevel.TENANT_ID, pId);
            tenantVipLevelService.page(page, queryWrapper);
        });
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("列表（无分页）")
    @PostMapping("listNoPage")
    public String listNoPage() {
        QueryWrapper<TenantVipLevel> eq = new QueryWrapper<TenantVipLevel>().eq(TenantVipLevel.STATUS, 1)
                .eq(TenantVipLevel.TENANT_ID, request.getAttribute("pId"));
        return ReturnBody.success(tenantVipLevelService.list(eq));
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(TenantVipLevel tenantVipLevel) {
        Integer rId = (Integer) request.getAttribute("pId");
        tenantVipLevel.setCreateTime(null).setStatus(null).setUpdateTime(null).setTenantId(rId);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(tenantVipLevel.getId())) {
                return ReturnBody.error();
            }
        }
        tenantVipLevel.setDiscountRatio(tenantVipLevel.getDiscountRatio() / 100);
        if (CommUtil.checkNull(tenantVipLevel.getIcon()) && tenantVipLevel.getIcon().contains("data:image")) {
            tenantVipLevel.setIcon(Base64Util.generateImage(request, ImgUrlEnum.TENANT_OTHER.getPath(), tenantVipLevel.getIcon(),
                    false));
        }
        if (!tenantVipLevelService.saveOrUpdate(tenantVipLevel)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        //查询是否有礼包绑定了该等级
        QueryWrapper<TenantPro> eq = new QueryWrapper<TenantPro>().eq(TenantPro.VIP_PRO, 1).eq(TenantPro.GIFT_VIP_LEVEL, id);
        TenantPro tenantPro = tenantProService.getOne(eq);
        if (CommUtil.checkNull(tenantPro)) {
            return ReturnBody.error(String.format("已有礼包:%s绑定了该会员等级",tenantPro.getName()));
        }
        //查询是否有会员购买了该等级
        QueryWrapper<UserVipLevel> vipEq = new QueryWrapper<UserVipLevel>().eq(UserVipLevel.VIP_LEVEL_ID,id);
        if (userVipLevelService.count(vipEq)!=0) {
            return ReturnBody.error("已有用户购买该礼包，无法删除");
        }

        return tenantVipLevelService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }


    @ApiOperation("会员分销等级列表")
    @PostMapping("vipListNoPage")
    public String vipListNoPage() {
        Integer pId = (Integer) request.getAttribute("pId");
        QueryWrapper<TenantVipLevel> eq =
                new QueryWrapper<TenantVipLevel>().eq(TenantVipLevel.TENANT_ID, pId).orderByAsc(TenantVipLevel.CREATE_TIME);
        return ReturnBody.success(tenantVipLevelService.list(eq));
    }

    @ApiOperation("批量修改会员等级")
    @PostMapping("batchUpdateVipLevel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "list", value = "会员等级列表", required = true),
    })
    @ParameterVerify(notNull = "list")
    public String batchUpdateVipLevel(String list) {
        List<TenantVipLevel> tenantVipLevels = JSONArray.parseArray(list, TenantVipLevel.class);
        if (!tenantVipLevelService.updateBatchById(tenantVipLevels)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }

    @ApiOperation("获取会员分销礼包")
    @PostMapping("vipProList")
    public String vipProList() {
        Integer pId = (Integer) request.getAttribute("pId");
        QueryWrapper<TenantPro> eq = new QueryWrapper<TenantPro>().eq(TenantPro.VIP_PRO, 1).eq(TenantPro.TENANT_ID, pId);
        return ReturnBody.success(tenantProService.list(eq));
    }

    @ApiOperation("批量修改会员礼包")
    @PostMapping("batchUpdateVipPro")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "list", value = "会员礼包列表", required = true),
    })
    @ParameterVerify(notNull = "list")
    public String batchUpdateVipPro(String list) {
        List<TenantPro> tenantProList = JSONArray.parseArray(list, TenantPro.class);
        if (!tenantProService.updateBatchById(tenantProList)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }


}
