package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsInfo_MteringUnitInfo implements Serializable {
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
     * 计量单位（来源计量单位档案）
     */
    private String meteringunitid;
    
    /**
     * 计量单位名称
     */
    private String meteringunitName;

    /**
     * 换算类型1固定2浮动
     */
    private String type;

    /**
     * 换算方式1辅比主2主比辅
     */
    private String mode;

    /**
     * 换算比率
     */
    private BigDecimal rate;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 条形码
     */
    private String barcode;
    
    /**
     * 是否默认计量单位1是0否
     */
    private String isdefault;

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
     * @return 计量单位（来源计量单位档案）
     */
    public String getMeteringunitid() {
        return meteringunitid;
    }

    /**
     * @param meteringunitid 
	 *            计量单位（来源计量单位档案）
     */
    public void setMeteringunitid(String meteringunitid) {
        this.meteringunitid = meteringunitid == null ? null : meteringunitid.trim();
    }

    /**
     * @return 换算类型1固定2浮动
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            换算类型1固定2浮动
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 换算方式1辅比主2主比辅
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode 
	 *            换算方式1辅比主2主比辅
     */
    public void setMode(String mode) {
        this.mode = mode == null ? null : mode.trim();
    }

    /**
     * @return 换算比率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate 
	 *            换算比率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

	public String getMeteringunitName() {
		return meteringunitName;
	}

	public void setMeteringunitName(String meteringunitName) {
		this.meteringunitName = meteringunitName;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
    
}