package com.leepsmart.code.admin.controller;

import com.leepsmart.code.admin.pojo.AdInfo;
import com.leepsmart.code.admin.pojo.AdMenu;
import com.leepsmart.code.admin.pojo.AdRole;
import com.leepsmart.code.admin.pojo.AdRoleMenu;
import com.leepsmart.code.admin.service.AdRoleMenuService;
import com.leepsmart.code.admin.service.AdRoleService;
import com.leepsmart.code.admin.tools.enums.AdMenuFlag;
import com.leepsmart.code.admin.tools.enums.AdRoleStatus;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.annotation.repeat.PreventRepeat;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leepsmart generator
 */
@Api(tags = "后台-角色")
@RestController
@RequestMapping("admin/adRole")
public class AdRoleController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private AdRoleService adRoleService;
    @Resource
    private AdRoleMenuService adRoleMenuService;

    @ApiOperation("角色列表")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "角色状态")
    @PostMapping("list")
    public String list(PageInfo pageInfo, Integer status) {
        pageInfo.setTimeScreen(AdRole.CREATE_TIME);
        PageResult<AdRole> pageResult = new PageUtil<AdRole>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(AdRole.STATUS, status);
            adRoleService.page(page, queryWrapper);
        });
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "roleName", value = "角色名称", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "remark", value = "备注")
    })
    @PostMapping("add")
    @ParameterVerify(notNull = {"roleName"})
    @PreventRepeat
    public String add(String roleName, String remark) {
        AdRole adRole = new AdRole()
                .setRoleName(roleName)
                .setStatus(AdRoleStatus.NORMAL.getValue())
                .setRemark(remark);
        if (!adRoleService.save(adRole)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "角色ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "roleName", value = "角色名称", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "remark", value = "备注")
    })
    @PostMapping("update")
    @ParameterVerify(notNull = {"id", "roleName"})
    public String update(Integer id, String roleName, String remark) {
        if (id == 1) {
            return ReturnBody.error("不可操作管理员角色");
        }
        AdRole adRole = new AdRole()
                .setId(id)
                .setRoleName(roleName)
                .setRemark(remark);
        if (!adRoleService.updateById(adRole)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("冻结或解冻操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "角色ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "新状态 -1:冻结; 1:正常", required = true)
    })
    @PostMapping("freezeOrThaw")
    @ParameterVerify(notNull = {"id", "status"})
    public String freezeOrThaw(Integer id, Integer status) {
        if (!status.equals(AdRoleStatus.FREEZE.getValue()) && status.equals(AdRoleStatus.NORMAL.getValue())) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (id == 1) {
            return ReturnBody.error("不可操作管理员角色");
        }
        AdRole adRole = new AdRole()
                .setStatus(status)
                .setId(id);
        if (!adRoleService.updateById(adRole)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除角色")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "角色ID", required = true)
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(Integer id) {
        if (id == 1) {
            return ReturnBody.error("不可操作管理员角色");
        }
        return adRoleService.delete(id);
    }


    @ApiOperation("分配菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "roleId", value = "角色ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer[].class, name = "menuIds", value = "菜单ID数组", required = true)
    })
    @PostMapping("setRoleMenu")
    @ParameterVerify(notNull = {"roleId"})
    public String setRoleMenu(Integer roleId, Integer[] menuIds) {
        if (menuIds == null) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (menuIds.length < 1) {
            return ReturnBody.error("请选择菜单");
        }
//        Integer id = (Integer) request.getAttribute(AdInfo.ID);
//        if (id != 1) {
//            return ReturnBody.error("操作该接口的权限不足");
//        }
        return adRoleMenuService.setRoleMenu(roleId, menuIds);
    }

    @ApiOperation("获取该角色拥有的菜单权限")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "角色ID", required = true)
    @PostMapping("getRoleMenuId")
    public String getRoleMenuId(Integer roleId) {
        if (!CommUtil.checkNull(roleId)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        AdRoleMenu adminRoleMenu = new AdRoleMenu();
        adminRoleMenu.setRoleId(roleId);
        List<AdMenu> list = adRoleMenuService.selectRoleMenu(adminRoleMenu);
        List<Integer> integers = new ArrayList<>();
        list.stream()
                .filter(item -> item.getFlag().equals(AdMenuFlag.INTERFACE.getValue()))
                .collect(Collectors.toList())
                .forEach(item -> integers.add(item.getId()));
        return ReturnBody.success(integers);
    }


}
