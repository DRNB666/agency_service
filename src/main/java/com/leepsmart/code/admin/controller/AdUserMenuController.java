package com.leepsmart.code.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.leepsmart.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.leepsmart.code.common.utils.LogUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.leepsmart.code.user.service.UserMenuService;
import com.leepsmart.code.user.pojo.UserMenu;
import javax.servlet.http.HttpServletRequest;
import com.leepsmart.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.utils.CommUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leepsmart generator
 * @since 2024-07-30
 */
@Api(tags = "后台-用户菜单")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "admin/userMenu", produces = "text/plain;charset=utf-8")
public class AdUserMenuController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserMenuService userMenuService;

    @ApiOperation("所有菜单")
    @PostMapping("allList")
    public String allList() {
        QueryWrapper<UserMenu> queryWrapper = new CustomQueryWrapper<UserMenu>()
                .select(UserMenu.getFields(UserMenu.VERSION, UserMenu.CREATE_TIME, UserMenu.UPDATE_TIME, UserMenu.SORT))
                .orderByDesc(UserMenu.SORT);
        List<UserMenu> list = userMenuService.list(queryWrapper);
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
        if (parentId != -1 && !CommUtil.checkNull(requestUrl)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        UserMenu UserMenu = new UserMenu()
                .setId(id)
                .setMenuName(menuName)
                .setRequestUrl(requestUrl)
                .setMenuIcon(menuIcon)
                .setParentId(parentId)
                .setFlag(flag)
                .setComponent(component)
                .setSort(0);

        // 参数校验
        if (request.getRequestURI().endsWith("update") && !CommUtil.checkNull(UserMenu.getId())) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (request.getRequestURI().endsWith("add")) {
            UserMenu.setId(null);
        }

        // 执行操作方法
        if (!userMenuService.saveOrUpdate(UserMenu)) {
            return ReturnBody.error();
        }

        return ReturnBody.success(UserMenu);
    }


    @ApiOperation("批量删除菜单")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer[].class, name = "ids", value = "菜单ID数组", required = true)
    @PostMapping("delete")
    public String delete(Integer[] ids) {
        if (null == ids) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (ids.length < 1) {
            return ReturnBody.error("必须选择一个菜单节点");
        }
        if (!userMenuService.removeByIds(new ArrayList<>(Arrays.asList(ids)))) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("设置排序")
    @PostMapping("setSort")
    @ParameterVerify(notNull = {"draggingId", "dropId", "parentId", "dropType"})
    public String setSort(Integer draggingId, Integer dropId, Integer parentId, String dropType) {
        // 判断拖拽节点是否是根节点
        List<UserMenu> newList = new ArrayList<>();

        QueryWrapper<UserMenu> queryWrapper = new CustomQueryWrapper<UserMenu>()
                .eq(UserMenu.PARENT_ID, parentId)
                .orderByDesc(UserMenu.SORT);
        List<UserMenu> list = userMenuService.list(queryWrapper);
        // 被拖拽菜单的元素
        UserMenu dragging = list.stream().filter(item -> item.getId().equals(draggingId)).collect(Collectors.toList()).get(0);

        int sort = list.size();
        // 如果被拖拽目标放在目标之前, 将被拖拽菜单放到目标之前, 并设置排序
        if ("before".equals(dropType)) {
            for (UserMenu UserMenu : list) {
                if (dropId.equals(UserMenu.getId())) {
                    sort--;
                    dragging.setSort(sort);
                    newList.add(dragging);
                    LogUtil.info(dragging.getMenuName() + ", sort:" + dragging.getSort());
                    sort--;
                    UserMenu.setSort(sort);
                    newList.add(UserMenu);
                    LogUtil.info(UserMenu.getMenuName() + ", sort:" + UserMenu.getSort());
                } else if (draggingId.equals(UserMenu.getId())) {
                    continue;
                } else {
                    sort--;
                    UserMenu.setSort(sort);
                    newList.add(UserMenu);
                    LogUtil.info(UserMenu.getMenuName() + ", sort:" + UserMenu.getSort());
                }
            }
        } else {
            // 之后
            for (UserMenu UserMenu : list) {
                if (dropId.equals(UserMenu.getId())) {
                    sort--;
                    UserMenu.setSort(sort);
                    LogUtil.info(UserMenu.getMenuName() + ", sort:" + UserMenu.getSort());
                    newList.add(UserMenu);
                    sort--;
                    dragging.setSort(sort);
                    newList.add(dragging);
                    LogUtil.info(dragging.getMenuName() + ", sort:" + dragging.getSort());
                } else if (draggingId.equals(UserMenu.getId())) {
                    continue;
                } else {
                    sort--;
                    UserMenu.setSort(sort);
                    newList.add(UserMenu);
                    LogUtil.info(UserMenu.getMenuName() + ", sort:" + UserMenu.getSort());
                }
            }
        }

        userMenuService.updateBatchById(newList);
        return ReturnBody.success();
    }

}
