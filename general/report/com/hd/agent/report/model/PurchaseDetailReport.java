package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 销售情况统计明细
 * @author chenwei
 */
public class PurchaseDetailReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String treeid;
    private String state;
    private String parentid;
    
    /**
     * 编号
     */
    private Integer id;

    /**
     * 日期
     */
    private String businessdate;

    /**
     * 供应商编号
     */
    private String supplierid;
    
    /**
     * 供应商名称
     */
    private String suppliername;
    
    /**
     * 采购部门编号
     */
    private String buydeptid;
    
    /**
     * 采购部门名称
     */
    private String buydeptname;
    
    /**
     * 采购人员编号
     */
    private String buyuserid;
    
    /**
     * 采购员
     */
    private String buyusername;
    
    /**
     * 商品编码
     */
    private String goodsid;
    
    /**
     * 商品名称
     */
    private String goodsname;
    
    /**
     * 条形码
     */
    private String barcode;

    /**
     * 品牌编号
     */
    private String brandid;
    
    /**
     * 商品品牌
     */
    private String brandname;

    /**
     * 税种编号
     */
    private String taxtype;

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
     * 进货数量(主单位数量)
     */
    private BigDecimal enternum;
    
    /**
     * 进货总数量描述
     */
    private String enternumdetail;
    
    /**
     * 进货辅单位整数数量
     */
    private BigDecimal auxenternum;
    /**
     * 进货辅单位余数数量
     */
    private BigDecimal auxenterremainder;
    /**
     * 辅单位数量
     */
    private String auxenternumdetail;

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
     * 进货箱数
     */
    private BigDecimal entertotalbox;

    /**
     * 退货数量
     */
    private BigDecimal outnum;
    /**
     * 退货辅单位整数数量
     */
    private BigDecimal auxoutnum;
    /**
     * 退货辅单位余数数量
     */
    private BigDecimal auxoutremainder;
    /**
     * 退货辅数量
     */
    private String auxoutnumdetail;
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
     * 退货箱数
     */
    private BigDecimal outtotalbox;
    
    /**
     * 验收退货率
     */
    private BigDecimal checkoutrate;

    /**
     * 合计金额（进货金额-退货金额）
     */
    private BigDecimal totalamount;

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
     * @return 日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
    }

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
     * @return 品牌编号
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌编号
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 税种编号
     */
    public String getTaxtype() {
        return taxtype;
    }

    /**
     * @param taxtype 
	 *            税种编号
     */
    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype == null ? null : taxtype.trim();
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

	public String getBuydeptid() {
		return buydeptid;
	}

	public void setBuydeptid(String buydeptid) {
		this.buydeptid = buydeptid;
	}

	public String getBuyuserid() {
		return buyuserid;
	}

	public void setBuyuserid(String buyuserid) {
		this.buyuserid = buyuserid;
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

	public String getBuydeptname() {
		return buydeptname;
	}

	public void setBuydeptname(String buydeptname) {
		this.buydeptname = buydeptname;
	}

	public String getBuyusername() {
		return buyusername;
	}

	public void setBuyusername(String buyusername) {
		this.buyusername = buyusername;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
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

	public String getAuxenternumdetail() {
		return auxenternumdetail;
	}

	public void setAuxenternumdetail(String auxenternumdetail) {
		this.auxenternumdetail = auxenternumdetail;
	}

	public String getAuxoutnumdetail() {
		return auxoutnumdetail;
	}

	public void setAuxoutnumdetail(String auxoutnumdetail) {
		this.auxoutnumdetail = auxoutnumdetail;
	}

	public BigDecimal getAuxenternum() {
		return auxenternum;
	}

	public void setAuxenternum(BigDecimal auxenternum) {
		this.auxenternum = auxenternum;
	}

	public BigDecimal getAuxenterremainder() {
		return auxenterremainder;
	}

	public void setAuxenterremainder(BigDecimal auxenterremainder) {
		this.auxenterremainder = auxenterremainder;
	}

	public BigDecimal getAuxoutnum() {
		return auxoutnum;
	}

	public void setAuxoutnum(BigDecimal auxoutnum) {
		this.auxoutnum = auxoutnum;
	}

	public BigDecimal getAuxoutremainder() {
		return auxoutremainder;
	}

	public void setAuxoutremainder(BigDecimal auxoutremainder) {
		this.auxoutremainder = auxoutremainder;
	}

	public String getEnternumdetail() {
		return enternumdetail;
	}

	public void setEnternumdetail(String enternumdetail) {
		this.enternumdetail = enternumdetail;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

    public BigDecimal getOuttotalbox() {
        return outtotalbox;
    }

    public void setOuttotalbox(BigDecimal outtotalbox) {
        this.outtotalbox = outtotalbox;
    }

    public BigDecimal getEntertotalbox() {
        return entertotalbox;
    }

    public void setEntertotalbox(BigDecimal entertotalbox) {
        this.entertotalbox = entertotalbox;
    }
}