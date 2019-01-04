package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.TaxType;

public class SalesInvoiceBillDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 来源单据类型1销售回单2销售退货通知单3冲差单
     */
    private String sourcetype;

    /**
     * 来源单据编号
     */
    private String sourceid;

    /**
     * 来源单据明细编号
     */
    private String sourcedetailid;

    /**
     * 发货单或者退货通知单编号
     */
    private String billno;

    /**
     * 发货单或者退货通知单明细编号
     */
    private String billdetailid;

    /**
     * 是否折扣1是0否2返利折扣
     */
    private String isdiscount;

    /**
     * 是否品牌折扣1是0否
     */
    private String isbranddiscount;

    /**
     * 是否折扣1是0否（当页合计使用）
     */
    private String isrebate;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 上级客户（总店）
     */
    private String pcustomerid;

    /**
     * 客户分类
     */
    private String customersort;

    /**
     * 销售区域
     */
    private String salesarea;

    /**
     * 销售部门
     */
    private String salesdept;

    /**
     * 销售员
     */
    private String salesuser;

    /**
     * 供应商编号
     */
    private String supplierid;

    /**
     * 厂家业务员
     */
    private String supplieruser;

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
     * 规格型号
     */
    private String model;

    /**
     * 箱装量
     */
    private BigDecimal boxnum;

    /**
     * 商品分类
     */
    private String goodssort;

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
     * 品牌部门
     */
    private String branddept;

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
     * 数量(辅计量显示)
     */
    private String auxnumdetail;

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
     * 税种
     */
    private String taxtype;
    
    /**
     * 税种名称
     */
    private String taxtypename;
    /**
     * 税率
     */
    private BigDecimal taxrate;

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
    private String remark;

    /**
     * 排序
     */
    private Integer seq;
    
    /**
     * 商品档案信息
     */
    private GoodsInfo goodsInfo;

    /**
     * 税种档案
     */
    private TaxType taxTypeInfo;

    /**
     * 打印报表空白，ireport专用,0表示不空，1表示空
     */
    private int isreportblank;
    /**
     * 数据行是否为负数,1为负，0不是
     */
    private int isdataneg;

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
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 来源单据类型1销售回单2销售退货通知单3冲差单
     */
    public String getSourcetype() {
        return sourcetype;
    }

    /**
     * @param sourcetype 
	 *            来源单据类型1销售回单2销售退货通知单3冲差单
     */
    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
    }

    /**
     * @return 来源单据编号
     */
    public String getSourceid() {
        return sourceid;
    }

    /**
     * @param sourceid 
	 *            来源单据编号
     */
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid == null ? null : sourceid.trim();
    }

    /**
     * @return 来源单据明细编号
     */
    public String getSourcedetailid() {
        return sourcedetailid;
    }

    /**
     * @param sourcedetailid 
	 *            来源单据明细编号
     */
    public void setSourcedetailid(String sourcedetailid) {
        this.sourcedetailid = sourcedetailid == null ? null : sourcedetailid.trim();
    }

    /**
     * @return 发货单或者退货通知单编号
     */
    public String getBillno() {
        return billno;
    }

    /**
     * @param billno 
	 *            发货单或者退货通知单编号
     */
    public void setBillno(String billno) {
        this.billno = billno == null ? null : billno.trim();
    }

    /**
     * @return 发货单或者退货通知单明细编号
     */
    public String getBilldetailid() {
        return billdetailid;
    }

    /**
     * @param billdetailid 
	 *            发货单或者退货通知单明细编号
     */
    public void setBilldetailid(String billdetailid) {
        this.billdetailid = billdetailid == null ? null : billdetailid.trim();
    }

    /**
     * @return 是否折扣1是0否2返利折扣
     */
    public String getIsdiscount() {
        return isdiscount;
    }

    /**
     * @param isdiscount 
	 *            是否折扣1是0否2返利折扣
     */
    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount == null ? null : isdiscount.trim();
    }

    /**
     * @return 是否品牌折扣1是0否
     */
    public String getIsbranddiscount() {
        return isbranddiscount;
    }

    /**
     * @param isbranddiscount 
	 *            是否品牌折扣1是0否
     */
    public void setIsbranddiscount(String isbranddiscount) {
        this.isbranddiscount = isbranddiscount == null ? null : isbranddiscount.trim();
    }

    /**
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 上级客户（总店）
     */
    public String getPcustomerid() {
        return pcustomerid;
    }

    /**
     * @param pcustomerid 
	 *            上级客户（总店）
     */
    public void setPcustomerid(String pcustomerid) {
        this.pcustomerid = pcustomerid == null ? null : pcustomerid.trim();
    }

    /**
     * @return 客户分类
     */
    public String getCustomersort() {
        return customersort;
    }

    /**
     * @param customersort 
	 *            客户分类
     */
    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
    }

    /**
     * @return 销售区域
     */
    public String getSalesarea() {
        return salesarea;
    }

    /**
     * @param salesarea 
	 *            销售区域
     */
    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea == null ? null : salesarea.trim();
    }

    /**
     * @return 销售部门
     */
    public String getSalesdept() {
        return salesdept;
    }

    /**
     * @param salesdept 
	 *            销售部门
     */
    public void setSalesdept(String salesdept) {
        this.salesdept = salesdept == null ? null : salesdept.trim();
    }

    /**
     * @return 销售员
     */
    public String getSalesuser() {
        return salesuser;
    }

    /**
     * @param salesuser 
	 *            销售员
     */
    public void setSalesuser(String salesuser) {
        this.salesuser = salesuser == null ? null : salesuser.trim();
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
     * @return 品牌编号
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌编号
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

	public String getTaxtypename() {
		return taxtypename;
	}

	public void setTaxtypename(String taxtypename) {
		this.taxtypename = taxtypename;
	}

    public TaxType getTaxTypeInfo() {
        return taxTypeInfo;
    }

    public void setTaxTypeInfo(TaxType taxTypeInfo) {
        this.taxTypeInfo = taxTypeInfo;
    }

    public int getIsreportblank() {
        return isreportblank;
    }

    public void setIsreportblank(int isreportblank) {
        this.isreportblank = isreportblank;
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

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getIsrebate() {
        return isrebate;
    }

    public void setIsrebate(String isrebate) {
        this.isrebate = isrebate;
    }

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public int getIsdataneg() {
        return isdataneg;
    }

    public void setIsdataneg(int isdataneg) {
        this.isdataneg = isdataneg;
    }
}