package com.turing.code.tenant.pojo;

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
 * @since 2024-06-11
 */
@ApiModel(value="TenantBanner对象", description="租户-轮播图")
public class TenantBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private Date testTime;

    @ApiModelProperty(value = "版本号")
    @Version
    private Long version;

    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    @ApiModelProperty(value = "图片路径")
    private String url;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "关联页面")
    private String pages;

    public Integer getId() {
        return id;
    }

    public TenantBanner setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public TenantBanner setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public TenantBanner setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public TenantBanner setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public TenantBanner setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Date getTestTime() {
        return testTime;
    }

    public TenantBanner setTestTime(Date testTime) {
        this.testTime = testTime;
        return this;
    }
    public Long getVersion() {
        return version;
    }

    public TenantBanner setVersion(Long version) {
        this.version = version;
        return this;
    }
    public Integer getTenantId() {
        return tenantId;
    }

    public TenantBanner setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public String getUrl() {
        return url;
    }

    public TenantBanner setUrl(String url) {
        this.url = url;
        return this;
    }
    public Integer getSort() {
        return sort;
    }

    public TenantBanner setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public String getPages() {
        return pages;
    }

    public TenantBanner setPages(String pages) {
        this.pages = pages;
        return this;
    }

    public static final String ID = "tenant_banner.id";
    public static final String STATUS = "tenant_banner.status";
    public static final String REMARK = "tenant_banner.remark";
    public static final String CREATE_TIME = "tenant_banner.create_time";
    public static final String UPDATE_TIME = "tenant_banner.update_time";
    public static final String TEST_TIME = "tenant_banner.test_time";
    public static final String VERSION = "tenant_banner.version";
    public static final String TENANT_ID = "tenant_banner.tenant_id";
    public static final String URL = "tenant_banner.url";
    public static final String SORT = "tenant_banner.sort";
    public static final String PAGES = "tenant_banner.pages";


    @Override
    public String toString() {
        return "TenantBanner{" +
            "id=" + id +
            ", status=" + status +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", testTime=" + testTime +
            ", version=" + version +
            ", tenantId=" + tenantId +
            ", url=" + url +
            ", sort=" + sort +
            ", pages=" + pages +
        "}";
    }

    public final static String[] getFields(String ...noField){
        List<String> list = new ArrayList<>(Arrays.asList(
            TenantBanner.ID
            ,TenantBanner.STATUS
            ,TenantBanner.REMARK
            ,TenantBanner.CREATE_TIME
            ,TenantBanner.UPDATE_TIME
            ,TenantBanner.TEST_TIME
            ,TenantBanner.VERSION
            ,TenantBanner.TENANT_ID
            ,TenantBanner.URL
            ,TenantBanner.SORT
            ,TenantBanner.PAGES
        ));
        list.removeAll(new ArrayList<>(Arrays.asList(noField)));
        return list.toArray(new String[0]);
    }
}