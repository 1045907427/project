package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.service.IOaAccessToBusinessService;
import com.hd.agent.oa.service.IOaAccessService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

public class OaAccessTaskListener12 extends TaskListenerImpl {

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

        boolean flag = accessBusinessService.deleteAuthedCustomerFeeByOaAccess(access);
        addHandlerLog(task, this.getClass(), flag, access);
    }

}
