/**
 * @(#)PurchaseOrderDetail.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 9, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;

import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class PurchaseOrderDetail {

	private static final long serialVersionUID = 1L;
	
	/**
     * 编号
     */
    private Integer id;

    /**
     * 采购进货单编号
     */
    private String orderid;

    /**
     * 商品编号
     */
    private String goodsid;
    /**
     * 商品档案
     */
    private GoodsInfo goodsInfo;

    /**
     * 计量单位编号
     */
    private String unitid;

    /**
     * 计量单位名称
     */
    private String unitname;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 辅计量单位编号
     */
    private String auxunitid;

    /**
     * 辅计量单位名称
     */
    private String auxunitname;

    /**
     * 数量(辅计量)
     */
    private BigDecimal auxnum;

    /**
     * 辅计量单位数量描述
     */
    private String auxnumdetail;

    /**
     * 辅单位余数数量（主单位换算后剩余的数量）
     */
    private BigDecimal auxremainder;

    /**
     * 含税单价
     */
    private BigDecimal taxprice;

    /**
     * 含税金额
     */
    private BigDecimal taxamount;

    /**
     * 税种
     */
    private String taxtype;
    /**
     * 税种名称
     */
    private String taxtypename;

    /**
     * 无税单价
     */
    private BigDecimal notaxprice;

    /**
     * 无税金额
     */
    private BigDecimal notaxamount;

    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 备注
     */
    private String remark;

    /**
     * 到货日期
     */
    private String arrivedate;

    /**
     * 批次号
     */
    private String batchno;

    /**
     * 生产日期
     */
    private String produceddate;

    /**
     * 有效截止日期
     */
    private String deadline;

    /**
     * 表体自定义项1
     */
    private String field01;

    /**
     * 表体自定义项2
     */
    private String field02;

    /**
     * 表体自定义项3
     */
    private String field03;

    /**
     * 表体自定义项4
     */
    private String field04;

    /**
     * 表体自定义项5
     */
    private String field05;

    /**
     * 表体自定义项6
     */
    private String field06;

    /**
     * 表体自定义项7
     */
    private String field07;

    /**
     * 表体自定义项8
     */
    private String field08;

    /**
     * 排序(订单明细显示序号)
     */
    private Integer seq;

    /**
     * 来源单据编号
     */
    private String billno;

    /**
     * 来源单位明细编号
     */
    private String billdetailno;

    /**
     * 已开票含税金额
     */
    private BigDecimal invoicetaxamount;

    /**
     * 已开票无税金额
     */
    private BigDecimal invoicenotaxamount;

    /**
     * 未开票无税金额
     */
    private BigDecimal uninvoicetaxamount;

    /**
     * 未开票含税金额
     */
    private BigDecimal uninvoicenotaxamount;
    
    /**
     * 已开票数量
     */
    private BigDecimal invoicenum;
    
    /**
     * 未开票数量
     */
    private BigDecimal uninvoicenum;
    
    /**
     * 已审核数量
     */
    private BigDecimal auditnum;
    /**
     * 开票引用，1引用，0未引用
     */
    private String invoicerefer;
    /**
     * 是否核销1是0否
     */
    private String iswriteoff;
    
    /**
     * 批次现存量编号
     */
    private String summarybatchid;
    /**
     * 仓库编号
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 库位编号
     */
    private String storagelocationid;
    /**
     * 库位名称
     */
    private String storagelocationname;


    /**
     * 已退货入库主单位数量
     */
    private BigDecimal returnleftunitnum;

    /**
     * 已退货入库辅单位数量
     */
    private BigDecimal returnleftauxnum;

    /**
     * 未退货入库主单位数量
     */
    private BigDecimal unreturnunitnum;

    /**
     * 未退货入库辅单位数量
     */
    private BigDecimal unreturnauxnum;

    /**
     * 已退货无税金额
     */
    private BigDecimal returnlefnotaxamount;

    /**
     * 已退货含税金额
     */
    private BigDecimal returnleftaxamount;

    /**
     * 未退货无税金额
     */
    private BigDecimal unreturnnotaxamount;

    /**
     * 未退货含税金额
     */
    private BigDecimal unreturntaxamount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
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

	public BigDecimal getUnitnum() {
		return unitnum;
	}

	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
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

	public BigDecimal getAuxremainder() {
		return auxremainder;
	}

	public void setAuxremainder(BigDecimal auxremainder) {
		this.auxremainder = auxremainder;
	}

	public BigDecimal getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
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

	public BigDecimal getNotaxprice() {
		return notaxprice;
	}

	public void setNotaxprice(BigDecimal notaxprice) {
		this.notaxprice = notaxprice;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getArrivedate() {
		return arrivedate;
	}

	public void setArrivedate(String arrivedate) {
		this.arrivedate = arrivedate;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getProduceddate() {
		return produceddate;
	}

	public void setProduceddate(String produceddate) {
		this.produceddate = produceddate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getField01() {
		return field01;
	}

	public void setField01(String field01) {
		this.field01 = field01;
	}

	public String getField02() {
		return field02;
	}

	public void setField02(String field02) {
		this.field02 = field02;
	}

	public String getField03() {
		return field03;
	}

	public void setField03(String field03) {
		this.field03 = field03;
	}

	public String getField04() {
		return field04;
	}

	public void setField04(String field04) {
		this.field04 = field04;
	}

	public String getField05() {
		return field05;
	}

	public void setField05(String field05) {
		this.field05 = field05;
	}

	public String getField06() {
		return field06;
	}

	public void setField06(String field06) {
		this.field06 = field06;
	}

	public String getField07() {
		return field07;
	}

	public void setField07(String field07) {
		this.field07 = field07;
	}

	public String getField08() {
		return field08;
	}

	public void setField08(String field08) {
		this.field08 = field08;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	public BigDecimal getInvoicetaxamount() {
		return invoicetaxamount;
	}

	public void setInvoicetaxamount(BigDecimal invoicetaxamount) {
		this.invoicetaxamount = invoicetaxamount;
	}

	public BigDecimal getInvoicenotaxamount() {
		return invoicenotaxamount;
	}

	public void setInvoicenotaxamount(BigDecimal invoicenotaxamount) {
		this.invoicenotaxamount = invoicenotaxamount;
	}

	public BigDecimal getUninvoicetaxamount() {
		return uninvoicetaxamount;
	}

	public void setUninvoicetaxamount(BigDecimal uninvoicetaxamount) {
		this.uninvoicetaxamount = uninvoicetaxamount;
	}

	public BigDecimal getUninvoicenotaxamount() {
		return uninvoicenotaxamount;
	}

	public void setUninvoicenotaxamount(BigDecimal uninvoicenotaxamount) {
		this.uninvoicenotaxamount = uninvoicenotaxamount;
	}

	public BigDecimal getInvoicenum() {
		return invoicenum;
	}

	public void setInvoicenum(BigDecimal invoicenum) {
		this.invoicenum = invoicenum;
	}

	public BigDecimal getUninvoicenum() {
		return uninvoicenum;
	}

	public void setUninvoicenum(BigDecimal uninvoicenum) {
		this.uninvoicenum = uninvoicenum;
	}

	public BigDecimal getAuditnum() {
		return auditnum;
	}

	public void setAuditnum(BigDecimal auditnum) {
		this.auditnum = auditnum;
	}

	public String getInvoicerefer() {
		return invoicerefer;
	}

	public void setInvoicerefer(String invoicerefer) {
		this.invoicerefer = invoicerefer;
	}

	public String getIswriteoff() {
		return iswriteoff;
	}

	public void setIswriteoff(String iswriteoff) {
		this.iswriteoff = iswriteoff;
	}

	public String getSummarybatchid() {
		return summarybatchid;
	}

	public void setSummarybatchid(String summarybatchid) {
		this.summarybatchid = summarybatchid;
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

	public BigDecimal getReturnleftunitnum() {
		return returnleftunitnum;
	}

	public void setReturnleftunitnum(BigDecimal returnleftunitnum) {
		this.returnleftunitnum = returnleftunitnum;
	}

	public BigDecimal getReturnleftauxnum() {
		return returnleftauxnum;
	}

	public void setReturnleftauxnum(BigDecimal returnleftauxnum) {
		this.returnleftauxnum = returnleftauxnum;
	}

	public BigDecimal getUnreturnunitnum() {
		return unreturnunitnum;
	}

	public void setUnreturnunitnum(BigDecimal unreturnunitnum) {
		this.unreturnunitnum = unreturnunitnum;
	}

	public BigDecimal getUnreturnauxnum() {
		return unreturnauxnum;
	}

	public void setUnreturnauxnum(BigDecimal unreturnauxnum) {
		this.unreturnauxnum = unreturnauxnum;
	}

	public BigDecimal getReturnlefnotaxamount() {
		return returnlefnotaxamount;
	}

	public void setReturnlefnotaxamount(BigDecimal returnlefnotaxamount) {
		this.returnlefnotaxamount = returnlefnotaxamount;
	}

	public BigDecimal getReturnleftaxamount() {
		return returnleftaxamount;
	}

	public void setReturnleftaxamount(BigDecimal returnleftaxamount) {
		this.returnleftaxamount = returnleftaxamount;
	}

	public BigDecimal getUnreturnnotaxamount() {
		return unreturnnotaxamount;
	}

	public void setUnreturnnotaxamount(BigDecimal unreturnnotaxamount) {
		this.unreturnnotaxamount = unreturnnotaxamount;
	}

	public BigDecimal getUnreturntaxamount() {
		return unreturntaxamount;
	}

	public void setUnreturntaxamount(BigDecimal unreturntaxamount) {
		this.unreturntaxamount = unreturntaxamount;
	}
}

