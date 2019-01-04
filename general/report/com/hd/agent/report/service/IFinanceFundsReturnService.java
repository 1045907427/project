/**
 * @(#)IFinanceFundsReturnService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 19, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.BranduserAssess;
import com.hd.agent.report.model.BranduserAssessExtend;
import com.hd.agent.system.model.TaskSchedule;

/**
 * 
 * 资金回笼报表service
 * @author chenwei
 */
public interface IFinanceFundsReturnService {
	/**
	 * 获取客户销售情况流水数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 22, 2013
	 */
	public PageData showCustomerSalesFlowList(PageMap pageMap) throws Exception;
	/**
	 * 获取客户销售情况流水数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jul 22, 2013
	 */
	public List showCustomerSalesFlowListForExport(PageMap pageMap) throws Exception;

	/**
	 * 导出销售流水数据
	 *
	 * @param title
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Sep 8, 2017
	 */
	public File exportCustomerFlowListData(String title, PageMap pageMap) throws Exception;

	/**
	 * 获取销售清单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 17, 2014
	 */
	public List showCustomerSalesFlowDetailList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取应收款未回笼超账期统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 15, 2013
	 */
	public PageData showCustomerReceivablePastDueListData(PageMap pageMap) throws Exception;
	/**
	 * 获取应收款已回笼超账期统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public PageData showCustomerWithdrawalPastDueListData(PageMap pageMap) throws Exception;
	/**
	 * 获取银行收支情况统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 23, 2013
	 */
	public PageData showBankReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取客户资金分布统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public PageData showCustomerReceiptBankListData(PageMap pageMap) throws Exception;
	/**
	 * 获取超账期回单明细列表
	 * @param customerid
	 * @param seq
	 * @param iswrite 		是否核销1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public PageData showCustomerPastDueListPage(PageMap pageMap) throws Exception;
	/**
	 * 获取客户业务员应收款未回笼超账期统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public PageData showSalesUserReceivablePastDueListData(PageMap pageMap) throws Exception;
	/**
	 * 获取客户业务员超账期回单明细列表
	 * @param customerid
	 * @param seq
	 * @param iswrite 		是否核销1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public PageData showSalesUserPastDueListPage(PageMap pageMap) throws Exception;
	
	/**
	 * 分客户资金回笼销售发货回单明细数据(历史在途应收款)
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public PageData showUnauditamountDataList(PageMap pageMap) throws Exception;
	
	/**
	 * 分客户资金回笼销售发货回单明细数据(历史验收应收款)
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public PageData showAuditamountDataList(PageMap pageMap)throws Exception;
	
	/**
	 * 分客户资金回笼销售发货回单明细数据(历史退货应收款)
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public PageData showRejectamountDataList(PageMap pageMap)throws  Exception;
	
	/**
	 * 商品
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 19, 2013
	 */
	public PageData showSalesGoodsFlowDetailDataList(PageMap pageMap)throws Exception;
	
	/**
	 * 分品牌资金回笼销售发货回单明细列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public PageData showSalesGoodsFlowDetailDataListByBrand(PageMap pageMap)throws Exception;
	
	/**
	 * 分品牌部门资金回笼销售发货回单明细列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public PageData showSalesGoodsFlowDetailDataListByBrandDept(PageMap pageMap)throws Exception;
	
	/**
	 * 分品牌业务员资金回笼销售发货回单明细列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public PageData showSalesGoodsFlowDetailDataListByBrandUser(PageMap pageMap)throws Exception;
	/**
	 * 获取资金回笼情况统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 18, 2013
	 */
	public PageData showBaseSalesWithdrawnData(PageMap pageMap) throws Exception;
	/**
	 * 获取客户应收款动态表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 20, 2013
	 */
	public PageData showCustomerReceivableDynamicData(PageMap pageMap) throws Exception;
	/**
	 * 客户发票对账明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 21, 2013
	 */
	public PageData showCustomerInvoiceAccountBillData(PageMap pageMap) throws Exception;
	/**
	 * 获取销售对账明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 22, 2013
	 */
	public PageData showWriteAccountDetailData(PageMap pageMap) throws Exception;
	/**
	 * 获取应收款超账期情况统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public PageData showBaseReceivablePassDueListData(PageMap pageMap) throws Exception;
	/**
	 * 获取回笼超账期情况统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public PageData showBaseWithdrawnPastdueListData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取银行回笼金额数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 19, 2013
	 */
	public PageData showBankWriteReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取分客户分银行回笼金额数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月2日
	 */
	public PageData showCustomerBankWriteReportData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取单资金回笼数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 15, 2014
	 */
	public PageData showBaseFinanceDrawnData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取分公司单资金回笼数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2015
	 */
	public Map getCompanyWithdrawnList(PageMap pageMap)throws Exception;
	
	/**
	 * 导出分供应商资金回笼情况表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 5, 2014
	 */
	public List getExportSupplierDrawnData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取单资金数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public PageData showBaseFinanceReceiptData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取分供应商单资金数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 1, 2014
	 */
	public List showSupplierFinanceReceiptData(PageMap pageMap)throws Exception;
	
	/*-----------------------品牌业务员考核表-----------------------------------*/
	
	/**
	 * 获取品牌业务员考核列表
	 */
	public PageData getBranduserAssessData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取品牌业务员考核明细列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public PageData getBranduserAssessExtendData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取品牌业务员考核信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public BranduserAssess getBranduserAssessInfo(String id)throws Exception;
	
	/**
	 * 判断是否存在品牌业务员考核
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public boolean checkBranduserAssess(Map map)throws Exception;
	
	/**
	 * 根据参数获取品牌业务员考核信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public BranduserAssess getBranduserAssessByParam(Map map)throws Exception;
	
	/**
	 * 新增品牌业务员考核
	 * @param branduserAssess
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public boolean addBranduserAssess(BranduserAssess branduserAssess)throws Exception;
	
	/**
	 * 修改品牌业务员考核
	 * @param branduserAssess
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public boolean editBranduserAssess(BranduserAssess branduserAssess)throws Exception;
	
	/**
	 * 新增品牌业务员考核明细
	 * @param branduserAssessExtend
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public boolean addBranduserAssessInfoExtend(BranduserAssessExtend branduserAssessExtend)throws Exception;
	
	/**
	 * 修改品牌业务员考核明细
	 * @param branduserAssessExtend
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public boolean editBranduserAssessInfoExtend(BranduserAssessExtend branduserAssessExtend)throws Exception;
	
	/**
	 * 删除品牌业务员考核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public boolean deleteBranduserAssess(String id)throws Exception;
	
	/**
	 * 导入品牌业务员考核
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public Map addDRBranduserAssess(List<BranduserAssess> list)throws Exception;
	
	/**
	 * 获取品牌业务员考核报表数量
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public PageData getBranduserAssessReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取品牌业务员考核报表扩展数量
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 1, 2014
	 */
	public PageData getBranduserAssessReportExtendData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取品牌业务员考核信息明细
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public BranduserAssessExtend getBranduserAssessExtendByParam(Map map)throws Exception;
	
	/**
	 * 根据业务月份获取超账金额列表
	 * @param businessdate
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public boolean getTotalPassAmount(Map map)throws Exception;
	
	/**
	 * 判断是否存在品牌业务员考核
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public boolean checkBranduserAssessExtend(Map map)throws Exception;
	
	/**
	 * 新增品牌业务员考核
	 * @param branduserAssess
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public Map addBranduserAssessExtend(BranduserAssessExtend branduserAssessExtend)throws Exception;
	
	/**
	 * 修改品牌业务员考核
	 * @param branduserAssessExtend
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public Map editBranduserAssessExtend(BranduserAssessExtend branduserAssessExtend)throws Exception;
	
	/**
	 * 删除品牌业务员考核扩展
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public boolean deleteBranduserAssessExtend(String id)throws Exception;
	
	/**
	 * 导入品牌业务员考核扩展
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public Map addDRbranduserAssessExtend(List<BranduserAssessExtend> list)throws Exception;
	/**
	 * 按月统计资金回笼数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年1月14日
	 */
	public PageData showMonthfinanceFundsReturnData(PageMap pageMap)throws Exception;
	/*--------------------------预期应收款--------------------*/
	
	public PageData getCustomerExpectReceiptListData(PageMap pageMap)throws Exception;

	/**
	 * 获取客户总应收款报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-24
	 */
	public PageData getCustomerTotalReceiptReportList(PageMap pageMap)throws Exception;

	/**
	 * 获取客户总应收款报表明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-25
	 */
	public PageData getCustomerTotalReceiptReportDetailList(PageMap pageMap)throws Exception;
    /**
     * 分客户分银行回笼情况表 获取客户的回笼金额数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-07-15
     */
    public List<Map> getCustomerSalesAmountByQueryMap(Map map) throws Exception;

	/**
	 * 如果有超账期数据，发送代垫应收超账期链接
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Oct 31, 2017
	 */
	public void sendSupplierPassDudData(TaskSchedule taskSchedule) throws Exception;

	/**
	 * 分客户分银行回笼情况表 获取客户的回笼金额数据 按单据生成
	 * @param map
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List<Map> getCustomerSalesAmountByQueryMapForThird(Map map) throws Exception;
}

