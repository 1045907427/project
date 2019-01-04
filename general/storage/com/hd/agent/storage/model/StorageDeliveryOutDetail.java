package com.hd.agent.storage.model;

import com.hd.agent.basefiles.model.GoodsInfo;

import java.io.Serializable;
import java.math.BigDecimal;

public class StorageDeliveryOutDetail implements Serializable {
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
     * 库存明细编号
     */
    private String summarybatchid;

    /**
     * 来源单据编号
     */
    private String sourcebillid;

    /**
     * 来源明细编号
     */
    private String sourcebilldetailid;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 商品分类
     */
    private String goodssort;

    /**
     * 品牌编码
     */
    private String brandid;

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
     * 辅单位数量+辅单位+主单位余数+主单位
     */
    private String auxnumdetail;

    /**
     * 主单位余数
     */
    private BigDecimal overnum;

    /**
     * 合计箱数
     */
    private BigDecimal totalbox;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal taxamount;
    /**
     * 出库时成本价
     */
    private BigDecimal addcostprice;
    /**
     * 序号
     */
    private Integer seq;
    
    
    private GoodsInfo goodsInfo;
    
    /**
     * 基准单价
     */
    private BigDecimal basesaleprice;
    
    /**
     * 箱价
     */
    private BigDecimal boxprice;
    /**
     * 体积
     */
	private BigDecimal volumn;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * goodsName
	 */
	private String goodsname;
	/**
     * 打印时的价格,针对供应商取buyprice,针对客户取price
     */
    private BigDecimal buyprice;
    
	private String barcode;
    
	private BigDecimal printprice;
	/**
	* 打印时的总金额
	*/
	private BigDecimal printAllprice;
    
	private String produceddate;
	private String deadline;
	private String batchno;
	
	
	public String getProduceddate() {
		return produceddate;
	}

	public void setProduceddate(String produceddate) {
		this.produceddate = produceddate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getPrintprice() {
		return printprice;
	}

	public void setPrintprice(BigDecimal printprice) {
		this.printprice = printprice;
	}

	public BigDecimal getPrintAllprice() {
		return printAllprice;
	}

	public void setPrintAllprice(BigDecimal printAllprice) {
		this.printAllprice = printAllprice;
	}

	public BigDecimal getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(BigDecimal buyprice) {
		this.buyprice = buyprice;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 总重量
	 */
	private BigDecimal weight;
    
    public BigDecimal getBasesaleprice() {
		return basesaleprice;
	}

	public void setBasesaleprice(BigDecimal basesaleprice) {
		this.basesaleprice = basesaleprice;
	}

	public BigDecimal getBoxprice() {
		return boxprice;
	}

	public void setBoxprice(BigDecimal boxprice) {
		this.boxprice = boxprice;
	}

	public BigDecimal getVolumn() {
		return volumn;
	}

	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

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
     * @return 库存明细编号
     */
    public String getSummarybatchid() {
        return summarybatchid;
    }

    /**
     * @param summarybatchid 
	 *            库存明细编号
     */
    public void setSummarybatchid(String summarybatchid) {
        this.summarybatchid = summarybatchid == null ? null : summarybatchid.trim();
    }

    /**
     * @return 来源单据编号
     */
    public String getSourcebillid() {
        return sourcebillid;
    }

    /**
     * @param sourcebillid 
	 *            来源单据编号
     */
    public void setSourcebillid(String sourcebillid) {
        this.sourcebillid = sourcebillid == null ? null : sourcebillid.trim();
    }

    /**
     * @return 来源明细编号
     */
    public String getSourcebilldetailid() {
        return sourcebilldetailid;
    }

    /**
     * @param sourcebilldetailid 
	 *            来源明细编号
     */
    public void setSourcebilldetailid(String sourcebilldetailid) {
        this.sourcebilldetailid = sourcebilldetailid == null ? null : sourcebilldetailid.trim();
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
     * @return 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return 金额
     */
    public BigDecimal getTaxamount() {
        return taxamount;
    }

    /**
     * @param taxamount 
	 *            金额
     */
    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    /**
     * @return 序号
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            序号
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

	@Override
	public String toString() {
		return "StorageDeliveryOutDetail [id=" + id + ", billid=" + billid + ", summarybatchid=" + summarybatchid + ", sourcebillid=" + sourcebillid + ", sourcebilldetailid=" + sourcebilldetailid
				+ ", goodsid=" + goodsid + ", goodssort=" + goodssort + ", brandid=" + brandid + ", unitid=" + unitid + ", unitname=" + unitname + ", unitnum=" + unitnum + ", auxunitid=" + auxunitid
				+ ", auxunitname=" + auxunitname + ", auxnum=" + auxnum + ", auxnumdetail=" + auxnumdetail + ", overnum=" + overnum + ", totalbox=" + totalbox + ", price=" + price + ", taxamount="
				+ taxamount + ", seq=" + seq + ", goodsInfo=" + goodsInfo + ", basesaleprice=" + basesaleprice + ", boxprice=" + boxprice + ", volumn=" + volumn + ", remark=" + remark
				+ ", goodsname=" + goodsname + ", buyprice=" + buyprice + ", weight=" + weight + "]";
	}
    public BigDecimal getAddcostprice() {
        return addcostprice;
    }

    public void setAddcostprice(BigDecimal addcostprice) {
        this.addcostprice = addcostprice;
    }
}