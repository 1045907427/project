package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.GoodsInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderGoodsDetail implements Serializable {
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
     * 商品类型0正常商品1赠品2捆绑
     */
    private String deliverytype;

    /**
     * 交货日期
     */
    private String deliverydate;

    /**
     * 过期日期
     */
    private Date expirationdate;

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

    private String storagename;

    /**
     * 要货单价（含税）
     */
    private BigDecimal demandprice;

    /**
     * 要货金额
     */
    private BigDecimal demandamount;

    /**
     * 是否已补货1是0否
     */
    private String issupply;

    /**
     * 批次现存量编号
     */
    private String summarybatchid;

    /**
     * 库位
     */
    private String storagelocationid;

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
     * 品牌折扣或订单折扣摊分类型0金额1数量2箱数
     */
    private String repartitiontype;

    /**
     * 最新采购价
     */
    private BigDecimal buyprice ;
    /**
     * 商品总箱重
     */
    private BigDecimal totalboxweight;
    /**
     * 商品总箱体积
     */
    private BigDecimal totalboxvolume;

    /**
     * 判断仓库商品数量是否足够 1足够 0不足
     */
    private String Isenough;

    /**
     * 可用量
     */
    private BigDecimal usablenum;

    /**
     * 含税箱价
     */
    private BigDecimal boxprice;

    /**
     * 最低销售价
     */
    private BigDecimal lowestsaleprice;
    /**
     * 基准价
     */
    private BigDecimal basesaleprice;

    /**
     * 对应的商品信息
     */
    private GoodsInfo goodsInfo;

    /**
     * 参考价
     */
    private BigDecimal referenceprice;

    /**
     * 已生成订单数量
     */
    private BigDecimal orderunitnum;

    /**
     * 未生成订单数量
     */
    private BigDecimal notorderunitnum;

    /**
     * 已生成含税订单金额
     */
    private BigDecimal ordertaxamount;

    /**
     * 已生成未税订单金额
     */
    private BigDecimal ordernotaxamount;

    /**
     * 未生成含税订单金额
     */
    private BigDecimal notordertaxamount;

    /**
     * 未生成未税订单金额
     */
    private BigDecimal notordernotaxamount;

    /**
     * 未生成订单箱数
     */
    private BigDecimal notorderbox;

    /**
     * 未生成订单个数 辅数量
     */
    private BigDecimal notorderovernum;

    /**
     * 原价
     */
    private BigDecimal oldtaxprice;

    /**
     * 要生成订单数量(手机在线订货单报表可以直接生成订单,这个字段记录手机端要生成的订单数量)
     */
    private BigDecimal isorderunitnum;

    /**
     * 要生成订单数量(手机在线订货单报表可以直接生成订单,这个字段记录手机端要生成的订单数量)
     */
    private BigDecimal isordertaxamount;


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
     * @return 是否折扣1是0否
     */
    public String getIsdiscount() {
        return isdiscount;
    }

    /**
     * @param isdiscount 
	 *            是否折扣1是0否
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
     * @return 过期日期
     */
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

    /**
     * @return 是否已补货1是0否
     */
    public String getIssupply() {
        return issupply;
    }

    /**
     * @param issupply 
	 *            是否已补货1是0否
     */
    public void setIssupply(String issupply) {
        this.issupply = issupply == null ? null : issupply.trim();
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
     * @return 品牌折扣或订单折扣摊分类型0金额1数量2箱数
     */
    public String getRepartitiontype() {
        return repartitiontype;
    }

    /**
     * @param repartitiontype 
	 *            品牌折扣或订单折扣摊分类型0金额1数量2箱数
     */
    public void setRepartitiontype(String repartitiontype) {
        this.repartitiontype = repartitiontype == null ? null : repartitiontype.trim();
    }

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    public BigDecimal getTotalboxweight() {
        return totalboxweight;
    }

    public void setTotalboxweight(BigDecimal totalboxweight) {
        this.totalboxweight = totalboxweight;
    }

    public BigDecimal getTotalboxvolume() {
        return totalboxvolume;
    }

    public void setTotalboxvolume(BigDecimal totalboxvolume) {
        this.totalboxvolume = totalboxvolume;
    }

    public String getTaxtypename() {
        return taxtypename;
    }

    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getIsenough() {
        return Isenough;
    }

    public void setIsenough(String isenough) {
        Isenough = isenough;
    }

    public BigDecimal getUsablenum() {
        return usablenum;
    }

    public void setUsablenum(BigDecimal usablenum) {
        this.usablenum = usablenum;
    }

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }

    public BigDecimal getLowestsaleprice() {
        return lowestsaleprice;
    }

    public void setLowestsaleprice(BigDecimal lowestsaleprice) {
        this.lowestsaleprice = lowestsaleprice;
    }

    public BigDecimal getBasesaleprice() {
        return basesaleprice;
    }

    public void setBasesaleprice(BigDecimal basesaleprice) {
        this.basesaleprice = basesaleprice;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public BigDecimal getReferenceprice() {
        return referenceprice;
    }

    public void setReferenceprice(BigDecimal referenceprice) {
        this.referenceprice = referenceprice;
    }

    public BigDecimal getOrderunitnum() {
        return orderunitnum;
    }

    public void setOrderunitnum(BigDecimal orderunitnum) {
        this.orderunitnum = orderunitnum;
    }

    public BigDecimal getNotorderunitnum() {
        return notorderunitnum;
    }

    public void setNotorderunitnum(BigDecimal notorderunitnum) {
        this.notorderunitnum = notorderunitnum;
    }

    public BigDecimal getOrdertaxamount() {
        return ordertaxamount;
    }

    public void setOrdertaxamount(BigDecimal ordertaxamount) {
        this.ordertaxamount = ordertaxamount;
    }

    public BigDecimal getOrdernotaxamount() {
        return ordernotaxamount;
    }

    public void setOrdernotaxamount(BigDecimal ordernotaxamount) {
        this.ordernotaxamount = ordernotaxamount;
    }

    public BigDecimal getNotordertaxamount() {
        return notordertaxamount;
    }

    public void setNotordertaxamount(BigDecimal notordertaxamount) {
        this.notordertaxamount = notordertaxamount;
    }

    public BigDecimal getNotordernotaxamount() {
        return notordernotaxamount;
    }

    public void setNotordernotaxamount(BigDecimal notordernotaxamount) {
        this.notordernotaxamount = notordernotaxamount;
    }

    public BigDecimal getNotorderbox() {
        return notorderbox;
    }

    public void setNotorderbox(BigDecimal notorderbox) {
        this.notorderbox = notorderbox;
    }

    public BigDecimal getNotorderovernum() {
        return notorderovernum;
    }

    public void setNotorderovernum(BigDecimal notorderovernum) {
        this.notorderovernum = notorderovernum;
    }

    public BigDecimal getOldtaxprice() {
        return oldtaxprice;
    }

    public void setOldtaxprice(BigDecimal oldtaxprice) {
        this.oldtaxprice = oldtaxprice;
    }

    public BigDecimal getIsorderunitnum() {
        return isorderunitnum;
    }

    public void setIsorderunitnum(BigDecimal isorderunitnum) {
        this.isorderunitnum = isorderunitnum;
    }

    public BigDecimal getIsordertaxamount() {
        return isordertaxamount;
    }

    public void setIsordertaxamount(BigDecimal isordertaxamount) {
        this.isordertaxamount = isordertaxamount;
    }
}