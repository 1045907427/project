package com.hd.agent.storage.model;

import com.hd.agent.basefiles.model.GoodsInfo;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 销售出库单明细
 * @author chenwei
 */
public class SaleoutDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 销售出库单编号
     */
    private String saleoutid;
    /**
     * 销售发货通知单编号
     */
    private String dispatchbillid;
    /**
     * 销售发货通知单明细编号
     */
    private String dispatchbilldetailid;

    /**
     * 批次现存量编号
     */
    private String summarybatchid;
    /**
     * 是否足够发货
     */
    private String isenough;
    /**
     * 是否折扣1是0否
     */
    private String isdiscount;
    /**
     * 是否品牌折扣1是0否
     */
    private String isbranddiscount;
    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 商品组编号
     */
    private String groupid;
    /**
     * 商品分类
     */
    private String goodssort;
    /**
     * 商品信息
     */
    private GoodsInfo goodsInfo;

    /**
     * 客户编码(大单发货单打印)
     */
    private String customerid;

    /**
     * 客户名称(大单发货单打印)
     */
    private String customername;

    /**
     * 客户数量(大单发货单打印)
     */
    private Integer customernum;

    /**
     * 商品规格
     */
    private String model;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 品牌业务员
     */
    private String branduser;
    /**
     * 品牌业务员员名称
     */
    private String brandusername;

    /**
     * 品牌部门
     */
    private String branddept;
    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 厂家业务员
     */
    private String supplieruser;
    /**
     * 发货仓库编码
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 库位
     */
    private String storagelocationid;
    /**
     * 库位名称
     */
    private String storagelocationname;
    /**
     * 批次号
     */
    private String batchno;

    /**
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;
    /**
     * 初始发货数量
     */
    private BigDecimal initnum;
    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 实际交货数量
     */
    private BigDecimal realsendnum;

    /**
     * 退回仓库数量
     */
    private BigDecimal returnnum;

    /**
     * 辅计量单位
     */
    private String auxunitid;

    /**
     * 辅计量单位名称
     */
    private String auxunitname;
    /**
     * 辅单位初始数量
     */
    private BigDecimal auxinitnum;
    /**
     * 数量(辅计量)
     */
    private BigDecimal auxnum;

    /**
     * 整箱数描述
     */
    private String totalauxnumdetail;

    /**
     * 辅单位余数数量
     */
    private BigDecimal auxremainder;

    /**
     * 个数描述
     */
    private String auxremainderdetail;
    /**
     * 数量(辅计量显示)
     */
    private String auxnumdetail;
    /**
     * 合计箱数
     */
    private BigDecimal totalbox;

    /**
     * 实际交货辅单位数量
     */
    private BigDecimal auxrealsendnum;

    /**
     * 实际交货辅单位数量描述
     */
    private String auxrealsendnumdetail;

    /**
     * 退回仓库辅单位数量
     */
    private BigDecimal auxreturnnum;

    /**
     * 退回仓库辅单位数量描述
     */
    private String auxreturnnumdetail;

    /**
     * 含税单价
     */
    private BigDecimal taxprice;
    /**
     * 辅单位初始金额
     */
    private BigDecimal inittaxamount;
    /**
     * 含税金额
     */
    private BigDecimal taxamount;
    /**
     * 初始成本金额
     */
    private BigDecimal initcostamount;
    /**
     * 成本金额
     */
    private BigDecimal costamount;
    /**
     * 实际交易含税金额
     */
    private BigDecimal realtaxamount;
    /**
     * 退货含税金额
     */
    private BigDecimal returntaxamount;
    /**
     * 无税单价
     */
    private BigDecimal notaxprice;
    /**
     * 辅单位初始无税金额
     */
    private BigDecimal initnotaxamount;
    /**
     * 无税金额
     */
    private BigDecimal notaxamount;

    /**
     * 实际交易无税金额
     */
    private BigDecimal realnotaxamount;
    /**
     * 退货无税金额
     */
    private BigDecimal returnnotaxamount;
    /**
     * 折扣金额
     */
    private BigDecimal discountamount;
    /**
     * 初始化单价
     */
    private BigDecimal inittaxprice;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 实际成本价
     */
    private BigDecimal realcostprice;
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
     * 备注
     */
    private String remark;

    /**
     * 生产日期
     */
    private String produceddate;

    /**
     * 有效截止日期
     */
    private String deadline;

    /**
     * 排序
     */
    private Integer seq;
    /**
     * 是否开票1是0否
     */
    private String isinvoice;
    /**
     * 是否核销1是0否
     */
    private String iswriteoff;
    /**
     * 开票日期
     */
    private String invoicedate;
    /**
     * 核销日期
     */
    private String writeoffdate;
    /**
     * 自定义信息1
     */
    private String field01;

    /**
     * 自定义信息2
     */
    private String field02;

    /**
     * 自定义信息3
     */
    private String field03;

    /**
     * 自定义信息4
     */
    private String field04;

    /**
     * 自定义信息5
     */
    private String field05;

    /**
     * 自定义信息6
     */
    private String field06;

    /**
     * 自定义信息7
     */
    private String field07;

    /**
     * 自定义信息8
     */
    private String field08;
    /**
     * 店内码
     */
    private String shopid;
    /**
     * 商品类型0正常1赠品2样品
     */
    private String deliverytype;

    /**
     * 是否实际开票1是0否
     */
    private String isinvoicebill;
    /**
     * 应收日期
     */
    private String duefromdate;


    /**
     * 原单价
     */
    private BigDecimal fixprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 销售出库单编号
     */
    public String getSaleoutid() {
        return saleoutid;
    }

    /**
     * @param saleoutid
     *            销售出库单编号
     */
    public void setSaleoutid(String saleoutid) {
        this.saleoutid = saleoutid == null ? null : saleoutid.trim();
    }

    /**
     * @return 销售发货通知单明细编号
     */
    public String getDispatchbilldetailid() {
        return dispatchbilldetailid;
    }

    /**
     * @param dispatchbilldetailid
     *            销售发货通知单明细编号
     */
    public void setDispatchbilldetailid(String dispatchbilldetailid) {
        this.dispatchbilldetailid = dispatchbilldetailid == null ? null : dispatchbilldetailid.trim();
    }

    /**
     * @return 批次现存量编号
     */
    public String getSummarybatchid() {
        return summarybatchid;
    }

    /**
     * @param summarybatchid
     *            批次现存量编号
     */
    public void setSummarybatchid(String summarybatchid) {
        this.summarybatchid = summarybatchid == null ? null : summarybatchid.trim();
    }

    /**
     * @return 商品编码
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid
     *            商品编码
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    /**
     * @return 发货仓库编码
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid
     *            发货仓库编码
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 库位
     */
    public String getStoragelocationid() {
        return storagelocationid;
    }

    /**
     * @param storagelocationid
     *            库位
     */
    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid == null ? null : storagelocationid.trim();
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
     * @return 主计量单位
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid
     *            主计量单位
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 主计量单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname
     *            主计量单位名称
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
     * @return 实际交货送来
     */
    public BigDecimal getRealsendnum() {
        return realsendnum;
    }

    /**
     * @param realsendnum
     *            实际交货送来
     */
    public void setRealsendnum(BigDecimal realsendnum) {
        this.realsendnum = realsendnum;
    }

    /**
     * @return 退回仓库数量
     */
    public BigDecimal getReturnnum() {
        return returnnum;
    }

    /**
     * @param returnnum
     *            退回仓库数量
     */
    public void setReturnnum(BigDecimal returnnum) {
        this.returnnum = returnnum;
    }

    /**
     * @return 辅计量单位
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid
     *            辅计量单位
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
     * @return 数量(辅计量显示)
     */
    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    /**
     * @param auxnumdetail
     *            数量(辅计量显示)
     */
    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail == null ? null : auxnumdetail.trim();
    }

    /**
     * @return 实际交货辅单位数量
     */
    public BigDecimal getAuxrealsendnum() {
        return auxrealsendnum;
    }

    /**
     * @param auxrealsendnum
     *            实际交货辅单位数量
     */
    public void setAuxrealsendnum(BigDecimal auxrealsendnum) {
        this.auxrealsendnum = auxrealsendnum;
    }

    /**
     * @return 实际交货辅单位数量描述
     */
    public String getAuxrealsendnumdetail() {
        return auxrealsendnumdetail;
    }

    /**
     * @param auxrealsendnumdetail
     *            实际交货辅单位数量描述
     */
    public void setAuxrealsendnumdetail(String auxrealsendnumdetail) {
        this.auxrealsendnumdetail = auxrealsendnumdetail == null ? null : auxrealsendnumdetail.trim();
    }

    /**
     * @return 退回仓库辅单位数量
     */
    public BigDecimal getAuxreturnnum() {
        return auxreturnnum;
    }

    /**
     * @param auxreturnnum
     *            退回仓库辅单位数量
     */
    public void setAuxreturnnum(BigDecimal auxreturnnum) {
        this.auxreturnnum = auxreturnnum;
    }

    /**
     * @return 退回仓库辅单位数量描述
     */
    public String getAuxreturnnumdetail() {
        return auxreturnnumdetail;
    }

    /**
     * @param auxreturnnumdetail
     *            退回仓库辅单位数量描述
     */
    public void setAuxreturnnumdetail(String auxreturnnumdetail) {
        this.auxreturnnumdetail = auxreturnnumdetail == null ? null : auxreturnnumdetail.trim();
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
     * @return 实际交易含税金额
     */
    public BigDecimal getRealtaxamount() {
        return realtaxamount;
    }

    /**
     * @param realtaxamount
     *            实际交易含税金额
     */
    public void setRealtaxamount(BigDecimal realtaxamount) {
        this.realtaxamount = realtaxamount;
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
     * @return 实际交易无税金额
     */
    public BigDecimal getRealnotaxamount() {
        return realnotaxamount;
    }

    /**
     * @param realnotaxamount
     *            实际交易无税金额
     */
    public void setRealnotaxamount(BigDecimal realnotaxamount) {
        this.realnotaxamount = realnotaxamount;
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
     * @return 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq
     *            排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getStoragelocationname() {
        return storagelocationname;
    }

    public void setStoragelocationname(String storagelocationname) {
        this.storagelocationname = storagelocationname;
    }

    public String getTaxtypename() {
        return taxtypename;
    }

    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
    }
    /**
     * 销售发货通知单编号
     * @return
     * @author chenwei
     * @date Jun 6, 2013
     */
    public String getDispatchbillid() {
        return dispatchbillid;
    }
    /**
     * 销售发货通知单编号
     * @param dispatchbillid
     * @author chenwei
     * @date Jun 6, 2013
     */
    public void setDispatchbillid(String dispatchbillid) {
        this.dispatchbillid = dispatchbillid;
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

    public BigDecimal getReturntaxamount() {
        return returntaxamount;
    }

    public void setReturntaxamount(BigDecimal returntaxamount) {
        this.returntaxamount = returntaxamount;
    }

    public BigDecimal getReturnnotaxamount() {
        return returnnotaxamount;
    }

    public void setReturnnotaxamount(BigDecimal returnnotaxamount) {
        this.returnnotaxamount = returnnotaxamount;
    }
    /**
     * 是否折扣1是0否
     * @return
     * @author chenwei
     * @date Jul 9, 2013
     */
    public String getIsdiscount() {
        return isdiscount;
    }
    /**
     * 是否折扣1是0否
     * @param isdiscount
     * @author chenwei
     * @date Jul 9, 2013
     */
    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getBranduser() {
        return branduser;
    }

    public void setBranduser(String branduser) {
        this.branduser = branduser;
    }

    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
    }

    public BigDecimal getInitnum() {
        return initnum;
    }

    public void setInitnum(BigDecimal initnum) {
        this.initnum = initnum;
    }

    public BigDecimal getAuxinitnum() {
        return auxinitnum;
    }

    public void setAuxinitnum(BigDecimal auxinitnum) {
        this.auxinitnum = auxinitnum;
    }

    public BigDecimal getInittaxamount() {
        return inittaxamount;
    }

    public void setInittaxamount(BigDecimal inittaxamount) {
        this.inittaxamount = inittaxamount;
    }

    public BigDecimal getInitnotaxamount() {
        return initnotaxamount;
    }

    public void setInitnotaxamount(BigDecimal initnotaxamount) {
        this.initnotaxamount = initnotaxamount;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    public BigDecimal getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(BigDecimal discountamount) {
        this.discountamount = discountamount;
    }

    public BigDecimal getInittaxprice() {
        return inittaxprice;
    }

    public void setInittaxprice(BigDecimal inittaxprice) {
        this.inittaxprice = inittaxprice;
    }

    public String getIswriteoff() {
        return iswriteoff;
    }

    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff;
    }

    public String getIsinvoice() {
        return isinvoice;
    }

    public void setIsinvoice(String isinvoice) {
        this.isinvoice = isinvoice;
    }

    public String getBranddept() {
        return branddept;
    }

    public void setBranddept(String branddept) {
        this.branddept = branddept;
    }

    public BigDecimal getCostamount() {
        return costamount;
    }

    public void setCostamount(BigDecimal costamount) {
        this.costamount = costamount;
    }

    public BigDecimal getInitcostamount() {
        return initcostamount;
    }

    public void setInitcostamount(BigDecimal initcostamount) {
        this.initcostamount = initcostamount;
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

    public String getIsbranddiscount() {
        return isbranddiscount;
    }

    public void setIsbranddiscount(String isbranddiscount) {
        this.isbranddiscount = isbranddiscount;
    }

    public BigDecimal getRealcostprice() {
        return realcostprice;
    }

    public void setRealcostprice(BigDecimal realcostprice) {
        this.realcostprice = realcostprice;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getGoodssort() {
        return goodssort;
    }

    public void setGoodssort(String goodssort) {
        this.goodssort = goodssort;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTotalauxnumdetail() {
        return totalauxnumdetail;
    }

    public void setTotalauxnumdetail(String totalauxnumdetail) {
        this.totalauxnumdetail = totalauxnumdetail;
    }

    public String getAuxremainderdetail() {
        return auxremainderdetail;
    }

    public void setAuxremainderdetail(String auxremainderdetail) {
        this.auxremainderdetail = auxremainderdetail;
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

    public Integer getCustomernum() {
        return customernum;
    }

    public void setCustomernum(Integer customernum) {
        this.customernum = customernum;
    }

    public String getSupplieruser() {
        return supplieruser;
    }

    public void setSupplieruser(String supplieruser) {
        this.supplieruser = supplieruser;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getIsenough() {
        return isenough;
    }

    public void setIsenough(String isenough) {
        this.isenough = isenough;
    }

    public BigDecimal getTotalbox() {
        return totalbox;
    }

    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    public String getGroupid() {
        return groupid;
    }

    public String getDeliverytype() {
        return deliverytype;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype;
    }

    public String getIsinvoicebill() {
        return isinvoicebill;
    }

    public void setIsinvoicebill(String isinvoicebill) {
        this.isinvoicebill = isinvoicebill;
    }

    public String getDuefromdate() {
        return duefromdate;
    }

    public void setDuefromdate(String duefromdate) {
        this.duefromdate = duefromdate;
    }

    public BigDecimal getFixprice() {
        return fixprice;
    }

    public void setFixprice(BigDecimal fixprice) {
        this.fixprice = fixprice;
    }

    public String getBrandusername() {
        return brandusername;
    }

    public void setBrandusername(String brandusername) {
        this.brandusername = brandusername;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }
}