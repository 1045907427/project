package com.hd.agent.oa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaCustomer;

public interface OaCustomerMapper {

	public List selectOaCustomerList(PageMap pageMap);
	
	public int selectOaCustomerListCount(PageMap pageMap);

	/**
	 * 查询新客户登录单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public OaCustomer selectOaCustomer(@Param("id") String id);
	
	/**
	 * 追加新客户登录单
	 * @param customer
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public int insertOaCustomer(OaCustomer customer);

	/**
	 * Check该客户编号是否被占用
	 * @param customerid
	 * @param id
	 * @return
	 * @author limin
	 * @date 2014-9-19
	 */
	public int selectExistedCustomerCount(@Param("customerid") String customerid, @Param("id") String id);

	/**
	 * Check该客户编号是否被占用
	 * @param customerid
	 * @param id
	 * @return
	 * @author limin
	 * @date Feb 6, 2018
	 */
	public int selectExistedCustomerNameCount(@Param("customername") String customername, @Param("id") String id);

	public int updateChkSts(OaCustomer customer);
	
	/**
	 * 更新新客户登录单
	 * @param customer
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public int updateOaCustomer(OaCustomer customer);
	
	/**
	 * 删除新客户登录单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public int deleteOaCustomer(@Param("id") String id);
	
	/**
	 * 根据单据编号删除品牌业务员
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public int deletePersonListByBillid(String billid);
	
	/**
	 * 根据单据编号获取品牌业务员信息
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public List<Map<String, String>> selectPersonListByBillid(String billid);
	
	/**
	 * 根据人员编码、客户编码生成对应客户对应品牌品牌业务员表
	 * @param personid
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-9-19
	 */
	public int addPsnBrandAndCustomerFromTbrandAndTcustomerBypsnWithcst(@Param("personid") String personid, @Param("billid") String billid/*, @Param("customerid")String customerid*/);
}