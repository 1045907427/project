/**
 * @(#)OaCustomerPayServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-17 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.dao.OaCustomerPayDetailMapper;
import com.hd.agent.oa.dao.OaCustomerPayMapper;
import com.hd.agent.oa.model.OaCustomerPay;
import com.hd.agent.oa.model.OaCustomerPayDetail;
import com.hd.agent.oa.service.IOaCustomerPayService;

/**
 * 客户费用支付申请单Service 实现类
 * 
 * @author limin
 */
public class OaCustomerPayServiceImpl extends BaseFilesServiceImpl implements IOaCustomerPayService {

	private OaCustomerPayMapper oaCustomerPayMapper;
	
	private OaCustomerPayDetailMapper oaCustomerPayDetailMapper;

	public OaCustomerPayMapper getOaCustomerPayMapper() {
		return oaCustomerPayMapper;
	}

	public void setOaCustomerPayMapper(OaCustomerPayMapper oaCustomerPayMapper) {
		this.oaCustomerPayMapper = oaCustomerPayMapper;
	}

	public OaCustomerPayDetailMapper getOaCustomerPayDetailMapper() {
		return oaCustomerPayDetailMapper;
	}

	public void setOaCustomerPayDetailMapper(
			OaCustomerPayDetailMapper oaCustomerPayDetailMapper) {
		this.oaCustomerPayDetailMapper = oaCustomerPayDetailMapper;
	}

	@Override
	public OaCustomerPay selectCustomerPayInfo(String id) {

		return oaCustomerPayMapper.selectCustomerPayInfo(id);
	}

	@Override
	public PageData selectOaCustomerPayDetailList(PageMap map) {

		List<OaCustomerPayDetail> list = oaCustomerPayDetailMapper.selectOaCustomerPayDetailList(map);
		int cnt = oaCustomerPayDetailMapper.selectOaCustomerPayDetailListCnt(map);
		
		return new PageData(cnt, list, map);
	}

	@Override
	public int addOaCustomerPay(OaCustomerPay customerpay,
			List<OaCustomerPayDetail> detailList) {

		int ret = oaCustomerPayMapper.insertOaCustomerPayInfo(customerpay);
		
		oaCustomerPayDetailMapper.deleteOaCustomerPayDetail(customerpay.getId());
		for(OaCustomerPayDetail detail : detailList) {
			
			if(StringUtils.isEmpty(detail.getBrandid())) {
				continue;
			}
			
			detail.setBillid(customerpay.getId());
			oaCustomerPayDetailMapper.insertOaCustomerPayDetail(detail);
		}
		
		return ret;
	}

	@Override
	public int editOaCustomerPay(OaCustomerPay customerpay,
			List<OaCustomerPayDetail> detailList) {

		int ret = oaCustomerPayMapper.updateOaCustomerPayInfo(customerpay);
		
		oaCustomerPayDetailMapper.deleteOaCustomerPayDetail(customerpay.getId());
		for(OaCustomerPayDetail detail : detailList) {
			
			if(StringUtils.isEmpty(detail.getBrandid())) {
				continue;
			}
			
			detail.setBillid(customerpay.getId());
			oaCustomerPayDetailMapper.insertOaCustomerPayDetail(detail);
		}
		
		return ret;
	}

	@Override
	public String getCustomerBrandSalesAmount(Map map) throws Exception {

		return oaCustomerPayMapper.getCustomerBrandSalesAmount(map);
	}

    @Override
    public List selectOaCustomerPayDetailList(String billid) {
        return oaCustomerPayDetailMapper.selectCustomerPayDetailInfo(billid);
    }
}

