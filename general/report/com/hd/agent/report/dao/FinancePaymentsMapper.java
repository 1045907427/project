/**
 * @(#)FinancePaymentsMapper.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.SupplierPayBill;
import com.hd.agent.report.model.SupplierPayFlow;
import com.hd.agent.report.model.SupplierPayments;
import com.hd.agent.report.model.SupplierPaymentsInvoiceReport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * 应付款dao
 * @author chenwei
 */
public interface FinancePaymentsMapper {
	/**
	 * 获取供应商应付款统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public List showSupplierPaymentsData(PageMap pageMap);
	/**
	 * 获取供应商应付款统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public int showSupplierPaymentsDataCount(PageMap pageMap);
	
	/**
	 * 供应商开票核销情况统计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 19, 2014
	 */
	public List getSupplierInvoiceWriteoffData(PageMap pageMap);
	
	/**
	 * 供应商开票核销情况统计数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 19, 2014
	 */
	public int getSupplierInvoiceWriteoffDataCount(PageMap pageMap);
	
	/**
	 * 供应商开票核销情况统计数据合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 19, 2014
	 */
	public SupplierPayments getSupplierInvoiceWriteoffSumData(PageMap pageMap);
	
	/**
	 * 获取供应商应付款合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public SupplierPayments showSupplierPaymentsSumData(PageMap pageMap);
	
	/**
	 * 获取供应商应付款流水明细数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 19, 2013
	 */
	public List showSupplierPaysFlowListData(PageMap pageMap);
	/**
	 * 获取供应商应付款流水数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 19, 2013
	 */
	public int showSupplierPaysFlowListCount(PageMap pageMap);
	/**
	 * 获取供应商应付款流水合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 19, 2013
	 */
	public SupplierPayFlow getSupplierPaysFlowSumData(PageMap pageMap);
	
	/**
	 * 获取供应商付款合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public  List showSupplierPayData(PageMap pageMap);
	/**
	 * 获取供应商付款合计数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public int showSupplierPayCount(PageMap pageMap);
	/**
	 * 根据客户编号和银行编号获取客户在该银行的收款金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public BigDecimal getSupplierPayBankSumBySupplieridAndBank(PageMap pageMap);
	/**
	 * 获取供应商付款总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public BigDecimal getSupplierPaySum(PageMap pageMap);
	/**
	 * 根据银行编号获取该银行的总收款金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public BigDecimal getSupplierPayBankSumByBank(PageMap pageMap);
	/**
	 * 供应商应付动态-单据对账明细 数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-16
	 */
	public List getSupplierPaymentsBillDetailData(PageMap pageMap);
	/**
	 * 供应商应付动态-单据对账明细 统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-16
	 */
	public int getSupplierPaymentsBillDetailCount(PageMap pageMap);
	/**
	 * 供应商应付动态- 采购发票数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-16
	 */
	public List getSupplierPaymentsInvoiceReportData(PageMap pageMap);
	/**
	 * 供应商应付动态-采购发票 统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-16
	 */
	public int getSupplierPaymentsInvoiceReportCount(PageMap pageMap);
	/**
	 * 供应商应付动态-采购发票 合计统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-27
	 */
	public SupplierPaymentsInvoiceReport getSupplierPaymentsInvoiceReportSum(PageMap pageMap);

    /**
     * 获取供应商对应付账统计报表数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public List<SupplierPayBill> getSupplierPayBillData(PageMap pageMap);

    /**
     * 获取供应商对应付账统计报表数量
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public int getSupplierPayBillCount(PageMap pageMap);

    /**
     * 获取供应商对应付账统计报表合计
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public SupplierPayBill getSupplierPayBillSum(PageMap pageMap);

    /**
     * 获取供应商对应付账明细统计报表
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public List<SupplierPayBill> getSupplierPayBillDetailData(PageMap pageMap);

	/**
	 * 获取期初金额
	 * @param pageMap
	 * @return
	 */
	public BigDecimal getSupplierPayBillInitbuyamount(PageMap pageMap);

	/**
	 * 获取应收款余额报表数据
	 * @param pageMap
	 * @return
	 */
	public List<Map> getReceiptAmountReportList(PageMap pageMap);

	/**
	 * 获取应收款余额报表数量
	 * @param pageMap
	 * @return
	 */
	public int getReceiptAmountReportCount(PageMap pageMap);

	/**
	 * 获取应收款余额报表合计
	 * @param pageMap
	 * @return
	 */
	public Map getReceiptAmountReportSum(PageMap pageMap);

	/**
	 * 获取应收款余额明细报表
	 * @param pageMap
	 * @return
	 */
	public List<Map> getReceiptAmountDetailList(PageMap pageMap);

	/**
	 * 获取期初应收
	 * @param pageMap
	 * @return
	 */
	public BigDecimal getReceiptAmountInitunwithdrawnamount(PageMap pageMap);

	/**
	 * 获取供应商应付款明细数据
	 * @param pageMap
	 * @return
	 */
	public List<SupplierPayments> getSupplierPaymentDetailList(PageMap pageMap);

	/**
	 * 获取供应商应付款期初金额
	 * @param pageMap
	 * @return
	 */
	public BigDecimal getSupplierPaymentInitunpayamount(PageMap pageMap);
}

