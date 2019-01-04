package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaGoodsDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 箱装条形码
     */
    private String boxbarcode;

    /**
     * 商品分类
     */
    private String goodssort;

    /**
     * 单位编号
     */
    private String unitid;

    /**
     * 单位名称
     */
    private String unitname;

    /**
     * 辅单位编号
     */
    private String auxunitid;

    /**
     * 辅单位名称
     */
    private String auxunitname;

    /**
     * 箱装量
     */
    private BigDecimal boxnum;

    /**
     * 毛重
     */
    private BigDecimal grossweight;

    /**
     * 单体积
     */
    private BigDecimal singlevolume;

    /**
     * 仓库编码
     */
    private String storageid;

    /**
     * 含税进价(对应档案的最高采购价)
     */
    private BigDecimal buytaxprice;

    /**
     * 未税进价
     */
    private BigDecimal buynotaxprice;

    /**
     * 税种编号
     */
    private String taxtype;

    /**
     * 税率
     */
    private BigDecimal taxrate;

    /**
     * 价格套1(基准销售价)
     */
    private BigDecimal price1;

    /**
     * 价格套2
     */
    private BigDecimal price2;

    /**
     * 价格套3
     */
    private BigDecimal price3;

    /**
     * 价格套4
     */
    private BigDecimal price4;

    /**
     * 价格套5
     */
    private BigDecimal price5;

    /**
     * 价格套6
     */
    private BigDecimal price6;

    /**
     * 价格套7
     */
    private BigDecimal price7;

    /**
     * 价格套8
     */
    private BigDecimal price8;
    
    /**
     * 商品分类名
     */
    private String goodssortname;
    
    /**
     * 仓库名
     */
    private String storagename;
    
    /**
     * 税种
     */
    private String taxname;
    
    /**
     * 品牌名
     */
    private String brandname;
    
    /**
     * 箱体积
     */
    private BigDecimal totalvolume;

    /**
     * 箱重
     */
    private BigDecimal totalweight;

    /**
     * 长度
     */
    private BigDecimal glength;
    
    /**
     * 宽度
     */
    private BigDecimal gwidth;
    
    /**
     * 高度
     */
    private BigDecimal gheight;
    
    /**
     * 基准销售价
     */
    private BigDecimal basesaleprice;
    
    /**
     * 备注
     */
    private String remark;

    /**
     * 核算成本价
     */
    private BigDecimal costaccountprice;

    /**
     * 规格型号
     */
    private String model;

    /**
     * 产地
     */
    private String productfield;

    /**
     * 保质期
     */
    private BigDecimal shelflife;

    /**
     * 保质期单位1天2周3月4年
     */
    private String shelflifeunit;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
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
     * @return 商品名称
     */
    public String getGoodsname() {
        return goodsname;
    }

    /**
     * @param goodsname 
	 *            商品名称
     */
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
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
     * @return 条形码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode 
	 *            条形码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    /**
     * @return 箱装条形码
     */
    public String getBoxbarcode() {
        return boxbarcode;
    }

    /**
     * @param boxbarcode 
	 *            箱装条形码
     */
    public void setBoxbarcode(String boxbarcode) {
        this.boxbarcode = boxbarcode == null ? null : boxbarcode.trim();
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
     * @return 单位编号
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid 
	 *            单位编号
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname 
	 *            单位名称
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    /**
     * @return 辅单位编号
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid 
	 *            辅单位编号
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid == null ? null : auxunitid.trim();
    }

    /**
     * @return 辅单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname 
	 *            辅单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname == null ? null : auxunitname.trim();
    }

    /**
     * @return 箱装量
     */
    public BigDecimal getBoxnum() {
        return boxnum;
    }

    /**
     * @param boxnum 
	 *            箱装量
     */
    public void setBoxnum(BigDecimal boxnum) {
        this.boxnum = boxnum;
    }

    /**
     * @return 毛重
     */
    public BigDecimal getGrossweight() {
        return grossweight;
    }

    /**
     * @param grossweight 
	 *            毛重
     */
    public void setGrossweight(BigDecimal grossweight) {
        this.grossweight = grossweight;
    }

    /**
     * @return 单体积
     */
    public BigDecimal getSinglevolume() {
        return singlevolume;
    }

    /**
     * @param singlevolume 
	 *            单体积
     */
    public void setSinglevolume(BigDecimal singlevolume) {
        this.singlevolume = singlevolume;
    }

    /**
     * @return 仓库编码
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            仓库编码
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 含税进价(对应档案的最高采购价)
     */
    public BigDecimal getBuytaxprice() {
        return buytaxprice;
    }

    /**
     * @param buytaxprice 
	 *            含税进价(对应档案的最高采购价)
     */
    public void setBuytaxprice(BigDecimal buytaxprice) {
        this.buytaxprice = buytaxprice;
    }

    /**
     * @return 未税进价
     */
    public BigDecimal getBuynotaxprice() {
        return buynotaxprice;
    }

    /**
     * @param buynotaxprice 
	 *            未税进价
     */
    public void setBuynotaxprice(BigDecimal buynotaxprice) {
        this.buynotaxprice = buynotaxprice;
    }

    /**
     * @return 税种编号
     */
    public String getTaxtype() {
        return taxtype;
    }

    /**
     * @param taxtype 
	 *            税种编号
     */
    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype == null ? null : taxtype.trim();
    }

    /**
     * @return 税率
     */
    public BigDecimal getTaxrate() {
        return taxrate;
    }

    /**
     * @param taxrate 
	 *            税率
     */
    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    /**
     * @return 价格套1(基准销售价)
     */
    public BigDecimal getPrice1() {
        return price1;
    }

    /**
     * @param price1 
	 *            价格套1(基准销售价)
     */
    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    /**
     * @return 价格套2
     */
    public BigDecimal getPrice2() {
        return price2;
    }

    /**
     * @param price2 
	 *            价格套2
     */
    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    /**
     * @return 价格套3
     */
    public BigDecimal getPrice3() {
        return price3;
    }

    /**
     * @param price3 
	 *            价格套3
     */
    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
    }

    /**
     * @return 价格套4
     */
    public BigDecimal getPrice4() {
        return price4;
    }

    /**
     * @param price4 
	 *            价格套4
     */
    public void setPrice4(BigDecimal price4) {
        this.price4 = price4;
    }

    /**
     * @return 价格套5
     */
    public BigDecimal getPrice5() {
        return price5;
    }

    /**
     * @param price5 
	 *            价格套5
     */
    public void setPrice5(BigDecimal price5) {
        this.price5 = price5;
    }

    /**
     * @return 价格套6
     */
    public BigDecimal getPrice6() {
        return price6;
    }

    /**
     * @param price6 
	 *            价格套6
     */
    public void setPrice6(BigDecimal price6) {
        this.price6 = price6;
    }

    /**
     * @return 价格套7
     */
    public BigDecimal getPrice7() {
        return price7;
    }

    /**
     * @param price7 
	 *            价格套7
     */
    public void setPrice7(BigDecimal price7) {
        this.price7 = price7;
    }

    /**
     * @return 价格套8
     */
    public BigDecimal getPrice8() {
        return price8;
    }

    /**
     * @param price8 
	 *            价格套8
     */
    public void setPrice8(BigDecimal price8) {
        this.price8 = price8;
    }

	public String getGoodssortname() {
		return goodssortname;
	}

	public void setGoodssortname(String goodssortName) {
		this.goodssortname = goodssortName;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public String getTaxname() {
		return taxname;
	}

	public void setTaxname(String taxname) {
		this.taxname = taxname;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public BigDecimal getTotalvolume() {
		return totalvolume;
	}

	public void setTotalvolume(BigDecimal totalvolume) {
		this.totalvolume = totalvolume;
	}

	public BigDecimal getTotalweight() {
		return totalweight;
	}

	public void setTotalweight(BigDecimal totalweight) {
		this.totalweight = totalweight;
	}

	public BigDecimal getGlength() {
		return glength;
	}

	public void setGlength(BigDecimal glength) {
		this.glength = glength;
	}

	public BigDecimal getGwidth() {
		return gwidth;
	}

	public void setGwidth(BigDecimal gwidth) {
		this.gwidth = gwidth;
	}

	public BigDecimal getGheight() {
		return gheight;
	}

	public void setGheight(BigDecimal gheight) {
		this.gheight = gheight;
	}

	public BigDecimal getBasesaleprice() {
		return basesaleprice;
	}

	public void setBasesaleprice(BigDecimal basesaleprice) {
		this.basesaleprice = basesaleprice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getCostaccountprice() {
		return costaccountprice;
	}

	public void setCostaccountprice(BigDecimal costaccountprice) {
		this.costaccountprice = costaccountprice;
	}

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductfield() {
        return productfield;
    }

    public void setProductfield(String productfield) {
        this.productfield = productfield;
    }

    public BigDecimal getShelflife() {
        return shelflife;
    }

    public void setShelflife(BigDecimal shelflife) {
        this.shelflife = shelflife;
    }

    public String getShelflifeunit() {
        return shelflifeunit;
    }

    public void setShelflifeunit(String shelflifeunit) {
        this.shelflifeunit = shelflifeunit;
    }
}