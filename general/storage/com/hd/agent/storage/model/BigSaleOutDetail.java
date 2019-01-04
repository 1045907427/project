/**
 * @(#)BigSaleOutDetail.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 12, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.storage.model;

import java.io.Serializable;

/**
 * 大胆发货单明细
 * 
 * @author panxiaoxiao
 */
public class BigSaleOutDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 明细编码
	 */
	private Integer id;
	
	/**
	 * 单据编号
	 */
	private String billid;
	
	/**
	 * 发货单编号
	 */
	private String saleoutid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getSaleoutid() {
		return saleoutid;
	}

	public void setSaleoutid(String saleoutid) {
		this.saleoutid = saleoutid;
	}
}

