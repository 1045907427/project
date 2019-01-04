/**
 * @(#)SupplierCapitalLog.java
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
public class SupplierCapitalLog {

	private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 供应商编码
     */
    private String supplierid;

    /**
     * 对应单据类型1付款单2采购发票3转账单4冲差单
     */
    private String billtype;

    /**
     * 对应单据号
     */
    private String billid;

    /**
     * 收发类型1收款2核销3转入4转出
     */
    private String prtype;

    /**
     * 收入金额
     */
    private BigDecimal incomeamount;

    /**
     * 支出金额
     */
    private BigDecimal payamount;
    
    /**
     * 余额
     */
    private BigDecimal balanceamount;

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

    /**
     * @return 供应商编码
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编码
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    /**
     * @return 对应单据类型1付款单2采购发票3转账单4冲差单
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            对应单据类型1付款单2采购发票3转账单4冲差单
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * @return 对应单据号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            对应单据号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 收发类型1收款2核销3转入4转出
     */
    public String getPrtype() {
        return prtype;
    }

    /**
     * @param prtype 
	 *            收发类型1收款2核销3转入4转出
     */
    public void setPrtype(String prtype) {
        this.prtype = prtype == null ? null : prtype.trim();
    }

    /**
     * @return 收入金额
     */
    public BigDecimal getIncomeamount() {
        return incomeamount;
    }

    /**
     * @param incomeamount 
	 *            收入金额
     */
    public void setIncomeamount(BigDecimal incomeamount) {
        this.incomeamount = incomeamount;
    }

    /**
     * @return 支出金额
     */
    public BigDecimal getPayamount() {
        return payamount;
    }

    /**
     * @param payamount 
	 *            支出金额
     */
    public void setPayamount(BigDecimal payamount) {
        this.payamount = payamount;
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
     * @return 制单人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 制单人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            制单人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 制单人部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            制单人部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
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

	public BigDecimal getBalanceamount() {
		return balanceamount;
	}

	public void setBalanceamount(BigDecimal balanceamount) {
		this.balanceamount = balanceamount;
	}
}

