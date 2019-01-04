/**
 * @(#)CustomerSalesFlow.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 22, 2013 chenwei 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 客户销售情况流水
 * @author chenwei
 */
public class CustomerSalesFlow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -244860337760326720L;
	
	/**
	 * 是否小计
	 */
	private String isbitsum;
	/**
	 * 单据编号
	 */
	private String id;
	
	/**
	 * 订单编号
	 */
	private String orderid;
	
	/**
	 * 业务日期
	 */
	private String businessdate;
	/**
	 * 单据类型1销售发货单2销售退货通知单3应收款冲差
	 */
	private String billtype;
	
	/**
	 * 单据类型名称
	 */
	private String billtypename;
	/**
	 * 客户编号
	 */
	private String customerid;
	/**
	 * 客户名称
	 */
	private String customername;
	/**
	 * 总店客户编号
	 */
	private String pcustomerid;
	/**
	 * 总店名称
	 */
	private String pcustomername;

	/**
	 * 客户分类
	 */
	private String customersort;

	/**
	 * 客户分类名称
	 */
	private String customersortname;
	/**
	 * 销售区域
	 */
	private String salesarea;
	/**
	 * 销售区域名称
	 */
	private String salesareaname;

	/**
	 * 客户业务员
	 */
	private String salesuser;

	/**
	 * 客户业务员名称
	 */
	private String salesusername;
	/**
	 * 品牌编号
	 */
	private String brandid;

	/**
	 * 品牌名称
	 */
	private String brandname;

	/**
	 * 箱装量
	 */
	private BigDecimal boxnum;
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
	 * 商品规格型号
	 */
	private String model;
	
	/**
	 * 应收日期
	 */
	private String duefromdate;
	
	/**
	 * 是否超账
	 */
	private String isultra;
	
	/**
	 * 条形码
	 */
	private String barcode;
	/**
	 * 商品单位
	 */
	private String unitid;
	/**
	 * 商品单位名称
	 */
	private String unitname;
	/**
	 * 商品单价
	 */
	private BigDecimal price;
	
	/**
	 * 商品未税单价
	 */
	private BigDecimal noprice;
	/**
	 * 初始单价（合同价 价格套价格）
	 */
	private BigDecimal initprice;
	/**
	 * 成本价
	 */
	private BigDecimal costprice;
	/**
	 * 商品数量
	 */
	private BigDecimal unitnum;
	/**
	 * 商品箱数
	 */
	private BigDecimal auxnum;

    /**
     * 箱价
     */
    private BigDecimal boxprice;
	/**
	 * 商品个数
	 */
	private BigDecimal auxremainder;
	/**
	 * 商品辅单位
	 */
	private String auxnumdetail;
	/**
	 * 合计箱数
	 */
	private BigDecimal totalbox;
	/**
	 * 商品金额
	 */
	private BigDecimal taxamount;
	
	/**
	 * 商品未税金额
	 */
	private BigDecimal notaxamount;
	
	/**
	 * 商品税额
	 */
	private BigDecimal tax;
	/**
	 * 成本金额
	 */
	private BigDecimal costamount;

	/**
	 * 毛利额
	 */
	private BigDecimal marginamount;

	/**
	 * 毛利率
	 */
	private BigDecimal marginamountrate;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否折扣
	 */
	private String isdiscount;
	/**
	 * 是否抽单 0未申请 1已申请
	 */
	private String isinvoice;
	/**
	 * 是否核销 1是0否
	 */
	private String iswriteoff;

	/**
	 * 申请状态名称
	 */
	private String isinvoicename;
	
	/**
	 * 核销状态
	 */
	private String writeoffname;
	/**
	 * 抽单日期
	 */
	private String invoicedate;
	/**
	 * 核销日期
	 */
	private String writeoffdate;

	/**
	 * 是否开票
	 */
	private String isinvoicebill;

	/**
	 * 是否开票名称
	 */
	private String isinvoicebillname;

	/**
	 * 开票日期
	 */
	private String invoicebilldate;
	
	/**
	 * 税种编码
	 */
	private String taxtype;
	/**
	 * 税种名称
	 */
	private String taxtypename;

    /**
     * 发货仓库
     * @return
     */
	private String deliveryStorage;

	/**
	 * 发货仓库名称
	 */
	private String deliverystoragename;

    public String getDeliveryStorage() {
        return deliveryStorage;
    }

    public void setDeliveryStorage(String deliveryStorage) {
        this.deliveryStorage = deliveryStorage;
    }

	public String getDeliverystoragename() {
		return deliverystoragename;
	}

	public void setDeliverystoragename(String deliverystoragename) {
		this.deliverystoragename = deliverystoragename;
	}

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
	public BigDecimal getUnitnum() {
		return unitnum;
	}
	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}
	public BigDecimal getTaxamount() {
		return taxamount;
	}
	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsdiscount() {
		return isdiscount;
	}
	public void setIsdiscount(String isdiscount) {
		this.isdiscount = isdiscount;
	}
	public String getIsinvoice() {
		return isinvoice;
	}
	public void setIsinvoice(String isinvoice) {
		this.isinvoice = isinvoice;
	}
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getPcustomerid() {
		return pcustomerid;
	}
	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}
	public String getPcustomername() {
		return pcustomername;
	}
	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}
	public String getBilltypename() {
		return billtypename;
	}
	public void setBilltypename(String billtypename) {
		this.billtypename = billtypename;
	}
	public String getIsinvoicename() {
		return isinvoicename;
	}
	public void setIsinvoicename(String isinvoicename) {
		this.isinvoicename = isinvoicename;
	}
	public String getWriteoffname() {
		return writeoffname;
	}
	public void setWriteoffname(String writeoffname) {
		this.writeoffname = writeoffname;
	}
	public String getIswriteoff() {
		return iswriteoff;
	}
	public void setIswriteoff(String iswriteoff) {
		this.iswriteoff = iswriteoff;
	}
	public BigDecimal getCostprice() {
		return costprice;
	}
	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}
	public BigDecimal getCostamount() {
		return costamount;
	}
	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}
	public String getInvoicedate() {
		return invoicedate;
	}
	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}
	public String getWriteoffdate() {
		return writeoffdate;
	}
	public void setWriteoffdate(String writeoffdate) {
		this.writeoffdate = writeoffdate;
	}
	public BigDecimal getInitprice() {
		return initprice;
	}
	public void setInitprice(BigDecimal initprice) {
		this.initprice = initprice;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public BigDecimal getAuxnum() {
		return auxnum;
	}
	public void setAuxnum(BigDecimal auxnum) {
		this.auxnum = auxnum;
	}
	public BigDecimal getAuxremainder() {
		return auxremainder;
	}
	public void setAuxremainder(BigDecimal auxremainder) {
		this.auxremainder = auxremainder;
	}
	public String getSalesarea() {
		return salesarea;
	}
	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}
	public String getSalesareaname() {
		return salesareaname;
	}
	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}
	public String getDuefromdate() {
		return duefromdate;
	}
	public void setDuefromdate(String duefromdate) {
		this.duefromdate = duefromdate;
	}
	public String getIsultra() {
		return isultra;
	}
	public void setIsultra(String isultra) {
		this.isultra = isultra;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getAuxnumdetail() {
		return auxnumdetail;
	}
	public void setAuxnumdetail(String auxnumdetail) {
		this.auxnumdetail = auxnumdetail;
	}
	public String getSpell() {
		return spell;
	}
	public void setSpell(String spell) {
		this.spell = spell;
	}
	public BigDecimal getNoprice() {
		return noprice;
	}
	public void setNoprice(BigDecimal noprice) {
		this.noprice = noprice;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getIsbitsum() {
		return null != isbitsum ? isbitsum : "0";
	}
	public void setIsbitsum(String isbitsum) {
		this.isbitsum = isbitsum;
	}
	public BigDecimal getTotalbox() {
		return totalbox;
	}
	public void setTotalbox(BigDecimal totalbox) {
		this.totalbox = totalbox;
	}

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }

	public String getInvoicebilldate() {
		return invoicebilldate;
	}

	public void setInvoicebilldate(String invoicebilldate) {
		this.invoicebilldate = invoicebilldate;
	}

	public String getIsinvoicebill() {
		return isinvoicebill;
	}

	public void setIsinvoicebill(String isinvoicebill) {
		this.isinvoicebill = isinvoicebill;
	}

	public String getIsinvoicebillname() {
		return isinvoicebillname;
	}

	public void setIsinvoicebillname(String isinvoicebillname) {
		this.isinvoicebillname = isinvoicebillname;
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

	public BigDecimal getMarginamount() {
		return marginamount;
	}

	public void setMarginamount(BigDecimal marginamount) {
		this.marginamount = marginamount;
	}

	public BigDecimal getMarginamountrate() {
		return marginamountrate;
	}

	public void setMarginamountrate(BigDecimal marginamountrate) {
		this.marginamountrate = marginamountrate;
	}

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}

	public String getCustomersortname() {
		return customersortname;
	}

	public void setCustomersortname(String customersortname) {
		this.customersortname = customersortname;
	}

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}
}

