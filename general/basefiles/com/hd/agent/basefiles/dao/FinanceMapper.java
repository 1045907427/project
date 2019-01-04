/**
 * @(#)FinanceMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.ExpensesSort;
import com.hd.agent.basefiles.model.Payment;
import com.hd.agent.basefiles.model.Settlement;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface FinanceMapper {

	/*-------------------------------税种档案-----------------------------------------------*/
	
	public List getTaxTypeList(PageMap pageMap);
	
	public int getTaxTypeListCount(PageMap pageMap);
	
	public int addTaxType(TaxType taxType);
	
	public int editTaxType(TaxType taxType);
	
	public int deleteTaxType(String id);
	
	public int enableTaxType(TaxType taxType);
	
	public int disableTaxType(TaxType taxType);
	
	public TaxType getTaxTypeInfo(String id);
	
	public int isUsedName(String name);
	
	public int isUsedRate(String rate);
	
	public List returnTaxIdByName(String name);
	
	/**
	 * 获取税种档案所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getTaxTypeListData();
	/*-------------------------------结算方式-----------------------------------------------*/
	
	public List getSettlementListPage(PageMap pageMap);
	
	public int getSettlementListCountPage(PageMap pageMap);
	
	public Settlement getSettlemetDetail(Map map);
	
	public int isUsedSettlementName(String name);
	
	public int addSettlement(Settlement settlement);
	
	public int editSettlement(Settlement settlement);
	
	public int deleteSettlement(String id);
	
	public int enableSettlement(Settlement settlement);
	
	public int disableSettlement(Settlement settlement);
	
	public List returnSettlementListByName(String name);
	/**
	 * 通过结算方式名称 获取结算方式
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Oct 5, 2013
	 */
	public Settlement getSettlementListByName(@Param("name")String name);
	/**
	 * 获取结算方式所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getSettlementListData();
	
	/*-------------------------------支付方式-----------------------------------------------*/
	
	public List getPaymentListPage(PageMap pageMap);
	
	public int getPaymentListPageCount(PageMap pageMap);
	
	public Payment getPaymentDetail(Map map); 
	
	public int isUsedPaymentName(String name);
	
	public int addPayment(Payment payment);
	
	public int editPayment(Payment payment);
	
	public int deletePayment(String id);
	
	public int enablePayment(Payment payment);
	
	public int disablePayment(Payment payment);
	
	/**
	 * 获取支付方式所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getPaymentListData();
	
	/*-------------------------------费用分类-----------------------------------------------*/
	
	public List getExpensesSortList(PageMap pageMap);
	
	public ExpensesSort getExpensesSortDetail(Map map);
	
	public List getExpensesSortParentAllChildren(PageMap pageMap);
	
	public int deleteExpensesSort(String id);
	
	public int addExpensesSort(ExpensesSort expensesSort);
	
	public int editExpensesSort(ExpensesSort expensesSort);
	
	public int enableExpensesSort(ExpensesSort expensesSort);
	
	public int disableExpensesSort(ExpensesSort expensesSort);
	
	public int isUsedExpensesSortName(String name);
	
	public String isLeafExpensesSort(String id);
	
	public List getExpensesSortByStateList();
	
	/**
	 * 获取费用分类所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getExpensesSortListData();
	
	/**
	 * 根据父级编码获取所有下属费用分类列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public List getExpensesSortChildList(String pid);
	
	/**
	 * 批量修改费用分类
	 * @param childList
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public int editExpensesSortBatch(List<ExpensesSort> childList);
	
	/*---------------------银行档案-------------------*/
	
	/**
	 * 获取银行档案列表数据分页
	 */
	public List getBankListPage(PageMap pageMap);
	
	/**
	 * 获取银行档案列表数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int getBankCountPage(PageMap pageMap);
	
	/**
	 * 获取银行档案详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public Bank getBankDetail(String id);

    /**
     * 根据银行名称获取银行档案
     * @return
     * @author lin_xx
     * @date Jan 13, 2016
     */
    public Bank getBankByName(String name);
	
	/**
	 * 新增银行档案
	 * @param bank
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int addBank(Bank bank);
	
	/**
	 * 修改银行档案
	 * @param bank
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int editBank(Bank bank);
	
	/**
	 * 删除银行档案
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int deleteBank(String id);
	
	/**
	 * 启用银行档案
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int openBank(Map map);
	
	/**
	 * 禁用银行档案
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int closeBank(Map map);
	
	/**
	 * 验证银行编码是否重复
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int checkBankidUserd(String id);
	
	/**
	 * 验证银行名称是否重复
	 * @param account
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int checkBandAccountUserd(String account);
	/**
	 * 获取启用银行档案列表
	 * @return
	 * @author chenwei 
	 * @date Aug 23, 2013
	 */
	public List getBankList();
	/**
	 * 获取全部银行档案列表
	 * @return
	 * @author chenwei 
	 * @date Dec 20, 2013
	 */
	public List getAllBankList();
	/**
	 * 获取支付方式、结算方式列表
	 * @return
	 * @author zhengziyong 
	 * @date Aug 17, 2013
	 */
	public List getSameFinanceList();
	
	public List returnPaymentIdByName(String name);

    /**
     * 获取名称为空，上级编码不为空的商品分类
     * @return
     * @author panxiaoxiao
     * @date 2015-03-18
     */
    public List<ExpensesSort> getExpensesSortWithoutName();
}

