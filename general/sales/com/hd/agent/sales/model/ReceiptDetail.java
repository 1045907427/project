package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.GoodsInfo;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售发货回单商品明细
 * @author zhengziyong
 *
 */
public class ReceiptDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 商品编号
     */
    private String goodsid;
    /**
     * 商品组编号
     */
    private String groupid;
    /**
     * 商品分类
     */
    private String goodssort;
    /**
     * 品牌编码
     */
    private String brandid;
    /**
     * 品牌业务员
     */
    private String branduser;
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
     * 是否折扣1是0否
     */
    private String isdiscount;
    /**
     * 是否品牌折扣1是0否
     */
    private String isbranddiscount;
    /**
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 客户接收数量
     */
    private BigDecimal receiptnum;

    /**
     * 应付含税金额
     */
    private BigDecimal receipttaxamount;

    /**
     * 应付未税金额
     */
    private BigDecimal receiptnotaxamount;

    /**
     * 辅计量单位
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
     * 辅单位余数数量
     */
    private BigDecimal auxremainder;
    /**
     * 数量(辅计量显示)
     */
    private String auxnumdetail;
    /**
     * 合计箱数
     */
    private BigDecimal totalbox;
    /**
     * 客户接收箱数
     */
    private BigDecimal receiptbox;
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
     * 无税单价
     */
    private BigDecimal notaxprice;

    /**
     * 无税金额
     */
    private BigDecimal notaxamount;
    /**
     * 折扣金额
     */
    private BigDecimal discountamount;
    /**
     * 初始单价
     */
    private BigDecimal inittaxprice;

    /**
     * 应收未税单据
     */
    private BigDecimal receiptnotaxprice;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 税种
     */
    private String taxtype;

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
     * 商品类型0正常商品1赠品2样品
     */
    private String deliverytype;
    /**
     * 交货日期
     */
    private String deliverydate;

    /**
     * 批次号
     */
    private String batchno;
    /**
     * 批次现存量编号
     */
    private String summarybatchid;
    /**
     * 库位
     */
    private String storagelocationid;
    /**
     * 库位名称
     */
    private String storagelocationname;
    /**
     * 生产日期
     */
    private String produceddate;
    /**
     * 有效截止日期
     */
    private String deadline;

    /**
     * 过期日期
     */
    private Date expirationdate;

    /**
     * 来源单据编号
     */
    private String billno;

    /**
     * 来源单位明细编号
     */
    private String billdetailno;

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
     * 已制退货通知单主单位数量
     */
    private BigDecimal rejectnummain;

    /**
     * 已制退货通知单辅单位数量
     */
    private BigDecimal rejectnumaux;

    /**
     * 未制退货通知单主单位数量
     */
    private BigDecimal norejectnummain;

    /**
     * 未制退货通知单辅单位数量
     */
    private BigDecimal norejectnumaux;

    /**
     * 已制退货通知单无税金额
     */
    private BigDecimal rejectamountnotax;

    /**
     * 已制退货通知单含税金额
     */
    private BigDecimal rejectamounttax;

    /**
     * 未制退货通知单无税金额
     */
    private BigDecimal norejectamountnotax;

    /**
     * 未制退货通知单含税金额
     */
    private BigDecimal norejectamounttax;

    /**
     * 排序
     */
    private Integer seq;
    /**
     * 是否生成销售发票1是0否
     */
    private String isinvoice;
    /**
     * 是否核销1已核销0未核销
     */
    private String iswriteoff;
    /**
     * 核销日期
     */
    private String canceldate;
    /**
     * 销售退货通知单编号
     */
    private String rejectbillid;
    /**
     * 直退销售退货通知单明细编号
     */
    private String rejectdetailid;

    /**
     * 是否实际开票1是0否
     */
    private String isinvoicebill;
    /**
     * 应收日期
     */
    private String duefromdate;
    private GoodsInfo goodsInfo;

    /**
     * 采购价
     */
    private BigDecimal buyprice ;
    /**
     * 店内码
     */
    private String shopid;

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    /**
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid
     *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid;
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
        this.goodsid = goodsid;
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
        this.unitid = unitid;
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
        this.unitname = unitname;
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
     * @return 客户接收数量
     */
    public BigDecimal getReceiptnum() {
        return receiptnum;
    }

    /**
     * @param receiptnum
     *            客户接收数量
     */
    public void setReceiptnum(BigDecimal receiptnum) {
        this.receiptnum = receiptnum;
    }

    /**
     *
     * @return 应付含税金额
     * @author zhengziyong
     * @date Jul 2, 2013
     */
    public BigDecimal getReceipttaxamount() {
        return receipttaxamount;
    }

    /**
     *
     * @param receipttaxamount 应付含税金额
     * @author zhengziyong
     * @date Jul 2, 2013
     */
    public void setReceipttaxamount(BigDecimal receipttaxamount) {
        this.receipttaxamount = receipttaxamount;
    }

    /**
     *
     * @return 应付未税金额
     * @author zhengziyong
     * @date Jul 2, 2013
     */
    public BigDecimal getReceiptnotaxamount() {
        return receiptnotaxamount;
    }

    /**
     *
     * @param receiptnotaxamount 应付未税金额
     * @author zhengziyong
     * @date Jul 2, 2013
     */
    public void setReceiptnotaxamount(BigDecimal receiptnotaxamount) {
        this.receiptnotaxamount = receiptnotaxamount;
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
        this.auxunitid = auxunitid;
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
        this.auxunitname = auxunitname;
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
        this.auxnumdetail = auxnumdetail;
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
        this.taxtype = taxtype;
    }

    public String getTaxtypename() {
        return taxtypename;
    }

    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
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
        this.remark = remark;
    }

    /**
     * @return 交货日期
     */
    public String getDeliverydate() {
        return deliverydate;
    }

    /**
     * @param deliverydate
     *            交货日期
     */
    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getStoragelocationname() {
        return storagelocationname;
    }

    public void setStoragelocationname(String storagelocationname) {
        this.storagelocationname = storagelocationname;
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
        this.batchno = batchno;
    }

    /**
     * @return 过期日期
     */
    @JSON(format="yyyy-MM-dd")
    public Date getExpirationdate() {
        return expirationdate;
    }

    /**
     * @param expirationdate
     *            过期日期
     */
    public void setExpirationdate(Date expirationdate) {
        this.expirationdate = expirationdate;
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
        this.billno = billno;
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
        this.billdetailno = billdetailno;
    }

    /**
     * @return 自定义信息1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01
     *            自定义信息1
     */
    public void setField01(String field01) {
        this.field01 = field01;
    }

    /**
     * @return 自定义信息2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02
     *            自定义信息2
     */
    public void setField02(String field02) {
        this.field02 = field02;
    }

    /**
     * @return 自定义信息3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03
     *            自定义信息3
     */
    public void setField03(String field03) {
        this.field03 = field03;
    }

    /**
     * @return 自定义信息4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04
     *            自定义信息4
     */
    public void setField04(String field04) {
        this.field04 = field04;
    }

    /**
     * @return 自定义信息5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05
     *            自定义信息5
     */
    public void setField05(String field05) {
        this.field05 = field05;
    }

    /**
     * @return 自定义信息6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06
     *            自定义信息6
     */
    public void setField06(String field06) {
        this.field06 = field06;
    }

    /**
     * @return 自定义信息7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07
     *            自定义信息7
     */
    public void setField07(String field07) {
        this.field07 = field07;
    }

    /**
     * @return 自定义信息8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08
     *            自定义信息8
     */
    public void setField08(String field08) {
        this.field08 = field08;
    }

    /**
     * @return 已制退货通知单主单位数量
     */
    public BigDecimal getRejectnummain() {
        return rejectnummain;
    }

    /**
     * @param rejectnummain
     *            已制退货通知单主单位数量
     */
    public void setRejectnummain(BigDecimal rejectnummain) {
        this.rejectnummain = rejectnummain;
    }

    /**
     * @return 已制退货通知单辅单位数量
     */
    public BigDecimal getRejectnumaux() {
        return rejectnumaux;
    }

    /**
     * @param rejectnumaux
     *            已制退货通知单辅单位数量
     */
    public void setRejectnumaux(BigDecimal rejectnumaux) {
        this.rejectnumaux = rejectnumaux;
    }

    /**
     * @return 未制退货通知单主单位数量
     */
    public BigDecimal getNorejectnummain() {
        return norejectnummain;
    }

    /**
     * @param norejectnummain
     *            未制退货通知单主单位数量
     */
    public void setNorejectnummain(BigDecimal norejectnummain) {
        this.norejectnummain = norejectnummain;
    }

    /**
     * @return 未制退货通知单辅单位数量
     */
    public BigDecimal getNorejectnumaux() {
        return norejectnumaux;
    }

    /**
     * @param norejectnumaux
     *            未制退货通知单辅单位数量
     */
    public void setNorejectnumaux(BigDecimal norejectnumaux) {
        this.norejectnumaux = norejectnumaux;
    }

    /**
     * @return 已制退货通知单无税金额
     */
    public BigDecimal getRejectamountnotax() {
        return rejectamountnotax;
    }

    /**
     * @param rejectamountnotax
     *            已制退货通知单无税金额
     */
    public void setRejectamountnotax(BigDecimal rejectamountnotax) {
        this.rejectamountnotax = rejectamountnotax;
    }

    /**
     * @return 已制退货通知单含税金额
     */
    public BigDecimal getRejectamounttax() {
        return rejectamounttax;
    }

    /**
     * @param rejectamounttax
     *            已制退货通知单含税金额
     */
    public void setRejectamounttax(BigDecimal rejectamounttax) {
        this.rejectamounttax = rejectamounttax;
    }

    /**
     * @return 未制退货通知单无税金额
     */
    public BigDecimal getNorejectamountnotax() {
        return norejectamountnotax;
    }

    /**
     * @param norejectamountnotax
     *            未制退货通知单无税金额
     */
    public void setNorejectamountnotax(BigDecimal norejectamountnotax) {
        this.norejectamountnotax = norejectamountnotax;
    }

    /**
     * @return 未制退货通知单含税金额
     */
    public BigDecimal getNorejectamounttax() {
        return norejectamounttax;
    }

    /**
     * @param norejectamounttax
     *            未制退货通知单含税金额
     */
    public void setNorejectamounttax(BigDecimal norejectamounttax) {
        this.norejectamounttax = norejectamounttax;
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

    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
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

    public String getIsinvoice() {
        return isinvoice;
    }

    public void setIsinvoice(String isinvoice) {
        this.isinvoice = isinvoice;
    }

    public String getIswriteoff() {
        return iswriteoff;
    }

    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff;
    }

    public String getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(String canceldate) {
        this.canceldate = canceldate;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getRejectdetailid() {
        return rejectdetailid;
    }

    public void setRejectdetailid(String rejectdetailid) {
        this.rejectdetailid = rejectdetailid;
    }

    public String getBranduser() {
        return branduser;
    }

    public void setBranduser(String branduser) {
        this.branduser = branduser;
    }

    public String getBranddept() {
        return branddept;
    }

    public void setBranddept(String branddept) {
        this.branddept = branddept;
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

    public String getRejectbillid() {
        return rejectbillid;
    }

    public void setRejectbillid(String rejectbillid) {
        this.rejectbillid = rejectbillid;
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

    public String getSupplieruser() {
        return supplieruser;
    }

    public void setSupplieruser(String supplieruser) {
        this.supplieruser = supplieruser;
    }

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }

    public BigDecimal getTotalbox() {
        return totalbox;
    }

    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    public BigDecimal getReceiptbox() {
        return receiptbox;
    }

    public void setReceiptbox(BigDecimal receiptbox) {
        this.receiptbox = receiptbox;
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

    public String getStoragelocationid() {
        return storagelocationid;
    }

    public String getProduceddate() {
        return produceddate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid;
    }

    public void setProduceddate(String produceddate) {
        this.produceddate = produceddate;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getSummarybatchid() {
        return summarybatchid;
    }

    public void setSummarybatchid(String summarybatchid) {
        this.summarybatchid = summarybatchid;
    }

    public String getDuefromdate() {
        return duefromdate;
    }

    public void setDuefromdate(String duefromdate) {
        this.duefromdate = duefromdate;
    }

    public BigDecimal getReceiptnotaxprice() {
        return receiptnotaxprice;
    }

    public void setReceiptnotaxprice(BigDecimal receiptnotaxprice) {
        this.receiptnotaxprice = receiptnotaxprice;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid == null ? null : shopid.trim();
    }
}