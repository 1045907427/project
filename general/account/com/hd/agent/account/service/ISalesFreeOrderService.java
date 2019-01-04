/**
 * @(#)ISalesFreeOrderService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年5月29日 chenwei 创建版本
 */
package com.hd.agent.account.service;

import java.util.Map;

import com.hd.agent.account.model.ReceivablePastDueReason;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.TaskSchedule;

/**
 * 
 * 销售放单service
 * @author chenwei
 */
public interface ISalesFreeOrderService {
	/**
	 * 手机添加超账期原因并生成销售放单申请
	 * @param jsonstr
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月29日
	 */
	public boolean addSalesFreeOrderByPhone(String jsonstr) throws Exception;
	
	/**
	 * 获取放单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public PageData getSalesFreeOrderData(PageMap pageMap)throws Exception;
	
	/**
	 * 批量审核放单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public Map auditSalesFreeOrder(String ids)throws Exception;
	
	/**
	 * 批量反审放单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public Map oppauditSalesFreeOrder(String ids)throws Exception;
	
	/**
	 * 批量删除放单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public boolean deleteSalesFreeOrder(String ids)throws Exception;
	
	/**
	 * 获取客户应收款超账期原因报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public PageData showBaseReceivablePassDueReasonListData(PageMap pageMap)throws Exception;
	
	public PageData showBaseReceivablePassDueReasonListDataTest(PageMap pageMap)throws Exception;
	
	/**
	 * 添加客户应收款超账期原因
	 * @param receivablePastDueReason
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 22, 2014
	 */
	public Map addCustomerReceivablePastDueReason(ReceivablePastDueReason receivablePastDueReason)throws Exception;
	
	/**
	 * 获取客户应收款超账期原因变更次数
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2014
	 */
	public int getCustomerReceivablePastDueReasonChangenum(String customerid)throws Exception;
	
	/**
	 * 一键清除客户应收款超账期原因
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2014
	 */
	public boolean oneClearReceivablePastDueReason()throws Exception;
	
	/**
	 * 根据客户编码获取历史应收款超账期原因列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 23, 2014
	 */
	public PageData getHistoryCustomerReceivablePastDueReasonList(PageMap pageMap)throws Exception;
	
	/**
	 * 客户应收款超账期原因
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 22, 2014
	 */
	public ReceivablePastDueReason getCustomerReceivablePastDueReason(String customerid);

	/**
	 * 代垫应收分析报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Oct 27, 2017
	 */
	public PageData showSupplierReceivablePastDueReasonListData(PageMap pageMap) throws Exception;
}

