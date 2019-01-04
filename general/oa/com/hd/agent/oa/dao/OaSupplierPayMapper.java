/**
 * @(#)OaSupplierPayMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-29 limin 创建版本
 */
package com.hd.agent.oa.dao;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.oa.model.OaSupplierPay;

/**
 * 货款支付申请单Mapper
 * 
 * @author limin
 */
public interface OaSupplierPayMapper {

	/**
	 * 查询货款支付申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-29
	 */
	public OaSupplierPay selectOaSupplierPay(@Param("id") String id);
	
	/**
	 * 删除货款支付申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-29
	 */
	public int deleteOaSupplierPay(@Param("id") String id);
	
	/**
	 * 添加货款支付申请单
	 * @param pay
	 * @return
	 * @author limin 
	 * @date 2014-11-29
	 */
	public int insertOaSupplierPay(OaSupplierPay pay);
	
	/**
	 * 更新货款支付申请单
	 * @param pay
	 * @return
	 * @author limin 
	 * @date 2014-11-29
	 */
	public int updateOaSupplierPay(OaSupplierPay pay);
}

