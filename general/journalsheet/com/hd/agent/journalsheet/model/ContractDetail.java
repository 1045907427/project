package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContractDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 合同编号
     */
    private String billid;

    /**
     * 条款编号
     */
    private String caluseid;

    /**
     * 费用类型:0.可变费用,1.固定费用
     */
    private String costtype;

    /**
     * 分摊方式:0.一次性分摊,1.分月平坦
     */
    private String sharetype;

    /**
     * 返还类型:0.月返,1.季返,2.年返
     */
    private String returntype;

    /**
     * 返还月份
     */
    private Integer returnmonthnum;

    /**
     * 返还要求:0.无要求,1.销售达成
     */
    private String returnrequire;

    /**
     * 对应费用科目
     */
    private String subjectexpenses;

    /**
     * 计算方式
     */
    private String calculatetype;

    /**
     * 计算金额
     */
    private BigDecimal calculateamount;

    /**
     * 备注
     */
    private String remark;

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
     * @return 合同编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            合同编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    public String getCaluseid() {
        return caluseid;
    }

    public void setCaluseid(String caluseid) {
        this.caluseid = caluseid;
    }

    /**
     * @return 费用类型:0.可变费用,1.固定费用
     */
    public String getCosttype() {
        return costtype;
    }

    /**
     * @param costtype 
	 *            费用类型:0.可变费用,1.固定费用
     */
    public void setCosttype(String costtype) {
        this.costtype = costtype == null ? null : costtype.trim();
    }

    /**
     * @return 分摊方式:0.一次性分摊,1.分月平坦
     */
    public String getSharetype() {
        return sharetype;
    }

    /**
     * @param sharetype 
	 *            分摊方式:0.一次性分摊,1.分月平坦
     */
    public void setSharetype(String sharetype) {
        this.sharetype = sharetype == null ? null : sharetype.trim();
    }

    /**
     * @return 返还类型:0.月返,1.季返,2.年返
     */
    public String getReturntype() {
        return returntype;
    }

    /**
     * @param returntype 
	 *            返还类型:0.月返,1.季返,2.年返
     */
    public void setReturntype(String returntype) {
        this.returntype = returntype == null ? null : returntype.trim();
    }

    /**
     * @return 返还月份
     */
    public Integer getReturnmonthnum() {
        return returnmonthnum;
    }

    /**
     * @param returnmonthnum 
	 *            返还月份
     */
    public void setReturnmonthnum(Integer returnmonthnum) {
        this.returnmonthnum = returnmonthnum;
    }

    /**
     * @return 返还要求:0.无要求,1.销售达成
     */
    public String getReturnrequire() {
        return returnrequire;
    }

    /**
     * @param returnrequire 
	 *            返还要求:0.无要求,1.销售达成
     */
    public void setReturnrequire(String returnrequire) {
        this.returnrequire = returnrequire == null ? null : returnrequire.trim();
    }

    /**
     * @return 对应费用科目
     */
    public String getSubjectexpenses() {
        return subjectexpenses;
    }

    /**
     * @param subjectexpenses 
	 *            对应费用科目
     */
    public void setSubjectexpenses(String subjectexpenses) {
        this.subjectexpenses = subjectexpenses == null ? null : subjectexpenses.trim();
    }

    /**
     * @return 计算方式
     */
    public String getCalculatetype() {
        return calculatetype;
    }

    /**
     * @param calculatetype 
	 *            计算方式
     */
    public void setCalculatetype(String calculatetype) {
        this.calculatetype = calculatetype == null ? null : calculatetype.trim();
    }

    /**
     * @return 计算金额
     */
    public BigDecimal getCalculateamount() {
        return calculateamount;
    }

    /**
     * @param calculateamount 
	 *            计算金额
     */
    public void setCalculateamount(BigDecimal calculateamount) {
        this.calculateamount = calculateamount;
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



    private  String subjectexpensesname;

    public String getSubjectexpensesname() {
        return subjectexpensesname;
    }

    public void setSubjectexpensesname(String subjectexpensesname) {
        this.subjectexpensesname = subjectexpensesname;
    }
}