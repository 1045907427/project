/**
 * @(#)InnerMessageDelaySendOneJob.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-9 zhanghonghui 创建版本
 */
package com.hd.agent.message.job;

import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.service.IInnerMessageService;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class InnerMessageOneDelaySendJob  extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		//获取传递过来的参数
		Map<String,Object> map =  (Map) jobDetail.getJobDataMap().get("dataMap");
		IInnerMessageService innerMessageService=(IInnerMessageService)SpringContextUtils.getBean("innerMessageService");
		try{
			if(map.containsKey("id") && null !=map.get("id").toString() && !"".equals(map.get("id").toString().trim())){
				MsgContent msgContent=innerMessageService.showMsgContent(map.get("id").toString());
				if(null!=msgContent && "1".equals(msgContent.getDelflag()) && "2".equals(msgContent.getClocktype())){
					flag = innerMessageService.addDelaySendMessage(msgContent);
				}
			}
		}catch(Exception e){
			logger.error("定时器执行异常", e);			
		}
		super.executeInternal(jobExecutionContext);
	}
}

