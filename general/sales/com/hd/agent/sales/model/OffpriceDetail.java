package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.GoodsInfo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OffpriceDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 调价数量下限
     */
    private BigDecimal lownum;

    /**
     * 调价数量上限
     */
    private BigDecimal upnum;

    /**
     * 特价
     */
    private BigDecimal offprice;

    private BigDecimal oldprice;

    /**
     * 自定义信息1
     */
    private String field01;

    /**
     * 自定义信息2
     */
    private String field02;

    /**
     * 自定义信息3
     */
    private String field03;

    /**
     * 自定义信息4
     */
    private String field04;

    /**
     * 自定义信息5
     */
    private String field05;

    /**
     * 自定义信息6
     */
    private String field06;

    /**
     * 自定义信息7
     */
    private String field07;

    /**
     * 自定义信息8
     */
    private String field08;

    private GoodsInfo goodsInfo;

    /**
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            编号
     */
    public void setId(String id) {
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
        this.billid = billid;
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
        this.goodsid = goodsid;
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
        this.remark = remark;
    }

    /**
     *
     * @return 调价数量下限
     * @author zhengziyong
     * @date Nov 25, 2013
     */
    public BigDecimal getLownum() {
        return lownum;
    }

    /**
     *
     * @param lownum 调价数量下限
     * @author zhengziyong
     * @date Nov 25, 2013
     */
    public void setLownum(BigDecimal lownum) {
        this.lownum = lownum;
    }

    /**
     *
     * @return 调价数量上限
     * @author zhengziyong
     * @date Nov 25, 2013
     */
    public BigDecimal getUpnum() {
        return upnum;
    }

    /**
     *
     * @param upnum 调价数量上限
     * @author zhengziyong
     * @date Nov 25, 2013
     */
    public void setUpnum(BigDecimal upnum) {
        this.upnum = upnum;
    }

    /**
     * @return 特价
     */
    public BigDecimal getOffprice() {
        return offprice;
    }

    /**
     * @param offprice
     *            特价
     */
    public void setOffprice(BigDecimal offprice) {
        this.offprice = offprice;
    }

    public BigDecimal getOldprice() {
        return oldprice;
    }

    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    /**
     * @return 自定义信息1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01
     *            自定义信息1
     */
    public void setField01(String field01) {
        this.field01 = field01;
    }

    /**
     * @return 自定义信息2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02
     *            自定义信息2
     */
    public void setField02(String field02) {
        this.field02 = field02;
    }

    /**
     * @return 自定义信息3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03
     *            自定义信息3
     */
    public void setField03(String field03) {
        this.field03 = field03;
    }

    /**
     * @return 自定义信息4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04
     *            自定义信息4
     */
    public void setField04(String field04) {
        this.field04 = field04;
    }

    /**
     * @return 自定义信息5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05
     *            自定义信息5
     */
    public void setField05(String field05) {
        this.field05 = field05;
    }

    /**
     * @return 自定义信息6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06
     *            自定义信息6
     */
    public void setField06(String field06) {
        this.field06 = field06;
    }

    /**
     * @return 自定义信息7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07
     *            自定义信息7
     */
    public void setField07(String field07) {
        this.field07 = field07;
    }

    /**
     * @return 自定义信息8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08
     *            自定义信息8
     */
    public void setField08(String field08) {
        this.field08 = field08;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}