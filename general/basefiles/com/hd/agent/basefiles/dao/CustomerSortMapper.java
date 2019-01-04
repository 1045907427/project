package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.CustomerSort;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CustomerSortMapper {
    
	public CustomerSort getCustomerSortDetail(Map map);
	
	public List getCustomerSortList(PageMap pageMap);
	
	public List getCustomerSortParentAllChildren(PageMap pageMap);
	
	public int deleteCustomerSort(String id);
	
	public int addCustomerSort(CustomerSort customer);
	
	public int updateCustomerSort(CustomerSort customer);
	
	public int updateCustomerSortOpen(CustomerSort customer);
	
	public int updateCustomerSortClose(CustomerSort customer);
	
	public CustomerSort returnCustomerSortIdByName(String thisname);
	
	/**
	 * 根据父级编码获取所有下属客户分类列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public List getCustomerSortChildList(String pid);
	
	/**
	 * 批量修改客户分类
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public int editCustomerSortBatch(List<CustomerSort> list);
	
	/**
	 * 判断客户分类本级名称是否重复
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public int isRepeatThisName(String thisname);
    /**
     * 获取名称为空，上级编码不为空的客户分类
     * @return
     * @author panxiaoxiao
     * @date 2015-03-17
     */
    public List<CustomerSort> getCustomerSortWithoutName();

    /**
     * 获取第一级的客户分类列表
     * @return
     */
    List<CustomerSort> getFirstCustomerSort();
	/**
	 * 根据map中参数获取客户分类列表<br/>
	 * map中参数：<br/>
	 * idarrs: id字符串<br/>
	 * state: 1启用0禁用<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui
	 * @date 2016-08-11
	 */
	public List getCustomerSortListByMap(Map map);

	/**
	 * 获取更新时间在time时间之后的客户分类
	 *
	 * @param time      更新时间，获取更新时间在此之后的所有客户分类。格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 30, 2016
	 */
	public List<Map> getCustomersortListForMecshop(@Param("time") Date time);

}