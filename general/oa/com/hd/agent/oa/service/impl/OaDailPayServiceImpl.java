/**
 * @(#)OaDailyPayServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-27 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.dao.OaDailPayDetailMapper;
import com.hd.agent.oa.dao.OaDailPayMapper;
import com.hd.agent.oa.model.OaDailyPay;
import com.hd.agent.oa.model.OaDailyPayDetail;
import com.hd.agent.oa.service.IOaDailPayService;

/**
 * 日常费用支付申请单Service 实现类
 * 
 * @author limin
 */
public class OaDailPayServiceImpl extends BaseFilesServiceImpl implements
		IOaDailPayService {

	private OaDailPayMapper oaDailPayMapper;
	
	private OaDailPayDetailMapper oaDailPayDetailMapper;

	public OaDailPayMapper getOaDailPayMapper() {
		return oaDailPayMapper;
	}

	public void setOaDailPayMapper(OaDailPayMapper oaDailPayMapper) {
		this.oaDailPayMapper = oaDailPayMapper;
	}

	public OaDailPayDetailMapper getOaDailPayDetailMapper() {
		return oaDailPayDetailMapper;
	}

	public void setOaDailPayDetailMapper(OaDailPayDetailMapper oaDailPayDetailMapper) {
		this.oaDailPayDetailMapper = oaDailPayDetailMapper;
	}

	@Override
	public int deleteOaDailPay(String id) {

		return oaDailPayMapper.deleteOaDailPay(id);
	}

	@Override
	public int insertOaDailPay(OaDailyPay pay, List list) {

		int ret = oaDailPayMapper.insertOaDailPay(pay);
		
		oaDailPayDetailMapper.deleteOaDailPayDetail(pay.getId());
		for(int i = 0; i < list.size(); i++) {
			
			OaDailyPayDetail detail = (OaDailyPayDetail) list.get(i);
			if(StringUtils.isEmpty(detail.getItemid()) && StringUtils.isEmpty(detail.getItemname())) {
				
				continue;
			}
			
			detail.setBillid(pay.getId());
			oaDailPayDetailMapper.insertOaDailPayDetail(detail);
		}
		
		return ret;
	}

	@Override
	public OaDailyPay selectOaDailPay(String id) {

		return oaDailPayMapper.selectOaDailPay(id);
	}

	@Override
	public int updateOaDailPay(OaDailyPay pay, List list) {

		int ret = oaDailPayMapper.updateOaDailPay(pay);
		
		oaDailPayDetailMapper.deleteOaDailPayDetail(pay.getId());
		for(int i = 0; i < list.size(); i++) {
			
			OaDailyPayDetail detail = (OaDailyPayDetail) list.get(i);
			if(StringUtils.isEmpty(detail.getItemid()) && StringUtils.isEmpty(detail.getItemname())) {
				
				continue;
			}
			
			detail.setBillid(pay.getId());
			oaDailPayDetailMapper.insertOaDailPayDetail(detail);
		}
		
		return ret;
	}

	@Override
	public PageData selectOaDailPayDetailList(PageMap map) {

		List list = oaDailPayDetailMapper.selectOaDailPayDetailList(map);
		int cnt = oaDailPayDetailMapper.selectOaDailPayDetailListCnt(map);
		return new PageData(cnt, list, map);
	}

	@Override
	public List selectOaDailPayDetailListByBillid(String billid) {

		return oaDailPayDetailMapper.selectOaDailPayDetailListByBillid(billid);
	}
}

