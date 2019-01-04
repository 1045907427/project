package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ReimburseInput implements Serializable {
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
     * 供应商编码
     */
    private String supplierid;
    
    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商所属部门编码
     */
    private String supplierdeptid;
    
    /**
     * 供应商所属部门名称
     */
    private String supplierdeptName;

    /**
     * 品牌编码
     */
    private String brandid;
    
    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 科目编码
     */
    private String subjectid;
    
    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 收回方式
     */
    private String reimbursetype;
    
    /**
     * 收回方式名称
     */
    private String reimbursetypeName;

    /**
     * 计划收回方式
     */
    private String planreimbursetype;
    
    /**
     * 计划收回方式名称
     */
    private String planreimbursetypeName;
    /**
     * 计划金额
     */
    private BigDecimal planamount;
    
    /**
     * 计划收回时间
     */
    private String planreimbursetime;

    /**
     * 收回金额
     */
    private BigDecimal takebackamount;

    /**
     * 未收回金额
     */
    private BigDecimal untakebackamount;

    /**
     * 代垫金额
     */
    private BigDecimal actingmatamount;

    /**
     * 盈余金额
     */
    private BigDecimal surplusamount;

    /**
     * 制单人编码
     */
    private String adduserid;

    /**
     * 制单人
     */
    private String addusername;

    /**
     * 制单日期
     */
    private Date addtime;

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
     * @return 供应商所属部门编码
     */
    public String getSupplierdeptid() {
        return supplierdeptid;
    }

    /**
     * @param supplierdeptid 
	 *            供应商所属部门编码
     */
    public void setSupplierdeptid(String supplierdeptid) {
        this.supplierdeptid = supplierdeptid == null ? null : supplierdeptid.trim();
    }

    /**
     * @return 品牌编码
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌编码
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 科目编码
     */
    public String getSubjectid() {
        return subjectid;
    }

    /**
     * @param subjectid 
	 *            科目编码
     */
    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid == null ? null : subjectid.trim();
    }

    /**
     * @return 收回方式
     */
    public String getReimbursetype() {
        return reimbursetype;
    }

    /**
     * @param reimbursetype 
	 *            收回方式
     */
    public void setReimbursetype(String reimbursetype) {
        this.reimbursetype = reimbursetype == null ? null : reimbursetype.trim();
    }

    /**
     * @return 计划金额
     */
    public BigDecimal getPlanamount() {
        return planamount;
    }

    /**
     * @param planamount 
	 *            计划金额
     */
    public void setPlanamount(BigDecimal planamount) {
        this.planamount = planamount;
    }

    /**
     * @return 收回金额
     */
    public BigDecimal getTakebackamount() {
        return takebackamount;
    }

    /**
     * @param takebackamount 
	 *            收回金额
     */
    public void setTakebackamount(BigDecimal takebackamount) {
        this.takebackamount = takebackamount;
    }

    /**
     * @return 未收回金额
     */
    public BigDecimal getUntakebackamount() {
        return untakebackamount;
    }

    /**
     * @param untakebackamount 
	 *            未收回金额
     */
    public void setUntakebackamount(BigDecimal untakebackamount) {
        this.untakebackamount = untakebackamount;
    }

    /**
     * @return 代垫金额
     */
    public BigDecimal getActingmatamount() {
        return actingmatamount;
    }

    /**
     * @param actingmatamount 
	 *            代垫金额
     */
    public void setActingmatamount(BigDecimal actingmatamount) {
        this.actingmatamount = actingmatamount;
    }

    /**
     * @return 盈余金额
     */
    public BigDecimal getSurplusamount() {
        return surplusamount;
    }

    /**
     * @param surplusamount 
	 *            盈余金额
     */
    public void setSurplusamount(BigDecimal surplusamount) {
        this.surplusamount = surplusamount;
    }

    /**
     * @return 制单人编码
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编码
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 制单日期
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单日期
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierdeptName() {
		return supplierdeptName;
	}

	public void setSupplierdeptName(String supplierdeptName) {
		this.supplierdeptName = supplierdeptName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getReimbursetypeName() {
		return reimbursetypeName;
	}

	public void setReimbursetypeName(String reimbursetypeName) {
		this.reimbursetypeName = reimbursetypeName;
	}

	public String getPlanreimbursetype() {
		return planreimbursetype;
	}

	public void setPlanreimbursetype(String planreimbursetype) {
		this.planreimbursetype = planreimbursetype;
	}

	public String getPlanreimbursetime() {
		return planreimbursetime;
	}

	public void setPlanreimbursetime(String planreimbursetime) {
		this.planreimbursetime = planreimbursetime;
	}

	public String getPlanreimbursetypeName() {
		return planreimbursetypeName;
	}

	public void setPlanreimbursetypeName(String planreimbursetypeName) {
		this.planreimbursetypeName = planreimbursetypeName;
	}
    
    
}