/**
 * @(#)PrintTempletSubjectMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-11-30 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.agprint.model.PrintTempletSubject;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 打印模板代码相关数据库操作
 * @author zhanghonghui
 */
public interface PrintTempletSubjectMapper {
	/**
	 * 获取打印模板代码列表
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	public List<PrintTempletSubject> getPrintTempletSubjectList();
	
	/**
	 * 根据条件获取打印模板代码列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	public List<PrintTempletSubject> getPrintTempletSubjectPageList(PageMap pageMap);
	
	/**
	 * 根据条件获取打印模板代码数量
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	public int getPrintTempletSubjectCount(PageMap pageMap);
	/**
	 * 根据打印模板代码获取打印模板代码详情
	 * @param code
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月30日
	 */
	public PrintTempletSubject getPrintTempletSubject(@Param("code")String code);
	/**
	 * 根据打印模板代码获取启用打印模板代码详情
	 * @param code
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月9日
	 */
	public PrintTempletSubject getEnablePrintTempletSubject(@Param("code")String code);
	
	/**
	 * 添加打印模板代码
	 * @param printTempletSubject
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	public int addPrintTempletSubject(PrintTempletSubject printTempletSubject);
	
	/**
	 * 修改打印模板代码信息
	 * @param printTempletSubject
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	public int editPrintTempletSubject(PrintTempletSubject printTempletSubject);
	
	/**
	 * 禁用打印模板代码
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	public int disablePrintTempletSubject(PrintTempletSubject printTempletSubject);
	
	/**
	 * 启用打印模板代码
	 * @param printTempletSubject
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public int enablePrintTempletSubject(PrintTempletSubject printTempletSubject);
	
	/**
	 * 删除打印模板代码<br/>
	 * @param code
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月3日
	 */
	public int deletePrintTempletSubject(@Param("code")String code);
	/**
	 * 删除打印模板代码<br/>
	 * map中参数：<br/>
	 * notState : 查询不相等于传入的值<br/>
	 * state : 状态<br/>
	 * code : 代码<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-27
	 */
	public int deletePrintTempletSubjectBy(Map map);
	/**
	 * 根据相关条件统计模板代码条数<br/>
	 * map中参数：<br/>
	 * code : 代码<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月30日
	 */
	public int getPrintTempletSubjectCountBy(Map map);
}

