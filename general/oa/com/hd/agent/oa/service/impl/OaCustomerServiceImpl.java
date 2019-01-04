/**
 * @(#)OaCustomerServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-14 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.dao.ProcessMapper;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.Settlement;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.dao.OaCustomerBrandMapper;
import com.hd.agent.oa.dao.OaCustomerMapper;
import com.hd.agent.oa.model.OaCustomer;
import com.hd.agent.oa.model.OaCustomerBrand;
import com.hd.agent.oa.service.IOaCustomerService;

/**
 * 
 * 
 * @author limin
 */
public class OaCustomerServiceImpl extends BaseFilesServiceImpl implements IOaCustomerService {

	private OaCustomerMapper oaCustomerMapper;
	
	private OaCustomerBrandMapper oaCustomerBrandMapper;
	
	private ProcessMapper processMapper;

	public OaCustomerMapper getOaCustomerMapper() {
		return oaCustomerMapper;
	}

	public void setOaCustomerMapper(OaCustomerMapper oaCustomerMapper) {
		this.oaCustomerMapper = oaCustomerMapper;
	}

	public OaCustomerBrandMapper getOaCustomerBrandMapper() {
		return oaCustomerBrandMapper;
	}

	public void setOaCustomerBrandMapper(OaCustomerBrandMapper oaCustomerBrandMapper) {
		this.oaCustomerBrandMapper = oaCustomerBrandMapper;
	}

	public ProcessMapper getProcessMapper() {
		return processMapper;
	}

	public void setProcessMapper(ProcessMapper processMapper) {
		this.processMapper = processMapper;
	}

	@Override
	public int selectBarcodenum(String brand) {

		return oaCustomerBrandMapper.selectBarcodenum(brand);
	}

	@Override
	public PageData selectOaCustomerList(PageMap pageMap) {

		return new PageData(oaCustomerMapper.selectOaCustomerListCount(pageMap), oaCustomerMapper.selectOaCustomerList(pageMap), pageMap);
	}

	@Override
	public OaCustomer selectOaCustomer(String id) {

		return oaCustomerMapper.selectOaCustomer(id);
	}

	@Override
	public PageData selectCustomerBrandList(PageMap pageMap) {

		return new PageData(oaCustomerBrandMapper.selectCustomerBrandListCount(pageMap), oaCustomerBrandMapper.selectCustomerBrandList(pageMap), pageMap);
	}

	@Override
	public int insertOaCustomer(OaCustomer customer, List brandList) {

		int ret = oaCustomerMapper.insertOaCustomer(customer);

		for(int i = 0; i < brandList.size(); i++) {
			OaCustomerBrand brand = (OaCustomerBrand)brandList.get(i);
			brand.setBillid(customer.getId());
			oaCustomerBrandMapper.insertCustomerBrand(brand);
		}
		return ret;
	}

	@Override
	public int selectExistedCustomerCount(String customerid, String id) {

		return oaCustomerMapper.selectExistedCustomerCount(customerid, id);
	}

	@Override
	public int selectExistedCustomerNameCount(String customername, String id) {
		return oaCustomerMapper.selectExistedCustomerNameCount(customername, id);
	}

	@Override
	public int updateOaCustomer(OaCustomer customer, List brandList) {

		int ret = oaCustomerMapper.updateOaCustomer(customer);

		oaCustomerBrandMapper.deleteBrandByBillid(customer.getId());

		for(int i = 0; i < brandList.size(); i++) {
			OaCustomerBrand brand = (OaCustomerBrand)brandList.get(i);
			brand.setBillid(customer.getId());
			oaCustomerBrandMapper.insertCustomerBrand(brand);
		}

		return ret;
	}

	@Override
	public int deleteOaCustomer(String id) {

		oaCustomerBrandMapper.deleteBrandByBillid(id);
		return oaCustomerMapper.deleteOaCustomer(id);
	}

	@Override
	public List<Map<String, String>> selectPersonListByBillid(String billid) {

		return oaCustomerMapper.selectPersonListByBillid(billid);
	}

	@Override
	public boolean addPsnBrandCustomer(Map paramMap) {

		String billid = (String) paramMap.get("billid");
		String personids = CommonUtils.nullToEmpty((String) paramMap.get("personids"));
		String[] personArray = personids.split(",");
		
		oaCustomerMapper.deletePersonListByBillid(billid);

		boolean flag = true;
		for(String personid : personArray) {
			
			Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(personid);
			if(personnel != null) {
				int ret = oaCustomerMapper.addPsnBrandAndCustomerFromTbrandAndTcustomerBypsnWithcst(personnel.getId(), billid);
				flag = flag && (ret > 0);
			}
		}
		
		return flag;
	}

    @Override
    public Settlement getSettlement(String id) throws Exception{

        return getSettlementByID(id);
    }
}

