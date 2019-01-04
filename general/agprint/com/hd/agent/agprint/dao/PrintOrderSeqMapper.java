package com.hd.agent.agprint.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.agprint.model.PrintOrderSeq;
import com.hd.agent.common.util.PageMap;

/**
 * 打印内容排序策略DAO
 * @author master
 *
 */
public interface PrintOrderSeqMapper {
	/**
	 * 获取打印内容排序策略列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月6日
	 */
	public List<PrintOrderSeq> getPrintOrderSeqPageList(PageMap pageMap);
	/**
	 * 获取打印内容排序策略列表合计条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月6日
	 */
	public int getPrintOrderSeqPageCount(PageMap pageMap);
	/**
	 * 添加打印内容排序策略
	 * @param printOrderSeq
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public int insertPrintOrderSeq(PrintOrderSeq printOrderSeq);
	/**
	 * 更新打印内容排序策略
	 * @param printOrderSeq
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public int updatePrintOrderSeq(PrintOrderSeq printOrderSeq);
	/**
	 * 删除一条打印内容排序策略
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public int deletePrintOrderSeq(@Param("id")String id);
	/**
	 * 启用打印内容排序策略
	 * @param printOrderSeq
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月6日
	 */
	public int enablePrintOrderSeq(PrintOrderSeq printOrderSeq);
	/**
	 * 禁用打印内容排序策略
	 * @param printOrderSeq
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月6日
	 */
	public int disablePrintOrderSeq(PrintOrderSeq printOrderSeq);
	/**
	 * 根据编号获取打印内容排序策略
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月6日
	 */
	public PrintOrderSeq getPrintOrderSeq(@Param("id")String id);
	/**
	 * 根据编号获取启用打印内容排序策略
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年11月6日
	 */
	public PrintOrderSeq getEnablePrintOrderSeq(@Param("id")String id);
}
