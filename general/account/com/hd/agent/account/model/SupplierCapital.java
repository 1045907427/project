/**
 * @(#)SupplierCapital.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 12, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class SupplierCapital {

	private static final long serialVersionUID = 1L;

    /**
     * 供应商编号
     */
    private String id;
    /**
     * 供应商名称
     */
    private String suppliername;
    
    /**
     * 资金余额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    private Date addtime;

    private Date modifytime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
}

