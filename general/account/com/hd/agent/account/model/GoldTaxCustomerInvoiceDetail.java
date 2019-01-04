package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoldTaxCustomerInvoiceDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;
    /**
     * 页面编号
     */
    private String upid;

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
     * 来源商品编号
     */
    private String sourcegoodsid;

    /**
     * 来源商品名称
     */
    private String sourcegoodsname;

    /**
     * 金税税收分类编码
     */
    private String jstypeid;

    /**
     * 规格
     */
    private String model;

    /**
     * 单位名称
     */
    private String unitname;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 含税单价
     */
    private BigDecimal taxprice;

    /**
     * 含税金额
     */
    private BigDecimal taxamount;

    /**
     * 无税单价
     */
    private BigDecimal notaxprice;

    /**
     * 无税金额
     */
    private BigDecimal notaxamount;

    /**
     * 免税标识：0普通零税率1免税
     */
    private String taxfreeflag;

    /**
     * 税率
     */
    private BigDecimal taxrate;

    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 来源，0录入，1导入
     */
    private String sourcetype;

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
     * @return 来源商品编号
     */
    public String getSourcegoodsid() {
        return sourcegoodsid;
    }

    /**
     * @param sourcegoodsid
     *            来源商品编号
     */
    public void setSourcegoodsid(String sourcegoodsid) {
        this.sourcegoodsid = sourcegoodsid == null ? null : sourcegoodsid.trim();
    }

    /**
     * @return 来源商品名称
     */
    public String getSourcegoodsname() {
        return sourcegoodsname;
    }

    /**
     * @param sourcegoodsname
     *            来源商品名称
     */
    public void setSourcegoodsname(String sourcegoodsname) {
        this.sourcegoodsname = sourcegoodsname == null ? null : sourcegoodsname.trim();
    }

    /**
     * @return 金税税收分类编码
     */
    public String getJstypeid() {
        return jstypeid;
    }

    /**
     * @param jstypeid
     *            金税税收分类编码
     */
    public void setJstypeid(String jstypeid) {
        this.jstypeid = jstypeid == null ? null : jstypeid.trim();
    }

    /**
     * @return 规格
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model
     *            规格
     */
    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    /**
     * @return 单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname
     *            单位名称
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
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
     * @return 含税单价
     */
    public BigDecimal getTaxprice() {
        return taxprice;
    }

    /**
     * @param taxprice
     *            含税单价
     */
    public void setTaxprice(BigDecimal taxprice) {
        this.taxprice = taxprice;
    }

    /**
     * @return 含税金额
     */
    public BigDecimal getTaxamount() {
        return taxamount;
    }

    /**
     * @param taxamount
     *            含税金额
     */
    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    /**
     * @return 无税单价
     */
    public BigDecimal getNotaxprice() {
        return notaxprice;
    }

    /**
     * @param notaxprice
     *            无税单价
     */
    public void setNotaxprice(BigDecimal notaxprice) {
        this.notaxprice = notaxprice;
    }

    /**
     * @return 无税金额
     */
    public BigDecimal getNotaxamount() {
        return notaxamount;
    }

    /**
     * @param notaxamount
     *            无税金额
     */
    public void setNotaxamount(BigDecimal notaxamount) {
        this.notaxamount = notaxamount;
    }

    /**
     * @return 免税标识：0普通零税率1免税
     */
    public String getTaxfreeflag() {
        return taxfreeflag;
    }

    /**
     * @param taxfreeflag
     *            免税标识：0普通零税率1免税
     */
    public void setTaxfreeflag(String taxfreeflag) {
        this.taxfreeflag = taxfreeflag == null ? null : taxfreeflag.trim();
    }

    /**
     * @return 税率
     */
    public BigDecimal getTaxrate() {
        return taxrate;
    }

    /**
     * @param taxrate
     *            税率
     */
    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    /**
     * @return 税额
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * @param tax
     *            税额
     */
    public void setTax(BigDecimal tax) {
        this.tax = tax;
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

    /**
     * @return 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq
     *            排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * @return 来源，0录入，1导入
     */
    public String getSourcetype() {
        return sourcetype;
    }

    /**
     * @param sourcetype
     *            来源，0录入，1导入
     */
    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
    }

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid == null ? null : upid.trim();
    }
}