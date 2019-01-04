package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 部门日常费用
 * @author chenwei
 */
public class DeptDailyCost implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 业务日期-年
     */
    private Integer year;

    /**
     * 业务日期-月
     */
    private Integer month;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加者编号
     */
    private String adduserid;

    /**
     * 添加者名称
     */
    private String addusername;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 最新修改者编号
     */
    private String modifyuserid;

    /**
     * 最新修改者名称
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 审核者编号
     */
    private String audituserid;

    /**
     * 审核者名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * OA编号
     */
    private String oaid;

    /**
     * 日常费用科目
     */
    private String costsort;
    /**
     * 日常费用科目名称
     */
    private String costsortname;
    /**
     * 部门编号
     */
    private String deptid;
    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 大写金额
     */
    private String upamount;
    /**
     * 银行档案编号
     */
    private String bankid;
    /**
     * 银行档案名称
     */
    private String bankname;

    /**
     * 是否通过银行支付1是0否
     */
    private String isbankpay;
    /**
     * 对方名称
     */
    private String oppname;
    /**
     * 对方银行
     */
    private String oppbank;
    /**
     * 对方银行账号
     */
    private String oppbankno;
    /**
     * 供应商编码
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;
    /**
     * 品牌编码
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 主单位编号
     */
    private String unitid;
    /**
     * 主单位名称
     */
    private String unitname;
    /**
     * 数量
     */
    private BigDecimal unitnum;
    /**
     * 含税单价
     */
    private BigDecimal taxprice;

    /**
     * 打印次数
     */
    private Integer printtimes;
    /**
     * 客户业务员
     */
    private String salesuser;

    private String salesusername;
    /**
     * 生成凭证次数
     */
    private Integer vouchertimes;
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
        this.id = id;
    }

    /**
     * @return 业务日期-年
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year 
	 *            业务日期-年
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return 业务日期-月
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month 
	 *            业务日期-月
     */
    public void setMonth(Integer month) {
        this.month = month;
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
     * @return 添加者编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加者编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加者名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加者名称
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
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
     * @return 最新修改者编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最新修改者编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最新修改者名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最新修改者名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 审核者编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核者编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核者名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核者名称
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
     * @return 日常费用科目
     */
    public String getCostsort() {
        return costsort;
    }

    /**
     * @param costsort 
	 *            日常费用科目
     */
    public void setCostsort(String costsort) {
        this.costsort = costsort == null ? null : costsort.trim();
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
     * @return 是否通过银行支付1是0否
     */
    public String getIsbankpay() {
        return isbankpay;
    }

    /**
     * @param isbankpay 
	 *            是否通过银行支付1是0否
     */
    public void setIsbankpay(String isbankpay) {
        this.isbankpay = isbankpay == null ? null : isbankpay.trim();
    }

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getCostsortname() {
		return costsortname;
	}

	public void setCostsortname(String costsortname) {
		this.costsortname = costsortname;
	}

	public String getUpamount() {
		return upamount;
	}

	public void setUpamount(String upamount) {
		this.upamount = upamount;
	}

	public String getOppname() {
		return oppname;
	}

	public void setOppname(String oppname) {
		this.oppname = oppname;
	}

	public String getOppbank() {
		return oppbank;
	}

	public void setOppbank(String oppbank) {
		this.oppbank = oppbank;
	}

	public String getOppbankno() {
		return oppbankno;
	}

	public void setOppbankno(String oppbankno) {
		this.oppbankno = oppbankno;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
	}

	public BigDecimal getUnitnum() {
		return unitnum;
	}

	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}

	public BigDecimal getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
        this.suppliername = suppliername == null ? null : suppliername.trim();
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
	}

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getSalesuser() {
        return salesuser;
    }

    public void setSalesuser(String salesuser) {
        this.salesuser = salesuser;
    }

    public String getSalesusername() {
        return salesusername;
    }

    public void setSalesusername(String salesusername) {
        this.salesusername = salesusername;
    }

    public Integer getVouchertimes() {
        return vouchertimes;
    }

    public void setVouchertimes(Integer vouchertimes) {
        this.vouchertimes = vouchertimes;
    }
}