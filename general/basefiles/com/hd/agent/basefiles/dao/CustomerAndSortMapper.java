package com.hd.agent.basefiles.dao;

import java.util.List;

import com.hd.agent.basefiles.model.CustomerAndSort;

public interface CustomerAndSortMapper {
    
	public CustomerAndSort getCustomerAndSortDetail(CustomerAndSort sort);
	
	public int deleteCustomerAddSort(String id);
	
	public int deleteCustomerAddSortByCustomer(String id);
	
	public int addCustomerAndSort(CustomerAndSort sort);
	
	public int updateCustomerAndSort(CustomerAndSort sort);
	
	public List getCustomerAndSortListByCustomer(String id);
	
}