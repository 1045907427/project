/**
 * @(#)LendReportMapper.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 库存报表dao
 * @author chenwei
 */
public interface LendReportMapper {
	/**
	 * 获取仓库出入库数据列表
	 * @param pageMap
	 * @return
	 * @author jqq
	 * @date Aug 14, 2013
	 */
	public List getLendInOutReportSumDataList(PageMap pageMap);
	/**
	 * 获取仓库出入库数据数量
	 * @param pageMap
	 * @return
	 * @author jqq
	 * @date Aug 14, 2013
	 */
	public int getLendInOutReportSumDataCount(PageMap pageMap);
	/**
	 * 获取仓库出入库合计数据
	 * @param pageMap
	 * @return
	 * @author jqq
	 * @date Aug 14, 2013
	 */
	public LendInOutReport getLendInOutReportSumData(PageMap pageMap);



}

