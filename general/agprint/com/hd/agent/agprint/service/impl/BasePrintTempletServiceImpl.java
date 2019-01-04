package com.hd.agent.agprint.service.impl;

import com.hd.agent.agprint.dao.*;
import com.hd.agent.agprint.model.PrintOrderSeq;
import com.hd.agent.agprint.model.PrintPaperSize;
import com.hd.agent.agprint.model.PrintTempletResource;
import com.hd.agent.agprint.model.PrintTempletSubject;
import com.hd.agent.common.service.impl.BaseServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class BasePrintTempletServiceImpl extends BaseServiceImpl {
	/**
	 * 打印模板
	 */
	private PrintTempletMapper printTempletMapper;

	public PrintTempletMapper getPrintTempletMapper() {
		return printTempletMapper;
	}

	public void setPrintTempletMapper(PrintTempletMapper printTempletMapper) {
		this.printTempletMapper = printTempletMapper;
	}
	/**
	 * 打印模板代码
	 */
	private PrintTempletSubjectMapper printTempletSubjectMapper;
	
	public PrintTempletSubjectMapper getPrintTempletSubjectMapper() {
		return printTempletSubjectMapper;
	}

	public void setPrintTempletSubjectMapper(PrintTempletSubjectMapper printTempletSubjectMapper) {
		this.printTempletSubjectMapper = printTempletSubjectMapper;
	}
	/**
	 * 打印资源
	 */
	private PrintTempletResourceMapper printTempletResourceMapper;

	public PrintTempletResourceMapper getPrintTempletResourceMapper() {
		return printTempletResourceMapper;
	}

	public void setPrintTempletResourceMapper(PrintTempletResourceMapper printTempletResourceMapper) {
		this.printTempletResourceMapper = printTempletResourceMapper;
	}
	/**
	 * 打印内容排序
	 */
	private PrintOrderSeqMapper printOrderSeqMapper;
	

	public PrintOrderSeqMapper getPrintOrderSeqMapper() {
		return printOrderSeqMapper;
	}

	public void setPrintOrderSeqMapper(PrintOrderSeqMapper printOrderSeqMapper) {
		this.printOrderSeqMapper = printOrderSeqMapper;
	}

	/**
	 * 打印纸张大小
	 */
	private PrintPaperSizeMapper printPaperSizeMapper;

	public PrintPaperSizeMapper getPrintPaperSizeMapper() {
		return printPaperSizeMapper;
	}

	public void setPrintPaperSizeMapper(PrintPaperSizeMapper printPaperSizeMapper) {
		this.printPaperSizeMapper = printPaperSizeMapper;
	}

	/**
	 * 获取打印模板代码信息
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月10日
	 */
	public PrintTempletSubject getPrintTempletSubjectInfo(String code) throws Exception{
		return printTempletSubjectMapper.getPrintTempletSubject(code);
	}
	/**
	 * 获取启用的模板代码信息
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月10日
	 */
	public PrintTempletSubject getEnablePrintTempletSubjectInfo(String code) throws Exception{
		return printTempletSubjectMapper.getEnablePrintTempletSubject(code);
	}
	/**
	 * 获取打印模板资源信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月10日
	 */
	public PrintTempletResource getPrintTempletResourceInfo(String id) throws Exception{
		return printTempletResourceMapper.getPrintTempletResource(id);
	}
	/**
	 * 获取启用的打印模板资源信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月10日
	 */
	public PrintTempletResource getEnablePrintTempletResourceInfo(String id) throws Exception{
		return printTempletResourceMapper.getEnablePrintTempletResource(id);
	}
	
	/**
	 * 获取打印内容排序策略
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月10日
	 */
	public PrintOrderSeq getPrintOrderSeqInfo(String id) throws Exception{
		return printOrderSeqMapper.getPrintOrderSeq(id);
	}
	/**
	 * 获取启用的打印内容排序策略
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月10日
	 */
	public PrintOrderSeq getEnablePrintOrderSeqInfo(String id) throws Exception{
		return printOrderSeqMapper.getEnablePrintOrderSeq(id);
	}

	/**
	 * 获取打印纸张大小
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年12月10日
	 */
	public PrintPaperSize getPrintPaperSizeInfo(String id) throws Exception{
		return printPaperSizeMapper.getPrintPaperSize(id);
	}
	/**
	 * 获取启用的打印纸张大小
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年12月10日
	 */
	public PrintPaperSize getEnablePrintPaperSizeInfo(String id) throws Exception{
		return printPaperSizeMapper.getEnablePrintPaperSize(id);
	}

}
