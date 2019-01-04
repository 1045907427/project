/**
 * @(#)PerformanceKPISubjectMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-8-25 zhanghonghui 创建版本
 */
package com.hd.agent.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.PerformanceKPISubject;

/**
 * 
 * 考核指标科目
 * @author zhanghonghui
 */
public interface PerformanceKPISubjectMapper {
	/**
	 * 获取考核指标科目列表
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List getPerformanceKPISubjectList();
	/**
	 * 获取考核指标科目启用列表，state='1'
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List showPerformanceKPISubjectEnableList();

	/**
	 * 根据map中参数，获取考核指标科目有效列表
	 * @param map
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List getPerformanceKPISubjectListByMap(Map map);
	
	
	/**
	 * 根据条件获取考核指标科目列表
	 * @param pageMap
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List getPerformanceKPISubjectPageList(PageMap pageMap);
	
	/**
	 * 根据条件获取考核指标科目分页总数量
	 * @param pageMap
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int getPerformanceKPISubjectCount(PageMap pageMap);
	
	/**
	 * 获取考核指标科目详情信息
	 * @param code
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public PerformanceKPISubject getPerformanceKPISubjectByCode(@Param("code")String code);
	
	/**
	 * 获取考核指标科目详情信息
	 * @param id
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public PerformanceKPISubject getPerformanceKPISubjectById(@Param("id")String id);
	
	/**
	 * 添加考核指标科目
	 * @param deptCostsSubject
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int addPerformanceKPISubject(PerformanceKPISubject deptCostsSubject);
	
	/**
	 * 修改考核指标科目信息
	 * @param deptCostsSubject
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int updatePerformanceKPISubject(PerformanceKPISubject deptCostsSubject);
	
	/**
	 * 禁用代码
	 * @param id
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int disablePerformanceKPISubject(@Param("id")String id);
	
	/**
	 * 启用代码
	 * @param id 
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	public int enablePerformanceKPISubject(@Param("id")String id);
	
	/**
	 * 删除代码
	 * @param paramMap
	 * @return
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public int deletePerformanceKPISubjectByCode(@Param("code")String code);
	/**
	 * 删除代码
	 * @param paramMap
	 * @return
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public int deletePerformanceKPISubjectById(@Param("id")String code);
	
	/**
	 * 根据条件获取考核指标科目数量
	 * @param map
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int getPerformanceKPISubjectCountByMap(Map map);
}

