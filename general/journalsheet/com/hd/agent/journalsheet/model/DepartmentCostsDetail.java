package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 费用明细表
 * @author zhanghonghui
 * 时间 2014-6-26
 */
public class DepartmentCostsDetail implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3391445964371889460L;

	/**
     * 编码
     */
    private Long id;

    /**
     * 科目
     */
    private String subjectid;
    /**
     * 科目名称
     */
    private String subjectname;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 主表编号
     */
    private Integer deptcostsid;
    /**
     * 指定分摊的供应商
     */
    private String supplierid;

    /**
     * @return 编码
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return 主表编号
     */
    public Integer getDeptcostsid() {
        return deptcostsid;
    }

    /**
     * @param deptcostsid 
	 *            主表编号
     */
    public void setDeptcostsid(Integer deptcostsid) {
        this.deptcostsid = deptcostsid;
    }

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname == null ? null : subjectname.trim();
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid == null ? null : supplierid.trim();
	}
}