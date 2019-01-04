package com.hd.agent.activiti.dao;

import java.util.List;

import com.hd.agent.activiti.model.WorkLog;
import com.hd.agent.common.util.PageMap;

public interface WorkLogMapper {

	public int addLog(WorkLog log);
	
	public List getLogList(PageMap pageMap);
	
	public int getLogCount(PageMap pageMap);
	
}