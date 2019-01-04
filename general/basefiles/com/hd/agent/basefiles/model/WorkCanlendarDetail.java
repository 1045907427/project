/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-28 chenwei 创建版本
 */
package com.hd.agent.basefiles.model;

import java.io.Serializable;
/**
 * 工作日历明细
 * @author chenwei
 */
public class WorkCanlendarDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 工作日历编号
     */
    private String canlendarid;

    /**
     * 日期(yyyy-MM-dd)
     */
    private String date;
    /**
     * 年度
     */
    private String year;
    /**
     * 是否工作日1是0否
     */
    private String iswork;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 工作日历编号
     */
    public String getCanlendarid() {
        return canlendarid;
    }

    /**
     * @param canlendarid 
	 *            工作日历编号
     */
    public void setCanlendarid(String canlendarid) {
        this.canlendarid = canlendarid == null ? null : canlendarid.trim();
    }

    /**
     * @return 日期(yyyy-MM-dd)
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date 
	 *            日期(yyyy-MM-dd)
     */
    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    /**
     * @return 是否工作日1是0否
     */
    public String getIswork() {
        return iswork;
    }

    /**
     * @param iswork 
	 *            是否工作日1是0否
     */
    public void setIswork(String iswork) {
        this.iswork = iswork == null ? null : iswork.trim();
    }

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
    
}