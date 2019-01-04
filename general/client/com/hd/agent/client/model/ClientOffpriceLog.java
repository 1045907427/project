package com.hd.agent.client.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ClientOffpriceLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String offpriceid;

    private String deptid;

    private String deptname;

    private String goodsid;

    private String begintimebefore;

    private String begintimeafter;

    private String endtimebefore;

    private String endtimeafter;

    private String begindatebefore;

    private String begindateafter;

    private String enddatebefore;

    private String enddateafter;

    private BigDecimal retailpricebefore;

    private BigDecimal retailpriceafter;

    private Date operatetime;
    
    
    //前台展示
    private String retailprice;
    private String goodsname;

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getRetailprice() {
		return retailprice;
	}

	public void setRetailprice(String retailprice) {
		this.retailprice = retailprice;
	}

	/**
     * 操作人id
     */
    private String operateuserid;

    /**
     * 操作人名称
     */
    private String operateusername;

    /**
     * 操作类型  0 新增 1修改
     */
    private String operatetype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public String getBegintimebefore() {
        return begintimebefore;
    }

    public void setBegintimebefore(String begintimebefore) {
        this.begintimebefore = begintimebefore;
    }

    public String getBegintimeafter() {
        return begintimeafter;
    }

    public void setBegintimeafter(String begintimeafter) {
        this.begintimeafter = begintimeafter;
    }

    public String getEndtimebefore() {
        return endtimebefore;
    }

    public void setEndtimebefore(String endtimebefore) {
        this.endtimebefore = endtimebefore;
    }

    public String getEndtimeafter() {
        return endtimeafter;
    }

    public void setEndtimeafter(String endtimeafter) {
        this.endtimeafter = endtimeafter;
    }

    public String getBegindatebefore() {
        return begindatebefore;
    }

    public void setBegindatebefore(String begindatebefore) {
        this.begindatebefore = begindatebefore;
    }

    public String getBegindateafter() {
        return begindateafter;
    }

    public void setBegindateafter(String begindateafter) {
        this.begindateafter = begindateafter;
    }

    public String getEnddatebefore() {
        return enddatebefore;
    }

    public void setEnddatebefore(String enddatebefore) {
        this.enddatebefore = enddatebefore;
    }

    public String getEnddateafter() {
        return enddateafter;
    }

    public void setEnddateafter(String enddateafter) {
        this.enddateafter = enddateafter;
    }

    public BigDecimal getRetailpricebefore() {
        return retailpricebefore;
    }

    public void setRetailpricebefore(BigDecimal retailpricebefore) {
        this.retailpricebefore = retailpricebefore;
    }

    public BigDecimal getRetailpriceafter() {
        return retailpriceafter;
    }

    public void setRetailpriceafter(BigDecimal retailpriceafter) {
        this.retailpriceafter = retailpriceafter;
    }
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    /**
     * @return 操作人id
     */
    public String getOperateuserid() {
        return operateuserid;
    }

    /**
     * @param operateuserid 
	 *            操作人id
     */
    public void setOperateuserid(String operateuserid) {
        this.operateuserid = operateuserid == null ? null : operateuserid.trim();
    }

    /**
     * @return 操作人名称
     */
    public String getOperateusername() {
        return operateusername;
    }

    /**
     * @param operateusername 
	 *            操作人名称
     */
    public void setOperateusername(String operateusername) {
        this.operateusername = operateusername == null ? null : operateusername.trim();
    }

    /**
     * @return 操作类型  0 新增 1修改
     */
    public String getOperatetype() {
        return operatetype;
    }

    /**
     * @param operatetype 
	 *            操作类型  0 新增 1修改
     */
    public void setOperatetype(String operatetype) {
        this.operatetype = operatetype == null ? null : operatetype.trim();
    }

    public String getOffpriceid() {
        return offpriceid;
    }

    public void setOffpriceid(String offpriceid) {
        this.offpriceid = offpriceid;
    }
}