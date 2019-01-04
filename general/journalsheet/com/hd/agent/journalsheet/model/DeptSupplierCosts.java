package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeptSupplierCosts implements Serializable {

    /**
	 * 部门费用供应商摊派
	 */
	private static final long serialVersionUID = 814932735531011263L;

	private Long id;

    /**
     * 部门费用编码
     */
    private Integer deptcostsid;

    /**
     * 科目
     */
    private String subjectid;
    /**
     * 科目名称
     */
    private String subjectname;

    /**
     * 供应商
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;

    /**
     * 金额
     */
    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 部门费用编码
     */
    public Integer getDeptcostsid() {
        return deptcostsid;
    }

    /**
     * @param deptfeeid 
	 *            部门费用编码
     */
    public void setDeptcostsid(Integer deptcostsid) {
        this.deptcostsid = deptcostsid;
    }

    /**
     * @return 科目
     */
    public String getSubjectid() {
        return subjectid;
    }

    /**
     * @param subjectid 
	 *            科目
     */
    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid == null ? null : subjectid.trim();
    }

    /**
     * @return 供应商
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
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

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname == null ? null : subjectname.trim();
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername == null ? null : suppliername.trim();
	}
}