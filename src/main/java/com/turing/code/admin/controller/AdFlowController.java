package com.turing.code.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.admin.mapper.AdFlowMapper;
import com.turing.code.admin.pojo.vo.AdFlowVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.turing.code.admin.service.AdFlowService;
import com.turing.code.admin.pojo.AdFlow;
import javax.servlet.http.HttpServletRequest;
import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-05
 */
@Api(tags = "管理员-平台流水")
@ApiResponses({
    @ApiResponse(code = 200, message = "请求成功"),
    @ApiResponse(code = 401, message = "无用户权限"),
    @ApiResponse(code = 403, message = "无资源权限"),
    @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "admin/adFlow", produces = "text/plain;charset=utf-8")
public class AdFlowController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private AdFlowService adFlowService;
    @Resource
    private AdFlowMapper adFlowMapper;

    @ApiOperation("分页列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        PageResult<AdFlow> pageResult = new PageUtil<AdFlow>(pageInfo).startPage((page, queryWrapper) -> adFlowService.page(page, queryWrapper));
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("添加或修改")
    @PostMapping({"add", "update"})
    public String addOrUpdate(AdFlow adFlow) {
        adFlow.setCreateTime(null).setStatus(null);
        if (request.getRequestURI().contains("update")) {
            if (!CommUtil.checkNull(adFlow.getId())) {
                return ReturnBody.error();
            }
        }
        if (!adFlowService.saveOrUpdate(adFlow)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("删除")
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(String id) {
        return adFlowService.removeById(id) ? ReturnBody.success() : ReturnBody.error();
    }

    /**
     * 查询业务员推广商品或者平台的记录
     */
    @ApiOperation("业务分佣记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "type", value = "推荐类型:1直推 2：间推", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "orderId", value = "订单号", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "1收入 2支出 3收入冻结中 4退款", required = false),
    })
    @PostMapping(value = "rewardFlow")
    public String rewardFlow(PageInfo pageInfo, Integer type, Long orderId, Integer status){
        Map<String, Object> map = new HashMap<>();
        if (CommUtil.checkNull(pageInfo.getEndTime())) {
            map.put("endTime", pageInfo.getEndTime());
        }
        if (CommUtil.checkNull(pageInfo.getStartTime())) {
            map.put("startTime", pageInfo.getStartTime());
        }
        if (CommUtil.checkNull(type)) {
            map.put("type", type);
        }
        if (CommUtil.checkNull(orderId)) {
            map.put("orderId", orderId);
        }
        if (CommUtil.checkNull(status)) {
            map.put("status", status);
        }
        return adFlowService.selectRewardFlow(pageInfo, map);
    }

    @ApiOperation("业务分佣记录(导出)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "type", value = "推荐类型:1直推 2：间推", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "orderId", value = "订单号", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "1收入 2支出 3收入冻结中 4退款", required = false),
    })
    @PostMapping(value = "rewardFlowExport")
    public String rewardFlowExport(String startTime, String endTime, Integer type, Long orderId, Integer status) {
        Map<String, Object> map = new HashMap<>();
        if (CommUtil.checkNull(startTime)) {
            map.put("startTime", startTime);
        }
        if (CommUtil.checkNull(endTime)) {
            map.put("endTime", endTime);
        }
        if (CommUtil.checkNull(type)) {
            map.put("type", type);
        }
        if (CommUtil.checkNull(orderId)) {
            map.put("orderId", orderId);
        }
        if (CommUtil.checkNull(status)) {
            map.put("status", status);
        }
        return adFlowService.selectRewardFlowExport(map);
    }


    @ApiOperation("管理分配记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "orderId", value = "订单号", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "1收入 2支出 3收入冻结中 4退款", required = false),
    })
    @PostMapping(value = "adminFlow")
    public String adminFlow(PageInfo pageInfo, Long orderId, Integer status) {
        Map<String, Object> map = new HashMap<>();
        if (CommUtil.checkNull(pageInfo.getEndTime())) {
            map.put("endTime", pageInfo.getEndTime());
        }
        if (CommUtil.checkNull(pageInfo.getStartTime())) {
            map.put("startTime", pageInfo.getStartTime());
        }
        if (CommUtil.checkNull(orderId)) {
            map.put("orderId", orderId);
        }
        if (CommUtil.checkNull(status)) {
            map.put("status", status);
        }
        PageResult<AdFlowVO> pageResult = new PageResult<>();
        Page<AdFlowVO> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<AdFlowVO> iPage = adFlowMapper.selectAdFlow(page,map);
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("管理分配记录导出")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "orderId", value = "订单号", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "1收入 2支出 3收入冻结中 4退款", required = false),
    })
    @PostMapping(value = "adminFlowExport")
    public String adminFlowExport(String startTime, String endTime, Long orderId, Integer status) {
        Map<String, Object> map = new HashMap<>();
        if (CommUtil.checkNull(startTime)) {
            map.put("startTime", startTime);
        }
        if (CommUtil.checkNull(endTime)) {
            map.put("endTime", endTime);
        }
        if (CommUtil.checkNull(orderId)) {
            map.put("orderId", orderId);
        }
        map.put("status", status);
        return adFlowService.selectAdFlowExport(map);
    }


}
