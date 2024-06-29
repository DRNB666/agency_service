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
 * @since 2024-06-06
 */
@ApiModel(value="TenantProSku对象", description="商品规格表 sku")
public class TenantProSku implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "团购id")
    private Long groupId;

    @ApiModelProperty(value = "秒杀id")
    private Long spikeId;

    @ApiModelProperty(value = "商品Id")
    private Long proId;

    @ApiModelProperty(value = "规格json")
    private String spec;

    @ApiModelProperty(value = "1普通商品 2管理员商品 3秒杀 4团购")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态 商品规格状态  0:下架 1:上架 ")
    private Integer status;

    @ApiModelProperty(value = "库存数量 -1无限")
    private Integer stock;

    @ApiModelProperty(value = "规格小图")
    private String cover;

    @ApiModelProperty(value = "原价")
    private BigDecimal rioPrice;

    @ApiModelProperty(value = "现价")
    private BigDecimal curPrice;

    @ApiModelProperty(value = "老乡价")
    private BigDecimal firmPrice;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public TenantProSku setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getGroupId() {
        return groupId;
    }

    public TenantProSku setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }
    public Long getSpikeId() {
        return spikeId;
    }

    public TenantProSku setSpikeId(Long spikeId) {
        this.spikeId = spikeId;
        return this;
    }
    public Long getProId() {
        return proId;
    }

    public TenantProSku setProId(Long proId) {
        this.proId = proId;
        return this;
    }
    public String getSpec() {
        return spec;
    }

    public TenantProSku setSpec(String spec) {
        this.spec = spec;
        return this;
    }
    public Integer getType() {
        return type;
    }

    public TenantProSku setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantProSku setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantProSku setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getStock() {
        return stock;
    }

    public TenantProSku setStock(Integer stock) {
        this.stock = stock;
        return this;
    }
    public String getCover() {
        return cover;
    }

    public TenantProSku setCover(String cover) {
        this.cover = cover;
        return this;
    }
    public BigDecimal getRioPrice() {
        return rioPrice;
    }

    public TenantProSku setRioPrice(BigDecimal rioPrice) {
        this.rioPrice = rioPrice;
        return this;
    }
    public BigDecimal getCurPrice() {
        return curPrice;
    }

    public TenantProSku setCurPrice(BigDecimal curPrice) {
        this.curPrice = curPrice;
        return this;
    }
    public BigDecimal getFirmPrice() {
        return firmPrice;
    }

    public TenantProSku setFirmPrice(BigDecimal firmPrice) {
        this.firmPrice = firmPrice;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantProSku setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantProSku setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "tenant_pro_sku.id";
    public static final String GROUP_ID = "tenant_pro_sku.group_id";
    public static final String SPIKE_ID = "tenant_pro_sku.spike_id";
    public static final String PRO_ID = "tenant_pro_sku.pro_id";
    public static final String SPEC = "tenant_pro_sku.spec";
    public static final String TYPE = "tenant_pro_sku.type";
    public static final String REMARK = "tenant_pro_sku.remark";
    public static final String STATUS = "tenant_pro_sku.status";
    public static final String STOCK = "tenant_pro_sku.stock";
    public static final String COVER = "tenant_pro_sku.cover";
    public static final String RIO_PRICE = "tenant_pro_sku.rio_price";
    public static final String CUR_PRICE = "tenant_pro_sku.cur_price";
    public static final String FIRM_PRICE = "tenant_pro_sku.firm_price";
    public static final String CREATE_TIME = "tenant_pro_sku.create_time";
    public static final String UPDATE_TIME = "tenant_pro_sku.update_time";


    @Override
    public String toString() {
        return "TenantProSku{" +
            "id=" + id +
            ", groupId=" + groupId +
            ", spikeId=" + spikeId +
            ", proId=" + proId +
            ", spec=" + spec +
            ", type=" + type +
            ", remark=" + remark +
            ", status=" + status +
            ", stock=" + stock +
            ", cover=" + cover +
            ", rioPrice=" + rioPrice +
            ", curPrice=" + curPrice +
            ", firmPrice=" + firmPrice +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantProSku.ID
            ,TenantProSku.GROUP_ID
            ,TenantProSku.SPIKE_ID
            ,TenantProSku.PRO_ID
            ,TenantProSku.SPEC
            ,TenantProSku.TYPE
            ,TenantProSku.REMARK
            ,TenantProSku.STATUS
            ,TenantProSku.STOCK
            ,TenantProSku.COVER
            ,TenantProSku.RIO_PRICE
            ,TenantProSku.CUR_PRICE
            ,TenantProSku.FIRM_PRICE
            ,TenantProSku.CREATE_TIME
            ,TenantProSku.UPDATE_TIME
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}