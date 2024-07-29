package com.leepsmart.code.system.controller;

import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.utils.ImgUrlEnum;
import com.leepsmart.code.common.utils.encrypt.Base64Util;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.system.pojo.SysParams;
import com.leepsmart.code.system.service.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author leepsmart generator
 */
@Api(tags = "系统-参数集")
@RestController
@RequestMapping("system/sysParams")
public class SysParamsController {

    @Resource
    private SysParamsService sysParamsService;
    @Resource
    private HttpServletRequest request;


    @PostMapping("admin/list")
    @ApiOperation("系统参数列表")
    public String list(PageInfo pageInfo) {
        PageResult<SysParams> pageResult = new PageUtil<SysParams>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(SysParams.STATUS, 1);
            sysParamsService.page(page, queryWrapper);
        });
        return ReturnBody.success(pageResult);
    }

    @PostMapping("admin/update")
    @ApiOperation("编辑系统参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "系统参数ID (4使用手册  5关于我们  6上传logo)", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "value", value = "系统参数值", required = true)
    })
    @ParameterVerify(notNull = {"id", "value"})
    public String update(Integer id, String value) {
        SysParams param = sysParamsService.getById(id);
        if (param == null) {
            return ReturnBody.error();
        }
        switch (id) {
            //4使用手册  5关于我们
            case 4:
            case 5:
                param.setBlodValue(value);
                break;

            case 6: {
                if (value.startsWith("data:image")) {
                    value = Base64Util.generateImage(request, ImgUrlEnum.SYSTEM_IMAGES.getPath(), value, false);
                    if (value == null) {
                        return ReturnBody.error("图片格式不正确");
                    }
                } else if (!value.startsWith("/files")) {
                    return ReturnBody.error("图片格式不正确");
                }
                param.setValue(value);
                break;
            }

            default:
                param.setValue(value);
                break;
        }
        if (!sysParamsService.updateById(param)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }

    @ApiOperation("获取系统参数")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "4使用手册  5关于我们  6logo", required = true)
    @ParameterVerify(notNull = "id")
    @PostMapping("public/getAgreement")
    public String getAgreement(Integer id) {
        if (id == null) {
            return ReturnBody.error("参数错误");
        }
        switch (id) {
            case 4:
            case 5:
                return ReturnBody.success(sysParamsService.getById(id).getBlodValue());
            default:
                return ReturnBody.success(sysParamsService.getById(id).getValue());
        }
    }

}
