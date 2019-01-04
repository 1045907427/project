package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户对应客户分类
 * @author zhengziyong
 *
 */
public class CustomerAndSort implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 档案编号
     */
    private String customerid;

    /**
     * 档案分类编号
     */
    private String sortid;

    /**
     * 档案分类名称
     */
    private String sortname;

    /**
     * 是否默认分类
     */
    private String defaultsort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
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
        this.id = id;
    }

    /**
     * @return 档案编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            档案编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    /**
     * @return 档案分类编号
     */
    public String getSortid() {
        return sortid;
    }

    /**
     * @param sortid 
	 *            档案分类编号
     */
    public void setSortid(String sortid) {
        this.sortid = sortid;
    }

    /**
     * @return 档案分类名称
     */
    public String getSortname() {
        return sortname;
    }

    /**
     * @param sortname 
	 *            档案分类名称
     */
    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    /**
     * @return 是否默认分类
     */
    public String getDefaultsort() {
        return defaultsort;
    }

    /**
     * @param defaultsort 
	 *            是否默认分类
     */
    public void setDefaultsort(String defaultsort) {
        this.defaultsort = defaultsort;
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
        this.remark = remark;
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
}