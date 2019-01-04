/**
 * @(#)ExceptionReportMapper.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 22, 2013 zhengziyong 创建版本
 */
package com.hd.agent.report.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.GoodsOut;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface ExceptionReportMapper {

	public List getGoodsOutReportReal(PageMap pageMap);
	
	public int getGoodsOutReportRealCount(PageMap pageMap);

    public GoodsOut getGoodsOutReportRealSum(PageMap pageMap);

	public List getGoodsNotSalesReport(PageMap pageMap);
	
	public int getGoodsNotSalesReportCount(PageMap pageMap);
	
	public List getCustomerNotStockReport(PageMap pageMap);
	
	public int getCustomerNotStockReportCount(PageMap pageMap);

    public List getGoodsNotSalesInStorage(PageMap pageMap);
	
	/**
	 * 获取发货出库差额统计报表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public List showSalesOutBalanceReportData(PageMap pageMap);
	/**
	 * 获取发货出库差额统计报表数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public int showSalesOutBalanceReportDataCount(PageMap pageMap);
	/**
	 * 获取发货出库差额统计报表合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public Map showSalesOutBalanceReportSumData(PageMap pageMap);

    /**
     * 获取多日未验收单据报表数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-04-23
     */
    public List<Map> getBillUnAuditReportList(PageMap pageMap);

    /**
     * 获取多日未验收单据报表数量
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-04-23
     */
    public int getBillUnAuditReportCount(PageMap pageMap);

    /**
     * 销售价格异常报表数据
     * @param pageMap
     * @return
     */
    public List<Map> getSalePriceReportList(PageMap pageMap);

    /**
     * 销售价格异常报表数量
     * @param pageMap
     * @return
     */
    public int getSalePriceReportListCount(PageMap pageMap);

    /**
     * 销售价格异常报表合计
     * @param pageMap
     * @return
     */
    public List<Map> getSalePriceReportListSum(PageMap pageMap);

    /**
     * 获取退货价格异常报表数据
     * @param pageMap
     * @return
     */
    public List<Map> getRejectPriceReportList(PageMap pageMap);

    /**
     * 获取退货价格异常报表数据数量
     * @param pageMap
     * @return
     */
    public int getRejectPriceReportListCount(PageMap pageMap);

    /**
     * 获取退货价格异常报表数据合计
     * @param pageMap
     * @return
     */
    public Map getRejectPriceReportListSum(PageMap pageMap);
}

