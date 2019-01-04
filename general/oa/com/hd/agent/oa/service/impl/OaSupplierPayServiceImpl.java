/**
 * @(#)OaSupplierPayServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-29 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.oa.dao.OaSupplierPayMapper;
import com.hd.agent.oa.model.OaSupplierPay;
import com.hd.agent.oa.service.IOaSupplierPayService;

/**
 * 货款支付申请单Service 实现类
 * 
 * @author limin
 */
public class OaSupplierPayServiceImpl extends BaseFilesServiceImpl implements
		IOaSupplierPayService {

	private OaSupplierPayMapper oaSupplierPayMapper;
	
	public OaSupplierPayMapper getOaSupplierPayMapper() {
		return oaSupplierPayMapper;
	}

	public void setOaSupplierPayMapper(OaSupplierPayMapper oaSupplierPayMapper) {
		this.oaSupplierPayMapper = oaSupplierPayMapper;
	}

	@Override
	public int insertOaSupplierPay(OaSupplierPay pay) {

		return oaSupplierPayMapper.insertOaSupplierPay(pay);
	}

	@Override
	public OaSupplierPay selectSupplierPay(String id) {

		return oaSupplierPayMapper.selectOaSupplierPay(id);
	}

	@Override
	public int updateOaSupplierPay(OaSupplierPay pay) {

		return oaSupplierPayMapper.updateOaSupplierPay(pay);
	}

}

