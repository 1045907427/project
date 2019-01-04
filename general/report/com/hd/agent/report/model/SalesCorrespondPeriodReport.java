/**
 * @(#)SalesCorrespondPeriodReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-11-23 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class SalesCorrespondPeriodReport implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8462001301614469058L;

	private String treeid;
    
    private String state;
    
    private String parentid;
    /**
     * 编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 总店客户
     */
    private String pcustomerid;
    /**
     * 总店客户名称
     */
    private String pcustomername;
    /**
     * 所属部门编号
     */
    private String salesdept;
    /**
     * 所属部门名称
     */
    private String salesdeptname;
    /**
     * 所属区域
     */
    private String salesarea;
    /**
     * 所属区域名称
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
     * 供应商编码
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;
    
    /**
     * 条形码
     */
    private String barcode;
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
     * 品牌业务员名称
     */
    private String brandusername;
    /**
     * 品牌部门
     */
    private String branddept;
    /**
     * 品牌部门名称
     */
    private String branddeptname;
    
    /**
     * 销售数量
     */
    private BigDecimal salenum;
    
    /**
     * 销售额
     */
    private BigDecimal saleamount;

    /**
     * 无税销售金额
     */
    private BigDecimal salenotaxamount;
	/**
	 * 销售箱数
	 */
	private BigDecimal saletotalbox;
	/**
	 * 前期销售箱数
	 */
	private BigDecimal qqsaletotalbox;
	/**
	 * 销售箱数增长
	 */
	private BigDecimal saletotalboxgrowth;
    /**
     * 前期销售数量
     */
    private BigDecimal qqsalenum;
    
    /**
     * 前期销售金额
     */
    private BigDecimal qqsaleamount;

    /**
     * 前期无税销售金额
     */
    private BigDecimal qqsalenotaxamount;
    /**
     * 销售增长率
     */
    private BigDecimal salegrowth;
    
    /**
     * 销售毛利金额
     */
    private BigDecimal salegrossamount;

    /**
     * 前期销售毛利金额
     */
    private BigDecimal qqsalegrossamount;
    /**
     * 销售毛利增长
     */
    private BigDecimal salegrossgrowth;
    /**
     * 销售毛利率
     */
    private BigDecimal salegrossrate;
    /**
     * 前期销售毛利率
     */
    private BigDecimal qqsalegrossrate;
    /**
     * 前期销售毛利率增长率
     */
    private BigDecimal salegrossrategrowth;
    /**
     * 成本金额
     */
    private BigDecimal costamount;
    /**
     * 前期成本金额
     */
    private BigDecimal qqcostamount;
    /**
     * 回笼金额
     */
    private BigDecimal writeoffamount;
    /**
     * 回笼成本金额
     */
    private BigDecimal costwriteoffamount;
    /**
     * 前期回笼金额
     */
    private BigDecimal qqwriteoffamount;
    /**
     * 前期回笼成本金额
     */
    private BigDecimal qqcostwriteoffamount;
    /**
     * 回笼毛利金额
     */
    private BigDecimal writeoffgrossamount;
    /**
     * 前期回笼毛利
     */
    private BigDecimal qqwriteoffgrossamount;
    /**
     * 回笼毛利增长
     */
    private BigDecimal writeoffgrossgrowth;
    /**
     * 回笼增长率
     */
    private BigDecimal writeoffgrowth;
    /**
     * 回笼毛利率
     */
    private BigDecimal writeoffgrate;
    /**
     * 前期回笼毛利率
     */
    private BigDecimal qqwriteoffgrate;
    /**
     * 回笼毛利率增长
     */
    private BigDecimal writeoffgrategrowth;
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
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
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
     * @return 销售数量
     */
    public BigDecimal getSalenum() {
        return salenum;
    }

    /**
     * @param salenum 
	 *            销售数量
     */
    public void setSalenum(BigDecimal salenum) {
        this.salenum = salenum;
    }

    /**
     * @return 销售额
     */
    public BigDecimal getSaleamount() {
        return saleamount;
    }

    /**
     * @param saleamount 
	 *            销售额
     */
    public void setSaleamount(BigDecimal saleamount) {
        this.saleamount = saleamount;
    }

    /**
     * @return 无税销售金额
     */
    public BigDecimal getSalenotaxamount() {
        return salenotaxamount;
    }

    /**
     * @param salenotaxamount 
	 *            无税销售金额
     */
    public void setSalenotaxamount(BigDecimal salenotaxamount) {
        this.salenotaxamount = salenotaxamount;
    }

	public BigDecimal getCostamount() {
		if(null!=costamount && costamount.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return costamount;
		}
	}

	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
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

	public String getSalesarea() {
		return salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getSalesdept() {
		return salesdept;
	}

	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}

	public String getSalesdeptname() {
		return salesdeptname;
	}

	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
	}

	public String getSalesuser() {
		return salesuser;
	}

	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getSalesareaname() {
		return salesareaname;
	}

	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}

	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}

	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}

	public BigDecimal getCostwriteoffamount() {
		return costwriteoffamount;
	}

	public void setCostwriteoffamount(BigDecimal costwriteoffamount) {
		this.costwriteoffamount = costwriteoffamount;
	}

	public BigDecimal getWriteoffgrate() {
		return writeoffgrate;
	}

	public void setWriteoffgrate(BigDecimal writeoffgrate) {
		this.writeoffgrate = writeoffgrate;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getSalesusername() {
		return salesusername;
	}

	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getBranduser() {
		return branduser;
	}

	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}

	public String getBrandusername() {
		return brandusername;
	}

	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
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
	public String getTreeid() {
		return treeid;
	}

	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public BigDecimal getQqsalenum() {
		return qqsalenum;
	}

	public void setQqsalenum(BigDecimal qqsalenum) {
		this.qqsalenum = qqsalenum;
	}

	public BigDecimal getQqsaleamount() {
		return qqsaleamount;
	}

	public void setQqsaleamount(BigDecimal qqsaleamount) {
		this.qqsaleamount = qqsaleamount;
	}

	public BigDecimal getQqsalenotaxamount() {
		return qqsalenotaxamount;
	}

	public void setQqsalenotaxamount(BigDecimal qqsalenotaxamount) {
		this.qqsalenotaxamount = qqsalenotaxamount;
	}

	public BigDecimal getSalegrowth() {
		return salegrowth;
	}

	public void setSalegrowth(BigDecimal salegrowth) {
		this.salegrowth = salegrowth;
	}

	public BigDecimal getSalegrossamount() {
		return salegrossamount;
	}

	public void setSalegrossamount(BigDecimal salegrossamount) {
		this.salegrossamount = salegrossamount;
	}

	public BigDecimal getQqsalegrossamount() {
		return qqsalegrossamount;
	}

	public void setQqsalegrossamount(BigDecimal qqsalegrossamount) {
		this.qqsalegrossamount = qqsalegrossamount;
	}

	public BigDecimal getQqcostamount() {
		return qqcostamount;
	}

	public void setQqcostamount(BigDecimal qqcostamount) {
		this.qqcostamount = qqcostamount;
	}

	public BigDecimal getQqwriteoffamount() {
		return qqwriteoffamount;
	}

	public void setQqwriteoffamount(BigDecimal qqwriteoffamount) {
		this.qqwriteoffamount = qqwriteoffamount;
	}

	public BigDecimal getQqcostwriteoffamount() {
		return qqcostwriteoffamount;
	}

	public void setQqcostwriteoffamount(BigDecimal qqcostwriteoffamount) {
		this.qqcostwriteoffamount = qqcostwriteoffamount;
	}

	public BigDecimal getSalegrossgrowth() {
		return salegrossgrowth;
	}

	public void setSalegrossgrowth(BigDecimal salegrossgrowth) {
		this.salegrossgrowth = salegrossgrowth;
	}

	public BigDecimal getSalegrossrate() {
		return salegrossrate;
	}

	public void setSalegrossrate(BigDecimal salegrossrate) {
		this.salegrossrate = salegrossrate;
	}

	public BigDecimal getQqsalegrossrate() {
		return qqsalegrossrate;
	}

	public void setQqsalegrossrate(BigDecimal qqsalegrossrate) {
		this.qqsalegrossrate = qqsalegrossrate;
	}

	public BigDecimal getSalegrossrategrowth() {
		return salegrossrategrowth;
	}

	public void setSalegrossrategrowth(BigDecimal salegrossrategrowth) {
		this.salegrossrategrowth = salegrossrategrowth;
	}

	public BigDecimal getWriteoffgrowth() {
		return writeoffgrowth;
	}

	public void setWriteoffgrowth(BigDecimal writeoffgrowth) {
		this.writeoffgrowth = writeoffgrowth;
	}

	public BigDecimal getQqwriteoffgrate() {
		return qqwriteoffgrate;
	}

	public void setQqwriteoffgrate(BigDecimal qqwriteoffgrate) {
		this.qqwriteoffgrate = qqwriteoffgrate;
	}

	public BigDecimal getWriteoffgrategrowth() {
		return writeoffgrategrowth;
	}

	public void setWriteoffgrategrowth(BigDecimal writeoffgrategrowth) {
		this.writeoffgrategrowth = writeoffgrategrowth;
	}

	public BigDecimal getWriteoffgrossamount() {
		return writeoffgrossamount;
	}

	public void setWriteoffgrossamount(BigDecimal writeoffgrossamount) {
		this.writeoffgrossamount = writeoffgrossamount;
	}

	public BigDecimal getQqwriteoffgrossamount() {
		return qqwriteoffgrossamount;
	}

	public void setQqwriteoffgrossamount(BigDecimal qqwriteoffgrossamount) {
		this.qqwriteoffgrossamount = qqwriteoffgrossamount;
	}

	public BigDecimal getWriteoffgrossgrowth() {
		return writeoffgrossgrowth;
	}

	public void setWriteoffgrossgrowth(BigDecimal writeoffgrossgrowth) {
		this.writeoffgrossgrowth = writeoffgrossgrowth;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public BigDecimal getSaletotalbox() {
		return saletotalbox;
	}

	public void setSaletotalbox(BigDecimal saletotalbox) {
		this.saletotalbox = saletotalbox;
	}

	public BigDecimal getQqsaletotalbox() {
		return qqsaletotalbox;
	}

	public void setQqsaletotalbox(BigDecimal qqsaletotalbox) {
		this.qqsaletotalbox = qqsaletotalbox;
	}

	public BigDecimal getSaletotalboxgrowth() {
		return saletotalboxgrowth;
	}

	public void setSaletotalboxgrowth(BigDecimal saletotalboxgrowth) {
		this.saletotalboxgrowth = saletotalboxgrowth;
	}

	public String getGoodssort() {
		return goodssort;
	}

	public void setGoodssort(String goodssort) {
		this.goodssort = goodssort;
	}

	public String getGoodssortname() {
		return goodssortname;
	}

	public void setGoodssortname(String goodssortname) {
		this.goodssortname = goodssortname;
	}
}

