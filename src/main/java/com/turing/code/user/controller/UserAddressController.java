package com.turing.code.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.turing.code.user.service.UserAddressService;
import com.turing.code.user.pojo.UserAddress;

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
 * @since 2024-06-14
 */
@Api(tags = "用户-地址")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "user/userAddress", produces = "text/plain;charset=utf-8")
public class UserAddressController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserAddressService userAddressService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        Long rId = (Long) request.getAttribute("id");
        PageResult<UserAddress> pageResult = new PageUtil<UserAddress>(pageInfo).startPage((page, queryWrapper) -> {
                    queryWrapper.eq(UserAddress.US_ID, rId);
                    userAddressService.page(page, queryWrapper);
                }
        );
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(UserAddress userAddress) {
        Long rId = (Long) request.getAttribute("id");
        userAddress.setCreateTime(null).setStatus(null).setUsId(rId);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(userAddress.getId())) {
                return ReturnBody.error();
            }
        }
        if (!userAddressService.saveOrUpdate(userAddress)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return userAddressService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }
    


}
