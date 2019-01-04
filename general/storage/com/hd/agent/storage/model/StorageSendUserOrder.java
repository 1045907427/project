package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StorageSendUserOrder implements Serializable {
    /**
     * 编码
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private Integer id;

    /**
     * 单据类型(1:发货核对，2:直接上车，3:卸货)
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    private String billtype;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 发货人编号
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    private String senduserid;

    /**
     * 发货时间
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    private Date sendtime;

    /**
     * 仓库编号
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    private String storageid;

    /**
     * 金额
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    private BigDecimal totalamount;

    /**
     * 体积
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    private BigDecimal totalvolume;

    /**
     * 重量
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    private BigDecimal totalweight;

    /**
     * 总箱数
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    private BigDecimal totalbox;

    private BigDecimal num;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 单据类型(1:发货核对，2:直接上车，3:卸货)
     * @return  billtype  单据类型(1:发货核对，2:直接上车，3:卸货)
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * 单据类型(1:发货核对，2:直接上车，3:卸货)
     * @param  billtype  单据类型(1:发货核对，2:直接上车，3:卸货)
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * 发货人编号
     * @return  senduserid  发货人编号
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public String getSenduserid() {
        return senduserid;
    }

    /**
     * 发货人编号
     * @param  senduserid  发货人编号
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public void setSenduserid(String senduserid) {
        this.senduserid = senduserid == null ? null : senduserid.trim();
    }

    /**
     * 发货时间
     * @return  sendtime  发货时间
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * 发货时间
     * @param  sendtime  发货时间
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    /**
     * 仓库编号
     * @return  storageid  仓库编号
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * 仓库编号
     * @param  storageid  仓库编号
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * 金额
     * @return  totalamount  金额
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public BigDecimal getTotalamount() {
        return totalamount;
    }

    /**
     * 金额
     * @param  totalamount  金额
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    /**
     * 体积
     * @return  totalvolume  体积
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public BigDecimal getTotalvolume() {
        return totalvolume;
    }

    /**
     * 体积
     * @param  totalvolume  体积
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public void setTotalvolume(BigDecimal totalvolume) {
        this.totalvolume = totalvolume;
    }

    /**
     * 重量
     * @return  totalweight  重量
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public BigDecimal getTotalweight() {
        return totalweight;
    }

    /**
     * 重量
     * @param  totalweight  重量
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public void setTotalweight(BigDecimal totalweight) {
        this.totalweight = totalweight;
    }

    /**
     * 总箱数
     * @return  totalbox  总箱数
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public BigDecimal getTotalbox() {
        return totalbox;
    }

    /**
     * 总箱数
     * @param  totalbox  总箱数
     *
     * @mbggenerated Wed Oct 26 10:53:23 CST 2016
     */
    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
    }


    private String storagename;

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    private  String sendusername;

    public String getSendusername() {
        return sendusername;
    }

    public void setSendusername(String sendusername) {
        this.sendusername = sendusername;
    }

    private String  sourceid;

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    private String checkusername;

    private String loadedusername;

    private String unloadedusername;

    public String getCheckusername() {
        return checkusername;
    }

    public void setCheckusername(String checkusername) {
        this.checkusername = checkusername;
    }

    public String getLoadedusername() {
        return loadedusername;
    }

    public void setLoadedusername(String loadedusername) {
        this.loadedusername = loadedusername;
    }

    public String getUnloadedusername() {
        return unloadedusername;
    }

    public void setUnloadedusername(String unloadedusername) {
        this.unloadedusername = unloadedusername;
    }




    /**
     * 核对数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal checknum;

    /**
     * 核对金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal checkamount;

    /**
     * 核对体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal checkvolume;

    /**
     * 核对重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal checkweight;

    /**
     * 核对箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal checkbox;

    /**
     * 装车数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal loadednum;

    /**
     * 装车金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal loadedamount;

    /**
     * 装车体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal loadedvolume;

    /**
     * 装车重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal loadedweight;

    /**
     * 装车箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal loadedbox;

    /**
     * 卸货数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal unloadednum;

    /**
     * 卸货金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal unloadedamount;

    /**
     * 卸货体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal unloadedvolume;

    /**
     * 卸货重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal unloadedweight;

    /**
     * 卸货箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    private BigDecimal unloadedbox;

    /**
     * 核对数量
     * @return  checknum  核对数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getChecknum() {
        return checknum;
    }

    /**
     * 核对数量
     * @param  checknum  核对数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setChecknum(BigDecimal checknum) {
        this.checknum = checknum;
    }

    /**
     * 核对金额
     * @return  checkamount  核对金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getCheckamount() {
        return checkamount;
    }

    /**
     * 核对金额
     * @param  checkamount  核对金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setCheckamount(BigDecimal checkamount) {
        this.checkamount = checkamount;
    }

    /**
     * 核对体积
     * @return  checkvolume  核对体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getCheckvolume() {
        return checkvolume;
    }

    /**
     * 核对体积
     * @param  checkvolume  核对体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setCheckvolume(BigDecimal checkvolume) {
        this.checkvolume = checkvolume;
    }

    /**
     * 核对重量
     * @return  checkweight  核对重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getCheckweight() {
        return checkweight;
    }

    /**
     * 核对重量
     * @param  checkweight  核对重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setCheckweight(BigDecimal checkweight) {
        this.checkweight = checkweight;
    }

    /**
     * 核对箱数
     * @return  checkbox  核对箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getCheckbox() {
        return checkbox;
    }

    /**
     * 核对箱数
     * @param  checkbox  核对箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setCheckbox(BigDecimal checkbox) {
        this.checkbox = checkbox;
    }

    /**
     * 装车数量
     * @return  loadednum  装车数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getLoadednum() {
        return loadednum;
    }

    /**
     * 装车数量
     * @param  loadednum  装车数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setLoadednum(BigDecimal loadednum) {
        this.loadednum = loadednum;
    }

    /**
     * 装车金额
     * @return  loadedamount  装车金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getLoadedamount() {
        return loadedamount;
    }

    /**
     * 装车金额
     * @param  loadedamount  装车金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setLoadedamount(BigDecimal loadedamount) {
        this.loadedamount = loadedamount;
    }

    /**
     * 装车体积
     * @return  loadedvolume  装车体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getLoadedvolume() {
        return loadedvolume;
    }

    /**
     * 装车体积
     * @param  loadedvolume  装车体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setLoadedvolume(BigDecimal loadedvolume) {
        this.loadedvolume = loadedvolume;
    }

    /**
     * 装车重量
     * @return  loadedweight  装车重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getLoadedweight() {
        return loadedweight;
    }

    /**
     * 装车重量
     * @param  loadedweight  装车重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setLoadedweight(BigDecimal loadedweight) {
        this.loadedweight = loadedweight;
    }

    /**
     * 装车箱数
     * @return  loadedbox  装车箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getLoadedbox() {
        return loadedbox;
    }

    /**
     * 装车箱数
     * @param  loadedbox  装车箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setLoadedbox(BigDecimal loadedbox) {
        this.loadedbox = loadedbox;
    }

    /**
     * 卸货数量
     * @return  unloadednum  卸货数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getUnloadednum() {
        return unloadednum;
    }

    /**
     * 卸货数量
     * @param  unloadednum  卸货数量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setUnloadednum(BigDecimal unloadednum) {
        this.unloadednum = unloadednum;
    }

    /**
     * 卸货金额
     * @return  unloadedamount  卸货金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getUnloadedamount() {
        return unloadedamount;
    }

    /**
     * 卸货金额
     * @param  unloadedamount  卸货金额
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setUnloadedamount(BigDecimal unloadedamount) {
        this.unloadedamount = unloadedamount;
    }

    /**
     * 卸货体积
     * @return  unloadedvolume  卸货体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getUnloadedvolume() {
        return unloadedvolume;
    }

    /**
     * 卸货体积
     * @param  unloadedvolume  卸货体积
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setUnloadedvolume(BigDecimal unloadedvolume) {
        this.unloadedvolume = unloadedvolume;
    }

    /**
     * 卸货重量
     * @return  unloadedweight  卸货重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getUnloadedweight() {
        return unloadedweight;
    }

    /**
     * 卸货重量
     * @param  unloadedweight  卸货重量
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setUnloadedweight(BigDecimal unloadedweight) {
        this.unloadedweight = unloadedweight;
    }

    /**
     * 卸货箱数
     * @return  unloadedbox  卸货箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public BigDecimal getUnloadedbox() {
        return unloadedbox;
    }

    /**
     * 卸货箱数
     * @param  unloadedbox  卸货箱数
     *
     * @mbggenerated Wed Oct 26 13:50:26 CST 2016
     */
    public void setUnloadedbox(BigDecimal unloadedbox) {
        this.unloadedbox = unloadedbox;
    }


    private String saleorderid;

    public String getSaleorderid() {
        return saleorderid;
    }

    public void setSaleorderid(String saleorderid) {
        this.saleorderid = saleorderid;
    }
}