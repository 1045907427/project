package com.hd.agent.purchase.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by luoq on 2018/1/8.
 * 采购进货单运费分摊记录
 */
public class ArrivalOrderChange {
    /**
     * 主键id
     */
    private int id;

    /**
     * 采购进货单编号
     */
    private String billid;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 采购进货单来源单据
     */
    private String billno;

    /**
     * 采购进货单来源明细编号
     */
    private String billdetailno;

    /**
     * 分摊金额
     */
    private BigDecimal taxamount;

    /**
     * 分摊未税金额
     */
    private BigDecimal notaxamount;

    /**
     * 分摊税额
     */
    private BigDecimal tax;

    /**
     * 制单人用户编号
     */
    private String adduserid;

    /**
     * 制单人用户名称
     */
    private String addusername;

    /**
     * 制单人部门编号
     */
    private String adddeptid;

    /**
     * 制单人部门名称
     */
    private String adddeptname;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 税种
     */
    private String taxtype;

    /**
     * 备注
     */
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getBilldetailno() {
        return billdetailno;
    }

    public void setBilldetailno(String billdetailno) {
        this.billdetailno = billdetailno;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    public BigDecimal getNotaxamount() {
        return notaxamount;
    }

    public void setNotaxamount(BigDecimal notaxamount) {
        this.notaxamount = notaxamount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getAdduserid() {
        return adduserid;
    }

    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid;
    }

    public String getAddusername() {
        return addusername;
    }

    public void setAddusername(String addusername) {
        this.addusername = addusername;
    }

    public String getAdddeptid() {
        return adddeptid;
    }

    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid;
    }

    public String getAdddeptname() {
        return adddeptname;
    }

    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
