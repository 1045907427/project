package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门金额日志
 * @author chenwei
 */
public class DeptAmountLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 部门编号
     */
    private String deptid;

    /**
     * 银行档案编号
     */
    private String bankid;

    /**
     * 对应单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账
     */
    private String billtype;

    /**
     * 对应单据号
     */
    private String billid;

    /**
     * 借方金额(收入)
     */
    private BigDecimal inamount;

    /**
     * 贷方金额(支出)
     */
    private BigDecimal outamount;

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
     * @return 部门编号
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            部门编号
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 银行档案编号
     */
    public String getBankid() {
        return bankid;
    }

    /**
     * @param bankid 
	 *            银行档案编号
     */
    public void setBankid(String bankid) {
        this.bankid = bankid == null ? null : bankid.trim();
    }

    /**
     * @return 对应单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            对应单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账
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
     * @return 借方金额(收入)
     */
    public BigDecimal getInamount() {
        return inamount;
    }

    /**
     * @param inamount 
	 *            借方金额(收入)
     */
    public void setInamount(BigDecimal inamount) {
        this.inamount = inamount;
    }

    /**
     * @return 贷方金额(支出)
     */
    public BigDecimal getOutamount() {
        return outamount;
    }

    /**
     * @param outamount 
	 *            贷方金额(支出)
     */
    public void setOutamount(BigDecimal outamount) {
        this.outamount = outamount;
    }

    /**
     * @return 余额
     */
    public BigDecimal getBalanceamount() {
        return balanceamount;
    }

    /**
     * @param balanceamount 
	 *            余额
     */
    public void setBalanceamount(BigDecimal balanceamount) {
        this.balanceamount = balanceamount;
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