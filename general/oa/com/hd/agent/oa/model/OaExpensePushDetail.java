package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaExpensePushDetail implements Serializable {
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
     * 已批OA编号
     */
    private String oaid;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 费用所属部门
     */
    private String deptid;

    /**
     * 折让金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 折差品牌名称
     */
    private String brandname;
    
    /**
     * 费用部门名称
     */
    private String deptname;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 原价
     */
    private BigDecimal oldprice;

    /**
     * 现价
     */
    private BigDecimal newprice;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 费用分类
     */
    private String expensesort;

    /**
     * 开始日期
     */
    private String startdate;

    /**
     * 结束日期
     */
    private String enddate;

    /**
     * 费用分类名称
     */
    private String expensesortname;

    /**
     * 最新采购价
     */
    private BigDecimal buyprice;
    
    /**
     * 冲差类型
     */
    private String pushtype;

    /**
     * 冲差类型名称
     */
    private String pushtypename;
    
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
     * @return 已批OA编号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * @param oaid 
	 *            已批OA编号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
    }

    /**
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
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
     * @return 费用所属部门
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            费用所属部门
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 折让金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            折让金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public BigDecimal getOldprice() {
        return oldprice;
    }

    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    public BigDecimal getNewprice() {
        return newprice;
    }

    public void setNewprice(BigDecimal newprice) {
        this.newprice = newprice;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getExpensesort() {
        return expensesort;
    }

    public void setExpensesort(String expensesort) {
        this.expensesort = expensesort;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getExpensesortname() {
        return expensesortname;
    }

    public void setExpensesortname(String expensesortname) {
        this.expensesortname = expensesortname;
    }

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    public String getPushtype() {
        return pushtype;
    }

    public void setPushtype(String pushtype) {
        this.pushtype = pushtype;
    }

    public String getPushtypename() {
        return pushtypename;
    }

    public void setPushtypename(String pushtypename) {
        this.pushtypename = pushtypename;
    }
}