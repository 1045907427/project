package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 仓库出入库
 * @author chenwei
 */
public class LendInOutReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

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
     * 条形码
     */
    private String barcode;
    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 商品分类
     */
    private String goodssort;
    /**
     * 商品分类名称
     */
    private String goodssortname;
    /**
     * 箱装量
     */
    private BigDecimal boxnum;
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
     * 初始数量
     */
    private BigDecimal initnum;
    /**
     * 期初箱数
     */
    private BigDecimal initauxnum;
    /**
     * 初始金额
     */
    private BigDecimal initamount;
    
    /**
     * 初始辅单位描述
     */
    private String initauxnumdetail;
    /**
     * 入库数量
     */
    private BigDecimal enternum;
    /**
     * 入库箱数
     */
    private BigDecimal enterauxnum;
    /**
     * 入库金额
     */
    private BigDecimal enteramount;
    /**
     * 入库数量辅单位描述
     */
    private String enterauxnumdetail;
    /**
     * 出库数量
     */
    private BigDecimal outnum;
    /**
     * 出库箱数
     */
    private BigDecimal outauxnum;
    /**
     * 出库金额
     */
    private BigDecimal outamount;
    /**
     * 出库数量辅单位描述
     */
    private String outauxnumdetail;
    /**
     * 期末结存数量
     */
    private BigDecimal endnum;
    /**
     * 期末箱数
     */
    private BigDecimal endauxnum;
    /**
     * 期末结存金额
     */
    private BigDecimal endamount;
    /**
     * 期末数量辅单位描述
     */
    private String endauxnumdetail;
    /**
     * 添加时间
     */
    private Date addtime;

    private String lendid;

    private String lendname;

    private String deptid;

    private String deptname;

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getLendid() {
        return lendid;
    }

    public void setLendid(String lendid) {
        this.lendid = lendid;
    }

    public String getLendname() {
        return lendname;
    }

    public void setLendname(String lendname) {
        this.lendname = lendname;
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
     * @return 初始数量
     */
    public BigDecimal getInitnum() {
        return initnum;
    }

    /**
     * @param initnum 
	 *            初始数量
     */
    public void setInitnum(BigDecimal initnum) {
        this.initnum = initnum;
    }

    /**
     * @return 初始金额
     */
    public BigDecimal getInitamount() {
        return initamount;
    }

    /**
     * @param initamount 
	 *            初始金额
     */
    public void setInitamount(BigDecimal initamount) {
        this.initamount = initamount;
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

    /**
     * @return 期末结存数量
     */
    public BigDecimal getEndnum() {
        return endnum;
    }

    /**
     * @param endnum 
	 *            期末结存数量
     */
    public void setEndnum(BigDecimal endnum) {
        this.endnum = endnum;
    }

    /**
     * @return 期末结存金额
     */
    public BigDecimal getEndamount() {
        return endamount;
    }

    /**
     * @param endamount 
	 *            期末结存金额
     */
    public void setEndamount(BigDecimal endamount) {
        this.endamount = endamount;
    }

    /**
     * @return 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
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


	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getGoodssort() {
		return goodssort;
	}

	public String getGoodssortname() {
		return goodssortname;
	}

	public BigDecimal getInitauxnum() {
		return initauxnum;
	}

	public String getInitauxnumdetail() {
		return initauxnumdetail;
	}

	public BigDecimal getEnterauxnum() {
		return enterauxnum;
	}

	public String getEnterauxnumdetail() {
		return enterauxnumdetail;
	}

	public BigDecimal getOutauxnum() {
		return outauxnum;
	}

	public String getOutauxnumdetail() {
		return outauxnumdetail;
	}

	public String getEndauxnumdetail() {
		return endauxnumdetail;
	}

	public void setGoodssort(String goodssort) {
		this.goodssort = goodssort;
	}

	public void setGoodssortname(String goodssortname) {
		this.goodssortname = goodssortname;
	}

	public void setInitauxnum(BigDecimal initauxnum) {
		this.initauxnum = initauxnum;
	}

	public void setInitauxnumdetail(String initauxnumdetail) {
		this.initauxnumdetail = initauxnumdetail;
	}

	public void setEnterauxnum(BigDecimal enterauxnum) {
		this.enterauxnum = enterauxnum;
	}

	public void setEnterauxnumdetail(String enterauxnumdetail) {
		this.enterauxnumdetail = enterauxnumdetail;
	}

	public void setOutauxnum(BigDecimal outauxnum) {
		this.outauxnum = outauxnum;
	}

	public void setOutauxnumdetail(String outauxnumdetail) {
		this.outauxnumdetail = outauxnumdetail;
	}

	public void setEndauxnumdetail(String endauxnumdetail) {
		this.endauxnumdetail = endauxnumdetail;
	}

	public BigDecimal getEndauxnum() {
		return endauxnum;
	}

	public void setEndauxnum(BigDecimal endauxnum) {
		this.endauxnum = endauxnum;
	}

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}
	
}