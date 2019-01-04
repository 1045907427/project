package com.hd.agent.activiti.dao;

import java.util.List;

import com.hd.agent.activiti.model.Delegate;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

public interface DelegateMapper {
	
	public Delegate getDelegate(String id);
	
	public int addDelegate(Delegate delegate);
	
	public int updateDelegate(Delegate delegate);
	
	public List getDelegateList(PageMap pageMap);
	
	public int getDelegateCount(PageMap pageMap);
	
	public int deleteDelegate(String id);
	
	public List getDelegateListByUserAndKey(String userId, String definitionkey);

    /**
     * 根据OA编号删除委托规则
     * @param oaid
     * @return
     * @author
     * @date Apr 23, 2016
     */
    public int deleteDelegateByOaid(@Param("oaid")String oaid);

    /**
     * 根据OA编号查询委托规则
     * @param oaid
     * @return
     * @author
     * @date Apr 23, 2016
     */
    public Delegate selectDelegateByOaid(@Param("oaid")String oaid);
}