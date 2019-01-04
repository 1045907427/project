package com.hd.agent.activiti.dao;

import com.hd.agent.activiti.model.DefinitionTask;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface DefinitionTaskMapper {
	
	public int getCountByKey(String definitionkey, String definitionid, String taskkey);

    /**
     * 获取definitiontask
     * @param definitionkey
     * @param definitionid
     * @param taskkey
     * @return
     * @author limin
     * @date Jun 16, 2015
     */
	public DefinitionTask getDefinitionTaskByKey(@Param("definitionkey")String definitionkey, @Param("definitionid")String definitionid, @Param("taskkey")String taskkey);
	
	public int addDefinitionTask(DefinitionTask task);
	
	public int updateDefinitionTaskByKey(DefinitionTask task);

    /**
     * 根据definitionid删除所有definitiontask设定
     * @param definitionkey
     * @param definitionid
     * @return
     * @author limin
     * @date May 7, 2015
     */
    public int deleteDefinitionTaskByDefinitionid(@Param("definitionkey")String definitionkey, @Param("definitionid")String definitionid);

    /**
     * 根据definitionid删除所有definitiontask设定
     * @param definitionkey
     * @param definitionid
     * @return
     * @author limin
     * @date May 7, 2015
     */
    public int deleteDefinitionTaskByDefinitionkey(@Param("definitionkey")String definitionkey);

    /**
     * 将旧版本的definitiontask设定clone到新版本
     * @param param
     * @return
     * @author limin
     * @date May 8, 2015
     */
    public int doCloneOldVerson2NewVersion(Map param);
}