package com.turing.code.tenant.controller;

import com.turing.code.common.utils.FileUtil;
import com.turing.code.common.utils.ImgUrlEnum;
import com.turing.code.tenant.mapper.TenantShopCategoryMapper;
import com.turing.code.tenant.mapper.TenantShopMapper;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantShopCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.tenant.service.TenantShopService;
import com.turing.code.tenant.pojo.TenantShop;
import javax.servlet.http.HttpServletRequest;
import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-01
 */
@Api(tags = "租户-店铺信息")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantShop", produces = "text/plain;charset=utf-8")
public class TenantShopController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantShopService tenantShopService;
    @Resource
    private TenantShopMapper tenantShopMapper;
    @Resource
    private TenantShopCategoryMapper tenantShopCategoryMapper;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<TenantShop> pageResult = new PageUtil<TenantShop>(pageInfo).startPage((page, queryWrapper) -> tenantShopService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(TenantShop tenantShop) {
        tenantShop.setCreateTime(null).setStatus(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(tenantShop.getId())) {
                return ReturnBody.error();
            }
        }
        if (!tenantShopService.saveOrUpdate(tenantShop)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return tenantShopService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

    @PostMapping(value = "curMerchantList")
    @ApiOperation("商家店铺列表 不带分页")
    public String curMerchantList() {
        TenantInfo merchantInfo =(TenantInfo)request.getAttribute("info");
        Map<String,Object> map = new HashMap<>();
        map.put("tenantId",merchantInfo.getId());
        return ReturnBody.success(tenantShopMapper.selectMap(map));
    }

    @PostMapping(value = "proCategoryList")
    @ApiOperation("商品分组集合不带分页")
    public String proCategoryList() {
        return ReturnBody.success(tenantShopCategoryMapper.selectInCache());
    }

    @PostMapping(value = "public/proUpload")
    @ApiOperation("商家 商品 富文本图片上传接口")
    public String upload(@RequestParam MultipartFile file){
        String url = FileUtil.upload(ImgUrlEnum.TENANT_PRO_INVITE.getPath(), file, "image",request);
        return ReturnBody.success(url);
    }

}
