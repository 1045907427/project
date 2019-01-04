package com.hd.agent.client.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ClientOffprice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 部门编号
     */
    private String deptid;


    /**
     * 部门名称
     */
    private String deptname;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 零售价
     */
    private BigDecimal retailprice;

    /**
     * 起始时间
     */
    private String begintime;

    /**
     * 终止时间
     */
    private String endtime;

    /**
     * 起始日期
     */
    private String begindate;

    /**
     * 终止日期
     */
    private String enddate;

    /**
     * 添加时间
     */
    private Date addtime;

    private String barcode;
    private String mainunit;
    private String goodsname;

    /**
     * 基准销售价
     */
    private BigDecimal basesaleprice;

    /**
     * 添加人
     */
    private String operateuserid;

    /**
     * 添加人名称
     */
    private String operateusername;

    /**
     * 主单位编号
     */
    private String mainunitid;

    /**
     * 主单位名称
     */
    private String mainunitname;

    /**
     * 状态
     */
    private String status;


	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getMainunit() {
		return mainunit;
	}

	public void setMainunit(String mainunit) {
		this.mainunit = mainunit;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	/**
     * @return 主键
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(String id) {
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

    /**
     * @return 商品编号
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid 
	 *            商品编号
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    /**
     * @return 零售价
     */
    public BigDecimal getRetailprice() {
        return retailprice;
    }

    /**
     * @param retailprice 
	 *            零售价
     */
    public void setRetailprice(BigDecimal retailprice) {
        this.retailprice = retailprice;
    }

    /**
     * @return 起始时间
     */
    public String getBegintime() {
        return begintime;
    }

    /**
     * @param begintime 
	 *            起始时间
     */
    public void setBegintime(String begintime) {
        this.begintime = begintime == null ? null : begintime.trim();
    }

    /**
     * @return 终止时间
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * @param endtime 
	 *            终止时间
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    /**
     * @return 起始日期
     */
    public String getBegindate() {
        return begindate;
    }

    /**
     * @param begindate 
	 *            起始日期
     */
    public void setBegindate(String begindate) {
        this.begindate = begindate == null ? null : begindate.trim();
    }

    /**
     * @return 终止日期
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate 
	 *            终止日期
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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

    public BigDecimal getBasesaleprice() {
        return basesaleprice;
    }

    public void setBasesaleprice(BigDecimal basesaleprice) {
        this.basesaleprice = basesaleprice;
    }

    public String getOperateuserid() {
        return operateuserid;
    }

    public void setOperateuserid(String operateuserid) {
        this.operateuserid = operateuserid;
    }

    public String getOperateusername() {
        return operateusername;
    }

    public void setOperateusername(String operateusername) {
        this.operateusername = operateusername;
    }

    public String getMainunitid() {
        return mainunitid;
    }

    public void setMainunitid(String mainunitid) {
        this.mainunitid = mainunitid;
    }

    public String getMainunitname() {
        return mainunitname;
    }

    public void setMainunitname(String mainunitname) {
        this.mainunitname = mainunitname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}