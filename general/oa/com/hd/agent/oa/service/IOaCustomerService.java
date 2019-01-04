/**
 * @(#)IOaCustomerService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-14 limin 创建版本
 */
package com.hd.agent.oa.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.Settlement;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaCustomer;

/**
 * 
 * 
 * @author limin
 */
public interface IOaCustomerService {

	public int selectBarcodenum(String brand);
	
	public PageData selectOaCustomerList(PageMap pageMap);
	
	/**
	 * 查询新客户登录单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public OaCustomer selectOaCustomer(String id);
	
	public PageData selectCustomerBrandList(PageMap pageMap);
	
	/**
	 * 追加新客户登录单
	 * @param customer
	 * @param brandList
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public int insertOaCustomer(OaCustomer customer, List brandList);

	/**
	 * 检查当前客户编号是否被占用
	 * @param customerid
	 * @param id
	 * @return
	 * @author limin
	 * @date 2014-9-19
	 */
	public int selectExistedCustomerCount(String customerid, String id);

	/**
	 * 检查当前客户编号是否被占用
	 * @param customername
	 * @param id
	 * @return
	 * @author limin
	 * @date Feb 6, 2018
	 */
	public int selectExistedCustomerNameCount(String customername, String id);

	/**
	 * 更新新客户登录单
	 * @param customer
	 * @param brandList
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public int updateOaCustomer(OaCustomer customer, List brandList);
	
	/**
	 * 删除新客户登录单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public int deleteOaCustomer(String id);
	
	/**
	 * 根据单据编号获取品牌业务员信息
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public List<Map<String, String>> selectPersonListByBillid(String billid);

	/**
	 * 分配品牌业务员
	 * @param paramMap
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public boolean addPsnBrandCustomer(Map paramMap);

    /**
     * 获取结算方式
     * @param id
     * @return
     * @author limin
     * @date Jul 30, 2015
     */
    public Settlement getSettlement(String id) throws Exception;
}

