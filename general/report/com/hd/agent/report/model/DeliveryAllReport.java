package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by luoq on 2016/8/31.
 */
public class DeliveryAllReport implements Serializable {
    private String brandid;
    private String brandname;
    private String barcode;
    private String supplierid;
    private String suppliername;
    private String storageid;
    private String storagename;
    private String unitid;
    private String unitname;
    /**
     * 辅单位编号
     */
    private String auxunitid;

    /**
     * 辅单位名称
     */
    private String auxunitname;
    private String goodsid;
    private String goodsname;
    /**
     * 期初数量
     */
    private BigDecimal existingnum;
    /**
     * 期初箱数
     */
    private BigDecimal existingbox;
    /**
     * 成本价
     */
    private BigDecimal price;
    private String initnum;
    private BigDecimal taxamount;
    private BigDecimal notaxamount;
    /**
     * 采购入库箱数
     */
    private BigDecimal intotalbox;
    /**
     * 采购入库数量
     */
    private BigDecimal intotalnum;

    private BigDecimal inbuyprice;
    private BigDecimal inprice;
    private BigDecimal intaxamount;
    private BigDecimal inaddcostprice;
    /**
     * 采购未税金额
     */
    private BigDecimal innotaxamount;
    /**
     * 退货箱数
     */
    private BigDecimal outtotalbox;
    /**
     * 退货数量
     */
    private BigDecimal outtotalnum;
    private BigDecimal outbuyprice;
    private BigDecimal outprice;
    private BigDecimal outtaxamount;
    private BigDecimal outaddcostprice;
    /**
     * 退货未税金额
     */
    private BigDecimal outnotaxamount;
    private BigDecimal saleintotalbox;
    /**
     * 销售退货数量
     */
    private BigDecimal saleintotalnum;
    private BigDecimal saleinbuyprice;
    private BigDecimal saleinprice;
    private BigDecimal saleintaxamount;
    /**
     * 销售退货出库未税 金额
     */
    private  BigDecimal saleinnotaxamount;
    private BigDecimal saleinaddcostprice;
    /**
     * 销售出库箱数
     */
    private BigDecimal saleouttotalbox;
    /**
     * 销售出库数量
     */
    private BigDecimal saleouttotalnum;
    private BigDecimal saleoutbuyprice;
    private BigDecimal saleoutprice;
    /**
     * 销售出库金额
     */
    private BigDecimal saleouttaxamount;
    /**
     * 销售出库成本价
     */
    private BigDecimal saleoutaddcostprice;
    /**
     * 销售出库未税金额
     */
    private BigDecimal saleoutnotaxamount;
    /**
     * 安全库存
     */
    private BigDecimal safenum;

    /**
     * 安全库存辅单位描述
     */
    private String auxsafenumdetail;

    /**
     * 合计成本金额
     * @return
     */
    private BigDecimal costoutamount;

    /**
     * 合计成本未税金额
     * @return
     */
    private BigDecimal costnotaxoutamount;

    /**
     * 初始辅单位描述
     */
    private String auxinitnumdetail;

    /**
     * 进货箱数
     */
    private String auxbuyinnumdetail;

    /**
     * 退货箱数
     */
    private String auxbuyoutnumdetail;

    /**
     * 入库数量辅单位描述
     */
    private String auxenternumdetail;

    /**
     * 销售箱数
     */
    private String auxsaleoutnumdetail;

    /**
     * 退货箱数
     */
    private String auxsaleinnumdetail;

    /**
     * 出库数量辅单位描述
     */
    private String auxoutnumdetail;

    /**
     * 期末数量辅单位描述
     */
    private String auxendnumdetail;

    /**
     * 期末结存数量
     */
    private BigDecimal endnum;

    /**
     * 期末合计箱数
     */
    private BigDecimal endtotalbox;

    /**
     * 期末结存金额
     */
    private BigDecimal endamount;

    private String taxtype;

    /**
     * 期末结存未税金额
     */
    private BigDecimal endnotaxamount;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getAuxunitid() {
        return auxunitid;
    }

    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid;
    }

    public String getAuxunitname() {
        return auxunitname;
    }

    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname;
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

    public String getInitnum() {
        return initnum;
    }

    public void setInitnum(String initnum) {
        this.initnum = initnum;
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

    public BigDecimal getIntotalbox() {
        return intotalbox;
    }

    public void setIntotalbox(BigDecimal intotalbox) {
        this.intotalbox = intotalbox;
    }

    public BigDecimal getInbuyprice() {
        return inbuyprice;
    }

    public void setInbuyprice(BigDecimal inbuyprice) {
        this.inbuyprice = inbuyprice;
    }

    public BigDecimal getInprice() {
        return inprice;
    }

    public void setInprice(BigDecimal inprice) {
        this.inprice = inprice;
    }

    public BigDecimal getIntaxamount() {
        return intaxamount;
    }

    public void setIntaxamount(BigDecimal intaxamount) {
        this.intaxamount = intaxamount;
    }

    public BigDecimal getInaddcostprice() {
        return inaddcostprice;
    }

    public void setInaddcostprice(BigDecimal inaddcostprice) {
        this.inaddcostprice = inaddcostprice;
    }

    public BigDecimal getOuttotalbox() {
        return outtotalbox;
    }

    public void setOuttotalbox(BigDecimal outtotalbox) {
        this.outtotalbox = outtotalbox;
    }

    public BigDecimal getOutbuyprice() {
        return outbuyprice;
    }

    public void setOutbuyprice(BigDecimal outbuyprice) {
        this.outbuyprice = outbuyprice;
    }

    public BigDecimal getOutprice() {
        return outprice;
    }

    public void setOutprice(BigDecimal outprice) {
        this.outprice = outprice;
    }

    public BigDecimal getOuttaxamount() {
        return outtaxamount;
    }

    public void setOuttaxamount(BigDecimal outtaxamount) {
        this.outtaxamount = outtaxamount;
    }

    public BigDecimal getOutaddcostprice() {
        return outaddcostprice;
    }

    public void setOutaddcostprice(BigDecimal outaddcostprice) {
        this.outaddcostprice = outaddcostprice;
    }

    public BigDecimal getSaleintotalbox() {
        return saleintotalbox;
    }

    public void setSaleintotalbox(BigDecimal saleintotalbox) {
        this.saleintotalbox = saleintotalbox;
    }

    public BigDecimal getSaleinbuyprice() {
        return saleinbuyprice;
    }

    public void setSaleinbuyprice(BigDecimal saleinbuyprice) {
        this.saleinbuyprice = saleinbuyprice;
    }

    public BigDecimal getSaleinprice() {
        return saleinprice;
    }

    public void setSaleinprice(BigDecimal saleinprice) {
        this.saleinprice = saleinprice;
    }

    public BigDecimal getSaleintaxamount() {
        return saleintaxamount;
    }

    public void setSaleintaxamount(BigDecimal saleintaxamount) {
        this.saleintaxamount = saleintaxamount;
    }

    public BigDecimal getSaleinaddcostprice() {
        return saleinaddcostprice;
    }

    public void setSaleinaddcostprice(BigDecimal saleinaddcostprice) {
        this.saleinaddcostprice = saleinaddcostprice;
    }

    public BigDecimal getSaleouttotalbox() {
        return saleouttotalbox;
    }

    public void setSaleouttotalbox(BigDecimal saleouttotalbox) {
        this.saleouttotalbox = saleouttotalbox;
    }

    public BigDecimal getSaleoutbuyprice() {
        return saleoutbuyprice;
    }

    public void setSaleoutbuyprice(BigDecimal saleoutbuyprice) {
        this.saleoutbuyprice = saleoutbuyprice;
    }

    public BigDecimal getSaleoutprice() {
        return saleoutprice;
    }

    public void setSaleoutprice(BigDecimal saleoutprice) {
        this.saleoutprice = saleoutprice;
    }

    public BigDecimal getSaleouttaxamount() {
        return saleouttaxamount;
    }

    public void setSaleouttaxamount(BigDecimal saleouttaxamount) {
        this.saleouttaxamount = saleouttaxamount;
    }

    public BigDecimal getSaleoutaddcostprice() {
        return saleoutaddcostprice;
    }

    public void setSaleoutaddcostprice(BigDecimal saleoutaddcostprice) {
        this.saleoutaddcostprice = saleoutaddcostprice;
    }

    public BigDecimal getSafenum() {
        return safenum;
    }

    public void setSafenum(BigDecimal safenum) {
        this.safenum = safenum;
    }

    public String getAuxsafenumdetail() {
        return auxsafenumdetail;
    }

    public void setAuxsafenumdetail(String auxsafenumdetail) {
        this.auxsafenumdetail = auxsafenumdetail;
    }

    public BigDecimal getExistingnum() {
        return existingnum;
    }

    public void setExistingnum(BigDecimal existingnum) {
        this.existingnum = existingnum;
    }

    public BigDecimal getExistingbox() {
        return existingbox;
    }

    public void setExistingbox(BigDecimal existingbox) {
        this.existingbox = existingbox;
    }

    public BigDecimal getIntotalnum() {
        return intotalnum;
    }

    public void setIntotalnum(BigDecimal intotalnum) {
        this.intotalnum = intotalnum;
    }

    public BigDecimal getInnotaxamount() {
        return innotaxamount;
    }

    public void setInnotaxamount(BigDecimal innotaxamount) {
        this.innotaxamount = innotaxamount;
    }

    public BigDecimal getOutnotaxamount() {
        return outnotaxamount;
    }

    public void setOutnotaxamount(BigDecimal outnotaxamount) {
        this.outnotaxamount = outnotaxamount;
    }

    public BigDecimal getSaleoutnotaxamount() {
        return saleoutnotaxamount;
    }

    public void setSaleoutnotaxamount(BigDecimal saleoutnotaxamount) {
        this.saleoutnotaxamount = saleoutnotaxamount;
    }

    public BigDecimal getSaleinnotaxamount() {
        return saleinnotaxamount;
    }

    public void setSaleinnotaxamount(BigDecimal saleinnotaxamount) {
        this.saleinnotaxamount = saleinnotaxamount;
    }

    public BigDecimal getOuttotalnum() {
        return outtotalnum;
    }

    public void setOuttotalnum(BigDecimal outtotalnum) {
        this.outtotalnum = outtotalnum;
    }

    public BigDecimal getSaleintotalnum() {
        return saleintotalnum;
    }

    public void setSaleintotalnum(BigDecimal saleintotalnum) {
        this.saleintotalnum = saleintotalnum;
    }

    public BigDecimal getSaleouttotalnum() {
        return saleouttotalnum;
    }

    public void setSaleouttotalnum(BigDecimal saleouttotalnum) {
        this.saleouttotalnum = saleouttotalnum;
    }

    public BigDecimal getCostoutamount() {
        return costoutamount;
    }

    public void setCostoutamount(BigDecimal costoutamount) {
        this.costoutamount = costoutamount;
    }

    public BigDecimal getCostnotaxoutamount() {
        return costnotaxoutamount;
    }

    public void setCostnotaxoutamount(BigDecimal costnotaxoutamount) {
        this.costnotaxoutamount = costnotaxoutamount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAuxinitnumdetail() {
        return auxinitnumdetail;
    }

    public void setAuxinitnumdetail(String auxinitnumdetail) {
        this.auxinitnumdetail = auxinitnumdetail;
    }

    public String getAuxbuyinnumdetail() {
        return auxbuyinnumdetail;
    }

    public void setAuxbuyinnumdetail(String auxbuyinnumdetail) {
        this.auxbuyinnumdetail = auxbuyinnumdetail;
    }

    public String getAuxbuyoutnumdetail() {
        return auxbuyoutnumdetail;
    }

    public void setAuxbuyoutnumdetail(String auxbuyoutnumdetail) {
        this.auxbuyoutnumdetail = auxbuyoutnumdetail;
    }

    public String getAuxenternumdetail() {
        return auxenternumdetail;
    }

    public void setAuxenternumdetail(String auxenternumdetail) {
        this.auxenternumdetail = auxenternumdetail;
    }

    public String getAuxsaleoutnumdetail() {
        return auxsaleoutnumdetail;
    }

    public void setAuxsaleoutnumdetail(String auxsaleoutnumdetail) {
        this.auxsaleoutnumdetail = auxsaleoutnumdetail;
    }

    public String getAuxsaleinnumdetail() {
        return auxsaleinnumdetail;
    }

    public void setAuxsaleinnumdetail(String auxsaleinnumdetail) {
        this.auxsaleinnumdetail = auxsaleinnumdetail;
    }

    public String getAuxoutnumdetail() {
        return auxoutnumdetail;
    }

    public void setAuxoutnumdetail(String auxoutnumdetail) {
        this.auxoutnumdetail = auxoutnumdetail;
    }

    public String getAuxendnumdetail() {
        return auxendnumdetail;
    }

    public void setAuxendnumdetail(String auxendnumdetail) {
        this.auxendnumdetail = auxendnumdetail;
    }

    public BigDecimal getEndnum() {
        return endnum;
    }

    public void setEndnum(BigDecimal endnum) {
        this.endnum = endnum;
    }

    public BigDecimal getEndtotalbox() {
        return endtotalbox;
    }

    public void setEndtotalbox(BigDecimal endtotalbox) {
        this.endtotalbox = endtotalbox;
    }

    public BigDecimal getEndamount() {
        return endamount;
    }

    public void setEndamount(BigDecimal endamount) {
        this.endamount = endamount;
    }

    public BigDecimal getEndnotaxamount() {
        return endnotaxamount;
    }

    public void setEndnotaxamount(BigDecimal endnotaxamount) {
        this.endnotaxamount = endnotaxamount;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }
}
