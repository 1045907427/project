/**
 * @(#)IPrintTempletSubjectService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.PrintTempletSubject;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 处理打印模板代码的业务逻辑的接口
 * @author zhanghonghui
 */
public interface IPrintTempletSubjectService {
	
	/**
	 * 显示打印模板代码列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public List showPrintTempletSubjectList() throws Exception;
	/**
	 * 显示打印模板代码分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public PageData showPrintTempletSubjectPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据代码获取打印模板代码详情
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public PrintTempletSubject showPrintTempletSubjectInfo(String code) throws Exception;
	
	/**
	 * 添加打印模板代码
	 * @param printTempletSubject
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public Map addPrintTempletSubject(PrintTempletSubject printTempletSubject) throws Exception;
	
	/**
	 * 修改打印模板代码
	 * @param printTempletSubject
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public Map editPrintTempletSubject(PrintTempletSubject printTempletSubject) throws Exception;
	
	/**
	 * 禁用打印模板代码
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public Map disablePrintTempletSubject(String code) throws Exception;
	
	/**
	 * 启用打印模板代码
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public Map enablePrintTempletSubject(String code) throws Exception;	
	
	/**
	 * 删除打印模板代码
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-27
	 */
	public Map deletePrintTempletSubject(String code)throws Exception;
	/**
	 * 批量删除打印模板代码，多个编号以, 分隔
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public Map deletePrintTempletSubjectMore(String codearrs) throws Exception;
	/**
	 * 判断代码是否已经被使用
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月3日
	 */
	public boolean isUsedPrintTempletSubjectCode(String code) throws Exception;

	/**
	 * 生成以code为关键字
	 * @return
	 * @throws Exception
	 */
	public String getPrintTempletSubjectListJsonCache() throws Exception;
	
}

