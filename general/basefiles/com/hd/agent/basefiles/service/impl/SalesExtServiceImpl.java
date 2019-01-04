/**
 * @(#)SalesExtServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 8, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.dao.CustomerMapper;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.service.ISalesExtService;
import com.hd.agent.common.service.impl.BaseServiceImpl;

/**
 * 
 * 
 * @author zhengziyong
 */
public class SalesExtServiceImpl extends BaseServiceImpl implements ISalesExtService {

	private CustomerMapper customerMapper;

	public CustomerMapper getCustomerMapper() {
		return customerMapper;
	}

	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	@Override
	public boolean updateCustomerBack(Customer customer) throws Exception {
		return customerMapper.updateCustomerBack(customer)>0;
	}

	@Override
	public Customer getCustomerBack(String id) throws Exception {
		return customerMapper.getCustomerBack(id);
	}
	
	@Override
	public Customer getHeadCustomer(String id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		Customer customer = customerMapper.getCustomerDetail(map);
		if(customer != null){
			if(StringUtils.isNotEmpty(customer.getPid())){
				map.put("id", customer.getPid());
				Customer customer2 = customerMapper.getCustomerDetail(map);
				return customer2;
			}
			else{
				return customer;
			}
		}
		return null;
	}
	
}

