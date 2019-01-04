package com.hd.agent.sales.model;

import java.io.Serializable;

/**
 * Created by LINXX on 2015/10/28.
 */
public class ModelOrder implements Serializable {

    /**
     * 客户编号
     */
    private String busid;
    /**
     * 总店编号
     */
    private String mainbusid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 客户地址
     */
    private String customerAddress;
    /**
     * 客户简称
     */
    private String shortname;
    /**
     * 客户合同商品店内码
     */
    private String shopid;
    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 数量
     */
    private String unitnum;
    /**
     * 箱数
     */
    private String boxnum;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 单位
     */
    private String unitname;

    /**
     * 助记符
     * @return
     */
    private String spell ;
    /**
     * 商品单价
     * @return
     */
    private String taxprice ;
    /**
     * 业务日期或订单其它信息
     */
    private String otherMsg;
    /**
     * 备注
     * @return
     */
    private String remark;

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(String boxnum) {
        this.boxnum = boxnum;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(String unitnum) {
        this.unitnum = unitnum;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getMainbusid() {
        return mainbusid;
    }

    public void setMainbusid(String mainbusid) {
        this.mainbusid = mainbusid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getTaxprice() {
        return taxprice;
    }

    public void setTaxprice(String taxprice) {
        this.taxprice = taxprice;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getOtherMsg() {
        return otherMsg;
    }

    public void setOtherMsg(String otherMsg) {
        this.otherMsg = otherMsg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
