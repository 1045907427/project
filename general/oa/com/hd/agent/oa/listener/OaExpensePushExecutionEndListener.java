/**
 * @(#)OaExpensePushExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-14 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.oa.service.IOaBusinessService;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaExpensePush;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaExpensePushService;

/**
 * 费用冲差支付申请单End Listener
 * 
 * @author limin
 */
public class OaExpensePushExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;
	
	private IOaExpensePushService oaExpensePushService;

	private IOaBusinessService businessService;

	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaExpensePushService getOaExpensePushService() {
		return oaExpensePushService;
	}

	public void setOaExpensePushService(IOaExpensePushService oaExpensePushService) {
		this.oaExpensePushService = oaExpensePushService;
	}

	public IOaBusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(IOaBusinessService businessService) {
		this.businessService = businessService;
	}

	@Override
	public void delete(Process process) throws Exception {
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaExpensePush push = oaExpensePushService.selectOaExpensePush(process.getBusinessid());
		if(push == null) {
			
			return ;
		}

        businessService.rollbackCustomerPushBanlanceByOaExpensePush(push);
		businessService.rollbackCustomerPayByOaExpensePush(push);
	}

	@Override
	public void end(Process process) throws Exception {

	}

	@Override
	public boolean check(Process process) throws Exception {
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return true;
		}
		
		OaExpensePush push = oaExpensePushService.selectOaExpensePush(process.getBusinessid());
		if(push == null) {
			
			return true;
		}

		return businessService.checkCustomerPayByOaExpensePush(push);
	}

}

