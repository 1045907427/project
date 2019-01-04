package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 银行账户借贷业务（收入支出业务）
 * @author chenwei
 */
public class BankAmountOthers implements Serializable {
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
     * 状态
     */
    private String status;

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
     * 审核人编号
     */
    private String audituserid;

    /**
     * 审核人名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 关闭时间
     */
    private Date closetime;

    /**
     * 银行档案编号
     */
    private String bankid;
    /**
     * 银行名称
     */
    private String bankname;
    /**
     * 部门编号
     */
    private String deptid;
    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 借贷类型1借(收入)2贷（支出）
     */
    private String lendtype;

    /**
     * OA编号
     */
    private String oaid;

    /**
     * 相关单据号
     */
    private String billid;

    /**
     * 单据类型（0期初1收款单2付款单,不在该单据内）3日常费用支付4客户费用支付5个人借款6预付款7转账8代垫收回
     */
    private String billtype;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 大写金额
     */
    private String upamount;

    /**
     * 对方编号（客户编号，供应商编号等）
     */
    private String oppid;

    /**
     * 对方名称（客户名称，供应商名称等）
     */
    private String oppname;

    /**
     * 对方银行
     */
    private String oppbank;

    /**
     * 对方开户银行账号
     */
    private String oppbankno;

    /**
     * 发票编号
     */
    private String invoiceid;

    /**
     * 发票金额
     */
    private BigDecimal invoiceamount;

    /**
     * 发票类型
     */
    private String invoicetype;

    /**
     * 发票日期
     */
    private String invoicedate;

    /**
     * 备注
     */
    private String remark;

    /**
     * oa类型，1正常转交，2驳回
     */
    private String oatype;

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
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 审核人编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核人编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核人名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核人名称
     */
    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername == null ? null : auditusername.trim();
    }

    /**
     * @return 审核时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAudittime() {
        return audittime;
    }

    /**
     * @param audittime 
	 *            审核时间
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    /**
     * @return 关闭时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            关闭时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
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
     * @return 借贷类型1借(收入)2贷（支出）
     */
    public String getLendtype() {
        return lendtype;
    }

    /**
     * @param lendtype 
	 *            借贷类型1借(收入)2贷（支出）
     */
    public void setLendtype(String lendtype) {
        this.lendtype = lendtype == null ? null : lendtype.trim();
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
     * @return 相关单据号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            相关单据号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 单据类型（0期初1收款单2付款单,不在该单据内）3日常费用支付4客户费用支付5个人借款6预付款7转账
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            单据类型（0期初1收款单2付款单,不在该单据内）3日常费用支付4客户费用支付5个人借款6预付款7转账
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            金额
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
     * @return 对方编号（客户编号，供应商编号等）
     */
    public String getOppid() {
        return oppid;
    }

    /**
     * @param oppid 
	 *            对方编号（客户编号，供应商编号等）
     */
    public void setOppid(String oppid) {
        this.oppid = oppid == null ? null : oppid.trim();
    }

    /**
     * @return 对方名称（客户名称，供应商名称等）
     */
    public String getOppname() {
        return oppname;
    }

    /**
     * @param oppname 
	 *            对方名称（客户名称，供应商名称等）
     */
    public void setOppname(String oppname) {
        this.oppname = oppname == null ? null : oppname.trim();
    }

    /**
     * @return 对方银行
     */
    public String getOppbank() {
        return oppbank;
    }

    /**
     * @param oppbank 
	 *            对方银行
     */
    public void setOppbank(String oppbank) {
        this.oppbank = oppbank == null ? null : oppbank.trim();
    }

    /**
     * @return 对方开户银行账号
     */
    public String getOppbankno() {
        return oppbankno;
    }

    /**
     * @param oppbankno 
	 *            对方开户银行账号
     */
    public void setOppbankno(String oppbankno) {
        this.oppbankno = oppbankno == null ? null : oppbankno.trim();
    }

    /**
     * @return 发票编号
     */
    public String getInvoiceid() {
        return invoiceid;
    }

    /**
     * @param invoiceid 
	 *            发票编号
     */
    public void setInvoiceid(String invoiceid) {
        this.invoiceid = invoiceid == null ? null : invoiceid.trim();
    }

    /**
     * @return 发票金额
     */
    public BigDecimal getInvoiceamount() {
        return invoiceamount;
    }

    /**
     * @param invoiceamount 
	 *            发票金额
     */
    public void setInvoiceamount(BigDecimal invoiceamount) {
        this.invoiceamount = invoiceamount;
    }

    /**
     * @return 发票类型
     */
    public String getInvoicetype() {
        return invoicetype;
    }

    /**
     * @param invoicetype 
	 *            发票类型
     */
    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype == null ? null : invoicetype.trim();
    }

    /**
     * @return 发票日期
     */
    public String getInvoicedate() {
        return invoicedate;
    }

    /**
     * @param invoicedate 
	 *            发票日期
     */
    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate == null ? null : invoicedate.trim();
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

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

    public String getOatype() {
        return oatype;
    }

    public void setOatype(String oatype) {
        this.oatype = oatype;
    }
}