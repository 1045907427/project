package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.SalesArea;
import com.hd.agent.common.util.PageMap;

public interface SalesAreaMapper {
    
	public SalesArea getSalesAreaDetail(Map map);
	
	public List getSalesAreaList(PageMap pageMap);
	
	public List getSalesAreaParentAllChildren(PageMap pageMap);
	
	public int deleteSalesArea(String id);
	
	public int addSalesArea(SalesArea area);
	
	public int updateSalesArea(SalesArea area);
	
	public int updateSalesAreaOpen(SalesArea area);
	
	public int updateSalesAreaClose(SalesArea area);
	
	//
	
	public int updateTreeName(Map map);
	
	public List getTreeInfo(Map map);
	/**
	 * 通过名称获取销售区域列表
	 * @param thisname
	 * @return
	 * @author chenwei 
	 * @date Oct 5, 2013
	 */
	public List returnSalesAreaIdByName(@Param("thisname")String thisname);
	
	/**
	 * 根据父级编码获取所有下属销售区域列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public List getSalesAreaChildList(String pid);
	
	/**
	 * 批量修改销售区域
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public int editSalesAreaBatch(List<SalesArea> list);
	
	/**
	 * 获取一级销售区域列表
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 4, 2014
	 */
	public List getFirstLevelSalesAreaList();
	
	/**
	 * 判断销售区域本级名称是否重复
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public int isRepeatThisName(String thisname);

    /**
     * 获取名称为空，上级编码不为空的销售区域
     * @return
     * @author panxiaoxiao
     * @date 2015-03-17
     */
    public List<SalesArea> getSalesAreaWithoutName();

    /**
     * 根据销售区域编码获取销售区域列表
     * @param salesareaid
     * @return
     */
    public List<SalesArea> getSalesAreaListById(@Param("salesareaid")String salesareaid);

	/**
	 * 获取全部的销售区域
	 *
	 * @return
	 * @author limin
	 * @date Jun 29, 2016
     */
	public List<Map> getSalesareaListForMecshop();
}