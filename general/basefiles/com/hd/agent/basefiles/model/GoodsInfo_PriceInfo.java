package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsInfo_PriceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 价格套编码
     */
    private String code;

    /**
     * 价格套名称
     */
    private String name;

    /**
     * 含税单价
     */
    private BigDecimal taxprice;

    /**
     * 税种(来源税种档案)
     */
    private String taxtype;
    
    /**
     * 税种名称
     */
    private String taxtypeName;
    
    /**
     * 税种税率
     */
    private BigDecimal taxtypeRate;

    /**
     * 无税单价
     */
    private BigDecimal price;

    /**
     * 箱价
     */
    private BigDecimal boxprice;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 商品名称
     */
    private String goodsname;
    
    /**
     * 商品品牌
     */
    private String goodsbrand;
    
    private String goodsbrandname;

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
     * @return 价格套编码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 
	 *            价格套编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return 价格套名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            价格套名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * @return 税种(来源税种档案)
     */
    public String getTaxtype() {
        return taxtype;
    }

    /**
     * @param taxtype 
	 *            税种(来源税种档案)
     */
    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype == null ? null : taxtype.trim();
    }

    /**
     * @return 无税单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            无税单价
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
        this.remark = remark == null ? null : remark.trim();
    }

	public String getTaxtypeName() {
		return taxtypeName;
	}

	public void setTaxtypeName(String taxtypeName) {
		this.taxtypeName = taxtypeName;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodsbrand() {
		return goodsbrand;
	}

	public void setGoodsbrand(String goodsbrand) {
		this.goodsbrand = goodsbrand;
	}

	public String getGoodsbrandname() {
		return goodsbrandname;
	}

	public void setGoodsbrandname(String goodsbrandname) {
		this.goodsbrandname = goodsbrandname;
	}

	public BigDecimal getTaxtypeRate() {
		return taxtypeRate;
	}

	public void setTaxtypeRate(BigDecimal taxtypeRate) {
		this.taxtypeRate = taxtypeRate;
	}

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }
}