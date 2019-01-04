package com.hd.agent.activiti.dao;

import java.util.List;

import com.hd.agent.activiti.model.DelegateLog;
import com.hd.agent.common.util.PageMap;

public interface DelegateLogMapper {
    
	public int addDelegateLog(DelegateLog log);
	
	public List getDelegateLogList(PageMap pageMap);
	
	public int getDelegateLogCount(PageMap pageMap);
	
}