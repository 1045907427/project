/**
 * @(#)OaBrandFeeExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-04-11 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaBrandFee;
import com.hd.agent.oa.service.IOaBrandFeeService;
import com.hd.agent.oa.service.IOaPayService;
import org.apache.commons.lang3.StringUtils;

/**
 * 品牌费用申请单（支付）EndListener
 * 
 * @author limin
 */
public class OaBrandFeeExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;

	private IOaBrandFeeService oaBrandFeeService;
	
	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaBrandFeeService getOaBrandFeeService() {
		return oaBrandFeeService;
	}

	public void setOaBrandFeeService(IOaBrandFeeService oaBrandFeeService) {
		this.oaBrandFeeService = oaBrandFeeService;
	}

	@Override
	public void delete(Process process) throws Exception {

		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

			return ;
		}

		OaBrandFee fee = oaBrandFeeService.selectOaBrandFee(process.getBusinessid());
		if(fee == null) {

			return ;
		}

//		payService.rollbackBillByOaCustomerFee(fee);
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

		OaBrandFee fee = oaBrandFeeService.selectOaBrandFee(process.getBusinessid());
		if(fee == null) {

			return true;
		}

		boolean ret1 = payService.checkMatcostsInputByOaBrandFee(fee);
		boolean ret2 = payService.checkCustomerFeeByOaBrandFee(fee);
		return ret1 && ret2;
	}
}

