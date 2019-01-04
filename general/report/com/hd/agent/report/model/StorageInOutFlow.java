package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 仓库出入库
 * @author chenwei
 */
public class StorageInOutFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 流水类型:0：采购入库 1销售退货入库 2其他入库 3:调拨入库 4：调账入库
     * 5：销售出库 6：采购退货出库 7：其他出库 8：调账出库 9：调拨出库
     */
    private String billtype;
    
    /**
     * 单据名称
     */
    private String billtypename;
    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 仓库编号
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 商品编号
     */
    private String goodsid;
    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 箱装量
     */
    private BigDecimal boxnum;
    /**
     * 条形码
     */
    private String barcode;

    /**
     * 助记符
     */
    private String spell;
    
    /**
     * 客户编码
     */
    private String customerid;
    
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 客户分类
     */
    private String customersort;

    /**
     * 客户分类名称
     */
    private String customersortname;
    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 主单位编号
     */
    private String unitid;

    /**
     * 主单位名称
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
     * 入库数量
     */
    private BigDecimal enternum;

    /**
     * 入库金额
     */
    private BigDecimal enteramount;
    /**
     * 辅单位入库整数数量
     */
    private BigDecimal auxenternum;
    /**
     * 辅单位入库余数数量
     */
    private BigDecimal auxenterremainder;
    /**
     * 入库数量辅单位描述
     */
    private String auxenternumdetail;
    /**
     * 出库数量
     */
    private BigDecimal outnum;

    /**
     * 出库金额
     */
    private BigDecimal outamount;
    /**
     * 辅单位出库整数数量
     */
    private BigDecimal auxoutnum;
    /**
     * 辅单位出库余数数量
     */
    private BigDecimal auxoutremainder;
    /**
     * 出库数量辅单位描述
     */
    private String auxoutnumdetail;
    
    /**
     * 金额
     */
    private BigDecimal amount;
    
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 对应单据明细备注
     */
    private String remark;

    /**
     * 供应商编码
     */
    private String supplierid;

    /**
     * 供应商名称
     */
    private String suppliername;
    
    private BigDecimal storagePrice;
    private BigDecimal storagePriceAmount;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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
     * 流水类型:0：采购入库 1销售退货入库 2其他入库 3:调拨入库 4：调账入库
     * 5：销售出库 6：采购退货出库 7：其他出库 8：调账出库 9：调拨出库
     * @return
     * @author chenwei 
     * @date Sep 9, 2013
     */
    public String getBilltype() {
		return billtype;
	}
    /**
     * 流水类型:0：采购入库 1销售退货入库 2其他入库 3:调拨入库 4：调账入库
     * 5：销售出库 6：采购退货出库 7：其他出库 8：调账出库 9：调拨出库
     * @param type
     * @author chenwei 
     * @date Sep 9, 2013
     */
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	/**
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
    }

    /**
     * @return 仓库编号
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            仓库编号
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
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
     * @return 主单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname 
	 *            主单位名称
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
     * @return 入库数量
     */
    public BigDecimal getEnternum() {
        return enternum;
    }

    /**
     * @param enternum 
	 *            入库数量
     */
    public void setEnternum(BigDecimal enternum) {
        this.enternum = enternum;
    }

    /**
     * @return 入库金额
     */
    public BigDecimal getEnteramount() {
        return enteramount;
    }

    /**
     * @param enteramount 
	 *            入库金额
     */
    public void setEnteramount(BigDecimal enteramount) {
        this.enteramount = enteramount;
    }

    /**
     * @return 出库数量
     */
    public BigDecimal getOutnum() {
        return outnum;
    }

    /**
     * @param outnum 
	 *            出库数量
     */
    public void setOutnum(BigDecimal outnum) {
        this.outnum = outnum;
    }

    /**
     * @return 出库金额
     */
    public BigDecimal getOutamount() {
        return outamount;
    }

    /**
     * @param outamount 
	 *            出库金额
     */
    public void setOutamount(BigDecimal outamount) {
        this.outamount = outamount;
    }


	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
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

	public String getBilltypename() {
		return billtypename;
	}

	public void setBilltypename(String billtypename) {
		this.billtypename = billtypename;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

	public BigDecimal getStoragePrice() {
		return storagePrice;
	}

	public void setStoragePrice(BigDecimal storagePrice) {
		this.storagePrice = storagePrice;
	}

	public BigDecimal getStoragePriceAmount() {
		return storagePriceAmount;
	}

	public void setStoragePriceAmount(BigDecimal storagePriceAmount) {
		this.storagePriceAmount = storagePriceAmount;
	}

    public BigDecimal getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(BigDecimal boxnum) {
        this.boxnum = boxnum;
    }

    public String getCustomersort() {
        return customersort;
    }

    public void setCustomersort(String customersort) {
        this.customersort = customersort;
    }

    public String getCustomersortname() {
        return customersortname;
    }

    public void setCustomersortname(String customersortname) {
        this.customersortname = customersortname;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }
}