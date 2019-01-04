package com.hd.agent.salestarget.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by master on 2016/8/7.
 */
public interface ISalesTargetReportService {

    /**
     * 销售目标跟踪报表
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData showSalesTargetTraceReportData(PageMap pageMap) throws Exception;
    /**
     * 销售毛利目标跟踪报表
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData showSalesTargetGrossTraceReportData(PageMap pageMap) throws Exception;

    /**
     * 按品牌月度目标分解
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData showSalesTargetBrandMonthAnalyzeReportData(PageMap pageMap) throws  Exception;
    /**
     * 按渠道月度目标分解
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData showSalesTargetCustomerSortMonthAnalyzeReportData(PageMap pageMap) throws  Exception;
    /**
     * 计算月度分解时间进度
     * @param iMonth
     * @param businessdate
     * @return
     * @throws Exception
     */
    public BigDecimal getCalcMonthAnalyzeTimeSchedule(Integer iMonth,String businessdate) throws Exception;
    /**
     * 计算月时间进度，年进度
     * @param businessdate
     * @return
     * @throws Exception
     */
    public Map getCalcYearAndMonthTimeSchedule(String businessdate) throws Exception;

}
