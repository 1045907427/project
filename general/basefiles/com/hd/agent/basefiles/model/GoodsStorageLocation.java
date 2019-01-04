package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsStorageLocation implements Serializable {
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
     * 库位编码
     */
    private String storagelocationid;
    
    /**
     * 库位名称
     */
    private String storagelocationName;

    /**
     * 是否默认库位1是0否
     */
    private String isdefault;
    
    /**
     * 库位容量（能容纳该商品多少箱）
     */
    private BigDecimal boxnum;

    /**
     * 备注
     */
    private String remark;

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
     * @return 库位编码
     */
    public String getStoragelocationid() {
        return storagelocationid;
    }

    /**
     * @param storagelocationid 
	 *            库位编码
     */
    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid == null ? null : storagelocationid.trim();
    }

    /**
     * @return 是否默认库位1是0否
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault 
	 *            是否默认库位1是0否
     */
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault == null ? null : isdefault.trim();
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

	public String getStoragelocationName() {
		return storagelocationName;
	}

	public void setStoragelocationName(String storagelocationName) {
		this.storagelocationName = storagelocationName;
	}

	public BigDecimal getBoxnum() {
        return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}
    
    
}