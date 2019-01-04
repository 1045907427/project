/**
 * @(#)JournalSheetMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 14, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.journalsheet.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.ApprovalPrice;
import com.hd.agent.journalsheet.model.CapitalInput;
import com.hd.agent.journalsheet.model.ExpensesEntering;
import com.hd.agent.journalsheet.model.FundInput;
import com.hd.agent.journalsheet.model.MatcostsInput;
import com.hd.agent.journalsheet.model.MatcostsInputStatement;
import com.hd.agent.journalsheet.model.ReimburseInput;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface JournalSheetMapper {

	/**
	 * 获取资金录入列表分页
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public List getCapitalInputListPage(PageMap pageMap);
	
	/**
	 * 获取资金录入列表分页数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int getCapitalInputListCount(PageMap pageMap);
	
	/**
	 * 根据查询条件获取费用合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public BigDecimal getCapitalInputSum(PageMap pageMap);
	
	/**
	 * 新增资金录入
	 * @param capitalInput
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int addCapitalInput(CapitalInput capitalInput);
	/**
	 * 根据供应商跟科目删除资金录入信息
	 * @param supplierid
	 * @param subjectid
	 * @return
	 * @author chenwei 
	 * @date Jul 1, 2013
	 */
	public int deleteCapitalInputBySupplieridAndSubjectid(@Param("supplierid")String supplierid,@Param("subjectid")String subjectid);
	
	/**
	 *  修改资金录入 
	 * @param capitalInput
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int editCapitalInput(CapitalInput capitalInput);
	
	/**
	 * 删除资金录入
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int deleteCapitalInput(String id);
		
	/**
	 * 删除所有资金录入数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 2, 2013
	 */
	public int deleteCapitalInputAll();
	
	/**
	 * 获取资金录入详情
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public CapitalInput getCapitalInputDetail(Map map);
	/**
	 * 根据日期记录当天的资金信息
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Dec 10, 2013
	 */
	public int addFundinputHisJob(@Param("id")String id);
	/**
	 * 删除历史资金信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jan 23, 2014
	 */
	public int deleteFundInputHis(@Param("id")String id);
	/**
	 * 更新历史资金信息状态
	 * @return
	 * @author chenwei 
	 * @date Jan 23, 2014
	 */
	public int updateFundInputHisState();
	/**
	 * 获取最近有效数据
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Jan 22, 2014
	 */
	public List getFundinputHisList(@Param("date")String date);
	/**
	 * 获取历史表中的数据
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jan 22, 2014
	 */
	public FundInput getFundInputHisData(@Param("id")String id);
	
	/*-------------------------资金录入(t_js_fundinput)----------------------------------*/
	/**
	 * 获取资金录入列表分页
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public List getFundInputListPage(PageMap pageMap);
	
	/**
	 * 获取资金录入列表分页数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int getFundInputListCount(PageMap pageMap);
	
	/**
	 * 新增资金录入
	 * @param capitalInput
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int addFundInput(FundInput fundInput);
	
	/**
	 *  修改资金录入 
	 * @param capitalInput
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int editFundInput(FundInput fundInput);
	
	/**
	 * 删除资金录入
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int deleteFundInput(String id);
		
	/**
	 * 根据供应商编码修改资金录入
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 2, 2013
	 */
	public int editFundInputBySupplierid(String supplierid);
	
	/**
	 * 获取资金录入详情
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public FundInput getFundInputDetail(Map map);
	
	/**
	 * 根据供应商编码判断是否存在该资金录入
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 2, 2013
	 */
	public int checkFundInputBySupplierid(String supplierid);
	
	/**
	 * 根据供应商编码和业务日期判断是否存在该资金录入
	 * @param supplierid
	 * @param businessdate
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2014
	 */
	public int checkFundInputBySupplieridAndDate(@Param("supplierid")String supplierid,@Param("businessdate")String businessdate);
	
	/**
	 * 根据供应商编码和业务日期判断删除该资金录入
	 * @param supplierid
	 * @param businessdate
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2014
	 */
	public int deleteFundInputBySupplieridAndDate(@Param("supplierid")String supplierid,@Param("businessdate")String businessdate);

	/**
	 * 根据条件判断是否存在资金录入
	 * @param map
	 * @return
	 */
	public int checkFundInputByMap(Map map);
	/**
	 * 根据查询条件获取各项资金合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public FundInput getFundInputSum(PageMap pageMap);
	
	/*----------------------------资金统计报表（t_js_fundinput）---------------------------*/
	
	/**
	 * 资金录入下的供应商列表（不重复）
	 */
	public List<FundInput> getFundStatisticsBySupplier(PageMap pageMap);
	
	/**
	 * 资金录入下的供应商数量（不重复）数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int getFundStatisticsBySupplierCount(PageMap pageMap);
	
	/**
	 * 根据供应商获取各项资金合计金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public FundInput getFundAmountBySupplier(PageMap pageMap);
	
	/**
	 * 获取所有各项资金总和 
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public FundInput getTotalFundAmountByPageMap(PageMap pageMap);
	
	
	
	/*----------------------------资金统计报表-------------------------------*/
	
	/**
	 * 资金录入下的供应商列表（不重复）,获取报表分类
	 */
	public List<CapitalInput> getCapitalStatisticsBySupplier(PageMap pageMap);
	
	/**
	 * 资金录入下的供应商数量（不重复）,获取报表分类数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public int getCapitalStatisticsBySupplierCount(PageMap pageMap);
	
	/**
	 * 根据供应商和资金科目获取资金合计金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public BigDecimal getCapitalAmountBySupplierAndPriceSubject(PageMap pageMap);
	
	/**
	 * 根据资金科目获取科目费用合计金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public BigDecimal getCapitalStatisticsQuerySumByPriceSubject(PageMap pageMap);
	
	/**
	 * 根据资金科目获取其总金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public BigDecimal getCapitalStatisticsAllSumByPriceSubejct(PageMap pageMap);
	
	/**
	 * 获取所有数据的合计金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public BigDecimal getCapitalStatisticsAllSum(PageMap pageMap);
	
	/**
	 * 根据供应商编号等条件，获取费用明细
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 14, 2013
	 */
	public List<CapitalInput> getCapitalStatisticsDetailList(PageMap pageMap);
	
	/**
	 * 根据供应商编号获取合计金额（资金统计报表合计金额与统计报表实际占用金额关联）
	 * @param supplierid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 15, 2013
	 */
	public BigDecimal getRetruntCapitalAmount(PageMap pageMap3);
	
	/*-----------------------------资金平均金额统计报表-----------------------------------*/
	
	/**
	 * 获取供应商资金平均统计情况报表
	 */
	public List<FundInput> getAverageDataGroupBySupplierList(PageMap pageMap);
	
	/**
	 * 获取供应商资金平均统计情况数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 21, 2014
	 */
	public int getAverageDataGroupBySupplierCount(PageMap pageMap);
	/**
	 * 获取供应商资金平均统计情况报表，返Map列表对象
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-10
	 */
	public List<Map<String, Object>>getAverageDataGroupBySupplierMapList(PageMap pageMap);
	
	/**
	 * 合计当前平均金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 21, 2014
	 */
	public FundInput getSumAvgDataGroupBySupplier(PageMap pageMap);
	
	/**
	 * 合计全部平均金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 21, 2014
	 */
	public FundInput getSumAllAvgData(PageMap pageMap);
	
	/**
	 * 获取所有不同供应商列表
	 */
	public List<FundInput> getAllSupplierFromFundAverage(PageMap pageMap);
	
	/**
	 * 获取获取所有不同供应商列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 9, 2013
	 */
	public int getAllSupplierFromFundAverageCount(PageMap pageMap);
	
	/**
	 * 根据查询条件获取资金个数
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 9, 2013
	 */
	public int getFundInputCount(PageMap pageMap);
	
	/**
	 * 获取所有各项资金总和
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 9, 2013
	 */
	public FundInput getTotalFundAmount();
	
	/**
	 * 获取所有各项资金数量
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 9, 2013
	 */
	public int getTotalFundCount();
	
	/**
	 * 根据条件获取资金录入详情列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 9, 2013
	 */
	public List<FundInput> getFundStatisticsDetailList(PageMap pageMap);
	
	/**
	 * 根据条件获取资金录入情况数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 21, 2014
	 */
	public int getFundStatisticsDetailListCount(PageMap pageMap);
	
	/**
	 * 资金录入情况详情合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 21, 2014
	 */
	public FundInput getTotalFundDetailAmount(PageMap pageMap);
	
	/*-------------------------------费用录入-----------------------------------------------*/
	
	public List getExpensesEnteringListPage(PageMap pageMap);
	
	public int  getExpensesEnteringListCount(PageMap pageMap);
	/**
	 * 根据查询条件获取费用合计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 3, 2013
	 */
	public BigDecimal getExpensesEnteringSum(PageMap pageMap);
	
	public int addExpensesEntering(ExpensesEntering expensesEntering);
	
	public int editExpensesEntering(ExpensesEntering expensesEntering);
	
	public int deleteExpensesEntering(String id);
	
	public ExpensesEntering getExpensesEnteringDetail(Map map);
	
	public int isUsedSupplierid(String supplierid);
	
	public int isUsedSubjectid(String subjectid);

    public  List<Map> getSupplierExpenseSumData(List<String> idarr);

	/**
	 * 获取凭证数据时，按单据获取
	 * @param idarr
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public  List<Map> getSupplierExpenseSumDataForThird(List<String> idarr);
	
	/*--------------------------------分科目开单流水账----------------------------------------------------*/
	
	public List<ExpensesEntering> getSubjectDayAccountList(PageMap pageMap);
	/**
	 * 获取查询的科目合计金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public BigDecimal getSubjectQuerySum(PageMap pageMap);
	/**
	 * 获取全部的科目合计金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public BigDecimal getSubjectAllSum(PageMap pageMap);
	
	public List getSupplierListBySubjectid(PageMap pageMap);
	
	public int getSupplierListCountBySubjectid(PageMap pageMap);
	
	public List getClienteleDayAccountList(PageMap pageMap);
	/**
	 * 获取分客户统计明细列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 1, 2013
	 */
	public List getClienteleDayAccountDetailList(PageMap pageMap);
	
	public List<ExpensesEntering> getStatisticslist(PageMap pageMap);
	
	public int getStatisticsCount(PageMap pageMap);
	/**
	 * 获取查询合计总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 3, 2013
	 */
	public BigDecimal getStatisticsSumBySupplierid(PageMap pageMap);
	/**
	 * 获取全部总金额
	 * @return
	 * @author chenwei 
	 * @date Jun 3, 2013
	 */
	public BigDecimal getStatisticsAllSum(PageMap pageMap);
	/**
	 * 根据科目获取当前查询总金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public BigDecimal getStatisticsQuerySumBySubjectid(PageMap pageMap);
	/**
	 * 根据科目获取该科目的总计金额
	 * @param subjectid
	 * @return
	 * @author chenwei 
	 * @date Jun 3, 2013
	 */
	public BigDecimal getStatisticsAllSumBySubejctid(PageMap pageMap);
	
	public List<ExpensesEntering> getStatisticslistBySupplierid(PageMap pageMap);
	
	public int getStatisticslistBySupplieridCount(PageMap pageMap);
	
	public BigDecimal getAmountBySupplierAndSubjectid(PageMap pageMap);
	/**
	 * 获取货款统计报表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Oct 5, 2013
	 */
	public List<Map> getStatisticsListData(PageMap pageMap);
	/**
	 * 获取货款统计报表数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Oct 5, 2013
	 */
	public int getStatisticsListCount(PageMap pageMap);
	/**
	 * 获取货款统计报表合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Oct 5, 2013
	 */
	public Map getStatisticsListSumData(PageMap pageMap);
	/**
	 * 根据供应商编号等条件，获取费用明细
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Jun 1, 2013
	 */
	public List<ExpensesEntering> getStatisticsDetailList(PageMap pageMap);
	
	/**
	 * 获取货款明细数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 5, 2014
	 */
	public int getStatisticsDetailCount(PageMap pageMap);
	
	
	public List getExpensesEnteringListBySupplieridAndSubjectid(@Param("supplierid")String supplierid,@Param("subjectid")String subjectid);
	
	/*--------------------------------核准金额----------------------------------------------------*/
	
	/**
	 * 获取核准金额列表
	 */
	public List<ApprovalPrice> getApprovalPriceList(PageMap pageMap);
	
	/**
	 * 获取核准金额列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public int getApprovalPriceCount(PageMap pageMap);
	
	/**
	 * 新增核准金额
	 * @param approvalPrice
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public int addApprovalPrice(ApprovalPrice approvalPrice);
	
	/**
	 * 修改核准金额
	 * @param approvalPrice
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public int editApprovalPrice(ApprovalPrice approvalPrice);
	
	/**
	 * 删除核准金额
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public int deleteApprovalPrice(String id);
	
	/**
	 * 获取核准金额详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 3, 2013
	 */
	public ApprovalPrice getApprovalPriceDetail(String id);
	
	/**
	 * 获取未导入供应商列表
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 3, 2013
	 */
	public List getSupplierListForApproval();
	/**
	 * 根据供应商编号获取该供应商核准金额
	 * @param supplierid
	 * @return
	 * @author chenwei 
	 * @date Jun 3, 2013
	 */
	public BigDecimal getApprovalPriceBySupplierid(PageMap pageMap);
	/**
	 * 根据供应商编号获取该供应商实际占用金额
	 * @param supplierid
	 * @return
	 * @author chenwei 
	 * @date Jun 11, 2013
	 */
	public BigDecimal getRealAtamountBySupplierid(PageMap pageMap);
	/**
	 * 根据查询条件获取供应商的核准金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public BigDecimal getApprovalPriceByQuery(PageMap pageMap);
	/**
	 * 根据查询条件获取供应商的实际占用金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 11, 2013
	 */
	public BigDecimal getRealAtamountByQuery(PageMap pageMap);
	/**
	 * 获取全部供应商的核准总金额
	 * @return
	 * @author chenwei 
	 * @date Jun 3, 2013
	 */
	public BigDecimal getTotalApprovalPrice(PageMap pageMap);
	/**
	 * 获取全部供应商实际占用金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 11, 2013
	 */
	public BigDecimal getTotalRealAtamount(PageMap pageMap);
	
	/*--------------------------------代垫录入----------------------------------------------------*/
	
	/**
	 * 获取代垫录入列表分页
	 */
	public List getReimburseInputListPage(PageMap pageMap);
	
	/**
	 * 获取代垫录入列表分页数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public int getReimburseInputListCount(PageMap pageMap);
	
	/**
	 * 新增代垫录入
	 * @param reimburseInput
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public int addReimburseInput(ReimburseInput reimburseInput);
	
	/**
	 * 修改代垫录入
	 * @param reimburseInput
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public int editReimburseInput(ReimburseInput reimburseInput);
	
	/**
	 * 删除代垫录入
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public int deleteReimburseInput(String id);
	
	/**
	 * 获取代垫录入详情
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public ReimburseInput getReimberseInputDetail(Map map);
	
	/**
	 * 根据查询条件获取金额合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public ReimburseInput getReimberseInputSums(PageMap pageMap);
	
	/*--------------------------------代垫统计报表-----------------------------------------*/
	
	/**
	 * 第一层 获取所有供应商列表
	 */
	public List<ReimburseInput> getRISSheetAllSupplier(PageMap pageMap);
	
	/**
	 * 第一层 获取所有供应商列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 27, 2013
	 */
	public int getRISSheetAllSupplierCount(PageMap pageMap);
	
	/**
	 * 第一层 获取总金额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 27, 2013
	 */
	public ReimburseInput getRISSheetAllSum(PageMap pageMap);
	
	/**
	 * 第二层 根据第一层供应商编码获取所有品牌列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 27, 2013
	 */
	public List<ReimburseInput> getRISSheetAllBrandBySupplierid(PageMap pageMap);
	
	/**
	 * 第三层 根据第二层指定供应商下所指定品牌下的所有科目
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 27, 2013
	 */
	public List<ReimburseInput> getRISSheetAllBrandBySupplieridAndBrandid(PageMap pageMap);
	
	/**
	 * 根据指定供应商、品牌、科目条件，获取代垫明细
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 27, 2013
	 */
	public List getRISSheetDetailList(PageMap pageMap);
	
	//---------------------------新 垫付录入-------------------------
	/**
	 * 获取代垫录入列表分页
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public List getMatcostsInputPageList(PageMap pageMap);

	/**
	 * 获取代垫录入列表分页数量
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public int getMatcostsInputListCount(PageMap pageMap);
	
	/**
	 * 根据供应商编码获取代垫金额合计
	 * @param supplierid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 12, 2014
	 */
	public BigDecimal getMatcostsInputActingmatamountSum(String supplierid);
	

	/**
	 * 获取代垫录入列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public List getMatcostsInputList(PageMap pageMap);
	/**
	 * 新增代垫录入（所有字段都可以插入）
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年6月2日
	 */
	public int insertMatcostsInputALL(MatcostsInput matcostsInput);
	
	/**
	 * 新增代垫录入（核销字段除外）
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public int insertMatcostsInput(MatcostsInput matcostsInput);
	
	/**
	 * 修改代垫录入
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public int updateMatcostsInput(MatcostsInput matcostsInput);
	
	/**
	 * 修改代垫录入（所有字段都可以更新）
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public int updateMatcostsInputALL(MatcostsInput matcostsInput);
	
	/**
	 * 删除代垫录入
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public int deleteMatcostsInput(String id);
	/**
	 * 删除代垫录入,权限控制
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public int deleteMatcostsInputByAuth(Map map);
	
	/**
	 * 获取代垫录入详情
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public MatcostsInput getMatcostsInputDetail(Map map);
	
	/**
	 * 获取代垫录入详情
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public MatcostsInput getMatcostsInput(String id);
	/**
	 * 根据map获取代垫录入详情<br/>
	 * map中参数：<br/>
	 * hcreferid:红冲对应的代垫<br/>
	 * sourcefrome:数据来源，0添加，1导入,2流程<br/>
	 * hcflag:红冲标志：0普通单据，1红冲,2被红冲的单据<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public MatcostsInput getMatcostsInputByMap(Map map);
	
	/**
	 * 根据查询条件获取金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map getMatcostsInputSums(PageMap pageMap); 
	/**
	 * 获取代垫汇总统计报表数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Feb 28, 2014
	 */
	public List showMatcostsReportData(PageMap pageMap);
	/**
	 * 获取代垫汇总统计报表数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Feb 28, 2014
	 */
	public int showMatcostsReportDataCount(PageMap pageMap);
	/**
	 * 获取代垫汇总报表数据明细
	 * @param supplierid
	 * @param businessdate1
	 * @param businessdate2
	 * @return
	 * @author chenwei 
	 * @date Feb 28, 2014
	 */
	public List showMatcostsReportDetail(Map map);
	/**
	 * 根据业务日期获取供应商期初金额
	 * @param supplierid
	 * @param businessdate
	 * @return
	 * @author chenwei 
	 * @date Feb 28, 2014
	 */
	public BigDecimal getMatcostsBeginAmountBySupplierid(@Param("supplierid")String supplierid,@Param("businessdate")String businessdate);
	/**
	 * 根据参数查询期初金额<br/>
	 * map 中参数<br/>
	 * businessdate：必填写<br/>
	 * supplierid:供应商<br/>
	 * writeoffstatus:1已核销，2未核销（不含核销和核销中），3核销中，4已经核销和核销中，5除已经核销外（未核销和核销中），其他情况数据为空<br/>
	 * @param map
	 * @return java.math.BigDecimal
	 * @throws
	 * @author zhanghonghui
	 * @date Jul 03, 2017
	 */
	public BigDecimal getMatcostsBeginAmountByMap(Map map);
	/**
	 * 核销代垫
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-5-24
	 */
	public int updateMatcostsInputWriteoff(MatcostsInput matcostsInput);
	/**
	 * 插入代垫收回
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-5-30
	 */
	public int insertMatcostsReimburse(MatcostsInput matcostsInput);
	/**
	 * 更新代垫收回
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-5-30
	 */
	public int updateMatcostsReimburse(MatcostsInput matcostsInput);
	/**
	 * 更新代垫收回方式
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-5-30
	 */
	public int updateMatcostsReimburseType(MatcostsInput matcostsInput);
	/**
	 * 获取代垫收回列表分页
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public List getMatcostsReimbursePageList(PageMap pageMap);

	/**
	 * 获取代垫收回列表分页数量
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public int getMatcostsReimburseListCount(PageMap pageMap);
	/**
	 * 根据查询条件获取代垫收回金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map getMatcostsReimburseSums(PageMap pageMap); 
	/**
	 * 获取代垫收回列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public List getMatcostsReimburseList(PageMap pageMap);
	/**
	 * 添加收回核销记录
	 * @param matcostsInputStatement
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-5-30
	 */
	public int insertMatcostsStatement(MatcostsInputStatement matcostsInputStatement);
	/**
	 * 更新销售记录中收回方式
	 * @param matcostsInputStatement
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-30
	 */
	public int updateMatcostsStatementReiburseType(MatcostsInputStatement matcostsInputStatement);
	/**
	 * 删除收回核销记录
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-5-30
	 */
	public int deleteMatcostsStatement(String id);
	/**
	 * 核销记录列表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-7
	 */
	public List getMatcostsStatementPageList(PageMap pageMap);
	/**
	 * 核销记录列表条数统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-7
	 */
	public int getMatcostsStatementPageCount(PageMap pageMap);
	/**
	 * 核销记录列表合计金额
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-11
	 */
	public Map getMatcostsStatementSums(PageMap pageMap);
	/**
	 * 核销记录列表
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-11
	 */
	public List getMatcostsStatementList(Map map);
	/**
	 * 获取核销记录编码
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-11
	 */
	public List getMatcostsStatementDetail(String id);
	/**
	 * 代垫核销报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-13
	 */
	public List getMatcostsInputWriteoffReportList(PageMap pageMap);

	/**
	 * 代垫核销报表,记录数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-13
	 */
	public int getMatcostsInputWriteoffReportCount(PageMap pageMap);
	/**
	 * 代垫核销报表,合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-13
	 */
	public Map getMatcostsInputWriteoffReportSums(PageMap pageMap);
	/**
	 * 收回核销报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-13
	 */
	public List getMatcostsReimburseWriteoffReportList(PageMap pageMap);

	/**
	 * 收回核销报表,记录数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-13
	 */
	public int getMatcostsReimburseWriteoffReportCount(PageMap pageMap);
	/**
	 * 收回核销报表,记录数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-13
	 */
	public Map getMatcostsReimburseWriteoffReportSums(PageMap pageMap);
	/**
	 * 通过OA编号删除代垫
	 * @param oaid
	 * @return
	 * @author chenwei 
	 * @date 2014年10月24日
	 */
	public int deleteMatcostsInputByOA(@Param("oaid")String oaid);
	
	/**
	 * 根据OA编号查询代垫
	 * @param oaid
	 * @return
	 * @author limin 
	 * @date 2015-1-17
	 */
	public List selectMatcostsInputByOaid(@Param("oaid")String oaid);
	
	/**
	 * 查询代垫收支情况
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2015-1-29
	 */
	public List selectMatcostsBalanceList(PageMap map);
	
	/**
	 * 查询代垫收支情况count
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2015-1-29
	 */
	public int selectMatcostsBalanceListCount(PageMap map);
	
	/**
	 * 查询代垫收支情况合计
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2015-1-30
	 */
	public Map selectMatcostsBalanceSum(PageMap map);
	
	/**
	 * 查询代垫收支明细情况
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2015-1-30
	 */
	public List selectMatcostsBalanceDetailList(PageMap map);
	
	/**
	 * 查询代垫收支明细情况count
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2015-1-30
	 */
	public int selectMatcostsBalanceDetailListCount(PageMap map);
	
	/**
	 * 查询代垫收支明细情况合计
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2015-1-30
	 */
	public Map selectMatcostsBalanceDetailSum(PageMap map);
	/**
	 * 更新代垫被红冲次数,countype 为1 加1，为2时减1
	 * @param id
	 * @param countype
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年5月26日
	 */
	public int updateMatcostsInputHCReferCount(@Param("id")String id,@Param("countype")String countype);

	/**
	 * 获取资金统计报表数据
	 * @param pageMap
	 * @return
	 */
	public List getFundStatisticsSheetList(PageMap pageMap);

	/**
	 * 获取资金统计报表数量
	 * @param pageMap
	 * @return
	 */
	public int getFundStatisticsSheetListCount(PageMap pageMap);

	/**
	 * 获取资金统计报表合计
	 * @param pageMap
	 * @return
	 */
	public FundInput getFundStatisticsSheetListSum(PageMap pageMap);

	/**
	 * 获取上次资金录入信息
	 * @param supplierid
	 * @return
	 */
	public FundInput getLastFundInputSum(@Param("supplierid")String supplierid,@Param("businessdate")String businessdate);

	/**
	 * 获取最大、最小业务日期
	 * @return
	 */
	public Map getMaxDateMinDateFundInput(Map map);
	/**
	 * 代垫打印次数	
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年2月14日
	 */
	public int updateMatcostsInputPrinttimes(MatcostsInput matcostsInput);
	/**
	 * 收回打印次数
	 * @param matcostsInput
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年2月14日
	 */
	public int updateMatcostsReimbursePrinttimes(MatcostsInput matcostsInput);
	/**
	 *
	 * @throws
	 * @author lin_xx
	 * @date 2017-12-01
	 */
	public int updateMatcostsInputVouchertimes(MatcostsInput matcostsInput);
	/**
	 * 根据map中参数查询部门费用<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * deptid: 部门编号<br/>
	 * costsort: 科目<br/>
	 * costsortlike: 模板查询，以costsortlike开头的科目<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年1月27日
	 */
	public List getMatcostsReimburseListBy(Map map);

	/**
	 * 截断资金录入历史数据
	 * @return
	 */
	public int deleteTotalFundInputHis();
	 /**
	  * 根据编码或者代垫录入生成凭证数据
	  * @author lin_xx
	  * @date 2016/12/12
	  */
	public List<Map> getReimberseInputByIds(List<String> idarr);

	/**
	 * 获取代垫录入数据
	 * @param map
	 * @return
	 * @author panxiaoxiao
	 * @date 2017-01-20
	 */
	public List<MatcostsInput> getMatcostsInputListBy(Map map);

	/**
	 * 获取超账期的代垫录入数据
	 * @param supplierid
	 * @return java.util.List<com.hd.agent.journalsheet.model.MatcostsInput>
	 * @throws
	 * @author luoqiang
	 * @date Oct 26, 2017
	 */
	public List<MatcostsInput> getMatcostsNoWriteoffListBySupplierid(@Param("supplierid") String supplierid);

	/**
	 * 修改代垫录入单据的应收日期
	 * @param id
	 * @param duefromdate
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Oct 26, 2017
	 */
	public int updateMatcostsDuefromdateByBillid(@Param("id")String id,@Param("duefromdate")String duefromdate);
}

