package com.leepsmart.code.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.admin.mapper.AdFlowMapper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

import com.leepsmart.code.admin.pojo.AdFlow;
import javax.servlet.http.HttpServletRequest;
import com.leepsmart.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.CommUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leepsmart generator
 */
@Api(tags = "后台-平台流水")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "admin/adFlow", produces = "text/plain;charset=utf-8")
public class AdFlowController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private AdFlowMapper adFlowMapper;




}
