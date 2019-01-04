package com.hd.agent.sales.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;

public class OweOrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String orderid;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 商品组编号（赠品，买赠，捆绑等）
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
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;

    /**
     * 固定销售数量(做缺货比较)
     */
    private BigDecimal fixnum;

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
     * 辅单位整数数量
     */
    private BigDecimal auxnum;

    /**
     * 主单位余数
     */
    private BigDecimal overnum;

    /**
     * 辅单位数量+辅单位+主单位余数+主单位
     */
    private String auxnumdetail;

    /**
     * 合计箱数
     */
    private BigDecimal totalbox;

    /**
     * 原价
     */
    private BigDecimal fixprice;

    /**
     * 促销价
     */
    private BigDecimal offprice;

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
     * 税种
     */
    private String taxtype;

    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商品类型0正常商品1赠品2捆绑
     */
    private String deliverytype;

    /**
     * 交货日期
     */
    private String deliverydate;

    /**
     * 来源单据编号
     */
    private String billno;

    /**
     * 来源单位明细编号
     */
    private String billdetailno;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 固定价
     */
    private BigDecimal oldprice;

    /**
     * 仓库编号
     */
    private String storageid;

    /**
     * 要货单价（含税）
     */
    private BigDecimal demandprice;

    /**
     * 要货金额
     */
    private BigDecimal demandamount;

    private GoodsInfo goodsInfo;
    private String customername;
    private BigDecimal useable;




    public BigDecimal getUseable() {
        return useable;
    }

    public void setUseable(BigDecimal useable) {
        this.useable = useable;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

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
     * @return 单据编号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid
     *            单据编号
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

    /**
     * @return 商品组编号（赠品，买赠，捆绑等）
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * @param groupid
     *            商品组编号（赠品，买赠，捆绑等）
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    /**
     * @return 商品分类
     */
    public String getGoodssort() {
        return goodssort;
    }

    /**
     * @param goodssort
     *            商品分类
     */
    public void setGoodssort(String goodssort) {
        this.goodssort = goodssort == null ? null : goodssort.trim();
    }

    /**
     * @return 品牌编码
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid
     *            品牌编码
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 品牌业务员
     */
    public String getBranduser() {
        return branduser;
    }

    /**
     * @param branduser
     *            品牌业务员
     */
    public void setBranduser(String branduser) {
        this.branduser = branduser == null ? null : branduser.trim();
    }

    /**
     * @return 品牌部门
     */
    public String getBranddept() {
        return branddept;
    }

    /**
     * @param branddept
     *            品牌部门
     */
    public void setBranddept(String branddept) {
        this.branddept = branddept == null ? null : branddept.trim();
    }

    /**
     * @return 供应商编号
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid
     *            供应商编号
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    /**
     * @return 厂家业务员
     */
    public String getSupplieruser() {
        return supplieruser;
    }

    /**
     * @param supplieruser
     *            厂家业务员
     */
    public void setSupplieruser(String supplieruser) {
        this.supplieruser = supplieruser == null ? null : supplieruser.trim();
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
     * @return 固定销售数量(做缺货比较)
     */
    public BigDecimal getFixnum() {
        return fixnum;
    }

    /**
     * @param fixnum
     *            固定销售数量(做缺货比较)
     */
    public void setFixnum(BigDecimal fixnum) {
        this.fixnum = fixnum;
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
     * @return 辅单位整数数量
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum
     *            辅单位整数数量
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 主单位余数
     */
    public BigDecimal getOvernum() {
        return overnum;
    }

    /**
     * @param overnum
     *            主单位余数
     */
    public void setOvernum(BigDecimal overnum) {
        this.overnum = overnum;
    }

    /**
     * @return 辅单位数量+辅单位+主单位余数+主单位
     */
    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    /**
     * @param auxnumdetail
     *            辅单位数量+辅单位+主单位余数+主单位
     */
    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail == null ? null : auxnumdetail.trim();
    }

    /**
     * @return 合计箱数
     */
    public BigDecimal getTotalbox() {
        return totalbox;
    }

    /**
     * @param totalbox
     *            合计箱数
     */
    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    /**
     * @return 原价
     */
    public BigDecimal getFixprice() {
        return fixprice;
    }

    /**
     * @param fixprice
     *            原价
     */
    public void setFixprice(BigDecimal fixprice) {
        this.fixprice = fixprice;
    }

    /**
     * @return 促销价
     */
    public BigDecimal getOffprice() {
        return offprice;
    }

    /**
     * @param offprice
     *            促销价
     */
    public void setOffprice(BigDecimal offprice) {
        this.offprice = offprice;
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
     * @return 成本价
     */
    public BigDecimal getCostprice() {
        return costprice;
    }

    /**
     * @param costprice
     *            成本价
     */
    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
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
     * @return 商品类型0正常商品1赠品2捆绑
     */
    public String getDeliverytype() {
        return deliverytype;
    }

    /**
     * @param deliverytype
     *            商品类型0正常商品1赠品2捆绑
     */
    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype == null ? null : deliverytype.trim();
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
        this.deliverydate = deliverydate == null ? null : deliverydate.trim();
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

    /**
     * @return 固定价
     */
    public BigDecimal getOldprice() {
        return oldprice;
    }

    /**
     * @param oldprice
     *            固定价
     */
    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    /**
     * @return 仓库编号
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid
     *            仓库编号
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 要货单价（含税）
     */
    public BigDecimal getDemandprice() {
        return demandprice;
    }

    /**
     * @param demandprice
     *            要货单价（含税）
     */
    public void setDemandprice(BigDecimal demandprice) {
        this.demandprice = demandprice;
    }

    /**
     * @return 要货金额
     */
    public BigDecimal getDemandamount() {
        return demandamount;
    }

    /**
     * @param demandamount
     *            要货金额
     */
    public void setDemandamount(BigDecimal demandamount) {
        this.demandamount = demandamount;
    }

    private BigDecimal ordernum;

    public BigDecimal getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(BigDecimal ordernum) {
        this.ordernum = ordernum;
    }
}