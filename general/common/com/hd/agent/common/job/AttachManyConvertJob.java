/**
 * @(#)AttachManyConvert.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-11-12 zhanghonghui 创建版本
 */
package com.hd.agent.common.job;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class AttachManyConvertJob extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		try{			
			IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
			Calendar today= Calendar.getInstance();
			int hour=today.HOUR_OF_DAY;
			Map<String,Object> queryMap=new HashMap<String, Object>();
			queryMap.put("jobcondition", "1");
			if(8<hour && hour<23){ //8~23内，计算当天转换
				queryMap.put("interdate", 0);
			}else{ //计算3内天转换
				queryMap.put("interdate", 3);				
			}//转换次数
			queryMap.put("wiconverts", 3);
			List<AttachFile> list=attachFileService.getAttachFileList(queryMap);
			if(null!=list && list.size()>0){
				for(AttachFile item : list){
					if(null!=item){
						attachFileService.updateAttachConvertByJob(item);
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
