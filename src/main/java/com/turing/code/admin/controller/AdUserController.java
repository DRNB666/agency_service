package com.turing.code.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.user.pojo.UserInfo;
import com.turing.code.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;


@Api(tags = "管理员-用户相关操作")
@RestController
@RequestMapping("admin/userManager")
public class AdUserController {

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("用户列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo,String keyword,Integer level,String startTime,String endTime){
        PageResult<UserInfo> pageResult = new PageResult<>();
        Page<UserInfo> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<UserInfo> iPage = userInfoService.userList(page,new HashMap<String, Object>(){{
            put("keyword",keyword);
            put("level",level);
            put("startTime",startTime);
            put("endTime",endTime);
        }});
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        return ReturnBody.success(pageResult);
    }

}
