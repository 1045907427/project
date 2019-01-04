/**
 * @(#)IFinanceService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.ExpensesSort;
import com.hd.agent.basefiles.model.Payment;
import com.hd.agent.basefiles.model.Settlement;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface IFinanceService {

	/*-----------------------------------税种档案-------------------------------------------*/
	
	/**
	 * 获取税种列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public PageData getTaxTypeList(PageMap pageMap)throws Exception;
	
	/**
	 * 新增税种
	 * @param taxType
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public boolean addTaxType(TaxType taxType)throws Exception;
	
	/**
	 * 修改税种
	 * @param taxType
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public boolean editTaxType(TaxType taxType)throws Exception;
	
	/**
	 * 删除税种
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public boolean deleteTaxType(String id)throws Exception;
	
	/**
	 * 启用税种
	 * @param taxType
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public boolean enableTaxType(TaxType taxType)throws Exception;
	
	/**
	 * 禁用税种
	 * @param taxType
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public boolean disableTaxType(TaxType taxType)throws Exception;
	
	/**
	 * 获取税种详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public TaxType getTaxTypeInfo(String id)throws Exception;
	
	/**
	 * 名称是否被使用,true 已使用，false 未使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public boolean isUsedName(String name)throws Exception;
	
	/**
	 * 税率是否被使用，true已使用，false 未使用
	 * @param rate
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public boolean isUsedRate(String rate)throws Exception;
	
	/**
	 * 新增导入的税种
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public Map addDRTaxType(List<TaxType> list)throws Exception;
	
	/*-----------------------------------结算方式-------------------------------------------*/
	
	/**
	 * 获取结算方式列表
	 */
	public PageData getSettlementListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 获取结算方式详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public Settlement getSettlemetDetail(String id)throws Exception;
	
	/**
	 * 验证结算方式名称是否被使用，true 被使用，false 未被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean isUsedSettlementName(String name)throws Exception;
	
	/**
	 * 新增结算方式
	 * @param settlement
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean addSettlement(Settlement settlement)throws Exception;
	
	/**
	 * 修改结算方式
	 * @param settlement
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean editSettlement(Settlement settlement)throws Exception;
	
	/**
	 * 删除结算方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean deleteSettlement(String id)throws Exception;
	
	/**
	 * 启用结算方式
	 * @param settlement
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean enableSettlement(Settlement settlement)throws Exception;
	
	/**
	 * 禁用结算方式
	 * @param settlement
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean disableSettlement(Settlement settlement)throws Exception;
	
	/**
	 * 导入新增
	 * @param settlement
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public Map addDRSettlement(List<Settlement> list)throws Exception;
	
	/*-----------------------------------支付方式-------------------------------------------*/
	
	/**
	 * 获取支付方式列表
	 * @param pageMap
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public PageData getPaymentListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 获取支付方式详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public Payment getPaymentDetail(String id)throws Exception; 
	
	/**
	 * 验证支付方式名称是否被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public boolean isUsedPaymentName(String name)throws Exception;
	
	/**
	 * 新增支付方式
	 * @param payment
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public boolean addPayment(Payment payment)throws Exception;
	
	/**
	 * 修改支付方式
	 * @param payment
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public boolean editPayment(Payment payment)throws Exception;
	
	/**
	 * 删除支付方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public boolean deletePayment(String id)throws Exception;
	
	/**
	 * 启用支付方式
	 * @param payment
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public boolean enablePayment(Payment payment)throws Exception;
	
	/**
	 * 禁用支付方式
	 * @param payment
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public boolean disablePayment(Payment payment)throws Exception;
	
	public Map addDRPayment(List<Payment> list)throws Exception;
	
	/*-----------------------------------费用分类-------------------------------------------*/
	
	/**
	 * 获取费用分类列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public List getExpensesSortList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取费用分类详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public ExpensesSort getExpensesSortDetail(String id)throws Exception;
	
	/**
	 * 获取父节点及其全部子节点
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public List getExpensesSortParentAllChildren(PageMap pageMap)throws Exception;
	
	/**
	 * 删除费用分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean deleteExpensesSort(String id)throws Exception;
	
	/**
	 * 新增费用分类
	 * @param expensesSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean addExpensesSort(ExpensesSort expensesSort)throws Exception;
	
	/**
	 * 修改费用分类
	 * @param expensesSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public Map editExpensesSort(ExpensesSort expensesSort)throws Exception;
	
	/**
	 * 启用费用分类
	 * @param expensesSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean enableExpensesSort(ExpensesSort expensesSort)throws Exception;
	
	/**
	 * 禁用费用分类
	 * @param expensesSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean disableExpensesSort(ExpensesSort expensesSort)throws Exception;
	
	/**
	 * 验证费用分类名称是否被使用,true 被使用,false 未被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean isUsedExpensesSortName(String name)throws Exception;
	
	/*-----------------接口调用----------------------------------------------*/
	
	/**
	 * 获取税种档案所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getTaxTypeListData()throws Exception;
	
	/**
	 * 获取结算方式所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getSettlementListData()throws Exception;
	
	/**
	 * 获取支付方式所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getPaymentListData()throws Exception;
	
	/**
	 * 获取费用分类所有列表数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getExpensesSortListData()throws Exception;
	
	/*--------------------银行档案-------------------------------*/
	
	/**
	 * 获取银行档案列表数据分页
	 */
	public PageData getBankList(PageMap pageMap)throws Exception;

    /**
     * 根据银行名称获取银行档案
     * @param name
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Jan 13, 2016
     */
    public Bank getBankInfoByName(String name) throws Exception;
	
	/**
	 * 获取银行档案详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public Bank getBankDetail(String id)throws Exception;
	
	/**
	 * 新增银行档案
	 * @param bank
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public boolean addBank(Bank bank)throws Exception;
	
	/**
	 * 修改银行档案
	 * @param bank
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public boolean editBank(Bank bank)throws Exception;
	
	/**
	 * 删除银行档案
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public boolean deleteBank(String id)throws Exception;
	
	/**
	 * 启用银行档案
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public boolean openBank(String id)throws Exception;
	
	/**
	 * 禁用银行档案
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public boolean closeBank(String id)throws Exception;
	
	/**
	 * 验证银行编码是否重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public boolean checkBankidUserd(String id)throws Exception;
	
	/**
	 * 验证银行名称是否重复
	 * @param account
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public boolean checkBandAccountUserd(String account)throws Exception;
	
	/**
	 * 新增导入的银行档案
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 12, 2013
	 */
	public Map addDRbank(List<Bank> list)throws Exception;
	/**
	 * 获取银行档案列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 23, 2013
	 */
	public List getBankList() throws Exception;
	/**
	 * 获取全部银行档案列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 20, 2013
	 */
	public List getAllBankList() throws Exception;

    /**
     * Excel费用分类导入列表数据
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-18
     */
    public Map addDRExpensesSortExcel(List<ExpensesSort> list)throws Exception;
}

