/**
 * @(#)ArrivalOrderDetail.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-21 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.model;

import com.hd.agent.basefiles.model.GoodsInfo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购进货单明细
 *
 * @author zhanghonghui
 */
public class ArrivalOrderDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1869174487029481952L;

    private String ordertype;
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
     * 合计箱数
     */
    private BigDecimal totalbox;
    /**
     * 含税单价
     */
    private BigDecimal taxprice;
    /**
     * 含税箱价
     */
    private BigDecimal boxprice;
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
     * 未税单价
     */
    private BigDecimal noboxprice;
    /**
     * 无税金额
     */
    private BigDecimal notaxamount;

    /**
     * 税额
     */
    private BigDecimal tax;
    /**
     * 入库的成本价
     */
    private BigDecimal addcostprice;
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
     * 采购订单编号
     */
    private String buyorderid;

    /**
     * 已开票含税金额
     */
    private BigDecimal invoicetaxamount;

    /**
     * 已开票无税金额
     */
    private BigDecimal invoicenotaxamount;

    /**
     * 未开票含税金额
     */
    private BigDecimal uninvoicetaxamount;

    /**
     * 未开票无税金额
     */
    private BigDecimal uninvoicenotaxamount;
    /**
     * 核销金额
     */
    private BigDecimal writeoffamount;
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
     * 开票核销数量
     */
    private BigDecimal writeoffnum;
    /**
     * 品牌
     */
    private String brandid;

    /**
     * 分摊前金额
     */
    private BigDecimal oldtaxamount;

    /**
     * 分摊前未税金额
     */
    private BigDecimal oldnotaxamount;

    /**
     * 分摊前税额
     */
    private BigDecimal oldtax;

    /**
     * 分摊金额
     */
    private String changeamount;

    /**
     * 分摊前单价
     */
    private BigDecimal oldtaxprice;

    /**
     * 分摊前未税单价
     */
    private BigDecimal oldnotaxprice;

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
     * @return 采购单编号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid
     *            采购单编号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    /**
     * @return 商品编号
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid
     *            商品编号
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    /**
     * @return 计量单位编号
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid
     *            计量单位编号
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 计量单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname
     *            计量单位名称
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
     * @return 辅计量单位编号
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid
     *            辅计量单位编号
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid == null ? null : auxunitid.trim();
    }

    /**
     * @return 辅计量单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname
     *            辅计量单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname == null ? null : auxunitname.trim();
    }

    /**
     * @return 数量(辅计量)
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum
     *            数量(辅计量)
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 辅计量单位数量描述
     */
    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    /**
     * @param auxnumdetail
     *            辅计量单位数量描述
     */
    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail == null ? null : auxnumdetail.trim();
    }

    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
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
     * @return 税种
     */
    public String getTaxtype() {
        return taxtype;
    }

    /**
     * @param taxtype
     *            税种
     */
    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype == null ? null : taxtype.trim();
    }

    public String getTaxtypename() {
        return taxtypename;
    }

    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
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
     * @return 要求到货日期
     */
    public String getArrivedate() {
        return arrivedate;
    }

    /**
     * @param arrivedate
     *            要求到货日期
     */
    public void setArrivedate(String arrivedate) {
        this.arrivedate = arrivedate;
    }
    /**
     * @return 批次号
     */
    public String getBatchno() {
        return batchno;
    }

    /**
     * @param batchno
     *            批次号
     */
    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    /**
     * @return 生产日期
     */
    public String getProduceddate() {
        return produceddate;
    }

    /**
     * @param produceddate
     *            生产日期
     */
    public void setProduceddate(String produceddate) {
        this.produceddate = produceddate == null ? null : produceddate.trim();
    }

    /**
     * @return 有效截止日期
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * @param deadline
     *            有效截止日期
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline == null ? null : deadline.trim();
    }

    /**
     * @return 表体自定义项1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01
     *            表体自定义项1
     */
    public void setField01(String field01) {
        this.field01 = field01 == null ? null : field01.trim();
    }

    /**
     * @return 表体自定义项2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02
     *            表体自定义项2
     */
    public void setField02(String field02) {
        this.field02 = field02 == null ? null : field02.trim();
    }

    /**
     * @return 表体自定义项3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03
     *            表体自定义项3
     */
    public void setField03(String field03) {
        this.field03 = field03 == null ? null : field03.trim();
    }

    /**
     * @return 表体自定义项4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04
     *            表体自定义项4
     */
    public void setField04(String field04) {
        this.field04 = field04 == null ? null : field04.trim();
    }

    /**
     * @return 表体自定义项5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05
     *            表体自定义项5
     */
    public void setField05(String field05) {
        this.field05 = field05 == null ? null : field05.trim();
    }

    /**
     * @return 表体自定义项6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06
     *            表体自定义项6
     */
    public void setField06(String field06) {
        this.field06 = field06 == null ? null : field06.trim();
    }

    /**
     * @return 表体自定义项7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07
     *            表体自定义项7
     */
    public void setField07(String field07) {
        this.field07 = field07 == null ? null : field07.trim();
    }

    /**
     * @return 表体自定义项8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08
     *            表体自定义项8
     */
    public void setField08(String field08) {
        this.field08 = field08 == null ? null : field08.trim();
    }

    /**
     * @return 排序(订单明细显示序号)
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq
     *            排序(订单明细显示序号)
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * @return 来源单据编号
     */
    public String getBillno() {
        return billno;
    }

    /**
     * @param billno
     *            来源单据编号
     */
    public void setBillno(String billno) {
        this.billno = billno == null ? null : billno.trim();
    }

    /**
     * @return 来源单位明细编号
     */
    public String getBilldetailno() {
        return billdetailno;
    }

    /**
     * @param billdetailno
     *            来源单位明细编号
     */
    public void setBilldetailno(String billdetailno) {
        this.billdetailno = billdetailno == null ? null : billdetailno.trim();
    }

    /**
     * @return 已开票含税金额
     */
    public BigDecimal getInvoicetaxamount() {
        return invoicetaxamount;
    }

    /**
     * @param invoicetaxamount
     *            已开票含税金额
     */
    public void setInvoicetaxamount(BigDecimal invoicetaxamount) {
        this.invoicetaxamount = invoicetaxamount;
    }

    /**
     * @return 已开票无税金额
     */
    public BigDecimal getInvoicenotaxamount() {
        return invoicenotaxamount;
    }

    /**
     * @param invoicenotaxamount
     *            已开票无税金额
     */
    public void setInvoicenotaxamount(BigDecimal invoicenotaxamount) {
        this.invoicenotaxamount = invoicenotaxamount;
    }

    /**
     * @return 未开票含税金额
     */
    public BigDecimal getUninvoicetaxamount() {
        return uninvoicetaxamount;
    }

    /**
     * @param uninvoicetaxamount
     *            未开票含税金额
     */
    public void setUninvoicetaxamount(BigDecimal uninvoicetaxamount) {
        this.uninvoicetaxamount = uninvoicetaxamount;
    }

    /**
     * @return 未开票无税金额
     */
    public BigDecimal getUninvoicenotaxamount() {
        return uninvoicenotaxamount;
    }

    /**
     * @param uninvoicenotaxamount
     *            未开票无税金额
     */
    public void setUninvoicenotaxamount(BigDecimal uninvoicenotaxamount) {
        this.uninvoicenotaxamount = uninvoicenotaxamount;
    }

    public String getInvoicerefer() {
        return invoicerefer;
    }

    public void setInvoicerefer(String invoicerefer) {
        this.invoicerefer = invoicerefer;
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

    public String getIswriteoff() {
        return iswriteoff;
    }

    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getBuyorderid() {
        return buyorderid;
    }

    public void setBuyorderid(String buyorderid) {
        this.buyorderid = buyorderid;
    }

    public BigDecimal getWriteoffamount() {
        return writeoffamount;
    }

    public void setWriteoffamount(BigDecimal writeoffamount) {
        this.writeoffamount = writeoffamount;
    }

    public BigDecimal getWriteoffnum() {
        return writeoffnum;
    }

    public void setWriteoffnum(BigDecimal writeoffnum) {
        this.writeoffnum = writeoffnum;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }

    public BigDecimal getNoboxprice() {
        return noboxprice;
    }

    public void setNoboxprice(BigDecimal noboxprice) {
        this.noboxprice = noboxprice;
    }

    public BigDecimal getTotalbox() {
        return totalbox;
    }

    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    public BigDecimal getAddcostprice() {
        return addcostprice;
    }

    public void setAddcostprice(BigDecimal addcostprice) {
        this.addcostprice = addcostprice;
    }

    public BigDecimal getOldtaxamount() {
        return oldtaxamount;
    }

    public void setOldtaxamount(BigDecimal oldtaxamount) {
        this.oldtaxamount = oldtaxamount;
    }

    public BigDecimal getOldnotaxamount() {
        return oldnotaxamount;
    }

    public void setOldnotaxamount(BigDecimal oldnotaxamount) {
        this.oldnotaxamount = oldnotaxamount;
    }

    public BigDecimal getOldtax() {
        return oldtax;
    }

    public void setOldtax(BigDecimal oldtax) {
        this.oldtax = oldtax;
    }

    public String getChangeamount() {
        return changeamount;
    }

    public void setChangeamount(String changeamount) {
        this.changeamount = changeamount;
    }

    public BigDecimal getOldtaxprice() {
        return oldtaxprice;
    }

    public void setOldtaxprice(BigDecimal oldtaxprice) {
        this.oldtaxprice = oldtaxprice;
    }

    public BigDecimal getOldnotaxprice() {
        return oldnotaxprice;
    }

    public void setOldnotaxprice(BigDecimal oldnotaxprice) {
        this.oldnotaxprice = oldnotaxprice;
    }
}

