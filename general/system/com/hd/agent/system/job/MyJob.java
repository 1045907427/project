/**
 * @(#)MyJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 13, 2013 chenwei 创建版本
 */
package com.hd.agent.system.job;

import java.util.Date;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;




/**
 * 
 * 任务类。执行任务调度器生成的任务
 * @author chenwei
 */
public class MyJob extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		//获取传递过来的参数
		Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
		System.out.println(new Date());
		//执行任务后续操作 任务执行日志 任务是否继续执行等。。
		//该方法必须执行
		setTaskScheduleExecuteLog(jobExecutionContext);
	}

}

