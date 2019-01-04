package com.hd.agent.sales.model;

import java.math.BigDecimal;

/**
 * Created by luoq on 2018/3/9.
 */
public class OrderGoodsRelation {
    private int id;

    /**
     * 关联类型1订单关联订货单2要货单关联订货单
     */
    private String billtype;

    /**
     * 订单或要货单编号
     */
    private String orderid;

    /**
     * 订单或要货单明细编号
     */
    private String orderdetailid;

    /**
     * 订货单编号
     */
    private String ordergoodsid;

    /**
     * 订货单明细编号
     */
    private String ordergoodsdetailid;

    /**
     * 关联的数量
     */
    private BigDecimal unitnum;

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderdetailid() {
        return orderdetailid;
    }

    public void setOrderdetailid(String orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    public String getOrdergoodsid() {
        return ordergoodsid;
    }

    public void setOrdergoodsid(String ordergoodsid) {
        this.ordergoodsid = ordergoodsid;
    }

    public String getOrdergoodsdetailid() {
        return ordergoodsdetailid;
    }

    public void setOrdergoodsdetailid(String ordergoodsdetailid) {
        this.ordergoodsdetailid = ordergoodsdetailid;
    }

    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }
}
