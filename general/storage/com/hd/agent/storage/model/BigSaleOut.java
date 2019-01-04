/**
 * @(#)LargeSingle.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 11, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.storage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * 大单发货 model
 * 
 * @author panxiaoxiao
 */
public class BigSaleOut implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号
	 */
	private String id;
	
	/**
	 * 业务日期
	 */
	private String businessdate;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 状态名称
	 */
	private String statusname;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 制单人编号
	 */
	private String adduserid;
	
	/**
	 * 制单人姓名
	 */
	private String addusername;
	
	/**
	 * 制单人部门编号
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
	
	/**
	 * 审核人编号
	 */
	private String audituserid;
	
	/**
	 * 审核人名称
	 */
	private String auditusername;
	
	/**
	 * 审核时间
	 */
	private Date audittime;
	
	/**
	 * 中止人编号
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
	 * 仓库编码
	 */
	private String storageid;
	
	/**
	 * 仓库名称
	 */
	private String storagename;
	
	/**
	 * 大单发货单明细列表
	 */
	private List<BigSaleOutDetail> sourceList;
	
	/**
	 * 大单发货单商品明细
	 */
	private List<SaleoutDetail> goodsList;

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

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
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

	public String getStorageid() {
		return storageid;
	}

	public void setStorageid(String storageid) {
		this.storageid = storageid;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public List<BigSaleOutDetail> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<BigSaleOutDetail> sourceList) {
		this.sourceList = sourceList;
	}

	public List<SaleoutDetail> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<SaleoutDetail> goodsList) {
		this.goodsList = goodsList;
	}

}

