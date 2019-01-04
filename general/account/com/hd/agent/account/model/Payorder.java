/**
 * @(#)Payorder.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 15, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;
/**
 * 
 * 付款单model
 * @author panxiaoxiao
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Payorder implements Serializable {
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
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人姓名
     */
    private String addusername;

    /**
     * 制单人部门编号
     */
    private String adddeptid;

    /**
     * 制单人部门名称
     */
    private String adddeptname;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改人名称
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 审核人编号
     */
    private String audituserid;

    /**
     * 审核人名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 中止人编号
     */
    private String stopuserid;

    /**
     * 中止人名称
     */
    private String stopusername;

    /**
     * 中止时间
     */
    private Date stoptime;

    /**
     * 关闭时间
     */
    private Date closetime;

    /**
     * 打印次数
     */
    private Integer printtimes;

    /**
     * 表头自定义项1
     */
    private String field01;

    /**
     * 表头自定义项2
     */
    private String field02;

    /**
     * 表头自定义项3
     */
    private String field03;

    /**
     * 表头自定义项4
     */
    private String field04;

    /**
     * 表头自定义项5
     */
    private String field05;

    /**
     * 表头自定义项6
     */
    private String field06;

    /**
     * 表头自定义项7
     */
    private String field07;

    /**
     * 表头自定义项8
     */
    private String field08;
    /**
     * 是否预付单 1是0否
     */
    private String prepay;
    /**
     * 付款类型1货款2折让3费用4其他
     */
    private String paytype;

    /**
     * 采购部门
     */
    private String buydeptid;
    
    /**
     * 采购部门名称
     */
    private String buydeptname;

    /**
     * 采购员
     */
    private String buyuserid;
    
    /**
     * 采购员名称
     */
    private String buyusername;

    /**
     * 供应商编号
     */
    private String supplierid;
    
    /**
     * 供应商名称
     */
    private String suppliername;

    /**
     * 对方经手人
     */
    private String handlerid;
    
    /**
     * 对方经手人名称
     */
    private String handlername;
    /**
     * 支付方式
     */
    private String payment;
    /**
     * 银行
     */
    private String bank;
    /**
     * 对应发票号
     */
    private String invoiceno;

    /**
     * 收款金额
     */
    private BigDecimal amount;

    /**
     * 核销金额
     */
    private BigDecimal writeoffamount;

    /**
     * 剩余金额
     */
    private BigDecimal remainderamount;

    /**
     * 是否核销完成1核销完0未开始2核销中
     */
    private String iswriteoff;
    /**
     * OA编号
     */
    private String oaid;
    /**
     * 生成凭证次数
     */
    private Integer vouchertimes;

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
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
     * @return 制单人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 制单人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            制单人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 制单人部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            制单人部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 制单时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最后修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最后修改人名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 最后修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            最后修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 审核人编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核人编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核人名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核人名称
     */
    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername == null ? null : auditusername.trim();
    }

    /**
     * @return 审核时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAudittime() {
        return audittime;
    }

    /**
     * @param audittime 
	 *            审核时间
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    /**
     * @return 中止人编号
     */
    public String getStopuserid() {
        return stopuserid;
    }

    /**
     * @param stopuserid 
	 *            中止人编号
     */
    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid == null ? null : stopuserid.trim();
    }

    /**
     * @return 中止人名称
     */
    public String getStopusername() {
        return stopusername;
    }

    /**
     * @param stopusername 
	 *            中止人名称
     */
    public void setStopusername(String stopusername) {
        this.stopusername = stopusername == null ? null : stopusername.trim();
    }

    /**
     * @return 中止时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getStoptime() {
        return stoptime;
    }

    /**
     * @param stoptime 
	 *            中止时间
     */
    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    /**
     * @return 关闭时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            关闭时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 打印次数
     */
    public Integer getPrinttimes() {
        return printtimes;
    }

    /**
     * @param printtimes 
	 *            打印次数
     */
    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    /**
     * @return 表头自定义项1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01 
	 *            表头自定义项1
     */
    public void setField01(String field01) {
        this.field01 = field01 == null ? null : field01.trim();
    }

    /**
     * @return 表头自定义项2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02 
	 *            表头自定义项2
     */
    public void setField02(String field02) {
        this.field02 = field02 == null ? null : field02.trim();
    }

    /**
     * @return 表头自定义项3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03 
	 *            表头自定义项3
     */
    public void setField03(String field03) {
        this.field03 = field03 == null ? null : field03.trim();
    }

    /**
     * @return 表头自定义项4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04 
	 *            表头自定义项4
     */
    public void setField04(String field04) {
        this.field04 = field04 == null ? null : field04.trim();
    }

    /**
     * @return 表头自定义项5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05 
	 *            表头自定义项5
     */
    public void setField05(String field05) {
        this.field05 = field05 == null ? null : field05.trim();
    }

    /**
     * @return 表头自定义项6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06 
	 *            表头自定义项6
     */
    public void setField06(String field06) {
        this.field06 = field06 == null ? null : field06.trim();
    }

    /**
     * @return 表头自定义项7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07 
	 *            表头自定义项7
     */
    public void setField07(String field07) {
        this.field07 = field07 == null ? null : field07.trim();
    }

    /**
     * @return 表头自定义项8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08 
	 *            表头自定义项8
     */
    public void setField08(String field08) {
        this.field08 = field08 == null ? null : field08.trim();
    }

    /**
     * @return 付款类型1货款2折让3费用4其他
     */
    public String getPaytype() {
        return paytype;
    }

    /**
     * @param paytype 
	 *            付款类型1货款2折让3费用4其他
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
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
     * @return 对方经手人
     */
    public String getHandlerid() {
        return handlerid;
    }

    /**
     * @param handlerid 
	 *            对方经手人
     */
    public void setHandlerid(String handlerid) {
        this.handlerid = handlerid == null ? null : handlerid.trim();
    }

    /**
     * @return 对应发票号
     */
    public String getInvoiceno() {
        return invoiceno;
    }

    /**
     * @param invoiceno 
	 *            对应发票号
     */
    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    /**
     * @return 收款金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            收款金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return 核销金额
     */
    public BigDecimal getWriteoffamount() {
        return writeoffamount;
    }

    /**
     * @param writeoffamount 
	 *            核销金额
     */
    public void setWriteoffamount(BigDecimal writeoffamount) {
        this.writeoffamount = writeoffamount;
    }

    /**
     * @return 剩余金额
     */
    public BigDecimal getRemainderamount() {
        return remainderamount;
    }

    /**
     * @param remainderamount 
	 *            剩余金额
     */
    public void setRemainderamount(BigDecimal remainderamount) {
        this.remainderamount = remainderamount;
    }

    /**
     * @return 是否核销完成1核销完0未开始2核销中
     */
    public String getIswriteoff() {
        return iswriteoff;
    }

    /**
     * @param iswriteoff 
	 *            是否核销完成1核销完0未开始2核销中
     */
    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff == null ? null : iswriteoff.trim();
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

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getHandlername() {
		return handlername;
	}

	public void setHandlername(String handlername) {
		this.handlername = handlername;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getPrepay() {
		return prepay;
	}

	public void setPrepay(String prepay) {
		this.prepay = prepay;
	}

	public String getOaid() {
		return oaid;
	}

	public void setOaid(String oaid) {
		this.oaid = oaid;
	}

    public Integer getVouchertimes() {
        return vouchertimes;
    }

    public void setVouchertimes(Integer vouchertimes) {
        this.vouchertimes = vouchertimes;
    }
}
