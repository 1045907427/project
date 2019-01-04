/**
 * @(#)BuySupplierDetailSortMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-23 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.BuySupplierDetailsort;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface BuySupplierDetailSortMapper {
	/**
	 * 添加供应商对应分类
	 * @param detailsort
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int insertBuySupplierDetailSort(BuySupplierDetailsort detailsort);
	/**
	 * 修改供应商对应分类
	 * @param detailsort
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int updateBuySupplierDetailSort(BuySupplierDetailsort detailsort);
	/**
	 * 删除供应商对应分类
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int deleteBuySupplierDetailSort(@Param("id")String id);
	/**
	 * 根据供应商编号删除供应商对应分类
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int deleteBuySupplierDetailSortBySupplier(@Param("supplierid")String supplierid);

	/**
	 * 根据供应商编号显示供应商对应分类列表<br/>
	 * map中参数：<br/>
	 * supplierid : 供应商档案编号<br/>
	 * state : 供应商分类中的state,状态4新增3暂存2保存1启用0禁用<br/>
	 * statearrs : 供应商分类中的state数组列表字符串，格式：1,2,3<br/>
	 * orderby : 查询排序<br/>
	 * ordersort : 排序方式：desc降序，asc升序<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public List showBuySupplierDetailSortListBy(Map map);
	
	public BuySupplierDetailsort getBuySupplierDetailsortInfo(String id);
}

