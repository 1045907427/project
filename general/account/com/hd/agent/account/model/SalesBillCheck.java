/**
 * @(#)SalesBillCheck.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 14, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 销售单据核对
 * @author panxiaoxiao
 */
public class SalesBillCheck {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编码
	 */
	private String id;
	
	/**
	 * 客户编码
	 */
	private String customerid;
	
	/**
	 * 客户名称
	 */
	private String customername;
	
	/**
	 * 业务日期
	 */
	private String businessdate;
	
	/**
	 * 销售金额
	 */
	private BigDecimal salesamount;
	
	/**
	 * 单据数
	 */
	private Integer billnums;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 制单人编号
	 */
	private String adduserid;
	
	/**
	 * 制单人名称
	 */
	private String addusername;
	
	/**
	 * 制单时间
	 */
	private Date addtime;
	
	/**
	 * 最后修改人编号
	 */
	private String modifyuserid;
	
	/**
	 * 最后修改人名称
	 */
	private String modifyusername;
	
	/**
	 * 最后修改时间
	 */
	private Date modifytime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getBusinessdate() {
		return businessdate;
	}

	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}

	public BigDecimal getSalesamount() {
		return salesamount;
	}

	public void setSalesamount(BigDecimal salesamount) {
		this.salesamount = salesamount;
	}

	public Integer getBillnums() {
		return billnums;
	}

	public void setBillnums(Integer billnums) {
		this.billnums = billnums;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdduserid() {
		return adduserid;
	}

	public void setAdduserid(String adduserid) {
		this.adduserid = adduserid;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getModifyuserid() {
		return modifyuserid;
	}

	public void setModifyuserid(String modifyuserid) {
		this.modifyuserid = modifyuserid;
	}

	public String getModifyusername() {
		return modifyusername;
	}

	public void setModifyusername(String modifyusername) {
		this.modifyusername = modifyusername;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
}

