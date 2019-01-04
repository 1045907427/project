/**
 * @(#)AdjustPriceExport.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月9日 wanghongteng 创建版本
 */
package com.hd.agent.basefiles.model;

import java.math.BigDecimal;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class AdjustPriceExport {
    

    /**
     * 调价单名称
     */
    private String name;
    /**
     * 业务日期
     */
    private String businessdate;
    /**
     * 1商品采购价2商品基准销售价3价格套4合同价
     */
    private String type;
    /**
     * 1商品采购价2商品基准销售价3价格套4合同价
     */
    private String typename;
    /**
     * 调价类型对应编号比如价格套编号，合同价客户编号（采购价与基准销售价客户不填）
     */
    private String busid;
    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 原价(调整前价格)
     */
    private BigDecimal oldprice;

    /**
     * 现价(调整后价格)
     */
    private BigDecimal nowprice;
    /**
     * 涨幅%
     */
    private BigDecimal rate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 现箱价
     */
    private BigDecimal nowboxprice;
    /**
     * 原箱价
     */
    private BigDecimal oldboxprice;
    /**
     * 箱装量
     */
    private BigDecimal boxnum;
    
    
    
	public BigDecimal getNowboxprice() {
		return nowboxprice;
	}
	public void setNowboxprice(BigDecimal nowboxprice) {
		this.nowboxprice = nowboxprice;
	}
	public BigDecimal getOldboxprice() {
		return oldboxprice;
	}
	public void setOldboxprice(BigDecimal oldboxprice) {
		this.oldboxprice = oldboxprice;
	}
	public BigDecimal getBoxnum() {
		return boxnum;
	}
	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusinessdate() {
		return businessdate;
	}
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getBusid() {
		return busid;
	}
	public void setBusid(String busid) {
		this.busid = busid;
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
	public BigDecimal getOldprice() {
		return oldprice;
	}
	public void setOldprice(BigDecimal oldprice) {
		this.oldprice = oldprice;
	}
	public BigDecimal getNowprice() {
		return nowprice;
	}
	public void setNowprice(BigDecimal nowprice) {
		this.nowprice = nowprice;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
}

