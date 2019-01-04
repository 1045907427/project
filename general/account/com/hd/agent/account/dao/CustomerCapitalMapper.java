package com.hd.agent.account.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.account.model.CustomerCapitalLog;
import com.hd.agent.common.util.PageMap;

/**
 * 客户资金情况dao
 * @author chenwei
 */
public interface CustomerCapitalMapper {
	/**
	 * 客户资金情况添加
	 * @param customerCapital
	 * @return
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public int addCustomerCapital(CustomerCapital customerCapital);
	/**
	 * 客户资金情况修改
	 * @param customerCapital
	 * @return
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public int updateCustomerCapital(CustomerCapital customerCapital);
	/**
	 * 获取客户资金情况
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public CustomerCapital getCustomerCapital(@Param("id")String id);
	
	/**
	 * 根据客户编码集获取客户余额合计
	 * @param customerstr
	 * @return
	 * @author panxiaoxiao 
	 * @date May 22, 2014
	 */
	public BigDecimal getCustomerCapitalSum(@Param("customerstr")String customerstr);
	/**
	 * 添加客户资金流水删除
	 * @param customerCapitalLog
	 * @return
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public int addCustomerCapitalLog(CustomerCapitalLog customerCapitalLog);
	/**
	 * 客户资金流水删除
	 * @param billid
	 * @param billtype
	 * @return
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public int deleteCustomerCapitalLog(@Param("customerid")String cutomerid,@Param("billid")String billid,@Param("billtype")String billtype);
	/**
	 * 获取客户资金情况列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public List showCustomerAccountList(PageMap pageMap);
	
	/**
	 * 获取客户资金列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public int showCustomerAccountCount(PageMap pageMap);
	/**
	 * 获取客户资金合计金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jan 11, 2014
	 */
	public BigDecimal showCustomerAccountSum(PageMap pageMap);
	/**
	 * 获取客户资金流水列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public List showCustomerCapitalLogList(PageMap pageMap);
	/**
	 * 获取客户资金流水数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public int showCustomerCapitalLogCount(PageMap pageMap);
	/**
	 * 更新客户资金余额
	 * @param customerid
	 * @param amount
	 * @return
	 * @author chenwei 
	 * @date Nov 8, 2013
	 */
	public int updateCustomerCapitalAmount(@Param("customerid")String customerid,@Param("amount")BigDecimal amount);
	/**
	 * 销售发票核销后，根据销售发票更新客户余额
	 * @param customerid
	 * @param salesinvoiceid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月26日
	 */
	public int writeoffCustomerCapitalWithSalesInvoice(@Param("customerid")String customerid,@Param("salesinvoiceid")String salesinvoiceid);

	/**
	 * 销售发票反核销后，根据销售发票更新客户余额
	 * @param customerid
	 * @param backWriteoffAmount
	 * @return
	 * @author chenwei 
	 * @date 2014年11月26日
	 */
	public int oppWriteoffCustomerCapitalWithSalesInvoice(@Param("customerid")String customerid,@Param("backWriteoffAmount")BigDecimal backWriteoffAmount);
	/**
	 * 客户余额变更
	 * @param customerid
	 * @param addamount
	 * @return
	 * @author chenwei 
	 * @date 2014年11月26日
	 */
	public int updateCustomerCapitalAmont(@Param("customerid")String customerid,@Param("addamount")BigDecimal addamount);
	
	/*---------------普通版特有开始-------------------------*/
	
	/**
	 * 销售开票核销后，根据销售开票更新客户余额
	 * @param customerid
	 * @param salesinvoicebillid
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public int writeoffCustomerCapitalWithSalesInvoiceBill(@Param("customerid")String customerid,@Param("salesinvoicebillid")String salesinvoicebillid);
	
	/*---------------普通版特有结束-----------------------*/

	/**
	 * 获取客户资金余额List
	 *
	 * @return
	 * @author limin
	 * @date Jul 26, 2016
	 */
	public List<CustomerCapital> getCustomerCapitalListForSync();

	/**
	 * 获取客户资金余额List
	 *
	 * @return
	 * @author limin
	 * @date Jul 26, 2016
	 */
	public List<CustomerCapitalLog> getCustomerCapitalLogListForSync(@Param("startindex") int startindex, @Param("endindex") int endindex);
}