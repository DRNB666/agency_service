package com.turing.code.tenant.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.ImgUrlEnum;
import com.turing.code.common.utils.LogUtil;
import com.turing.code.common.utils.encrypt.Base64Util;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import com.turing.code.tenant.mapper.TenantProSkuMapper;
import com.turing.code.tenant.mapper.TenantProSpecMapper;
import com.turing.code.tenant.pojo.TenantInfo;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.tenant.mapper.TenantProMapper;
import com.turing.code.tenant.pojo.TenantProSku;
import com.turing.code.tenant.pojo.TenantProSpec;
import com.turing.code.tenant.service.TenantProService;
import com.turing.code.common.mybatisplus.methods.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author turing generator
 * @since 2024-06-01
 */
@Service
public class TenantProServiceImpl extends CommonServiceImpl<TenantProMapper, TenantPro> implements TenantProService {

    @Resource
    private HttpServletRequest request;
    @Resource
    private TenantProSkuMapper tenantProSkuMapper;
    @Resource
    private TenantProSpecMapper tenantProSpecMapper;
    @Resource
    private TenantProMapper tenantProMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProSkuSpec(TenantPro merchantPro, String skuList) {
        if (!updateById(merchantPro)) {
            throw new ServiceException("操作失败");
        }
        //判断商品是否选择规格
        if (merchantPro.getIsSpec() == 1) {
            this.updateSkuSpec(merchantPro.getId(), skuList, 1, merchantPro);
        } else {
            //如果没有选择规格那么查询之前是否有规格项 如果有则删除
            Map<String, Object> map = new HashMap<>();
            map.put("proId", merchantPro.getId());
            map.put("type", 1);
            map.put("status", 1);
            List<TenantProSpec> specs = tenantProSpecMapper.selectByMap(map);
            if (specs.size() > 0) {
                if (tenantProSpecMapper.deleteByProIdAndStatus(merchantPro.getId(), 1) != specs.size()) {
                    throw new ServiceException("操作失败");
                }
            }
            List<TenantProSku> proSkus = tenantProSkuMapper.selectByMap(map);
            if (proSkus.size() > 0) {
                if (tenantProSkuMapper.deleteByProIdAndType(merchantPro.getId(), 1) != proSkus.size()) {
                    throw new ServiceException("操作失败");
                }
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertProSkuSpec(TenantPro merchantPro, String skuList) {
        if (!save(merchantPro)) {
            throw new ServiceException("操作失败");
        }
        //添加中间表
/*        UserSearchPro searchPro = UserSearchPro.builder().proId(merchantPro.getId()).name(merchantPro.getName()).type(1).build();
        if (userSearchProService.insertSelective(searchPro) != 1) {
            throw new ServiceException("操作失败");
        }*/
        //判断商品是否选择规格
        if (merchantPro.getIsSpec() == 1) {
            //type 1普通商品 2管理员商品 3秒杀 4团购
            this.addSkuSpec(merchantPro.getId(), skuList, 1, merchantPro);
        }
    }

    //修改商品 规格组 和 sku  type:1正常 2管理员商品 3秒杀 4团购
    @Override
    public void updateSkuSpec(Long merchantProId, String skuList, Integer type, Object objectPro) {
        TimeInterval timer = DateUtil.timer();
        List<TenantProSku> proSkus = JSONArray.parseArray(skuList, TenantProSku.class);
        if (proSkus.isEmpty()) {
            throw new ServiceException("请添加规格");
        }

        Set<Map.Entry<String, String>> specSet = new HashSet<>();
        Map<String, Object> map = new HashMap<>();
        map.put("pro_id", merchantProId);
        map.put("type", type);
        //获取原有的 sku
        List<TenantProSku> merchantProSkus = tenantProSkuMapper.selectByMap(map);
        //规格字符串作为key的原sku表
        Map<String, TenantProSku> oriSkuMap = merchantProSkus.stream().collect(Collectors.toMap(TenantProSku::getSpec, v -> v));
        //现在的sku
        Map<String, TenantProSku> SkuMap = proSkus.stream().collect(Collectors.toMap(TenantProSku::getSpec, v -> v));
        //需要删除的sku
        Set<String> delSku = new HashSet<>();
        //遍历循环sku 做rud 操作
        int stocks = 0;//库存总和
        for (Map.Entry<String, TenantProSku> merchantProSkuEntry : SkuMap.entrySet()) {
            TenantProSku merchantProSku = merchantProSkuEntry.getValue();
            if (merchantProSku == null || merchantProSku.getStock() == null || merchantProSku.getCurPrice() == null || merchantProSku.getRioPrice() == null) {
                throw new ServiceException("请输入规格参数");
            }
            merchantProSku.setType(type);
            String skuKey = merchantProSkuEntry.getKey();
            stocks += merchantProSku.getStock();
            //查看图片是否为更新
            if (CommUtil.checkNull(merchantProSku.getCover())) {
                if (merchantProSku.getCover().contains("data:image")) {
                    merchantProSku.setCover(Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), merchantProSku.getCover(), false));
                }
            } else {
                throw new ServiceException("请上传规格为" + merchantProSku.getSpec() + "的封面图片");
            }
            //如果存在则修改这个
            if (oriSkuMap.containsKey(skuKey)) {
                TenantProSku oriMerchantProSku = oriSkuMap.get(skuKey);
                merchantProSku.setId(oriMerchantProSku.getId());
                merchantProSku.setProId(oriMerchantProSku.getProId());
                if (tenantProSkuMapper.updateById(merchantProSku) != 1) {
                    throw new ServiceException(ReturnBody.error(ResultCodeInfo.ERROR));
                }
            } else {
                //不包含则是添加
                merchantProSku.setProId(merchantProId);
                merchantProSku.setType(type);
                if (tenantProSkuMapper.insert(merchantProSku) != 1) {
                    throw new ServiceException(ReturnBody.error(ResultCodeInfo.ERROR));
                }
            }
            //把全部的sku 保存到set里面
            delSku.add(skuKey);
            //添加规格
            Map<String, String> pairs = JSON.parseObject(merchantProSku.getSpec(), Map.class);
            specSet.addAll(pairs.entrySet());
        }
        //遍历所有的以前的sku 和 现有的sku 如果不包含就是不存在 则为已删除
        oriSkuMap.forEach((skuKey, oriSku) -> {
            if (!delSku.contains(skuKey)) {
                if (tenantProSkuMapper.deleteById(oriSku.getId()) != 1) {
                    throw new ServiceException("操作失败");
                }
            }
        });
        proSkus.sort(Comparator.comparing(TenantProSku::getCurPrice));
        //更新商品价格，库存信息
        switch (type) {
            case 1:
                TenantPro merchantPro = (TenantPro) objectPro;
                merchantPro.setPrice(proSkus.get(0).getCurPrice());
                merchantPro.setOriginalPrice(proSkus.get(0).getRioPrice());
                merchantPro.setStock(stocks);
                if (!updateById(merchantPro)) {
                    throw new ServiceException("没有商品数据");
                }
                break;
            default:
                break;
        }

        //原规格表
        map.put("status", 1);
        List<TenantProSpec> oriSpecList = tenantProSpecMapper.selectByMap(map);
        //现规格
        Map<String, List<String>> specMap = new HashMap<>();
        //遍历 map
        specSet.stream().collect(Collectors.groupingBy(Map.Entry::getKey)).forEach((k, v) -> {
            //将map key 作为当前key     遍历获取map的并添加到当前map的value类型为list
            specMap.put(k, v.stream().map(Map.Entry::getValue).collect(Collectors.toList()));
        });
        //原父规格 <name,{spec}>    分组名称做key 对应实体类为value
        Map<String, TenantProSpec> oriSpecGroupMap = oriSpecList.stream().filter(v -> v.getParentName() == null).collect(Collectors.toMap(TenantProSpec::getName, v -> v));
        //原子规格
        Map<String, List<TenantProSpec>> oriSpecMap = oriSpecList.stream().filter(v -> v.getParentName() != null).collect(Collectors.groupingBy(TenantProSpec::getParentName));
        oriSpecGroupMap.forEach((key, merchantProGroupSpec) -> {
            if (!specMap.containsKey(key)) {
                //不包含 先删除子规格组
                for (TenantProSpec sonSpec : oriSpecMap.get(key)) {
                    if (tenantProSkuMapper.deleteById(sonSpec.getId()) != 1) {
                        throw new ServiceException("操作失败");
                    }
                }
                //在删除父规格
                if (tenantProSkuMapper.deleteById(merchantProGroupSpec.getId()) != 1) {
                    throw new ServiceException("操作失败");
                }
            } else {
                //如果存在父规则组 则判断子规则组是否存在
                for (TenantProSpec sonSpec : oriSpecMap.get(key)) {
                    //如果子规则不存在 就删除旧的子规则组
                    if (!specMap.get(key).contains(sonSpec.getName())) {
                        tenantProSkuMapper.deleteById(sonSpec.getId());

                    }
                }
            }
        });
        //添加新增规则组
        specMap.forEach((newSpec, listSpec) -> {
            //如果不存在 就添加新的父规则组
            if (!oriSpecGroupMap.containsKey(newSpec)) {
                TenantProSpec proSpec = new TenantProSpec().setName(newSpec).setType(type).setProId(merchantProId);
                if (tenantProSpecMapper.insert(proSpec) != 1) {
                    throw new ServiceException("操作失败");
                }
                //保存子选项
                for (String sonGroupName : listSpec) {
                    TenantProSpec spec =
                            new TenantProSpec().setName(sonGroupName).setType(type).setParentId(proSpec.getId()).setParentName(proSpec.getName())
                                    .setProId(merchantProId);
                    if (tenantProSpecMapper.insert(spec) != 1) {
                        throw new ServiceException("操作失败");
                    }
                }
            } else {
                //拿出父级规则组
                TenantProSpec proSpec = oriSpecGroupMap.get(newSpec);
                //取出所有子选项 保存不存在的子选项
                Set<String> oriSpecNameSet = oriSpecMap.get(newSpec).stream().map(TenantProSpec::getName).collect(Collectors.toSet());
                for (String sonGroupName : listSpec) {
                    //不包含才保存
                    if (!oriSpecNameSet.contains(sonGroupName)) {
                        TenantProSpec spec = new TenantProSpec().setName(sonGroupName).setType(type).setParentId(proSpec.getId()).setParentName(proSpec.getName())
                                .setProId(merchantProId);
                        if (tenantProSpecMapper.insert(spec) != 1) {
                            throw new ServiceException("操作失败");
                        }
                    }
                }
            }
        });
        LogUtil.debug(timer.interval() + "耗时秒");
    }

    @Override
    public void delete(Long id) {
        //删除商品 对应的 spec 对应的 sku
        Map<String, Object> map = new HashMap<>();
        map.put("pro_id", id);
        TenantPro merchantPro = tenantProMapper.selectById(id);


        List<TenantProSku> proSkus = tenantProSkuMapper.selectByMap(map);
        map.put("status", 1);
        List<TenantProSpec> specs = tenantProSpecMapper.selectByMap(map);
        if (tenantProSkuMapper.deleteByProIdAndType(id, 1) != proSkus.size()) {
            throw new ServiceException("操作失败");
        }
        if (tenantProSpecMapper.deleteByProIdAndStatus(id, 1) != specs.size()) {
            throw new ServiceException("操作失败");
        }

        //删除搜索表
/*        List<UserSearchPro> userSearchPros = userSearchProService.selectByMap(map);
        if (userSearchProService.deleteByProId(id) != userSearchPros.size()) {
            throw new ServiceException("操作失败");
        }*/
        //删除用户收藏表
        map.put("productId", id);
//        List<UserFavor> favors = favorService.selectByMap(map);
//        if (favorService.deleteByProId(id.intValue()) != favors.size()) {
//            throw new ServiceException("操作失败");
//        }
        //最后删除商品
        if (tenantProMapper.deleteById(id) != 1) {
            throw new ServiceException("操作失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject batchUpdate(String ids, Integer status, String remark) {
        JSONObject result = new JSONObject();
        int success = 0;
        int error = 0;
        List<JSONObject> array = JSONArray.parseArray(ids, JSONObject.class);
        for (JSONObject object : array) {
            Long id = object.getLong("id");
            TenantPro merchantPro = tenantProMapper.selectById(id);
            if (merchantPro == null) {
                error += 1;
                break;
            }
            if (CommUtil.checkNull(remark) && status == 2) {
                merchantPro.setRemark(remark);
            }
            merchantPro.setStatus(status);
            if (tenantProMapper.updateById(merchantPro) != 1) {
                error += 1;
                break;
            }
        }
        success = array.size() - error;
        result.put("success", success);
        result.put("error", error);
        result.put("total", array.size());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void indexTop(List<TenantPro> tenantPros) {
        tenantPros.forEach(item -> {
            if (!updateById(item)) {
                throw new ServiceException();
            }
        });
    }

    @Override
    public BigDecimal highPriceSku(Long id) {
        return tenantProMapper.highPriceSku(id);
    }


    //type 1正常 2管理员商品 3秒杀 4团购
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSkuSpec(Long merchantProId, String skuList, Integer type, Object objectPro) {
        TimeInterval timer = DateUtil.timer();
        List<TenantProSku> proSkus = JSONArray.parseArray(skuList, TenantProSku.class);

        //将第一个规格的价格设置到商品表中
        Set<Map.Entry<String, String>> specSet = new HashSet<>();
        int stocks = 0;//总库存
        for (TenantProSku skus : proSkus) {
            if (skus == null || skus.getStock() == null || skus.getCurPrice() == null || skus.getRioPrice() == null) {
                throw new ServiceException("请输入规格参数");
            }
            //设置商品ID
            stocks += skus.getStock();
            skus.setType(type);
            skus.setProId(merchantProId);
            if (!CommUtil.checkNull(skus.getCover())) {
                throw new ServiceException("请上传规格为" + skus.getSpec() + "的封面图片");
            }
            //上传图片
            if (skus.getCover().startsWith("data:image/")) {
                skus.setCover(Base64Util.generateImage(request, ImgUrlEnum.TENANT_PRO_INVITE.getPath(), skus.getCover(), false));
            }
            //规格
            Map<String, String> map = JSON.parseObject(skus.getSpec(), Map.class);
            specSet.addAll(map.entrySet());
        }
        proSkus.sort(Comparator.comparing(TenantProSku::getCurPrice));
        switch (type) {
            case 1:
                TenantPro merchantPro = (TenantPro) objectPro;
                merchantPro.setPrice(proSkus.get(0).getCurPrice());
                merchantPro.setOriginalPrice(proSkus.get(0).getRioPrice());
                merchantPro.setStock(stocks);
                if (!updateById(merchantPro)) {
                    throw new ServiceException("没有商品数据");
                }
                break;
            default:
                break;
        }
        //保存商品 sku
        if (tenantProSkuMapper.insertList(proSkus) != proSkus.size()) {
            throw new ServiceException("操作失败");
        }
        //构建规格树 格式 {12=[我发给, 发广告], 大小=[XL, L], 颜色=[黑色, 褐色]}
        Map<String, List<String>> specMap = new HashMap<>();
        specSet.stream().collect(Collectors.groupingBy(Map.Entry::getKey)).forEach((k, v) -> {
            specMap.put(k, v.stream().map(Map.Entry::getValue).collect(Collectors.toList()));
        });
        //保存 spec 规格组
        specMap.forEach((key, valueList) -> {
            TenantProSpec proSpec = new TenantProSpec().setName(key).setType(type).setProId(merchantProId);
            if (tenantProSpecMapper.insert(proSpec) != 1) {
                throw new ServiceException("操作失败");
            }
            //保存子选项
            for (String sonGroupName : valueList) {
                TenantProSpec spec = new TenantProSpec().setType(type).setName(sonGroupName).setParentId(proSpec.getId()).setParentName(proSpec.getName())
                        .setProId(merchantProId);
                if (tenantProSpecMapper.insert(spec) != 1) {
                    throw new ServiceException("操作失败");
                }
            }
            LogUtil.debug("用时：" + timer.interval());
        });
    }
}
