/**
 * @(#)ISalesExtService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 8, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.service;

import com.hd.agent.basefiles.model.Customer;

/**
 * 
 * 销售档案对外接口
 * @author zhengziyong
 */
public interface ISalesExtService {

	/**
	 * 回写客户档案(销售额、收款额等等信息)
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 8, 2013
	 */
	public boolean updateCustomerBack(Customer customer) throws Exception;
	
	/**
	 * 获取客户档案回写的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 8, 2013
	 */
	public Customer getCustomerBack(String id) throws Exception;
	
	/**
	 * 获取客户档案总店，如果没有上级则返回自身
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 8, 2013
	 */
	public Customer getHeadCustomer(String id) throws Exception;
}

