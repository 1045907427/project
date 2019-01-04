package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 进销存汇总
 * @author chenwei
 */
public class StorageBuySaleReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 是否折扣
     */
    private String isdiscount;

    /**
     * 排序，折扣放最后
     */
    private Integer seq;

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
     * 商品条形码
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
     * 品牌部门编号
     */
    private String deptid;

    /**
     * 品牌部门名称
     */
    private String deptname;
    /**
     * 主单位编号
     */
    private String unitid;

    /**
     * 主单位名称
     */
    private String unitname;
    /**
     * 单价
     */
    private BigDecimal price;
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
     * 期初合计箱数
     */
    private BigDecimal inittotalbox;

    /**
     * 初始金额
     */
    private BigDecimal initamount;
    
    /**
     * 初始未税金额
     */
    private BigDecimal initnotaxamount;
    /**
     * 初始辅单位描述
     */
    private String auxinitnumdetail;
    
    /**
     * 进货数量
     */
    private BigDecimal buyinnum;

    /**
     * 采购进货合计箱数
     */
    private BigDecimal buyintotalbox;
    
    /**
     * 进货金额
     */
    private BigDecimal buyinamount;
    
    /**
     * 进货未税金额
     */
    private BigDecimal buyinnotaxamount;
    
    /**
     * 进货箱数
     */
    private String auxbuyinnumdetail;
    
    /**
     * 退货数量
     */
    private BigDecimal buyoutnum;

    /**
     * 采购退货合计箱数
     */
    private BigDecimal buyouttotalbox;
    
    /**
     * 退货金额
     */
    private BigDecimal buyoutamount;
    
    /**
     * 退货未税金额
     */
    private BigDecimal buyoutnotaxamount;
    
    /**
     * 退货箱数
     */
    private String auxbuyoutnumdetail;
    
    /**
     * 入库数量
     */
    private BigDecimal enternum;

    /**
     * 采购合计箱数
     */
    private BigDecimal entertotalbox;

    /**
     * 入库金额
     */
    private BigDecimal enteramount;
    /**
     * 入库未税金额
     */
    private BigDecimal enternotaxamount;
    /**
     * 入库数量辅单位描述
     */
    private String auxenternumdetail;
    
    /**
     * 销售数量
     */
    private BigDecimal saleoutnum;

    /**
     * 销售出库箱数合计
     */
    private BigDecimal saleouttotalbox;
    
    /**
     * 销售金额
     */
    private BigDecimal saleoutamount;
    
    /**
     * 销售未税金额
     */
    private BigDecimal saleoutnotaxamount;
    
    /**
     * 销售箱数
     */
    private String auxsaleoutnumdetail;
    
    /**
     * 退货数量
     */
    private BigDecimal saleinnum;

    /**
     * 销售退货合计箱数
     */
    private BigDecimal saleintotalbox;

    /**
     * 退货金额
     */
    private BigDecimal saleinamount;
    
    /**
     * 退货未税金额
     */
    private BigDecimal saleinnotaxamount;
    
    /**
     * 退货箱数
     */
    private String auxsaleinnumdetail;
    
    /**
     * 出库数量
     */
    private BigDecimal outnum;

    /**
     * 销售合计箱数
     */
    private BigDecimal outtotalbox;

    /**
     * 出库金额
     */
    private BigDecimal outamount;
    /**
     * 出库未税金额
     */
    private BigDecimal outnotaxamount;
    /**
     * 出库成本金额
     */
    private	BigDecimal costoutamount;
    
    /**
     * 出库成本未税金额
     */
    private BigDecimal costnotaxoutamount;
    /**
     * 出库数量辅单位描述
     */
    private String auxoutnumdetail;
    
    /**
     * 调拨入库数量
     */
    private BigDecimal allocateinnum;

    /**
     * 调拨入库合计箱数
     */
    private BigDecimal allocateintotalbox;
    
    /**
     * 调拨入库金额
     */
    private BigDecimal allocateinamount;
    
    /**
     * 调拨入库未税金额
     */
    private BigDecimal allocateinnotaxamount;
    
    /**
     * 调拨入库箱数
     */
    private String auxallocateinnumdetail;
    
    /**
     * 调拨出库数量
     */
    private BigDecimal allocateoutnum;

    /**
     * 调拨出库合计箱数
     */
    private BigDecimal allocateouttotalbox;
    
    /**
     * 调拨出库金额
     */
    private BigDecimal allocateoutamount;
    
    /**
     * 调拨出库未税金额
     */
    private BigDecimal allocateoutnotaxamount;
    
    /**
     * 调拨出库箱数
     */
    private String auxallocateoutnumdetail;
    /**
     * 损益数量（其他出入库）
     */
    private BigDecimal lossnum;

    /**
     * 损益合计箱数
     */
    private BigDecimal losstotalbox;
    /**
     * 损益数量箱数
     */
    private String auxlossnumdetail;
    /**
     * 损益金额
     */
    private BigDecimal lossamount;
    /**
     * 损益未税金额
     */
    private BigDecimal lossnotaxamount;
    /**
     * 损益成本金额
     */
    private BigDecimal costlossamount;
    
    /**
     * 损益成本未税金额
     */
    private BigDecimal costnotaxlossamount;
    /**
     * 期末结存数量
     */
    private BigDecimal endnum;

    /**
     * 期末合计箱数
     */
    private BigDecimal endtotalbox;

    /**
     * 期末结存金额
     */
    private BigDecimal endamount;
    
    /**
     * 期末结存未税金额
     */
    private BigDecimal endnotaxamount;
    /**
     * 期末数量辅单位描述
     */
    private String auxendnumdetail;
    /**
     * 安全库存
     */
    private BigDecimal safenum;

    /**
     * 安全库存辅单位描述
     */
    private String auxsafenumdetail;
    /**
     * 在途可用量
     */
    private BigDecimal transitusenum;
    /**
     * 在途可用量辅单位描述
     */
    private String transitusenumdetail;
    /**
     * 建议采购量
     */
    private BigDecimal suggestnum;
    /**
     * 建议采购量辅单位描述
     */
    private String auxsuggestnumdetail;
    /**
     * 确认采购辅单位数量
     */
    private BigDecimal auxbuynum;
    
    /**
     * 确认采购辅单位描述
     */
    private String auxbuynumdetail;
    
    /**
     * 添加时间
     */
    private Date addtime;
    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;

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

	public String getAuxinitnumdetail() {
		return auxinitnumdetail;
	}

	public void setAuxinitnumdetail(String auxinitnumdetail) {
		this.auxinitnumdetail = auxinitnumdetail;
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

	public String getAuxendnumdetail() {
		return auxendnumdetail;
	}

	public void setAuxendnumdetail(String auxendnumdetail) {
		this.auxendnumdetail = auxendnumdetail;
	}

	public BigDecimal getSafenum() {
		return safenum;
	}

	public void setSafenum(BigDecimal safenum) {
		this.safenum = safenum;
	}

	public String getAuxsafenumdetail() {
		return auxsafenumdetail;
	}

	public void setAuxsafenumdetail(String auxsafenumdetail) {
		this.auxsafenumdetail = auxsafenumdetail;
	}

	public BigDecimal getTransitusenum() {
		return transitusenum;
	}

	public void setTransitusenum(BigDecimal transitusenum) {
		this.transitusenum = transitusenum;
	}

	public String getTransitusenumdetail() {
		return transitusenumdetail;
	}

	public void setTransitusenumdetail(String transitusenumdetail) {
		this.transitusenumdetail = transitusenumdetail;
	}

	public BigDecimal getSuggestnum() {
		return suggestnum;
	}

	public void setSuggestnum(BigDecimal suggestnum) {
		this.suggestnum = suggestnum;
	}

	public String getAuxsuggestnumdetail() {
		return auxsuggestnumdetail;
	}

	public void setAuxsuggestnumdetail(String auxsuggestnumdetail) {
		this.auxsuggestnumdetail = auxsuggestnumdetail;
	}

	public BigDecimal getAuxbuynum() {
		return auxbuynum;
	}

	public void setAuxbuynum(BigDecimal auxbuynum) {
		this.auxbuynum = auxbuynum;
	}

	public String getAuxbuynumdetail() {
		return auxbuynumdetail;
	}

	public void setAuxbuynumdetail(String auxbuynumdetail) {
		this.auxbuynumdetail = auxbuynumdetail;
	}

	public BigDecimal getCostoutamount() {
		return costoutamount;
	}

	public void setCostoutamount(BigDecimal costoutamount) {
		this.costoutamount = costoutamount;
	}

	public BigDecimal getEnternotaxamount() {
		return enternotaxamount;
	}

	public void setEnternotaxamount(BigDecimal enternotaxamount) {
		this.enternotaxamount = enternotaxamount;
	}

	public BigDecimal getOutnotaxamount() {
		return outnotaxamount;
	}

	public void setOutnotaxamount(BigDecimal outnotaxamount) {
		this.outnotaxamount = outnotaxamount;
	}

	public BigDecimal getLossnum() {
		return lossnum;
	}

	public void setLossnum(BigDecimal lossnum) {
		this.lossnum = lossnum;
	}

	public BigDecimal getLossamount() {
		return lossamount;
	}

	public void setLossamount(BigDecimal lossamount) {
		this.lossamount = lossamount;
	}

	public BigDecimal getLossnotaxamount() {
		return lossnotaxamount;
	}

	public void setLossnotaxamount(BigDecimal lossnotaxamount) {
		this.lossnotaxamount = lossnotaxamount;
	}

	public BigDecimal getCostlossamount() {
		return costlossamount;
	}

	public void setCostlossamount(BigDecimal costlossamount) {
		this.costlossamount = costlossamount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAuxlossnumdetail() {
		return auxlossnumdetail;
	}

	public void setAuxlossnumdetail(String auxlossnumdetail) {
		this.auxlossnumdetail = auxlossnumdetail;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getInitnotaxamount() {
		return initnotaxamount;
	}

	public void setInitnotaxamount(BigDecimal initnotaxamount) {
		this.initnotaxamount = initnotaxamount;
	}

	public BigDecimal getCostnotaxoutamount() {
		return costnotaxoutamount;
	}

	public void setCostnotaxoutamount(BigDecimal costnotaxoutamount) {
		this.costnotaxoutamount = costnotaxoutamount;
	}

	public BigDecimal getCostnotaxlossamount() {
		return costnotaxlossamount;
	}

	public void setCostnotaxlossamount(BigDecimal costnotaxlossamount) {
		this.costnotaxlossamount = costnotaxlossamount;
	}

	public BigDecimal getEndnotaxamount() {
		return endnotaxamount;
	}

	public void setEndnotaxamount(BigDecimal endnotaxamount) {
		this.endnotaxamount = endnotaxamount;
	}

	public BigDecimal getBuyinnum() {
		return buyinnum;
	}

	public void setBuyinnum(BigDecimal buyinnum) {
		this.buyinnum = buyinnum;
	}

	public BigDecimal getBuyinamount() {
		return buyinamount;
	}

	public void setBuyinamount(BigDecimal buyinamount) {
		this.buyinamount = buyinamount;
	}

	public BigDecimal getBuyinnotaxamount() {
		return buyinnotaxamount;
	}

	public void setBuyinnotaxamount(BigDecimal buyinnotaxamount) {
		this.buyinnotaxamount = buyinnotaxamount;
	}

	public String getAuxbuyinnumdetail() {
		return auxbuyinnumdetail;
	}

	public void setAuxbuyinnumdetail(String auxbuyinnumdetail) {
		this.auxbuyinnumdetail = auxbuyinnumdetail;
	}

	public BigDecimal getBuyoutnum() {
		return buyoutnum;
	}

	public void setBuyoutnum(BigDecimal buyoutnum) {
		this.buyoutnum = buyoutnum;
	}

	public BigDecimal getBuyoutamount() {
		return buyoutamount;
	}

	public void setBuyoutamount(BigDecimal buyoutamount) {
		this.buyoutamount = buyoutamount;
	}

	public BigDecimal getBuyoutnotaxamount() {
		return buyoutnotaxamount;
	}

	public void setBuyoutnotaxamount(BigDecimal buyoutnotaxamount) {
		this.buyoutnotaxamount = buyoutnotaxamount;
	}

	public String getAuxbuyoutnumdetail() {
		return auxbuyoutnumdetail;
	}

	public void setAuxbuyoutnumdetail(String auxbuyoutnumdetail) {
		this.auxbuyoutnumdetail = auxbuyoutnumdetail;
	}

	public BigDecimal getSaleoutnum() {
		return saleoutnum;
	}

	public void setSaleoutnum(BigDecimal saleoutnum) {
		this.saleoutnum = saleoutnum;
	}

	public BigDecimal getSaleoutamount() {
		return saleoutamount;
	}

	public void setSaleoutamount(BigDecimal saleoutamount) {
		this.saleoutamount = saleoutamount;
	}

	public BigDecimal getSaleoutnotaxamount() {
		return saleoutnotaxamount;
	}

	public void setSaleoutnotaxamount(BigDecimal saleoutnotaxamount) {
		this.saleoutnotaxamount = saleoutnotaxamount;
	}

	public String getAuxsaleoutnumdetail() {
		return auxsaleoutnumdetail;
	}

	public void setAuxsaleoutnumdetail(String auxsaleoutnumdetail) {
		this.auxsaleoutnumdetail = auxsaleoutnumdetail;
	}

	public BigDecimal getSaleinnum() {
		return saleinnum;
	}

	public void setSaleinnum(BigDecimal saleinnum) {
		this.saleinnum = saleinnum;
	}

	public BigDecimal getSaleinamount() {
		return saleinamount;
	}

	public void setSaleinamount(BigDecimal saleinamount) {
		this.saleinamount = saleinamount;
	}

	public BigDecimal getSaleinnotaxamount() {
		return saleinnotaxamount;
	}

	public void setSaleinnotaxamount(BigDecimal saleinnotaxamount) {
		this.saleinnotaxamount = saleinnotaxamount;
	}

	public String getAuxsaleinnumdetail() {
		return auxsaleinnumdetail;
	}

	public void setAuxsaleinnumdetail(String auxsaleinnumdetail) {
		this.auxsaleinnumdetail = auxsaleinnumdetail;
	}

	public BigDecimal getAllocateinnum() {
		return allocateinnum;
	}

	public void setAllocateinnum(BigDecimal allocateinnum) {
		this.allocateinnum = allocateinnum;
	}

	public BigDecimal getAllocateinamount() {
		return allocateinamount;
	}

	public void setAllocateinamount(BigDecimal allocateinamount) {
		this.allocateinamount = allocateinamount;
	}

	public BigDecimal getAllocateinnotaxamount() {
		return allocateinnotaxamount;
	}

	public void setAllocateinnotaxamount(BigDecimal allocateinnotaxamount) {
		this.allocateinnotaxamount = allocateinnotaxamount;
	}

	public String getAuxallocateinnumdetail() {
		return auxallocateinnumdetail;
	}

	public void setAuxallocateinnumdetail(String auxallocateinnumdetail) {
		this.auxallocateinnumdetail = auxallocateinnumdetail;
	}

	public BigDecimal getAllocateoutnum() {
		return allocateoutnum;
	}

	public void setAllocateoutnum(BigDecimal allocateoutnum) {
		this.allocateoutnum = allocateoutnum;
	}

	public BigDecimal getAllocateoutamount() {
		return allocateoutamount;
	}

	public void setAllocateoutamount(BigDecimal allocateoutamount) {
		this.allocateoutamount = allocateoutamount;
	}

	public BigDecimal getAllocateoutnotaxamount() {
		return allocateoutnotaxamount;
	}

	public void setAllocateoutnotaxamount(BigDecimal allocateoutnotaxamount) {
		this.allocateoutnotaxamount = allocateoutnotaxamount;
	}

	public String getAuxallocateoutnumdetail() {
		return auxallocateoutnumdetail;
	}

	public void setAuxallocateoutnumdetail(String auxallocateoutnumdetail) {
		this.auxallocateoutnumdetail = auxallocateoutnumdetail;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
        this.suppliername = suppliername == null ? null : suppliername.trim();
	}

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

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

    public String getIsdiscount() {
        return isdiscount;
    }

    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount;
    }

    public BigDecimal getInittotalbox() {
        return inittotalbox;
    }

    public void setInittotalbox(BigDecimal inittotalbox) {
        this.inittotalbox = inittotalbox;
    }

    public BigDecimal getBuyintotalbox() {
        return buyintotalbox;
    }

    public void setBuyintotalbox(BigDecimal buyintotalbox) {
        this.buyintotalbox = buyintotalbox;
    }

    public BigDecimal getBuyouttotalbox() {
        return buyouttotalbox;
    }

    public void setBuyouttotalbox(BigDecimal buyouttotalbox) {
        this.buyouttotalbox = buyouttotalbox;
    }

    public BigDecimal getEntertotalbox() {
        return entertotalbox;
    }

    public void setEntertotalbox(BigDecimal entertotalbox) {
        this.entertotalbox = entertotalbox;
    }

    public BigDecimal getSaleouttotalbox() {
        return saleouttotalbox;
    }

    public void setSaleouttotalbox(BigDecimal saleouttotalbox) {
        this.saleouttotalbox = saleouttotalbox;
    }

    public BigDecimal getSaleintotalbox() {
        return saleintotalbox;
    }

    public void setSaleintotalbox(BigDecimal saleintotalbox) {
        this.saleintotalbox = saleintotalbox;
    }

    public BigDecimal getOuttotalbox() {
        return outtotalbox;
    }

    public void setOuttotalbox(BigDecimal outtotalbox) {
        this.outtotalbox = outtotalbox;
    }

    public BigDecimal getAllocateintotalbox() {
        return allocateintotalbox;
    }

    public void setAllocateintotalbox(BigDecimal allocateintotalbox) {
        this.allocateintotalbox = allocateintotalbox;
    }

    public BigDecimal getAllocateouttotalbox() {
        return allocateouttotalbox;
    }

    public void setAllocateouttotalbox(BigDecimal allocateouttotalbox) {
        this.allocateouttotalbox = allocateouttotalbox;
    }

    public BigDecimal getLosstotalbox() {
        return losstotalbox;
    }

    public void setLosstotalbox(BigDecimal losstotalbox) {
        this.losstotalbox = losstotalbox;
    }

    public BigDecimal getEndtotalbox() {
        return endtotalbox;
    }

    public void setEndtotalbox(BigDecimal endtotalbox) {
        this.endtotalbox = endtotalbox;
    }
}