package com.hd.agent.storage.model;

import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;

import java.math.BigDecimal;

/**
 * Created by luoq on 2018/3/2.
 */
public class SaleoutExport {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusname;

    /**
     * 客户
     */
    private Customer customerInfo;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 客户业务员
     */
    private String salesuser;
    /**
     * 客户业务员姓名
     */
    private String salesusername;
    /**
     * 出库仓库
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 销售订单编号
     */
    private String saleorderid;
    /**
     * 来源类型0无1销售发货通知单
     */
    private String sourcetype;

    /**
     * 来源编号
     */
    private String sourceid;

    /**
     * 0未开票1已开票4开票中

     */
    private String isinvoicebill;

    /**
     * 销售内勤人员编号
     */
    private String indooruserid;
    /**
     * 销售内勤人员名称
     */
    private String indoorusername;

    /**
     * 商品编码
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
     * 商品信息
     */
    private GoodsInfo goodsInfo;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 销售部门
     */
    private String salesdept;
    /**
     * 销售部门名称
     */
    private String salesdeptname;

    /**
     * 仓库发货人
     */
    private String storager;
    /**
     * 仓库发货人
     */
    private String storagername;

    /**
     * 税种
     */
    private String taxtype;
    /**
     * 税种名称
     */
    private String taxtypename;

    /**
     * 库位
     */
    private String storagelocationid;
    /**
     * 库位名称
     */
    private String storagelocationname;

    /**
     * 是否折扣1是0否
     */
    private String isdiscount;
    /**
     * 是否品牌折扣1是0否
     */
    private String isbranddiscount;

    /**
     * 数量(辅计量显示)
     */
    private String auxnumdetail;

    /**
     * 含税单价
     */
    private BigDecimal taxprice;

    /**
     * 成本单价
     */
    private BigDecimal costprice;

    /**
     * 金额
     */
    private BigDecimal taxamount;

    /**
     * 是否足够发货
     */
    private String isenough;

    /**
     * 批次现存量编号
     */
    private String summarybatchid;

    /**
     * 备注
     * @return
     */
    private String dremark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
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

    public String getSalesuser() {
        return salesuser;
    }

    public void setSalesuser(String salesuser) {
        this.salesuser = salesuser;
    }

    public String getSalesusername() {
        return salesusername;
    }

    public void setSalesusername(String salesusername) {
        this.salesusername = salesusername;
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

    public String getSaleorderid() {
        return saleorderid;
    }

    public void setSaleorderid(String saleorderid) {
        this.saleorderid = saleorderid;
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

    public String getIsinvoicebill() {
        return isinvoicebill;
    }

    public void setIsinvoicebill(String isinvoicebill) {
        this.isinvoicebill = isinvoicebill;
    }

    public String getIndooruserid() {
        return indooruserid;
    }

    public void setIndooruserid(String indooruserid) {
        this.indooruserid = indooruserid;
    }

    public String getIndoorusername() {
        return indoorusername;
    }

    public void setIndoorusername(String indoorusername) {
        this.indoorusername = indoorusername;
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

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public String getSalesdept() {
        return salesdept;
    }

    public void setSalesdept(String salesdept) {
        this.salesdept = salesdept;
    }

    public String getSalesdeptname() {
        return salesdeptname;
    }

    public void setSalesdeptname(String salesdeptname) {
        this.salesdeptname = salesdeptname;
    }

    public String getStorager() {
        return storager;
    }

    public void setStorager(String storager) {
        this.storager = storager;
    }

    public String getStoragername() {
        return storagername;
    }

    public void setStoragername(String storagername) {
        this.storagername = storagername;
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

    public String getStoragelocationid() {
        return storagelocationid;
    }

    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid;
    }

    public String getStoragelocationname() {
        return storagelocationname;
    }

    public void setStoragelocationname(String storagelocationname) {
        this.storagelocationname = storagelocationname;
    }

    public String getIsdiscount() {
        return isdiscount;
    }

    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount;
    }

    public String getIsbranddiscount() {
        return isbranddiscount;
    }

    public void setIsbranddiscount(String isbranddiscount) {
        this.isbranddiscount = isbranddiscount;
    }

    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail;
    }

    public BigDecimal getTaxprice() {
        return taxprice;
    }

    public void setTaxprice(BigDecimal taxprice) {
        this.taxprice = taxprice;
    }

    public String getIsenough() {
        return isenough;
    }

    public void setIsenough(String isenough) {
        this.isenough = isenough;
    }

    public String getSummarybatchid() {
        return summarybatchid;
    }

    public void setSummarybatchid(String summarybatchid) {
        this.summarybatchid = summarybatchid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDremark() {
        return dremark;
    }

    public void setDremark(String dremark) {
        this.dremark = dremark;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }
}
