/**
 * @(#)BuySupplierDetailsort.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-17 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.model;

import java.io.Serializable;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class BuySupplierDetailsort implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6181136028622161542L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 供应商编码
     */
    private String supplierid;

    /**
     * 供应商分类编码
     */
    private String suppliersort;

    /**
     * 供应商分类名称
     */
    private String suppliersortname;

    /**
     * 是否默认分类1是0否
     */
    private String isdefault;

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
     * @return 供应商分类编码
     */
    public String getSuppliersort() {
        return suppliersort;
    }

    /**
     * @param suppliersort 
	 *            供应商分类编码
     */
    public void setSuppliersort(String suppliersort) {
        this.suppliersort = suppliersort == null ? null : suppliersort.trim();
    }

    /**
     * @return 供应商分类名称
     */
    public String getSuppliersortname() {
        return suppliersortname;
    }

    /**
     * @param suppliersortname 
	 *            供应商分类名称
     */
    public void setSuppliersortname(String suppliersortname) {
        this.suppliersortname = suppliersortname == null ? null : suppliersortname.trim();
    }

    /**
     * @return 是否默认分类1是0否
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault 
	 *            是否默认分类1是0否
     */
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault == null ? null : isdefault.trim();
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
}

