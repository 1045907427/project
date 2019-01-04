/**
 * @(#)IOaPersonelService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-22 limin 创建版本
 */
package com.hd.agent.oa.service;

import com.hd.agent.oa.model.OaPersonalLoan;

/**
 * 个人借款/预付款Service
 * 
 * @author limin
 */
public interface IOaPersonalLoanService {

	/**
	 * 添加个人借款/预付款申请单
	 * @param loan 个人借款/预付款申请单
	 * @return
	 * @author limin 
	 * @date 2014-11-22
	 */
	public int insertOaPersonalLoan(OaPersonalLoan loan);
	
	/**
	 * 修改个人借款/预付款申请单
	 * @param loan 个人借款/预付款申请单
	 * @return
	 * @author limin 
	 * @date 2014-11-22
	 */
	public int editOaPersonalLoan(OaPersonalLoan loan);
	
	/**
	 * 查询个人借款/预付款申请单
	 * @param id 个人借款/预付款申请单编号
	 * @return 个人借款/预付款申请单
	 * @author limin 
	 * @date 2014-11-22
	 */
	public OaPersonalLoan selectOaPersonalLoanInfo(String id);
	
	/**
	 * 删除个人借款/预付款申请单
	 * @param id 个人借款/预付款申请单编号
	 * @return
	 * @author limin 
	 * @date 2014-11-22
	 */
	public int deleteOaPersonalLoanInfo(String id);
}

