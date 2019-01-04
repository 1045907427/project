/**
 * @(#)ScheduleServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 13, 2013 chenwei 创建版本
 */
package com.hd.agent.system.service.impl;

import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.dao.TaskScheduleMapper;
import com.hd.agent.system.model.TaskDetail;
import com.hd.agent.system.model.TaskList;
import com.hd.agent.system.model.TaskLog;
import com.hd.agent.system.model.TaskSchedule;
import com.hd.agent.system.service.ITaskScheduleService;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 定时任务调度实现类
 * @author chenwei
 */
public class TaskScheduleServiceImpl extends BaseServiceImpl implements
		ITaskScheduleService {
	/**
	 * quartz任务调度工厂
	 */
	private Scheduler scheduler;
	/**
	 * 任务调度dao
	 */
	private TaskScheduleMapper taskScheduleMapper;
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public TaskScheduleMapper getTaskScheduleMapper() {
		return taskScheduleMapper;
	}

	public void setTaskScheduleMapper(TaskScheduleMapper taskScheduleMapper) {
		this.taskScheduleMapper = taskScheduleMapper;
	}

    public void startScheduleJobForOne(TaskDetail taskDetail) throws Exception{
        //建立一个任务
        JobDetail jobDetail = new JobDetail();
        jobDetail.setName(taskDetail.getTaskid());
        jobDetail.setGroup(taskDetail.getGroup());
        //设置业务参数
        jobDetail.getJobDataMap().put("dataMap", taskDetail.getDataMap());
        //任务执行类型 1单次执行 2循环执行
        jobDetail.getJobDataMap().put("type", taskDetail.getType());
        //设置任务需要处理的类
        Class cla = Class.forName(taskDetail.getClasspath());
        jobDetail.setJobClass(cla);
//        jobDetail.setDurability(true);
//        scheduler.addJob(jobDetail, true);
        SimpleTrigger simpleTrigger = new SimpleTrigger(taskDetail.getTaskid(),taskDetail.getGroup(),1,5000L);
//        //绑定任务与任务触发条件
//        CronTrigger trigger = new CronTrigger(taskDetail.getTaskid(),taskDetail.getGroup(),taskDetail.getTaskid(),taskDetail.getGroup());
//        trigger.setCronExpression(taskDetail.getCronTrigger());
        //设置任务执行时间
        scheduler.scheduleJob(jobDetail, simpleTrigger);
//        scheduler.scheduleJob(simpleTrigger);
    }

	public void addScheduleJob(TaskDetail taskDetail) throws Exception{
		//建立一个任务
		JobDetail jobDetail = new JobDetail();
		jobDetail.setName(taskDetail.getTaskid());
		jobDetail.setGroup(taskDetail.getGroup());
		//设置业务参数
		jobDetail.getJobDataMap().put("dataMap", taskDetail.getDataMap());
		//任务执行类型 1单次执行 2循环执行
		jobDetail.getJobDataMap().put("type", taskDetail.getType());
		//设置任务需要处理的类
		Class cla = Class.forName(taskDetail.getClasspath());
		jobDetail.setJobClass(cla);
		scheduler.addJob(jobDetail, true);
		
		//绑定任务与任务触发条件
		CronTrigger trigger = new CronTrigger(taskDetail.getTaskid(),taskDetail.getGroup(),taskDetail.getTaskid(),taskDetail.getGroup());
		trigger.setCronExpression(taskDetail.getCronTrigger());
		//设置任务执行时间
		scheduler.scheduleJob(trigger);
	}

	@Override
	public boolean addTaskList(TaskList taskList) throws Exception {
		return taskScheduleMapper.addTaskList(taskList)>0;
	}

	@Override
	public PageData showTaskListData(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(taskScheduleMapper.showTaskListCount(pageMap),taskScheduleMapper.showTaskListData(pageMap),pageMap);
		return pageData;
	}

	@Override
	public TaskList showTaskListInfo(String id) throws Exception {
		TaskList taskList = taskScheduleMapper.showTaskListInfo(id);
		return taskList;
	}

	@Override
	public boolean editTaskList(TaskList taskList) throws Exception {
		int i = taskScheduleMapper.editTaskList(taskList);
		return i>0;
	}

	@Override
	public boolean deleteTaskList(String id) throws Exception {
		int i = taskScheduleMapper.deleteTaskList(id);
		return i>0;
	}

	@Override
	public boolean openTaskList(String id) throws Exception {
		return taskScheduleMapper.openTaskList(id)>0;
	}

	@Override
	public boolean closeTaskList(String id) throws Exception {
		return taskScheduleMapper.closeTaskList(id)>0;
	}

	@Override
	public List getTaskListData(String type) throws Exception {
		return taskScheduleMapper.getTaskListData(type);
	}

	@Override
	public boolean addTaskSchedule(TaskSchedule taskSchedule) throws Exception {
		return taskScheduleMapper.addTaskSchedule(taskSchedule)>0;
	}

	@Override
	public PageData showTaskScheduleList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(taskScheduleMapper.showTaskScheduleCount(pageMap),taskScheduleMapper.showTaskScheduleList(pageMap),pageMap);
		return pageData;
	}
	@Override
	public boolean addTaskScheduleAndStart(String taskid,String taskname,String classpath,String team,String triggertime,String type,Map dataMap) throws Exception{
		return addTaskScheduleAndStart(taskid,taskname,classpath,taskid,team,triggertime,type,dataMap);
	}
	@Override
	public boolean addTaskScheduleAndStart(String taskid,String taskname,String classpath,String roottaskid,String team,String triggertime,String type,Map dataMap) throws Exception{
		//添加任务计划安排
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setTaskid(taskid);
		taskSchedule.setTaskname(taskname);
		taskSchedule.setClasspath(classpath);
		taskSchedule.setTeam(team);
		taskSchedule.setTriggertime(triggertime);
		taskSchedule.setType(type);
		taskSchedule.setRoottaskid(roottaskid);
		taskScheduleMapper.addTaskSchedule(taskSchedule);
		
		TaskDetail taskDetail = new TaskDetail();
		taskDetail.setTaskid(taskSchedule.getTaskid());
		taskDetail.setTaskname(taskSchedule.getTaskname());
		taskDetail.setGroup(taskSchedule.getTeam());
		taskDetail.setCronTrigger(taskSchedule.getTriggertime());
		taskDetail.setClasspath(taskSchedule.getClasspath());
		taskDetail.setType(taskSchedule.getType());
		if(null!=dataMap){
			dataMap.put("ClassPath", taskSchedule.getClasspath());
		}else{
			dataMap = new HashMap();
			dataMap.put("ClassPath", taskSchedule.getClasspath());
		}
		if(!dataMap.containsKey("TaskName")){
			dataMap.put("TaskName", taskname);
		}
		taskDetail.setDataMap(dataMap);
        //只执行一次的的任务 调用后立即执行
        if("1".equals(taskSchedule.getType())){
            startScheduleJobForOne(taskDetail);
        }else{
            //任务添加到quartz
            addScheduleJob(taskDetail);
        }

		
		int i = taskScheduleMapper.startTaskSchedule(taskSchedule.getTaskid(),taskSchedule.getTeam());
		return i>0;
	}
	@Override
	public boolean startTaskSchedule(String taskid,String group) throws Exception {
		TaskSchedule taskSchedule = taskScheduleMapper.getTaskSchedule(taskid,group);
		//当任务状态是停止时或需自动启用状态时
		if(null!=taskSchedule && "3".equals(taskSchedule.getState()) || "8".equals(taskSchedule.getState())){
			TaskDetail taskDetail = new TaskDetail();
			taskDetail.setTaskid(taskSchedule.getTaskid());
			taskDetail.setTaskname(taskSchedule.getTaskname());
			taskDetail.setGroup(taskSchedule.getTeam());
			taskDetail.setCronTrigger(taskSchedule.getTriggertime());
			taskDetail.setClasspath(taskSchedule.getClasspath());
			taskDetail.setType(taskSchedule.getType());
			//任务添加到quartz
			addScheduleJob(taskDetail);
			
			int i = taskScheduleMapper.startTaskSchedule(taskid,group);
			return i>0;
		}else if(null!=taskSchedule && "2".equals(taskSchedule.getState())){
			//任务状态为暂停时
			scheduler.resumeTrigger(taskSchedule.getTaskid(), taskSchedule.getTeam());
			int i = taskScheduleMapper.startTaskSchedule(taskid,group);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public boolean pauseTaskSchedule(String taskid,String group) throws Exception {
		TaskSchedule taskSchedule = taskScheduleMapper.getTaskSchedule(taskid,group);
		if(null!=taskSchedule && "1".equals(taskSchedule.getState())){
			//暂停quartz触发器
			scheduler.pauseTrigger(taskSchedule.getTaskid(), taskSchedule.getTeam());
			int i = taskScheduleMapper.pauseTaskSchedule(taskid,group);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public boolean cancelTaskSchedule(String taskid,String group) throws Exception {
		TaskSchedule taskSchedule = taskScheduleMapper.getTaskSchedule(taskid,group);
		//任务状态为启动或者暂停时可以停止
		if(null!=taskSchedule && "1".equals(taskSchedule.getState()) || "2".equals(taskSchedule.getState())){
			//删除任务
			scheduler.deleteJob(taskSchedule.getTaskid(), taskSchedule.getTeam());
			//删除触发器
			scheduler.unscheduleJob(taskSchedule.getTaskid(), taskSchedule.getTeam());
			int i = taskScheduleMapper.cancelTaskSchedule(taskid,group);
			return i>0;
		}
		return false;
	}
	@Override
	public boolean deleteTaskSchedule(String taskid, String group)
			throws Exception {
		TaskSchedule taskSchedule = taskScheduleMapper.getTaskSchedule(taskid,group);
		//任务状态为停止或者执行完毕 可以删除
		if(null!=taskSchedule && "3".equals(taskSchedule.getState()) || "0".equals(taskSchedule.getState())){
			int i = taskScheduleMapper.deleteTaskSchedule(taskid,group);
			return i>0;
		}
		return false;
	}
	@Override
	public boolean addTaskLog(String taskid, String group,String type) throws Exception {
		TaskSchedule taskSchedule = taskScheduleMapper.getTaskSchedule(taskid, group);
		if(null!=taskSchedule){
			//添加任务执行日志
			TaskLog taskLog = new TaskLog();
			taskLog.setTaskid(taskSchedule.getTaskid());
			taskLog.setTaskname(taskSchedule.getTaskname());
			taskLog.setTeam(taskSchedule.getTeam());
			taskLog.setState("1");
			int i = taskScheduleMapper.addTaskLog(taskLog);
			//更新任务执行次数
			taskScheduleMapper.updateTaskScheduleTimes(taskid,group);
			if("1".equals(type)){
				taskScheduleMapper.setTaskScheduleOver(taskid,group);
			}
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public TaskSchedule showTaskSchedule(String taskid, String group)
			throws Exception {
		TaskSchedule taskSchedule = taskScheduleMapper.getTaskSchedule(taskid, group);
		return taskSchedule;
	}

	@Override
	public boolean editTaskSchedule(TaskSchedule taskSchedule) throws Exception {
		int i = taskScheduleMapper.editTaskSchedule(taskSchedule);
		return i>0;
	}

	@Override
	public PageData showTaskLogsList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(taskScheduleMapper.showTaskLogsCount(pageMap),taskScheduleMapper.showTaskLogsList(pageMap),pageMap);
		return pageData;
	}

    /**
     * 立即运行任务
     *
     * @param taskid
     * @param group
     * @return
     * @throws Exception
     */
    @Override
    public boolean runTaskSchedule(String taskid, String group) throws Exception {
        TaskSchedule taskSchedule = taskScheduleMapper.getTaskSchedule(taskid,group);
        if(null!=taskSchedule && ("1".equals(taskSchedule.getState()) || "2".equals(taskSchedule.getState()))){
            scheduler.triggerJob(taskid,group);
            return true;
        }
        return false;
    }

	@Override
	public List getNeedAutoStartTaskScheduleList() throws Exception {
		return taskScheduleMapper.getNeedAutoStartTaskScheduleList();
	}


}

