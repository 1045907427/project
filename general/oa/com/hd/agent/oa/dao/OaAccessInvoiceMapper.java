package com.hd.agent.oa.dao;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.oa.model.OaAccessInvoice;

public interface OaAccessInvoiceMapper {
	
	/**
	 * 费用特价通路单支票支付信息登录
	 * @param invoice
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public int insertOaAccessInvoice(OaAccessInvoice invoice);
	
	/**
	 * 费用特价通路单支票支付信息修改
	 * @param invoice
	 * @return
	 * @author limin 
	 * @date 2014-10-5
	 */
	public int updateOaAccessInvoice(OaAccessInvoice invoice);
	
	/**
	 * 费用特价通路单支票支付信息查询
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-10-7
	 */
	public OaAccessInvoice selectOaAccessInvoice(@Param("billid") String billid);
}