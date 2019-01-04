/**
 * @(#)IExceptionReportService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 22, 2013 zhengziyong 创建版本
 */
package com.hd.agent.report.service;


import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IExceptionReportService {

	public PageData getGoodsOutReport(PageMap pageMap) throws Exception;
	
	public PageData getGoodsNotSalesReport(PageMap pageMap) throws Exception;

    public List getGoodsNotSalesInStorage(PageMap pageMap) throws Exception ;
	
	public PageData getCustomerNotStockReport(PageMap pageMap) throws Exception;
	
	/**
	 * 获取销售发货差额统计报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public PageData showSalesOutBalanceReportData(PageMap pageMap) throws Exception;

    /**
     * 获取多日未验收单据报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-23
     */
    public PageData getBillUnAuditReportList(PageMap pageMap)throws Exception;


    /**
     * 获取销售价格异常报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-07
     */
    public PageData getSalePriceReportList(PageMap pageMap)throws Exception;

    /**
     * 获取退货价格异常报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-08
     */
    public PageData getRejectPriceReportList(PageMap pageMap)throws Exception;
}

