package com.turing.code.admin.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.admin.mapper.AdFlowProfitMapper;
import com.turing.code.admin.pojo.AdFlow;
import com.turing.code.admin.mapper.AdFlowMapper;
import com.turing.code.admin.pojo.AdFlowProfit;
import com.turing.code.admin.pojo.vo.AdFlowVO;
import com.turing.code.admin.service.AdFlowService;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import com.turing.code.common.utils.CacheConstant;
import com.turing.code.common.utils.ExcelUtil;
import com.turing.code.common.utils.PathUtil;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.system.mapper.SysParamsMapper;
import com.turing.code.system.pojo.SysParams;
import com.turing.code.tenant.pojo.vo.UserRoyalFlowProVO;
import com.turing.code.user.mapper.UserRoyalFlowProMapper;
import com.turing.code.user.pojo.UserRoyalFlow;
import com.turing.code.user.pojo.UserRoyalFlowPro;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author turing generator
 * @since 2024-06-05
 */
@Service
public class AdFlowServiceImpl extends CommonServiceImpl<AdFlowMapper, AdFlow> implements AdFlowService {

    @Resource
    private SysParamsMapper sysParamsMapper;
    @Resource
    private UserRoyalFlowProMapper userRoyalFlowProMapper;
    @Resource
    private AdFlowMapper adFlowMapper;
    @Resource
    private AdFlowProfitMapper adFlowProfitMapper;

    @Override
    public String selectRewardFlow(PageInfo pageInfo, Map<String, Object> map) {
        //处理用户权益
        JSONArray user = new JSONArray();
        user.add(sysParamsMapper.selectById(CacheConstant.FIRM_DIRECT_RECOMMEND).getValue());
        user.add(sysParamsMapper.selectById(CacheConstant.FIRM_INDIRECT_RECOMMEND).getValue());
        user.add(sysParamsMapper.selectById(CacheConstant.MERCHANT_DIRECT_RECOMMEND).getValue());
        user.add(sysParamsMapper.selectById(CacheConstant.MERCHANT_INDIRECT_RECOMMEND).getValue());

        JSONArray goldCon = new JSONArray(Arrays.asList(sysParamsMapper.selectById(CacheConstant.GOLD_MEMBER_CONDITION).getValue().split(",")));
        JSONArray goldAutho = new JSONArray(Arrays.asList(sysParamsMapper.selectById(CacheConstant.GOLD_MEMBER_AUTHO).getValue().split(",")));

        JSONArray partnerCon = new JSONArray(Arrays.asList(sysParamsMapper.selectById(CacheConstant.PARTNER_CONDITION).getValue().split(",")));
        JSONArray partnerAutho = new JSONArray(Arrays.asList(sysParamsMapper.selectById(CacheConstant.PARTNER_AUTHO).getValue().split(",")));

        JSONArray superPartnerCon = new JSONArray(Arrays.asList(sysParamsMapper.selectById(CacheConstant.SUPER_PARTNER_CONDITION).getValue().split(",")));
        JSONArray superPartnerAutho = new JSONArray(Arrays.asList(sysParamsMapper.selectById(CacheConstant.SUPER_PARTNER_AUTHO).getValue().split(",")));

        PageResult<UserRoyalFlowProVO> pageResult = new PageResult<>();
        Page<UserRoyalFlowProVO> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<UserRoyalFlowProVO> iPage = userRoyalFlowProMapper.selectFlowProWithAllByMap(page,map);
        pageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        pageResult.setList(iPage.getRecords());

        pageResult.getList().forEach(flow -> {
            if (flow.getQueryType() == 2) {
                //2为应用分佣
                switch (flow.getGrade()) {
                    case 1:
                        flow.setAutho(user.toJSONString());
                        flow.setCondition(goldCon.toJSONString());
                        break;
                    case 2:
                        flow.setAutho(goldAutho.toJSONString());
                        flow.setCondition(partnerCon.toJSONString());
                        break;
                    case 3:
                        flow.setAutho(partnerAutho.toJSONString());
                        flow.setCondition(superPartnerCon.toJSONString());
                        break;
                    case 4:
                        flow.setAutho(superPartnerAutho.toJSONString());
                        flow.setCondition(superPartnerCon.toJSONString());
                        break;
                    default:
                        break;
                }
            } else {
                if (flow.getGrade() == null) {
                    flow.setCondition(superPartnerCon.toJSONString());
                    flow.setQueryType(2);
                    flow.setSourceType(14);
                } else {
                    //1为商品分佣
                    flow.setGrade(flow.getGrade() + 1);
                }
            }
        });
        return ReturnBody.success(pageResult);
    }

    @Override
    public String selectRewardFlowExport(Map<String, Object> map) {
        List<UserRoyalFlowProVO> userRoyalFlowProVOS = userRoyalFlowProMapper.selectFlowProWithAllByMap(map);
        if (userRoyalFlowProVOS.isEmpty()) {
            return ReturnBody.error("没有数据");
        }
        //封装excel参数
        Map<String, List<String>> exportMap = new LinkedHashMap<>();
        for (int i = 0; i < userRoyalFlowProVOS.size(); i++) {
            UserRoyalFlowProVO flow = userRoyalFlowProVOS.get(i);
            ArrayList<String> members = new ArrayList<>();
            members.add(flow.getId().toString());
            members.add(flow.getQueryType() == 1 ? "商家" : "平台");
            members.add(flow.getOrderAmount() != null ? flow.getOrderAmount().toString() : "");
            members.add(flow.getNick());
            String gradeName;//状态  1普通会员；2黄金会员；3合伙人;4至尊合伙人
            String statusName;//0待付款   1冻结中   2已发放   3已退款
            String sourceTypeName;//1集体应用，2市级代理应用 , 3商家分销应用，11拼团应用，12秒杀应用，13优惠券应用   0分销推广商品
            if (flow.getQueryType() == 1) {
                flow.setGrade(flow.getGrade() + 1);
            }
            switch (flow.getGrade()) {
                case 1:
                    gradeName = "普通会员";
                    break;
                case 2:
                    gradeName = "黄金会员";
                    break;
                case 3:
                    gradeName = "合伙人";
                    break;
                case 4:
                    gradeName = "至尊合伙人";
                    break;
                default:
                    gradeName = "";
                    break;
            }
            switch (flow.getStatus()) {
                case 0:
                    statusName = "待付款";
                    break;
                case 1:
                    statusName = "冻结中";
                    break;
                case 2:
                    statusName = "已发放";
                    break;
                case 3:
                    statusName = "已退款";
                    break;
                default:
                    statusName = "";
                    break;
            }
            switch (flow.getSourceType()) {
                case 1:
                    sourceTypeName = "集体应用";
                    break;
                case 2:
                    sourceTypeName = "市级代理应用";
                    break;
                case 3:
                    sourceTypeName = "商家分销应用";
                    break;
                case 11:
                    sourceTypeName = "拼团应用";
                    break;
                case 12:
                    sourceTypeName = "秒杀应用";
                    break;
                case 13:
                    sourceTypeName = "优惠券应用";
                    break;
                case 0:
                    sourceTypeName = "分销推广商品";
                    break;
                default:
                    sourceTypeName = "";
                    break;
            }
            members.add(gradeName);
            members.add(sourceTypeName);
            members.add(flow.getSecAmount().toString());
            members.add(statusName);
            members.add(flow.getType() == 1 ? "直推" : "间推");
            members.add(CacheConstant.FORMAT.format(flow.getCreateTime()));
            exportMap.put(i + "", members);
        }
        //设置表头参数
        String[] strArray = {"订单号", "类型", "订单金额", "分配对象", "分销员等级", "分配内容", "分配金额", "状态", "类型", "时间"};
        String sifxx = "/files/excel/" + "业务员分配记录" + new SimpleDateFormat(" yyyy-MM-dd HH-mm-ss").format(new Date()) + ".xls";
        String s = PathUtil.getFullRealPath() + sifxx;
        ExcelUtil.createExcel(exportMap, strArray, s);
        return ReturnBody.success(sifxx);
    }

    @Override
    public String selectAdFlowExport(Map<String, Object> map) {
        List<AdFlowVO> adFlowVOS = adFlowMapper.selectAdFlow(map);
        if (adFlowVOS.isEmpty()) {
            return ReturnBody.error("没有数据");
        }
        //封装excel参数
        Map<String, List<String>> exportMap = new LinkedHashMap<>();
        for (int i = 0; i < adFlowVOS.size(); i++) {
            AdFlowVO flow = adFlowVOS.get(i);
            ArrayList<String> members = new ArrayList<>();
            members.add(flow.getId().toString());
            members.add(flow.getOrderMoney() != null ? flow.getOrderMoney().toString() : "");
            members.add(flow.getName());
            String typeName;//状态  1集体；2城市代理；3平台
            String statusName;//状态  1：收入，2：支出  3冻结中  4退款
            switch (flow.getType()) {
                case 1:
                    typeName = "集体";
                    break;
                case 2:
                    typeName = "城市代理";
                    break;
                case 3:
                    typeName = "平台";
                    break;
                default:
                    typeName = "";
                    break;
            }
            switch (flow.getStatus()) {
                case 1:
                    statusName = "收入";
                    break;
                case 2:
                    statusName = "支出";
                    break;
                case 3:
                    statusName = "冻结中";
                    break;
                case 4:
                    statusName = "退款";
                    break;
                default:
                    statusName = "";
                    break;
            }
            members.add(typeName);
            members.add(flow.getRemark());
            members.add(flow.getMoney().toString());
            members.add(statusName);
            members.add(CacheConstant.FORMAT.format(flow.getCreateTime()));
            exportMap.put(i + "", members);
        }
        //设置表头参数
        String[] strArray = {"订单号", "订单金额", "分配对象", "分配类型", "分配内容", "分配金额", "状态", "时间"};
        String sifxx = "/files/excel/" + "管理分配记录" + new SimpleDateFormat(" yyyy-MM-dd HH-mm-ss").format(new Date()) + ".xls";
        String s = PathUtil.getFullRealPath() + sifxx;
        ExcelUtil.createExcel(exportMap, strArray, s);
        return ReturnBody.success(sifxx);
    }

    @Override
    public void addWxServiceMoney(BigDecimal money, Long orderId, int type, int status) {
        // 添加平台收益流水
        AdFlowProfit adFlowProfit = new AdFlowProfit();
        adFlowProfit.setMoney(money.multiply(BigDecimal.valueOf(6)).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP));
        adFlowProfit.setOrigin("微信千分之六手续费");
        adFlowProfit.setOriginType(44);
        adFlowProfit.setOrderMoney(money);
        adFlowProfit.setOrderId(orderId);
        adFlowProfit.setType(type);
        adFlowProfit.setStatus(status);

        if (adFlowProfit.getMoney().compareTo(BigDecimal.ZERO) < 1) {
            return;
        }

        if (adFlowProfitMapper.insert(adFlowProfit) != 1) {
            throw new ServiceException();
        }

        AdFlow adFlow = new AdFlow();
        adFlow.setMoney(adFlowProfit.getMoney());
        adFlow.setType(type);
        adFlow.setRemark("微信千分之六手续费");
        adFlow.setOrderId(orderId);
        adFlow.setOrderType(44);

        if (adFlowMapper.insert(adFlow) != 1) {
            throw new ServiceException();
        }
    }


}
