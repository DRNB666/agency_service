package com.leepsmart.code.common.utils.page.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页对象
 */
@ApiModel(value = "分页对象", description = "分页对象")
public class PageInfo {

    @ApiModelProperty(value = "页码", name = "pageNo", example = "1", required = true)
    private Integer pageNo;
    @ApiModelProperty(value = "一页的数量", name = "pageSize", example = "10", required = true)
    private Integer pageSize;
    @ApiModelProperty(value = "开始时间", name = "startTime")
    private String startTime;
    @ApiModelProperty(value = "结束时间", name = "endTime")
    private String endTime;
    @ApiModelProperty(value = "关键字", name = "keyWord")
    private String keyWord;
    @ApiModelProperty(value = "id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "排序", name = "sortKey")
    private String sortKey;

    @ApiModelProperty(value = "排序规则", name = "pageSortRoles", hidden = true)
    private PageSortRoles pageSortRoles;

    @ApiModelProperty(value = "默认排序", name = "pageSortRoles", hidden = true)
    private Map<String, String> defaultSort;

    @ApiModelProperty(value = "是否进行count查询", name = "isCount", example = "true")
    private Boolean isCount;

    @ApiModelProperty(value = "时间范围筛选字段名称", hidden = true)
    private String timeScreen;

    public PageSortRoles getPageSortRoles() {
        return pageSortRoles;
    }


    public void setPageSortRoles(PageSortRoles pageSortRoles) {
        this.pageSortRoles = pageSortRoles;
    }

    public String getTimeScreen() {
        return timeScreen;
    }

    public void setTimeScreen(String timeScreen) {
        this.timeScreen = timeScreen;
    }

    public Map<String, String> getDefaultSort() {
        return defaultSort;
    }

    public void setDefaultSort(String field, SortWay sortWay) {
        if (defaultSort == null) {
            defaultSort = new HashMap<>();
        }
        defaultSort.put("field", field);
        defaultSort.put("way", sortWay.toString());
    }

    public Boolean getIsCount() {
        return isCount;
    }

    public void setIsCount(Boolean isCount) {
        this.isCount = isCount;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartTime() {
        if (startTime == null) {
            return null;
        }
        return startTime.length() == 0 ? null : startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        if (endTime == null) {
            return null;
        }
        return endTime.length() == 0 ? null : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
