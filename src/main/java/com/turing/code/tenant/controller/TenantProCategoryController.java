package com.turing.code.tenant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.common.utils.ImgUrlEnum;
import com.turing.code.common.utils.encrypt.Base64Util;
import com.turing.code.common.utils.page.pojo.SortWay;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantShopCategory;
import com.turing.code.tenant.pojo.vo.TenantProCategoryVo;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.turing.code.tenant.service.TenantProCategoryService;
import com.turing.code.tenant.pojo.TenantProCategory;

import javax.servlet.http.HttpServletRequest;

import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-06
 */
@Api(tags = "租户-商品分类表")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantProCategory", produces = "text/plain;charset=utf-8")
public class TenantProCategoryController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantProCategoryService tenantProCategoryService;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo,String name) {
        Integer pId = (Integer) request.getAttribute("pId");
        List<TenantProCategoryVo> tenantProCategoryVos = new ArrayList<>();
        PageResult<TenantProCategoryVo> result = new PageResult<>();
        PageResult<TenantProCategory> pageResult = new PageUtil<TenantProCategory>(pageInfo).startPage((page, queryWrapper) -> {
            if (CommUtil.checkNull(name)) {
                queryWrapper.eq(TenantProCategory.NAME, name);
            }
            queryWrapper.eq(TenantProCategory.TENANT_ID,pId);
            queryWrapper.eq(TenantProCategory.PARENT_ID, -1);
            tenantProCategoryService.page(page, queryWrapper);
        });
        pageResult.getList().forEach(item -> {
            //查询是否有子分类
            QueryWrapper<TenantProCategory> eq = new QueryWrapper<TenantProCategory>()
                    .eq(TenantProCategory.PARENT_ID, item.getId()).eq(TenantProCategory.TENANT_ID,pId);
            int count = tenantProCategoryService.count(eq);
            TenantProCategoryVo tenantProCategoryVo = new TenantProCategoryVo();
            BeanUtils.copyProperties(item, tenantProCategoryVo);
            tenantProCategoryVo.setHasChildren(count != 0);
            tenantProCategoryVos.add(tenantProCategoryVo);
        });
        //查询系统默认分类
        List<TenantProCategory> list = tenantProCategoryService.list(new QueryWrapper<TenantProCategory>().eq(TenantProCategory.TENANT_ID, -1));
        if (CommUtil.checkNull(list)){
            list.forEach(item->{
                TenantProCategoryVo tenantProCategoryVo = new TenantProCategoryVo();
                BeanUtils.copyProperties(item, tenantProCategoryVo);
                tenantProCategoryVo.setHasChildren(false);
                tenantProCategoryVos.add(tenantProCategoryVo);
            });

        }
        BeanUtils.copyProperties(pageResult, result);
        result.setList(tenantProCategoryVos);
        return ReturnBody.success(result);
    }

    @ApiOperation("子分类列表")
    @PostMapping("sonList")
    public String sonList(Long id) {
        Integer pId = (Integer) request.getAttribute("pId");
        List<TenantProCategoryVo> tenantProCategoryVos = new ArrayList<>();
        QueryWrapper<TenantProCategory> Eq = new QueryWrapper<TenantProCategory>()
                .eq(TenantProCategory.PARENT_ID, id).eq(TenantProCategory.TENANT_ID,pId);
        List<TenantProCategory> list = tenantProCategoryService.list(Eq);
        list.forEach(item -> {
            QueryWrapper<TenantProCategory> eq = new QueryWrapper<TenantProCategory>()
                    .eq(TenantProCategory.PARENT_ID, item.getId()).eq(TenantProCategory.TENANT_ID,pId);
            int count = tenantProCategoryService.count(eq);
            TenantProCategoryVo tenantProCategoryVo = new TenantProCategoryVo();
            BeanUtils.copyProperties(item, tenantProCategoryVo);
            tenantProCategoryVo.setHasChildren(count != 0);
            tenantProCategoryVos.add(tenantProCategoryVo);
        });
        return ReturnBody.success(tenantProCategoryVos);
    }

    @ApiOperation("根据名称查询分类")
    @PostMapping("queryCategoryByName")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "name", value = "名称", required = true),
     })
    public String queryCategoryByName(String name){
        Integer rId = (Integer) request.getAttribute(TenantInfo.ID);
        QueryWrapper<TenantProCategory> eq =
                new QueryWrapper<TenantProCategory>().eq(TenantProCategory.TENANT_ID, rId).like(TenantProCategory.NAME,name);
        return ReturnBody.success(tenantProCategoryService.list(eq));
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "edit"})
    public String addOrUpdate(TenantProCategory tenantProCategory) {
        Integer pId = (Integer) request.getAttribute("pId");
        tenantProCategory.setCreateTime(null).setStatus(null).setUpdateTime(null).setTenantId(pId);
        if (request.getRequestURI().contains("edit")) {
            if (!CommUtil.checkNull(tenantProCategory.getId())) {
                return ReturnBody.error();
            }
        }
        if (CommUtil.checkNull(tenantProCategory.getCover()) && tenantProCategory.getCover().contains("data:image")) {
            tenantProCategory.setCover(Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), tenantProCategory.getCover(),
                    false));
        }
        if (!CommUtil.checkNull(tenantProCategory.getId())){
            while (true){
                String random = CommUtil.createRandom(true, 6);
                TenantProCategory proCategory = tenantProCategoryService.getById(Long.parseLong(random));
                if (!CommUtil.checkNull(proCategory)&&random.length()==6){
                    tenantProCategory.setId(Long.parseLong(random));
                    break;
                }
            }
        }
        if (!tenantProCategoryService.saveOrUpdate(tenantProCategory)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(Long id) {
        tenantProCategoryService.delete(id);
        return ReturnBody.success();
    }

    @ApiOperation("获取所有分类")
    @PostMapping("allList")
    public String allList() {
        Integer pId = (Integer) request.getAttribute("pId");
        QueryWrapper<TenantProCategory> eq = new QueryWrapper<TenantProCategory>().eq(TenantProCategory.TENANT_ID, pId);
        List<TenantProCategory> list = tenantProCategoryService.list(eq);
        return ReturnBody.success(list);
    }

}
