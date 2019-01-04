/**
 * @(#)ICMRMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.MarketActivitySort;
import com.hd.agent.basefiles.model.SaleChance_Sort;
import com.hd.agent.basefiles.model.SaleMode;
import com.hd.agent.basefiles.model.TaskSort;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface CMRMapper {

	/*------------------------------------销售方式--------------------------------------------------*/
	/**
	 * 获取销售方式列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public List getSaleModeListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 获取销售方式列表数量
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public int getSaleModeListCount(PageMap pageMap)throws Exception;
	
	/**
	 * 获取销售方式详情信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public SaleMode getSaleModeInfo(@Param("id")String id)throws Exception;
	
	/**
	 * 根据销售方式编号获取销售方式细节性情列表
	 * @param salemodeid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public List getSaleModeDetailList(@Param("salemodeid")String salemodeid)throws Exception;
	
	/**
	 * 检验销售方式编号唯一性
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public int isRepeatSaleModeId(@Param("id")String id)throws Exception;
	
	/**
	 * 检验销售方式姓名唯一性
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public int isRepeatSaleModeName(@Param("name")String name)throws Exception;
	
	/**
	 * 新增销售方式
	 * @param saleMode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public int addSaleMode(SaleMode saleMode)throws Exception;
	
	/**
	 * 批量新增销售方式细节详情
	 * @param saleModeDetailMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public int addSaleModeDetails(List saleModeDetailList)throws Exception;
	
	/**
	 * 修改销售方式
	 * @param saleMode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public int editSaleMode(SaleMode saleMode)throws Exception;
	
	/**
	 * 批量修改销售方式细节详情
	 * @param saleModeDetailList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public int editSaleModeDetails(List updateDetailList)throws Exception;
	
	/**
	 * 删除销售方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public int deleteSaleMode(@Param("id")String id)throws Exception;
	
	/**
	 * 批量删除销售方式细节详情
	 * @param saleModeDetailList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public int deleteSaleModeDetails(List delDetailList)throws Exception;
	
	/**
	 * 启用销售方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public int enableSaleMode(Map paramMap)throws Exception;
	
	/**
	 * 禁用销售方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	public int disableSaleMode(Map paramMap)throws Exception;
	
	/**
	 * 检验销售阶段编码唯一性
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 9, 2013
	 */
	public int isRepeatStageCode(Map map)throws Exception;
	
	/**
	 * 检验销售阶段名称唯一性
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 9, 2013
	 */
	public int isRepeatStageName(Map map)throws Exception;
	
	/**
	 * 获取销售方式所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getSaleModeListData();
	
	/*---------------------------------------------销售机会来源分类---------------------------------------------------------*/
	
	/**
	 * 获取销售机会来源详情信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public SaleChance_Sort getSaleChanceInfo(Map map);
	
	/**
	 * 获取销售机会来源列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public List getSaleChanceSortList(PageMap pageMap);
	
	/**
	 * 获取父级销售机会及其下级所有销售机会
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public List getSalesAreaParentAllChildren(PageMap pageMap);
	
	/**
	 * 删除销售机会来源
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public int deleteSaleChance(String id);
	
	/**
	 * 新增销售机会来源
	 * @param saleChance_Sort
	 * @return
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public int addSaleChance(SaleChance_Sort saleChance_Sort);
	
	/**
	 * 修改销售机会来源
	 * @param saleChance_Sort
	 * @return
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public int editSaleChance(SaleChance_Sort saleChance_Sort);
	
	/**
	 * 启用销售机会来源
	 * @param saleChance_Sort
	 * @return
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public int enableSaleChance(SaleChance_Sort saleChance_Sort);
	
	/**
	 * 禁用销售机会来源
	 * @param saleChance_Sort
	 * @return
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public int disableSaleChance(SaleChance_Sort saleChance_Sort);
	
	/**
	 * 名称是否被使用
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date May 14, 2013
	 */
	public int saleChanceNameNOUsed(String name);
	
	/**
	 * 判断是否为末级标志
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 9, 2014
	 */
	public String isLeafSaleChance(String id);
	
	/**
	 * 获取状态不为暂存的销售机会来源列表
	 * @return
	 * @author panxiaoxiao 
	 * @date May 9, 2014
	 */
	public List getSaleChanceByStateList();
	
	/**
	 * 获取销售机会来源分了所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getSaleChanceSortListData();
	
	/**
	 * 获取包含其父级编码的所有所属销售机会来源列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 9, 2014
	 */
	public List getSaleChanceSortChildList(String pid);
	
	/**
	 * 批量修改销售来源分类
	 * @param childList
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public int editSaleChanceSortBatch(List<SaleChance_Sort> childList);
	
	/*------------------------------------------任务分类--------------------------------------------*/
	
	/**
	 * 获取任务分类列表
	 * @param map
	 * @return
	 * @author panxiaoxiao
	 * @date May 17, 2013
	 */
	public List getTaskSortList(PageMap pageMap);
	
	/**
	 * 获取节点及其所有子节点
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public List getTaskSortParentAllChildren(PageMap pageMap);
	
	/**
	 * 获取任务分类详情
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public TaskSort getTaskSortView(Map map);
	
	/**
	 * 删除任务分类
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public int deleteTaskSort(String id);
	
	/**
	 * 新增任务分类
	 * @param taskSort
	 * @return
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public int addTaskSort(TaskSort taskSort);
	
	/**
	 * 修改任务分类
	 * @param taskSort
	 * @return
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public int editTaskSort(TaskSort taskSort);
	
	/**
	 * 启用任务分类
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public int enableTaskSort(TaskSort taskSort);
	
	/**
	 * 禁用任务分类
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public int disableTaskSort(TaskSort taskSort);
	
	/**
	 * 任务名称是否被使用
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public int isUsedTaskSortName(String name);
	
	/**
	 * 任务分类是否为末及标志
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 9, 2014
	 */
	public String isLeafTaskSort(String id);
	
	/**
	 * 获取状态不为暂存的任务分类列表
	 * @return
	 * @author panxiaoxiao 
	 * @date May 9, 2014
	 */
	public List getTaskSortByStateList();
	
	/**
	 * 获取任务分类所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getTaskSortListData();
	
	/**
	 * 根据父级编码获取所有下属任务分类列表
	 * @return
	 * @author panxiaoxiao 
	 * @date May 9, 2014
	 */
	public List getTaskSortChildList(String pid);
	
	/**
	 * 批量修改
	 * @param childList
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public int editTaskSortBatch(List<TaskSort> childList);
	
	/*------------------------------------市场活动分类--------------------------------------------------*/
	
	public List getmarketActivitySortList(PageMap pageMap);
	
	public MarketActivitySort getmarketActivitySortDetail(Map map);
	
	public List getmarketActivitySortParentAllChildren(PageMap pageMap);
	
	public int deletemarketActivitySort(String id);
	
	public int addmarketActivitySort(MarketActivitySort marketActivitySort);
	
	public int editmarketActivitySort(MarketActivitySort marketActivitySort);
	
	public int enablemarketActivitySort(MarketActivitySort marketActivitySort);
	
	public int disablemarketActivitySort(MarketActivitySort marketActivitySort);
	
	public int isUsedmarketActivitySortName(String name);
	
	public String isLeafmarketActivitySort(String id);
	
	public List getmarketActivitySortByStateList();
	
	/**
	 * 获取市场活动分类所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getmarketActivitySortListData();
	
	/**
	 * 根据父级编码获取所有下属市场活动分类列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 9, 2014
	 */
	public List getMarketActivitySortChildList(String pid);
	
	/**
	 * 批量修改市场活动分类
	 * @param childList
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public int editMarketActivitySortBatch(List<MarketActivitySort> childList);
}

