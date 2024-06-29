package com.turing.code.tenant.controller;

import com.turing.code.common.utils.ImgUrlEnum;
import com.turing.code.common.utils.encrypt.Base64Util;
import com.turing.code.tenant.pojo.TenantInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.tenant.service.TenantBannerService;
import com.turing.code.tenant.pojo.TenantBanner;
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
 * @since 2024-06-11
 */
@Api(tags = "租户-轮播图")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantBanner", produces = "text/plain;charset=utf-8")
public class TenantBannerController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantBannerService tenantBannerService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<TenantBanner> pageResult = new PageUtil<TenantBanner>(pageInfo).startPage((page, queryWrapper) -> tenantBannerService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(TenantBanner tenantBanner) {
        Integer rId = (Integer) request.getAttribute("pId");
        tenantBanner.setCreateTime(null).setStatus(null).setUpdateTime(null).setTenantId(rId);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(tenantBanner.getId())) {
                return ReturnBody.error();
            }
        }
        if (CommUtil.checkNull(tenantBanner.getUrl()) && tenantBanner.getUrl().contains("data:image")) {
            tenantBanner.setUrl(Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), tenantBanner.getUrl(),
                    false));
        }
        if (!tenantBannerService.saveOrUpdate(tenantBanner)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return tenantBannerService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

}
