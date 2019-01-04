/**
 * @(#)PGoodsInfo.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 14, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author zhengziyong
 */
public class PGoodsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String goodsid;
	private String ptype;
	private String name;
	private BigDecimal price;
	private BigDecimal inprice;
	private String spell;
	private String barcode;
	private String boxbarcode;
    private String model;
    /**
     * 是否批次管理
     */
    private String isbatch;
    /**
     * 批次条形码
     */
    private String sbarcode;
	private String brand;
	private String defaultsort;
    private String goodstype;
    private String supplierid;
	private String unitid;
	private String unitname;
	private String auxunitid;
	private String auxunitname;
	private BigDecimal rate;
	private BigDecimal minimum;
    private String remark;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getInprice() {
		return inprice;
	}
	public void setInprice(BigDecimal inprice) {
		this.inprice = inprice;
	}
	public String getSpell() {
		return spell;
	}
	public void setSpell(String spell) {
		this.spell = spell;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getBoxbarcode() {
		return boxbarcode;
	}
	public void setBoxbarcode(String boxbarcode) {
		this.boxbarcode = boxbarcode;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDefaultsort() {
		return defaultsort;
	}
	public void setDefaultsort(String defaultsort) {
		this.defaultsort = defaultsort;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getAuxunitid() {
		return auxunitid;
	}
	public void setAuxunitid(String auxunitid) {
		this.auxunitid = auxunitid;
	}
	public String getAuxunitname() {
		return auxunitname;
	}
	public void setAuxunitname(String auxunitname) {
		this.auxunitname = auxunitname;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

    public String getSbarcode() {
        return sbarcode;
    }

    public void setSbarcode(String sbarcode) {
        this.sbarcode = sbarcode;
    }

    public String getIsbatch() {
        return isbatch;
    }

    public void setIsbatch(String isbatch) {
        this.isbatch = isbatch;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

	public BigDecimal getMinimum() {
		return minimum;
	}

	public void setMinimum(BigDecimal minimum) {
		this.minimum = minimum;
	}
}

