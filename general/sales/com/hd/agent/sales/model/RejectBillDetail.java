package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.GoodsInfo;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RejectBillDetail implements Serializable {
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
     * 商品分类
     */
    private String goodssort;
    /**
     * 品牌编号
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
     * 含税单价
     */
    private BigDecimal taxprice;

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
     * 成本价
     */
    private BigDecimal costprice;

    /**
     * 默认销售价（价格套价格或合同价）
     */
    private BigDecimal defaultprice;

    /**
     * 最近一次销售价（交易价）
     */
    private BigDecimal lastprice;

    /**
     * 最低销售价（多少天之内或多少月之内的最低销售价）
     */
    private BigDecimal lowestprice;
    /**
     * 箱价
     */
    private BigDecimal boxprice;
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
     * 退货类型
     */
    private String rejecttype;
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
     * 仓库编号
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 批次现存量编号
     */
    private String summarybatchid;
    /**
     * 库位
     */
    private String storagelocationid;
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
     * 已退货入库主单位数量
     */
    private BigDecimal innummain;

    /**
     * 已退货入库辅单位数量
     */
    private BigDecimal innumaux;

    /**
     * 未退货入库主单位数量
     */
    private BigDecimal noinnummain;

    /**
     * 未退货入库辅单位数量
     */
    private BigDecimal noinnumaux;

    /**
     * 已退货入库无税金额
     */
    private BigDecimal inamountnotax;

    /**
     * 已退货入库含税金额
     */
    private BigDecimal inamounttax;

    /**
     * 未退货入库无税金额
     */
    private BigDecimal noinamountnotax;

    /**
     * 未退货入库含税金额
     */
    private BigDecimal noinamounttax;
    /**
     * 直退关联数量
     */
    private BigDecimal relatenum;
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
     * 直退通知单是否被回单关联1是0否
     */
    private String isrefer;

    /**
     * 是否实际开票1是0否
     */
    private String isinvoicebill;
    /**
     * 商品信息
     */
    private GoodsInfo goodsInfo;
    /**
     * 拆分数量
     */
    private BigDecimal splitnum;
    /**
     * 回单编号
     */
    private String receiptid;
    /**
     * 回单明细编号
     */
    private String receiptdetailid;
    /**
     * 应收日期
     */
    private String duefromdate;
    /**
     * 最新采购价
     */
    private BigDecimal buyprice ;

    /**
     * 退货属性
     */
    private String rejectcategory;
    /**
     * 店内码
     */
    private String shopid;

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

    /**
     * 税种名称
     * @return
     * @author zhengziyong
     * @date Jun 5, 2013
     */
    public String getTaxtypename() {
        return taxtypename;
    }

    /**
     * 税种名称
     * @param taxtypename
     * @author zhengziyong
     * @date Jun 5, 2013
     */
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
     * @return 退货类型
     */
    public String getRejecttype() {
        return rejecttype;
    }

    /**
     * @param rejecttype
     *            退货类型
     */
    public void setRejecttype(String rejecttype) {
        this.rejecttype = rejecttype;
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
     * @return 已退货入库主单位数量
     */
    public BigDecimal getInnummain() {
        return innummain;
    }

    /**
     * @param innummain
     *            已退货入库主单位数量
     */
    public void setInnummain(BigDecimal innummain) {
        this.innummain = innummain;
    }

    /**
     * @return 已退货入库辅单位数量
     */
    public BigDecimal getInnumaux() {
        return innumaux;
    }

    /**
     * @param innumaux
     *            已退货入库辅单位数量
     */
    public void setInnumaux(BigDecimal innumaux) {
        this.innumaux = innumaux;
    }

    /**
     * @return 未退货入库主单位数量
     */
    public BigDecimal getNoinnummain() {
        return noinnummain;
    }

    /**
     * @param noinnummain
     *            未退货入库主单位数量
     */
    public void setNoinnummain(BigDecimal noinnummain) {
        this.noinnummain = noinnummain;
    }

    /**
     * @return 未退货入库辅单位数量
     */
    public BigDecimal getNoinnumaux() {
        return noinnumaux;
    }

    /**
     * @param noinnumaux
     *            未退货入库辅单位数量
     */
    public void setNoinnumaux(BigDecimal noinnumaux) {
        this.noinnumaux = noinnumaux;
    }

    /**
     * @return 已退货入库无税金额
     */
    public BigDecimal getInamountnotax() {
        return inamountnotax;
    }

    /**
     * @param inamountnotax
     *            已退货入库无税金额
     */
    public void setInamountnotax(BigDecimal inamountnotax) {
        this.inamountnotax = inamountnotax;
    }

    /**
     * @return 已退货入库含税金额
     */
    public BigDecimal getInamounttax() {
        return inamounttax;
    }

    /**
     * @param inamounttax
     *            已退货入库含税金额
     */
    public void setInamounttax(BigDecimal inamounttax) {
        this.inamounttax = inamounttax;
    }

    /**
     * @return 未退货入库无税金额
     */
    public BigDecimal getNoinamountnotax() {
        return noinamountnotax;
    }

    /**
     * @param noinamountnotax
     *            未退货入库无税金额
     */
    public void setNoinamountnotax(BigDecimal noinamountnotax) {
        this.noinamountnotax = noinamountnotax;
    }

    /**
     * @return 未退货入库含税金额
     */
    public BigDecimal getNoinamounttax() {
        return noinamounttax;
    }

    /**
     * @param noinamounttax
     *            未退货入库含税金额
     */
    public void setNoinamounttax(BigDecimal noinamounttax) {
        this.noinamounttax = noinamounttax;
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

    public String getIsrefer() {
        return isrefer;
    }

    public void setIsrefer(String isrefer) {
        this.isrefer = isrefer;
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

    public String getBranddept() {
        return branddept;
    }

    public void setBranddept(String branddept) {
        this.branddept = branddept;
    }

    public BigDecimal getRelatenum() {
        return relatenum;
    }

    public void setRelatenum(BigDecimal relatenum) {
        this.relatenum = relatenum;
    }

    public BigDecimal getSplitnum() {
        return splitnum;
    }

    public void setSplitnum(BigDecimal splitnum) {
        this.splitnum = splitnum;
    }

    public String getReceiptid() {
        return receiptid;
    }

    public void setReceiptid(String receiptid) {
        this.receiptid = receiptid;
    }

    public String getReceiptdetailid() {
        return receiptdetailid;
    }

    public void setReceiptdetailid(String receiptdetailid) {
        this.receiptdetailid = receiptdetailid;
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

    public String getDeliverytype() {
        return deliverytype;
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

    public String getStorageid() {
        return storageid;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public BigDecimal getDefaultprice() {
        return defaultprice;
    }

    public void setDefaultprice(BigDecimal defaultprice) {
        this.defaultprice = defaultprice;
    }

    public BigDecimal getLastprice() {
        return lastprice;
    }

    public void setLastprice(BigDecimal lastprice) {
        this.lastprice = lastprice;
    }

    public BigDecimal getLowestprice() {
        return lowestprice;
    }

    public void setLowestprice(BigDecimal lowestprice) {
        this.lowestprice = lowestprice;
    }

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    public String getDuefromdate() {
        return duefromdate;
    }

    public void setDuefromdate(String duefromdate) {
        this.duefromdate = duefromdate;
    }

    public String getRejectcategory() {
        return rejectcategory;
    }

    public void setRejectcategory(String rejectcategory) {
        this.rejectcategory = rejectcategory;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
}