/**
 * @(#)ReceiptHand.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 24, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import java.util.Date;

/**
 *  应收款交接单model
 * 
 * @author panxiaoxiao
 */
public class ReceiptHand implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 删除的客户明细客户编码字符串集合
	 */
	private String delcustomerids;
	
	/**
	 * 交接单编号
	 */
	private String id;
	
	/**
	 * 交接日期
	 */
	private String businessdate;
	
	/**
	 * 状态
	 */
	private String status;
	
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
	 * 制单人部门
	 */
	private String adddeptid;
	
	/**
	 * 制单人部门名称
	 */
	private String adddeptname;
	
	/**
	 * 制单时间
	 */
	private Date addtime;
	
	/**
	 * 修改人编码
	 */
	private String modifyuserid;
	
	/**
	 * 修改人名称
	 */
	private String modifyusername;
	
	/**
	 * 修改人时间
	 */
	private Date modifytime;
	
	/**
	 * 审核人编码
	 */
	private String audituserid;
	
	/**
	 * 审核人名称
	 */
	private String auditusername;
	
	/**
	 * 审核人时间
	 */
	private Date audittime;
	
	/**
	 * 中止人
	 */
	private String stopuserid;
	
	/**
	 * 中止人名称
	 */
	private String stopusername;
	
	/**
	 * 中止时间
	 */
	private Date stoptime;
	
	/**
	 * 关闭时间
	 */
	private Date closetime;
	
	/**
	 * 打印次数
	 */
	private Integer printtimes;
	
	/**
	 * 领单人编号
	 */
	private String handuserid;
	
	/**
	 * 领单人名称
	 */
	private String handusername;
	
	/**
	 * 领单人所属部门
	 */
	private String handdeptid;
	
	/**
	 * 领单人所属部门名称
	 */
	private String handdeptname;
	
	/**
	 * 客户家数
	 */
	private Integer cnums;
	
	/**
	 * 应收金额
	 */
	private BigDecimal totalamount;
	
	/**
	 * 收款金额
	 */
	private BigDecimal collectamount;
	
	/**
	 *  未收款金额
	 */
	private BigDecimal uncollectamount;
	
	/**
	 * 客户列表明细
	 */
	private List customerlist;
	
	/**
	 * 单据列表明细
	 */
	private List billlist;
	
	/**
	 * 单据数
	 */
	private Integer billnums;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessdate() {
		return businessdate;
	}

	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAdddeptid() {
		return adddeptid;
	}

	public void setAdddeptid(String adddeptid) {
		this.adddeptid = adddeptid;
	}

	public String getAdddeptname() {
		return adddeptname;
	}

	public void setAdddeptname(String adddeptname) {
		this.adddeptname = adddeptname;
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

	public String getAudituserid() {
		return audituserid;
	}

	public void setAudituserid(String audituserid) {
		this.audituserid = audituserid;
	}

	public String getAuditusername() {
		return auditusername;
	}

	public void setAuditusername(String auditusername) {
		this.auditusername = auditusername;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public String getStopuserid() {
		return stopuserid;
	}

	public void setStopuserid(String stopuserid) {
		this.stopuserid = stopuserid;
	}

	public String getStopusername() {
		return stopusername;
	}

	public void setStopusername(String stopusername) {
		this.stopusername = stopusername;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStoptime() {
		return stoptime;
	}

	public void setStoptime(Date stoptime) {
		this.stoptime = stoptime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getClosetime() {
		return closetime;
	}

	public void setClosetime(Date closetime) {
		this.closetime = closetime;
	}

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public String getHanduserid() {
		return handuserid;
	}

	public void setHanduserid(String handuserid) {
		this.handuserid = handuserid;
	}

	public String getHanddeptid() {
		return handdeptid;
	}

	public void setHanddeptid(String handdeptid) {
		this.handdeptid = handdeptid;
	}

	public Integer getCnums() {
		return cnums;
	}

	public void setCnums(Integer cnums) {
		this.cnums = cnums;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public BigDecimal getCollectamount() {
		return collectamount;
	}

	public void setCollectamount(BigDecimal collectamount) {
		this.collectamount = collectamount;
	}

	public BigDecimal getUncollectamount() {
		return uncollectamount;
	}

	public void setUncollectamount(BigDecimal uncollectamount) {
		this.uncollectamount = uncollectamount;
	}

	public List getCustomerlist() {
		return customerlist;
	}

	public void setCustomerlist(List customerlist) {
		this.customerlist = customerlist;
	}

	public List getBilllist() {
		return billlist;
	}

	public void setBilllist(List billlist) {
		this.billlist = billlist;
	}

	public String getHandusername() {
		return handusername;
	}

	public void setHandusername(String handusername) {
		this.handusername = handusername;
	}

	public String getHanddeptname() {
		return handdeptname;
	}

	public void setHanddeptname(String handdeptname) {
		this.handdeptname = handdeptname;
	}

	public Integer getBillnums() {
		return billnums;
	}

	public void setBillnums(Integer billnums) {
		this.billnums = (null != billnums) ? billnums : 0;
	}

	public String getDelcustomerids() {
		return delcustomerids;
	}

	public void setDelcustomerids(String delcustomerids) {
		this.delcustomerids = delcustomerids;
	}
}

