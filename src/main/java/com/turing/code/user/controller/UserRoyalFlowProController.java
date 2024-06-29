package com.turing.code.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.user.service.UserRoyalFlowProService;
import com.turing.code.user.pojo.UserRoyalFlowPro;
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
 * @since 2024-05-31
 */
@Api(tags = "用户-推广商品佣金流水")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "user/userRoyalFlowPro", produces = "text/plain;charset=utf-8")
public class UserRoyalFlowProController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserRoyalFlowProService userRoyalFlowProService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<UserRoyalFlowPro> pageResult = new PageUtil<UserRoyalFlowPro>(pageInfo).startPage((page, queryWrapper) -> userRoyalFlowProService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(UserRoyalFlowPro userRoyalFlowPro) {
//        userRoyalFlowPro.setCreateTime(null).setStatus(null).setVersion(null).setUpdateTime(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(userRoyalFlowPro.getId())) {
                return ReturnBody.error();
            }
        }
        if (!userRoyalFlowProService.saveOrUpdate(userRoyalFlowPro)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return userRoyalFlowProService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

}