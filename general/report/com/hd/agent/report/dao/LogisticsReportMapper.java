package com.hd.agent.report.dao;

import com.hd.agent.common.util.PageMap;

import java.util.List;


public interface LogisticsReportMapper {

	/**
	 * 获取物流报表列表
	 * @param pageMap
	 * @return
	 * @author yezhenyu 
	 * @date Jul 5, 2014
	 */
	List getLogisticsReportList(PageMap pageMap);

	/**
	 * 获取物流报表列表数量
	 * @param pageMap
	 * @return
	 * @author yezhenyu 
	 * @date Jul 5, 2014
	 */
	int getLogisticsReportListCount(PageMap pageMap);

	/**
	 * 获取物流报表详细列表
	 * @param pageMap
	 * @return
	 * @author yezhenyu 
	 * @date Jul 5, 2014
	 */
	List getLogisticsReportDetailList(PageMap pageMap);
	
}