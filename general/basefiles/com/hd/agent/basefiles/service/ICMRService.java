/**
 * @(#)ICMRService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.MarketActivitySort;
import com.hd.agent.basefiles.model.SaleChance_Sort;
import com.hd.agent.basefiles.model.SaleMode;
import com.hd.agent.basefiles.model.SaleMode_Detail;
import com.hd.agent.basefiles.model.TaskSort;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface ICMRService {

	/*--------------------------------销售方式----------------------------------------*/
	
	/**
	 * 获取销售方式列表分页
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public PageData showSaleModeListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 显示销售方式详情信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public SaleMode showSaleModeInfo(String id)throws Exception;
	
	/**
	 * 根据销售方式编号获取销售方式细节性情列表
	 * @param salemodeid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public List showSaleModeDetailList(String salemodeid)throws Exception;
	
	/**
	 * 检验销售方式编号的唯一性
	 * true 已存在 false 不存在
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public boolean isRepeatSaleModeId(String id)throws Exception;
	
	/**
	 * 检验销售方式名称的唯一性
	 * true 已存在 false 不存在
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public boolean isRepeatSaleModeName(String name)throws Exception;
	
	/**
	 * 新增销售方式
	 * @param saleMode
	 * @param saleModeDetailList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public boolean addSaleMode(SaleMode saleMode,List<SaleMode_Detail> saleModeDetailList)throws Exception;
	
	/**
	 * 修改销售方式
	 * @param saleMode
	 * @param saleModeDetailList
	 * @param updateDetailList
	 * @param delDetailList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public boolean editSaleMode(SaleMode saleMode,List<SaleMode_Detail> saleModeDetailList,
			List<SaleMode_Detail> updateDetailList,List<SaleMode_Detail> delDetailList)throws Exception;
	
	/**
	 * 删除销售方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public boolean deleteSaleMode(String id)throws Exception;
	
	/**
	 * 启用销售方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public boolean enableSaleMode(Map paramMap)throws Exception;
	
	/**
	 * 禁用销售方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public boolean disableSaleMode(Map paramMap)throws Exception;
	
	/**
	 * 检验销售阶段编码唯一性
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 9, 2013
	 */
	public boolean isRepeatStageCode(Map map)throws Exception;
	
	/**
	 * 检验销售阶段名称唯一性
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 9, 2013
	 */
	public boolean isRepeatStageName(Map map)throws Exception;
	
	public Map addDRSaleMode(List<SaleMode> list)throws Exception;
	
	/*--------------------------------销售机会来源分类----------------------------------------*/
	
	/**
	 * 新增销售机会来源
	 * @param saleChance_Sort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public boolean addSaleChance(SaleChance_Sort saleChance_Sort)throws Exception;
	
	/**
	 * 修改销售机会来源
	 * @param saleChance_Sort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public Map editSaleChance(SaleChance_Sort saleChance_Sort)throws Exception;
	
	/**
	 * 获取销售机会来源列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public List getSaleChanceSortList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取销售机会来源信息详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public SaleChance_Sort getSaleChanceInfo(String id)throws Exception;
	
	/**
	 * 获取节点及节点下所有子节点
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public List getSalesAreaParentAllChildren(PageMap pageMap)throws Exception;
	
	/**
	 * 删除销售机会来源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public boolean deleteSaleChance(String id)throws Exception;
	
	/**
	 * 启用销售机会来源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public boolean enableSaleChance(SaleChance_Sort saleChance_Sort)throws Exception;
	
	/**
	 * 禁用销售机会来源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public boolean disableSaleChance(SaleChance_Sort saleChance_Sort)throws Exception;
	
	/**
	 * 名称是否被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2013
	 */
	public boolean saleChanceNameNOUsed(String thisname)throws Exception;
	
	/*-----------------------------------任务分类----------------------------------------------*/
	
	/**
	 * 新增任务分类
	 * @param taskSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public boolean addTaskSort(TaskSort taskSort)throws Exception;
	
	/**
	 * 修改任务分类
	 * @param taskSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public Map editTaskSort(TaskSort taskSort)throws Exception;
	
	/**
	 * 获取任务分类列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public List getTaskSortList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取节点及节点下所有子节点
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public List getTaskSortParentAllChildren(PageMap pageMap)throws Exception;
	
	/**
	 * 根据任务分类编码获取任务分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public TaskSort getTaskSortView(String id)throws Exception;
	
	/**
	 * 删除任务
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public boolean deleteTaskSort(String id)throws Exception;
	
	/**
	 * 启用任务
	 * @param taskSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public boolean enableTaskSort(TaskSort taskSort)throws Exception;
	
	/**
	 * 禁用任务
	 * @param taskSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public boolean disableTaskSort(TaskSort taskSort)throws Exception;
	
	/**
	 * 任务名称是否被使用,true 被使用，false 未被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public boolean isUsedTaskSortName(String name)throws Exception;
	
	/*--------------------------------市场活动分类----------------------------------------*/
	
	/**
	 * 获取市场活动分类列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 18, 2013
	 */
	public List getmarketActivitySortList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取市场活动分类详情
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 18, 2013
	 */
	public MarketActivitySort getmarketActivitySortDetail(String id)throws Exception;
	
	/**
	 * 获取节点及其全部子节点
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 18, 2013
	 */
	public List getmarketActivitySortParentAllChildren(PageMap pageMap)throws Exception;
	
	/**
	 * 删除市场活动分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 18, 2013
	 */
	public boolean deletemarketActivitySort(String id)throws Exception;
	
	/**
	 * 新增市场活动分类
	 * @param marketActivitySort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 18, 2013
	 */
	public boolean addmarketActivitySort(MarketActivitySort marketActivitySort)throws Exception;
	
	/**
	 * 修改市场活动分类
	 * @param marketActivitySort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 18, 2013
	 */
	public Map editmarketActivitySort(MarketActivitySort marketActivitySort)throws Exception;
	
	/**
	 * 启用市场活动分类
	 * @param marketActivitySort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 18, 2013
	 */
	public boolean enablemarketActivitySort(MarketActivitySort marketActivitySort)throws Exception;
	
	/**
	 * 禁用市场活动分类
	 * @param marketActivitySort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	public boolean disablemarketActivitySort(MarketActivitySort marketActivitySort)throws Exception;
	
	/**
	 * 市场活动市场名称是否被使用，true 被使用 ，false 未被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date May 20, 2013
	 */
	public boolean isUsedmarketActivitySortName(String name)throws Exception;
	
	/*-----------------接口调用----------------------------------------------*/
	/**
	 * 获取销售方式所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getSaleModeListData()throws Exception;
	
	/**
	 * 获取销售机会来源分了所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getSaleChanceSortListData()throws Exception;
	
	/**
	 * 获取任务分类所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getTaskSortListData()throws Exception;
	
	/**
	 * 获取市场活动分类所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getmarketActivitySortListData()throws Exception;
}

