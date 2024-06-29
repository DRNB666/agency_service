package com.turing.code.tenant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.tenant.service.TenantInfoBalanceService;
import com.turing.code.tenant.pojo.TenantInfoBalance;
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
 * @since 2024-06-22
 */
@ApiIgnore
@Api(tags = "租户-余额")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantInfoBalance", produces = "text/plain;charset=utf-8")
public class TenantInfoBalanceController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantInfoBalanceService tenantInfoBalanceService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<TenantInfoBalance> pageResult = new PageUtil<TenantInfoBalance>(pageInfo).startPage((page, queryWrapper) -> tenantInfoBalanceService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(TenantInfoBalance tenantInfoBalance) {
        tenantInfoBalance.setStatus(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(tenantInfoBalance.getId())) {
                return ReturnBody.error();
            }
        }
        if (!tenantInfoBalanceService.saveOrUpdate(tenantInfoBalance)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return tenantInfoBalanceService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

}
