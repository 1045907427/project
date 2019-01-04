package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 资金占用报表
 * @author chenwei
 */
public class CapitalOccupyReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 业务日期
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
     * 品牌部门编号
     */
    private String branddept;
    /**
     * 品牌部门名称
     */
    private String branddeptname;
    /**
     * 预付金额
     */
    private BigDecimal prepayamount;

    /**
     * 库存金额
     */
    private BigDecimal storageamount;

    /**
     * 应收金额
     */
    private BigDecimal receivableamount;

    /**
     * 代垫金额
     */
    private BigDecimal advanceamount;

    /**
     * 应付金额
     */
    private BigDecimal payableamount;

    /**
     * 合计金额（预付+库存+应收+代垫-应付）
     */
    private BigDecimal totalamount;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
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
     * @return 品牌部门编号
     */
    public String getBranddept() {
        return branddept;
    }

    /**
     * @param branddept 
	 *            品牌部门编号
     */
    public void setBranddept(String branddept) {
        this.branddept = branddept == null ? null : branddept.trim();
    }

    /**
     * @return 预付金额
     */
    public BigDecimal getPrepayamount() {
        return prepayamount;
    }

    /**
     * @param prepayamount 
	 *            预付金额
     */
    public void setPrepayamount(BigDecimal prepayamount) {
        this.prepayamount = prepayamount;
    }

    /**
     * @return 库存金额
     */
    public BigDecimal getStorageamount() {
        return storageamount;
    }

    /**
     * @param storageamount 
	 *            库存金额
     */
    public void setStorageamount(BigDecimal storageamount) {
        this.storageamount = storageamount;
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
    public BigDecimal getAdvanceamount() {
        return advanceamount;
    }

    /**
     * @param advanceamount 
	 *            代垫金额
     */
    public void setAdvanceamount(BigDecimal advanceamount) {
        this.advanceamount = advanceamount;
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
     * @return 合计金额（预付+库存+应收+代垫-应付）
     */
    public BigDecimal getTotalamount() {
        return totalamount;
    }

    /**
     * @param totalamount 
	 *            合计金额（预付+库存+应收+代垫-应付）
     */
    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
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

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getBranddeptname() {
		return branddeptname;
	}

	public void setBranddeptname(String branddeptname) {
		this.branddeptname = branddeptname;
	}
    
}