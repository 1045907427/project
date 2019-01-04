/**
 * @(#)PurchaseStatement.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;
/**
 * 
 * 采购发票核销对账单
 * @author panxiaoxiao
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class PurchaseStatement implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 收款单编号
     */
    private String supplierid;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 单据类型1采购发票2付款单合并
     */
    private String billtype;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 付款金额
     */
    private BigDecimal amount;

    /**
     * 单据金额
     */
    private BigDecimal billamount;

    /**
     * 核销金额
     */
    private BigDecimal writeoffamount;

    /**
     * 尾差金额
     */
    private BigDecimal tailamount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人
     */
    private String addusername;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	/**
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
    }

    /**
     * @return 单据类型1采购发票2付款单合并
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            单据类型1采购发票2付款单合并
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * @return 单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 付款金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            付款金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return 单据金额
     */
    public BigDecimal getBillamount() {
        return billamount;
    }

    /**
     * @param billamount 
	 *            单据金额
     */
    public void setBillamount(BigDecimal billamount) {
        this.billamount = billamount;
    }

    /**
     * @return 核销金额
     */
    public BigDecimal getWriteoffamount() {
        return writeoffamount;
    }

    /**
     * @param writeoffamount 
	 *            核销金额
     */
    public void setWriteoffamount(BigDecimal writeoffamount) {
        this.writeoffamount = writeoffamount;
    }

    /**
     * @return 尾差金额
     */
    public BigDecimal getTailamount() {
        return tailamount;
    }

    /**
     * @param tailamount 
	 *            尾差金额
     */
    public void setTailamount(BigDecimal tailamount) {
        this.tailamount = tailamount;
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return 制单人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 制单时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}

