package com.turing.code.tenant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.turing.code.tenant.mapper.TenantProSkuMapper;
import com.turing.code.tenant.mapper.TenantProSpecMapper;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.tenant.pojo.TenantProSku;
import com.turing.code.tenant.pojo.TenantProSpec;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommService {

    @Resource
    private TenantProSkuMapper merchantProSkuMapper;
    @Resource
    private TenantProSpecMapper merchantProSpecMapper;

    /**
     * 获取商品详情
     * @param proId 商品对象
     * @return
     */
    public JSONObject commonGetProDetail(Long proId, Integer type){
        JSONObject result = new JSONObject();
        Map<String,Object> map  = new HashMap<>();
        map.put("pro_id", proId);
        map.put("type", type);
        //获取对应对应规格组
        List<TenantProSku> proSkus = merchantProSkuMapper.selectByMap(map);
        map.put("status", type);
        List<TenantProSpec> proSpecs = merchantProSpecMapper.selectByMap(map);
        //取 parentName 为null的  父规格
        List<TenantProSpec> parentSpecs = proSpecs.stream().filter(spec -> spec.getParentName() == null).collect(Collectors.toList());
        //按照parentName 进行分组 {parentName:[{parentName:1},{parentName:2}]}
        Map<String, List<TenantProSpec>> sonSpecs = proSpecs.stream().filter(spec -> spec.getParentName() != null).collect(Collectors.groupingBy(TenantProSpec::getParentName));
        List<JSONObject> arraySpec = new ArrayList<>();
        //返回JSON 实体-》[数组]
        for (TenantProSpec parentSpec : parentSpecs) {
            JSONObject spec = new JSONObject();
            spec.put("parentName",parentSpec.getName());
            //遍历循环添加名字 转换为 【】数据
            spec.put("sonName",sonSpecs.get(parentSpec.getName()).stream().map(TenantProSpec::getName).toArray());
            arraySpec.add(spec);
        }
        result.put("arraySpec",arraySpec);
        result.put("proSkus",JSONObject.toJSONString(proSkus));
        return result;
    }

//    public JSONObject commonGetAdProDetail(AdPro adPro, Integer type) {
//        JSONObject result = new JSONObject();
//        Map<String,Object> map  = new HashMap<>();
//        map.put("proId",adPro.getId());
//        map.put("type",type);
//        //获取对应对应规格组
//        List<MerchantProSku> proSkus = merchantProSkuMapper.selectByMap(map);
//        map.put("status",type);
//        List<MerchantProSpec> proSpecs = merchantProSpecMapper.selectByMap(map);
//        //取 parentName 为null的  父规格
//        List<MerchantProSpec> parentSpecs = proSpecs.stream().filter(spec -> spec.getParentName() == null).collect(Collectors.toList());
//        //按照parentName 进行分组 {parentName:[{parentName:1},{parentName:2}]}
//        Map<String, List<MerchantProSpec>> sonSpecs = proSpecs.stream().filter(spec -> spec.getParentName() != null).collect(Collectors.groupingBy(MerchantProSpec::getParentName));
//        List<JSONObject> arraySpec = new ArrayList<>();
//        //返回JSON 实体-》[数组]
//        for (MerchantProSpec parentSpec : parentSpecs) {
//            JSONObject spec = new JSONObject();
//            spec.put("parentName",parentSpec.getName());
//            //遍历循环添加名字 转换为 【】数据
//            spec.put("sonName",sonSpecs.get(parentSpec.getName()).stream().map(MerchantProSpec::getName).toArray());
//            arraySpec.add(spec);
//        }
//        result.put("arraySpec",arraySpec);
//        result.put("proSkus",JSON.toJSONString(proSkus));
//        return result;
//    }
}