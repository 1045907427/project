package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaOffPriceDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 进价
     */
    private BigDecimal buyprice;

    /**
     * 原价
     */
    private BigDecimal oldprice;

    /**
     * 特价
     */
    private BigDecimal offprice;

    /**
     * 毛利率
     */
    private BigDecimal profitrate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 特价
     */
    private String ordernum;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 条形码
     */
    private String barcode;

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
     * @return 进价
     */
    public BigDecimal getBuyprice() {
        return buyprice;
    }

    /**
     * @param buyprice 
	 *            进价
     */
    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    /**
     * @return 原价
     */
    public BigDecimal getOldprice() {
        return oldprice;
    }

    /**
     * @param oldprice 
	 *            原价
     */
    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    /**
     * @return 特价
     */
    public BigDecimal getOffprice() {
        return offprice;
    }

    /**
     * @param offprice 
	 *            特价
     */
    public void setOffprice(BigDecimal offprice) {
        this.offprice = offprice;
    }

    /**
     * @return 毛利率
     */
    public BigDecimal getProfitrate() {
        return profitrate;
    }

    /**
     * @param profitrate 
	 *            毛利率
     */
    public void setProfitrate(BigDecimal profitrate) {
        this.profitrate = profitrate;
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

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}