package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luoq on 2017/10/25.
 */
public class BuySupplierBrandSettletype implements Serializable {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 供应商编号
     */
    private String supplierid;

    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 结算方式
     */
    private String settletype;

    /**
     * 结算方式名称
     */
    private String settletypename;

    /**
     * 每月结算日
     */
    private String settleday;

    /**
     * 备注
     */
    private String remark;
    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 品牌编码集合
     */
    private String brandids;

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
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

    public String getSettletype() {
        return settletype;
    }

    public void setSettletype(String settletype) {
        this.settletype = settletype;
    }

    public String getSettletypename() {
        return settletypename;
    }

    public void setSettletypename(String settletypename) {
        this.settletypename = settletypename;
    }

    public String getSettleday() {
        return settleday;
    }

    public void setSettleday(String settleday) {
        this.settleday = settleday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandids() {
        return brandids;
    }

    public void setBrandids(String brandids) {
        this.brandids = brandids;
    }
}
