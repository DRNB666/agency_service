package com.leepsmart.code.admin.controller;

import com.leepsmart.code.admin.pojo.AdProfit;
import com.leepsmart.code.admin.service.AdProfitService;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author leepsmart generator
 */
@Api(tags = "后台-平台收益")
@RestController
@RequestMapping("admin/adProfit")
public class AdProfitController {

    @Resource
    private AdProfitService adProfitService;


    @ApiOperation("收益列表")
    @PostMapping("list")
    public String list(PageInfo pageInfo) {
        // 添加时间筛选
        pageInfo.setTimeScreen(AdProfit.CREATE_TIME);

        // 分页操作
        PageResult<AdProfit> pageResult = new PageUtil<AdProfit>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper.select(AdProfit.getFields(AdProfit.VERSION));
            adProfitService.page(page, queryWrapper);
        });
        return ReturnBody.success(pageResult);
    }


    @ApiOperation("收益数据统计")
    @PostMapping("countData")
    public String countData() throws ExecutionException, InterruptedException {
        Map<String, Object> result = new HashMap<>(8);
        // 获取今天收益
        CompletableFuture<BigDecimal> thisSum = CompletableFuture.supplyAsync(() -> adProfitService.selectSumByDay(0));
        // 获取近7天收益
        CompletableFuture<BigDecimal> sevenSum = CompletableFuture.supplyAsync(() -> adProfitService.selectSumByDay(6));
        // 获取近30天收益
        CompletableFuture<BigDecimal> thirtySum = CompletableFuture.supplyAsync(() -> adProfitService.selectSumByDay(29));
        // 获取昨日收益
        CompletableFuture<BigDecimal> lastSum = CompletableFuture.supplyAsync(() -> {
            Map<String, String> map = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            map.put("endTime", sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            map.put("startTime", sdf.format(calendar.getTime()));
            return adProfitService.selectSumMoneyByDate(map);
        });
        result.put("lastSum", lastSum.get());
        result.put("thisSum", thisSum.get());
        result.put("sevenSum", sevenSum.get());
        result.put("thirtySum", thirtySum.get());
        return ReturnBody.success(result);
    }


    @ApiOperation("根据日期统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "type", value = "week: 州; month: 月; year: 年", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "selectDate", value = "所选日期", required = true),
    })
    @PostMapping("countByDate")
    @ParameterVerify(notNull = {"type", "startTime", "endTime"})
    public String countByDate(String type, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("dateType", type);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return ReturnBody.success(adProfitService.selectCountByDate(map));
    }

}
