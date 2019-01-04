/**
 * @(#)OaCustomerFeeExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-03-29 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaCustomerFee;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaCustomerFeeService;
import org.apache.commons.lang3.StringUtils;

/**
 * 客户费用申请单（账扣）EndListener
 * 
 * @author limin
 */
public class OaCustomerFeeExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;

	private IOaCustomerFeeService oaCustomerFeeService;
	
	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaCustomerFeeService getOaCustomerFeeService() {
		return oaCustomerFeeService;
	}

	public void setOaCustomerFeeService(IOaCustomerFeeService oaCustomerFeeService) {
		this.oaCustomerFeeService = oaCustomerFeeService;
	}

	@Override
	public void delete(Process process) throws Exception {

		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

			return ;
		}

		OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(process.getBusinessid());
		if(fee == null) {

			return ;
		}

		payService.rollbackBillByOaCustomerFee(fee);
	}

	@Override
	public void end(Process process) throws Exception {

		// do nothing.
	}

	@Override
	public boolean check(Process process) throws Exception {

		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

			return true;
		}

		OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(process.getBusinessid());
		if(fee == null) {

			return true;
		}

		boolean ret1 = payService.checkCollectionOrderByOaCustomerFee(fee);
		boolean ret2 = payService.checkMatcostsInputByOaCustomerFee(fee);
		boolean ret3 = payService.checkCustomerFeeByOaCustomerFee(fee);
		return ret1 && ret2 && ret3;
	}
}

