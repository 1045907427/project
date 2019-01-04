package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 收款单发票对账单
 * @author chenwei
 */
public class CollectionOrderSatement implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 收款单编号
     */
    private String orderid;

    /**
     * 销售发票编号(或者冲差单编号)
     */
    private String billid;
    /**
     * 单据类型1销售发票2冲差单
     */
    private String billtype;
    /**
     * 收款单金额
     */
    private BigDecimal orderamount;

    /**
     * 发票金额
     */
    private BigDecimal invoiceamount;

    /**
     * 关联金额
     */
    private BigDecimal relateamount;
    
    /**
     * 收款单未核销金额
     */
    private BigDecimal unwriteoffamount;

    /**
     * 添加时间
     */
    private Date addtime;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 发票号
     */
    private String invoiceid;
    /**
     * 单据日期
     */
    private String businessdate;
    /**
     * 核销日期
     */
    private String writeoffdate;
    /**
     * 是否核销1是0否
     */
    private String iswrieteoff;
    /**
     * 已核销金额
     */
    private BigDecimal writeoffamount;
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 银行编码
     */
    private String bank;
    
    /**
     * 银行名称
     */
    private String bankname;
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
     * @return 收款单编号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid 
	 *            收款单编号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    /**
     * @return 销售发票编号(或者冲差单编号)
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            销售发票编号(或者冲差单编号)
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 收款单金额
     */
    public BigDecimal getOrderamount() {
        return orderamount;
    }

    /**
     * @param orderamount 
	 *            收款单金额
     */
    public void setOrderamount(BigDecimal orderamount) {
        this.orderamount = orderamount;
    }

    /**
     * @return 发票金额
     */
    public BigDecimal getInvoiceamount() {
        return invoiceamount;
    }

    /**
     * @param invoiceamount 
	 *            发票金额
     */
    public void setInvoiceamount(BigDecimal invoiceamount) {
        this.invoiceamount = invoiceamount;
    }

    /**
     * @return 关联金额
     */
    public BigDecimal getRelateamount() {
        return relateamount;
    }

    /**
     * @param relateamount 
	 *            关联金额
     */
    public void setRelateamount(BigDecimal relateamount) {
        this.relateamount = relateamount;
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

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(String invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getBusinessdate() {
		return businessdate;
	}

	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWriteoffdate() {
		return writeoffdate;
	}

	public void setWriteoffdate(String writeoffdate) {
		this.writeoffdate = writeoffdate;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}

	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}

	public String getIswrieteoff() {
		return iswrieteoff;
	}

	public void setIswrieteoff(String iswrieteoff) {
		this.iswrieteoff = iswrieteoff;
	}

	public BigDecimal getUnwriteoffamount() {
		return unwriteoffamount;
	}

	public void setUnwriteoffamount(BigDecimal unwriteoffamount) {
		this.unwriteoffamount = unwriteoffamount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
    
}