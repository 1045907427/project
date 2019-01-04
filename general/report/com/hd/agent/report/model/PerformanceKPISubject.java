package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PerformanceKPISubject implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 代码
     */
    private String code;

    /**
     * 代码名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 状态1启用0禁用
     */
    private String state;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加用户编号
     */
    private String adduserid;

    /**
     * 添加用户名称
     */
    private String addusername;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门编码
     */
    private String deptid;

    /**
     * 指标分数
     */
    private BigDecimal score;

    /**
     * 指标分值
     */
    private BigDecimal svalue;
    /**
     * 原代码
     */
    private String oldcode;
    /**
     * 部门名称
     */
    private String deptname;

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
     * @return 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 
	 *            代码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return 代码名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            代码名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * @return 状态1启用0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1启用0禁用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 添加时间
     */
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
     * @return 添加用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加用户名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加用户名称
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
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
     * @return 部门编码
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            部门编码
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }


    /**
     * @param deptid 
	 *            部门名称
     */
    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }
    /**
     * @return 部门名称
     */
    public String getDeptname() {
        return deptname;
    }

    /**
     * @return 指标分数
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * @param score 
	 *            指标分数
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * @return 指标分值
     */
    public BigDecimal getSvalue() {
        return svalue;
    }

    /**
     * @param svalue 
	 *            指标分值
     */
    public void setSvalue(BigDecimal svalue) {
        this.svalue = svalue;
    }

	public String getOldcode() {
		return oldcode;
	}

	public void setOldcode(String oldcode) {
		this.oldcode = oldcode;
	}
}