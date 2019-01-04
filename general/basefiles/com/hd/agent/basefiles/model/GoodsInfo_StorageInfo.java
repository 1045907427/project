package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsInfo_StorageInfo implements Serializable {
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
     * 仓库编号（来源仓库档案）
     */
    private String storageid;
    
    private String storageName;

    /**
     * 是否默认仓库1是0否
     */
    private String isdefault;

    /**
     * 最高库存
     */
    private BigDecimal highestinventory;

    /**
     * 最低库存
     */
    private BigDecimal lowestinventory;

    /**
     * 安全库存
     */
    private BigDecimal safeinventory;

    /**
     * 盘点方式1定期盘点
     */
    private String checktype;

    /**
     * 盘点周期
     */
    private BigDecimal checkdate;

    /**
     * 盘点周期单位1天2周3月4年
     */
    private String checkunit;

    /**
     * 上次盘点日期
     */
    private String lastcheckdate;

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
     * @return 仓库编号（来源仓库档案）
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            仓库编号（来源仓库档案）
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 是否默认仓库1是0否
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault 
	 *            是否默认仓库1是0否
     */
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault == null ? null : isdefault.trim();
    }

    /**
     * @return 最高库存
     */
    public BigDecimal getHighestinventory() {
        return highestinventory;
    }

    /**
     * @param highestinventory 
	 *            最高库存
     */
    public void setHighestinventory(BigDecimal highestinventory) {
        this.highestinventory = highestinventory;
    }

    /**
     * @return 最低库存
     */
    public BigDecimal getLowestinventory() {
        return lowestinventory;
    }

    /**
     * @param lowestinventory 
	 *            最低库存
     */
    public void setLowestinventory(BigDecimal lowestinventory) {
        this.lowestinventory = lowestinventory;
    }

    /**
     * @return 安全库存
     */
    public BigDecimal getSafeinventory() {
        return safeinventory;
    }

    /**
     * @param safeinventory 
	 *            安全库存
     */
    public void setSafeinventory(BigDecimal safeinventory) {
        this.safeinventory = safeinventory;
    }

    /**
     * @return 盘点方式1定期盘点
     */
    public String getChecktype() {
        return checktype;
    }

    /**
     * @param checktype 
	 *            盘点方式1定期盘点
     */
    public void setChecktype(String checktype) {
        this.checktype = checktype == null ? null : checktype.trim();
    }

    /**
     * @return 盘点周期
     */
    public BigDecimal getCheckdate() {
        return checkdate;
    }

    /**
     * @param checkdate 
	 *            盘点周期
     */
    public void setCheckdate(BigDecimal checkdate) {
        this.checkdate = checkdate;
    }

    /**
     * @return 盘点周期单位1天2周3月4年
     */
    public String getCheckunit() {
        return checkunit;
    }

    /**
     * @param checkunit 
	 *            盘点周期单位1天2周3月4年
     */
    public void setCheckunit(String checkunit) {
        this.checkunit = checkunit == null ? null : checkunit.trim();
    }

    /**
     * @return 上次盘点日期
     */
    public String getLastcheckdate() {
        return lastcheckdate;
    }

    /**
     * @param lastcheckdate 
	 *            上次盘点日期
     */
    public void setLastcheckdate(String lastcheckdate) {
        this.lastcheckdate = lastcheckdate == null ? null : lastcheckdate.trim();
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

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
    
    
}