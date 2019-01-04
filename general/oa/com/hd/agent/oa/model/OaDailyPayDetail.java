package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 日常费用支付单明细
 * @author chenwei
 */
public class OaDailyPayDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 物品编号（项目编号）
     */
    private String itemid;

    /**
     * 物品名称（项目名称）
     */
    private String itemname;

    /**
     * 领用部门编号
     */
    private String applydetpid;

    /**
     * 单位名称
     */
    private String uintname;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 是否固定资产1是0否
     */
    private String isfix;

    /**
     * 有效期（针对固定资产单位为年）
     */
    private Integer uselife;
    
    /**
     * 领用部门名称
     */
    private String applydetpname;

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
     * @return 物品编号（项目编号）
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * @param itemid 
	 *            物品编号（项目编号）
     */
    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    /**
     * @return 物品名称（项目名称）
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname 
	 *            物品名称（项目名称）
     */
    public void setItemname(String itemname) {
        this.itemname = itemname == null ? null : itemname.trim();
    }

    /**
     * @return 领用部门编号
     */
    public String getApplydetpid() {
        return applydetpid;
    }

    /**
     * @param applydetpid 
	 *            领用部门编号
     */
    public void setApplydetpid(String applydetpid) {
        this.applydetpid = applydetpid == null ? null : applydetpid.trim();
    }

    /**
     * @return 单位名称
     */
    public String getUintname() {
        return uintname;
    }

    /**
     * @param uintname 
	 *            单位名称
     */
    public void setUintname(String uintname) {
        this.uintname = uintname == null ? null : uintname.trim();
    }

    /**
     * @return 数量
     */
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    /**
     * @param unitnum 
	 *            数量
     */
    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    /**
     * @return 单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return 是否固定资产1是0否
     */
    public String getIsfix() {
        return isfix;
    }

    /**
     * @param isfix 
	 *            是否固定资产1是0否
     */
    public void setIsfix(String isfix) {
        this.isfix = isfix == null ? null : isfix.trim();
    }

    /**
     * @return 有效期（针对固定资产单位为年）
     */
    public Integer getUselife() {
        return uselife;
    }

    /**
     * @param uselife 
	 *            有效期（针对固定资产单位为年）
     */
    public void setUselife(Integer uselife) {
        this.uselife = uselife;
    }

	public String getApplydetpname() {
		return applydetpname;
	}

	public void setApplydetpname(String applydetpname) {
		this.applydetpname = applydetpname;
	}
}