package com.hd.agent.delivery.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.storage.model.StorageSummary;

public class DeliveryAogreturnDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 单据编号
     */
    private String billid;

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
     * 序号
     */
    private Integer seq;
    
    private String remark;
    /**
     * 商品信息
     */
    private GoodsInfo goodsInfo;
    /**
     * 可用量
     */
    public BigDecimal usablenum;
    private StorageSummary storageSummary;
    
    private String batchno;
    private String produceddate;
    private String deadline;
    
    public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

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

	public StorageSummary getStorageSummary() {
		return storageSummary;
	}

	public void setStorageSummary(StorageSummary storageSummary) {
		this.storageSummary = storageSummary;
	}

	public BigDecimal getUsablenum() {
		return usablenum;
	}

	public void setUsablenum(BigDecimal usablenum) {
		this.usablenum = usablenum;
	}

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
        this.id = id == null ? null : id.trim();
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
    
    /**
     * @return 商品信息
     */
	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}
	/**
     * @param goodsInfo 
	 *            商品信息
     */
	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}