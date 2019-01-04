package com.hd.agent.activiti.dao;

import java.util.List;

import com.hd.agent.activiti.model.DefinitionType;

public interface DefinitionTypeMapper {

	public int addDefinitionType(DefinitionType type);
	
	public List getDefinitionTypeList();
	
	public DefinitionType getDefinitionType(String id);
	
	public DefinitionType getDefinitionTypeByKey(String key);
	
	public int getDefinitionTypeCountByKey(String key);
	
	public int updateDefinitionType(DefinitionType type);
	
	public int deleteDefinitionType(String id);
	
}