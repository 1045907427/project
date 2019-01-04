package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ContractReport implements Serializable {
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
     * 费用金额
     */
    private BigDecimal costamount;

    /**
     * 代垫金额
     */
    private BigDecimal matcostsamount;

    /**
     * 自理金额
     */
    private BigDecimal selfamount;

    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人姓名
     */
    private String addusername;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 销售额
     */
    private BigDecimal saleamount;

    /**
     * 门店数量
     */
    private BigDecimal storenum;

    /**
     * 新门店数量
     */
    private BigDecimal newstorenum;

    /**
     * 新品数量
     */
    private BigDecimal newgoodsnum;

    /**
     * 百分比
     */
    private BigDecimal rate;

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
     * @return 费用金额
     */
    public BigDecimal getCostamount() {
        return costamount;
    }

    /**
     * @param costamount 
	 *            费用金额
     */
    public void setCostamount(BigDecimal costamount) {
        this.costamount = costamount;
    }

    /**
     * @return 代垫金额
     */
    public BigDecimal getMatcostsamount() {
        return matcostsamount;
    }

    /**
     * @param matcostsamount 
	 *            代垫金额
     */
    public void setMatcostsamount(BigDecimal matcostsamount) {
        this.matcostsamount = matcostsamount;
    }

    /**
     * @return 自理金额
     */
    public BigDecimal getSelfamount() {
        return selfamount;
    }

    /**
     * @param selfamount 
	 *            自理金额
     */
    public void setSelfamount(BigDecimal selfamount) {
        this.selfamount = selfamount;
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

    /**
     * @return 销售额
     */
    public BigDecimal getSaleamount() {
        return saleamount;
    }

    /**
     * @param saleamount 
	 *            销售额
     */
    public void setSaleamount(BigDecimal saleamount) {
        this.saleamount = saleamount;
    }

    /**
     * @return 门店数量
     */
    public BigDecimal getStorenum() {
        return storenum;
    }

    /**
     * @param storenum 
	 *            门店数量
     */
    public void setStorenum(BigDecimal storenum) {
        this.storenum = storenum;
    }

    /**
     * @return 新门店数量
     */
    public BigDecimal getNewstorenum() {
        return newstorenum;
    }

    /**
     * @param newstorenum 
	 *            新门店数量
     */
    public void setNewstorenum(BigDecimal newstorenum) {
        this.newstorenum = newstorenum;
    }

    /**
     * @return 新品数量
     */
    public BigDecimal getNewgoodsnum() {
        return newgoodsnum;
    }

    /**
     * @param newgoodsnum 
	 *            新品数量
     */
    public void setNewgoodsnum(BigDecimal newgoodsnum) {
        this.newgoodsnum = newgoodsnum;
    }

    /**
     * @return 百分比
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate 
	 *            百分比
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    private String customername;

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    private String deptname;

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }


    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    private String  calculatetype;

    private BigDecimal calculateamount;

    public String getCalculatetype() {
        return calculatetype;
    }

    public void setCalculatetype(String calculatetype) {
        this.calculatetype = calculatetype;
    }

    public BigDecimal getCalculateamount() {
        return calculateamount;
    }

    public void setCalculateamount(BigDecimal calculateamount) {
        this.calculateamount = calculateamount;
    }

    private BigDecimal initnewstorenum;
    private BigDecimal initnewgoodsnum;
    private BigDecimal initmatcostsamount;
    private BigDecimal initselfamount;

    public BigDecimal getInitnewstorenum() {
        return initnewstorenum;
    }

    public void setInitnewstorenum(BigDecimal initnewstorenum) {
        this.initnewstorenum = initnewstorenum;
    }

    public BigDecimal getInitnewgoodsnum() {
        return initnewgoodsnum;
    }

    public void setInitnewgoodsnum(BigDecimal initnewgoodsnum) {
        this.initnewgoodsnum = initnewgoodsnum;
    }

    public BigDecimal getInitmatcostsamount() {
        return initmatcostsamount;
    }

    public void setInitmatcostsamount(BigDecimal initmatcostsamount) {
        this.initmatcostsamount = initmatcostsamount;
    }

    public BigDecimal getInitselfamount() {
        return initselfamount;
    }

    public void setInitselfamount(BigDecimal initselfamount) {
        this.initselfamount = initselfamount;
    }
}