/**
 * @(#)InnerMessageJob.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-31 zhanghonghui 创建版本
 */
package com.hd.agent.message.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class InnerMessageManyDelaySendJob extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		try{
			IInnerMessageService innerMessageService=(IInnerMessageService)SpringContextUtils.getBean("innerMessageService");
			Map<String,Object> queryMap=new HashMap<String, Object>();
			queryMap.put("wclocktype", "2");
			queryMap.put("wdelflag", "1");
			queryMap.put("woverclocktime", "1");
			queryMap.put("showreceiverscol", "1");
			List<MsgContent> list= innerMessageService.getMsgContentListBy(queryMap);
			if(list.size()>0){
				for(MsgContent item:list){
					if(item!=null){
						innerMessageService.addDelaySendMessage(item);
					}
				}
			}
			flag = true;
		}catch(Exception e){
			logger.error("定时器执行异常", e);			
		}
		super.executeInternal(jobExecutionContext);
	}
}