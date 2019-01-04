/**
 * @(#)BaseAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.action;

import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.activiti.service.IFormService;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import org.activiti.engine.HistoryService;

/**
 * 
 * 
 * @author zhengziyong
 */
public class BaseAction extends com.hd.agent.common.action.BaseAction {

    /**
     * 默认文字编码
     */
    protected static String DEFAULT_ENCODING = "UTF-8";

    protected IDefinitionService definitionService;

	protected IFormService formService;

	protected IWorkService workService;
	
	protected BaseServiceImpl baseService;

	protected HistoryService historyService;

	public IDefinitionService getDefinitionService() {
		return definitionService;
	}

	public void setDefinitionService(IDefinitionService definitionService) {
		this.definitionService = definitionService;
	}
	
	public IFormService getFormService() {
		return formService;
	}

	public void setFormService(IFormService formService) {
		this.formService = formService;
	}

	public IWorkService getWorkService() {
		return workService;
	}

	public void setWorkService(IWorkService workService) {
		this.workService = workService;
	}

	public BaseServiceImpl getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseServiceImpl baseService) {
		this.baseService = baseService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
}

