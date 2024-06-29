package com.turing.code.tenant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantMenu;
import com.turing.code.tenant.pojo.TenantRoleMenu;
import com.turing.code.tenant.service.TenantInfoService;
import com.turing.code.tenant.service.TenantRoleMenuService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.tenant.service.TenantRoleService;
import com.turing.code.tenant.pojo.TenantRole;
import javax.servlet.http.HttpServletRequest;
import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author turing generator
 * @since 2024-05-28
 */
@Api(tags = "租户-角色")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantRole", produces = "text/plain;charset=utf-8")
public class TenantRoleController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantRoleService tenantRoleService;
    @Resource
    private TenantInfoService tenantInfoService;
    @Resource
    private TenantRoleMenuService tenantRoleMenuService;

    @ApiOperation("角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "角色状态"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "name", value = "角色名称"),
    })
    @PostMapping({"list", "list_role", "list_user_role"})
    public String list(PageInfo pageInfo, Integer status, String name) {
        pageInfo.setTimeScreen(TenantRole.CREATE_TIME);
        PageResult<TenantRole> pageResult = new PageUtil<TenantRole>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.eq(status != null, TenantRole.STATUS, status);
            queryWrapper.like(name != null && !"".equals(name), TenantRole.ROLE_NAME, name);
            tenantRoleService.page(page, queryWrapper);
        });
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "roleName", value = "角色名称", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "remark", value = "备注"),
    })
    @PostMapping("add")
    @ParameterVerify(notNull = {"roleName"})
    public String add(String roleName, String remark, String sensitiveFields, Boolean canSeeAllOrder, Boolean updateTodayDocument) {
        TenantRole adRole = new TenantRole()
                .setRoleName(roleName)
                .setRemark(remark);
        if (!tenantRoleService.save(adRole)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "角色ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "roleName", value = "角色名称", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "remark", value = "备注"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "sensitiveFields", value = "可查看的敏感字段", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Boolean.class, name = "canSeeAllOrder", value = "是否可查看所有订单"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Boolean.class, name = "updateTodayDocument", value = "是否仅能修改当天单据"),

    })
    @PostMapping("update")
    @ParameterVerify(notNull = {"id", "roleName", "sensitiveFields", "canSeeAllOrder", "updateTodayDocument"})
    public String update(Integer id, String roleName, String remark, String sensitiveFields, Boolean canSeeAllOrder, Boolean updateTodayDocument) {
        TenantRole adRole = new TenantRole()
                .setId(id)
                .setRemark(remark);

            adRole.setRoleName(roleName);

        if (!tenantRoleService.updateById(adRole)) {
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
        TenantRole adRole = new TenantRole()
                .setStatus(status)
                .setId(id);
        if (!tenantRoleService.updateById(adRole)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除角色")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "角色ID", required = true)
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(Integer id) {
        //查询是否有账号绑定该角色
        QueryWrapper<TenantInfo> eq = new QueryWrapper<TenantInfo>().eq(TenantInfo.ROLE_ID, id);
        if(tenantInfoService.count(eq)!=0){
            return ReturnBody.error("当前角色下有账号存在，请解绑角色后再操作");
        }
        if (!tenantRoleService.removeById(id)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
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
        return tenantRoleMenuService.setRoleMenu(roleId, menuIds);
    }

    @ApiOperation("获取该角色拥有的菜单权限")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "roleId", value = "角色ID", required = true)
    @PostMapping("getRoleMenuId")
    public String getRoleMenuId(Integer roleId) {
        if (!CommUtil.checkNull(roleId)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        TenantRoleMenu adminRoleMenu = new TenantRoleMenu();
        adminRoleMenu.setRoleId(roleId);
        List<TenantMenu> list = tenantRoleMenuService.selectRoleMenu(adminRoleMenu);
        List<Integer> collect = list.stream()
//                .filter(item->item.getFlag()==2)
                .map(TenantMenu::getId)
                .collect(Collectors.toList());
        return ReturnBody.success(collect);
    }

}
