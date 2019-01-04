package com.hd.agent.report.model;

import java.math.BigDecimal;

/**
 * Created by lin_xx on 2016/8/10.
 */
public class SaleoutDetailAndDiscount {
    /**
     * 人员部门
     */
    private String persondept;
    private String persondeptname;
    /**
     * 来源单据编号(发货单或退货入库单编号)
     */
    private String sourceid;
    /**
     * 销售订单编号
     */
    private String saleorderid;
    /**
     * 客户编码
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 品牌业务员
     */
    private String branduser;
    /**
     * 品牌业务员名称
     */
    private String brandusername;
    /**
     * 商品编码
     */
    private String goodsid;
    private String goodsname;
    private String barcode;
    private String model;

    private String brandid;
    private String brandname;

    /**
     * 发货数量
     */
    private BigDecimal unitnum;
    /**
     *发货件数
     */
    private BigDecimal totalbox ;
    /**
     *发货折扣
     */
    private BigDecimal discountamount;
    /**
     *发货金额
     */
    private BigDecimal taxamount;

    public String getPersondept() {
        return persondept;
    }

    public void setPersondept(String persondept) {
        this.persondept = persondept;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getBranduser() {
        return branduser;
    }

    public void setBranduser(String branduser) {
        this.branduser = branduser;
    }

    public String getBrandusername() {
        return brandusername;
    }

    public void setBrandusername(String brandusername) {
        this.brandusername = brandusername;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public BigDecimal getTotalbox() {
        return totalbox;
    }

    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    public BigDecimal getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(BigDecimal discountamount) {
        this.discountamount = discountamount;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    public String getPersondeptname() {
        return persondeptname;
    }

    public void setPersondeptname(String persondeptname) {
        this.persondeptname = persondeptname;
    }

    public String getSaleorderid() {
        return saleorderid;
    }

    public void setSaleorderid(String saleorderid) {
        this.saleorderid = saleorderid;
    }
}

