package com.leepsmart.code.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leepsmart.code.admin.pojo.AdMenu;
import com.leepsmart.code.admin.service.AdMenuService;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.LogUtil;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leepsmart generator
 */
@Api(tags = "后台-菜单")
@RestController
@RequestMapping("admin/adMenu")
public class AdMenuController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private AdMenuService adMenuService;


    @ApiOperation("所有菜单")
    @PostMapping("allList")
    public String allList() {
        QueryWrapper<AdMenu> queryWrapper = new CustomQueryWrapper<AdMenu>()
                .select(AdMenu.getFields(AdMenu.VERSION, AdMenu.CREATE_TIME, AdMenu.UPDATE_TIME, AdMenu.SORT))
                .orderByDesc(AdMenu.SORT);
        List<AdMenu> list = adMenuService.list(queryWrapper);
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
        AdMenu adMenu = new AdMenu()
                .setId(id)
                .setMenuName(menuName)
                .setRequestUrl(requestUrl)
                .setMenuIcon(menuIcon)
                .setParentId(parentId)
                .setFlag(flag)
                .setComponent(component)
                .setSort(0);

        // 参数校验
        if (request.getRequestURI().endsWith("update") && !CommUtil.checkNull(adMenu.getId())) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        if (request.getRequestURI().endsWith("add")) {
            adMenu.setId(null);
        }

        // 执行操作方法
        if (!adMenuService.saveOrUpdate(adMenu)) {
            return ReturnBody.error();
        }

        return ReturnBody.success(adMenu);
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
        if (!adMenuService.removeByIds(new ArrayList<>(Arrays.asList(ids)))) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("设置排序")
    @PostMapping("setSort")
    @ParameterVerify(notNull = {"draggingId", "dropId", "parentId", "dropType"})
    public String setSort(Integer draggingId, Integer dropId, Integer parentId, String dropType) {
        // 判断拖拽节点是否是根节点
        List<AdMenu> newList = new ArrayList<>();

        QueryWrapper<AdMenu> queryWrapper = new CustomQueryWrapper<AdMenu>()
                .eq(AdMenu.PARENT_ID, parentId)
                .orderByDesc(AdMenu.SORT);
        List<AdMenu> list = adMenuService.list(queryWrapper);
        // 被拖拽菜单的元素
        AdMenu dragging = list.stream().filter(item -> item.getId().equals(draggingId)).collect(Collectors.toList()).get(0);

        int sort = list.size();
        // 如果被拖拽目标放在目标之前, 将被拖拽菜单放到目标之前, 并设置排序
        if ("before".equals(dropType)) {
            for (AdMenu adMenu : list) {
                if (dropId.equals(adMenu.getId())) {
                    sort--;
                    dragging.setSort(sort);
                    newList.add(dragging);
                    LogUtil.info(dragging.getMenuName() + ", sort:" + dragging.getSort());
                    sort--;
                    adMenu.setSort(sort);
                    newList.add(adMenu);
                    LogUtil.info(adMenu.getMenuName() + ", sort:" + adMenu.getSort());
                } else if (draggingId.equals(adMenu.getId())) {
                    continue;
                } else {
                    sort--;
                    adMenu.setSort(sort);
                    newList.add(adMenu);
                    LogUtil.info(adMenu.getMenuName() + ", sort:" + adMenu.getSort());
                }
            }
        } else {
            // 之后
            for (AdMenu adMenu : list) {
                if (dropId.equals(adMenu.getId())) {
                    sort--;
                    adMenu.setSort(sort);
                    LogUtil.info(adMenu.getMenuName() + ", sort:" + adMenu.getSort());
                    newList.add(adMenu);
                    sort--;
                    dragging.setSort(sort);
                    newList.add(dragging);
                    LogUtil.info(dragging.getMenuName() + ", sort:" + dragging.getSort());
                } else if (draggingId.equals(adMenu.getId())) {
                    continue;
                } else {
                    sort--;
                    adMenu.setSort(sort);
                    newList.add(adMenu);
                    LogUtil.info(adMenu.getMenuName() + ", sort:" + adMenu.getSort());
                }
            }
        }

        adMenuService.updateBatchById(newList);
        return ReturnBody.success();
    }

}
