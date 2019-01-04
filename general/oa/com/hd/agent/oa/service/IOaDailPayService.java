/**
 * @(#)IOaDailyPayService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-27 limin 创建版本
 */
package com.hd.agent.oa.service;

import java.util.List;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaDailyPay;

/**
 * 日常费用支付申请单Service
 * 
 * @author limin
 */
public interface IOaDailPayService {

	/**
	 * 查询日常费用支付申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-28
	 */
	public OaDailyPay selectOaDailPay(String id);
	
	/**
	 * 删除日常费用支付申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-28
	 */
	public int deleteOaDailPay(String id);

	/**
	 * 添加日常费用支付申请单
	 * @param pay
	 * @param list
	 * @return
	 * @author limin 
	 * @date 2014-11-28
	 */
	public int insertOaDailPay(OaDailyPay pay, List list);
	
	/**
	 * 更新日常费用支付申请单
	 * @param pay
	 * @param list
	 * @return
	 * @author limin 
	 * @date 2014-11-28
	 */
	public int updateOaDailPay(OaDailyPay pay, List list);
	
	/**
	 * 查询日常费用支付单明细
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-11-28
	 */
	public PageData selectOaDailPayDetailList(PageMap map);
	
	/**
	 * 查询日常费用支付单明细List
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-11-29
	 */
	public List selectOaDailPayDetailListByBillid(String billid);
}

