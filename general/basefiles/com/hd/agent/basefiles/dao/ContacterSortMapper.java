package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.ContacterSort;
import com.hd.agent.common.util.PageMap;

public interface ContacterSortMapper {
    
	public List getContacterSortList(PageMap pageMap);
	
	public ContacterSort getContacterSortDetail(Map map);
	
	public List getContacterSortParentAllChildren(PageMap pageMap);
	
	public int deleteContacterSort(String id);
	
	public int addContacterSort(ContacterSort sort);
	
	public int updateContacterSort(ContacterSort sort);
	
	public int updateContacterSortOpen(ContacterSort sort);
	
	public int updateContacterSortClose(ContacterSort sort);
	
	/**
	 * 根据父级编码获取所有下属联系人分类列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 12, 2014
	 */
	public List getContacterSortChildList(String pid);
	
	/**
	 * 批量修改联系人分类
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date May 12, 2014
	 */
	public int editContacterSortBatch(List<ContacterSort> list);
	
	/**
	 * 判断联系人分类本级名称是否重复
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public int isRepeatThisName(String thisname);
}