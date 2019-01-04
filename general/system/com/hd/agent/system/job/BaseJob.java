/**
 * @(#)BaseJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 18, 2013 chenwei 创建版本
 */
package com.hd.agent.system.job;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.service.ISysLogService;
import com.hd.agent.system.model.TaskSchedule;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.service.ITaskScheduleService;

/**
 * 
 * 任务执行基础类
 * @author chenwei
 */
public class BaseJob extends QuartzJobBean{
	
	protected boolean flag = false;
	
	protected static final Logger logger = Logger.getLogger(Logger.class);
	
	protected ITaskScheduleService taskScheduleService;
	
	public ITaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}
	public BaseJob() {
		taskScheduleService = (ITaskScheduleService) SpringContextUtils.getBean("taskScheduleService");
	}

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		setTaskScheduleExecuteLog(jobExecutionContext);
	}
	
	protected void setTaskScheduleExecuteLog(JobExecutionContext jobExecutionContext){
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		//任务类型 1单次执行2循环执行
		String type = (String) jobDetail.getJobDataMap().get("type");
		if(flag){
			try {
				if("1".equals(type)){
					Scheduler scheduler = jobExecutionContext.getScheduler();
					//删除任务
					scheduler.deleteJob(jobDetail.getName(), jobDetail.getGroup());
					//删除触发器
					scheduler.unscheduleJob(jobDetail.getName(), jobDetail.getGroup());
					//单次执行的任务 执行完毕后 删除任务计划
					//taskScheduleService.deleteTaskSchedule(jobDetail.getName(), jobDetail.getGroup());
				}
				taskScheduleService.addTaskLog(jobDetail.getName(), jobDetail.getGroup(),type);
				
			} catch (Exception e) {
				logger.error("定时器执行异常", e);
			}
		}else{
			//单次任务 如果执行失败后 重新执行
			if("1".equals(type)){
				try {
					//单次执行的任务 执行完毕后 删除任务计划
					//taskScheduleService.deleteTaskSchedule(jobDetail.getName(), jobDetail.getGroup());
					taskScheduleService.addTaskLog(jobDetail.getName(), jobDetail.getGroup(),type);


					Scheduler scheduler = jobExecutionContext.getScheduler();
					//删除任务
					scheduler.deleteJob(jobDetail.getName(), jobDetail.getGroup());
					//删除触发器
					scheduler.unscheduleJob(jobDetail.getName(), jobDetail.getGroup());

					String taskid = CommonUtils.getDataNumberSendsWithRand();	
					Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
					if(map.containsKey("ClassPath")){
						int executeTimes = 1;
						String jobName = jobDetail.getName();
						if(map.containsKey("TaskName")){
							jobName = (String) map.get("TaskName");
						}else{
							map.put("TaskName", jobName);
						}
						if(map.containsKey("executeTimes")){
							executeTimes = (Integer) map.get("executeTimes");
							executeTimes ++;
						}
						map.put("executeTimes", executeTimes);
						String classPath = (String) map.get("ClassPath");
						String roottaskid = null;
						if(map.containsKey("jobroottaskid")){
							roottaskid=(String)map.get("jobroottaskid");
						}
						if(null==roottaskid
								|| "".equals(roottaskid.trim())
								|| "0".equals(roottaskid.trim())){
							TaskSchedule prevTaskSchedule=taskScheduleService.showTaskSchedule(jobDetail.getName(),jobDetail.getGroup());
							if(null!=prevTaskSchedule && StringUtils.isNotEmpty(prevTaskSchedule.getRoottaskid())){
								roottaskid=prevTaskSchedule.getRoottaskid();
								map.put("jobroottaskid", roottaskid);
							}
						}
						if(null==roottaskid || "".equals(roottaskid.trim())){
							roottaskid=null;
						}

						
						//把执行时间转成quartz表达式 适合单次执行
						Calendar nowTime = Calendar.getInstance();
						nowTime.add(Calendar.MINUTE, executeTimes);
						Date afterDate = (Date) nowTime.getTime();
						//把执行时间转成quartz表达式 适合单次执行
						String con = CommonUtils.getQuartzCronExpression(afterDate);
						//执行次数大于10后 就不再执行
						if(executeTimes<=10){
							taskScheduleService.addTaskScheduleAndStart(taskid,jobName+"_"+executeTimes, classPath,roottaskid, jobDetail.getGroup(), con, "1",map);
						}
						
					}
				} catch (Exception e) {
					logger.error("定时器重新执行失败", new Exception("定时器重新执行失败"));
				}
				
			}
			logger.error("定时器执行失败  准备重新执行", new Exception("定时器执行失败  准备重新执行"));
		}
	}
	/**
	 * 向系统日志里添加日志
	 * @param key
	 * @param logStr
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 19, 2017
	 */
	public void addSysLogInfo(String key,String logStr){
		try{
			ISysLogService sysLogService=(ISysLogService) SpringContextUtils.getBean("sysLogService");
			SysLog sysLog = new SysLog();

			//获取当前登录的用户

			sysLog.setUserid("systemjobuserid");
			sysLog.setName("系统任务");

			sysLog.setKeyname(key);
			sysLog.setContent(logStr);
			sysLog.setType("0");
			sysLog.setIp("127.0.0.1");
			sysLogService.addSysLog(sysLog);

		}catch (Exception ex) {
			logger.error(ex);
		}
	}
	/**
	 * 向系统日志里添加日志
	 * @param logStr
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 19, 2017
	 */
	public void addSysLogInfo(String logStr){
		addSysLogInfo("SYSJOB",logStr);
	}
}

