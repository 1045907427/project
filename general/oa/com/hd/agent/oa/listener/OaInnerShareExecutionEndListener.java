/**
 * @(#)OaInnerShareExecutionEndListener.java
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

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaInnerShare;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaInnerShareService;
import org.apache.commons.lang3.StringUtils;

/**
 * 内部分摊/内部调账申请单EndListener
 * 
 * @author limin
 */
public class OaInnerShareExecutionEndListener extends BusinessEndListenerImpl {

	private IOaPayService payService;
	
	private IOaInnerShareService oaInnerShareService;

	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaInnerShareService getOaInnerShareService() {
		return oaInnerShareService;
	}

	public void setOaInnerShareService(IOaInnerShareService oaInnerShareService) {
		this.oaInnerShareService = oaInnerShareService;
	}

	@Override
	public void delete(Process process) throws Exception {

	}

	@Override
	public void end(Process process) throws Exception {
//
//		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
//			
//			return ;
//		}
//
//		OaInnerShare share = oaInnerShareService.selectOaInnerShare(process.getBusinessid());
//		
//		if(share == null) {
//			
//			return ;
//		}
//		
//		payService.addDeptDailyCostByOaInnerShare(share);
		
	}

	@Override
	public boolean check(Process process) throws Exception {

        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return true;
        }

        OaInnerShare share = oaInnerShareService.selectOaInnerShare(process.getBusinessid());
        if(share == null) {

            return true;
        }

        return payService.checkDeptDailyCostByOaInnerShare(share);
	}

}

