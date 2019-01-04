/**
 * @(#)PersonnelCustomer.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 19, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.model;
/**
 * 
 * 人员对应客户model
 * @author panxiaoxiao
 */

import java.io.Serializable;

public class PersonnelCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 人员编号
     */
    private String personid;

    /**
     * 客户编码
     */
    private String customerid;
    
    /**
     * 客户名称
     */
    private String customername;
    
    /**
     * 助记符
     */
    private String shortcode;
    
    /**
     * 所属分类
     */
    private String customersort;
    
    /**
     * 所属分类名称
     */
    private String customersortname;
    
    /**
     * 所属区域
     */
    private String salesarea;
    
    /**
     * 所属区域名称
     */
    private String salesareaname;
    
    /**
     * 排序
     */
    private Integer seq;

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
     * @return 客户编码
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编码
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}

	public String getCustomersortname() {
		return customersortname;
	}

	public void setCustomersortname(String customersortname) {
		this.customersortname = customersortname;
	}

	public String getSalesarea() {
		return salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getSalesareaname() {
		return salesareaname;
	}

	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
}

