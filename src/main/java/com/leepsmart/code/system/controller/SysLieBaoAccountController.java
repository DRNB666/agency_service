package com.leepsmart.code.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.leepsmart.code.system.service.SysLieBaoAccountService;
import com.leepsmart.code.system.pojo.SysLieBaoAccount;
import javax.servlet.http.HttpServletRequest;
import com.leepsmart.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.CommUtil;

/**
 * @author leepsmart generator
 * @since 2024-07-29
 */
@Api(tags = "系统-猎豹-广告账户")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "system/sysLieBaoAccount", produces = "text/plain;charset=utf-8")
public class SysLieBaoAccountController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private SysLieBaoAccountService sysLieBaoAccountService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<SysLieBaoAccount> pageResult = new PageUtil<SysLieBaoAccount>(pageInfo).startPage((page, queryWrapper) -> sysLieBaoAccountService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(SysLieBaoAccount sysLieBaoAccount) {
        sysLieBaoAccount.setCreateTime(null).setUpdateTime(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(sysLieBaoAccount.getId())) {
                return ReturnBody.error();
            }
        }
        if (!sysLieBaoAccountService.saveOrUpdate(sysLieBaoAccount)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return sysLieBaoAccountService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

}
