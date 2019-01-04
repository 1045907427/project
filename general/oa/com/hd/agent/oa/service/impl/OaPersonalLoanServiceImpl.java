/**
 * @(#)OaPersonelServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-22 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.oa.dao.OaPersonalLoanMapper;
import com.hd.agent.oa.model.OaPersonalLoan;
import com.hd.agent.oa.service.IOaPersonalLoanService;

/**
 * 个人借款/预付款Service 实现类
 * 
 * @author limin
 */
public class OaPersonalLoanServiceImpl extends BaseFilesServiceImpl implements IOaPersonalLoanService {

	private OaPersonalLoanMapper oaPersonalLoanMapper;

	public OaPersonalLoanMapper getOaPersonalLoanMapper() {
		return oaPersonalLoanMapper;
	}

	public void setOaPersonalLoanMapper(OaPersonalLoanMapper oaPersonalLoanMapper) {
		this.oaPersonalLoanMapper = oaPersonalLoanMapper;
	}

	@Override
	public int insertOaPersonalLoan(OaPersonalLoan loan) {

		return oaPersonalLoanMapper.insertOaPersonalLoan(loan);
	}

	@Override
	public int editOaPersonalLoan(OaPersonalLoan loan) {

		return oaPersonalLoanMapper.editOaPersonalLoan(loan);
	}

	@Override
	public OaPersonalLoan selectOaPersonalLoanInfo(String id) {

		return oaPersonalLoanMapper.selectOaPersonalLoanInfo(id);
	}

	@Override
	public int deleteOaPersonalLoanInfo(String id) {

		return oaPersonalLoanMapper.deleteOaPersonalLoanInfo(id);
	}
}

