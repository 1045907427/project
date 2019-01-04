package com.hd.agent.oa.listener;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.model.OaAccessGoodsPrice;
import com.hd.agent.oa.service.IOaAccessToBusinessService;
import com.hd.agent.oa.service.IOaAccessService;

public class OaAccessTaskListener2 extends TaskListenerImpl {
	
	private IOaAccessService oaAccessService;

	private IOaAccessToBusinessService accessBusinessService;

	public IOaAccessService getOaAccessService() {
		return oaAccessService;
	}

	public void setOaAccessService(IOaAccessService oaAccessService) {
		this.oaAccessService = oaAccessService;
	}

	public IOaAccessToBusinessService getAccessBusinessService() {
		return accessBusinessService;
	}

	public void setAccessBusinessService(IOaAccessToBusinessService accessBusinessService) {
		this.accessBusinessService = accessBusinessService;
	}

	@Override
	public void notify(DelegateTask task) throws Exception {

		String instanceid = task.getProcessInstanceId();
		Process process = workService.getProcess(instanceid, "2");

		if(process == null){

			return ;
		}
		String businessid = process.getBusinessid();

		OaAccess access = oaAccessService.selectOaAccessInfo(businessid);

		List<OaAccessGoodsPrice> goodsPricelist = oaAccessService.selectOaAccessGoodsPriceList(businessid);

		Map map = accessBusinessService.addOffPriceByOaAccess(access, goodsPricelist);
		addHandlerLog(task, this.getClass(), map, access, goodsPricelist);
	}

}
