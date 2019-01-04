package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaAccessGoodsAmount implements Serializable {
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
     * 商品差价
     */
    private BigDecimal difprice;

    /**
     * 商品数量
     */
    private BigDecimal unitnum;

    /**
     * 辅单位整数数量
     */
    private BigDecimal auxnum;

    /**
     * 辅单位余数数量
     */
    private BigDecimal auxremainder;

    /**
     * 数量描述
     */
    private String auxdetail;

    /**
     * 差价金额
     */
    private BigDecimal amount;

    /**
     * ERP数量
     */
    private BigDecimal erpnum;

    /**
     * ERP数量描述
     */
    private String erpdetail;

    /**
     * 降价金额
     */
    private BigDecimal downamount;

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
     * @return 商品差价
     */
    public BigDecimal getDifprice() {
        return difprice;
    }

    /**
     * @param difprice 
	 *            商品差价
     */
    public void setDifprice(BigDecimal difprice) {
        this.difprice = difprice;
    }

    /**
     * @return 商品数量
     */
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    /**
     * @param unitnum 
	 *            商品数量
     */
    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    /**
     * @return 辅单位整数数量
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum 
	 *            辅单位整数数量
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 辅单位余数数量
     */
    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    /**
     * @param auxremainder 
	 *            辅单位余数数量
     */
    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
    }

    /**
     * @return 数量描述
     */
    public String getAuxdetail() {
        return auxdetail;
    }

    /**
     * @param auxdetail 
	 *            数量描述
     */
    public void setAuxdetail(String auxdetail) {
        this.auxdetail = auxdetail == null ? null : auxdetail.trim();
    }

    /**
     * @return 差价金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            差价金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return ERP数量
     */
    public BigDecimal getErpnum() {
        return erpnum;
    }

    /**
     * @param erpnum 
	 *            ERP数量
     */
    public void setErpnum(BigDecimal erpnum) {
        this.erpnum = erpnum;
    }

    /**
     * @return ERP数量描述
     */
    public String getErpdetail() {
        return erpdetail;
    }

    /**
     * @param erpdetail 
	 *            ERP数量描述
     */
    public void setErpdetail(String erpdetail) {
        this.erpdetail = erpdetail == null ? null : erpdetail.trim();
    }

    /**
     * @return 降价金额
     */
    public BigDecimal getDownamount() {
        return downamount;
    }

    /**
     * @param downamount 
	 *            降价金额
     */
    public void setDownamount(BigDecimal downamount) {
        this.downamount = downamount;
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