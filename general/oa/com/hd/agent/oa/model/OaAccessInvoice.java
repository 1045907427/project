package com.hd.agent.oa.model;

import java.io.Serializable;

public class OaAccessInvoice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * OA编号
     */
    private String oaid;

    /**
     * 收款单位编码
     */
    private String companyid;

    /**
     * 收款单位名称
     */
    private String companyname;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 银行账号
     */
    private String bankno;

    /**
     * 付款金额
     */
    private String payamount;

    /**
     * 金额大写
     */
    private String amountwords;

    /**
     * 费用项目
     */
    private String expensesort;

    /**
     * 发票种类
     */
    private String invoicetype;

    /**
     * 到发票日期
     */
    private String invoicedate;

    /**
     * 付款日期
     */
    private String paydate;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return OA编号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * @param oaid 
	 *            OA编号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
    }

    /**
     * @return 收款单位编码
     */
    public String getCompanyid() {
        return companyid;
    }

    /**
     * @param companyid 
	 *            收款单位编码
     */
    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
    }

    /**
     * @return 收款单位名称
     */
    public String getCompanyname() {
        return companyname;
    }

    /**
     * @param companyname 
	 *            收款单位名称
     */
    public void setCompanyname(String companyname) {
        this.companyname = companyname == null ? null : companyname.trim();
    }

    /**
     * @return 开户银行
     */
    public String getBank() {
        return bank;
    }

    /**
     * @param bank 
	 *            开户银行
     */
    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    /**
     * @return 银行账号
     */
    public String getBankno() {
        return bankno;
    }

    /**
     * @param bankno 
	 *            银行账号
     */
    public void setBankno(String bankno) {
        this.bankno = bankno == null ? null : bankno.trim();
    }

    /**
     * @return 付款金额
     */
    public String getPayamount() {
        return payamount;
    }

    /**
     * @param payamount 
	 *            付款金额
     */
    public void setPayamount(String payamount) {
        this.payamount = payamount;
    }

    /**
     * @return 金额大写
     */
    public String getAmountwords() {
        return amountwords;
    }

    /**
     * @param amountwords 
	 *            金额大写
     */
    public void setAmountwords(String amountwords) {
        this.amountwords = amountwords == null ? null : amountwords.trim();
    }

    /**
     * @return 费用项目
     */
    public String getExpensesort() {
        return expensesort;
    }

    /**
     * @param expensesort 
	 *            费用项目
     */
    public void setExpensesort(String expensesort) {
        this.expensesort = expensesort == null ? null : expensesort.trim();
    }

    /**
     * @return 发票种类
     */
    public String getInvoicetype() {
        return invoicetype;
    }

    /**
     * @param invoicetype 
	 *            发票种类
     */
    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype == null ? null : invoicetype.trim();
    }

    /**
     * @return 到发票日期
     */
    public String getInvoicedate() {
        return invoicedate;
    }

    /**
     * @param invoicedate 
	 *            到发票日期
     */
    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate == null ? null : invoicedate.trim();
    }

    /**
     * @return 付款日期
     */
    public String getPaydate() {
        return paydate;
    }

    /**
     * @param paydate 
	 *            付款日期
     */
    public void setPaydate(String paydate) {
        this.paydate = paydate == null ? null : paydate.trim();
    }
}