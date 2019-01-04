/**
 * @(#)MatcostsInputStatement.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-5-30 zhanghonghui 创建版本
 */
package com.hd.agent.journalsheet.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class MatcostsInputStatement {
	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 代垫编号
	 */
	private String payid;
	/**
	 * 收回编号
	 */
	private String takebackid;
	/**
	 * 核销日期
	 */
	private String writeoffdate;
	/**
	 * 是否核销1是0否
	 */
	private String iswriteoff;
	/**
	 * 收回方式
	 */
	private String reimbursetype;
	/**
	 * 工厂投入
	 */
	private BigDecimal factoryamount;
	/**
	 * 本次核销工厂投入未核销余额
	 */
	private BigDecimal remainderamount;
	/**
	 * 关联金额（本次核销金额）
	 */
	private BigDecimal relateamount;
	/**
	 * 添加日期
	 */
	private Date addtime;
	/**
	 * 添加者编号
	 */
	private String adduserid;
	/**
	 * 添加者名称
	 */
	private String addusername;
	/**
	 * 收回金额
	 */
	private BigDecimal takebackamount;
	/**
	 * 本次核销收回未核销余额
	 */
	private BigDecimal tbremainderamount;
	/**
	 * 编号
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 代垫编号
	 */
	public String getPayid() {
		return payid;
	}
	/**
	 * 代垫编号
	 */
	public void setPayid(String payid) {
		this.payid = payid;
	}
	/**
	 * 收回编号
	 */
	public String getTakebackid() {
		return takebackid;
	}
	/**
	 * 收回编号
	 */
	public void setTakebackid(String takebackid) {
		this.takebackid = takebackid;
	}
	/**
	 * 核销日期
	 */
	public String getWriteoffdate() {
		return writeoffdate;
	}
	/**
	 * 核销日期
	 */
	public void setWriteoffdate(String writeoffdate) {
		this.writeoffdate = writeoffdate;
	}
	/**
	 * 是否核销1是0否
	 */
	public String getIswriteoff() {
		return iswriteoff;
	}
	/**
	 * 是否核销1是0否
	 */
	public void setIswriteoff(String iswriteoff) {
		this.iswriteoff = iswriteoff;
	}
	/**
	 * 收回方式
	 */
	public String getReimbursetype() {
		return reimbursetype;
	}
	/**
	 * 收回方式
	 */
	public void setReimbursetype(String reimbursetype) {
		this.reimbursetype = reimbursetype;
	}
	/**
	 * 工厂投入
	 */
	public BigDecimal getFactoryamount() {
		return factoryamount;
	}
	/**
	 * 工厂投入
	 */
	public void setFactoryamount(BigDecimal factoryamount) {
		this.factoryamount = factoryamount;
	}
	/**
	 * 未核销余额
	 */
	public BigDecimal getRemainderamount() {
		return remainderamount;
	}
	/**
	 * 未核销余额
	 */
	public void setRemainderamount(BigDecimal remainderamount) {
		this.remainderamount = remainderamount;
	}
	/**
	 * 关联金额
	 */
	public BigDecimal getRelateamount() {
		return relateamount;
	}
	/**
	 * 关联金额
	 */
	public void setRelateamount(BigDecimal relateamount) {
		this.relateamount = relateamount;
	}
	/**
	 * 添加日期
	 */
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}
	/**
	 * 添加日期
	 */
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
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
	public BigDecimal getTakebackamount() {
		return takebackamount;
	}
	public void setTakebackamount(BigDecimal takebackamount) {
		this.takebackamount = takebackamount;
	}
	public BigDecimal getTbremainderamount() {
		return tbremainderamount;
	}
	public void setTbremainderamount(BigDecimal tbremainderamount) {
		this.tbremainderamount = tbremainderamount;
	}
}

