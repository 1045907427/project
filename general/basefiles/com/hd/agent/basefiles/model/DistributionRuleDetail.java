package com.hd.agent.basefiles.model;

import java.io.Serializable;

public class DistributionRuleDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 规则编号
     */
    private String ruleid;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 品牌编码
     */
    private String brandid;

    /**
     * 商品分类
     */
    private String goodssort;

    /**
     * 商品类型
     */
    private String goodstype;

    /**
     * 供应商编码
     */
    private String supplierid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 商品分类名称
     */
    private String goodssortname;

    /**
     * 商品类型名称
     */
    private String goodstypename;

    /**
     * 供应商名称
     */
    private String suppliername;

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
     * @return 规则编号
     */
    public String getRuleid() {
        return ruleid;
    }

    /**
     * @param ruleid 
	 *            规则编号
     */
    public void setRuleid(String ruleid) {
        this.ruleid = ruleid == null ? null : ruleid.trim();
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
     * @return 商品分类
     */
    public String getGoodssort() {
        return goodssort;
    }

    /**
     * @param goodssort 
	 *            商品分类
     */
    public void setGoodssort(String goodssort) {
        this.goodssort = goodssort == null ? null : goodssort.trim();
    }

    /**
     * @return 商品类型
     */
    public String getGoodstype() {
        return goodstype;
    }

    /**
     * @param goodstype 
	 *            商品类型
     */
    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype == null ? null : goodstype.trim();
    }

    /**
     * @return 供应商编码
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编码
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getGoodssortname() {
        return goodssortname;
    }

    public void setGoodssortname(String goodssortname) {
        this.goodssortname = goodssortname;
    }

    public String getGoodstypename() {
        return goodstypename;
    }

    public void setGoodstypename(String goodstypename) {
        this.goodstypename = goodstypename;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}