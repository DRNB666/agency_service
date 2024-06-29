package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
</#if>
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import javax.annotation.Resource;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import javax.servlet.http.HttpServletRequest;
import ${cfg.packageName}.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import ${cfg.packageName}.common.annotation.parameterverify.ParameterVerify;
import ${cfg.packageName}.common.utils.page.pojo.PageResult;
import ${cfg.packageName}.common.utils.page.pojo.PageInfo;
import ${cfg.packageName}.common.utils.page.util.PageUtil;
import ${cfg.packageName}.common.utils.CommUtil;

/**
 * @author ${author}
 * @since ${date}
 */
<#if swagger2>
@Api(tags = "${table.comment!}")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping(value = "<#if package.ModuleName?? && package.ModuleName != "">${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>", produces = "text/plain;charset=utf-8")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private HttpServletRequest request;
    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<${entity}> pageResult = new PageUtil<${entity}>(pageInfo).startPage((page, queryWrapper) -> ${table.serviceName?uncap_first}.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(${entity} ${entity?uncap_first}) {
        ${entity?uncap_first}.setCreateTime(null).setStatus(null).setUpdateTime(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(${entity?uncap_first}.getId())) {
                return ReturnBody.error();
            }
        }
        if (!${table.serviceName?uncap_first}.saveOrUpdate(${entity?uncap_first})) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return ${table.serviceName?uncap_first}.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

}
</#if>
