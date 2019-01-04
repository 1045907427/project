/**
 * @(#)OaAccessExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-10-9 limin 创建版本
 */
package com.hd.agent.oa.listener;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.service.IOaAccessToBusinessService;
import com.hd.agent.oa.service.IOaAccessService;

/**
 * 客户、特价、通路费用申请表End Listener
 * 
 * @author limin
 */
public class OaAccessExecutionEndListener extends BusinessEndListenerImpl {

	private IOaAccessService accessService;
	
	private IOaAccessToBusinessService businessService;
	
	private IWorkService workService;

	public IOaAccessService getAccessService() {
		return accessService;
	}

	public void setAccessService(IOaAccessService accessService) {
		this.accessService = accessService;
	}

	public IOaAccessToBusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(IOaAccessToBusinessService businessService) {
		this.businessService = businessService;
	}

	public IWorkService getWorkService() {
		return workService;
	}

	public void setWorkService(IWorkService workService) {
		this.workService = workService;
	}

	@Override
	public void delete(Process process) throws Exception {

		if(process == null){
			
			return ;
		}
		
		String businessid = process.getBusinessid();
		
		if(StringUtils.isEmpty(businessid)) {
			
			return ;
		}
		
		OaAccess access = accessService.selectOaAccessInfo(businessid);
		if(access == null){
			
			return ;
		}
		businessService.deleteBusinessBillByOaAccess(access);
		businessService.deleteMatcostsByOaAccess(access);
		businessService.deleteOffpriceByOaAccess(access);
		businessService.rollbackCustomerCostPayableByOaAccess(access);
	}

	@Override
	public void end(Process process) throws Exception {

	}

	@Override
	public boolean check(Process process) throws Exception {

		if(process == null){
			
			return true;
		}

		String id = process.getId();
		
		List list1 = businessService.selectCustomerCostPayableByOaid(id);
		if(list1.size() > 0) {
			
			return false;
		}

		List list2 = businessService.selectMatcostsByOaid(id);
		if(list2.size() > 0) {
			
			return false;
		}

		return true;
	}

}

