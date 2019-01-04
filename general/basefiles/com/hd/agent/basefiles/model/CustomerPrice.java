package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerPrice implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 条形码
     */
    private String barcode;
    
    /**
     * 税率
     */
    private BigDecimal taxrate;
    
    /**
     * 店内码
     */
    private String shopid;
    
    /**
     * 初始店内码
     */
    private String initshopid;

    /**
     * 价格套价格
     */
    private BigDecimal taxprice;

    /**
     * 价格套箱价
     */
    private BigDecimal boxprice;
    /**
     * 含税合同价格
     */
    private BigDecimal price;

    /**
     * 初始含税合同价格
     */
    private BigDecimal initprice;

    /**
     * 合同箱价
     */
    private BigDecimal ctcboxprice;

    /**
     * 初始合同箱价
     */
    private BigDecimal initctcboxprice;
    
    /**
     * 未税合同价格
     */
    private BigDecimal noprice;
    
    /**
     * 初始未税合同价格
     */
    private BigDecimal initnoprice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date addtime;
    
    /**
     * 是否修改
     */
    private String isedit;

    private GoodsInfo goodsInfo;

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
        this.shopid = shopid == null ? null : shopid.trim();
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

	public String getIsedit() {
		return null != isedit ? isedit : "0";
	}

	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}

	public String getInitshopid() {
		return initshopid;
	}

	public void setInitshopid(String initshopid) {
		this.initshopid = initshopid;
	}

	public BigDecimal getInitprice() {
		return initprice;
	}

	public void setInitprice(BigDecimal initprice) {
		this.initprice = initprice;
	}

	public BigDecimal getInitnoprice() {
		return initnoprice;
	}

	public void setInitnoprice(BigDecimal initnoprice) {
		this.initnoprice = initnoprice;
	}

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }

    public BigDecimal getCtcboxprice() {
        return ctcboxprice;
    }

    public void setCtcboxprice(BigDecimal ctcboxprice) {
        this.ctcboxprice = ctcboxprice;
    }

    public BigDecimal getInitctcboxprice() {
        return initctcboxprice;
    }

    public void setInitctcboxprice(BigDecimal initctcboxprice) {
        this.initctcboxprice = initctcboxprice;
    }
}