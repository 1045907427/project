/**
 * @(#)IBugetAnalyzerService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月28日 huangzhiqian 创建版本
 */
package com.hd.agent.journalsheet.service;

import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.BugetAnalyzer;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface IBugetAnalyzerService {
	/**
	 * 新增
	 * @param bugetAnalyzer
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月29日
	 */
	Map addbuget(BugetAnalyzer bugetAnalyzer)throws Exception;
	/**
	 * 列表数据
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月29日
	 */
	PageData getbugetAnalyzerList(PageMap pageMap)throws Exception;
	/**
	 * 删除预算分析数据
	 * @param idArr
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	Map deletebugetAnalyzer(String[] idArr)throws Exception;
	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	BugetAnalyzer getBugetAnalyzerById(String id)throws Exception;
	/**
	 * 启用(禁用)预算分析
	 * @param idArr
	 * @param type "enable","disable"
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	Map updateState(String[] idArr, String type)throws Exception;
	/**
	 * 修改预算分析
	 * @param bugetAnalyzer
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	Map updatebugetAnalyzer(BugetAnalyzer bugetAnalyzer)throws Exception;
	/**
	 * 报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	PageData getbugetAnalyzerGroupData(PageMap pageMap)throws Exception;

}

