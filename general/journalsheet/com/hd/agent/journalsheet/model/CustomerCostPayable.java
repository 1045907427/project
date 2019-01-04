package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 客户应付费用
 * @author chenwei
 */
public class CustomerCostPayable implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * OA编号
     */
    private String oaid;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 总店编号
     */
    private String pcustomerid;

    /**
     * 费用类别
     */
    private String expensesort;
    /**
     * 费用类别名称
     */
    private String expensesortname;
    /**
     * 借（收入金额）
     */
    private BigDecimal lendamount;
    /**
     * 贷（支出金额）
     */
    private BigDecimal payamount;
    
    /**
     * 应付金额
     */
    private BigDecimal amount;

    /**
     * 付款日期
     */
    private String paydate;

    /**
     * 是否付款1是0否
     */
    private String ispay;

    /**
     * 申请人编号
     */
    private String applyuserid;
    /**
     * 申请人名称
     */
    private String applyusername;
    /**
     * 申请人部门编号
     */
    private String applydeptid;
    /**
     * 申请部门名称
     */
    private String applydeptname;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 付款确认人用户编号
     */
    private String payuserid;

    /**
     * 付款确认人所属部门
     */
    private String paydeptid;

    /**
     * 付款时间
     */
    private Date paytime;

    /**
     * 单据类型 1：客户应付费用；2：客户支付费用
     */
    private String billtype;
    /**
     * 单据类型名称
     */
    private String billtypename;
    
    /**
     * 关联OA号
     */
    private String relateoaid;
    /**
     * 是否期初1是0否
     */
    private String isbegin;
    /**
     * 是否期初名称
     */
    private String isbeginname;
    /**
     * 销售区域名称
     */
    private String salesareaname;

    /**
     * 来源单据编号
     */
    private String billno;
    /**
     * 来源 ,0手工录入，1代垫， 11：费用冲差支付单 12:通路单 13：客户费用申请单 14：客户费用批量支付申请单 15：客户费用申请单（账扣） 16：客户费用申请单（账扣）（驳回） 17：品牌费用申请单（支付） 18：品牌费用申请单（支付）（驳回）
     */
    private String sourcefrom;
    /**
     * 来源名称
     */
    private String sourcefromname;
    /**
     * 红冲标志：0普通单据，1红冲标志,2被红冲的单据
     */
    private String hcflag;
    /**
     * 红冲标志名称
     */
    private String hcflagname;

    /**
     * 所属部门/品牌部门
     */
    private String deptid;
    /**
     * 所属部门名称
     */
    private String deptname;

    /**
     * 红冲关联编号
     */
    private String hcreferid;
    /**
     * 支付类型
     */
    private String paytype;
    /**
     * 支付类型
     */
    private String paytypename;
    /**
     * xls导入时错误消息
     */
    private String errormessage;

    /**
     * 期初金额
     */
    private BigDecimal beginamount;

    /**
     * 期末金额
     */
    private BigDecimal endamount;

    /**
     * 银行编码
     */
    private String bankid;

    /**
     * 银行名称
     */
    private String bankname;

    /**
     * 生成凭证次数
     */
    private int vouchertimes;

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
     * @return 供应商编号
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编号
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
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
     * @return 总店编号
     */
    public String getPcustomerid() {
        return pcustomerid;
    }

    /**
     * @param pcustomerid 
	 *            总店编号
     */
    public void setPcustomerid(String pcustomerid) {
        this.pcustomerid = pcustomerid == null ? null : pcustomerid.trim();
    }

    /**
     * @return 费用类别
     */
    public String getExpensesort() {
        return expensesort;
    }

    /**
     * @param expensesort 
	 *            费用类别
     */
    public void setExpensesort(String expensesort) {
        this.expensesort = expensesort == null ? null : expensesort.trim();
    }

    /**
     * @return 应付金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            应付金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    /**
     * @return 是否付款1是0否
     */
    public String getIspay() {
        return ispay;
    }

    /**
     * @param ispay 
	 *            是否付款1是0否
     */
    public void setIspay(String ispay) {
        this.ispay = ispay == null ? null : ispay.trim();
    }

    /**
     * @return 申请人编号
     */
    public String getApplyuserid() {
        return applyuserid;
    }

    /**
     * @param applyuserid 
	 *            申请人编号
     */
    public void setApplyuserid(String applyuserid) {
        this.applyuserid = applyuserid == null ? null : applyuserid.trim();
    }

    /**
     * @return 申请人部门编号
     */
    public String getApplydeptid() {
        return applydeptid;
    }

    /**
     * @param applydeptid 
	 *            申请人部门编号
     */
    public void setApplydeptid(String applydeptid) {
        this.applydeptid = applydeptid == null ? null : applydeptid.trim();
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 付款确认人用户编号
     */
    public String getPayuserid() {
        return payuserid;
    }

    /**
     * @param payuserid 
	 *            付款确认人用户编号
     */
    public void setPayuserid(String payuserid) {
        this.payuserid = payuserid == null ? null : payuserid.trim();
    }

    /**
     * @return 付款确认人所属部门
     */
    public String getPaydeptid() {
        return paydeptid;
    }

    /**
     * @param paydeptid 
	 *            付款确认人所属部门
     */
    public void setPaydeptid(String paydeptid) {
        this.paydeptid = paydeptid == null ? null : paydeptid.trim();
    }

    /**
     * @return 付款时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPaytime() {
        return paytime;
    }

    /**
     * @param paytime 
	 *            付款时间
     */
    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getExpensesortname() {
		return expensesortname;
	}

	public void setExpensesortname(String expensesortname) {
		this.expensesortname = expensesortname;
	}

	public String getApplyusername() {
		return applyusername;
	}

	public void setApplyusername(String applyusername) {
		this.applyusername = applyusername;
	}

	public String getApplydeptname() {
		return applydeptname;
	}

	public void setApplydeptname(String applydeptname) {
		this.applydeptname = applydeptname;
	}

	public BigDecimal getLendamount() {
		return lendamount;
	}

	public BigDecimal getPayamount() {
		return payamount;
	}

	public void setLendamount(BigDecimal lendamount) {
		this.lendamount = lendamount;
	}

	public void setPayamount(BigDecimal payamount) {
		this.payamount = payamount;
	}

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
    	this.billtype = billtype == null ? null : billtype.trim();
	}

	public String getRelateoaid() {
		return relateoaid;
	}

	public void setRelateoaid(String relateoaid) {
    	this.relateoaid = relateoaid == null ? null : relateoaid.trim();
	}

	public String getIsbegin() {
		return isbegin;
	}

	public void setIsbegin(String isbegin) {
    	this.isbegin = isbegin == null ? null : isbegin.trim();
	}

	public String getSalesareaname() {
		return salesareaname;
	}

	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}

    /**
     * @return 来源单据编号
     */
    public String getBillno() {
        return billno;
    }

    /**
     * @param billno 
	 *            来源单据编号
     */
    public void setBillno(String billno) {
        this.billno = billno == null ? null : billno.trim();
    }
    /**
     * @param sourcefrom
	 *            来源类型,0手工录入，1代垫， 11：费用冲差支付单 12:通路单 13：客户费用申请单 14：客户费用批量支付申请单
     */
    public String getSourcefrom() {
        return sourcefrom;
    }
    /**
     * @param sourcefrom
	 *            来源类型,0手工录入，1代垫， 11：费用冲差支付单 12:通路单 13：客户费用申请单 14：客户费用批量支付申请单
     */
    public void setSourcefrom(String sourcefrom) {
        this.sourcefrom = sourcefrom == null ? null : sourcefrom.trim();
    }

	public String getHcflag() {
		return hcflag;
	}

	public void setHcflag(String hcflag) {
        this.hcflag = hcflag == null ? null : hcflag.trim();
	}

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
    	this.deptid = deptid == null ? null : deptid.trim();
    }

	public String getHcreferid() {
		return hcreferid;
	}

	public void setHcreferid(String hcreferid) {
        this.hcreferid = hcreferid == null ? null : hcreferid.trim();
	}

	public String getBilltypename() {
		return billtypename;
	}

	public void setBilltypename(String billtypename) {
        this.billtypename = billtypename == null ? null : billtypename.trim();
	}

	public String getHcflagname() {
		return hcflagname;
	}

	public void setHcflagname(String hcflagname) {
        this.hcflagname = hcflagname == null ? null : hcflagname.trim();
	}

	public String getSourcefromname() {
		return sourcefromname;
	}

	public void setSourcefromname(String sourcefromname) {
        this.sourcefromname = sourcefromname == null ? null : sourcefromname.trim();
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
	}

	public String getIsbeginname() {
		return isbeginname;
	}

	public void setIsbeginname(String isbeginname) {
        this.isbeginname = isbeginname == null ? null : isbeginname.trim();
	}

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }
    public String getPaytypename() {
        return paytypename;
    }

    public void setPaytypename(String paytypename) {
        this.paytypename = paytypename == null ? null : paytypename.trim();
    }
    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage == null ? null : errormessage.trim();
    }

    public BigDecimal getBeginamount() {
        return beginamount;
    }

    public void setBeginamount(BigDecimal beginamount) {
        this.beginamount = beginamount;
    }

    public BigDecimal getEndamount() {
        return endamount;
    }

    public void setEndamount(BigDecimal endamount) {
        this.endamount = endamount;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public int getVouchertimes() {
        return vouchertimes;
    }

    public void setVouchertimes(int vouchertimes) {
        this.vouchertimes = vouchertimes;
    }
}