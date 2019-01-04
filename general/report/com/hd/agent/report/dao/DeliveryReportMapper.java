/**
 * @(#)DeliveryReportMapper.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月16日 huangzhiqian 创建版本
 */
package com.hd.agent.report.dao;

import java.util.List;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.DeliveryCustomerOutReport;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface DeliveryReportMapper {
	/**
	 * 统计代配送客户数据
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月17日
	 */
	List<DeliveryCustomerOutReport> getDeliveryCustomerData(PageMap pageMap);

	/**
	 * 获取代配送进销存总数据
	 * @param pageMap
	 * @return
	 * @author luoqiang
	 * @date 2016/9/5
	 */
	public List showAllDeliveryData(PageMap pageMap);
	/**
	 * 获取代配送进销存总数居数量
	 * @param pageMap
	 * @return
	 * @author luoqiang
	 * @date 2016/9/5
	 */
	public int showAllDeliveryCount(PageMap pageMap);

	/**
	 * 代配送进销存总数据合计
	 * @param pageMap
	 * @return
	 * @author luoqiang
	 * @date  2016/9/5
	 */
	public List showAllDeliverySum(PageMap pageMap);
	
	

}

