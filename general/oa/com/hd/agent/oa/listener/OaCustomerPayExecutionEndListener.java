/**
 * @(#)OaCustomerPayExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-26 limin 创建版本
 */
package com.hd.agent.oa.listener;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaCustomerPay;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaCustomerPayService;

/**
 * 客户费用支付申请单EndListener
 * 
 * @author limin
 */
public class OaCustomerPayExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;
	
	private IOaCustomerPayService oaCustomerPayService;
	
	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaCustomerPayService getOaCustomerPayService() {
		return oaCustomerPayService;
	}

	public void setOaCustomerPayService(IOaCustomerPayService oaCustomerPayService) {
		this.oaCustomerPayService = oaCustomerPayService;
	}

	@Override
	public void delete(Process process) throws Exception {

		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaCustomerPay pay = oaCustomerPayService.selectCustomerPayInfo(process.getBusinessid());
		if(pay == null) {
			
			return ;
		}
		
		payService.rollbackBankBillByOaCustomerPay(pay);
	}

	@Override
	public void end(Process process) throws Exception {

	}

	@Override
	public boolean check(Process process) throws Exception {

		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return true;
		}
		
		OaCustomerPay pay = oaCustomerPayService.selectCustomerPayInfo(process.getBusinessid());
		if(pay == null) {
			
			return true;
		}

		return payService.checkBankBillByOaCustomerPay(pay);
	}
}
