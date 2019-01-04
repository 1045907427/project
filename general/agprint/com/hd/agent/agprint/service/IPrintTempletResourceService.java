package com.hd.agent.agprint.service;

import java.util.Map;

import com.hd.agent.agprint.model.PrintTempletResource;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

public interface IPrintTempletResourceService {
	/**
	 * 模板资源列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public PageData showPrintTempletResourcePageList(PageMap pageMap) throws Exception;
	/**
	 * 根据编号只单单获取打印模板资源信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年12月1日
	 */
	public PrintTempletResource showPurePrintTempletResource(String id) throws Exception;
	/**
	 * 根据编号获取打印模板资源，并查询出相关模板分类信息，打印纸张信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public PrintTempletResource showPrintTempletResourceInfo(String id) throws Exception;
	/**
	 * 添加打印模板资源
	 * @param printTempletResource
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map addPrintTempletResource(PrintTempletResource printTempletResource) throws Exception;
	/**
	 * 编辑打印模板资源
	 * @param printTempletResource
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map editPrintTempletResource(PrintTempletResource printTempletResource) throws Exception;
	/**
	 * 禁用打印模板资源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map disablePrintTempletResource(String id) throws Exception;
	/**
	 * 启用打印模板资源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map enablePrintTempletResource(String id) throws Exception;
	/**
	 * 删除打印模板资源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map deletePrintTempletResource(String id)throws Exception;
	/**
	 * 批量删除打印模板资源
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月3日
	 */
	public Map deletePrintTempletResourceMore(String idarrs)throws Exception;
}
