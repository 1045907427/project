package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OaPersonalLoan implements Serializable {
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
     * 打印次数
     */
    private Integer printtimes;

    /**
     * 单据类型1个人借款2预付款
     */
    private String billtype;

    /**
     * oa编号
     */
    private String oaid;

    /**
     * 所属部门编号
     */
    private String paydeptid;

    /**
     * 付款方式(1现金2转账)
     */
    private String paytype;

    /**
     * 付款银行(银行档案编号)
     */
    private String paybank;

    /**
     * 收汇人编号
     */
    private String payuserid;

    /**
     * 收汇人名称
     */
    private String payusername;

    /**
     * 付款金额
     */
    private BigDecimal amount;

    /**
     * 大写金额
     */
    private String upamount;

    /**
     * 借款类型
     */
    private String loantype;

    /**
     * 收款人编号
     */
    private String collectuserid;

    /**
     * 收款人名称
     */
    private String collectusername;

    /**
     * 收款人银行
     */
    private String collectbank;

    /**
     * 收款人银行账号
     */
    private String collectbankno;

    /**
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    /**
     * @return 最后修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最后修改人名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 最后修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            最后修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 打印次数
     */
    public Integer getPrinttimes() {
        return printtimes;
    }

    /**
     * @param printtimes 
	 *            打印次数
     */
    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    /**
     * @return 单据类型1个人借款2预付款
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            单据类型1个人借款2预付款
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * @return oa编号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * @param oaid 
	 *            oa编号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
    }

    /**
     * @return 所属部门编号
     */
    public String getPaydeptid() {
        return paydeptid;
    }

    /**
     * @param paydeptid 
	 *            所属部门编号
     */
    public void setPaydeptid(String paydeptid) {
        this.paydeptid = paydeptid == null ? null : paydeptid.trim();
    }

    /**
     * @return 付款方式(1现金2转账)
     */
    public String getPaytype() {
        return paytype;
    }

    /**
     * @param paytype 
	 *            付款方式(1现金2转账)
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    /**
     * @return 付款银行(银行档案编号)
     */
    public String getPaybank() {
        return paybank;
    }

    /**
     * @param paybank 
	 *            付款银行(银行档案编号)
     */
    public void setPaybank(String paybank) {
        this.paybank = paybank == null ? null : paybank.trim();
    }

    /**
     * @return 收汇人编号
     */
    public String getPayuserid() {
        return payuserid;
    }

    /**
     * @param payuserid 
	 *            收汇人编号
     */
    public void setPayuserid(String payuserid) {
        this.payuserid = payuserid == null ? null : payuserid.trim();
    }

    /**
     * @return 收汇人名称
     */
    public String getPayusername() {
        return payusername;
    }

    /**
     * @param payusername 
	 *            收汇人名称
     */
    public void setPayusername(String payusername) {
        this.payusername = payusername == null ? null : payusername.trim();
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
     * @return 大写金额
     */
    public String getUpamount() {
        return upamount;
    }

    /**
     * @param upamount 
	 *            大写金额
     */
    public void setUpamount(String upamount) {
        this.upamount = upamount == null ? null : upamount.trim();
    }

    /**
     * @return 借款类型
     */
    public String getLoantype() {
        return loantype;
    }

    /**
     * @param loantype 
	 *            借款类型
     */
    public void setLoantype(String loantype) {
        this.loantype = loantype == null ? null : loantype.trim();
    }

    /**
     * @return 收款人编号
     */
    public String getCollectuserid() {
        return collectuserid;
    }

    /**
     * @param collectuserid 
	 *            收款人编号
     */
    public void setCollectuserid(String collectuserid) {
        this.collectuserid = collectuserid == null ? null : collectuserid.trim();
    }

    /**
     * @return 收款人名称
     */
    public String getCollectusername() {
        return collectusername;
    }

    /**
     * @param collectusername 
	 *            收款人名称
     */
    public void setCollectusername(String collectusername) {
        this.collectusername = collectusername == null ? null : collectusername.trim();
    }

    public String getCollectbank() {
        return collectbank;
    }

    public void setCollectbank(String collectbank) {
        this.collectbank = collectbank;
    }

    public String getCollectbankno() {
        return collectbankno;
    }

    public void setCollectbankno(String collectbankno) {
        this.collectbankno = collectbankno;
    }
}