package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.struts2.json.annotations.JSON;

public class FundInput implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 供应商编码
     */
    private String supplierid;
    
    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商所属部门
     */
    private String supplierdeptid;
    
    /**
     * 供应商所属部门名称
     */
    private String supplierdeptName;

    /**
     * 预付金额
     */
    private BigDecimal advanceamount;

    /**
     * 库存金额
     */
    private BigDecimal stockamount;

    /**
     * 应收金额
     */
    private BigDecimal receivableamount;

    /**
     * 代垫金额
     */
    private BigDecimal actingmatamount;

    /**
     * 应付金额
     */
    private BigDecimal payableamount;

    /**
     * 代垫未收
     */
    private BigDecimal norecactingmat;

    /**
     * 费用未收
     */
    private BigDecimal norecexpenses;

    /**
     * 库存折差
     */
    private BigDecimal stockdiscount;

    /**
     * 本期代垫
     */
    private BigDecimal currentactingmat;

    /**
     * 汇款收回
     */
    private BigDecimal remittancerecovery;

    /**
     * 货补收回
     */
    private BigDecimal goodsrecovery;

    /**
     * 合计金额
     */
    private BigDecimal totalamount;

    /**
     * 累计金额
     */
    private BigDecimal sumamount;

    /**
     * 累计代垫
     */
    private BigDecimal sumactingmat;

    /**
     * 累计已收
     */
    private BigDecimal sumreceivable;

    /**
     * 累计未收
     */
    private BigDecimal sumnorec;

    /**
     * 制单人编码
     */
    private String adduserid;

    /**
     * 制单人
     */
    private String addusername;

    /**
     * 制单日期
     */
    private String addtime;
    
    /**
     * 状态
     */
    private String state;
    
    /**
     * 状态名称
     */
    private String stateName;

    /**
     * @return 编码
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
     * @return 供应商编码
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编码
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    /**
     * @return 供应商所属部门
     */
    public String getSupplierdeptid() {
        return supplierdeptid;
    }

    /**
     * @param supplierdeptid 
	 *            供应商所属部门
     */
    public void setSupplierdeptid(String supplierdeptid) {
        this.supplierdeptid = supplierdeptid == null ? null : supplierdeptid.trim();
    }

    /**
     * @return 预付金额
     */
    public BigDecimal getAdvanceamount() {
        return advanceamount;
    }

    /**
     * @param advanceamount 
	 *            预付金额
     */
    public void setAdvanceamount(BigDecimal advanceamount) {
        this.advanceamount = advanceamount;
    }

    /**
     * @return 库存金额
     */
    public BigDecimal getStockamount() {
        return stockamount;
    }

    /**
     * @param stockamount 
	 *            库存金额
     */
    public void setStockamount(BigDecimal stockamount) {
        this.stockamount = stockamount;
    }

    /**
     * @return 应收金额
     */
    public BigDecimal getReceivableamount() {
        return receivableamount;
    }

    /**
     * @param receivableamount 
	 *            应收金额
     */
    public void setReceivableamount(BigDecimal receivableamount) {
        this.receivableamount = receivableamount;
    }

    /**
     * @return 代垫金额
     */
    public BigDecimal getActingmatamount() {
        return actingmatamount;
    }

    /**
     * @param actingmatamount 
	 *            代垫金额
     */
    public void setActingmatamount(BigDecimal actingmatamount) {
        this.actingmatamount = actingmatamount;
    }

    /**
     * @return 应付金额
     */
    public BigDecimal getPayableamount() {
        return payableamount;
    }

    /**
     * @param payableamount 
	 *            应付金额
     */
    public void setPayableamount(BigDecimal payableamount) {
        this.payableamount = payableamount;
    }

    /**
     * @return 制单人编码
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编码
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 制单日期
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public String getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单日期
     */
    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierdeptName() {
		return supplierdeptName;
	}

	public void setSupplierdeptName(String supplierdeptName) {
		this.supplierdeptName = supplierdeptName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

    public BigDecimal getNorecactingmat() {
        return norecactingmat;
    }

    public void setNorecactingmat(BigDecimal norecactingmat) {
        this.norecactingmat = norecactingmat;
    }

    public BigDecimal getNorecexpenses() {
        return norecexpenses;
    }

    public void setNorecexpenses(BigDecimal norecexpenses) {
        this.norecexpenses = norecexpenses;
    }

    public BigDecimal getStockdiscount() {
        return stockdiscount;
    }

    public void setStockdiscount(BigDecimal stockdiscount) {
        this.stockdiscount = stockdiscount;
    }

    public BigDecimal getCurrentactingmat() {
        return currentactingmat;
    }

    public void setCurrentactingmat(BigDecimal currentactingmat) {
        this.currentactingmat = currentactingmat;
    }

    public BigDecimal getRemittancerecovery() {
        return remittancerecovery;
    }

    public void setRemittancerecovery(BigDecimal remittancerecovery) {
        this.remittancerecovery = remittancerecovery;
    }

    public BigDecimal getGoodsrecovery() {
        return goodsrecovery;
    }

    public void setGoodsrecovery(BigDecimal goodsrecovery) {
        this.goodsrecovery = goodsrecovery;
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public BigDecimal getSumamount() {
        return sumamount;
    }

    public void setSumamount(BigDecimal sumamount) {
        this.sumamount = sumamount;
    }

    public BigDecimal getSumactingmat() {
        return sumactingmat;
    }

    public void setSumactingmat(BigDecimal sumactingmat) {
        this.sumactingmat = sumactingmat;
    }

    public BigDecimal getSumreceivable() {
        return sumreceivable;
    }

    public void setSumreceivable(BigDecimal sumreceivable) {
        this.sumreceivable = sumreceivable;
    }

    public BigDecimal getSumnorec() {
        return sumnorec;
    }

    public void setSumnorec(BigDecimal sumnorec) {
        this.sumnorec = sumnorec;
    }
}