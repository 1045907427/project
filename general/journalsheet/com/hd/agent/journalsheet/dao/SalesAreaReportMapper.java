/**
 * @(#)SalesAreaReportMapper.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年4月15日 wanghongteng 创建版本
 */
package com.hd.agent.journalsheet.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author wanghongteng
 */

public interface SalesAreaReportMapper {
	/**
	 * 销售区域明细列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng  
	 * @date 2016-4-15
	 */
	public List getSalesAreaDataList(PageMap pageMap);
}

