/**
 * @(#)IPerformanceReportService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-8 zhanghonghui 创建版本
 */
package com.hd.agent.report.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.PerformanceKPIScore;
import com.hd.agent.report.model.PerformanceKPISubject;
import com.hd.agent.report.model.PerformanceKPISummary;

/**
 * 
 * 考核指标数据汇总报表
 * @author zhanghonghui
 */
public interface IPerformanceReportService {
	/**
	 * 添加部门考核指标数据汇总
	 * @param requestMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public Map<String, Object> addPerformanceKPISummary(Map<String, Object> requestMap) throws Exception;
	/**
	 * 更新部门考核指标数据汇总
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public Map<String, Object> updatePerformanceKPISummary(String id) throws Exception;
	/**
	 * 删除部门考核指标数据汇总
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public boolean deletePerformanceKPISummary(String id) throws Exception;
	/**
	 * 删除部门考核指标数据汇总
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public Map deletePerformanceKPISummaryMore(String idarrs) throws Exception;
	/**
	 * 批量审核部门指标数据汇总
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map<String,Object> auditPerformanceKPISummaryMore(String idarrs)throws Exception;
	/**
	 * 批量反审核部门指标数据汇总
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map<String,Object> oppauditPerformanceKPISummaryMore(String idarrs)throws Exception;
	/**
	 * 获取分布列表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public PageData getPerformanceKPISummaryPageData(PageMap pageMap) throws Exception;
	/**
	 * 获取部门考核指标数据汇总
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-2
	 */
	public PerformanceKPISummary showPerformanceKPISummary(String id) throws Exception;
	
	
	/**
	 * 部门考核指标
	 */
	/**================================================================*/
	/**
	 * 显示所有部门考核指标列表
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List showPerformanceKPISubjectList() throws Exception;
	/**
	 * 获取启用的部门考核指标列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-24
	 */
	public List showPerformanceKPISubjectEnableList() throws Exception;
	/**
	 * 显示部门考核指标分页数据
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public PageData showPerformanceKPISubjectPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 获取部门考核指标详情
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public PerformanceKPISubject showPerformanceKPISubjectByCode(String code) throws Exception;
	
	/**
	 * 获取部门考核指标详情
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public PerformanceKPISubject showPerformanceKPISubjectById(String id) throws Exception;
	
	/**
	 * 添加部门考核指标
	 * @param performanceKPISubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map addPerformanceKPISubject(PerformanceKPISubject performanceKPISubject) throws Exception;
	
	/**
	 * 修改部门考核指标
	 * @param performanceKPISubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map editPerformanceKPISubject(PerformanceKPISubject performanceKPISubject) throws Exception;
	
	/**
	 * 禁用考核指标
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public boolean disablePerformanceKPISubject(String id) throws Exception;
	
	/**
	 * 启用考核指标
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	public boolean enablePerformanceKPISubject(String id) throws Exception;
	
	
	/**
	 * 根据科目代码删除考核指标
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public boolean deletePerformanceKPISubjectByCode(String code)throws Exception;
	/**
	 * 根据编码删除考核指标
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public boolean deletePerformanceKPISubjectById(String id)throws Exception;
	/**
	 * 指删除考核指标
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-23
	 */
	public Map deletePerformanceKPISubjectMore(String idarrs)throws Exception;
	
	
	//==================================================================================
	//部门业绩考核数据
	//==================================================================================
	/**
	 * 添加部门业绩考核数据
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-3
	 */
	public Map addPerformanceKPIScore(Map<String, Object> requestMap) throws Exception;
	/**
	 * 更新部门业绩考核数据
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-3
	 */
	public Map updatePerformanceKPIScore(PerformanceKPIScore performanceKPIScore,Map<String, Object> requestMap) throws Exception;

	/**
	 * 获取部门业绩考核数据汇总
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-2
	 */
	public PerformanceKPIScore showPerformanceKPIScore(String id) throws Exception;
	/**
	 * 部门业绩考核数据分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-6
	 */
	public PageData getPerformanceKPIScorePageData(PageMap pageMap) throws Exception;
	/**
	 * 删除部门业绩考核数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-6
	 */
	public boolean deletePerformanceKPIScore(String id) throws Exception;
	/**
	 * 删除部门业绩考核数据
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-6
	 */
	public Map deletePerformanceKPIScoreMore(String idarrs) throws Exception;
	/**
	 * 审核部门业绩考核数据
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-9
	 */
	public Map<String,Object> auditPerformanceKPIScoreMore(String idarrs)throws Exception;
	/**
	 * 反审核部门业绩考核数据
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-9
	 */
	public Map<String,Object> oppauditPerformanceKPIScoreMore(String idarrs)throws Exception;
	
	//==================================================================================
	//报表
	//==================================================================================
	/**
	 * 获取部门考核指标数据汇总报表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public PageData getPerformanceKPISummaryReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取部门业绩考核报表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-28
	 */
	public PageData getPerformanceKPIScoreReportData(PageMap pageMap) throws Exception;
	
	
}

