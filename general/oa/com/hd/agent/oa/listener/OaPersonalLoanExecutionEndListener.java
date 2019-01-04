/**
 * @(#)OaPersonalLoanExecutionEndListener.java
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
import com.hd.agent.oa.model.OaPersonalLoan;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaPersonalLoanService;

/**
 * 个人借款/预付款申请单EndListener
 * 
 * @author limin
 */
public class OaPersonalLoanExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;
	
	private IOaPersonalLoanService oaPersonalLoanService;

	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaPersonalLoanService getOaPersonalLoanService() {
		return oaPersonalLoanService;
	}

	public void setOaPersonalLoanService(
			IOaPersonalLoanService oaPersonalLoanService) {
		this.oaPersonalLoanService = oaPersonalLoanService;
	}

	@Override
	public void delete(Process process) throws Exception {

	}

	@Override
	public void end(Process process) throws Exception {

//		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
//
//			return ;
//		}
//
//		OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(process.getBusinessid());
//
//		if(loan == null) {
//
//			return ;
//		}
//
//		payService.addBankBillByOaPersonalLoan(loan);
	}

	@Override
	public boolean check(Process process) throws Exception {


        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return true;
        }

        OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(process.getBusinessid());

        if(loan == null) {

            return true;
        }

        return payService.checkBankBillByOaPersonalLoan(loan);
    }

}

