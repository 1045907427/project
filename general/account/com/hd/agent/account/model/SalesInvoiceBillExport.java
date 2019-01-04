package com.hd.agent.account.model;

import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.TaxType;

import java.math.BigDecimal;

/**
 * Created by luoq on 2018/3/2.
 */
public class SalesInvoiceBillExport {
    /**
     * 编号
     */
    private String id;

    /**
     * 发票号
     */
    private String invoiceno;

    /**
     * 单据类型 1销售开票，2冲差单
     */
    private String ordertype;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 单据类型1正常开票2预开票
     */
    private String billtype;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusname;

    /**
     * 客户(总店客户)
     */
    private String customerid;

    /**
     * 客户名称
     */
    private String customername;

    /**
     * 是否核销1是0否
     */
    private String iswriteoff;

    /**
     * 来源编号
     */
    private String sourceid;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;

    /**
     * 数量(辅计量)
     */
    private BigDecimal auxnum;

    /**
     * 数量(辅计量显示)
     */
    private String auxnumdetail;

    /**
     * 含税金额
     */
    private BigDecimal taxamount;

    /**
     * 未税金额
     */
    private BigDecimal notaxamount;

    /**
     * 税种
     */
    private String taxtype;

    /**
     * 税种名称
     */
    private String taxtypename;
    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 成本价
     */
    private BigDecimal costprice;

    /**
     * 备注
     */
    private String dremark;

    /**
     * 商品档案信息
     */
    private GoodsInfo goodsInfo;

    /**
     * 税种档案
     */
    private TaxType taxTypeInfo;

    /**
     * 来源单据类型1销售回单2销售退货通知单3冲差单
     */
    private String sourcetype;

    /**
     * 是否折扣1是0否2返利折扣
     */
    private String isdiscount;

    /**
     * 含税单价
     */
    private BigDecimal dtaxprice;

    /**
     * 商品金额
     */
    private BigDecimal dtaxamount;

    /**
     * 未税金额
     */
    private BigDecimal dnotaxamount;

    /**
     * 未税单价
     */
    private BigDecimal dnotaxprice;

    /**
     * 税额
     */
    private BigDecimal dtax;

    /**
     * 是否品牌折扣1是0否
     */
    private String isbranddiscount;

    /**
     * 辅计量单位
     */
    private String auxunitid;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 客户余额
     */
    private BigDecimal customeramount;

    /**
     * 销售员
     */
    private String salesuser;

    /**
     * 销售员名称
     */
    private String salesusername;

    /**
     * 规格型号
     */
    private String model;

    /**
     * 箱装量
     */
    private BigDecimal boxnum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
    }

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDremark() {
        return dremark;
    }

    public void setDremark(String dremark) {
        this.dremark = dremark;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public TaxType getTaxTypeInfo() {
        return taxTypeInfo;
    }

    public void setTaxTypeInfo(TaxType taxTypeInfo) {
        this.taxTypeInfo = taxTypeInfo;
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

    public String getIswriteoff() {
        return iswriteoff;
    }

    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    public String getTaxtypename() {
        return taxtypename;
    }

    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
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
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public BigDecimal getAuxnum() {
        return auxnum;
    }

    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public String getIsdiscount() {
        return isdiscount;
    }

    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount;
    }

    public BigDecimal getDtaxprice() {
        return dtaxprice;
    }

    public void setDtaxprice(BigDecimal dtaxprice) {
        this.dtaxprice = dtaxprice;
    }

    public String getIsbranddiscount() {
        return isbranddiscount;
    }

    public void setIsbranddiscount(String isbranddiscount) {
        this.isbranddiscount = isbranddiscount;
    }

    public String getAuxunitid() {
        return auxunitid;
    }

    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public BigDecimal getNotaxamount() {
        return notaxamount;
    }

    public void setNotaxamount(BigDecimal notaxamount) {
        this.notaxamount = notaxamount;
    }

    public BigDecimal getCustomeramount() {
        return customeramount;
    }

    public void setCustomeramount(BigDecimal customeramount) {
        this.customeramount = customeramount;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getSalesusername() {
        return salesusername;
    }

    public void setSalesusername(String salesusername) {
        this.salesusername = salesusername;
    }

    public String getSalesuser() {
        return salesuser;
    }

    public void setSalesuser(String salesuser) {
        this.salesuser = salesuser;
    }

    public BigDecimal getDtaxamount() {
        return dtaxamount;
    }

    public void setDtaxamount(BigDecimal dtaxamount) {
        this.dtaxamount = dtaxamount;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(BigDecimal boxnum) {
        this.boxnum = boxnum;
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

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public BigDecimal getDnotaxamount() {
        return dnotaxamount;
    }

    public void setDnotaxamount(BigDecimal dnotaxamount) {
        this.dnotaxamount = dnotaxamount;
    }

    public BigDecimal getDnotaxprice() {
        return dnotaxprice;
    }

    public void setDnotaxprice(BigDecimal dnotaxprice) {
        this.dnotaxprice = dnotaxprice;
    }

    public BigDecimal getDtax() {
        return dtax;
    }

    public void setDtax(BigDecimal dtax) {
        this.dtax = dtax;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }
}
