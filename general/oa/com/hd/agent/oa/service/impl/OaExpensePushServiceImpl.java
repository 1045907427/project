/**
 * @(#)OaExpensePushServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-7 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.journalsheet.model.CustomerCostPayable;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.oa.dao.OaExpensePushDetailMapper;
import com.hd.agent.oa.dao.OaExpensePushMapper;
import com.hd.agent.oa.model.OaExpensePush;
import com.hd.agent.oa.model.OaExpensePushDetail;
import com.hd.agent.oa.service.IOaExpensePushService;

/**
 * 费用冲差支付申请单Service 实现类
 * 
 * @author limin
 */
public class OaExpensePushServiceImpl extends BaseFilesServiceImpl implements IOaExpensePushService {

	private OaExpensePushMapper oaExpensePushMapper;

	private OaExpensePushDetailMapper oaExpensePushDetailMapper;
	
	/**
	 * 客户应收款冲差单接口
	 */
	private ICustomerPushBanlanceService customerPushBanlanceService;

	public OaExpensePushMapper getOaExpensePushMapper() {
		return oaExpensePushMapper;
	}

	public void setOaExpensePushMapper(OaExpensePushMapper oaExpensePushMapper) {
		this.oaExpensePushMapper = oaExpensePushMapper;
	}

	public OaExpensePushDetailMapper getOaExpensePushDetailMapper() {
		return oaExpensePushDetailMapper;
	}

	public ICustomerPushBanlanceService getCustomerPushBanlanceService() {
		return customerPushBanlanceService;
	}

	public void setCustomerPushBanlanceService(
			ICustomerPushBanlanceService customerPushBanlanceService) {
		this.customerPushBanlanceService = customerPushBanlanceService;
	}

	public void setOaExpensePushDetailMapper(
			OaExpensePushDetailMapper oaExpensePushDetailMapper) {
		this.oaExpensePushDetailMapper = oaExpensePushDetailMapper;
	}

	@Override
	public List<OaExpensePushDetail> selectOaExpensePushDetailList(String billid) throws Exception {

		List<OaExpensePushDetail> list = oaExpensePushDetailMapper.selectOaExpensePushDetailList(billid);
		for(OaExpensePushDetail detail : list) {
			SysCode sysCode = getBaseSysCodeInfo(detail.getPushtype(), "pushtypeprint");
			if (sysCode != null) {
				detail.setPushtypename(sysCode.getCodename());
			}
		}

		return list;
	}

	@Override
	public OaExpensePush selectOaExpensePush(String id) throws Exception {

		OaExpensePush push = oaExpensePushMapper.selectOaExpensePush(id);
		if(push != null && StringUtils.isNotEmpty(push.getSalesdeptid())) {
			DepartMent dept = getDepartmentByDeptid(push.getSalesdeptid());
			if(dept != null) {
				push.setSalesdeptname(dept.getName());
			}
		}
		return push;
	}

	@Override
	public int insertOaExpensePush(OaExpensePush push,
			List<OaExpensePushDetail> list) throws Exception {

		int ret = oaExpensePushMapper.insertOaExpensePush(push);
		
		oaExpensePushDetailMapper.deleteOaExpensePushDetail(push.getId());
		for(OaExpensePushDetail detail : list) {
			
			if(StringUtils.isEmpty(detail.getBrandid())) {
				
				continue;
			}
			/*
			if(StringUtils.isEmpty(detail.getOaid())) {
				
				continue;
			}
			*/
			detail.setBillid(push.getId());
			detail.setCustomerid(push.getCustomerid());
			oaExpensePushDetailMapper.insertOaExpensePushDetail(detail);
		}
		
		return ret;
	}

	@Override
	public int updateOaExpensePush(OaExpensePush push,
			List<OaExpensePushDetail> list) throws Exception {

		int ret = oaExpensePushMapper.updateOaExpensePush(push);
		
		oaExpensePushDetailMapper.deleteOaExpensePushDetail(push.getId());
		for(OaExpensePushDetail detail : list) {
			
			if(StringUtils.isEmpty(detail.getBrandid())) {
				
				continue;
			}

			detail.setBillid(push.getId());
			detail.setCustomerid(push.getCustomerid());
			oaExpensePushDetailMapper.insertOaExpensePushDetail(detail);
		}
		
		return ret;
	}

	@Override
	public int deleteOaExpensePush(String id) throws Exception {

		int ret = oaExpensePushMapper.deleteOaExpensePush(id);
		
		oaExpensePushDetailMapper.deleteOaExpensePushDetail(id);
		
		return ret;
	}
}
