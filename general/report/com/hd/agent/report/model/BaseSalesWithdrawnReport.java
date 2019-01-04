package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 基础销售应收报表
 * @author chenwei
 */
public class BaseSalesWithdrawnReport implements Serializable {
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
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 总店客户
     */
    private String pcustomerid;
    /**
     * 总店客户名称
     */
    private String pcustomername;
    /**
     * 供应商编码
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;
    /**
     * 所属部门编号
     */
    private String salesdept;
    /**
     * 所属部门名称
     */
    private String salesdeptname;
    /**
     * 所属区域
     */
    private String salesarea;
    /**
     * 所属区域名称
     */
    private String salesareaname;
    /**
     * 客户业务员
     */
    private String salesuser;
    /**
     * 客户业务员名称
     */
    private String salesusername;
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
     * 品牌名称
     */
    private String brandname;
    /**
     * 品牌业务员
     */
    private String branduser;
    /**
     * 品牌业务员名称
     */
    private String brandusername;

	/**
	 * 人员部门
	 */
	private String branduserdept;

	/**
	 * 人员部门名称
	 */
	private String branduserdeptname;
	/**
	 * 商品分类编号
	 */
	private String goodssort;
	/**
	 * 商品分类名称
	 */
	private String goodssortname;

    /**
     * 厂家业务员
     */
    private String supplieruser;

    /**
     * 厂家业务员名称
     */
    private String supplierusername;
    /**
     * 品牌部门
     */
    private String branddept;
    /**
     * 品牌部门名称
     */
    private String branddeptname;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 辅数量
     */
    private BigDecimal auxnum;

    /**
     * 辅数量描述
     */
    private String auxnumdetail;
    /**
	 * 发货金额
	 */
	private BigDecimal sendamount;
	/**
	 * 发货无税金额
	 */
	private BigDecimal sendnotaxamount;
	/**
	 * 发货成本金额
	 */
	private BigDecimal sendcostamount;
    /**
     * 直退金额
     */
    private BigDecimal directreturnamount;

    /**
     * 无税直退金额
     */
    private BigDecimal directreturnnotaxamount;
    /**
     * 直退成本金额
     */
    private BigDecimal directreturncostamount;
    /**
     * 售后退货金额
     */
    private BigDecimal checkreturnamount;

    /**
     * 无税售后退货金额
     */
    private BigDecimal checkreturnnotaxamount;
    /**
     * 售后退货成本金额
     */
    private BigDecimal checkreturncostamount;
	/**
	 * 退货金额
	 */
	private BigDecimal returntaxamount;
	/**
	 * 退货无税金额
	 */
	private BigDecimal returnnotaxamount;
	/**
	 * 退货成本
	 */
	private BigDecimal returncostamount;
	/**
	 * 冲差金额（时候折扣）
	 */
	private BigDecimal pushbalanceamount;
	/**
	 * 实际应收金额
	 */
	private BigDecimal receivableamount;
	/**
	 * 销售总额
	 */
	private BigDecimal saleamount;
	/**
	 * 销售未税总额
	 */
	private BigDecimal salenotaxamount;
	/**
	 * 销售成本金额
	 */
	private BigDecimal salecostamount;
	/**
	 * 销售毛利额
	 */
	private BigDecimal salemarginamount;
	/**
	 * 销售毛利率
	 */
	private BigDecimal salerate;
	/**
	 * 已回笼金额
	 */
	private BigDecimal withdrawnamount;
	/**
	 * 未回笼金额
	 */
	private BigDecimal unwithdrawnamount;
	/**
	 * 回笼成本金额
	 */
	private BigDecimal costwriteoffamount;
	/**
	 * 回笼毛利额 
	 */
	private BigDecimal writeoffmarginamount;
	/**
	 * 回笼毛利率
	 */
	private BigDecimal writeoffrate;
	/**
	 * 资金回笼率
	 */
	private BigDecimal withdrawrate;
	/**
	 * 未核销总发货金额
	 */
	private BigDecimal allsendamount;
	/**
	 * 未核销总退货金额
	 */
	private BigDecimal allreturnamount;
	/**
	 * 未核销冲差金额（冲差应收款）
	 */
	private BigDecimal allpushbalanceamount;

	/**
	 * 未核销期初应收款
	 */
	private BigDecimal allbeginamount;
	/**
	 * 应收款总额
	 */
	private BigDecimal allunwithdrawnamount;
	/**
	 * 回单未审核应收款（未验收应收款）
	 */
	private BigDecimal unauditamount;
	/**
	 * 回单已审核应收款（已验收应收款）
	 */
	private BigDecimal auditamount;
	/**
	 * 售后退货未核销应收款（退货应收款）
	 */
	private BigDecimal rejectamount;

    /**
     * 客户资金余额
     */
    private BigDecimal customeramount;
	public String getId() {
		return id;
	}

    public BigDecimal getCustomeramount() {
        return customeramount;
    }

    public void setCustomeramount(BigDecimal customeramount) {
        this.customeramount = null != customeramount ? customeramount : BigDecimal.ZERO;
    }

    public void setId(String id) {
		this.id = id;
	}
	public String getBusinessdate() {
		return businessdate;
	}
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
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
	public String getPcustomerid() {
		return pcustomerid;
	}
	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}
	public String getPcustomername() {
		return pcustomername;
	}
	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}
	
	public String getSalesdept() {
		return salesdept;
	}
	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}
	public String getSalesdeptname() {
		return salesdeptname;
	}
	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
	}
	public String getSalesarea() {
		return salesarea;
	}
	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}
	public String getSalesareaname() {
		return salesareaname;
	}
	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}
	public String getSalesusername() {
		return salesusername;
	}
	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
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
	
	public String getBrandid() {
		return brandid;
	}
	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	public String getBranduser() {
		return branduser;
	}
	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}
	public String getBrandusername() {
		return brandusername;
	}
	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}
	public String getBranddept() {
		return branddept;
	}
	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}
	public String getBranddeptname() {
		return branddeptname;
	}
	public void setBranddeptname(String branddeptname) {
		this.branddeptname = branddeptname;
	}
	public BigDecimal getSendamount() {
		return sendamount;
	}
	public void setSendamount(BigDecimal sendamount) {
		this.sendamount = sendamount;
	}
	public BigDecimal getSendnotaxamount() {
		return sendnotaxamount;
	}
	public void setSendnotaxamount(BigDecimal sendnotaxamount) {
		this.sendnotaxamount = sendnotaxamount;
	}
	public BigDecimal getSendcostamount() {
		return sendcostamount;
	}
	public void setSendcostamount(BigDecimal sendcostamount) {
		this.sendcostamount = sendcostamount;
	}
	public BigDecimal getDirectreturnamount() {
		return directreturnamount;
	}
	public void setDirectreturnamount(BigDecimal directreturnamount) {
		this.directreturnamount = directreturnamount;
	}
	public BigDecimal getDirectreturnnotaxamount() {
		return directreturnnotaxamount;
	}
	public void setDirectreturnnotaxamount(BigDecimal directreturnnotaxamount) {
		this.directreturnnotaxamount = directreturnnotaxamount;
	}
	public BigDecimal getDirectreturncostamount() {
		return directreturncostamount;
	}
	public void setDirectreturncostamount(BigDecimal directreturncostamount) {
		this.directreturncostamount = directreturncostamount;
	}
	public BigDecimal getCheckreturnamount() {
		return checkreturnamount;
	}
	public void setCheckreturnamount(BigDecimal checkreturnamount) {
		this.checkreturnamount = checkreturnamount;
	}
	public BigDecimal getCheckreturnnotaxamount() {
		return checkreturnnotaxamount;
	}
	public void setCheckreturnnotaxamount(BigDecimal checkreturnnotaxamount) {
		this.checkreturnnotaxamount = checkreturnnotaxamount;
	}
	public BigDecimal getCheckreturncostamount() {
		return checkreturncostamount;
	}
	public void setCheckreturncostamount(BigDecimal checkreturncostamount) {
		this.checkreturncostamount = checkreturncostamount;
	}
	public BigDecimal getReturntaxamount() {
		return returntaxamount;
	}
	public void setReturntaxamount(BigDecimal returntaxamount) {
		this.returntaxamount = returntaxamount;
	}
	public BigDecimal getReturnnotaxamount() {
		return returnnotaxamount;
	}
	public void setReturnnotaxamount(BigDecimal returnnotaxamount) {
		this.returnnotaxamount = returnnotaxamount;
	}
	public BigDecimal getReturncostamount() {
		return returncostamount;
	}
	public void setReturncostamount(BigDecimal returncostamount) {
		this.returncostamount = returncostamount;
	}
	public BigDecimal getPushbalanceamount() {
		return pushbalanceamount;
	}
	public void setPushbalanceamount(BigDecimal pushbalanceamount) {
		this.pushbalanceamount = pushbalanceamount;
	}
	public BigDecimal getReceivableamount() {
		return receivableamount;
	}
	public void setReceivableamount(BigDecimal receivableamount) {
		this.receivableamount = receivableamount;
	}
	public BigDecimal getSaleamount() {
		return saleamount;
	}
	public void setSaleamount(BigDecimal saleamount) {
		this.saleamount = saleamount;
	}
	public BigDecimal getSalenotaxamount() {
		return salenotaxamount;
	}
	public void setSalenotaxamount(BigDecimal salenotaxamount) {
		this.salenotaxamount = salenotaxamount;
	}
	public BigDecimal getSalecostamount() {
		return salecostamount;
	}
	public void setSalecostamount(BigDecimal salecostamount) {
		this.salecostamount = salecostamount;
	}
	public BigDecimal getSalerate() {
		return salerate;
	}
	public void setSalerate(BigDecimal salerate) {
		this.salerate = salerate;
	}
	public BigDecimal getWithdrawnamount() {
		return withdrawnamount;
	}
	public void setWithdrawnamount(BigDecimal withdrawnamount) {
		this.withdrawnamount = withdrawnamount;
	}
	public BigDecimal getCostwriteoffamount() {
		return costwriteoffamount;
	}
	public void setCostwriteoffamount(BigDecimal costwriteoffamount) {
		this.costwriteoffamount = costwriteoffamount;
	}
	public BigDecimal getWriteoffrate() {
		return writeoffrate;
	}
	public void setWriteoffrate(BigDecimal writeoffrate) {
		this.writeoffrate = writeoffrate;
	}
	public BigDecimal getWithdrawrate() {
		return withdrawrate;
	}
	public void setWithdrawrate(BigDecimal withdrawrate) {
		this.withdrawrate = withdrawrate;
	}
	public BigDecimal getAllsendamount() {
		return allsendamount;
	}
	public void setAllsendamount(BigDecimal allsendamount) {
		this.allsendamount = allsendamount;
	}
	public BigDecimal getAllreturnamount() {
		return allreturnamount;
	}
	public void setAllreturnamount(BigDecimal allreturnamount) {
		this.allreturnamount = allreturnamount;
	}
	public BigDecimal getAllpushbalanceamount() {
		return allpushbalanceamount;
	}
	public void setAllpushbalanceamount(BigDecimal allpushbalanceamount) {
		this.allpushbalanceamount = allpushbalanceamount;
	}
	public BigDecimal getAllunwithdrawnamount() {
		return allunwithdrawnamount;
	}
	public void setAllunwithdrawnamount(BigDecimal allunwithdrawnamount) {
		this.allunwithdrawnamount = allunwithdrawnamount;
	}
	public BigDecimal getUnauditamount() {
		return unauditamount;
	}
	public void setUnauditamount(BigDecimal unauditamount) {
		this.unauditamount = unauditamount;
	}
	public BigDecimal getAuditamount() {
		return auditamount;
	}
	public void setAuditamount(BigDecimal auditamount) {
		this.auditamount = auditamount;
	}
	public BigDecimal getRejectamount() {
		return rejectamount;
	}
	public void setRejectamount(BigDecimal rejectamount) {
		this.rejectamount = rejectamount;
	}
	public BigDecimal getSalemarginamount() {
		return salemarginamount;
	}
	public void setSalemarginamount(BigDecimal salemarginamount) {
		this.salemarginamount = salemarginamount;
	}
	public BigDecimal getUnwithdrawnamount() {
		return unwithdrawnamount;
	}
	public void setUnwithdrawnamount(BigDecimal unwithdrawnamount) {
		this.unwithdrawnamount = unwithdrawnamount;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	public String getSalesuser() {
		return salesuser;
	}
	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}
	public BigDecimal getWriteoffmarginamount() {
		return writeoffmarginamount;
	}
	public void setWriteoffmarginamount(BigDecimal writeoffmarginamount) {
		this.writeoffmarginamount = writeoffmarginamount;
	}
    public String getSupplierusername() {
        return supplierusername;
    }

    public void setSupplierusername(String supplierusername) {
        this.supplierusername = supplierusername;
    }

    public String getSupplieruser() {
        return supplieruser;
    }

    public void setSupplieruser(String supplieruser) {
        this.supplieruser = supplieruser;
    }

    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public BigDecimal getAuxnum() {
        return auxnum;
    }

    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail;
    }

	public BigDecimal getAllbeginamount() {
		return allbeginamount;
	}

	public void setAllbeginamount(BigDecimal allbeginamount) {
		this.allbeginamount = allbeginamount;
	}

	public String getBranduserdept() {
		return branduserdept;
	}

	public void setBranduserdept(String branduserdept) {
		this.branduserdept = branduserdept;
	}

	public String getBranduserdeptname() {
		return branduserdeptname;
	}

	public void setBranduserdeptname(String branduserdeptname) {
		this.branduserdeptname = branduserdeptname;
	}

	public String getGoodssort() {
		return goodssort;
	}

	public void setGoodssort(String goodssort) {
		this.goodssort = goodssort;
	}

	public String getGoodssortname() {
		return goodssortname;
	}

	public void setGoodssortname(String goodssortname) {
		this.goodssortname = goodssortname;
	}
}