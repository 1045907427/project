package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContractLiability implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 月份
     */
    private String month;

    /**
     * 合同编号
     */
    private String contractid;
    /**
     * 合同编号
     */
    private String contractbillid;

    /**
     * 合同条款编号
     */
    private String contractcaluseid;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 部门编号
     */
    private String deptid;

    /**
     * 权责金额
     */
    private BigDecimal liabilityamount;

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
     * @return 月份
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month 
	 *            月份
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    /**
     * @return 合同编号
     */
    public String getContractid() {
        return contractid;
    }

    /**
     * @param contractid 
	 *            合同编号
     */
    public void setContractid(String contractid) {
        this.contractid = contractid == null ? null : contractid.trim();
    }

    public String getContractbillid() {
        return contractbillid;
    }

    public void setContractbillid(String contractbillid) {
        this.contractbillid = contractbillid;
    }

    /**
     * @return 合同条款编号
     */
    public String getContractcaluseid() {
        return contractcaluseid;
    }

    /**
     * @param contractcaluseid 
	 *            合同条款编号
     */
    public void setContractcaluseid(String contractcaluseid) {
        this.contractcaluseid = contractcaluseid == null ? null : contractcaluseid.trim();
    }

    /**
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
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
     * @return 权责金额
     */
    public BigDecimal getLiabilityamount() {
        return liabilityamount;
    }

    /**
     * @param liabilityamount 
	 *            权责金额
     */
    public void setLiabilityamount(BigDecimal liabilityamount) {
        this.liabilityamount = liabilityamount;
    }

    private String customername;

    private String deptname;

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String subjectexpenses;

    public String getSubjectexpenses() {
        return subjectexpenses;
    }

    public void setSubjectexpenses(String subjectexpenses) {
        this.subjectexpenses = subjectexpenses;
    }
}