package com.hd.agent.activiti.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.activiti.model.AuthRule;

public interface AuthRuleMapper {
	
	/**
	 * 添加工作流规则
	 * @param rule
	 * @return
	 */
	public int addAuthRule(AuthRule rule);
	
	/**
	 * 根据type删除指定流程的对应权限
	 * @param definitionkey
	 * @param type
	 * @return
	 */
	public int deleteAuthRuleByType(@Param("definitionkey")String definitionkey, @Param("type")String type);
	
	/**
	 * 删除指定流程的对应节点权限
	 * @param definitionkey
	 * @param taskkey
	 * @param type
	 * @return
	 */
	public int deleteAuthRule(@Param("definitionkey")String definitionkey, @Param("taskkey")String taskkey, @Param("type")String type);
	
	/**
	 * 根据type获取指定流程的对应权限配置规则
	 * @param definitionkey
	 * @param type
	 * @return
	 */
	public List<String> getRuleByType(@Param("definitionkey")String definitionkey, @Param("type")String type);
	
	/**
	 * 根据type获取指定流程的对应权限配置规则
	 * @param definitionkey
	 * @param taskkey
	 * @param type
	 * @return
	 */
	public List<String> getRuleByUserTaskType(@Param("definitionkey")String definitionkey, @Param("taskkey")String taskkey, @Param("type")String type);
	
	/**
	 * 根据type获取指定流程的对应权限配置详情
	 * @param definitionkey
	 * @param taskkey
	 * @param type
	 * @return
	 */
	public List<Map> getRuleDetail(@Param("definitionkey")String definitionkey, @Param("taskkey")String taskkey, @Param("type")String type, @Param("rule")String rule);
	
	/**
	 * 删除用户节点的人员设定（审批）
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-12-3
	 */
	public int deleteAuthRuleForUserTask(Map map);
	
	/**
	 * 删除用户节点的"是否指定人员"设定（审批）
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-12-3
	 */
	public int deleteAuthRuleForCanassign(Map map);
	
	/**
	 * 获取用户节点的人员规则设定（审批）
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-12-3
	 */
	public List<AuthRule> selectAuthRuleForUserTask(Map map);
	
	/**
	 * 获取用户节点的人员规则设定（包含节点人员设定、能否指定人员设定）
	 * @param map 其中canassign未设定时，取出用户节点人员设定，canassign!=null and canassign != ''时，获取的是用户节点的“能否指定人员”设定
	 * @return
	 * @author limin 
	 * @date 2014-12-4
	 */
	public List<AuthRule> selectAuthRule(Map map);
	
	/**
	 * 清空流程节点的人员设定规则（新建工作、审批权限）
	 * @param definitionkey
	 * @param taskkey
	 * @param type
	 * @return
	 * @author limin 
	 * @date 2015-1-12
	 */
	public int clearAuthRule(@Param("definitionkey")String definitionkey, @Param("taskkey")String taskkey, @Param("type")String type);

    /**
     * 清空节点的人员设定
     * @param definitionkey
     * @param definitionid
     * @param taskkey
     * @param type
     * @return
     */
    public int deleteAuthRuleForDefinitionTask(@Param("definitionkey")String definitionkey, @Param("definitionid")String definitionid, @Param("taskkey")String taskkey, @Param("type")String type);

    /**
     * 将旧版本的人员设定clone到新版本
     * @param param
     * @return
     * @author limin
     * @date May 8, 2015
     */
    public int doCloneOldVerson2NewVersion(Map param);
}