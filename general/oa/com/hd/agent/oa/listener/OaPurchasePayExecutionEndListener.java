/**
 * @(#)OaPurchasePayExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-9-18 limin 创建版本
 */
package com.hd.agent.oa.listener;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaPurchasePay;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaPurchasePayService;

/**
 * 行政采购付款申请单EndListener
 * 
 * @author limin
 */
public class OaPurchasePayExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;
	
	private IOaPurchasePayService oaPurchasePayService;
	
	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaPurchasePayService getOaPurchasePayService() {
		return oaPurchasePayService;
	}

	public void setOaPurchasePayService(IOaPurchasePayService oaPurchasePayService) {
		this.oaPurchasePayService = oaPurchasePayService;
	}

	@Override
	public void delete(Process process) throws Exception {
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaPurchasePay pay = oaPurchasePayService.selectPurchasePay(process.getBusinessid());
		if(pay == null) {
			
			return ;
		}
		
		payService.rollbackBankBillByOaPurchasePay(pay);

	}

	@Override
	public void end(Process process) throws Exception {

	}

	@Override
	public boolean check(Process process) throws Exception {
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return true;
		}
		
		OaPurchasePay pay = oaPurchasePayService.selectPurchasePay(process.getBusinessid());
		if(pay == null) {
			
			return true;
		}
		
		return payService.checkBankBillByOaPurchasePay(pay);
	}

}

