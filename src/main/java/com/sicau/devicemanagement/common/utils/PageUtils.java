package com.sicau.devicemanagement.common.utils;

import com.github.pagehelper.PageHelper;
import com.sicau.devicemanagement.common.core.page.PageDomain;
import com.sicau.devicemanagement.common.core.page.TableSupport;
import com.sicau.devicemanagement.common.utils.file.DateUtils;
import com.sicau.devicemanagement.common.utils.sql.SqlUtil;


/**
 * 分页工具类
 * 
 * @author ruoyi
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }

    /**
     * 检查时间参数是否非法
     *
     * @param begin 开始
     * @param end   结束
     * @return boolean
     * @author sora
     * @date 2022/02/12
     */
    public static boolean timeIllegal(String begin, String end) {
        if (!StringUtils.isNotEmpty(begin) || !StringUtils.isNotEmpty(end)) {
            return true;
        }
        if (!DateUtils.dateStrIsValid(begin) || !DateUtils.dateStrIsValid(end)) {
            return true;
        }
        // 比较 begin 是否在 end 前面
        return !DateUtils.before(begin, end, DateUtils.YYYY_MM_DD);
    }

    /**
     * 检查分页参数是否非法
     *
     * @param size 大小
     * @param page 页面
     * @return boolean
     * @author sora
     * @date 2022/02/12
     */
    public static boolean pageCuttingIllegal(int size, int page) {
        if (size < 1 || page < 1) {
            return true;
        }
        return size > 3000;
    }
}
