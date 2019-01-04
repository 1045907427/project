/**
 * @(#)PersonnelBrandAndCustomer.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 23, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.model;
/**
 * 
 * 对应品牌和客户model
 * @author panxiaoxiao
 */
import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class PersonnelBrandAndCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private Integer id;

    /**
     * 人员编号
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
     * 客户编码
     */
    private String customerid;
    
    /**
     * 客户名称
     */
    private String customername;
    
    /**
     * 标记
     */
    private String sign;
    
    /**
     * 添加时间
     */
    private Date addtime;
    
    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * @return 编码
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
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

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
}

