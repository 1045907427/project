/**
 * @(#)IJournalSheetService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 14, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.journalsheet.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.ApprovalPrice;
import com.hd.agent.journalsheet.model.CapitalInput;
import com.hd.agent.journalsheet.model.CustomerCostPayable;
import com.hd.agent.journalsheet.model.ExpensesEntering;
import com.hd.agent.journalsheet.model.FundInput;
import com.hd.agent.journalsheet.model.MatcostsInput;
import com.hd.agent.journalsheet.model.ReimburseInput;

/**
 * 
 * 资金相关接口
 * @author panxiaoxiao
 */
public interface IJournalSheetService {

	/**
	 * 获取资金录入列表分页
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public PageData getCapitalInputListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 新增资金录入
	 * @param capitalInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public boolean addCapitalInput(CapitalInput capitalInput)throws Exception;
	
	/**
	 * 导入资金录入
	 * @param capitalInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public Map addDRCapitalInput(List<CapitalInput> list)throws Exception;
	
	/**
	 * 修改资金录入
	 * @param capitalInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public boolean editCapitalInput(CapitalInput capitalInput)throws Exception;
	
	/**
	 * 删除资金录入
	 * @param capitalInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public boolean deleteCapitalInput(String id)throws Exception;
	
	/**
	 * 获取资金录入
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public CapitalInput getCapitalInputDetail(String id)throws Exception;
	
	/*------------------------资金录入（t_js_fundinput）--------------------------------------*/
	/**
	 * 获取资金录入列表分页
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public PageData getFundInputListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 新增资金录入
	 * @param fundInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public boolean addFundInput(FundInput fundInput)throws Exception;
	
	/**
	 * 导入资金录入
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public Map addDRFundInput(List<FundInput> list)throws Exception;
	
	/**
	 * 修改资金录入
	 * @param fundInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public boolean editFundInput(FundInput fundInput)throws Exception;
	
	/**
	 * 删除资金录入
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public boolean deleteFundInput(String id)throws Exception;
	
	/**
	 * 获取资金录入
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public FundInput getFundInputDetail(String id)throws Exception;
	
	/*-----------------资金统计报表----------------------------------*/

	/**
	 * 获取资金统计报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 */
	public PageData getFundStatisticsSheetList(PageMap pageMap)throws Exception;

	/**
	 * 获取资金统计报表
	 */
	public PageData getCapitalStatisticsSheetList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取资金统计报表明细
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public List getCapitalStatisticsDetailList(Map map)throws Exception;
	
	/*---------------------------资金平均金额统计报表-------------------------------*/
	
	/**
	 * 获取资金平均金额父级列表（获取所有不同供应商列表）
	 */
	public PageData getFundAverageStatisticsParentList(PageMap pageMap)throws Exception;
	
	/**
	 * 根据所选供应商编码获取资金录入详情列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 9, 2013
	 */
	public List getFundAverageStatisticsDetailList(Map map)throws Exception;
	
	/**
	 * 供应商获取资金录入详情数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 21, 2014
	 */
	public PageData getFundAverageStatisticsDetailList(PageMap pageMap)throws Exception;
	
	/*-------------------------------费用录入-----------------------------------------------*/
	
	/**
	 * 获取费用录入列表分页
	 */
	public PageData getExpensesEnteringListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 新增费用录入
	 * @param expensesEntering
	 * @return
	 * @author panxiaoxiao 
	 * @date May 29, 2013
	 */
	public boolean addExpensesEntering(ExpensesEntering expensesEntering)throws Exception;
	
	/**
	 * 导入费用录入
	 * @param expensesEntering
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public Map addDRExpensesEntering(List<ExpensesEntering> list)throws Exception;
	
	/**
	 * 修改费用录入
	 * @param expensesEntering
	 * @return
	 * @author panxiaoxiao 
	 * @date May 29, 2013
	 */
	public boolean editExpensesEntering(ExpensesEntering expensesEntering)throws Exception;
	
	/**
	 * 删除费用录入
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 29, 2013
	 */
	public boolean deleteExpensesEntering(String id)throws Exception;
	
	/**
	 * 获取费用录入详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 29, 2013
	 */
	public ExpensesEntering getExpensesEnteringDetail(String id)throws Exception;
	
	/**
	 * 判断供应商编码是否被使用
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2013
	 */
	public boolean isUsedSupplierid(String supplierid)throws Exception;

    public List<Map> getSupplierExpenseSumData(List<String> idarr) throws Exception;

	public List<Map> getSupplierExpenseSumDataForThird(List<String> idarr) throws Exception;
	
	/**
	 * 判断科目编码是否被使用
	 * @param subjectid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2013
	 */
	public boolean isUsedSubjectid(String subjectid)throws Exception;
	
	/**
	 * 分科目开单流水账列表
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 31, 2013
	 */
	public PageData getSubjectDayAccountList(PageMap pageMap)throws Exception;
	/**
	 * 分供应商开单流水账列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 31, 2013
	 */
	public Map getClienteleDayAccountList(PageMap pageMap) throws Exception;
	/**
	 * 分供应商开单流水账明细列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 1, 2013
	 */
	public List getClienteleDayAccountDetailList(Map map) throws Exception;
	/**
	 * 统计报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 31, 2013
	 */
	public PageData getStatisticslist(PageMap pageMap)throws Exception;
	/**
	 * 获取统计报表明细
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 1, 2013
	 */
	public PageData getStatisticsDetailList(PageMap pageMap) throws Exception; 
	
	/*--------------------------------核准金额----------------------------------------------------*/
	
	/**
	 * 获取核准金额列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public PageData getApprovalPriceList(PageMap pageMap)throws Exception;
	
	
	/**
	 * 新增核准金额
	 * @param approvalPrice
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public boolean addApprovalPrice(String idsArr)throws Exception;
	
	/**
	 * 修改核准金额
	 * @param approvalPrice
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public boolean editApprovalPrice(ApprovalPrice approvalPrice)throws Exception;
	
	/**
	 * 删除核准金额
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public boolean deleteApprovalPrice(String id)throws Exception;
	
	/**
	 * 获取核准金额
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 3, 2013
	 */
	public ApprovalPrice getApprovalPriceDetail(String id)throws Exception;
	
	/**
	 * 获取为导入供应商列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 3, 2013
	 */
	public List getSupplierListForApproval()throws Exception;
	
	/*-----------------代垫录入----------------------------------*/
	
	/**
	 * 获取代垫录入列表分页
	 */
	public PageData getReimburseInputListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 新增代垫录入
	 * @param reimburseInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public boolean addReimburseInput(ReimburseInput reimburseInput)throws Exception;
	
	/**
	 * 修改代垫录入
	 * @param reimburseInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public boolean editReimburseInput(ReimburseInput reimburseInput)throws Exception;
	
	/**
	 * 删除代垫录入
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public boolean deleteReimburseInput(String id)throws Exception;
	
	/**
	 * 获取代垫录入详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public ReimburseInput getReimberseInputDetail(String id)throws Exception;
	
	/**
	 * 导入代垫录入
	 * @param reimburseInput
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public Map addDRReimburseInput(List<ReimburseInput> list)throws Exception;
	
	/*-----------------代垫统计报表----------------------------------*/
	
	/**
	 * 获取整体代垫统计报表
	 * 第一层:供应商
	 */
	public PageData getRIStatisticsSheetFirstList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取代垫统计报表详情列表
	 * 第二层:品牌
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public List getRIStatisticsSheetSecondList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取代垫统计报表详情列表
	 * 第三层：科目
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public List getRIStatisticsSheetThirdList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取代垫统计报表详细列表（最后一层）
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 27, 2013
	 */
	public List getRIStatisticsSheetDetailList(Map map)throws Exception;
	
	/*-----------------新 代垫录入----------------------------------*/
	
	/**
	 * 获取代垫录入列表分页
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public PageData getMatcostsInputPageList(PageMap pageMap)throws Exception;
	
	/**
	 * 新增代垫录入,默认需要添加 客户应付费用
	 * @param reimburseInput
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public boolean addMatcostsInput(MatcostsInput matcostsInput)throws Exception;

	/**
	 * 新增代垫录入，matblance是否需要添加 客户应付费用
	 * @param reimburseInput
	 * @param matblance 是否需要添加 客户应付费用，true为需要，false不需要
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public boolean addMatcostsInput(MatcostsInput matcostsInput,boolean matblance)throws Exception;
	
	/**
	 * 修改代垫录入
	 * @param matcostsInput
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public boolean editMatcostsInput(MatcostsInput matcostsInput)throws Exception;

	
	/**
	 * 新增代垫红冲
	 * @param reimburseInput
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map addMatcostsInputHC(MatcostsInput matcostsInput)throws Exception;

	/**
	 * 撤销代垫红冲
	 * @param reimburseInput
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map removeMatcostsInputHC(String id)throws Exception;
	
	/**
	 * 删除代垫录入
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public boolean deleteMatcostsInput(String id)throws Exception;
	/**
	 * 批量删除代垫录入
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map deleteMatcostsInputMore(String idarrs)throws Exception;
	
	/**
	 * 获取代垫录入详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public MatcostsInput getMatcostsInputDetail(String id)throws Exception;
	
	/**
	 * 导入代垫录入
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map addDRMatcostsInput(List<Map> list)throws Exception;
	/**
	 * 获取代垫统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 28, 2014
	 */
	public PageData showMatcostsReportData(PageMap pageMap) throws Exception;
	/**
	 * 按供应商代垫统计数据明细列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 28, 2014
	 */
	public List showMatcostsReportDetail(Map map) throws Exception;
	/**
	 * 代垫反核销
	 * @param inputId
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-24
	 */
	public Map updateMatcostsInputRewriteoff(String inputId) throws Exception;
	
	/**
	 * 新增代垫收回
	 * @param reimburseInput
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public boolean addMatcostsReimburse(MatcostsInput matcostsInput)throws Exception;
	
	/**
	 * 修改代垫收回
	 * @param matcostsInput
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public boolean editMatcostsReimburse(MatcostsInput matcostsInput)throws Exception;
	
	/**
	 * 删除代垫收回
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public boolean deleteMatcostsReimburse(String id)throws Exception;
	/**
	 * 获取代垫收回列表分页
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public PageData getMatcostsReimbursePageList(PageMap pageMap)throws Exception;
	/**
	 * 获取收回信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-30
	 */
	public MatcostsInput getMatcostsReimburseDetail(String id) throws Exception;
	/**
	 * 导入收回
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-30
	 */
	public Map addDRMatcostsReimburse(List<Map> list) throws Exception;
	/**
	 * 根据map中的数据获取收回列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年2月14日
	 */
	public List<MatcostsInput> getMatcostsReimburseListBy(Map map) throws Exception;
	/**
	 * 新增代垫核销
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-24
	 */
	public boolean addMatcostsReimburseWriteoff(Map map) throws Exception;
	/**
	 * 核销记录分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-7
	 */
	public PageData getMatcostsStatementPageListData(PageMap pageMap) throws Exception;
	/**
	 * 代垫核销报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-13
	 */
	public PageData getMatcostsInputWriteoffReportData(PageMap pageMap) throws Exception;
	/**
	 * 收回核销报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-13
	 */
	public PageData getMatcostsReimburseWriteoffReportData(PageMap pageMap) throws Exception;
	/**
	 * 代垫收回方式迁移
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-24
	 */
	public Map updateMatcostsReimburseTypeChange(Map requestMap) throws Exception;
	/**
	 * 通过OA编号删除代垫
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月24日
	 */
	public boolean deleteMatcostsInputByOA(String oaid) throws Exception;
	/**
	 * 添加客户应付费用
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public boolean addCustomerCostPayable(CustomerCostPayable customerCostPayable) throws Exception;
	/**
	 * 根据OA编号删除客户应付费用
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public boolean deleteCustomerCostPayableByOaid(String oaid) throws Exception;
	/**
	 * 审核关闭客户应付费用
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public boolean updateCustomerCostPayableColse(String oaid) throws Exception;
	/**
	 * 启用客户应付费用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public boolean updateCustomerCostPayableOpen(String oaid) throws Exception;

	/**
	 * 根据OA编号查询客户应付费用
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-17
	 */
	public List selectCustomerCostPayablByOaid(String oaid) throws Exception;
	
	/**
	 * 根据OA编号查询代垫
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-17
	 */
	public List selectMatcostsInputByOaid(String oaid) throws Exception;
	
	/**
	 * 查询代垫收支情况一览List
	 * @param map
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-29
	 */
	public PageData selectMatcostsBalanceList(PageMap map) throws Exception;
	
	/**
	 * 查询代垫收支明细情况一览List
	 * @param map
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-30
	 */
	public PageData selectMatcostsBalanceDetailList(PageMap map) throws Exception;

	/**
	 * 删除客户费用
	 * @param oaid
	 * @param billtype
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Sep 29, 2015
	 */
	public int deleteCustomerFee(String oaid, String billtype) throws Exception;

	/**
	 * 更新客户费用
	 * @param payable
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Sep 29, 2015
	 */
	public int updateCustomerFee(CustomerCostPayable payable) throws Exception;

	/**
	 * 查询客户费用
	 * @param oaid
	 * @param billtype
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Sep 29, 2015
	 */
	public CustomerCostPayable selectCustomerFee(String oaid, String billtype) throws Exception;

	/**
	 * 重新生成报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-01-07
	 */
	public boolean doResetFundStatisticsSheetList()throws Exception;

	/**
	 * 获取资金录入最大、最小业务日期
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map getMaxDateMinDateFundInput(Map map)throws Exception;

	/**
	 * 判断是否存在资金录入
	 * @param supplierid
	 * @param businessdate
	 * @return
	 * @throws Exception
	 */
	public boolean checkFundInputByMap(String supplierid,String businessdate)throws Exception;

	/**
	 * 获取最新业务日期根据供应商
	 * @param supplierid
	 * @return
	 * @throws Exception
	 */
	public String getMaxDateFundInput(String supplierid)throws Exception;
	/**
	 * 代垫打印次数	
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年2月14日
	 */
	public boolean updateMatcostsInputPrinttimes(MatcostsInput matcostsInput);
	/**
	 * 收回打印次数
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年2月14日
	 */
	public boolean updateMatcostsReimbursePrinttimes(MatcostsInput matcostsInput);
	/**
	 * 生成凭证次数
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui
	 * @date 2016年2月14日
	 */
	public boolean updateMatcostsInputVouchertimes(MatcostsInput matcostsInput);

	/**
	 * 获取代垫录入数据集合
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-20
	 */
	public List<MatcostsInput> getMatcostsInputListBy(Map map)throws Exception;

	/**
	 * 修改代垫录入单据的应收日期
	 * @param supplierid
	 * @return Boolean
	 * @throws
	 * @author luoqiang
	 * @date Oct 26, 2017
	 */
	public Boolean updateMatcostsNoWriteoffDuefromdateBySupplier(String supplierid) throws Exception;
}

