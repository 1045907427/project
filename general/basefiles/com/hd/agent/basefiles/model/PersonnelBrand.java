/**
 * @(#)PersonnelBrand.java
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
 * 人员对应品牌model
 * @author panxiaoxiao
 */

import java.io.Serializable;

public class PersonnelBrand implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 人员编码
     */
    private String personid;

    /**
     * 品牌编码
     */
    private String brandid;
    
    /**
     * 品牌名称
     */
    private String brandname;
    
    /**
     * @return 人员编码
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid 
	 *            人员编码
     */
    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
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

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

