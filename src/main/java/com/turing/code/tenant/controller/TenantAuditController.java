package com.turing.code.tenant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.tenant.service.TenantAuditService;
import com.turing.code.tenant.pojo.TenantAudit;
import javax.servlet.http.HttpServletRequest;
import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author turing generator
 * @since 2024-06-17
 */
@ApiIgnore
@Api(tags = "租户-入驻集体审核")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantAudit", produces = "text/plain;charset=utf-8")
public class TenantAuditController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantAuditService tenantAuditService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<TenantAudit> pageResult = new PageUtil<TenantAudit>(pageInfo).startPage((page, queryWrapper) -> tenantAuditService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(TenantAudit tenantAudit) {
        tenantAudit.setStatus(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(tenantAudit.getId())) {
                return ReturnBody.error();
            }
        }
        if (!tenantAuditService.saveOrUpdate(tenantAudit)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return tenantAuditService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

}
