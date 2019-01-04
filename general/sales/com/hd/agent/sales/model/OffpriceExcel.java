package com.hd.agent.sales.model;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: PXX
 * Date: 2015/10/5
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
public class OffpriceExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 客户类型
     */
    private String customertype;
    /**
     * 客户类型名称
     */
    private String customertypename;

    /**
     * 客户
     */
    private String customerid;

    /**
     * 客户名称
     */
    private String customername;

    /**
     * 开始生效日期
     */
    private String begindate;

    /**
     * 有效截止日期
     */
    private String enddate;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 商品助记符
     */
    private String spell;

    /**
     * 商品条形码
     */
    private String barcode;

    /**
     * 单位
     */
    private String mainunit;

    /**
     * 单位名称
     */
    private String mainunitname;

    /**
     * 调价数量下限
     */
    private BigDecimal lownum;

    /**
     * 调价数量上限
     */
    private BigDecimal upnum;

    /**
     * 数量区间
     */
    private String numextent;

    /**
     * 特价
     */
    private BigDecimal offprice;

    /**
     * 原价
     */
    private BigDecimal oldprice;

    /**
     * 备注
     */
    private String remark;

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public String getCustomertypename() {
        return customertypename;
    }

    public void setCustomertypename(String customertypename) {
        this.customertypename = customertypename;
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

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
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

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMainunitname() {
        return mainunitname;
    }

    public void setMainunitname(String mainunitname) {
        this.mainunitname = mainunitname;
    }

    public String getNumextent() {
        return numextent;
    }

    public void setNumextent(String numextent) {
        this.numextent = numextent;
    }

    public BigDecimal getOffprice() {
        return offprice;
    }

    public void setOffprice(BigDecimal offprice) {
        this.offprice = offprice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getLownum() {
        return lownum;
    }

    public void setLownum(BigDecimal lownum) {
        this.lownum = lownum;
    }

    public BigDecimal getUpnum() {
        return upnum;
    }

    public void setUpnum(BigDecimal upnum) {
        this.upnum = upnum;
    }

    public String getMainunit() {
        return mainunit;
    }

    public void setMainunit(String mainunit) {
        this.mainunit = mainunit;
    }

    public BigDecimal getOldprice() {
        return oldprice;
    }

    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }
}
