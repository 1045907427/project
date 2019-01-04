/**
 * @(#)SalesCustomerSandPriceReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-1-22 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hd.agent.basefiles.model.GoodsInfo;

/**
 * 
 * 客户合同商品报表
 * @author zhanghonghui
 */
public class SalesCustomerPriceReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4723340094729417125L;

	/**
     * 编号
     */
    private String id;
    
    /**
     * 商品助记符
     */
    private String goodsspell;
    
    /**
     * 客户助记符 
     */
    private String shortcode;

    /**
     * 客户编号
     */
    private String customerid;
    
    /**
     * 客户名称
     */
    private String customername;
    
    /**
     * 上级客户
     */
    private String pcustomerid;
    
    /**
     * 上级客户名称
     */
    private String pcustomername;

    /**
     * 商品编号
     */
    private String goodsid;
    
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 品牌
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    
    /**
     * 条形码
     */
    private String barcode;

	/**
	 * 箱装量
	 */
	private BigDecimal boxnum;
    
    /**
     * 税率
     */
    private BigDecimal taxrate;
    
    /**
     * 店内码
     */
    private String shopid;
	/**
	 * 价格套
	 */
	private String pricetype;
	/**
     * 价格套价格
     */
    private BigDecimal taxprice;
    /**
     * 含税合同价格
     */
    private BigDecimal price;
    /**
     * 箱价
     */
    private BigDecimal boxprice;
    
    /**
     * 未税合同价格
     */
    private BigDecimal noprice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date addtime;
    
    private GoodsInfo goodsInfo;

	/**
	 * 成本价（商品最新采购价）
	 */
	private BigDecimal newbuyprice;
	/**
	 * 毛利率=（含税合同价-成本价）/含税合同价
	 */
	private BigDecimal rate;

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
        this.customerid = customerid;
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
     * @return 合同价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            合同价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * @return 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public BigDecimal getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
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

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodsspell() {
		return goodsspell;
	}

	public void setGoodsspell(String goodsspell) {
		this.goodsspell = goodsspell;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getNoprice() {
		return noprice;
	}

	public void setNoprice(BigDecimal noprice) {
		this.noprice = noprice;
	}

	public BigDecimal getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
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

	public BigDecimal getBoxprice() {
		return boxprice;
	}

	public void setBoxprice(BigDecimal boxprice) {
		this.boxprice = boxprice;
	}

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}

	public BigDecimal getNewbuyprice() {
		return newbuyprice;
	}

	public void setNewbuyprice(BigDecimal newbuyprice) {
		this.newbuyprice = newbuyprice;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getPricetype() {
		return pricetype;
	}

	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}
}

