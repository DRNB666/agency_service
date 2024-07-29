package com.leepsmart.code.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.mybatisplus.structure.CustomUpdateWrapper;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.system.pojo.SysAdvice;
import com.leepsmart.code.system.service.SysAdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author leepsmart generator
 */
@Api(tags = "系统-用户建议反馈")
@RestController
@RequestMapping("system/sysAdvice")
public class SysAdviceController {

    @Resource
    private SysAdviceService sysAdviceService;

    @ApiOperation("获取建议反馈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "处理状态：0 未处理 1 已处理"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "usId", value = "用户ID"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "type", value = "用户类型"),
    })
    @PostMapping("admin/list")
    public String list(PageInfo pageInfo, Integer status, Integer usId, Integer type) {
        pageInfo.setTimeScreen(SysAdvice.CREATE_TIME);
        PageResult<SysAdvice> pageResult = new PageUtil<SysAdvice>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(SysAdvice.STATUS, status)
                    .eq(SysAdvice.TYPE, type)
                    .eq(SysAdvice.US_ID, usId)
                    .orderByDesc(SysAdvice.CREATE_TIME);
            sysAdviceService.page(page, queryWrapper);
            System.out.println(123);
        });
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("回复操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "反馈ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "content", value = "回复内容", required = true)
    })
    @PostMapping("admin/reply")
    @ParameterVerify(notNull = {"id", "content"})
    public String reply(Integer id, String content) {
        SysAdvice sysAdvice = new SysAdvice()
                .setId(id)
                .setReply(content)
                .setIsRead(0)
                .setStatus(1);
        if (!sysAdviceService.updateById(sysAdvice)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }

    @ApiOperation("设置全部已读")
    @PostMapping("admin/setAllRead")
    public String setAllRead() {
        UpdateWrapper<SysAdvice> queryWrapper = new CustomUpdateWrapper<SysAdvice>()
                .set(SysAdvice.IS_READ, 1)
                .eq(SysAdvice.IS_READ, 0);

        sysAdviceService.update(queryWrapper);
        return ReturnBody.success();
    }

}
