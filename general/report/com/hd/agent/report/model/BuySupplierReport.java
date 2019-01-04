/**
 * @(#)BuySupplierReport.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 30, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.math.BigDecimal;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class BuySupplierReport {
	private static final long serialVersionUID = 1L;
	
	private String treeid;
    private String state;
    private String parentid;
    
    /**
     * 供应商编号
     */
    private String supplierid;
    
    /**
     * 供应商编码
     */
    private String suppliername;
    
    /**
     * 商品编码
     */
    private String goodsid;
    
    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 主单位编号
     */
    private String unitid;

    /**
     * 单位名称
     */
    private String unitname;

    /**
     * 辅单位编号
     */
    private String auxunitid;

    /**
     * 辅单位名称
     */
    private String auxunitname;

    /**
     * 进货数量
     */
    private BigDecimal enternum;

    /**
     * 进货金额
     */
    private BigDecimal entertaxamount;

    /**
     * 进货未税金额
     */
    private BigDecimal enternotaxamount;

    /**
     * 进货税额
     */
    private BigDecimal entertax;

    /**
     * 退货数量
     */
    private BigDecimal outnum;

    /**
     * 退货金额
     */
    private BigDecimal outtaxamount;

    /**
     * 退货无税金额
     */
    private BigDecimal outnotaxamount;

    /**
     * 退货税额
     */
    private BigDecimal outtax;
    
    /**
     * 验收退货率
     */
    private BigDecimal checkoutrate;

    /**
     * 合计金额（进货金额-退货金额）
     */
    private BigDecimal totalamount;

    /**
     * @return 供应商编号
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编号
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
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

    /**
     * @return 主单位编号
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid 
	 *            主单位编号
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname 
	 *            单位名称
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    /**
     * @return 辅单位编号
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid 
	 *            辅单位编号
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid == null ? null : auxunitid.trim();
    }

    /**
     * @return 辅单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname 
	 *            辅单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname == null ? null : auxunitname.trim();
    }

    /**
     * @return 进货数量
     */
    public BigDecimal getEnternum() {
        return enternum;
    }

    /**
     * @param enternum 
	 *            进货数量
     */
    public void setEnternum(BigDecimal enternum) {
        this.enternum = enternum;
    }

    /**
     * @return 进货金额
     */
    public BigDecimal getEntertaxamount() {
        return entertaxamount;
    }

    /**
     * @param entertaxamount 
	 *            进货金额
     */
    public void setEntertaxamount(BigDecimal entertaxamount) {
        this.entertaxamount = entertaxamount;
    }

    /**
     * @return 进货未税金额
     */
    public BigDecimal getEnternotaxamount() {
        return enternotaxamount;
    }

    /**
     * @param enternotaxamount 
	 *            进货未税金额
     */
    public void setEnternotaxamount(BigDecimal enternotaxamount) {
        this.enternotaxamount = enternotaxamount;
    }

    /**
     * @return 进货税额
     */
    public BigDecimal getEntertax() {
        return entertax;
    }

    /**
     * @param entertax 
	 *            进货税额
     */
    public void setEntertax(BigDecimal entertax) {
        this.entertax = entertax;
    }

    /**
     * @return 退货数量
     */
    public BigDecimal getOutnum() {
        return outnum;
    }

    /**
     * @param outnum 
	 *            退货数量
     */
    public void setOutnum(BigDecimal outnum) {
        this.outnum = outnum;
    }

    /**
     * @return 退货金额
     */
    public BigDecimal getOuttaxamount() {
        return outtaxamount;
    }

    /**
     * @param outtaxamount 
	 *            退货金额
     */
    public void setOuttaxamount(BigDecimal outtaxamount) {
        this.outtaxamount = outtaxamount;
    }

    /**
     * @return 退货无税金额
     */
    public BigDecimal getOutnotaxamount() {
        return outnotaxamount;
    }

    /**
     * @param outnotaxamount 
	 *            退货无税金额
     */
    public void setOutnotaxamount(BigDecimal outnotaxamount) {
        this.outnotaxamount = outnotaxamount;
    }

    /**
     * @return 退货税额
     */
    public BigDecimal getOuttax() {
        return outtax;
    }

    /**
     * @param outtax 
	 *            退货税额
     */
    public void setOuttax(BigDecimal outtax) {
        this.outtax = outtax;
    }

    /**
     * @return 合计金额（进货金额-退货金额）
     */
    public BigDecimal getTotalamount() {
        return totalamount;
    }

    /**
     * @param totalamount 
	 *            合计金额（进货金额-退货金额）
     */
    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

	public BigDecimal getCheckoutrate() {
		return checkoutrate;
	}

	public void setCheckoutrate(BigDecimal checkoutrate) {
		this.checkoutrate = checkoutrate;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getTreeid() {
		return treeid;
	}

	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}

