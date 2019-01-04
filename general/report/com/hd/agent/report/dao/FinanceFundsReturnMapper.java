/**
 * @(#)FinanceFundsReturnMapper.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 19, 2013 chenwei 创建版本
 */
package com.hd.agent.report.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.report.model.*;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;

/**
 * 
 * 资金回来情况dao
 * @author chenwei
 */
public interface FinanceFundsReturnMapper {
	/**
	 * 获取按客户资金回笼情况统计数据
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public List getFundsCustomerReturnReportData(PageMap pageMap);
	/**
	 * 获取按客户资金回笼情况统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 20, 2013
	 */
	public int getFundsCustomerReturnReportCount(PageMap pageMap);
	/**
	 * 根据客户编号获取客户各商品的成本
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 20, 2013
	 */
	public BigDecimal getFundsCustomerCostamountByCustomerid(PageMap pageMap);
	/**根据客户编号获取客户各商品的合计成本
	 * 
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 20, 2013
	 */
	public BigDecimal getFundsCustomerCostamountSum(PageMap pageMap);
	/**
	 * 按客户资金回笼情况合计统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 20, 2013
	 */
	public FundsCustomerReturnReport getFundsCustomerReturnSumReport(PageMap pageMap);
	/**
	 * 获取客户销售情况流水
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 22, 2013
	 */
	public List<CustomerSalesFlow> showCustomerSalesFlowList(PageMap pageMap);
	/**
	 * 获取客户销售情况流水
	 * @param pageMap
	 * @return
	 * @author chenwei
	 * @date Jul 22, 2013
	 */
	public List<Map> showCustomerSalesFlowListForExport(PageMap pageMap);

	/**
	 * 获取销售清单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 17, 2014
	 */
	public List<CustomerSalesFlow> showCustomerSalesFlowDetailList(PageMap pageMap);
	/**
	 * 获取客户销售情况流水数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 22, 2013
	 */
	public int showCustomerSalesFlowCount(PageMap pageMap);
	/**
	 * 获取客户销售流水合计金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 22, 2013
	 */
	public Map showCustomerSalesFlowSum(PageMap pageMap);
	/**
	 * 获取按品牌资金回笼情况数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 23, 2013
	 */
	public List showFundsBrandReturnReportData(PageMap pageMap);
	
	/**
	 * 获取按品牌资金回笼数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 23, 2013
	 */
	public int showFundsBrandReturnReportDataCount(PageMap pageMap);
	
	/**
	 * 获取按品牌部门资金回笼情况数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 14, 2013
	 */
	public List showFundsBrandDeptReturnReportData(PageMap pageMap);
	
	/**
	 * 获取按品牌部门资金回笼数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 14, 2013
	 */
	public int showFundsBrandDeptReturnReportDataCount(PageMap pageMap);
	
	/**
	 * 获取按品牌部门资金回笼合计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 14, 2013
	 */
	public FundsBrandDeptReturnReport getFundsBrandDeptReturnSumReport(PageMap pageMap);
	/**
	 * 获取品牌下商品的成本金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 23, 2013
	 */
	public BigDecimal getFundsBrandCostamountByBrand(PageMap pageMap);
	/**
	 * 获取按品牌资金回笼合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 23, 2013
	 */
	public FundsBrandReturnReport getFundsBrandReturnSumReport(PageMap pageMap);
	/**
	 * 获取按品牌资金回笼统计数据 商品成本总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 23, 2013
	 */
	public BigDecimal getFundsBrandCostamountSum(PageMap pageMap);
	/**
	 * 获取客户应收款未回笼统计数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public List showCustomerReceivablePastDueListData(PageMap pageMap);
	/**
	 * 获取客户应收款未回笼统计数据列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public int showCustomerReceivablePastDueListDataCount(PageMap pageMap);
	/**
	 * 获取客户应收款未回笼 超账多少天的金额
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getCustomerPastDueDataByDays(Map map);
	/**
	 * 获取客户应收款 未回笼 未超账期总金额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getCustomerNoPastDueDataAmount(@Param("customerid")String customerid);
	/**
	 * 获取客户应收款 未回笼 已超账期总金额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getCustomrPastDueDataAmount(@Param("customerid")String customerid);
	/**
	 * 获取客户回笼资金 未超账期总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public BigDecimal getCustomerWithdrawalNoPastDueDataAmount(PageMap pageMap);
	/**
	 * 获取客户应收款 未回笼 超账期总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getCustomrWithdrawalPastDueDataAmount(PageMap pageMap);
	/**
	 * 获取客户应收款 未回笼合计数据
	 */
	public Map getCustomerReceivablePastDueSumData(PageMap pageMap);
	/**
	 * 获取客户应收款未回笼 超账多少天的合计金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getCustomerPastDueSumDataByDays(PageMap pageMap);
	/**
	 * 获取客户应收款 未回笼 未超账期合计总金额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getCustomerNoPastDueSumDataAmount(PageMap pageMap);
	/**
	 * 获取客户应收款 未回笼 已超账期合计总金额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getCustomrPastDueSumDataAmount(PageMap pageMap);
	/**
	 * 获取客户应收款临时数据列表 按部门统计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public List getCustomerFundsReportDataTempListByDeptid(PageMap pageMap);
	/**
	 * 获取客户应收款临时数据数量 按部门统计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public int getCustomerFundsReportDataTempCountByDeptid(PageMap pageMap);
	/**
	 * 获取客户应收款临时数据列表 按客户业务员统计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public List getCustomerFundsReportDataTempListBySaleuser(PageMap pageMap);
	/**
	 * 获取客户应收款临时数据数量 按客户业务员统计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public int getCustomerFundsReportDataTempCountBySaleuser(PageMap pageMap);
	/**
	 *获取客户应收款临时数据合计数据
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public FundsDepartmentReturnReport getCustomerFundsReportDataTempSum(PageMap pageMap);
	/**
	 * 获取客户应收款临时数据列表 按品牌业务员统计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public List getCustomerFundsReportDataTempListByBranduser(PageMap pageMap);
	/**
	 * 获取客户应收款临时数据数量 按品牌业务员统计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public int getCustomerFundsReportDataTempCountByBranduser(PageMap pageMap);
	/**
	 *获取客户应收款临时数据合计数据 按品牌业务员统计
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public FundsBranduserReturnReport getCustomerFundsReportDataTempSumByBranduser(PageMap pageMap);
	/**
	 * 获取银行收支情况统计数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 23, 2013
	 */
	public List showBankReportDataList(PageMap pageMap);
	
	/**
	 * 获取客户收款合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public  List showCustomerReceiptData(PageMap pageMap);
	/**
	 * 获取客户收款合计数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public int showCustomerReceiptCount(PageMap pageMap);
	
	/**
	 * 获取分供应商应收款资金情况统计表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 1, 2014
	 */
	public List getSupplierFinanceReceiptList(PageMap pageMap);
	
	/**
	 * 获取分供应商应收款资金情况统计表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 1, 2014
	 */
	public int getSupplierFinanceReceiptCount(PageMap pageMap);
	/**
	 * 根据客户编号和银行编号获取客户在该银行的收款金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public BigDecimal getCustomerReceiptBankSumByCustomeridAndBank(PageMap pageMap);
	/**
	 * 获取客户收款总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public BigDecimal getCustomerReceiptSum(PageMap pageMap);
	/**
	 * 根据银行编号获取该银行的总收款金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public BigDecimal getCustomerReceiptBankSumByBank(PageMap pageMap);
	/**
	 * 获取超账期回单明细列表
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public List getCustomerPastDueDataListByDays(PageMap pageMap);
	/**
	 * 获取超账期回单明细列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public int getCustomerPastDueDataCountByDays(PageMap pageMap);
	/**
	 * 回单金额
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public Map getReceiptDetailTotal(@Param("id")String id);
	/**
	 * 获取客户业务员应收款未回笼统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public List showSalesUserReceivablePastDueListData(PageMap pageMap);
	/**
	 * 获取客户业务员应收款未回笼统计数据列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public int showSalesUserReceivablePastDueListDataCount(PageMap pageMap);
	/**
	 * 获取客户业务员应收款 未回笼 未超账期总金额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getSalesUserNoPastDueDataAmount(@Param("salesuserid")String salesuserid);
	/**
	 * 获取客户业务员应收款 未回笼 已超账期总金额
	 * @param salesuserid
	 * @return
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public BigDecimal getSalesUserPastDueDataAmount(@Param("salesuserid")String salesuserid);
	/**
	 * 获取客户业务员应收款未回笼 超账多少天的金额
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getSalesUserPastDueDataByDays(Map map);
	/**
	 * 获取客户业务员超账期回单明细列表
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public List getSalesUserPastDueDataListByDays(PageMap pageMap);
	/**
	 * 获取客户业务员超账期回单明细列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public int getSalesUserPastDueDataCountByDays(PageMap pageMap);
	/**
	 * 获取客户业务员应收款 未回笼合计数据
	 */
	public Map getSalesUserReceivablePastDueSumData(PageMap pageMap);
	/**
	 * 获取客户业务员应收款 未回笼 未超账期合计总金额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getSalesUserNoPastDueSumDataAmount(PageMap pageMap);
	/**
	 * 获取客户业务员应收款 未回笼 已超账期合计总金额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getSalesUserPastDueSumDataAmount(PageMap pageMap);
	/**
	 * 获取客户业务员应收款未回笼 超账多少天的合计金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getSalesUserPastDueSumDataByDays(PageMap pageMap);
	/**
	 * 获取客户业务员回笼资金 未超账期总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public BigDecimal getSalesUserWithdrawalNoPastDueDataAmount(PageMap pageMap);
	/**
	 * 获取客户业务员应收款 未回笼 超账期总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getSalesUserWithdrawalPastDueDataAmount(PageMap pageMap);
	
	/**
	 * 分客户资金回笼销售发货回单明细数据(历史在途应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public List getUnauditamountDataList(PageMap pageMap);
	
	/**
	 * 分客户资金回笼销售发货回单明细数量(历史在途应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public int getUnauditamountDataListCount(PageMap pageMap);
	
	/**
	 * 分客户资金回笼销售发货回单明细数据(历史验收应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public List getAuditamountDataList(PageMap pageMap);
	
	/**
	 * 分客户资金回笼销售发货回单明细数量(历史验收应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public int getAuditamountDataListCount(PageMap pageMap);
	
	/**
	 * 计算销售发货回单回单金额(历史验收应收款)
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public Map getAuditamountReceiptDetailTotal(@Param("id")String id);
	
	/**
	 * 分客户资金回笼销售发货回单明细数据(历史退货应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public List getRejectamountDataList(PageMap pageMap);
	
	/**
	 * 分客户资金回笼销售发货回单明细数量(历史退货应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public int getRejectamountDataListCount(PageMap pageMap);
	
	/**
	 * 计算销售退货入库单回单金额(历史退货应收款)
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public Map getRejectamountDetailTotal(@Param("id")String id);
	
	/**
	 * 分部门资金回笼销售发货回单明细数据(历史在途应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public List getUnauditamountDataListByDept(PageMap pageMap);
	
	/**
	 * 分部门资金回笼销售发货回单明细数据(历史验收应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public List getAuditamountDataListByDept(PageMap pageMap);
	
	/**
	 * 分部门资金回笼销售发货回单明细数据(历史退货应收款)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public List getRejectamountDataListByDept(PageMap pageMap);
	
	/**
	 * 获取回笼资金未核销金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public Map getNowriteoffAmountTotal(@Param("id")String id);
	
	/**
	 * 合计历史在途资金回笼销售回单金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public Map getUnauditamountReceiptDetailTotalSum(PageMap pageMap);
	
	/**
	 * 合计历史验收资金回笼销售回单金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public Map getAuditamountReceiptDetailTotalSum(PageMap pageMap);
	
	/**
	 * 合计历史退货资金回笼销售回单金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public Map getRejectamountReceiptDetailTotalSum(PageMap pageMap);
	
	/**
	 * 商品流水明细数据列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 19, 2013
	 */
	public List getSalesGoodsFlowDetailList(PageMap pageMap);
	
	/**
	 * 商品流水明细数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 19, 2013
	 */
	public int getSalesGoodsFlowDetailListCount(PageMap pageMap);
	
	/**
	 * 合计商品流水明细商品金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 19, 2013
	 */
	public BigDecimal getSalesGoodsFlowDetailSum(PageMap pageMap);
	
	/**
	 * 商品流水明细数据列表根据品牌
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 21, 2013
	 */
	public List getSalesGoodsFlowDetailListByBrand(PageMap pageMap);
	
	/**
	 * 商品流水明细数量根据品牌
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 21, 2013
	 */
	public int getSalesGoodsFlowDetailCountByBrand(PageMap pageMap);
	
	public BigDecimal getSalesGoodsFlowDetailByBrandSum(PageMap pageMap);
	
	/**
	 * 商品流水明细数据列表根据品牌部门
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public List getSalesGoodsFlowDetailListByBrandDept(PageMap pageMap);
	
	/**
	 * 商品流水明细数量根据品牌部门
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public int getSalesGoodsFlowDetailCountByBrandDept(PageMap pageMap);
	
	public BigDecimal getSalesGoodsFlowDetailByBrandDeptSum(PageMap pageMap);
	
	/**
	 * 商品流水明细数据列表根据品牌业务员
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public List getSalesGoodsFlowDetailListByBrandUser(PageMap pageMap);
	
	/**
	 * 商品流水明细数量根据品牌业务员
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public int getSalesGoodsFlowDetailCountByBrandUser(PageMap pageMap);
	
	public BigDecimal getSalesGoodsFlowDetailByBrandUserSum(PageMap pageMap);
	
	public Map getCustomerAuditamountReceiptDetailTotal(@Param("id")String id);
	
	public Map getAuditamountReceiptDetailReceipttaxamountSum(PageMap pageMap);
	/**
	 * 获取资金回来情况数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 18, 2013
	 */
	public List showBaseSalesWithdrawnData(PageMap pageMap);
	/**
	 * 获取资金回来情况数据列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 18, 2013
	 */
	public int showBaseSalesWithdrawnDataCount(PageMap pageMap);
	
	/**
	 * 获取单资金回笼情况数据列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date Jan 15, 2014
	 */
	public List showBaseFinanceDrawnData(PageMap pageMap);
	
	/**
	 * 获取单资金回笼情况数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date Jan 15, 2014
	 */
	public int showBaseFinanceDrawnDataCount(PageMap pageMap);
	
	/**
	 * 导出分供应商资金回笼数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 5, 2014
	 */
	public List getExportSupplierDrawnData(PageMap pageMap);
	
	/**
	 * 获取单资金应收款情况数据列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public List showBaseFinanceReceiptData(PageMap pageMap);
	
	/**
	 * 获取单资金应收款数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public int showBaseFinanceReceiptDataCount(PageMap pageMap);
	/**
	 * 获取客户应收款动态表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 20, 2013
	 */
	public List showCustomerReceivableDynamicData(PageMap pageMap);
	/**
	 * 获取客户应收款动态表数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 20, 2013
	 */
	public int showCustomerReceivableDynamicDataCount(PageMap pageMap);
	/**
	 * 获取客户应收款动态数据合计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 20, 2013
	 */
	public CustomerReceivableDynamic showCustomerReceivableDynamicSum(PageMap pageMap);
	/**
	 * 客户发票对账明细数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 21, 2013
	 */
	public List showCustomerInvoiceAccountBillData(PageMap pageMap);
	/**
	 * 获取客户发票对账明细数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 21, 2013
	 */
	public int showCustomerInvoiceAccountBillCount(PageMap pageMap);
	/**
	 * 获取客户发票对账合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 21, 2013
	 */
	public Map showCustomerInvoiceAccountBillSum(PageMap pageMap);
	/**
	 * 获取销售对账明细数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 22, 2013
	 */
	public List showWriteAccountDetailData(PageMap pageMap);
	/**
	 * 获取销售对账明细数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 22, 2013
	 */
	public int showWriteAccountDetailCount(PageMap pageMap);
	/**
	 * 获取销售对账明细数据合计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 22, 2013
	 */
	public Map showWriteAccountDetailSum(PageMap pageMap);
	
	/**
	 * 获取应收款超账期情况统计数据中的游标
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Nov 25, 2016
	 */
	public List getBaseReceivablePassDueListDataRS(PageMap pageMap);
	/**
	 * 获取应收款超账期情况统计数据中的游标值
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Nov 25, 2016
	 */
	public int getBaseReceivablePassDueListDataRSCount(PageMap pageMap);
	/**
	 * 获取应收款超账期情况统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public List showBaseReceivablePassDueListData(PageMap pageMap);
	/**
	 * 获取应收款超账期情况统计数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public int showBaseReceivablePassDueListCount(PageMap pageMap);
	
	/**
	 * 获取回笼超账期情况统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public List showBaseWithdrawnPastdueListData(PageMap pageMap);
	/**
	 * 获取回笼超账期情况统计数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public int showBaseWithdrawnPastdueListCount(PageMap pageMap);
	
	/**
	 * 获取银行编号
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date 2015,2,14
	 */
	public List getBankId(PageMap pageMap);
	
	/**
	 * 获取客户银行回笼金额数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 20, 2013
	 */
	public List showBankWriteReportData(PageMap pageMap);
	/**
	 * 获取客户银行回笼金额数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 20, 2013
	 */
	public int showBankWriteReportCount(PageMap pageMap);
	/**
	 * 获取分客户分银行回笼金额数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年7月2日
	 */
	public List showCustomerBankWriteReportData(PageMap pageMap);
	/**
	 * 获取分客户分银行回笼金额数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年7月2日
	 */
	public int showCustomerBankWriteReportDataCount(PageMap pageMap);

    /**
     * 根据条件获取收款凭证信息
     * @param map
     * @return
     */
    public List<Map> getCustomerSalesAmountByQueryMap(Map map);

    /**
     * 根据条件获取金额总计
     * @param map
     * @return
     * @author lin_xx
     * @date 2016-9-6
     */
    public Map getCustomerSalesAmountSum(Map map);
	
	/*-----------------------品牌业务员考核表-----------------------------------*/
	
	/**
	 * 获取品牌业务员考核列表
	 */
	public List getBranduserAssessList(PageMap pageMap);
	
	/**
	 * 获取品牌业务员考核列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public int getBranduserAssessCount(PageMap pageMap);
	
	/**
	 * 获取品牌业务员考核合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public BranduserAssess getBranduserAssessSum(PageMap pageMap);
	
	/**
	 * 获取品牌业务员考核信息
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public BranduserAssess getBranduserAssessInfo(@Param("id")String id);
	
	/**
	 * 判读是否已存在品牌业务员考核信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public int checkBranduserAssess(Map map);
	
	/**
	 * 根据参数获取品牌业务员考核信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public BranduserAssess getBranduserAssessByParam(Map map);
	
	/**
	 * 新增品牌业务员考核
	 * @param branduserAssess
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public int addBranduserAssess(BranduserAssess branduserAssess);
	
	/**
	 * 修改品牌业务员考核
	 * @param branduserAssess
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public int editBranduserAssess(BranduserAssess branduserAssess);
	
	/**
	 * 删除品牌业务员考核
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public int deleteBranduserAssess(@Param("id")String id);
	
	/**
	 * 获取品牌业务员考核报表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public List getBranduserAssessReportList(PageMap pageMap);
	
	/**
	 * 获取品牌业务员考核报表数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public int getBranduserAssessReportCount(PageMap pageMap);
	
	/**
	 * 合计品牌业务员考核报表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public BranduserAssessReport getBranduserAssessReportSum(PageMap pageMap);
	
	/*--------------------------品牌业务员考核扩展------------------------*/
	
	/**
	 * 获取品牌业务员考核明细列表
	 */
	public List getBranduserAssessExtendList(PageMap pageMap);
	
	/**
	 * 获取品牌业务员考核明细数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public int getBranduserAssessExtendCount(PageMap pageMap);
	
	/**
	 * 删除品牌业务员考核扩展
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public int deleteBranduserAssessExtend(@Param("id")String id);
	
	/**
	 * 获取品牌业务员考核报表扩展数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 1, 2014
	 */
	public List getBranduserAssessReportExtendList(PageMap pageMap);
	
	/**
	 * 获取品牌业务员考核报表扩展数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 1, 2014
	 */
	public int getBranduserAssessReportExtendCount(PageMap pageMap);
	
	/**
	 * 获取品牌业务员考核报表合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 1, 2014
	 */
	public List getBranduserAssessReportExtendSum(PageMap pageMap);
	
	/**
	 * 根据品牌业务员，业务月份获取品牌业务员考核报表合计
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public BranduserAssessExtend getBranduserAssessExtendByParam(Map map);
	
	/**
	 * 根据业务月份获取品牌业务员考核合计
	 * @param businessdate
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 1, 2014
	 */
	public BranduserAssessExtend getBranduserAssessExtendTotalSum(Map map);
	
	/**
	 * 根据业务月份获取超账金额列表
	 * @param businessdate
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public List getTotalPassAmount(Map map);
	
	/**
	 * 新增品牌业务员考核
	 * @param branduserAssessExtend
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public int addBranduserAssessExtend(BranduserAssessExtend branduserAssessExtend);
	
	/**
	 * 修改品牌业务员考核
	 * @param branduserAssessExtend
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public int editBranduserAssessExtend(BranduserAssessExtend branduserAssessExtend);
	
	/**
	 * 判读是否已存在品牌业务员考核信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public int checkBranduserAssessExtend(Map map);
	/**
	 * 按月统计资金回笼报表
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2016年1月14日
	 */
	public List<MonthSaleWithdrawnReport> showMonthFinanceDrawnData(PageMap pageMap);
	
	/**
	 * 按月统计资金回笼报表Count查询
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2016年1月14日
	 */
	public int showMonthFinanceDrawnDataCount(PageMap pageMap);
	
	/*--------------------------预期应收款------------------------------------*/
	
	/**
	 * 获取客户预期收款报表数据
	 */
	public List getCustomerExpectReceiptListData(PageMap pageMap);
	
	/**
	 * 获取客户预期收款报表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 8, 2014
	 */
	public int getCustomerExpectReceiptCount(PageMap pageMap);
	

	/**
	 * 获取客户总应收款数据
	 * @param pageMap
	 * @return
	 */
	public List<Map> getCustomerTotalReceiptReportList(PageMap pageMap);

	/**
	 * 获取客户总应收款数量
	 * @param pageMap
	 * @return
	 */
	public int getCustomerTotalReceiptReportCount(PageMap pageMap);

	/**
	 * 获取客户总应收款合计
	 * @param pageMap
	 * @return
	 */
	public Map getCustomerTotalReceiptReportSum(PageMap pageMap);

	/**
	 * 获取客户总应收款报表明细
	 * @param pageMap
	 * @return
	 */
	public List getCustomerTotalReceiptReportDetailList(PageMap pageMap);

	/**
	 * 获取客户总应收款报表期初总应收额
	 * @param pageMap
	 * @return
	 */
	public BigDecimal getCustomerTotalReceiptReportInittotalreceiptamount(PageMap pageMap);

	/**
	 * 根据条件获取收款凭证信息
	 * @param map
	 * @return
	 */
	public List<Map> getCustomerSalesAmountByQueryMapForThird(Map map);

	/**
	 * 统计供应商代垫应收的日期统计数量
	 * @param pageMap
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Feb 24, 2018
	 */
	public int getSupplierPassDueListDataRSCount(PageMap pageMap);

}

