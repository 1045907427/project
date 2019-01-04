package com.hd.agent.basefiles.dao;

import java.util.List;

import com.hd.agent.basefiles.model.ContacterAndSort;

public interface ContacterAndSortMapper {
    
	public ContacterAndSort getContacterAndSortDetail(String id);
	
	public List getContacterAndSortListByContacter(String id);
	
	public int deleteContacterAndSortByContacter(String id);
	
	public int addContacterAndSort(ContacterAndSort sort);
	
}