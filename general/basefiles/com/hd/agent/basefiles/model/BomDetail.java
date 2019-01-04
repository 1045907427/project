package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品BOM规则明细
 * @author limin
 * @date Sep 21, 2015
 */
public class BomDetail implements Serializable {
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
     * 备注
     */
    private String remark;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 类型 1：主商品；2：换算商品
     */
    private String type;

    /**
     * 每份数量
     */
    private BigDecimal unitnum;

    /**
     * 每份辅单位整数数量
     */
    private BigDecimal auxnum;

    /**
     * 每份辅单位余数数量
     */
    private BigDecimal auxremainder;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 单价
     */
    private BigDecimal price;

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
     * @return 类型 1：主商品；2：换算商品
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            类型 1：主商品；2：换算商品
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 每份数量
     */
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    /**
     * @param unitnum
     *            每份数量
     */
    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    /**
     * @return 每份辅单位整数数量
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum
     *            每份辅单位整数数量
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 每份辅单位余数数量
     */
    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    /**
     * @param auxremainder
     *            每份辅单位余数数量
     */
    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}