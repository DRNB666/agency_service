package com.leepsmart.code.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.leepsmart.code.admin.service.AdFlowProfitService;
import com.leepsmart.code.admin.pojo.AdFlowProfit;
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
 */
@Api(tags = "后台-收益流水")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "admin/adFlowProfit", produces = "text/plain;charset=utf-8")
public class AdFlowProfitController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private AdFlowProfitService adFlowProfitService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<AdFlowProfit> pageResult = new PageUtil<AdFlowProfit>(pageInfo).startPage((page, queryWrapper) -> adFlowProfitService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(AdFlowProfit adFlowProfit) {
        adFlowProfit.setCreateTime(null).setStatus(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(adFlowProfit.getId())) {
                return ReturnBody.error();
            }
        }
        if (!adFlowProfitService.saveOrUpdate(adFlowProfit)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return adFlowProfitService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

}
