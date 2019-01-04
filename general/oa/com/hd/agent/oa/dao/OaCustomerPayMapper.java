/**
 * @(#)CustomerPayMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-17 limin 创建版本
 */
package com.hd.agent.oa.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.oa.model.OaCustomerPay;

/**
 * 客户费用支付申请单Mapper
 * 
 * @author limin
 */
public interface OaCustomerPayMapper {

	/**
	 * 查询客户费用申请单
	 * @param id 客户费用申请单编号
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public OaCustomerPay selectCustomerPayInfo(@Param("id") String id);
	
	/**
	 * 添加客户费用申请单
	 * @param customerpay 客户费用申请单
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public int insertOaCustomerPayInfo(OaCustomerPay customerpay);
	
	/**
	 * 编辑客户费用申请单
	 * @param customerpay 客户费用申请单
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public int updateOaCustomerPayInfo(OaCustomerPay customerpay);
	
	/**
	 * 获取品牌对应的销售金额
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-11-20
	 */
	public String getCustomerBrandSalesAmount(Map map);
}

