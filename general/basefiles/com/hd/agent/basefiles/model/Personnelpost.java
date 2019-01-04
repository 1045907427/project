package com.hd.agent.basefiles.model;

import java.io.Serializable;

public class Personnelpost implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 人员编号
     */
    private String personid;

    /**
     * 开始日期
     */
    private String startdate;

    /**
     * 结束日期
     */
    private String enddate;

    /**
     * 所属部门编号
     */
    private String belongdeptid;
    
    /**
     * 所属部门名称
     */
    private String belongdeptName;

    /**
     * 所属岗位编号
     */
    private String belongpostid;
    
    /**
     * 所属岗位名称
     */
    private String belongpostName;

    /**
     * 薪资方案
     */
    private String salaryscheme;

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
     * @return 人员编号
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid 
	 *            人员编号
     */
    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }

    /**
     * @return 开始日期
     */
    public String getStartdate() {
        return startdate;
    }

    /**
     * @param startdate 
	 *            开始日期
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    /**
     * @return 结束日期
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate 
	 *            结束日期
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    /**
     * @return 所属部门编号
     */
    public String getBelongdeptid() {
        return belongdeptid;
    }

    /**
     * @param belongdeptid 
	 *            所属部门编号
     */
    public void setBelongdeptid(String belongdeptid) {
        this.belongdeptid = belongdeptid == null ? null : belongdeptid.trim();
    }

    /**
     * @return 所属岗位编号
     */
    public String getBelongpostid() {
        return belongpostid;
    }

    /**
     * @param belongpostid 
	 *	所属岗位编号
     */
    public void setBelongpostid(String belongpostid) {
        this.belongpostid = belongpostid == null ? null : belongpostid.trim();
    }

    /**
     * @return 薪资方案
     */
    public String getSalaryscheme() {
        return salaryscheme;
    }

    /**
     * @param salaryscheme 
	 *            薪资方案
     */
    public void setSalaryscheme(String salaryscheme) {
        this.salaryscheme = salaryscheme == null ? null : salaryscheme.trim();
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

	public String getBelongdeptName() {
		return belongdeptName;
	}

	public void setBelongdeptName(String belongdeptName) {
		this.belongdeptName = belongdeptName;
	}

	public String getBelongpostName() {
		return belongpostName;
	}

	public void setBelongpostName(String belongpostName) {
		this.belongpostName = belongpostName;
	}
    
}