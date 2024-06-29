package com.turing.code.common.utils.page.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.turing.code.common.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果
 *
 * @param <T> 分页数据类型
 * @author Turing
 */
public class PageResult<T> {

    private Long pageNo;

    private Long pageSize;

    private Long pages;

    private Long total;

    private List<T> list;

    public Long getPageNo() {
        return pageNo;
    }

    public PageResult setPageNo(Long pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public PageResult setPageSize(Long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getPages() {
        return pages;
    }

    public PageResult setPages(Long pages) {
        this.pages = pages;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public PageResult setTotal(Long total) {
        this.total = total;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public PageResult setList(List<T> list) {
        this.list = list;
        if (!CommUtil.checkNull(list)) {
            this.list = new ArrayList<>();
        }
        return this;
    }
}
