/**
 * @(#)OaDailPayListener1.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-6 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaGoodsPrice;
import com.hd.agent.oa.model.OaGoodsPriceDetail;
import com.hd.agent.oa.service.IOaGoodsPriceService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 商品调价单Listener
 * 
 * @author limin
 */
public class OaGoodsPriceListener1 extends TaskListenerImpl {
	
	private IOaGoodsPriceService oaGoodsPriceService;

	public IOaGoodsPriceService getOaGoodsPriceService() {
		return oaGoodsPriceService;
	}

	public void setOaGoodsPriceService(IOaGoodsPriceService oaGoodsPriceService) {
		this.oaGoodsPriceService = oaGoodsPriceService;
	}

	@Override
	public void notify(DelegateTask task) throws Exception {

		String instanceid = task.getExecution().getProcessInstanceId();
		
		Process process = workService.getProcess(instanceid, "2");
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaGoodsPrice price = oaGoodsPriceService.selectOaGoodsPrice(process.getBusinessid());
		if(price == null) {
			
			return ;
		}
		
		List<OaGoodsPriceDetail> list = oaGoodsPriceService.selectOaGoodsPriceDetailListByBillid(price.getId());

        int ret = businessService.editGoodsInfoByOaGoodsPriceAdjustment(price, list);
		addHandlerLog(task, this.getClass(), ret, price, list);
	}

}

