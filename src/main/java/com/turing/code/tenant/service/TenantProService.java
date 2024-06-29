package com.turing.code.tenant.service;

import com.alibaba.fastjson.JSONObject;
import com.turing.code.tenant.pojo.TenantPro;
import com.turing.code.common.mybatisplus.methods.CommonService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author turing generator
 * @since 2024-06-01
 */
public interface TenantProService extends CommonService<TenantPro> {

    void updateProSkuSpec(TenantPro merchantPro, String skuList);

    void insertProSkuSpec(TenantPro merchantPro, String skuList);
    /**
     * 添加规格
     * @param merchantProId
     * @param skuList
     * @param type 1正常 2管理员商品 3秒杀 4团购
     */
    void addSkuSpec(Long merchantProId, String skuList, Integer type, Object objectPro);
    /**
     * 修改商品 规格组 和 sku
     * @param merchantProId
     * @param skuList
     * @param type 1正常 2管理员商品 3秒杀 4团购
     */
    void updateSkuSpec(Long merchantProId, String skuList, Integer type, Object objectPro);

    void delete(Long id);

    /**
     * 批量上下架
     */
    JSONObject batchUpdate(String goodsPros, Integer status, String remark);

    void indexTop(List<TenantPro> tenantPros);

    /**
     * 查询商品规格最贵的价格
     */
    BigDecimal highPriceSku(Long id);

}
