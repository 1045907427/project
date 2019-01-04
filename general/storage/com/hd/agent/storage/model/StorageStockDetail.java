package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class StorageStockDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 结算编号
     */
    private String accountid;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 收发类型1收入2发出
     */
    private String inouttype;

    /**
     * 来源单据类型 0采购进货1采购入库2调拨入库3其它入库4发货回单5调拨出库6其它出库
     */
    private String sourcetype;

    /**
     * 来源编号
     */
    private String sourceid;

    /**
     * 来源单据明细编号
     */
    private String sourcedetailid;

    /**
     * 仓库编号
     */
    private String storageid;

    /**
     * 仓库名称
     */
    private String storagename;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 收入数量
     */
    private BigDecimal innum;

    /**
     * 收入含税金额
     */
    private BigDecimal intaxamount;

    /**
     * 收入未税金额
     */
    private BigDecimal innotaxamount;

    /**
     * 发出数量
     */
    private BigDecimal outnum;

    /**
     * 发出含税金额
     */
    private BigDecimal outtaxamount;

    /**
     * 发出未税金额
     */
    private BigDecimal outnotaxamount;

    /**
     * 税种档案编号
     */
    private String taxtype;

    /**
     * 销售成本价
     */
    private BigDecimal costprice;

    /**
     * 结算成本价
     * @return
     */
    private BigDecimal accountprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
    }

    public String getInouttype() {
        return inouttype;
    }

    public void setInouttype(String inouttype) {
        this.inouttype = inouttype;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getSourcedetailid() {
        return sourcedetailid;
    }

    public void setSourcedetailid(String sourcedetailid) {
        this.sourcedetailid = sourcedetailid;
    }

    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public BigDecimal getInnum() {
        return innum;
    }

    public void setInnum(BigDecimal innum) {
        this.innum = innum;
    }

    public BigDecimal getIntaxamount() {
        return intaxamount;
    }

    public void setIntaxamount(BigDecimal intaxamount) {
        this.intaxamount = intaxamount;
    }

    public BigDecimal getInnotaxamount() {
        return innotaxamount;
    }

    public void setInnotaxamount(BigDecimal innotaxamount) {
        this.innotaxamount = innotaxamount;
    }

    public BigDecimal getOutnum() {
        return outnum;
    }

    public void setOutnum(BigDecimal outnum) {
        this.outnum = outnum;
    }

    public BigDecimal getOuttaxamount() {
        return outtaxamount;
    }

    public void setOuttaxamount(BigDecimal outtaxamount) {
        this.outtaxamount = outtaxamount;
    }

    public BigDecimal getOutnotaxamount() {
        return outnotaxamount;
    }

    public void setOutnotaxamount(BigDecimal outnotaxamount) {
        this.outnotaxamount = outnotaxamount;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public BigDecimal getAccountprice() {
        return accountprice;
    }

    public void setAccountprice(BigDecimal accountprice) {
        this.accountprice = accountprice;
    }
}