/**
 * @(#)ISalesStatementService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 11, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.CollectionOrderRelate;
import com.hd.agent.account.model.SalesStatement;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 销售发票核销，对账单service
 * @author chenwei
 */
public interface ISalesStatementService {
	/**
	 * 销售发票收款单核销确认
	 * @param customerid			客户编号
	 * @param salesStatementList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public Map auditWriteoffCollectionOrder(String customerid,List<SalesStatement> salesStatementList) throws Exception;

	/**
	 * 根据收款单编号获取对账单明细列表
	 * @param orderid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public List showSalesStatementList(String orderid) throws Exception;
	
	/**
	 * 获取对账单明细列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public PageData showSalesStatementData(PageMap pageMap)throws Exception;
	/**
	 * 添加核销关联收款单信息
	 * @param billid		核销单据编号
	 * @param billtype		单据类型1销售发票2冲差单
	 * @param amount		核销金额
	 * @param detailList	关联收款单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public boolean addRelateCollectionOrder(String billid,String billtype,BigDecimal amount,List<CollectionOrderRelate> detailList) throws Exception;
	
	/**
	 * 添加销售开票核销关联收款单信息
	 * @param billid        核销单据编号
	 * @param billtype      单据类型1销售发票2冲差单
	 * @param amount        核销金额
	 * @param detailList    关联收款单列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 17, 2015
	 */
	public boolean addInvoiceBillRelateCollectionOrder(String billid,String billtype,BigDecimal amount,List<CollectionOrderRelate> detailList)throws Exception;
	/**
	 * 取消关联收款单
	 * @param billid
	 * @param billtype
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public boolean deleteRelateCollectionOrder(String billid,String billtype) throws Exception;
	
	/**
	 * 取消销售开票关联收款单
	 * @param billid
	 * @param billtype
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 17, 2015
	 */
	public boolean deleteInvoiceBillRelateCollectionOrder(String billid,String billtype)throws Exception;
	
	/**
	 * 取消关联收款单（申请开票）
	 * @param billid
	 * @param billtype
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public boolean deleteRelateCollectionOrderInvoicebill(String billid,String billtype)throws Exception;
	/**
	 * 根据单据编号获取关联的收款单列表
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public List showRelateCollectionList(String billid) throws Exception;
	
	/**
	 * 销售发票反核销
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public boolean cancelBackSalesInvoice(String invoiceid)throws Exception;
}

