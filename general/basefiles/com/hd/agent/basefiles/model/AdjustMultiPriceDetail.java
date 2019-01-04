package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AdjustMultiPriceDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 商品编码
     */
    private String goodsid;

    private String goodsname;

    private String barcode;

    /**
     * 原始采购价
     */
    private BigDecimal oldbuyprice;

    /**
     * 调整后采购价
     */
    private BigDecimal newbuyprice;

    /**
     * 原始（基准）销售价
     */
    private BigDecimal oldsalesprice;

    /**
     * 调整后（基准）销售价
     */
    private BigDecimal newsalesprice;

    /**
     * 原始一号价
     */
    private BigDecimal oldprice1;

    /**
     * 调整后一号价
     */
    private BigDecimal newprice1;

    /**
     * 原始二号价
     */
    private BigDecimal oldprice2;

    /**
     * 调整后二号价
     */
    private BigDecimal newprice2;

    /**
     * 原始三号价
     */
    private BigDecimal oldprice3;

    /**
     * 调整后三号价
     */
    private BigDecimal newprice3;

    /**
     * 原始四号价
     */
    private BigDecimal oldprice4;

    /**
     * 调整后四号价
     */
    private BigDecimal newprice4;

    /**
     * 原始五号价
     */
    private BigDecimal oldprice5;

    /**
     * 调整后五号价
     */
    private BigDecimal newprice5;

    /**
     * 原始六号价
     */
    private BigDecimal oldprice6;

    /**
     * 调整后六号价
     */
    private BigDecimal newprice6;

    /**
     * 原始七号价
     */
    private BigDecimal oldprice7;

    /**
     * 调整后七号价
     */
    private BigDecimal newprice7;

    /**
     * 原始八号价
     */
    private BigDecimal oldprice8;

    /**
     * 调整后八号价
     */
    private BigDecimal newprice8;

    /**
     * 原始九号价
     */
    private BigDecimal oldprice9;

    /**
     * 调整后九号价
     */
    private BigDecimal newprice9;

    /**
     * 原始十号价
     */
    private BigDecimal oldprice10;

    /**
     * 调整后十号价
     */
    private BigDecimal newprice10;

    /**
     * 涨幅%
     */
    private BigDecimal rate;

    /**
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }

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

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * @return 原始采购价
     */
    public BigDecimal getOldbuyprice() {
        return oldbuyprice;
    }

    /**
     * @param oldbuyprice 
	 *            原始采购价
     */
    public void setOldbuyprice(BigDecimal oldbuyprice) {
        this.oldbuyprice = oldbuyprice;
    }

    /**
     * @return 调整后采购价
     */
    public BigDecimal getNewbuyprice() {
        return newbuyprice;
    }

    /**
     * @param newbuyprice 
	 *            调整后采购价
     */
    public void setNewbuyprice(BigDecimal newbuyprice) {
        this.newbuyprice = newbuyprice;
    }

    /**
     * @return 原始（基准）销售价
     */
    public BigDecimal getOldsalesprice() {
        return oldsalesprice;
    }

    /**
     * @param oldsalesprice 
	 *            原始（基准）销售价
     */
    public void setOldsalesprice(BigDecimal oldsalesprice) {
        this.oldsalesprice = oldsalesprice;
    }

    /**
     * @return 调整后（基准）销售价
     */
    public BigDecimal getNewsalesprice() {
        return newsalesprice;
    }

    /**
     * @param newsalesprice 
	 *            调整后（基准）销售价
     */
    public void setNewsalesprice(BigDecimal newsalesprice) {
        this.newsalesprice = newsalesprice;
    }

    /**
     * @return 原始一号价
     */
    public BigDecimal getOldprice1() {
        return oldprice1;
    }

    /**
     * @param oldprice1 
	 *            原始一号价
     */
    public void setOldprice1(BigDecimal oldprice1) {
        this.oldprice1 = oldprice1;
    }

    /**
     * @return 调整后一号价
     */
    public BigDecimal getNewprice1() {
        return newprice1;
    }

    /**
     * @param newprice1 
	 *            调整后一号价
     */
    public void setNewprice1(BigDecimal newprice1) {
        this.newprice1 = newprice1;
    }

    /**
     * @return 原始二号价
     */
    public BigDecimal getOldprice2() {
        return oldprice2;
    }

    /**
     * @param oldprice2 
	 *            原始二号价
     */
    public void setOldprice2(BigDecimal oldprice2) {
        this.oldprice2 = oldprice2;
    }

    /**
     * @return 调整后二号价
     */
    public BigDecimal getNewprice2() {
        return newprice2;
    }

    /**
     * @param newprice2 
	 *            调整后二号价
     */
    public void setNewprice2(BigDecimal newprice2) {
        this.newprice2 = newprice2;
    }

    /**
     * @return 原始三号价
     */
    public BigDecimal getOldprice3() {
        return oldprice3;
    }

    /**
     * @param oldprice3 
	 *            原始三号价
     */
    public void setOldprice3(BigDecimal oldprice3) {
        this.oldprice3 = oldprice3;
    }

    /**
     * @return 调整后三号价
     */
    public BigDecimal getNewprice3() {
        return newprice3;
    }

    /**
     * @param newprice3 
	 *            调整后三号价
     */
    public void setNewprice3(BigDecimal newprice3) {
        this.newprice3 = newprice3;
    }

    /**
     * @return 原始四号价
     */
    public BigDecimal getOldprice4() {
        return oldprice4;
    }

    /**
     * @param oldprice4 
	 *            原始四号价
     */
    public void setOldprice4(BigDecimal oldprice4) {
        this.oldprice4 = oldprice4;
    }

    /**
     * @return 调整后四号价
     */
    public BigDecimal getNewprice4() {
        return newprice4;
    }

    /**
     * @param newprice4 
	 *            调整后四号价
     */
    public void setNewprice4(BigDecimal newprice4) {
        this.newprice4 = newprice4;
    }

    /**
     * @return 原始五号价
     */
    public BigDecimal getOldprice5() {
        return oldprice5;
    }

    /**
     * @param oldprice5 
	 *            原始五号价
     */
    public void setOldprice5(BigDecimal oldprice5) {
        this.oldprice5 = oldprice5;
    }

    /**
     * @return 调整后五号价
     */
    public BigDecimal getNewprice5() {
        return newprice5;
    }

    /**
     * @param newprice5 
	 *            调整后五号价
     */
    public void setNewprice5(BigDecimal newprice5) {
        this.newprice5 = newprice5;
    }

    /**
     * @return 原始六号价
     */
    public BigDecimal getOldprice6() {
        return oldprice6;
    }

    /**
     * @param oldprice6 
	 *            原始六号价
     */
    public void setOldprice6(BigDecimal oldprice6) {
        this.oldprice6 = oldprice6;
    }

    /**
     * @return 调整后六号价
     */
    public BigDecimal getNewprice6() {
        return newprice6;
    }

    /**
     * @param newprice6 
	 *            调整后六号价
     */
    public void setNewprice6(BigDecimal newprice6) {
        this.newprice6 = newprice6;
    }

    /**
     * @return 原始七号价
     */
    public BigDecimal getOldprice7() {
        return oldprice7;
    }

    /**
     * @param oldprice7 
	 *            原始七号价
     */
    public void setOldprice7(BigDecimal oldprice7) {
        this.oldprice7 = oldprice7;
    }

    /**
     * @return 调整后七号价
     */
    public BigDecimal getNewprice7() {
        return newprice7;
    }

    /**
     * @param newprice7 
	 *            调整后七号价
     */
    public void setNewprice7(BigDecimal newprice7) {
        this.newprice7 = newprice7;
    }

    /**
     * @return 原始八号价
     */
    public BigDecimal getOldprice8() {
        return oldprice8;
    }

    /**
     * @param oldprice8 
	 *            原始八号价
     */
    public void setOldprice8(BigDecimal oldprice8) {
        this.oldprice8 = oldprice8;
    }

    /**
     * @return 调整后八号价
     */
    public BigDecimal getNewprice8() {
        return newprice8;
    }

    /**
     * @param newprice8 
	 *            调整后八号价
     */
    public void setNewprice8(BigDecimal newprice8) {
        this.newprice8 = newprice8;
    }

    /**
     * @return 原始九号价
     */
    public BigDecimal getOldprice9() {
        return oldprice9;
    }

    /**
     * @param oldprice9 
	 *            原始九号价
     */
    public void setOldprice9(BigDecimal oldprice9) {
        this.oldprice9 = oldprice9;
    }

    /**
     * @return 调整后九号价
     */
    public BigDecimal getNewprice9() {
        return newprice9;
    }

    /**
     * @param newprice9 
	 *            调整后九号价
     */
    public void setNewprice9(BigDecimal newprice9) {
        this.newprice9 = newprice9;
    }

    /**
     * @return 原始十号价
     */
    public BigDecimal getOldprice10() {
        return oldprice10;
    }

    /**
     * @param oldprice10 
	 *            原始十号价
     */
    public void setOldprice10(BigDecimal oldprice10) {
        this.oldprice10 = oldprice10;
    }

    /**
     * @return 调整后十号价
     */
    public BigDecimal getNewprice10() {
        return newprice10;
    }

    /**
     * @param newprice10 
	 *            调整后十号价
     */
    public void setNewprice10(BigDecimal newprice10) {
        this.newprice10 = newprice10;
    }

    /**
     * @return 涨幅%
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate 
	 *            涨幅%
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

}