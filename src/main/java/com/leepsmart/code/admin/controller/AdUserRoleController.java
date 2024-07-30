package com.leepsmart.code.admin.controller;

import com.leepsmart.code.admin.tools.enums.AdMenuFlag;
import com.leepsmart.code.admin.tools.enums.AdRoleStatus;
import com.leepsmart.code.common.annotation.repeat.PreventRepeat;
import com.leepsmart.code.user.pojo.UserMenu;
import com.leepsmart.code.user.pojo.UserRoleMenu;
import com.leepsmart.code.user.service.UserRoleMenuService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.leepsmart.code.user.service.UserRoleService;
import com.leepsmart.code.user.pojo.UserRole;
import javax.servlet.http.HttpServletRequest;
import com.leepsmart.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leepsmart generator
 * @since 2024-07-30
 */
@Api(tags = "后台-用户角色")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "admin/userRole", produces = "text/plain;charset=utf-8")
public class AdUserRoleController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserRoleMenuService adUserRoleMenuService;
    
    @ApiOperation("角色列表")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "角色状态")
    @PostMapping("list")
    public String list(PageInfo pageInfo, Integer status) {
        pageInfo.setTimeScreen(UserRole.CREATE_TIME);
        PageResult<UserRole> pageResult = new PageUtil<UserRole>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(UserRole.STATUS, status);
            userRoleService.page(page, queryWrapper);
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
        UserRole UserRole = new UserRole()
                .setRoleName(roleName)
                .setStatus(AdRoleStatus.NORMAL.getValue())
                .setRemark(remark);
        if (!userRoleService.save(UserRole)) {
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
        UserRole UserRole = new UserRole()
                .setId(id)
                .setRoleName(roleName)
                .setRemark(remark);
        if (!userRoleService.updateById(UserRole)) {
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
        UserRole UserRole = new UserRole()
                .setStatus(status)
                .setId(id);
        if (!userRoleService.updateById(UserRole)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
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
        return adUserRoleMenuService.setRoleMenu(roleId, menuIds);
    }

    @ApiOperation("获取该角色拥有的菜单权限")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "角色ID", required = true)
    @PostMapping("getRoleMenuId")
    public String getRoleMenuId(Integer roleId) {
        if (!CommUtil.checkNull(roleId)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        UserRoleMenu UserRoleMenu = new UserRoleMenu();
        UserRoleMenu.setRoleId(roleId);
        List<UserMenu> list = adUserRoleMenuService.selectRoleMenu(UserRoleMenu);
        List<Integer> integers = new ArrayList<>();
        list.stream()
                .filter(item -> item.getFlag().equals(AdMenuFlag.INTERFACE.getValue()))
                .collect(Collectors.toList())
                .forEach(item -> integers.add(item.getId()));
        return ReturnBody.success(integers);
    }





}
