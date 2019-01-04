/**
 * @(#)ILendReportService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * 
 * 借货还货报表service
 * @author chenwei
 */
public interface ILendReportService {
	/**
	 * 获取仓库借货还货统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public PageData showInOutReportData(PageMap pageMap) throws Exception;

}

