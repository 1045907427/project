package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AdjustPriceDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 原价(调整前价格)
     */
    private BigDecimal oldprice;

    /**
     * 现价(调整后价格)
     */
    private BigDecimal nowprice;

    /**
     * 涨幅%
     */
    private BigDecimal rate;

    /**
     * 备注
     */
    private String remark;
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 商品品牌
     */
    private String goodsbrandName;
    /**
     * 条形码
     */
    private String barcode;

    /**
     * 现箱价
     */
    private BigDecimal nowboxprice;
    /**
     * 原箱价
     */
    private BigDecimal oldboxprice;
    /**
     * 箱装量
     */
    private BigDecimal boxnum;
    
    

	public BigDecimal getNowboxprice() {
		return nowboxprice;
	}

	public void setNowboxprice(BigDecimal nowboxprice) {
		this.nowboxprice = nowboxprice;
	}

	public BigDecimal getOldboxprice() {
		return oldboxprice;
	}

	public void setOldboxprice(BigDecimal oldboxprice) {
		this.oldboxprice = oldboxprice;
	}

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getGoodsbrandName() {
		return goodsbrandName;
	}

	public void setGoodsbrandName(String goodsbrandName) {
		this.goodsbrandName = goodsbrandName;
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
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 商品编码
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid 
	 *            商品编码
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    /**
     * @return 原价(调整前价格)
     */
    public BigDecimal getOldprice() {
        return oldprice;
    }

    /**
     * @param oldprice 
	 *            原价(调整前价格)
     */
    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    /**
     * @return 现价(调整后价格)
     */
    public BigDecimal getNowprice() {
        return nowprice;
    }

    /**
     * @param nowprice 
	 *            现价(调整后价格)
     */
    public void setNowprice(BigDecimal nowprice) {
        this.nowprice = nowprice;
    }

    /**
     * @return 涨幅%
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate 
	 *            涨幅%
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
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