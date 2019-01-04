/**
 * @(#)ISalesAreaReportService.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年4月15日 wanghongteng 创建版本
 */
package com.hd.agent.journalsheet.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author wanghongteng
 */

public interface ISalesAreaReportService {
	/**
	 * 显示部门日常费月统计报表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public PageData showDSalesAreaReportData(PageMap pageMap) throws Exception;
	
	
}

