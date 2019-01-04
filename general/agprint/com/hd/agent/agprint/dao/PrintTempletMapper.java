/**
 * @(#)PrintTempletDao.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-10-29 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface PrintTempletMapper {
	/**
	 * 添加打印模板信息
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-10-29
	 */
	public int addPrintTemplet(PrintTemplet printTemplet);
	/**
	 * 更新打印模板,更新的呢字段
	 * @param printTemplet
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public int updatePrintTemplet(PrintTemplet printTemplet);
	/**
	 * 删除打印模板信息
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-10-29
	 */
	public int deletePrintTemplet(@Param("id")String id);
	/**
	 * 获取打印模板信息
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-10-29
	 */
	public List<PrintTemplet> getPrintTempletPageList(PageMap pageMap);
	/**
	 * 获取打印模板信息条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-10-29
	 */
	public int getPrintTempletPageCount(PageMap pageMap);
	/**
	 * 获取打印模板信息
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-10-29
	 */
	public PrintTemplet showPrintTemplet(@Param("id")String id);

	/**
	 * 打印模板，设置默认或不默认
	 * @param printTemplet
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public int updatePrintTempletDefault(PrintTemplet printTemplet);

	/**
	 * 启用打印模板
	 * @param printTemplet
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public int enablePrintTemplet(PrintTemplet printTemplet);
	/**
	 * 禁用打印模板
	 * @param printTemplet
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public int disablePrintTemplet(PrintTemplet printTemplet);
	/**
	 * 获取启用状态打印模板信息<br/>
	 * 其中map的参数<br/>
	 * code:模板代码，必填<br/>
	 * deptid:关联部门<br/>
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-10-29
	 */
	public PrintTemplet showThePrintTempletBy(Map map);
	/** 
	 * map中参数：<br/>
	 * state : 状态，1启用，0禁用<br/>
	 * code  : 代码 <br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014年12月15日
	 */
	public List getPrintTempletListBy(Map map);
}

