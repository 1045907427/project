package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 通路单，垫付
 * @author zhanghonghui
 * 时间 2014-2-26
 */
public class MatcostsInput implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2537807562822318383L;

	/**
     * 编号
     */
    private String id;
    /**
     * OA 编号
     */
    private String oaid;
    /**
     * 银行档案编号
     */
    private String bankid;
    /**
     * 银行名称
     */
    private String bankname;
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
    private String suppliername;

    /**
     * 供应商所属部门编码
     */
    private String supplierdeptid;
    
    /**
     * 供应商所属部门名称
     */
    private String supplierdeptname;

    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户分类
     */
    private String customersort;
    /**
     * 客户分类名称
     */
    private String customersortname;
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 科目编码
     */
    private String subjectid;
    
    /**
     * 科目名称
     */
    private String subjectname;

    /**
     * 工厂投入
     */
    private BigDecimal factoryamount;

    /**
     * 电脑折让
     */
    private BigDecimal htcompdiscount;

    /**
     * 支付金额
     */
    private BigDecimal htpayamount;

    /**
     * 转入分公司
     */
    private BigDecimal branchaccount;

    /**
     * 收回方式
     */
    private String reimbursetype;
    /**
     * 收回方式
     */
    private String reimbursetypename;

    /**
     * 收回金额
     */
    private BigDecimal reimburseamount;

    /**
     * 代垫金额
     */
    private BigDecimal actingmatamount;

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
    private Date addtime;
    /**
     * 品牌
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 是否核销
     */
    public String iswriteoff;
    /**
     * 核销日期
     */
    public String writeoffdate;
    /**
     * 核销人编号
     */
    public String writeoffer;
    /**
     * 核销人
     */
    public String writeoffername;
    /**
     * 数据来源，0添加，1导入,2流程,3流程（驳回）
     */
    public String sourcefrome;
    /**
     * 代垫类型1代垫2收回
     */
    public String billtype;
    /**
     * 已经核销金额
     */
    public BigDecimal writeoffamount;
    /**
     * 剩余代垫未核销金额
     */
    public BigDecimal remainderamount;
    /**
     * 费用
     */
    private BigDecimal expense; 
    /**
     * 红冲标志：0普通单据，1红冲标志,2被红冲的单据
     */
    private String hcflag;
    /**
     * 红冲关联代垫
     */
    private String hcreferid;
    /**
     * 被红冲次数合计
     */
    private Integer hcrefcount;
    /**
     * 支付日期
     */
    public String paydate;
    /**
     * 收回日期
     */
    public String takebackdate;
    /**
     * 主单位编号
     */
    private String unitid;
    /**
     * 主单位名称
     */
    private String unitname;
    /**
     * 数量
     */
    private BigDecimal unitnum;
    /**
     * 含税单价
     */
    private BigDecimal taxprice;

    /**
     * 代垫打印次数
     */
    public int printtimes;
    /**
     * 收回打印次数
     */
    public int shprinttimes;


    /**
     * 收回科目编码
     */
    private String shsubjectid;
    
    /**
     * 收回科目名称
     */
    private String shsubjectname;
    /**
     * 经办人编号
     */
    private String transactorid;
    /**
     * 经办人名称
     */
    private String transactorname;

    /**
     * 应收日期
     */
    private String duefromdate;

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
     * @return 供应商所属部门编码
     */
    public String getSupplierdeptid() {
        return supplierdeptid;
    }

    /**
     * @param supplierdeptid 
	 *            供应商所属部门编码
     */
    public void setSupplierdeptid(String supplierdeptid) {
        this.supplierdeptid = supplierdeptid == null ? null : supplierdeptid.trim();
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
     * @return 科目编码
     */
    public String getSubjectid() {
        return subjectid;
    }

    /**
     * @param subjectid 
	 *            科目编码
     */
    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid == null ? null : subjectid.trim();
    }

    /**
     * @return 工厂投入
     */
    public BigDecimal getFactoryamount() {
        return factoryamount;
    }

    /**
     * @param factoryamount 
	 *            工厂投入
     */
    public void setFactoryamount(BigDecimal factoryamount) {
        this.factoryamount = factoryamount;
    }

    /**
     * @return 电脑折让
     */
    public BigDecimal getHtcompdiscount() {
        return htcompdiscount;
    }

    /**
     * @param htcompdiscount 
	 *            电脑折让
     */
    public void setHtcompdiscount(BigDecimal htcompdiscount) {
        this.htcompdiscount = htcompdiscount;
    }

    /**
     * @return 支付金额
     */
    public BigDecimal getHtpayamount() {
        return htpayamount;
    }

    /**
     * @param htpayamount 
	 *            支付金额
     */
    public void setHtpayamount(BigDecimal htpayamount) {
        this.htpayamount = htpayamount;
    }

    /**
     * @return 转入分公司
     */
    public BigDecimal getBranchaccount() {
        return branchaccount;
    }

    /**
     * @param branchaccount 
	 *            转入分公司
     */
    public void setBranchaccount(BigDecimal branchaccount) {
        this.branchaccount = branchaccount;
    }

    /**
     * @return 收回方式
     */
    public String getReimbursetype() {
        return reimbursetype;
    }

    /**
     * @param reimbursetype 
	 *            收回方式
     */
    public void setReimbursetype(String reimbursetype) {
        this.reimbursetype = reimbursetype == null ? null : reimbursetype.trim();
    }

    /**
     * @return 收回金额
     */
    public BigDecimal getReimburseamount() {
        return reimburseamount;
    }

    /**
     * @param reimburseamount 
	 *            收回金额
     */
    public void setReimburseamount(BigDecimal reimburseamount) {
        this.reimburseamount = reimburseamount;
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
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单日期
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
        this.suppliername = suppliername == null ? null : suppliername.trim();
	}

	public String getSupplierdeptname() {
		return supplierdeptname;
	}

	public void setSupplierdeptname(String supplierdeptname) {
        this.supplierdeptname = supplierdeptname == null ? null : supplierdeptname.trim();
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
        this.subjectname = subjectname == null ? null : subjectname.trim();
	}

	public String getReimbursetypename() {
		return reimbursetypename;
	}

	public void setReimbursetypename(String reimbursetypename) {
        this.reimbursetypename = reimbursetypename == null ? null : reimbursetypename.trim();
	}

	public String getOaid() {
		return oaid;
	}

	public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
	}

	public String getIswriteoff() {
		return iswriteoff;
	}

	public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff == null ? null : iswriteoff.trim();
	}

	public String getWriteoffdate() {
		return writeoffdate;
	}

	public void setWriteoffdate(String writeoffdate) {
		this.writeoffdate = writeoffdate;
	}

	public String getWriteoffer() {
		return writeoffer;
	}

	public void setWriteoffer(String writeoffer) {
        this.writeoffer = writeoffer == null ? null : writeoffer.trim();
	}

	public String getWriteoffername() {
		return writeoffername;
	}

	public void setWriteoffername(String writeoffername) {
        this.writeoffername = writeoffername == null ? null : writeoffername.trim();
	}

	public String getSourcefrome() {
		return sourcefrome;
	}

	public void setSourcefrome(String sourcefrome) {
        this.sourcefrome = sourcefrome == null ? null : sourcefrome.trim();
	}

	/**
	 * 代垫类型1代垫2收回
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年6月1日
	 */
	public String getBilltype() {
		return billtype;
	}
	/**
	 * 代垫类型1代垫2收回
	 * @param billtype
	 * @author zhanghonghui 
	 * @date 2015年6月1日
	 */
	public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
	}

	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}

	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}

	public BigDecimal getRemainderamount() {
		return remainderamount;
	}

	public void setRemainderamount(BigDecimal remainderamount) {
		this.remainderamount = remainderamount;
	}

	public String getBankid() {
		return bankid;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankid(String bankid) {
        this.bankid = bankid == null ? null : bankid.trim();
	}

	public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
	}

	public BigDecimal getExpense() {
		return expense;
	}

	public void setExpense(BigDecimal expense) {
		this.expense = expense;
	}

	public String getHcflag() {
		return hcflag;
	}

	public void setHcflag(String hcflag) {
        this.hcflag = hcflag == null ? null : hcflag.trim();
	}

	public String getHcreferid() {
		return hcreferid;
	}

	public void setHcreferid(String hcreferid) {
        this.hcreferid = hcreferid == null ? null : hcreferid.trim();
	}

	public Integer getHcrefcount() {
		return hcrefcount;
	}

	public void setHcrefcount(Integer hcrefcount) {
		this.hcrefcount = hcrefcount;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
        this.paydate = paydate == null ? null : paydate.trim();
	}

	public String getTakebackdate() {
		return takebackdate;
	}

	public void setTakebackdate(String takebackdate) {
        this.takebackdate = takebackdate == null ? null : takebackdate.trim();
	}

	public int getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(int printtimes) {
		this.printtimes = printtimes;
	}

	public int getShprinttimes() {
		return shprinttimes;
	}

	public void setShprinttimes(int shprinttimes) {
		this.shprinttimes = shprinttimes;
	}
	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
	}

	public BigDecimal getUnitnum() {
		return unitnum;
	}

	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}

	public BigDecimal getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
	}

	public String getShsubjectid() {
		return shsubjectid;
	}

	public void setShsubjectid(String shsubjectid) {
        this.shsubjectid = shsubjectid == null ? null : shsubjectid.trim();
	}

	public String getShsubjectname() {
		return shsubjectname;
	}

	public void setShsubjectname(String shsubjectname) {
        this.shsubjectname = shsubjectname == null ? null : shsubjectname.trim();
	}

    public String getTransactorid() {
        return transactorid;
    }

    public void setTransactorid(String transactorid) {
        this.transactorid = transactorid == null ? null : transactorid.trim();
    }

    public String getTransactorname() {
        return transactorname;
    }

    public void setTransactorname(String transactorname) {
        this.transactorname = transactorname == null ? null : transactorname.trim();
    }

    public String getCustomersort() {
        return customersort;
    }

    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
    }

    public String getCustomersortname() {
        return customersortname;
    }

    public void setCustomersortname(String customersortname) {
        this.customersortname = customersortname == null ? null : customersortname.trim();
    }

    public String getDuefromdate() {
        return duefromdate;
    }

    public void setDuefromdate(String duefromdate) {
        this.duefromdate = duefromdate;
    }

    public Integer getVouchertimes() {
        return vouchertimes;
    }

    public void setVouchertimes(Integer vouchertimes) {
        this.vouchertimes = vouchertimes;
    }
}