/**
 * @(#)NoticeClearTopJob.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.service.INoticeService;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class NoticeClearTopAndStopJob extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		try{		
			INoticeService noticeService=(INoticeService) SpringContextUtils.getBean("noticeService");
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("overTopday", "1");
			map.put("delflag", "1");
			map.put("state", "1");
			List<MsgNotice> list=noticeService.getMsgNoticeList(map);
			List<String> idList=new ArrayList<String>();
			if(list!=null && list.size()>0){
				for(MsgNotice item :list){
					idList.add(item.getId().toString());
				}
				if(idList.size()>0){
					map.clear();
					map.put("isdataauth", "0");
					map.put("istop", "0");
					map.put("topday", "0");
					map.put("idarrs", StringUtils.join(idList.toArray((new String[0])),","));
					noticeService.updateMsgNoticeBy(map);
				}
			}
			map.clear();
			idList.clear();
			
			map.put("overEndday", "1");
			map.put("delflag", "1");
			map.put("state", "1");
			list=noticeService.getMsgNoticeList(map);			
			if(list!=null && list.size()>0){
				for(MsgNotice item : list){
					idList.add(item.getId().toString());
				}
				if(idList.size()>0){
					map.clear();
					map.put("isdataauth", "0");
					map.put("state", "0");
					map.put("idarrs", StringUtils.join(idList.toArray((new String[0])),","));
					flag = noticeService.updateMsgNoticeBy(map);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("定时器执行异常", e);	
		}
		super.executeInternal(jobExecutionContext);
	}

}

