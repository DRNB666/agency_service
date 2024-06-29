package com.turing.code.tenant.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author turing generator
 * @since 2024-06-25
 */
@ApiModel(value="TenantPro对象", description="商户-商品信息表")
public class TenantPro implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商户id")
    private Long tenantId;

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;

    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "展示图")
    private String banner;

    @ApiModelProperty(value = "实价")
    private BigDecimal price;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "浏览量")
    private Integer pv;

    @ApiModelProperty(value = "收藏量")
    private Integer favor;

    @ApiModelProperty(value = "销量")
    private Integer deal;

    @ApiModelProperty(value = "评价量")
    private Integer evaluation;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "-1无规格   1有规格")
    private Integer isSpec;

    @ApiModelProperty(value = "是否售后 1是  0否")
    private Integer isAfterSell;

    private String details;

    @ApiModelProperty(value = " 状态：1上架，2下架,  10已删除 ")
    private Integer status;

    private String remark;

    @ApiModelProperty(value = "配送方式 1：自提  2：外送      ")
    private String carriage;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "是否开启推广 1是  0否  -1服务过期")
    private Integer isInvite;

    @ApiModelProperty(value = "实时分数 越大越靠前")
    private Integer weightSort;

    @ApiModelProperty(value = "优选服务添加的分数")
    private Integer goodScore;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "售后标识")
    private String afterSellIdent;

    @ApiModelProperty(value = "0不推荐 1推荐")
    private Integer indexRecommend;

    @ApiModelProperty(value = "0普通商品 1会员商品")
    private Integer vipPro;

    @ApiModelProperty(value = "赠送金额(会员商品)")
    private BigDecimal giftAmount;

    @ApiModelProperty(value = "赠送分销等级(会员商品)	1:黄金推广员	2:合伙人	3:至尊合伙人")
    private Integer giftPromoteLeve;

    @ApiModelProperty(value = "赠送会员等级id(会员商品)")
    private Integer giftVipLevel;

    @ApiModelProperty(value = "普通推广员直推")
    private Double ptDirect;

    @ApiModelProperty(value = "普通推广员间推")
    private Double ptIndirect;

    @ApiModelProperty(value = "黄金推广员直推")
    private Double hjDirect;

    @ApiModelProperty(value = "黄金推广员间推")
    private Double hjIndirect;

    @ApiModelProperty(value = "合伙人直推")
    private Double hhrDirect;

    @ApiModelProperty(value = "合伙人间推")
    private Double hhrIndirect;

    @ApiModelProperty(value = "至尊合伙人直推")
    private Double zzDirect;

    @ApiModelProperty(value = "至尊合伙人间推")
    private Double zzIndirect;

    public Long getId() {
        return id;
    }

    public TenantPro setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public TenantPro setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public Long getShopId() {
        return shopId;
    }

    public TenantPro setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public TenantPro setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }
    public String getName() {
        return name;
    }

    public TenantPro setName(String name) {
        this.name = name;
        return this;
    }
    public String getCover() {
        return cover;
    }

    public TenantPro setCover(String cover) {
        this.cover = cover;
        return this;
    }
    public String getBanner() {
        return banner;
    }

    public TenantPro setBanner(String banner) {
        this.banner = banner;
        return this;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public TenantPro setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public TenantPro setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }
    public BigDecimal getFreight() {
        return freight;
    }

    public TenantPro setFreight(BigDecimal freight) {
        this.freight = freight;
        return this;
    }
    public String getVideo() {
        return video;
    }

    public TenantPro setVideo(String video) {
        this.video = video;
        return this;
    }
    public Integer getPv() {
        return pv;
    }

    public TenantPro setPv(Integer pv) {
        this.pv = pv;
        return this;
    }
    public Integer getFavor() {
        return favor;
    }

    public TenantPro setFavor(Integer favor) {
        this.favor = favor;
        return this;
    }
    public Integer getDeal() {
        return deal;
    }

    public TenantPro setDeal(Integer deal) {
        this.deal = deal;
        return this;
    }
    public Integer getEvaluation() {
        return evaluation;
    }

    public TenantPro setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
        return this;
    }
    public String getDescription() {
        return description;
    }

    public TenantPro setDescription(String description) {
        this.description = description;
        return this;
    }
    public Integer getIsSpec() {
        return isSpec;
    }

    public TenantPro setIsSpec(Integer isSpec) {
        this.isSpec = isSpec;
        return this;
    }
    public Integer getIsAfterSell() {
        return isAfterSell;
    }

    public TenantPro setIsAfterSell(Integer isAfterSell) {
        this.isAfterSell = isAfterSell;
        return this;
    }
    public String getDetails() {
        return details;
    }

    public TenantPro setDetails(String details) {
        this.details = details;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantPro setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantPro setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public String getCarriage() {
        return carriage;
    }

    public TenantPro setCarriage(String carriage) {
        this.carriage = carriage;
        return this;
    }
    public String getDistrict() {
        return district;
    }

    public TenantPro setDistrict(String district) {
        this.district = district;
        return this;
    }
    public String getCity() {
        return city;
    }

    public TenantPro setCity(String city) {
        this.city = city;
        return this;
    }
    public String getProvince() {
        return province;
    }

    public TenantPro setProvince(String province) {
        this.province = province;
        return this;
    }
    public Integer getIsInvite() {
        return isInvite;
    }

    public TenantPro setIsInvite(Integer isInvite) {
        this.isInvite = isInvite;
        return this;
    }
    public Integer getWeightSort() {
        return weightSort;
    }

    public TenantPro setWeightSort(Integer weightSort) {
        this.weightSort = weightSort;
        return this;
    }
    public Integer getGoodScore() {
        return goodScore;
    }

    public TenantPro setGoodScore(Integer goodScore) {
        this.goodScore = goodScore;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantPro setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantPro setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getStock() {
        return stock;
    }

    public TenantPro setStock(Integer stock) {
        this.stock = stock;
        return this;
    }
    public String getAfterSellIdent() {
        return afterSellIdent;
    }

    public TenantPro setAfterSellIdent(String afterSellIdent) {
        this.afterSellIdent = afterSellIdent;
        return this;
    }
    public Integer getIndexRecommend() {
        return indexRecommend;
    }

    public TenantPro setIndexRecommend(Integer indexRecommend) {
        this.indexRecommend = indexRecommend;
        return this;
    }
    public Integer getVipPro() {
        return vipPro;
    }

    public TenantPro setVipPro(Integer vipPro) {
        this.vipPro = vipPro;
        return this;
    }
    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public TenantPro setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
        return this;
    }
    public Integer getGiftPromoteLeve() {
        return giftPromoteLeve;
    }

    public TenantPro setGiftPromoteLeve(Integer giftPromoteLeve) {
        this.giftPromoteLeve = giftPromoteLeve;
        return this;
    }
    public Integer getGiftVipLevel() {
        return giftVipLevel;
    }

    public TenantPro setGiftVipLevel(Integer giftVipLevel) {
        this.giftVipLevel = giftVipLevel;
        return this;
    }
    public Double getPtDirect() {
        return ptDirect;
    }

    public TenantPro setPtDirect(Double ptDirect) {
        this.ptDirect = ptDirect;
        return this;
    }
    public Double getPtIndirect() {
        return ptIndirect;
    }

    public TenantPro setPtIndirect(Double ptIndirect) {
        this.ptIndirect = ptIndirect;
        return this;
    }
    public Double getHjDirect() {
        return hjDirect;
    }

    public TenantPro setHjDirect(Double hjDirect) {
        this.hjDirect = hjDirect;
        return this;
    }
    public Double getHjIndirect() {
        return hjIndirect;
    }

    public TenantPro setHjIndirect(Double hjIndirect) {
        this.hjIndirect = hjIndirect;
        return this;
    }
    public Double getHhrDirect() {
        return hhrDirect;
    }

    public TenantPro setHhrDirect(Double hhrDirect) {
        this.hhrDirect = hhrDirect;
        return this;
    }
    public Double getHhrIndirect() {
        return hhrIndirect;
    }

    public TenantPro setHhrIndirect(Double hhrIndirect) {
        this.hhrIndirect = hhrIndirect;
        return this;
    }
    public Double getZzDirect() {
        return zzDirect;
    }

    public TenantPro setZzDirect(Double zzDirect) {
        this.zzDirect = zzDirect;
        return this;
    }
    public Double getZzIndirect() {
        return zzIndirect;
    }

    public TenantPro setZzIndirect(Double zzIndirect) {
        this.zzIndirect = zzIndirect;
        return this;
    }

    public static final String ID = "tenant_pro.id";
    public static final String TENANT_ID = "tenant_pro.tenant_id";
    public static final String SHOP_ID = "tenant_pro.shop_id";
    public static final String CATEGORY_ID = "tenant_pro.category_id";
    public static final String NAME = "tenant_pro.name";
    public static final String COVER = "tenant_pro.cover";
    public static final String BANNER = "tenant_pro.banner";
    public static final String PRICE = "tenant_pro.price";
    public static final String ORIGINAL_PRICE = "tenant_pro.original_price";
    public static final String FREIGHT = "tenant_pro.freight";
    public static final String VIDEO = "tenant_pro.video";
    public static final String PV = "tenant_pro.pv";
    public static final String FAVOR = "tenant_pro.favor";
    public static final String DEAL = "tenant_pro.deal";
    public static final String EVALUATION = "tenant_pro.evaluation";
    public static final String DESCRIPTION = "tenant_pro.description";
    public static final String IS_SPEC = "tenant_pro.is_spec";
    public static final String IS_AFTER_SELL = "tenant_pro.is_after_sell";
    public static final String DETAILS = "tenant_pro.details";
    public static final String STATUS = "tenant_pro.status";
    public static final String REMARK = "tenant_pro.remark";
    public static final String CARRIAGE = "tenant_pro.carriage";
    public static final String DISTRICT = "tenant_pro.district";
    public static final String CITY = "tenant_pro.city";
    public static final String PROVINCE = "tenant_pro.province";
    public static final String IS_INVITE = "tenant_pro.is_invite";
    public static final String WEIGHT_SORT = "tenant_pro.weight_sort";
    public static final String GOOD_SCORE = "tenant_pro.good_score";
    public static final String UPDATE_TIME = "tenant_pro.update_time";
    public static final String CREATE_TIME = "tenant_pro.create_time";
    public static final String STOCK = "tenant_pro.stock";
    public static final String AFTER_SELL_IDENT = "tenant_pro.after_sell_ident";
    public static final String INDEX_RECOMMEND = "tenant_pro.index_recommend";
    public static final String VIP_PRO = "tenant_pro.vip_pro";
    public static final String GIFT_AMOUNT = "tenant_pro.gift_amount";
    public static final String GIFT_PROMOTE_LEVE = "tenant_pro.gift_promote_leve";
    public static final String GIFT_VIP_LEVEL = "tenant_pro.gift_vip_level";
    public static final String PT_DIRECT = "tenant_pro.pt_direct";
    public static final String PT_INDIRECT = "tenant_pro.pt_indirect";
    public static final String HJ_DIRECT = "tenant_pro.hj_direct";
    public static final String HJ_INDIRECT = "tenant_pro.hj_indirect";
    public static final String HHR_DIRECT = "tenant_pro.hhr_direct";
    public static final String HHR_INDIRECT = "tenant_pro.hhr_indirect";
    public static final String ZZ_DIRECT = "tenant_pro.zz_direct";
    public static final String ZZ_INDIRECT = "tenant_pro.zz_indirect";


    @Override
    public String toString() {
        return "TenantPro{" +
            "id=" + id +
            ", tenantId=" + tenantId +
            ", shopId=" + shopId +
            ", categoryId=" + categoryId +
            ", name=" + name +
            ", cover=" + cover +
            ", banner=" + banner +
            ", price=" + price +
            ", originalPrice=" + originalPrice +
            ", freight=" + freight +
            ", video=" + video +
            ", pv=" + pv +
            ", favor=" + favor +
            ", deal=" + deal +
            ", evaluation=" + evaluation +
            ", description=" + description +
            ", isSpec=" + isSpec +
            ", isAfterSell=" + isAfterSell +
            ", details=" + details +
            ", status=" + status +
            ", remark=" + remark +
            ", carriage=" + carriage +
            ", district=" + district +
            ", city=" + city +
            ", province=" + province +
            ", isInvite=" + isInvite +
            ", weightSort=" + weightSort +
            ", goodScore=" + goodScore +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            ", stock=" + stock +
            ", afterSellIdent=" + afterSellIdent +
            ", indexRecommend=" + indexRecommend +
            ", vipPro=" + vipPro +
            ", giftAmount=" + giftAmount +
            ", giftPromoteLeve=" + giftPromoteLeve +
            ", giftVipLevel=" + giftVipLevel +
            ", ptDirect=" + ptDirect +
            ", ptIndirect=" + ptIndirect +
            ", hjDirect=" + hjDirect +
            ", hjIndirect=" + hjIndirect +
            ", hhrDirect=" + hhrDirect +
            ", hhrIndirect=" + hhrIndirect +
            ", zzDirect=" + zzDirect +
            ", zzIndirect=" + zzIndirect +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantPro.ID
            ,TenantPro.TENANT_ID
            ,TenantPro.SHOP_ID
            ,TenantPro.CATEGORY_ID
            ,TenantPro.NAME
            ,TenantPro.COVER
            ,TenantPro.BANNER
            ,TenantPro.PRICE
            ,TenantPro.ORIGINAL_PRICE
            ,TenantPro.FREIGHT
            ,TenantPro.VIDEO
            ,TenantPro.PV
            ,TenantPro.FAVOR
            ,TenantPro.DEAL
            ,TenantPro.EVALUATION
            ,TenantPro.DESCRIPTION
            ,TenantPro.IS_SPEC
            ,TenantPro.IS_AFTER_SELL
            ,TenantPro.DETAILS
            ,TenantPro.STATUS
            ,TenantPro.REMARK
            ,TenantPro.CARRIAGE
            ,TenantPro.DISTRICT
            ,TenantPro.CITY
            ,TenantPro.PROVINCE
            ,TenantPro.IS_INVITE
            ,TenantPro.WEIGHT_SORT
            ,TenantPro.GOOD_SCORE
            ,TenantPro.UPDATE_TIME
            ,TenantPro.CREATE_TIME
            ,TenantPro.STOCK
            ,TenantPro.AFTER_SELL_IDENT
            ,TenantPro.INDEX_RECOMMEND
            ,TenantPro.VIP_PRO
            ,TenantPro.GIFT_AMOUNT
            ,TenantPro.GIFT_PROMOTE_LEVE
            ,TenantPro.GIFT_VIP_LEVEL
            ,TenantPro.PT_DIRECT
            ,TenantPro.PT_INDIRECT
            ,TenantPro.HJ_DIRECT
            ,TenantPro.HJ_INDIRECT
            ,TenantPro.HHR_DIRECT
            ,TenantPro.HHR_INDIRECT
            ,TenantPro.ZZ_DIRECT
            ,TenantPro.ZZ_INDIRECT
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}