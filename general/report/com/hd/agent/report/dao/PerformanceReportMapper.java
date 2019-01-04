/**
 * @(#)PerformanceMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-8 zhanghonghui 创建版本
 */
package com.hd.agent.report.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.PerformanceKPIScore;
import com.hd.agent.report.model.PerformanceKPISummary;

/**
 * 
 * 考核报表
 * @author zhanghonghui
 */
public interface PerformanceReportMapper {
	//================================================================
	//部门考核
	//================================================================
	/**
	 * 获取部门考核汇总数据，列表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public List getPerformanceKPISummaryPageList(PageMap pageMap);
	/**
	 * 获取部门考核汇总数据，列表数据统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public int getPerformanceKPISummaryPageCount(PageMap pageMap);
	/**
	 * 获取部门考核汇总数据，列表数据统计，合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public PerformanceKPISummary getPerformanceKPISummaryPageSum(PageMap pageMap);
	
	/**
	 * 根据Map中参数，计算部门考核指标数<br/>
	 * Map中参数：<br/>
	 * businessdate:业务日期<br/>
	 * deptid:部门编码
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-25
	 */
	public int getPerformanceKPISummaryCountByMap(Map map);
	/**
	 * 添加部门考核汇总数据
	 * @param performanceKPISummary
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public int insertPerformanceKPISummary(PerformanceKPISummary performanceKPISummary);
	/**
	 * 更新部门考核汇总数据
	 * @param performanceKPISummary
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public int updatePerformanceKPISummary(PerformanceKPISummary performanceKPISummary);
	/**
	 * 更新部门考核汇总数据<br/>
	 * map中条件：performanceKPISummary 汇总数据<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	public int updatePerformanceKPISummaryByMap(Map map);
	/**
	 * 获取部门考核汇总数据
	 * @param performanceKPISummary
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public PerformanceKPISummary getPerformanceKPISummary(String id);
	/**
	 * 删除部门考核汇总数据
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public int deletePerformanceKPISummary(String id);
	/**
	 * 删除部门考核汇总数据
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-9
	 */
	public int deletePerformanceKPISummaryBy(Map map);
	/**
	 * 根据map中参数获取考核汇总数据<br/>
	 * map中参数：<br/>
	 * id: 编号<br/>
	 * year: 年份<br/>
	 * month: 月份<br/>
	 * 
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-4
	 */
	public PerformanceKPISummary getPerformanceKPISummaryBy(Map map);
	
	
	//=======================================================================
	//部门业绩考核
	//=======================================================================
	
	/**
	 * 根据Map中参数，计算部门业绩考核数<br/>
	 * Map中参数：<br/>
	 * businessdate:业务日期<br/>
	 * deptid:部门编码
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-25
	 */
	public int getPerformanceKPIScoreCountByMap(Map map);
	/**
	 * 添加部门业绩考核数据
	 * @param performanceKPISummary
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public int insertPerformanceKPIScore(PerformanceKPIScore performanceKPIScore);
	/**
	 * 更新部门业绩考核数据
	 * @param performanceKPIScore
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public int updatePerformanceKPIScore(PerformanceKPIScore performanceKPIScore);
	/**
	 * 更新部门业绩考核数据
	 * map中条件：performanceKPIScore 汇总数据<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-9
	 */
	public int updatePerformanceKPIScoreByMap(Map map);
	/**
	 * 获取部门业绩考核数据
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-4
	 */
	public PerformanceKPIScore getPerformanceKPIScore(String id);
	/**
	 * 获取部门业绩考核分页数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-6
	 */
	public List getPerformanceKPIScorePageList(PageMap pageMap);
	/**
	 * 获取部门业绩考核分页数据条数统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-6
	 */
	public int getPerformanceKPIScorePageCount(PageMap pageMap);
	/**
	 * 获取部门业绩考核合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-6
	 */
	public PerformanceKPIScore getPerformanceKPIScorePageSum(PageMap pageMap);
	/**
	 * 删除部门业绩考核
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-6
	 */
	public int deletePerformanceKPIScore(String id);
	/**
	 * 删除部门业绩考核
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-6
	 */
	public int deletePerformanceKPIScoreBy(Map map);
	
	//=======================================================================
	//报表
	//=======================================================================
	/**
	 * 获取部门考核指标报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public List getPerformanceKPISummaryReportList(PageMap pageMap);
	/**
	 * 获取部门考核指标报表，列表数据统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public int getPerformanceKPISummaryReportCount(PageMap pageMap);
	/**
	 * 获取部门考核指标报表，列表数据统计，合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public PerformanceKPISummary getPerformanceKPISummaryReportSum(PageMap pageMap);
	

	/**
	 * 获取部门业绩考核指标报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public List getPerformanceKPIScoreReportList(PageMap pageMap);
	/**
	 * 获取部门业绩考核指标报表，列表数据统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public int getPerformanceKPIScoreReportCount(PageMap pageMap);
	/**
	 * 获取部门业绩考核指标报表，列表数据统计，合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public PerformanceKPIScore getPerformanceKPIScoreReportSum(PageMap pageMap);
}

