package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;

/**
 * 拆装单明细
 * @author limin
 * @date Oct 22, 2015
 */
public class SplitMergeDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 商品编号
     */
    private String goodsid;
    /**
     * 商品信息
     */
    private GoodsInfo goodsInfo;

    /**
     * 出库/入库仓库
     */
    private String storageid;

    /**
     * 出库/入库日期
     */
    private String storagedate;

    /**
     * 出库/入库状态
     */
    private String storagestatus;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 商品数量
     */
    private BigDecimal unitnum;

    /**
     * 拆分比例
     */
    private BigDecimal rate;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 仓库名称
     */
    private String storagename;

    /**
     * 批次号
     */
    private String batchid;

    /**
     * 批次现存量编号
     */
    private String summarybatchid;

    /**
     * 库位
     */
    private String storagelocationid;

    /**
     * 生产日期
     */
    private String produceddate;

    /**
     * 截止日期
     */
    private String deadline;

    /**
     * 条形码
     */
    private String barcode;

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
     * @return 出库/入库仓库
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid
     *            出库/入库仓库
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 出库/入库日期
     */
    public String getStoragedate() {
        return storagedate;
    }

    /**
     * @param storagedate
     *            出库/入库日期
     */
    public void setStoragedate(String storagedate) {
        this.storagedate = storagedate == null ? null : storagedate.trim();
    }

    /**
     * @return 出库/入库状态
     */
    public String getStoragestatus() {
        return storagestatus;
    }

    /**
     * @param storagestatus
     *            出库/入库状态
     */
    public void setStoragestatus(String storagestatus) {
        this.storagestatus = storagestatus == null ? null : storagestatus.trim();
    }

    /**
     * @return 单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     *            单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return 商品数量
     */
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    /**
     * @param unitnum
     *            商品数量
     */
    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    /**
     * @return 拆分比例
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate
     *            拆分比例
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getSummarybatchid() {
        return summarybatchid;
    }

    public void setSummarybatchid(String summarybatchid) {
        this.summarybatchid = summarybatchid;
    }

    public String getStoragelocationid() {
        return storagelocationid;
    }

    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
}