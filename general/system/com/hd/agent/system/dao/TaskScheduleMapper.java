/**
 * @author chenwei
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-14 chenwei 创建版本
 */
package com.hd.agent.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.TaskList;
import com.hd.agent.system.model.TaskLog;
import com.hd.agent.system.model.TaskSchedule;

/**
 * 系统任务dao
 * @author chenwei
 */
public interface TaskScheduleMapper {
	/**
	 * 添加任务清单
	 * @param taskList
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public int addTaskList(TaskList taskList);
	/**
	 * 显示任务清单数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public List showTaskListData(PageMap pageMap);
	/**
	 * 显示任务清单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public int showTaskListCount(PageMap pageMap);
	/**
	 * 获取任务清单详情
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public TaskList showTaskListInfo(@Param("id")String id);
	/**
	 * 修改任务清单
	 * @param taskList
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public int editTaskList(TaskList taskList);
	/**
	 * 删除任务清单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public int deleteTaskList(@Param("id")String id);
	/**
	 * 启用任务清单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public int openTaskList(@Param("id")String id);
	/**
	 * 禁用任务请单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public int closeTaskList(@Param("id")String id);
	/**
	 * 根据类型取任务清单列表
	 * @param type
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public List getTaskListData(@Param("type")String type);
	
	/**
	 * 添加任务计划安排
	 * @param taskSchedule
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public int addTaskSchedule(TaskSchedule taskSchedule);
	/**
	 * 获取任务计划安排列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public List showTaskScheduleList(PageMap pageMap);
	/**
	 * 获取任务计划安排数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public int showTaskScheduleCount(PageMap pageMap);
	/**
	 * 获取任务计划信息
	 * @param taskid
	 * @param team
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public TaskSchedule getTaskSchedule(@Param("taskid")String taskid,@Param("team")String team);
	/**
	 * 启动任务计划
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 16, 2013
	 */
	public int startTaskSchedule(@Param("taskid")String taskid,@Param("team")String team);
	/**
	 * 暂停任务计划
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public int pauseTaskSchedule(@Param("taskid")String taskid,@Param("team")String team);
	/**
	 * 停止任务计划
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public int cancelTaskSchedule(@Param("taskid")String taskid,@Param("team")String team);
	/**
	 * 删除任务计划
	 * @param taskid
	 * @param team
	 * @return
	 * @author chenwei 
	 * @date Nov 26, 2013
	 */
	public int deleteTaskSchedule(@Param("taskid")String taskid,@Param("team")String team);
	/**
	 * 更新任务执行次数
	 * @param taskid
	 * @param team
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public int updateTaskScheduleTimes(@Param("taskid")String taskid,@Param("team")String team);
	/**
	 * 添加任务执行日志
	 * @param taskLog
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public int addTaskLog(TaskLog taskLog);
	/**
	 * 设置任务计划状态为执行完毕
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public int setTaskScheduleOver(@Param("taskid")String taskid,@Param("team")String team);
	/**
	 * 修改任务计划信息
	 * @param taskSchedule
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public int editTaskSchedule(TaskSchedule taskSchedule);
	/**
	 * 获取任务执行日志列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 19, 2013
	 */
	public List showTaskLogsList(PageMap pageMap);
	/**
	 * 获取任务执行日志数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 19, 2013
	 */
	public int showTaskLogsCount(PageMap pageMap);

	/**
	 * 获取需自动启动的定时任务列表数据s
	 * @return
	 */
	public List getNeedAutoStartTaskScheduleList();
}