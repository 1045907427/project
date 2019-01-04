package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.Contacter;
import com.hd.agent.common.util.PageMap;

public interface ContacterMapper {
    
	public Contacter getContacterDetail(Map map);
	
	public int deleteContacter(String id);
	
	public int addContacter(Contacter contacter);
	
	public int updateContacter(Contacter contacter);
	
	public int updateContacterOpen(Contacter contacter);
	
	public int updateContacterClose(Contacter contacter);
	
	public int updateContacterDetault(String isdefault, String id);
	
	public List getContacterList(PageMap pageMap);
	
	public int getContacterCount(PageMap pageMap);
	
	public List getContacterListByCustomer(@Param("type")String type, String id);
	
	public int updateContacterNoDetaultBySupplier(String id);
	
	/**
	 * 验证联系人编码是否重复
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 5, 2013
	 */
	public int isRepeatContacterID(String id);
	
	public List returnContacterIdByName(String name);
	
	public List getContacterByName(String name);
	
}