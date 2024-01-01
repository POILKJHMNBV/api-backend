package com.zhenwu.api.common;

import static com.zhenwu.api.constant.CommonConstant.SORT_ORDER_ASC;

import lombok.Data;

/**
 * @author zhenwu
 * 分页请求基本类
 */
@Data
public class PageRequest {
    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = SORT_ORDER_ASC;
}
