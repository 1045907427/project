package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存入库明细
 * Created by chenwei on 2017-03-20.
 */
public class InventoryEnterDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private String businessdate;
    private String billid;
    private String detailid;
    private String storageid;
    private String goodsid;
    private BigDecimal unitnum;
    private BigDecimal inum;
    private BigDecimal age;
    private Date bustime;
    private Date addtime;

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getDetailid() {
        return detailid;
    }

    public void setDetailid(String detailid) {
        this.detailid = detailid;
    }

    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public BigDecimal getInum() {
        return inum;
    }

    public void setInum(BigDecimal inum) {
        this.inum = inum;
    }

    public Date getBustime() {
        return bustime;
    }

    public void setBustime(Date bustime) {
        this.bustime = bustime;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }
}
