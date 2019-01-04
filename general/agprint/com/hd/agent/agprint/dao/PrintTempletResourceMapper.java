package com.hd.agent.agprint.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.agprint.model.PrintTempletResource;
import com.hd.agent.common.util.PageMap;

public interface PrintTempletResourceMapper {
	/**
	 * 根据条件获取打印模板资源列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	public List<PrintTempletResource> getPrintTempletResourcePageList(PageMap pageMap);
	
	/**
	 * 根据条件获取打印模板资源数量
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	public int getPrintTempletResourceCount(PageMap pageMap);
	/**
	 * 根据编号获取打印模板资源
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public PrintTempletResource getPrintTempletResource(String id);

	/**
	 * 根据编号获取启用打印模板资源
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public PrintTempletResource getEnablePrintTempletResource(String id);
	/**
	 * 添加打印模板资源
	 * @param printTempletResource
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public int insertPrintTempletResource(PrintTempletResource printTempletResource);
	/**
	 * 编辑打印模板资源
	 * @param printTempletResource
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public int updatePrintTempletResource(PrintTempletResource printTempletResource);
	/**
	 * 禁用打印模板资源
	 * @param printTempletResource
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public int disablePrintTempletResource(PrintTempletResource printTempletResource);
	/**
	 * 启用打印模板资源
	 * @param printTempletResource
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public int enablePrintTempletResource(PrintTempletResource printTempletResource);
	/**
	 * 删除未启用的模板资源
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public int deleteUnEnablePrintTempletResource(@Param("id")String id);
}
