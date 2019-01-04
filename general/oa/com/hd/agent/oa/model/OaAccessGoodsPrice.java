package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaAccessGoodsPrice implements Serializable {
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
     * 商品名称
     */
    private String goodsname;

    /**
     * 进货价
     */
    private BigDecimal buyprice;

    /**
     * 工厂让利
     */
    private BigDecimal factoryprice;

    /**
     * 自理
     */
    private BigDecimal myprice;

    /**
     * 原价
     */
    private BigDecimal oldprice;

    /**
     * 现价
     */
    private BigDecimal newprice;

    /**
     * 毛利率%
     */
//    private BigDecimal rate;
    private String rate;

    /**
     * 门店出货信息
     */
    private String senddetail;

    /**
     * 备注
     */
    private String remark;

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
     * @return 商品名称
     */
    public String getGoodsname() {
        return goodsname;
    }

    /**
     * @param goodsname 
	 *            商品名称
     */
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
    }

    /**
     * @return 进货价
     */
    public BigDecimal getBuyprice() {
        return buyprice;
    }

    /**
     * @param buyprice 
	 *            进货价
     */
    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    /**
     * @return 工厂让利
     */
    public BigDecimal getFactoryprice() {
        return factoryprice;
    }

    /**
     * @param factoryprice 
	 *            工厂让利
     */
    public void setFactoryprice(BigDecimal factoryprice) {
        this.factoryprice = factoryprice;
    }

    /**
     * @return 自理
     */
    public BigDecimal getMyprice() {
        return myprice;
    }

    /**
     * @param myprice 
	 *            自理
     */
    public void setMyprice(BigDecimal myprice) {
        this.myprice = myprice;
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
     * @return 现价
     */
    public BigDecimal getNewprice() {
        return newprice;
    }

    /**
     * @param newprice 
	 *            现价
     */
    public void setNewprice(BigDecimal newprice) {
        this.newprice = newprice;
    }

    /**
     * @return 毛利率%
     */
    public String getRate() {
        return rate;
    }

    /**
     * @param rate 
	 *            毛利率%
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * @return 门店出货信息
     */
    public String getSenddetail() {
        return senddetail;
    }

    /**
     * @param senddetail 
	 *            门店出货信息
     */
    public void setSenddetail(String senddetail) {
        this.senddetail = senddetail == null ? null : senddetail.trim();
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