/**
 * @(#)IDeliveryService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月16日 huangzhiqian 创建版本
 */
package com.hd.agent.report.service;

import java.util.List;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.DeliveryCustomerOutReport;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface IDeliveryReportService {
	/**
	 * 根据条件查询数据  
     * REPORT_CUSTOMER_OUT 出库 REPORT_CUSTOMER_ENTER 入库
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月16日
	 */
	PageData getDeliveryCustomerData(PageMap pageMap,int type)throws Exception;
	/**
	 * 客户报表 Excel导出
	 * @param pageMap  
	 * @param type  REPORT_CUSTOMER_OUT 出库 REPORT_CUSTOMER_ENTER 入库
	 * @return
	 * @author huangzhiqian
	 * @date 2016年3月11日
	 */
	List<DeliveryCustomerOutReport> getDeliveryCustomerDataExcel(PageMap pageMap,int type)throws Exception;

	/**
	 * 代配送进销存报表
	 * @param pageMap
	 * @return
	 */
	public PageData showAllDeliveryData(PageMap pageMap)  throws Exception ;

}

