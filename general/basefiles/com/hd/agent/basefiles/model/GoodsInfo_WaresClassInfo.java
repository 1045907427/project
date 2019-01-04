package com.hd.agent.basefiles.model;

import java.io.Serializable;

public class GoodsInfo_WaresClassInfo implements Serializable {
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
     * 商品分类（来源商品分类档案）
     */
    private String waresclass;
    
    private String waresclassName;

    /**
     * 是否默认分类1是0否
     */
    private String isdefault;

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
     * @return 商品分类（来源商品分类档案）
     */
    public String getWaresclass() {
        return waresclass;
    }

    /**
     * @param waresclass 
	 *            商品分类（来源商品分类档案）
     */
    public void setWaresclass(String waresclass) {
        this.waresclass = waresclass == null ? null : waresclass.trim();
    }

    /**
     * @return 是否默认分类1是0否
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault 
	 *            是否默认分类1是0否
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

	public String getWaresclassName() {
		return waresclassName;
	}

	public void setWaresclassName(String waresclassName) {
		this.waresclassName = waresclassName;
	}
    
}