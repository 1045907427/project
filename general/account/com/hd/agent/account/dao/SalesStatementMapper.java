package com.hd.agent.account.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.CollectionOrderSatement;
import com.hd.agent.account.model.SalesStatement;
import com.hd.agent.common.util.PageMap;

/**
 * 销售对账单dao(核销dao)
 * @author chenwei
 */
public interface SalesStatementMapper {
	/**
	 * 添加销售发票收款单对账单
	 * @param salesStatement
	 * @return
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public int addSalesStatement(SalesStatement salesStatement);
	
	/**
	 * 根据客户编码、单据编号、单据类型删除销售对账单
	 * @param cutomerid
	 * @param billid
	 * @param billtype
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2014
	 */
	public int deleteSalesStatement(@Param("customerid")String cutomerid,@Param("billid")String billid,@Param("billtype")String billtype);
	/**
	 * 根据收款单编号获取对账单明细列表
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public List showSalesStatementList(@Param("customerid")String customerid);
	
	/**
	 * 获取对账单明细列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public List showSalesStatementListPage(PageMap pageMap);
	
	/**
	 * 获取对账单明细数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public int showSalesStatementCount(PageMap pageMap);
	/**
	 * 添加收款单关联核销对账单
	 * @param collectionOrderSatement
	 * @return
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public int addRelateCollectionOrder(CollectionOrderSatement collectionOrderSatement);
	/**
	 * 根据单据编号获取关联的收款单列表
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public List getRelateCollectionOrderListByBillid(@Param("billid")String billid);
	/**
	 * 删除关联的收款单信息
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public int deleteRelateCollectionOrderListByBillid(@Param("billid")String billid);
	/**
	 * 获取关联的发票金额
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public BigDecimal getRelateCollectionOrderInvoiceAmount(@Param("billid")String billid);
	/**
	 * 根据收款单编号获取相联的单据列表
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public List showCollectionStatementDetailList(@Param("orderid")String orderid);
	
	/**
	 * 根据（发票、冲差单）编号及类型获取关联收款单明细
	 * @param billid
	 * @param billtype
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 11, 2014
	 */
	public List getCollectionStatementDetailList(@Param("billid")String billid,@Param("billtype")String billtype);
	/**
	 * 更新收款单发票关联对账单核销日期
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Dec 19, 2013
	 */
	public int updateCollectionOrderStatementWritedateByInvoiceid(@Param("billid")String billid,@Param("writeoffdate") String writeoffdate);
	
	/**
	 * 根据核销日期获取收款单关联发票对账单列表
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 29, 2014
	 */
	public List getCollectionStatementDetailListByMap(Map map);

	/**
	 * 根据销售核销单据编号 获取关联的核销金额
	 * @param billid
	 * @return
	 */
	public BigDecimal getSalesInvoiceRelateAmount(@Param("billid") String billid);
}
