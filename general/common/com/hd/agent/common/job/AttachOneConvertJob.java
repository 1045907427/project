/**
 * @(#)UploadFileJob.java
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

import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 上传文件PDF转换
 * @author zhanghonghui
 */
public class AttachOneConvertJob extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
			//获取传递过来的参数
		Map<String,Object> map =  (Map) jobDetail.getJobDataMap().get("dataMap");
		try {
			IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
			if(map.containsKey("id") && null !=map.get("id").toString() && !"".equals(map.get("id").toString().trim())){
				AttachFile attachFile=attachFileService.getAttachFile(map.get("id").toString());
				if(null!=attachFile){
					attachFileService.updateAttachConvertByJob(attachFile);
				}
			}
			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 文件上传格式转换PDF(按单次计划)失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}
