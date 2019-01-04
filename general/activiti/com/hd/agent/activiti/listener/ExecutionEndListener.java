/**
 * @(#)ExecutionEndListener.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-25 zhengziyong 创建版本
 */
package com.hd.agent.activiti.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.dao.EventHandlerMapper;
import com.hd.agent.activiti.model.*;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IWorkService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.service.BusinessEndListener;
import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import org.apache.log4j.Logger;

/**
 * 流程执行结束时监听
 * 
 * @author zhengziyong
 */
public class ExecutionEndListener implements ExecutionListener {

	/**
	 * logger
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private IWorkService workService;

	private IDefinitionService definitionService;
	
	private BaseServiceImpl baseService;

	private EventHandlerMapper handlerMapper;

	public IWorkService getWorkService() {
		return workService;
	}

	public void setWorkService(IWorkService workService) {
		this.workService = workService;
	}

	public IDefinitionService getDefinitionService() {
		return definitionService;
	}

	public void setDefinitionService(IDefinitionService definitionService) {
		this.definitionService = definitionService;
	}

	public BaseServiceImpl getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseServiceImpl baseService) {
		this.baseService = baseService;
	}

	public EventHandlerMapper getHandlerMapper() {
		return handlerMapper;
	}

	public void setHandlerMapper(EventHandlerMapper handlerMapper) {
		this.handlerMapper = handlerMapper;
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		Boolean delete = (Boolean) execution.getVariable("delete_instance");
		
		if(delete == null || !delete) {

			String instanceid = execution.getProcessInstanceId();
			Process model = workService.getProcess(instanceid, "2");
			Definition definition = definitionService.getDefinitionByKey(model.getDefinitionkey(), model.getDefinitionid(), null);
			String businessurl = definition.getBusinessurl();
			String baseurl = "";
			if(StringUtils.isNotEmpty(businessurl)) {
				baseurl = businessurl;
				if(businessurl.indexOf('?') >= 0) {
					baseurl = businessurl.substring(0, businessurl.indexOf('?'));
				}
			}

			if(StringUtils.isNotEmpty(baseurl)) {
				List<Map> handlerList = handlerMapper.selectHanderItemsByUrl(baseurl, "5");
				for(Map handlerMap : handlerList) {
					String handlerStr = (String) handlerMap.get("handler");
					if(StringUtils.isNotEmpty(handlerStr)) {
						BusinessEndListener listener = (BusinessEndListener)SpringContextUtils.getBean(handlerStr);
						if(listener != null){
							listener.end(model);
							logger.info(">>>>>>>> Target:" + handlerMap.get("clazz") + ".end(): " + handlerMap.get("handlerdescription"));
						}
					}
				}
			}

//			if(StringUtils.isNotEmpty(endListener)){
//				BusinessEndListener listener = (BusinessEndListener)SpringContextUtils.getBean(endListener);
//				if(listener != null){
//					listener.end(model);
//				}
//			}
			Process process = new Process();
			process.setTaskid("");
			process.setTaskkey("");
			process.setTaskname("");
			process.setAssignee("");
			process.setCondidate("");
			process.setIsend("1");
			process.setStatus("9");
			process.setInstanceid(instanceid);
			workService.updateProcessByInstance(process);
			
			process = workService.getProcess(instanceid, "2");
			Comment comment = new Comment();
			comment.setProcessid(process.getId());
			comment.setEndtime(dateFormat.format(new Date()));
			comment.setInstanceid(instanceid);
			comment.setTaskid("");
			comment.setTaskkey("process_end");
			comment.setTaskname("结束");
			workService.addComment(comment);
			WorkLog log = new WorkLog();
			log.setInstanceid(execution.getProcessInstanceId());
			log.setTaskname("结束");
			log.setType("1");
			log.setContent("流程结束");
			workService.addLog(log);

			//----------------------------------------------------------------------
			// 流程结束通知设定↓↓↓↓↓↓↓↓↓↓↑↑↑↑↑↑↑↑↑↑
			//----------------------------------------------------------------------
			if(CommonUtils.nullToEmpty(definition.getEndremindtype()).contains("1")) {

				Map map = new HashMap();
				map.put("mtiptype", "1");
				map.put("receivers", model.getApplyuserid());
				String businessUrl = "act/workViewPage.do?from=1&taskid="+ model.getTaskid()+ "&taskkey="+ model.getTaskkey() +"&instanceid="+ instanceid;
				map.put("remindurl", businessUrl);
				map.put("title", "您的工作已经结束");
				map.put("msgtype", "4");
				map.put("content", "您发起的工作[" + model.getTitle() + "]已经审批结束。");
				map.put("tabtitle", "工作查看");
				baseService.addMessageReminder(map);

			}
			//----------------------------------------------------------------------
			// 流程结束通知设定↑↑↑↑↑↑↑↑↑↑
			//----------------------------------------------------------------------
		}
	}
}
