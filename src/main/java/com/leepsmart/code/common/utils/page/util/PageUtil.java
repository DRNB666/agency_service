package com.leepsmart.code.common.utils.page.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leepsmart.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.page.ex.PageException;
import com.leepsmart.code.common.utils.page.function.PageListOperation;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.pojo.SortWay;

import java.util.Map;

/**
 * 分页工具
 *
 * @author leepsmart
 */
public class PageUtil<T> {

    private PageResult<T> pageResult;

    private PageInfo pageInfo;

    public PageUtil() {
    }

    public PageUtil(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageResult<T> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<T> pageResult) {
        this.pageResult = pageResult;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    /**
     * 开始分页
     *
     * @param pageListOperation 获取数据列表行为函数
     * @return 分页结果
     * @throws PageException 分页异常
     */
    public PageResult<T> startPage(PageListOperation<T> pageListOperation) {
        // 参数校验
        verifyParam(this.pageInfo);

        // 创建条件构造器
        QueryWrapper<T> queryWrapper = new CustomQueryWrapper<>();

        // 分页对象
        Page<T> page = new Page<>(this.pageInfo.getPageNo(), this.pageInfo.getPageSize());

        if (CommUtil.checkNull(this.pageInfo.getIsCount())) {
            page.setSearchCount(this.pageInfo.getIsCount());
        }

        // 时间筛选
        if (CommUtil.checkNull(this.pageInfo.getTimeScreen())) {
            if (CommUtil.checkNull(this.pageInfo.getStartTime())) {
                queryWrapper.ge(this.pageInfo.getTimeScreen(), this.pageInfo.getStartTime());
            }
            if (CommUtil.checkNull(this.pageInfo.getEndTime())) {
                queryWrapper.le(this.pageInfo.getTimeScreen(), this.pageInfo.getEndTime());
            }
        }

        // 排序
        if (CommUtil.checkNull(this.pageInfo.getSortKey())) {
            Map<String, String> map = this.pageInfo.getPageSortRoles().get(this.pageInfo.getSortKey());
            if (map.get("field") != null && map.get("way") != null && map.get("way").equals(SortWay.ASC.toString())) {
                queryWrapper.orderByAsc(map.get("field"));
            }
            if (map.get("field") != null && map.get("way") != null && map.get("way").equals(SortWay.DESC.toString())) {
                queryWrapper.orderByDesc(map.get("field"));
            }
        } else {
            // 如果没有传入排序, 则执行默认排序
            if (CommUtil.checkNull(this.pageInfo.getDefaultSort())) {
                if (this.pageInfo.getDefaultSort().get("way").equals(SortWay.ASC.toString())) {
                    queryWrapper.orderByAsc(this.pageInfo.getDefaultSort().get("field"));
                }
                if (this.pageInfo.getDefaultSort().get("way").equals(SortWay.DESC.toString())) {
                    queryWrapper.orderByDesc(this.pageInfo.getDefaultSort().get("field"));
                }
            }
        }

        // 执行分页列表操作
        pageListOperation.run(page, queryWrapper);

        // 封装返回结果
        PageResult<T> pageResult = new PageResult<T>()
                .setPageNo(page.getCurrent())
                .setPageSize(page.getSize())
                .setList(page.getRecords())
                .setPages(page.getPages())
                .setTotal(page.getTotal());
        setPageResult(pageResult);
        return pageResult;
    }

    /**
     * 校验参数
     *
     * @param page 分页对象
     * @throws PageException 分页异常
     */
    private void verifyParam(PageInfo page) {
        if (null == page) {
            throw new RuntimeException("class 'Paging' is null");
        }
        if (null == page.getPageNo()) {
            throw new RuntimeException("parameter 'pageNo' is null");
        }
        if (null == page.getPageSize()) {
            throw new RuntimeException("parameter 'pageSize' is null");
        }
    }

}
