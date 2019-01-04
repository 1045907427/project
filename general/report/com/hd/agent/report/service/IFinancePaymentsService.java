/**
 * @(#)IFinancePaymentsService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;

/**
 * 
 * 应付款相关service
 * @author chenwei
 */
public interface IFinancePaymentsService {
	/**
	 * 获取供应商应付款统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public PageData showSupplierPaymentsData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取供应商开票核销情况统计
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2014
	 */
	public PageData getSupplierInvoiceWriteoffData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取供应商应付款明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 19, 2013
	 */
	public PageData showSupplierPaysFlowListData(PageMap pageMap) throws Exception;
	/**
	 * 获取供应商付款分布数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public PageData showSupplierPayBankListData(PageMap pageMap) throws Exception;
	/**
	 * 供应商应付动态-单据对账明细 数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-16
	 */
	public PageData showSupplierPaymentsBillDetailData(PageMap pageMap) throws Exception;
	/**
	 * 供应商应付动态-采购发票 数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-26
	 */
	public PageData showSupplierPaymentsInvoiceReportData(PageMap pageMap) throws Exception;

    /**
     * 获取供应商对应付账统计报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public PageData getSupplierPayBillData(PageMap pageMap)throws Exception;

    /**
     * 获取供应商对应付账明细统计报表
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public List getSupplierPayBillDetailData(PageMap pageMap)throws Exception;

	/**
	 * 获取应收款余额报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-03
	 */
	public PageData getReceiptAmountReportData(PageMap pageMap)throws Exception;

	/**
	 * 获取应收款余额明细报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-03
	 */
	public PageData getReceiptAmountDetailData(PageMap pageMap)throws Exception;

	/**
	 * 获取供应商应付款明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-22
	 */
	public PageData getSupplierPaymentDetailList(PageMap pageMap)throws Exception;
}

