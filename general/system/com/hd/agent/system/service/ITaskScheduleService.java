/**
 * @(#)IScheduleService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 13, 2013 chenwei 创建版本
 */
package com.hd.agent.system.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.TaskList;
import com.hd.agent.system.model.TaskSchedule;

import java.util.List;
import java.util.Map;

/**
 * 
 * 定时任务调度service
 * @author chenwei
 */
public interface ITaskScheduleService {
	/**
	 * 添加任务清单
	 * @param taskList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public boolean addTaskList(TaskList taskList) throws Exception;
	/**
	 * 显示任务清单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public PageData showTaskListData(PageMap pageMap) throws Exception;
	/**
	 * 获取任务清单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public TaskList showTaskListInfo(String id) throws Exception;
	/**
	 * 修改任务清单信息
	 * @param taskList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public boolean editTaskList(TaskList taskList) throws Exception;
	/**
	 * 删除任务清单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public boolean deleteTaskList(String id) throws Exception;
	/**
	 * 启用任务清单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public boolean openTaskList(String id) throws Exception;
	/**
	 * 禁用任务清单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public boolean closeTaskList(String id) throws Exception;
	/**
	 * 根据类型取任务清单列表
	 * @param type
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public List getTaskListData(String type) throws Exception;
	
	/**
	 * 添加任务计划安排
	 * @param taskSchedule
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public boolean addTaskSchedule(TaskSchedule taskSchedule) throws Exception;
	/**
	 * 获取任务计划安排列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public PageData showTaskScheduleList(PageMap pageMap) throws Exception;
	/**
	 * 添加任务并且启动
	 * @param taskid			任务编号
	 * @param taskname			任务名称
	 * @param classpath			任务执行类路径
	 * @param team				任务组
	 * @param triggertime		任务触发时间quartz表达式
	 * @param type				任务类型1单次执行2循环执行
	 * @param dataMap			需要传递的值
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 19, 2013
	 */
	public boolean addTaskScheduleAndStart(String taskid,String taskname,String classpath,String team,String triggertime,String type,Map dataMap) throws Exception;
	/**
	 * 添加任务并且启动
	 * @param taskid			任务编号
	 * @param taskname			任务名称
	 * @param classpath			任务执行类路径
	 * @param roottaskid		根级任务编号
	 * @param team				任务组
	 * @param triggertime		任务触发时间quartz表达式
	 * @param type				任务类型1单次执行2循环执行
	 * @param dataMap			需要传递的值
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Mar 19, 2013
	 */
	public boolean addTaskScheduleAndStart(String taskid,String taskname,String classpath,String roottaskid,String team,String triggertime,String type,Map dataMap) throws Exception;
	/**
	 * 启动任务计划
	 * @param taskid
	 * @param group
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public boolean startTaskSchedule(String taskid,String group) throws Exception;
	
	/**
	 * 暂停任务计划
	 * @param taskid
	 * @param group
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public boolean pauseTaskSchedule(String taskid,String group) throws Exception;
	/**
	 * 停止任务计划
	 * @param taskid
	 * @param group
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public boolean cancelTaskSchedule(String taskid,String group) throws Exception;
	/**
	 * 删除任务计划
	 * @param taskid
	 * @param group
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 26, 2013
	 */
	public boolean deleteTaskSchedule(String taskid,String group) throws Exception;
	/**
	 * 添加任务执行日志
	 * @param taskid	任务编号
	 * @param group		任务组
	 * @param type		执行类型1单次执行2循环执行
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public boolean addTaskLog(String taskid,String group,String type) throws Exception;
	/**
	 * 获取任务计划详细信息
	 * @param taskid
	 * @param group
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public TaskSchedule showTaskSchedule(String taskid,String group) throws Exception;
	/**
	 * 修改任务计划信息
	 * @param taskSchedule
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public boolean editTaskSchedule(TaskSchedule taskSchedule) throws Exception;
	/**
	 * 获取任务执行日志列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 19, 2013
	 */
	public PageData showTaskLogsList(PageMap pageMap) throws Exception;

    /**
     * 立即运行任务
     * @param taskid
     * @param group
     * @return
     * @throws Exception
     */
    public boolean runTaskSchedule(String taskid, String group) throws Exception;

	/**
	 * 获取需自动启动的定时任务列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-04-22
	 */
	public List getNeedAutoStartTaskScheduleList()throws Exception;
}

