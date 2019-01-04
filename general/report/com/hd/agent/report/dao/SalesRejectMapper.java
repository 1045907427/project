/**
 * @(#)SalesRejectMapper.java
 * @author limin
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 15, 2016 limin 创建版本
 */
package com.hd.agent.report.dao;


import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * 销售退货Mapper
 *
 * @author limin
 */
public interface SalesRejectMapper {

    /**
     * 查询销售退货统计报表分页数据
     *
     * @param pageMap
     * @return
     * @author limin
     * @date Aug 15, 2015
     */
    public List<Map> getSalesRejectReportList(PageMap pageMap);

    /**
     * 查询销售退货统计报表总记录数
     *
     * @param pageMap
     * @return
     * @author limin
     * @date Aug 15, 2015
     */
    public int getSalesRejectReportListCount(PageMap pageMap);

    /**
     * 查询销售退货统计报表合计数据
     *
     * @param pageMap
     * @return
     * @author limin
     * @date Aug 15, 2015
     */
    public Map getSalesRejectReportListSum(PageMap pageMap);
}
