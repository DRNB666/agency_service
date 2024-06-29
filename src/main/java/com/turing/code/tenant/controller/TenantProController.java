package com.turing.code.tenant.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.FileUtil;
import com.turing.code.common.utils.ImgUrlEnum;
import com.turing.code.common.utils.encrypt.Base64Util;
import com.turing.code.tenant.mapper.*;
import com.turing.code.tenant.pojo.*;
import com.turing.code.tenant.service.impl.CommService;
import com.turing.code.user.pojo.UserOrder;
import com.turing.code.user.pojo.vo.MerchantProInvite;
import io.swagger.annotations.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import com.turing.code.tenant.service.TenantProService;

import javax.servlet.http.HttpServletRequest;

import com.turing.code.common.utils.returnbody.*;
import org.springframework.web.bind.annotation.PostMapping;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.CommUtil;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author turing generator
 * @since 2024-06-01
 */
@Api(tags = "租户-商品信息表")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "tenant/tenantPro", produces = "text/plain;charset=utf-8")
public class TenantProController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantProService tenantProService;
    @Resource
    private TenantInviteMapper tenantInviteMapper;
    @Resource
    private TenantInviteConfigMapper tenantInviteConfigMapper;
    @Resource
    private TenantProMapper tenantProMapper;
    @Resource
    private TenantShopMapper tenantShopMapper;
    @Resource
    private CommService commService;
    @Resource
    private TenantProIdentifiMapper tenantProIdentifiMapper;
    @Resource
    private TenantProSkuMapper tenantProSkuMapper;

    @PostMapping(value = "list")
    @ApiOperation("获取租户商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "shopId", value = "店铺ID", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "categoryId", value = "商品分组Id", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "name", value = "商品名称", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "isInvite", value = "是否推广 0不推广  1推广", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "freight", value = "包邮 0不包邮  1包邮", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "1上架  2下架", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "carriage", value = "1自提  2外送", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "carriage", value = "1自提  2外送", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "vipPro", value = "0普通商品 1会员商品", required = false),
    })
    public String list(PageInfo pageInfo, Long shopId, Long categoryId, String name, Integer isInvite, Integer freight,
                       Integer status, Integer carriage, Integer indexRecommend, Integer vipPro) {
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        Map<String, Object> map = new HashMap<>();
        if (CommUtil.checkNull(pageInfo.getStartTime())) {
            map.put("startTime", pageInfo.getStartTime());
        }
        if (CommUtil.checkNull(pageInfo.getEndTime())) {
            map.put("endTime", pageInfo.getEndTime());
        }
        if (CommUtil.checkNull(isInvite)) {
            map.put("isInvite", isInvite);
        }
        if (carriage != null) {
            map.put("carriageLike", carriage.toString());
        }
        if (vipPro != null) {
            map.put("vipPro", vipPro);
        }
        if (freight != null) {
            if (freight == 1) {
                map.put("freight", 0);
            } else {
                map.put("freightGt", 0);
            }
        }
        map.put("tenantId", merchantInfo.getId());
        map.put("indexRecommend", indexRecommend);
        map.put("shopId", shopId);
        map.put("categoryId", categoryId);
        map.put("search", name);
        if (CommUtil.checkNull(status) && status != 0) {
            map.put("status", status);
        }
        map.put("orderBy", "create_time desc");
        if (CommUtil.checkNull(pageInfo.getSortKey())) {
            switch (pageInfo.getSortKey()) {
                case "weightSort":
                    map.put("orderBy", "weight_sort");
                    break;
                case "weightSortDesc":
                    map.put("orderBy", "weight_sort desc");
                    break;
                case "price":
                    map.put("orderBy", "price");
                    break;
                case "priceDesc":
                    map.put("orderBy", "price desc");
                    break;
                default:
                    map.put("orderBy", "create_time desc");
                    break;
            }
        }
        PageResult merchantProPageResult = new PageResult<>();
        Page<TenantPro> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        //这里是自定义sql
        IPage<TenantPro> iPage = tenantProMapper.selectByMapEx(page, map);
        merchantProPageResult.setPageSize(iPage.getSize()).setPageNo(iPage.getCurrent()).setPages(iPage.getPages()).
                setTotal(iPage.getTotal());
        merchantProPageResult.setList(iPage.getRecords());
        JSONArray result = new JSONArray();

        TenantInviteConfig config = tenantInviteConfigMapper.selectByMerchantId(merchantInfo.getId());

        BigDecimal money100 = new BigDecimal(100);
        Map query = new HashMap(8) {{
            put("notDelete", "");
        }};
        merchantProPageResult.getList().forEach(e -> {
            TenantPro o = (TenantPro) e;
            String[] split = o.getCarriage().replace("[", "").replace("]", "").split(",");
            if (split.length == 2) {
                o.setCarriage("自提/外送");
            } else if ("1".equals(split[0])) {
                o.setCarriage("自提");
            } else if ("2".equals(split[0])) {
                o.setCarriage("外送");
            }
            //计算分销商品的各级佣金
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(o);
            if (o.getIsInvite() == 1) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(o.getPrice().multiply(config.getUserDirect()).divide(money100).setScale(2, BigDecimal.ROUND_DOWN));
                jsonArray.add(o.getPrice().multiply(config.getUserIndirect()).divide(money100).setScale(2, BigDecimal.ROUND_DOWN));
                jsonArray.add(o.getPrice().multiply(config.getGoldDirect()).divide(money100).setScale(2, BigDecimal.ROUND_DOWN));
                jsonArray.add(o.getPrice().multiply(config.getGoldIndirect()).divide(money100).setScale(2, BigDecimal.ROUND_DOWN));
                jsonArray.add(o.getPrice().multiply(config.getPartnerDirect()).divide(money100).setScale(2, BigDecimal.ROUND_DOWN));
                jsonArray.add(o.getPrice().multiply(config.getPartnerIndirect()).divide(money100).setScale(2, BigDecimal.ROUND_DOWN));
                jsonArray.add(o.getPrice().multiply(config.getSuperPartnerDirect()).divide(money100).setScale(2, BigDecimal.ROUND_DOWN));
                jsonArray.add(o.getPrice().multiply(config.getSuperPartnerIndirect()).divide(money100).setScale(2, BigDecimal.ROUND_DOWN));
                jsonObject.put("reward", jsonArray);
            }
            query.put("proId", o.getId());
            jsonObject.put("isGroup", 0);
            jsonObject.put("isSpike", 0);
            result.add(jsonObject);
        });
        merchantProPageResult.setList(result);
        return ReturnBody.success(merchantProPageResult);
    }

    @PostMapping("saveOrUpdate")
    @ApiOperation("添加或修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "shopId", value = "店铺id", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "categoryId", value = "商品分组Id", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "name", value = "商品名称 ", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "cover", value = "商品封面图 ", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "banner", value = "商品展示图 多张 转成String格式 \"[\"data:image\",\"data:image\",\"data:image\",\"data\",\"data\"]\"", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "description", value = "商品描述", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "video", value = "视频", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "carriage", value = "配送方式 1：自题 2：外送", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "district", value = "区 暂时没用", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "city", value = "市 暂时没用", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "province", value = "省 暂时没用", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "商品状态 1：上架 2：下架", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "oriPrice", value = "商品 原价 当不使用规格时传入", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "curPrice", value = "商品 现价 当不使用规格时传入", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "freight", value = "运费,0为包邮", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "stock", value = "商品 库存 当不使用规格时传入", required = false),
//            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "firmPrice", value = "商品 老乡价 当不使用规格时传入", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "isSpec", value = "规格 id -1 表示不使用 1表示使用", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "video", value = "视频", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "skuList", value = "sku数组", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "isInvite", value = "是否推广 0不推广  1推广", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "isAfterSell", value = "是否售后 1是  0否", required = false),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "afterSellIdent", value = "售后标识", required = false),
    })
    @ParameterVerify(notNull = {"shopId", "categoryId", "name", "cover", "banner", "status", "carriage", "isInvite"})
    public String saveOrUpdate(Long id, Long shopId, Integer categoryId, String name, String cover, String banner, String description, String carriage,
                               String district, String city, String province, Integer status, BigDecimal oriPrice, BigDecimal curPrice, Integer stock,
                               Integer isSpec, String skuList, Integer isInvite, @RequestParam(required = false) MultipartFile video,
                               @RequestParam(required = false, defaultValue = "0") BigDecimal freight, Integer isAfterSell,
                               String afterSellIdent, Integer vipPro, BigDecimal giftAmount, Integer giftPromoteLeve, Integer giftVipLevel) {

        if (freight.compareTo(BigDecimal.ZERO) < 0) {
            return ReturnBody.error("运费不能为负数");
        }
        //获取当前登录的商家
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        Integer pId = (Integer) request.getAttribute("pId");
        if (isInvite == 1) {
            TenantInviteConfig merchantInviteConfig = tenantInviteConfigMapper.selectByMerchantId(merchantInfo.getId());
            if (merchantInviteConfig == null) {
                return ReturnBody.error("请先到分销管理-分销配置列表配置参数");
            }
        }
        TenantShop shop = tenantShopMapper.selectById(shopId);
        if (shop == null) {
            return ReturnBody.error("该店铺异常，无法操作商品，请联系管理员");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("tenant_id", merchantInfo.getId());
        map.put("id", shopId);
        List<TenantShop> shops = tenantShopMapper.selectByMap(map);
        if (shops.size() <= 0) {
            return ReturnBody.error(ResultCodeInfo.NO_AUTHORITY_OPERATE);
        }
        if (cover.contains("data:image")) {
            cover = Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), cover, false);
        }
        StringBuilder bannerUrl = new StringBuilder();
        JSONArray array = JSONArray.parseArray(banner);
        //商品轮播图
        for (int i = 0; i < array.size(); i++) {
            String path = (String) array.get(i);
            if (i == array.size() - 1) {
                if (path.contains("data:image")) {
                    bannerUrl.append(Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), path, false));
                } else {
                    bannerUrl.append(path);
                }
            } else {
                if (path.contains("data:image")) {
                    bannerUrl.append(Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), path, false)).append(",");
                } else {
                    bannerUrl.append(path).append(",");
                }
            }
        }
        //视频轮播图
        String videoUrl = null;
        if (video != null) {
            videoUrl = FileUtil.upload(ImgUrlEnum.TENANT_PRO_INVITE.getPath(), video, "video", request);
        }
        //商品信息
        TenantPro merchantPro = new TenantPro();
        if (vipPro == 1) {
            //会员商品特殊参数
            if (!CommUtil.checkNull(giftVipLevel)) {
                return ReturnBody.error("请选择直升会员等级");
            }
            merchantPro.setVipPro(vipPro);
            merchantPro.setGiftAmount(giftAmount);
            merchantPro.setGiftPromoteLeve(giftPromoteLeve);
            merchantPro.setGiftVipLevel(giftVipLevel);
        }
        merchantPro.setShopId(shopId);
        merchantPro.setCategoryId(categoryId);
        merchantPro.setName(name);
        merchantPro.setTenantId(pId.longValue());
        merchantPro.setCover(cover);
        merchantPro.setBanner(bannerUrl.toString());
        merchantPro.setVideo(videoUrl);
        merchantPro.setDescription(description);
        merchantPro.setCarriage(carriage);
        merchantPro.setDistrict(district);
        merchantPro.setIsSpec(isSpec);
        merchantPro.setCity(city);
        merchantPro.setProvince(province);
        merchantPro.setStatus(status);
        merchantPro.setStock(stock);
        merchantPro.setPrice(curPrice);
        merchantPro.setFreight(freight);
        merchantPro.setOriginalPrice(oriPrice);
        merchantPro.setIsInvite(isInvite);
        merchantPro.setIsAfterSell(isAfterSell);
        merchantPro.setAfterSellIdent(afterSellIdent);
        //保存商品
        if (CommUtil.checkNull(id)) {
            TenantPro oldMerchantPro = tenantProService.getById(id);
            if (oldMerchantPro == null) {
                return ReturnBody.error("商品不存在或已删除");
            }
            merchantPro.setId(id);
            tenantProService.updateProSkuSpec(merchantPro, skuList);
        } else {
//            merchantPro.setCity(merchantInfo.getCityCode().toString());
            tenantProService.insertProSkuSpec(merchantPro, skuList);
        }
        return ReturnBody.success();
    }

    @PostMapping(value = "deletePro")
    @ApiOperation("删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "id", value = "商品ID", required = true),
    })
    @ParameterVerify(notNull = "id")
    public String deletePro(Long id) {
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        Map<String, Object> map = new HashMap<>();
        //判断是否删除的为当前的商户的商品 以防非法操作
        map.put("tenant_id", merchantInfo.getId());
        map.put("id", id);
        List<TenantPro> merchantPros = tenantProMapper.selectByMap(map);
        if (merchantPros.size() <= 0) {
            return ReturnBody.error(ResultCodeInfo.NO_AUTHORITY_OPERATE);
        }
        tenantProService.delete(id);
        return ReturnBody.success();
    }

    @PostMapping(value = "updateProStatus")
    @ApiOperation("修改商家商品状态 上架 下架")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "id", value = "商品ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "status", value = "1上架 2下架", required = true),
    })
    @ParameterVerify(notNull = {"id", "status"})
    public String updateProStatus(Long id, Integer status) {
        if (status != 1 && status != 2) {
            return ReturnBody.error("非法状态");
        }
        TenantInfo merchantInfo = (TenantInfo) request.getAttribute("info");
        Map<String, Object> map = new HashMap<>();
        //判断是否删除的为当前的商户的商品 以防非法操作
        map.put("tenant_id", merchantInfo.getId());
        map.put("id", id);
        List<TenantPro> merchantPros = tenantProMapper.selectByMap(map);
        if (merchantPros.isEmpty()) {
            return ReturnBody.error(ResultCodeInfo.NO_AUTHORITY_OPERATE);
        }
        TenantPro merchantPro = new TenantPro().setId(id).setStatus(status);
        return tenantProMapper.updateById(merchantPro) != 1 ? ReturnBody.error() : ReturnBody.success();
    }

    @Deprecated
    @PostMapping(value = "detail")
    @ApiOperation("获取商品详情")
    @ParameterVerify(notNull = "id")
    public String detailPro(Long id) {
        TenantPro merchantPro = tenantProService.getByIdForUpdate(id);
        if (merchantPro == null) {
            return ReturnBody.error("没有数据");
        }
        JSONObject result = new JSONObject();
        if (merchantPro.getIsSpec() == 1) {
            result = commService.commonGetProDetail(merchantPro.getId(), 1);
            result.put("pro", merchantPro);
        } else {
            result.put("pro", merchantPro);
        }
        result.put("afterSellIdent", tenantProIdentifiMapper.selectSelective(new TenantProIdentifi().setStatus(1)));
        return ReturnBody.success(result);
    }

    @ApiOperation("查询对应商品的所有sku组合")
    @ParameterVerify(notNull = "proId")
    @PostMapping(value = "selectProSku")
    public String selectProSku(Long proId, @RequestParam(name = "type", defaultValue = "1") Integer type) {
        if (type == 2 || type == 3) {
            type++;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pro_id", proId);
        map.put("type", type);
        List<TenantProSku> proSkus = tenantProSkuMapper.selectByMap(map);
        return ReturnBody.success(proSkus);
    }

    @ApiOperation("批量审核商品状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "goodsPros", value = "商品ID对象数组[{'id':'50','type':'2'}]",
                    required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "status", value = "1上架 2下架", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "remark", value = "下架原因", required = false),
    })
    @PostMapping(value = "batchUpdateProStatus")
    public String batchUpdateProStatus(String goodsPros, Integer status, String remark) {
        JSONObject object = tenantProService.batchUpdate(goodsPros, status, remark);
        Integer success = object.getInteger("success");
        Integer error = object.getInteger("error");
        Integer total = object.getInteger("total");
        return ReturnBody.success(String.format("批量更新完成，总操作数：%s个,成功数:%s个,失败%s个", total, success, error));
    }

    @ApiOperation("首页推荐")
    @PostMapping("indexTop")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", required = true),
    })
    public String indexTop(String ids) {
        List<Long> longs = JSONArray.parseArray(ids, Long.class);
        if (!CommUtil.checkNull(longs)) {
            return ReturnBody.error("至少选择一个商品");
        }
        List<TenantPro> tenantPros = tenantProService.listByIds(longs);
        tenantPros.forEach(item -> {
            if (item.getStatus() != 1) {
                throw new ServiceException(String.format("商品%s还在仓库中，无法添加至首页推荐", item.getName()));
            }
            item.setIndexRecommend(1);
        });
        tenantProService.indexTop(tenantPros);
        return ReturnBody.success();
    }

    @ApiOperation("取消首页推荐")
    @PostMapping("cancelIndexTop")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
    })
    public String cancelIndexTop(Long id) {
        TenantPro pro = tenantProService.getById(id);
        pro.setIndexRecommend(0);
        if (!tenantProService.updateById(pro)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }


}
