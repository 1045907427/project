/**
 * @(#)OaSupplierPayExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-12-1 limin 创建版本
 */
package com.hd.agent.oa.listener;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaSupplierPay;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaSupplierPayService;

/**
 * 货款支付申请单EndListener
 * 
 * @author limin
 */
public class OaSupplierPayExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;
	
	private IOaSupplierPayService oaSupplierPayService;
	
	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaSupplierPayService getOaSupplierPayService() {
		return oaSupplierPayService;
	}

	public void setOaSupplierPayService(IOaSupplierPayService oaSupplierPayService) {
		this.oaSupplierPayService = oaSupplierPayService;
	}

	@Override
	public void delete(Process process) throws Exception {
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaSupplierPay pay = oaSupplierPayService.selectSupplierPay(process.getBusinessid());
		if(pay == null) {
			
			return ;
		}
		
		payService.rollbackPayOrderBySupplierPay(pay);

	}

	@Override
	public void end(Process process) throws Exception {

	}

	@Override
	public boolean check(Process process) throws Exception {
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return true;
		}
		
		OaSupplierPay pay = oaSupplierPayService.selectSupplierPay(process.getBusinessid());
		if(pay == null) {
			
			return true;
		}
		
		return payService.checkPayOrderBySupplierPay(pay);
	}

}

