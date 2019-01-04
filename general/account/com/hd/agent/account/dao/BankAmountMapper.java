package com.hd.agent.account.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.*;
import com.hd.agent.report.model.GoodsOut;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;

/**
 * 银行账户金额dao
 * @author chenwei
 */
public interface BankAmountMapper {
	/**
	 * 添加银行账户期初
	 * @param bankAmountBegin
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int addBankAmountBegin(BankAmountBegin bankAmountBegin);
	/**
	 * 修改银行账户期初
	 * @param bankAmountBegin
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int editBankAmountBegin(BankAmountBegin bankAmountBegin);
	/**
	 * 根据编号获取银行账户期初
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public BankAmountBegin getBankAmountBeginByID(@Param("id")String id);
	/**
	 * 删除银行账户期初
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int deleteBankAmountBegin(@Param("id")String id);
	/**
	 * 获取银行账户期初列表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public List showBankAmountBeginList(PageMap pageMap);
	/**
	 *  获取银行账户期初列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int showBankAmountBeginCount(PageMap pageMap);
	/**
	 * 审核银行账户期初
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int auditBankAmountBegin(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 反审银行账户期初
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int oppauditBankAmountBegin(@Param("id")String id);
	/**
	 * 关闭银行账户期初
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int closeBankAmountBegin(@Param("id")String id);
	
	/*分割线银行账户余额*/
	/**
	 * 根据银行档案编号获取银行账户金额
	 * @param bankid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public BankAmount getBankAmountByBankid(@Param("bankid")String bankid);
	/**
	 * 添加银行账户金额
	 * @param bankAmount
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int addBankAmount(BankAmount bankAmount);
	/**
	 * 修改银行账号金额
	 * @param bankAmount
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int updateBankAmount(BankAmount bankAmount);
	/**
	 * 变更银行账户金额
	 * @param amount
	 * @return
	 * @author chenwei 
	 * @date 2014年12月4日
	 */
	public int updateBankAmountByChange(@Param("bankid")String bankid,@Param("amount")BigDecimal amount);
	
	/*分割线 银行账户金额变更日志*/
	/**
	 * 添加银行账户金额日志
	 * @param bankAmountLog
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int addBankAmountLog(BankAmountLog bankAmountLog);
	
	/*分割线  银行账户其他业务单*/
	/**
	 * 添加银行账号其他业务单
	 * @param bankAmountOthers
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int addBankAmountOthers(BankAmountOthers bankAmountOthers);
	/**
	 * 根据相关单据号 删除银行账户其他业务单
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public int delteBankAmountOthersByBillid(@Param("billid")String billid);
	/**
	 * 根据相关单据编号 获取银行账户其他业务单
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public BankAmountOthers getBankAmountOthersByBillid(@Param("billid")String billid);
	/**
	 * 根据编号获取银行账户借贷单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public BankAmountOthers getBankAmountOthersByID(@Param("id")String id);
	/**
	 * 获取银行借贷单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月26日
	 */
	public List showBankAmountOthersList(PageMap pageMap);
	/**
	 * 获取银行借贷单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月26日
	 */
	public int showBankAmountOthersCount(PageMap pageMap);
	/**
	 * 获取银行借贷单合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2015年1月19日
	 */
	public Map showBankAmountOthersSum(PageMap pageMap);
	/**
	 * 修改银行账户借贷单
	 * @param bankAmountOthers
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public int editBankAmountOthers(BankAmountOthers bankAmountOthers);
	/**
	 * 审核银行账户借贷单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public int auditBankAmountOthers(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 反审银行账户借贷单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public int oppauditBankAmountOthers(@Param("id")String id);
	/**
	 * 删除银行账户借贷单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public int deleteBankAmountOthers(@Param("id")String id);
	/**
	 * 关闭银行账户借贷单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public int closeBankAmountOthers(@Param("id")String id);
	/**
	 * 获取银行账户余额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public List showBankAmountList(PageMap pageMap);
	/**
	 * 获取银行账户余额条数
	 * @param pageMap
	 * @return
	 * @author chenwei
	 * @date 2014年11月27日
	 */
	public int showBankAmountListCount(PageMap pageMap);
	/**
	 * 获取银行账户余额总金额
	 * @param pageMap
	 * @return
	 * @author chenwei
	 * @date 2014年11月27日
	 */
	public BankAmount showBankAmountListSum(PageMap pageMap);
	/**
	 * 获取银行账户日志列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public List showBankAmountLogList(PageMap pageMap);
	/**
	 * 获取银行账户日志数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月27日
	 */
	public int showBankAmountLogCount(PageMap pageMap);
	
	/**
	 * 删除银行支付单
	 * @param oaId
	 * @return
	 * @author limin 
	 * @date 2015-1-7
	 */
	public int deleteBankAmountOthersByOaId(@Param("oaId")String oaId);
	
	/**
	 * 查询OA编号对应的银行支付单
	 * @param param 
	 * @return
	 * @author limin 
	 * @date 2015-1-10
	 */
	public List<BankAmountOthers> selectBankAmountOthersForOA(Map param);
	/**
	 * 添加部门金额
	 * @param deptAmount
	 * @return
	 * @author chenwei 
	 * @date 2015年2月27日
	 */
	public int addDeptAmount(DeptAmount deptAmount);
	/**
	 * 变更部门金额
	 * @param deptid		部门编号
	 * @param amount		变更金额
	 * @return
	 * @author chenwei 
	 * @date 2015年2月27日
	 */
	public int updateDeptAmountChange(@Param("deptid")String deptid,@Param("amount")BigDecimal amount);
	
	/**
	 * 添加部门金额变更日志
	 * @param deptAmountLog
	 * @return
	 * @author chenwei 
	 * @date 2015年2月27日
	 */
	public int addDeptAmountLog(DeptAmountLog deptAmountLog);
	/**
	 * 根据部门编号获取部门金额
	 * @param deptid
	 * @return
	 * @author chenwei 
	 * @date 2015年2月27日
	 */
	public DeptAmount getDeptAmountByDeptid(@Param("deptid")String deptid);

    /**
     * 根据oa号查询银行借贷单
     * @param oaid
     * @return
     */
    public List selectBankAmountOthersByOaid(@Param("oaid")String oaid);
}