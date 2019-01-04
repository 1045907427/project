/**
 * @(#)OaDailPayExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-29 limin 创建版本
 */
package com.hd.agent.oa.listener;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaDailyPay;
import com.hd.agent.oa.model.OaDailyPayDetail;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaDailPayService;

/**
 * 日常费用支付申请单EndListener
 * 
 * @author limin
 */
public class OaDailPayExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;
	
	private IOaDailPayService oaDailPayService;
	
	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaDailPayService getOaDailPayService() {
		return oaDailPayService;
	}

	public void setOaDailPayService(IOaDailPayService oaDailPayService) {
		this.oaDailPayService = oaDailPayService;
	}
	
	@Override
	public void delete(Process process) throws Exception {
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaDailyPay pay = oaDailPayService.selectOaDailPay(process.getBusinessid());		
		if(pay == null) {
			
			return ;
		}
		
		List<OaDailyPayDetail> list = oaDailPayService.selectOaDailPayDetailListByBillid(pay.getId());
		
		payService.rollbackDeptDailCostByDailyPay(pay, list);

	}

	@Override
	public void end(Process process) throws Exception {

	}

	@Override
	public boolean check(Process process) throws Exception {
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return true;
		}
		
		OaDailyPay pay = oaDailPayService.selectOaDailPay(process.getBusinessid());		
		if(pay == null) {
			
			return true;
		}

		return payService.checkDeptDailCostByDailyPay(pay);
	}
}

