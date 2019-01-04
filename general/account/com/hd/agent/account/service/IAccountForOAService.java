/**
 * @(#)IAccountForOAService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月24日 chenwei 创建版本
 */
package com.hd.agent.account.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.account.model.Payorder;

/**
 * 针对OA模块--财务模块接口
 * 
 * @author chenwei
 */
public interface IAccountForOAService {
	/**
	 * 生成供应商付款单
	 * @param payorder			必填属性：supplierid(供应商编号) bank(银行档案编号) oaid(OA编号) prepay（是否预付）
	 * 							amount(付款金额)，invoiceno(采购发票号)，remark(备注)
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean addPayOrder(Payorder payorder) throws Exception;
	/**
	 * 生成银行其他业务单（处理银行账户金额 不包括供应商付款单 客户收款单）
	 * @param bankAmountOthers
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean addBankAmountOthers(BankAmountOthers bankAmountOthers) throws Exception;
	/**
	 * 根据单据号 删除银行其他业务单
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean delteBankAmountOthers(String billid) throws Exception;

	/**
	 * 删除银行支付单（for OA）
	 * @param oaId
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-7
	 */
	public boolean deleteBankAmountOthersByOaId(String oaId) throws Exception;
	
	/**
	 * 查询银行支付单
	 * @param param
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-10
	 */
	public List<BankAmountOthers> selectBankAmountOthersList(Map param) throws Exception;
	
	/**
	 * 根据OA编号删除付款单
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-10
	 */
	public int deletePayOrder(String oaid) throws Exception;
	
	/**
	 * 生成供应商付款单
	 * @param order			          必填属性：supplierid(供应商编号) bank(银行档案编号) oaid(OA编号) prepay（是否预付）
	 * 							amount(付款金额)，invoiceno(采购发票号)，remark(备注)
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-12
	 */
	public boolean rollbackdPayOrder(Payorder order) throws Exception;


    /**
     * 根据oa号查询借贷单
     * @param oaid
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-08
     */
    public List selectBankAmountOthersByOaid(String oaid) throws Exception;
}

