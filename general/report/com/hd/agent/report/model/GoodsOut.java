package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsOut implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 订单编号
     */
    private String orderid;

    /**
     * 订单日期
     */
    private String businessdate;

    /**
     * 客户编号
     */
    private String customerid;
    
    private String customername;

    /**
     * 客户总店编号
     */
    private String pid;

    /**
     * 业务员
     */
    private String salesuserid;
    
    private String salesusername;

    /**
     * 商品编号
     */
    private String goodsid;
    
    private String goodsname;
    
    private String brandid;
    
    private String brandname;
    
    /**
     * 条形码
     */
    private String barcode;
    
    private String auxunitid;

    /**
     * 订单数据
     */
    private BigDecimal fixnum;
    private BigDecimal fixamount;
    private BigDecimal fixnumint;
    private String fixauxnum;
    private String fixauxnumdetail;
    /**
     * 发货数据
     */
    private BigDecimal sendnum;
    private BigDecimal sendamount;
    private BigDecimal sendnumint;
    private String sendauxnum;
    private String sendauxnumdetail;
    /**
     * 缺货数据
     */
    private BigDecimal outnum;
    private BigDecimal outamount;
    private BigDecimal outnumint;
    private String outauxnum;
    private String outauxnumdetail;
    /**
     * 是否已补货1是0否
     */
    private String issupply;
    /**
     * 生成时间
     */
    private Date adddate;

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
     * @return 订单编号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid 
	 *            订单编号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * @return 订单日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            订单日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
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
        this.customerid = customerid;
    }

    public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

    public String getSalesuserid() {
		return salesuserid;
	}

	public void setSalesuserid(String salesuserid) {
		this.salesuserid = salesuserid;
	}

	public String getSalesusername() {
		return salesusername;
	}

	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
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
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	/**
     * @return 订单数据
     */
    public BigDecimal getFixnum() {
        return fixnum;
    }

    /**
     * @param fixnum 
	 *            订单数据
     */
    public void setFixnum(BigDecimal fixnum) {
        this.fixnum = fixnum;
    }

    public BigDecimal getFixamount() {
		return fixamount;
	}

	public void setFixamount(BigDecimal fixamount) {
		this.fixamount = fixamount;
	}

	/**
     * @return 发货数据
     */
    public BigDecimal getSendnum() {
        return sendnum;
    }

    /**
     * @param sendnum 
	 *            发货数据
     */
    public void setSendnum(BigDecimal sendnum) {
        this.sendnum = sendnum;
    }

    public BigDecimal getSendamount() {
		return sendamount;
	}

	public void setSendamount(BigDecimal sendamount) {
		this.sendamount = sendamount;
	}

	/**
     * @return 缺货数据
     */
    public BigDecimal getOutnum() {
        return outnum;
    }

    /**
     * @param outnum 
	 *            缺货数据
     */
    public void setOutnum(BigDecimal outnum) {
        this.outnum = outnum;
    }

    public BigDecimal getOutamount() {
		return outamount;
	}

	public void setOutamount(BigDecimal outamount) {
		this.outamount = outamount;
	}

	/**
     * @return 生成时间
     */
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            生成时间
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getAuxunitid() {
		return auxunitid;
	}

	public void setAuxunitid(String auxunitid) {
		this.auxunitid = auxunitid;
	}

	public String getFixauxnum() {
		return fixauxnum;
	}

	public void setFixauxnum(String fixauxnum) {
		this.fixauxnum = fixauxnum;
	}

	public String getSendauxnum() {
		return sendauxnum;
	}

	public void setSendauxnum(String sendauxnum) {
		this.sendauxnum = sendauxnum;
	}

	public String getOutauxnum() {
		return outauxnum;
	}

	public void setOutauxnum(String outauxnum) {
		this.outauxnum = outauxnum;
	}

	public String getIssupply() {
		return issupply;
	}

	public void setIssupply(String issupply) {
		this.issupply = issupply;
	}

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public BigDecimal getFixnumint() {
        return fixnumint;
    }

    public void setFixnumint(BigDecimal fixnumint) {
        this.fixnumint = fixnumint;
    }

    public BigDecimal getOutnumint() {
        return outnumint;
    }

    public void setOutnumint(BigDecimal outnumint) {
        this.outnumint = outnumint;
    }

    public BigDecimal getSendnumint() {
        return sendnumint;
    }

    public void setSendnumint(BigDecimal sendnumint) {
        this.sendnumint = sendnumint;
    }

    public String getFixauxnumdetail() {
        return fixauxnumdetail;
    }

    public void setFixauxnumdetail(String fixauxnumdetail) {
        this.fixauxnumdetail = fixauxnumdetail;
    }

    public String getOutauxnumdetail() {
        return outauxnumdetail;
    }

    public void setOutauxnumdetail(String outauxnumdetail) {
        this.outauxnumdetail = outauxnumdetail;
    }

    public String getSendauxnumdetail() {
        return sendauxnumdetail;
    }

    public void setSendauxnumdetail(String sendauxnumdetail) {
        this.sendauxnumdetail = sendauxnumdetail;
    }


    private String sourceid;

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }
}