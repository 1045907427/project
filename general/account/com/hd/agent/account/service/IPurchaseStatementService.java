/**
 * @(#)IPurchaseStatementService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.PurchaseStatement;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 采购发票核销，对账单service
 * @author panxiaoxiao
 */
public interface IPurchaseStatementService {

	/**
	 * 采购发票付款单核销确认
	 * @param payorderid
	 * @param purchaseStatementList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public Map auditWriteoffPayorder(String payorderid,List<PurchaseStatement> purchaseStatementList)throws Exception;
	
	/**
	 * 根据付款单编码获取对账单明细列表
	 * @param orderid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public List showPurchaseStatementList(String orderid)throws Exception;
	
	/**
	 * 获取对账单明细列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public PageData showPurchaseStatementData(PageMap pageMap)throws Exception;

    /**
     * 采购发票反核销
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public boolean uncancelPurchaseInvoice(String invoiceid)throws Exception;
}

