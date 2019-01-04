package com.hd.agent.storage.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by luoq on 2018/1/26.
 */
public class StorageStockSum {
    /**
     * 编码
     */
    private int id;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 仓库编码
     */
    private String storageid;

    /**
     * 仓库名称
     */
    private String storagename;

    /**
     * 采购价
     */
    private BigDecimal buyprice;

    /**
     * 最高采购价
     */
    private BigDecimal highestrealcostprice;

    /**
     * 最低采购价
     */
    private BigDecimal lowestrealcostprice;

    /**
     * 期末单价
     */
    private BigDecimal costprice;

    /**
     * 未税成本价
     */
    private BigDecimal costnotaxprice;

    /**
     * 最高成本价
     */
    private BigDecimal highestcostprice;

    /**
     * 最低成本价
     */
    private BigDecimal lowestcostprice;

    /**
     * 波动范围
     */
    private BigDecimal waverange;

    /**
     * 期初数量
     */
    private BigDecimal initnum;

    /**
     * 期初含税金额
     */
    private BigDecimal inittaxamount;

    /**
     * 期初未税金额
     */
    private BigDecimal initnotaxamount;

    /**
     * 收入数量
     */
    private BigDecimal innum;

    /**
     * 收入含税金额
     */
    private BigDecimal intaxamount;

    /**
     * 收入未税金额
     */
    private BigDecimal innotaxamount;

    /**
     * 发出数量
     */
    private BigDecimal outnum;

    /**
     * 发出含税金额
     */
    private BigDecimal outtaxamount;

    /**
     * 发出未税金额
     */
    private BigDecimal outnotaxamount;

    /**
     * 期末数量
     */
    private BigDecimal endnum;

    /**
     * 期末含税金额
     */
    private BigDecimal endtaxamount;

    /**
     * 期末未税金额
     * @return
     */
    private BigDecimal endnotaxamount;

    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人姓名
     */
    private String addusername;

    /**
     * 制单人部门编号
     */
    private String adddeptid;

    /**
     * 制单人部门名称
     */
    private String adddeptname;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 结算方式
     * @return
     */
    private String accounttype;

    /**
     * 库存成本波动范围
     * @return
     */
    private String swaverange;

    /**
     * 实际库存期末数量
     * @return
     */
    private BigDecimal realendnum;

    /**
     * 实际库存期末金额
     * @return
     */
    private BigDecimal realendamount;

    /**
     * 当前库存成本价
     * @return
     */
    private BigDecimal nowCostPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    public BigDecimal getHighestcostprice() {
        return highestcostprice;
    }

    public void setHighestcostprice(BigDecimal highestcostprice) {
        this.highestcostprice = highestcostprice;
    }

    public BigDecimal getLowestcostprice() {
        return lowestcostprice;
    }

    public void setLowestcostprice(BigDecimal lowestcostprice) {
        this.lowestcostprice = lowestcostprice;
    }

    public BigDecimal getWaverange() {
        return waverange;
    }

    public void setWaverange(BigDecimal waverange) {
        this.waverange = waverange;
    }

    public BigDecimal getInitnum() {
        return initnum;
    }

    public void setInitnum(BigDecimal initnum) {
        this.initnum = initnum;
    }

    public BigDecimal getInittaxamount() {
        return inittaxamount;
    }

    public void setInittaxamount(BigDecimal inittaxamount) {
        this.inittaxamount = inittaxamount;
    }

    public BigDecimal getInitnotaxamount() {
        return initnotaxamount;
    }

    public void setInitnotaxamount(BigDecimal initnotaxamount) {
        this.initnotaxamount = initnotaxamount;
    }

    public BigDecimal getInnum() {
        return innum;
    }

    public void setInnum(BigDecimal innum) {
        this.innum = innum;
    }

    public BigDecimal getIntaxamount() {
        return intaxamount;
    }

    public void setIntaxamount(BigDecimal intaxamount) {
        this.intaxamount = intaxamount;
    }

    public BigDecimal getInnotaxamount() {
        return innotaxamount;
    }

    public void setInnotaxamount(BigDecimal innotaxamount) {
        this.innotaxamount = innotaxamount;
    }

    public BigDecimal getOutnum() {
        return outnum;
    }

    public void setOutnum(BigDecimal outnum) {
        this.outnum = outnum;
    }

    public BigDecimal getOuttaxamount() {
        return outtaxamount;
    }

    public void setOuttaxamount(BigDecimal outtaxamount) {
        this.outtaxamount = outtaxamount;
    }

    public BigDecimal getOutnotaxamount() {
        return outnotaxamount;
    }

    public void setOutnotaxamount(BigDecimal outnotaxamount) {
        this.outnotaxamount = outnotaxamount;
    }

    public BigDecimal getEndnum() {
        return endnum;
    }

    public void setEndnum(BigDecimal endnum) {
        this.endnum = endnum;
    }

    public BigDecimal getEndtaxamount() {
        return endtaxamount;
    }

    public void setEndtaxamount(BigDecimal endtaxamount) {
        this.endtaxamount = endtaxamount;
    }

    public BigDecimal getEndnotaxamount() {
        return endnotaxamount;
    }

    public void setEndnotaxamount(BigDecimal endnotaxamount) {
        this.endnotaxamount = endnotaxamount;
    }

    public String getAdduserid() {
        return adduserid;
    }

    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid;
    }

    public String getAddusername() {
        return addusername;
    }

    public void setAddusername(String addusername) {
        this.addusername = addusername;
    }

    public String getAdddeptid() {
        return adddeptid;
    }

    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid;
    }

    public String getAdddeptname() {
        return adddeptname;
    }

    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public BigDecimal getCostnotaxprice() {
        return costnotaxprice;
    }

    public void setCostnotaxprice(BigDecimal costnotaxprice) {
        this.costnotaxprice = costnotaxprice;
    }

    public BigDecimal getHighestrealcostprice() {
        return highestrealcostprice;
    }

    public void setHighestrealcostprice(BigDecimal highestrealcostprice) {
        this.highestrealcostprice = highestrealcostprice;
    }

    public BigDecimal getLowestrealcostprice() {
        return lowestrealcostprice;
    }

    public void setLowestrealcostprice(BigDecimal lowestrealcostprice) {
        this.lowestrealcostprice = lowestrealcostprice;
    }

    public String getSwaverange() {
        return swaverange;
    }

    public void setSwaverange(String swaverange) {
        this.swaverange = swaverange;
    }

    public BigDecimal getRealendnum() {
        return realendnum;
    }

    public void setRealendnum(BigDecimal realendnum) {
        this.realendnum = realendnum;
    }

    public BigDecimal getRealendamount() {
        return realendamount;
    }

    public void setRealendamount(BigDecimal realendamount) {
        this.realendamount = realendamount;
    }

    public BigDecimal getNowCostPrice() {
        return nowCostPrice;
    }

    public void setNowCostPrice(BigDecimal nowCostPrice) {
        this.nowCostPrice = nowCostPrice;
    }
}
