/**
 * @(#)OaDailyPayMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-27 limin 创建版本
 */
package com.hd.agent.oa.dao;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.oa.model.OaDailyPay;

/**
 * 日常费用支付申请单Mapper
 * 
 * @author limin
 */
public interface OaDailPayMapper {

	/**
	 * 查询更新日常费用支付申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-27
	 */
	public OaDailyPay selectOaDailPay(@Param("id") String id);
	
	/**
	 * 删除更新日常费用支付申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-27
	 */
	public int deleteOaDailPay(@Param("id") String id);
	
	/**
	 * 添加更新日常费用支付申请单
	 * @param pay
	 * @return
	 * @author limin 
	 * @date 2014-11-27
	 */
	public int insertOaDailPay(OaDailyPay pay);
	
	/**
	 * 更新日常费用支付申请单
	 * @param pay
	 * @return
	 * @author limin 
	 * @date 2014-11-27
	 */
	public int updateOaDailPay(OaDailyPay pay);
}

