/**
 * @(#)PlannedOrderAnalysisReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-11-16 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class PlannedOrderAnalysis implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3803649194218371611L;

    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 商品分类
     */
    private String goodssort;
    /**
     * 商品分类名称
     */
    private String goodssortname;
    
    /**
     * 条形码
     */
    private String barcode;
    
    /**
     * 品牌编码
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 品牌部门
     */
    private String branddept;
    /**
     * 品牌部门名称
     */
    private String branddeptname;
    
    /**
     * 商品详细信息
     */
    private GoodsInfo goodsInfo;
    /**
     * 计量单位编码
     */
    private String unitid;

    /**
     * 计量单位名称
     */
    private String unitname;

    /**
     * 辅助计量单位编码
     */
    private String auxunitid;

    /**
     * 辅助计量单位名称
     */
    private String auxunitname;
    /**
     * 箱装数
     */
    private BigDecimal boxnum;
    /**
     * 箱价
     */
    private BigDecimal boxamount;
    /**
     * 采购单价
     */
    private BigDecimal buyprice;
    
    /**
     * 实际库存(现存量)
     */
    private BigDecimal existingunitnum;
    
    /**
     * 实际库存(现存量) 辅单位
     */
    private BigDecimal existingnum;
    
    /**
     * 实际库存描述
     */
    private String existingnumdetail;
    
    /**
     * 实际库存金额
     */
    private BigDecimal existingamount;
    
    /**
     * 在途量
     */
    private BigDecimal transitunitnum;
    
    /**
     * 在途量 辅单位
     */
    private BigDecimal transitnum;
    
    /**
     * 在途量描述
     */
    private String transitnumdetail;
    
    /**
     * 在途金额
     */
    private BigDecimal transitamount;
    /**
     * 本期库存（含待发量，在途量）
     */
    private BigDecimal curstorageunitnum;
    /**
     * 本期库存 辅单位（含待发量，在途量）
     */
    private BigDecimal curstoragenum;
    
    /**
     * 本期库存描述
     */
    private String curstoragenumdetail;
    /**
     * 本期库存金额
     */
    private BigDecimal curstorageamount;
    /**
     * 可订单主单位数量
     */
    private BigDecimal canorderunitnum;
    /**
     * 可订单数量 辅助单位
     */
    private BigDecimal canordernum;
    
    /**
     * 可订单数量描述
     */
    private String canordernumdetail;
    /**
     * 可订单金额 辅单位
     */
    private BigDecimal canorderamount;
    /**
     * 本次订货数量
     */
    private BigDecimal ordernum;
    
    /**
     * 本次订货数量描述
     */
    private String ordernumdetail;
    /**
     * 本次订货金额
     */
    private BigDecimal orderamount;
    /**
     * 本次订货主单位数
     */
    private BigDecimal orderunitnum;
    /**
     * 本次订货辅单位余数
     */
    private BigDecimal orderauxremainder;
    /**
     * 合计库存数
     */
    private BigDecimal totalstoragenum;
    
    /**
     * 合计库存数量描述
     */
    private String totalstoragenumdetail;
    /**
     * 合计库存数主单位
     */
    private BigDecimal totalstorageunitnum;
    /**
     * 合计库存金额
     */
    private BigDecimal totalstorageamount;
    /**
     * 可销售天数
     */
    private Integer cansaleday;
    /**
     * 前期同期销售天数
     */
    private int saleday;
    /**
     * 备注
     */
    private String remark;
    /**
     * 同期销售数量 主单位
     */
    private BigDecimal tqsaleunitnum;
    /**
     * 同期销售数量 辅单位
     */
    private BigDecimal tqsalenum;
    /**
     * 同期销售金额
     */
    private BigDecimal tqsaleamount;
    /**
     * 前期销售数量 主单位
     */
    private BigDecimal qqsaleunitnum;
    /**
     * 前期销售数量 辅单位
     */
    private BigDecimal qqsalenum;
    /**
     * 前期销售金额
     */
    private BigDecimal qqsaleamount;
    
    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;
    /**
     * 仓库编号
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 商品是否在采购计划单或采购订单里,0否，1是
     */
    private int isgoodsinbill;
    
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
	public BigDecimal getBoxnum() {
		return boxnum;
	}
	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}
	public BigDecimal getBoxamount() {
		return boxamount;
	}
	public void setBoxamount(BigDecimal boxamount) {
		this.boxamount = boxamount;
	}
	public BigDecimal getBuyprice() {
		return buyprice;
	}
	public void setBuyprice(BigDecimal buyprice) {
		this.buyprice = buyprice;
	}
	public BigDecimal getCurstorageunitnum() {
		return curstorageunitnum;
	}
	public void setCurstorageunitnum(BigDecimal curstorageunitnum) {
		this.curstorageunitnum = curstorageunitnum;
	}
	public BigDecimal getCurstoragenum() {
		return curstoragenum;
	}
	public void setCurstoragenum(BigDecimal curstoragenum) {
		this.curstoragenum = curstoragenum;
	}
	public BigDecimal getCanordernum() {
		return canordernum;
	}
	public void setCanordernum(BigDecimal canordernum) {
		this.canordernum = canordernum;
	}
	public BigDecimal getCanorderamount() {
		return canorderamount;
	}
	public void setCanorderamount(BigDecimal canorderamount) {
		this.canorderamount = canorderamount;
	}
	public BigDecimal getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(BigDecimal ordernum) {
		this.ordernum = ordernum;
	}
	public BigDecimal getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(BigDecimal orderamount) {
		this.orderamount = orderamount;
	}
	public BigDecimal getTotalstoragenum() {
		return totalstoragenum;
	}
	public void setTotalstoragenum(BigDecimal totalstoragenum) {
		this.totalstoragenum = totalstoragenum;
	}
	public Integer getCansaleday() {
		return cansaleday;
	}
	public void setCansaleday(Integer cansaleday) {
		this.cansaleday = cansaleday;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getCanorderunitnum() {
		return canorderunitnum;
	}
	public void setCanorderunitnum(BigDecimal canorderunitnum) {
		this.canorderunitnum = canorderunitnum;
	}
	public BigDecimal getTqsaleunitnum() {
		return tqsaleunitnum;
	}
	public void setTqsaleunitnum(BigDecimal tqsaleunitnum) {
		this.tqsaleunitnum = tqsaleunitnum;
	}
	public BigDecimal getTqsalenum() {
		return tqsalenum;
	}
	public void setTqsalenum(BigDecimal tqsalenum) {
		this.tqsalenum = tqsalenum;
	}
	public BigDecimal getQqsaleunitnum() {
		return qqsaleunitnum;
	}
	public void setQqsaleunitnum(BigDecimal qqsaleunitnum) {
		this.qqsaleunitnum = qqsaleunitnum;
	}
	public BigDecimal getQqsalenum() {
		return qqsalenum;
	}
	public void setQqsalenum(BigDecimal qqsalenum) {
		this.qqsalenum = qqsalenum;
	}
	public BigDecimal getOrderunitnum() {
		return orderunitnum;
	}
	public void setOrderunitnum(BigDecimal orderunitnum) {
		this.orderunitnum = orderunitnum;
	}
	public BigDecimal getOrderauxremainder() {
		return orderauxremainder;
	}
	public void setOrderauxremainder(BigDecimal orderauxremainder) {
		this.orderauxremainder = orderauxremainder;
	}
	public BigDecimal getTotalstorageunitnum() {
		return totalstorageunitnum;
	}
	public void setTotalstorageunitnum(BigDecimal totalstorageunitnum) {
		this.totalstorageunitnum = totalstorageunitnum;
	}
	public int getSaleday() {
		return saleday;
	}
	public void setSaleday(int saleday) {
		this.saleday = saleday;
	}
	public String getBranddept() {
		return branddept;
	}
	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}
	public String getBranddeptname() {
		return branddeptname;
	}
	public void setBranddeptname(String branddeptname) {
		this.branddeptname = branddeptname;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public BigDecimal getCurstorageamount() {
		return curstorageamount;
	}
	public void setCurstorageamount(BigDecimal curstorageamount) {
		this.curstorageamount = curstorageamount;
	}
	public BigDecimal getTotalstorageamount() {
		return totalstorageamount;
	}
	public void setTotalstorageamount(BigDecimal totalstorageamount) {
		this.totalstorageamount = totalstorageamount;
	}
	public BigDecimal getExistingunitnum() {
		return existingunitnum;
	}
	public void setExistingunitnum(BigDecimal existingunitnum) {
		this.existingunitnum = existingunitnum;
	}
	public BigDecimal getTransitunitnum() {
		return transitunitnum;
	}
	public void setTransitunitnum(BigDecimal transitunitnum) {
		this.transitunitnum = transitunitnum;
	}
	public BigDecimal getExistingnum() {
		return existingnum;
	}
	public void setExistingnum(BigDecimal existingnum) {
		this.existingnum = existingnum;
	}
	public BigDecimal getTransitnum() {
		return transitnum;
	}
	public void setTransitnum(BigDecimal transitnum) {
		this.transitnum = transitnum;
	}
	public BigDecimal getExistingamount() {
		return existingamount;
	}
	public void setExistingamount(BigDecimal existingamount) {
		this.existingamount = existingamount;
	}
	public BigDecimal getTransitamount() {
		return transitamount;
	}
	public void setTransitamount(BigDecimal transitamount) {
		this.transitamount = transitamount;
	}
	public String getExistingnumdetail() {
		return existingnumdetail;
	}
	public void setExistingnumdetail(String existingnumdetail) {
		this.existingnumdetail = existingnumdetail;
	}
	public String getTransitnumdetail() {
		return transitnumdetail;
	}
	public void setTransitnumdetail(String transitnumdetail) {
		this.transitnumdetail = transitnumdetail;
	}
	public String getCurstoragenumdetail() {
		return curstoragenumdetail;
	}
	public void setCurstoragenumdetail(String curstoragenumdetail) {
		this.curstoragenumdetail = curstoragenumdetail;
	}
	public String getCanordernumdetail() {
		return canordernumdetail;
	}
	public void setCanordernumdetail(String canordernumdetail) {
		this.canordernumdetail = canordernumdetail;
	}
	public String getOrdernumdetail() {
		return ordernumdetail;
	}
	public void setOrdernumdetail(String ordernumdetail) {
		this.ordernumdetail = ordernumdetail;
	}
	public String getTotalstoragenumdetail() {
		return totalstoragenumdetail;
	}
	public void setTotalstoragenumdetail(String totalstoragenumdetail) {
		this.totalstoragenumdetail = totalstoragenumdetail;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
        this.suppliername = suppliername == null ? null : suppliername.trim();
	}
	public String getStorageid() {
		return storageid;
	}
	public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
	}
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
        this.storagename = storagename == null ? null : storagename.trim();
	}
	public int getIsgoodsinbill() {
		return isgoodsinbill;
	}
	public void setIsgoodsinbill(int isgoodsinbill) {
		this.isgoodsinbill = isgoodsinbill;
	}
	public String getGoodssort() {
		return goodssort;
	}
	public void setGoodssort(String goodssort) {
        this.goodssort = goodssort == null ? null : goodssort.trim();
	}
	public String getGoodssortname() {
		return goodssortname;
	}
	public void setGoodssortname(String goodssortname) {
        this.goodssortname = goodssortname == null ? null : goodssortname.trim();
	}
	public BigDecimal getTqsaleamount() {
		return tqsaleamount;
	}
	public BigDecimal getQqsaleamount() {
		return qqsaleamount;
	}
	public void setTqsaleamount(BigDecimal tqsaleamount) {
		this.tqsaleamount = tqsaleamount;
	}
	public void setQqsaleamount(BigDecimal qqsaleamount) {
		this.qqsaleamount = qqsaleamount;
	}
}

