package com.qianxun.framework.web.page;

import com.qianxun.common.utils.ServletUtils;


public class TableSupport
{
    
    public static final String PAGE_NUM = "pageNum";

    
    public static final String PAGE_SIZE = "pageSize";

    
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    
    public static final String IS_ASC = "isAsc";

    
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
