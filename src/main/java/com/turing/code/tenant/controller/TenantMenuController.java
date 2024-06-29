package com.turing.code.tenant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.utils.LogUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.tenant.service.TenantMenuService;
import com.turing.code.tenant.pojo.TenantMenu;
import javax.servlet.http.HttpServletRequest;
import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author turing generator
 * @since 2024-05-28
 */
@Api(tags = "租户-菜单")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantMenu", produces = "text/plain;charset=utf-8")
public class TenantMenuController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantMenuService tenantMenuService;

    @ApiOperation("所有菜单")
    @PostMapping({"allList", "allList_role", "allList_menu"})
    public String allList() {
        QueryWrapper<TenantMenu> queryWrapper = new QueryWrapper<TenantMenu>()
                .select(TenantMenu.getFields(TenantMenu.VERSION, TenantMenu.CREATE_TIME, TenantMenu.UPDATE_TIME, TenantMenu.SORT))
                .orderByDesc(TenantMenu.SORT);
        List<TenantMenu> list = tenantMenuService.list(queryWrapper);
        return ReturnBody.success(list);
    }

    @ApiOperation("添加或修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "menuName", value = "菜单名称", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "requestUrl", value = "请求路径", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "menuIcon", value = "菜单图标"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "parentId", value = "父菜单ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "flag", value = "1 页面菜单 2 后端接口", required = true)
    })
    @PostMapping({"add", "update"})
    @ParameterVerify(notNull = {"menuName", "parentId", "flag"})
    public String addOrUpdate(Integer id, String menuName, String requestUrl, String menuIcon, String component, Integer parentId, Integer flag) {
//        if (!Arrays.asList(1, 2).contains(flag)) {
//            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
//        }
        if (parentId != -1 && ((flag == 1 && !CommUtil.checkNull(component)) || (flag == 2 && !CommUtil.checkNull(requestUrl)))) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        TenantMenu tenantMenu = new TenantMenu()
                .setId(id)
                .setMenuName(menuName)
                .setRequestUrl(requestUrl)
                .setMenuIcon(menuIcon)
                .setParentId(parentId)
                .setFlag(flag)
                .setComponent(component)
                .setSort(0);

        // 参数校验
        if (request.getRequestURI().endsWith("update") && !CommUtil.checkNull(tenantMenu.getId())) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (request.getRequestURI().endsWith("add")) {
            tenantMenu.setId(null);
        }

        // 执行操作方法
        if (!tenantMenuService.saveOrUpdate(tenantMenu)) {
            return ReturnBody.error();
        }

        return ReturnBody.success(tenantMenu);
    }


    @ApiOperation("批量删除菜单")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer[].class, name = "ids", value = "菜单ID数组", required = true)
    @PostMapping("delete")
    public String delete(Integer[] ids){
        if (null == ids) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (ids.length < 1) {
            return ReturnBody.error("必须选择一个菜单节点");
        }
        if (!tenantMenuService.removeByIds(new ArrayList<>(Arrays.asList(ids)))) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("设置排序")
    @PostMapping("setSort")
    @ParameterVerify(notNull = {"draggingId", "dropId", "parentId", "dropType"})
    public String setSort(Integer draggingId, Integer dropId, Integer parentId, String dropType){
        // 判断拖拽节点是否是根节点
        List<TenantMenu> newList = new ArrayList<>();

        QueryWrapper<TenantMenu> queryWrapper = new QueryWrapper<TenantMenu>()
                .eq(TenantMenu.PARENT_ID, parentId)
                .orderByDesc(TenantMenu.SORT);
        List<TenantMenu> list = tenantMenuService.list(queryWrapper);
        // 被拖拽菜单的元素
        TenantMenu dragging = list.stream().filter(item -> Objects.equals(item.getId(), draggingId)).collect(Collectors.toList()).get(0);

        int sort = list.size();
        // 如果被拖拽目标放在目标之前, 将被拖拽菜单放到目标之前, 并设置排序
        if ("before".equals(dropType)) {
            for (TenantMenu tenantMenu : list) {
                if (dropId.equals(tenantMenu.getId())) {
                    sort--;
                    dragging.setSort(sort);
                    newList.add(dragging);
                    LogUtil.info(dragging.getMenuName() + ", sort:" + dragging.getSort());
                    sort--;
                    tenantMenu.setSort(sort);
                    newList.add(tenantMenu);
                    LogUtil.info(tenantMenu.getMenuName() + ", sort:" + tenantMenu.getSort());
                } else if (draggingId.equals(tenantMenu.getId())) {
                    continue;
                } else {
                    sort--;
                    tenantMenu.setSort(sort);
                    newList.add(tenantMenu);
                    LogUtil.info(tenantMenu.getMenuName() + ", sort:" + tenantMenu.getSort());
                }
            }
        }else{
            // 之后
            for (TenantMenu tenantMenu : list) {
                if (dropId.equals(tenantMenu.getId())) {
                    sort--;
                    tenantMenu.setSort(sort);
                    LogUtil.info(tenantMenu.getMenuName() + ", sort:" + tenantMenu.getSort());
                    newList.add(tenantMenu);
                    sort--;
                    dragging.setSort(sort);
                    newList.add(dragging);
                    LogUtil.info(dragging.getMenuName() + ", sort:" + dragging.getSort());
                } else if (draggingId.equals(tenantMenu.getId())) {
                    continue;
                } else {
                    sort--;
                    tenantMenu.setSort(sort);
                    newList.add(tenantMenu);
                    LogUtil.info(tenantMenu.getMenuName() + ", sort:" + tenantMenu.getSort());
                }
            }
        }

        tenantMenuService.updateBatchById(newList);
        return ReturnBody.success();
    }

}
