package com.hd.agent.agprint.service;

import java.util.Map;

import com.hd.agent.agprint.model.PrintOrderSeq;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

public interface IPrintOrderSeqService {
	/**
	 * 获取打印内容排序策略列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public PageData showPrintOrderSeqPageListData(PageMap pageMap) throws Exception;
	/**
	 * 添加打印内容排序策略
	 * @param printOrderSeq
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public Map addPrintOrderSeq(PrintOrderSeq printOrderSeq) throws Exception;
	/**
	 * 更新打印内容排序策略
	 * @param printOrderSeq
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public Map updatePrintOrderSeq(PrintOrderSeq printOrderSeq) throws Exception;
	/**
	 * 删除打印内容排序策略
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public Map deletePrintOrderSeq(String id) throws Exception;
	/**
	 * 批量删除打印内容排序策略，多个编号以, 分隔
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public Map deletePrintOrderSeqMore(String idarrs) throws Exception;
	
	/**
	 * 启用打印内容排序策略
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public Map enablePrintOrderSeq(String id) throws Exception;
	/**
	 * 禁用打印内容排序策略
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public Map disablePrintOrderSeq(String id) throws Exception;
	/**
	 * 根据编号获取打印内容排序策略
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月6日
	 */
	public PrintOrderSeq showPrintOrderSeqInfo(String id) throws Exception;
}
