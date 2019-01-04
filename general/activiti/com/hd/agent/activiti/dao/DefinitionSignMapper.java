package com.hd.agent.activiti.dao;

import com.hd.agent.activiti.model.DefinitionSign;

import java.util.Map;

public interface DefinitionSignMapper {

	public DefinitionSign getDefinitionSignByDefinitionid(String definitionid, String taskkey);
	
	public int addDefinitionSign(DefinitionSign sign);
	
	public int updateDefinitionSignByKey(DefinitionSign sign);
	
	public int getDefinitionSignCountByDefinitionid(String definitionid, String taskkey);

	/**
	 * 将旧版本的definitiontask设定clone到新版本
	 * @param param
	 * @return
	 * @author limin
	 * @date Jun 2, 2017
	 */
	public int doCloneOldVerson2NewVersion(Map param);
}