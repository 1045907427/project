/**
 * @(#)ILogisticsReportService.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 4, 2014 yezhenyu 创建版本
 */
package com.hd.agent.report.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author yezhenyu
 */
public interface ILogisticsReportService {

	/**
	 * 获取物流奖金列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jul 5, 2014
	 */
	PageData getLogisticsReportList(PageMap pageMap) throws Exception;

	/**
	 * 获取物流奖金详细列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jul 5, 2014
	 */
	PageData getLogisticsReportDetailList(PageMap pageMap)throws Exception;

}

