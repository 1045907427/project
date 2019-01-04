package com.hd.agent.activiti.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.ProcessTask;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.util.PageMap;

public interface ProcessMapper {
	
	public int addProcess(Process process);
	
	public Process getProcess(String id);
	
	public Process getProcessByInstance(String id);
	
	public Process getProcessByBusinessId(String id);
	
	public List<Process> getCommonProcessList(Map map);
	
	public List<Process> getProcessList(PageMap pageMap);
	
	public int getProcessCount(PageMap pageMap);
	
	public int deleteProcess(String id);
	
	public int deleteProcessByInstance(String id);
	
	public int updateProcess(Process process);
	
	public int updateProcessByInstance(Process process);
	
	public List<Process> getWorkList(PageMap pageMap);
	
	public int getWorkCount(PageMap pageMap);
	
	public int UndoProcessFromActiviti(@Param("taskid")String taskid, @Param("instanceid")String instanceid);
	
	public int UndoProcess(@Param("id")String id, @Param("instanceid")String instanceid);
	
	public int UndoProcess2(/*@Param("id")String id, */@Param("instanceid")String instanceid);
	
	public int cancelProcess(@Param("id")String id);
	
	/**
	 * 获取节点的上一节点Key
	 * @param taskid
	 * @return
	 * @author zhengziyong 
	 * @date Oct 30, 2013
	 */
	public String getPrevTaskKey(String taskid);
	
	public int updateProcessTitle(Process process);
	
	/**
	 * 清空工作实例信息（退回至草稿箱）
	 * @param process
	 * @return
	 * @author limin 
	 * @date 2014-7-25
	 */
	public int clearProcessInstanceInfo(Process process);
	
	/**
	 * 
	 * @return
	 * @author limin 
	 * @date 2014-11-12
	 */
	public List<String> selectAllDefinitionKey();
	
	/**
	 * 舍弃未保存过的工作
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-11-14
	 */
	public int quitUnsavedWork(Map map);

    /**
     * 更新工作关键字
     * @param process
     * @return
     * @author limin
     * @date 2015-04-10
     */
    public int updateKeywords(Process process);

    /**
     * 根据definitionkey查询工作
     * @param definitionkey
     * @return
     * @author limin
     * @date Jun 29, 2015
     */
    public List<Process> selectProcessByDefinitionkey(@Param("definitionkey")String definitionkey);

	/**
	 * 判断用户是否当前流程当前节点的会签用户
	 *
	 * @param instanceid
	 * @param taskid
	 * @param userid
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public int selectIsSignUser(@Param("instanceid") String instanceid, @Param("taskid") String taskid, @Param("userid") String userid);

	/**
	 * 新增工作任务
	 *
	 * @param processTask
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public int insertProcessTask(ProcessTask processTask);

	/**
	 * 根据taskkey删除ProcessTask
	 *
	 * @param instanceid
	 * @param taskkey
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public int deleteProcessTaskByTaskkey(@Param("instanceid") String instanceid, @Param("taskkey") String taskkey);

	/**
	 * 根据taskkey删除ProcessTask
	 *
	 * @param instanceid
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public int deleteProcessTaskByInstanceid(@Param("instanceid") String instanceid);

	/**
	 * 根据taskkey获取ProcessTask
	 *
	 * @param instanceid
	 * @param taskkey
	 * @return
	 * @author limin
	 * @date Jun 16, 2017
	 */
	public List<ProcessTask> selectProcessTaskListByTaskkey(@Param("instanceid") String instanceid, @Param("taskkey") String taskkey);

	/**
	 * 获取工作list
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 21, 2017
	 */
	public List<Process> selectProcessListByMap(Map param);

	/**
	 * 更新ProcessTask begintime
	 *
	 * @param instanceid
	 * @param taskid
	 * @return
	 * @author limin
	 * @date Jul 18, 2017
	 */
	public int updateProcessTaskBegintime(@Param("instanceid") String instanceid, @Param("taskid") String taskid);

	/**
	 * 更新ProcessTask endtime
	 *
	 * @param instanceid
	 * @param taskid
	 * @return
	 * @author limin
	 * @date Jul 18, 2017
	 */
	public int updateProcessTaskEndtime(@Param("instanceid") String instanceid, @Param("taskid") String taskid);

}