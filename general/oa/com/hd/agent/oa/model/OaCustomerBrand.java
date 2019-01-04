package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaCustomerBrand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 新客户登录单编号
     */
    private String billid;

    /**
     * 品牌编号
     */
    private String brandid;
    
    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 条码总数（该品牌下商品数量）
     */
    private Integer barcodenum;

    /**
     * 实际进场数量（商品实际进场数）
     */
    private Integer realnum;

    /**
     * 陈列组数
     */
    private Integer displaynum;

    /**
     * 费用
     */
    private BigDecimal cost;

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
     * @return 新客户登录单编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            新客户登录单编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
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
     * @return 品牌名称
     */
    public String getBrandname() {
        return brandname;
    }

    /**
     * @param brandname 
	 *            品牌名称
     */
    public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
    }

    /**
     * @return 条码总数（该品牌下商品数量）
     */
    public Integer getBarcodenum() {
        return barcodenum;
    }

    /**
     * @param barcodenum 
	 *            条码总数（该品牌下商品数量）
     */
    public void setBarcodenum(Integer barcodenum) {
        this.barcodenum = barcodenum;
    }

    /**
     * @return 实际进场数量（商品实际进场数）
     */
    public Integer getRealnum() {
        return realnum;
    }

    /**
     * @param realnum 
	 *            实际进场数量（商品实际进场数）
     */
    public void setRealnum(Integer realnum) {
        this.realnum = realnum;
    }

    /**
     * @return 陈列组数
     */
    public Integer getDisplaynum() {
        return displaynum;
    }

    /**
     * @param displaynum 
	 *            陈列组数
     */
    public void setDisplaynum(Integer displaynum) {
        this.displaynum = displaynum;
    }

    /**
     * @return 费用
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * @param cost 
	 *            费用
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}