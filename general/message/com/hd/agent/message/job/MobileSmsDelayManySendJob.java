/**
 * @(#)MobileSmsDelaySendJob.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.message.model.MobileSms;
import com.hd.agent.message.service.IMobileSmsService;
import com.hd.agent.message.sms.SmsSender;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class MobileSmsDelayManySendJob extends BaseJob {
	private IMobileSmsService mobileSmsService = null;
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		//获取传递过来的参数
		mobileSmsService = (IMobileSmsService)SpringContextUtils.getBean("mobileSmsService");
		try{
				Map<String,Object> queryMap=new HashMap<String, Object>();
				queryMap.put("nodelflag", "0");
				queryMap.put("notsendflag", "0");
				List<MobileSms> list=mobileSmsService.getMobileSmsListBy(queryMap);
				if(list!=null && list.size()>0){
					for(MobileSms item : list){
						doSmsSend(item);
					}
				}
				flag = true;
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("手机短信定时发送异常",e);
		}
		super.executeInternal(jobExecutionContext);
	}
	
	public void doSmsSend(MobileSms mobileSms){
		boolean flag=false;
		try{
			Map<String, Object> map=new HashMap<String, Object>();
			if(StringUtils.isNotEmpty(mobileSms.getMobile()) && StringUtils.isNotEmpty(mobileSms.getContent())){
				map.put("id", mobileSms.getId());
				map.put("sendtime", new Date());
				map.put("dealtime", new Date());
				int irun=0;
				while(irun<5 && !flag){
					flag=SmsSender.sendSms(mobileSms.getMobile(), mobileSms.getContent());
					irun++;
				}
				if(flag){
					map.put("sendflag", "1");
				}else{
					map.put("sendflag", "2");
				}
				map.put("sendnum",irun);
				map.put("isdataauth", "0"); //去掉权限检查
				mobileSmsService.updateMobileSmsBy(map);
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("手机短信发送处理异常", e);
		}
	}
}

