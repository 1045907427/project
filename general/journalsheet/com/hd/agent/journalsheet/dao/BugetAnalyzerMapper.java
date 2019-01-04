package com.hd.agent.journalsheet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.BugetAnalyzer;


public interface BugetAnalyzerMapper {
	/**
	 * 录入预算分析
	 * @param bugetAnalyzer
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月29日
	 */
	int addbuget(BugetAnalyzer bugetAnalyzer);
	/**
	 * 获取列表数据
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月29日
	 */
	List<BugetAnalyzer> getbugetAnalyzerList(PageMap pageMap);
	/**
	 * 获取列表数量
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月29日
	 */
	int getbugetAnalyzerListCount(PageMap pageMap);
	/**
	 * 根据预算bugetid查询数据
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	BugetAnalyzer getAnalyzerByBugetId(String id);
	/**
	 * 根据bugetid删除数据
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	int deleteAnalyzerByBugetId(String id);
	/**
	 * 启用禁用
	 * @param state
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	int updateState(@Param("id")String id,@Param("state")int state);
	/**
	 * 修改更新
	 * @param bugetAnalyzer
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	int updateBugetAnalyzer(BugetAnalyzer bugetAnalyzer);
	/**
	 * 预算分析报表
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	List<BugetAnalyzer> getbugetAnalyzerGroupList(PageMap pageMap);
    
}