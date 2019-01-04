package com.hd.agent.crm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 商品分类
     */
    private String goodssort;

    /**
     * 品牌编码
     */
    private String brandid;

    /**
     * 供应商编号
     */
    private String supplierid;

    /**
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;

    /**
     * 辅计量单位
     */
    private String auxunitid;

    /**
     * 辅计量单位名称
     */
    private String auxunitname;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 总箱数
     */
    private BigDecimal totalbox;

    /**
     * 辅单位数量+辅单位+主单位余数+主单位
     */
    private String auxnumdetail;

    /**
     * 添加时间
     */
    private Date addtime;

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
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
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
     * @return 供应商编号
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编号
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    /**
     * @return 主计量单位
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid 
	 *            主计量单位
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 主计量单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname 
	 *            主计量单位名称
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    /**
     * @return 辅计量单位
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid 
	 *            辅计量单位
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid == null ? null : auxunitid.trim();
    }

    /**
     * @return 辅计量单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname 
	 *            辅计量单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname == null ? null : auxunitname.trim();
    }

    /**
     * @return 数量
     */
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    /**
     * @param unitnum 
	 *            数量
     */
    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    /**
     * @return 总箱数
     */
    public BigDecimal getTotalbox() {
        return totalbox;
    }

    /**
     * @param totalbox 
	 *            总箱数
     */
    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    /**
     * @return 辅单位数量+辅单位+主单位余数+主单位
     */
    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    /**
     * @param auxnumdetail 
	 *            辅单位数量+辅单位+主单位余数+主单位
     */
    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail == null ? null : auxnumdetail.trim();
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
}