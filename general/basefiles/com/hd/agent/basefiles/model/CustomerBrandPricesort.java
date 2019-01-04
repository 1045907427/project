package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 客户品牌对应价格套
 * @author chenwei
 */
public class CustomerBrandPricesort implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 品牌部门名称
     */
    private String deptname;
    /**
     * 价格套
     */
    private String pricesort;
    
    /**
     * 初始价格套
     */
    private String initpricesort;
    /**
     * 价格套名称
     */
    private String pricesortname;
    /**
     * 备注
     */
    private String remark;
    /**
     * 添加时间
     */
    private Date addtime;
    
    /**
     * 是否修改
     */
    private String isedit;
    
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
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 品牌编号
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌编号
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 价格套
     */
    public String getPricesort() {
        return pricesort;
    }

    /**
     * @param pricesort 
	 *            价格套
     */
    public void setPricesort(String pricesort) {
        this.pricesort = pricesort == null ? null : pricesort.trim();
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

	public String getBrandname() {
		return brandname;
	}

	public String getPricesortname() {
		return pricesortname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public void setPricesortname(String pricesortname) {
		this.pricesortname = pricesortname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getInitpricesort() {
		return initpricesort;
	}

	public void setInitpricesort(String initpricesort) {
		this.initpricesort = initpricesort;
	}

	public String getIsedit() {
		return null != isedit ? isedit : "0";
	}

	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}
    
}