/**
 * @(#)IBankAmountService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月24日 chenwei 创建版本
 */
package com.hd.agent.account.service;

import java.math.BigDecimal;
import java.util.List;

import com.hd.agent.account.model.BankAmount;
import com.hd.agent.account.model.BankAmountBegin;
import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 银行账户金额业务service
 * @author chenwei
 */
public interface IBankAmountService {
	/**
	 * 添加银行账户期初
	 * @param bankAmountBegin
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean addBankAmountBegin(BankAmountBegin bankAmountBegin) throws Exception;
	/**
	 * 修改银行账户期初
	 * @param bankAmountBegin
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean editBankAmountBegin(BankAmountBegin bankAmountBegin) throws Exception;
	/**
	 * 获取银行账户期初信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public BankAmountBegin getBankAmountBeginById(String id) throws Exception;
	/**
	 * 删除银行账户期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean deleteBankAmountBegin(String id) throws Exception;
	/**
	 * 获取银行账户期初列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public PageData showBankAmountBeginList(PageMap pageMap) throws Exception;
	/**
	 * 审核银行账户期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean auditBankAmountBegin(String id) throws Exception;
	/**
	 * 反审银行账户期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean oppauditBankAmountBegin(String id) throws Exception;
	/**
	 * 关闭银行账户期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean closeBankAmountBegin(String id) throws Exception;
	
	/**
	 * 获取银行账户借贷单数据列表
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月26日
	 */
	public PageData showBankAmountOthersList(PageMap pageMap) throws Exception;
	/**
	 * 添加银行账户借贷单
	 * @param bankAmountOthers
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public boolean addBankAmountOthers(BankAmountOthers bankAmountOthers) throws Exception;
	/**
	 * 修改银行账户借贷单
	 * @param bankAmountOthers
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public boolean editBankAmountOthers(BankAmountOthers bankAmountOthers) throws Exception;
	/**
	 * 审核银行账户借贷单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public boolean auditBankAmountOthers(String id) throws Exception;
	/**
	 * 反审银行账户借贷单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public boolean oppauditBankAmountOthers(String id) throws Exception;
	/**
	 * 删除银行账户借贷单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public boolean deleteBankAmountOthers(String id) throws Exception;
	/**
	 * 获取银行账户借贷单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public BankAmountOthers getBankAmountOthersByID(String id) throws Exception;
	/**
	 * 关闭银行账户借贷单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public boolean closeBankAmountOthers(String id) throws Exception;
	/**
	 * 获取银行账户余额列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public PageData showBankAmountList(PageMap pageMap) throws Exception;
	/**
	 * 获取银行账户日志列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public PageData showBankAmountLogList(PageMap pageMap) throws Exception;
	/**
	 * 银行账户转账
	 * @param outbankid			转出银行
	 * @param inbankid			转入银行
	 * @param amount			金额
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月16日
	 */
	public boolean addBankAmountTransfer(String outbankid,String inbankid,BigDecimal amount) throws Exception;
	/**
	 * 获取银行账户信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月17日
	 */
	public BankAmount getBankAmountInfo(String id) throws Exception;
}

