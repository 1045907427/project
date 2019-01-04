package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 日常费用支付单
 * @author chenwei
 */
public class OaDailyPay implements Serializable {
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
     * 打印次数
     */
    private Integer printtimes;

    /**
     * oa编号
     */
    private String oaid;

    /**
     * 收款户名
     */
    private String collectionname;

    /**
     * 开户银行名称
     */
    private String collectionbank;

    /**
     * 开户银行账号
     */
    private String collectionbankno;

    /**
     * 付款银行（银行档案关联）
     */
    private String paybank;

    /**
     * 付款日期
     */
    private String paydate;

    /**
     * 付款金额
     */
    private BigDecimal payamount;

    /**
     * 大写金额
     */
    private String upperpayamount;

    /**
     * 付款用途
     */
    private String payuse;

    /**
     * 费用科目
     */
    private String costsort;

    /**
     * 发票金额
     */
    private BigDecimal billamount;

    /**
     * 报销人编号
     */
    private String applyuserid;

    /**
     * 报销人名称
     */
    private String applyusername;

    /**
     * 报销时间
     */
    private Date applytime;

    /**
     * 报销类型
     */
    private String applytype;

    /**
     * 报销人所属部门
     */
    private String applydeptid;

    /**
     * 分摊方式：1直接分摊2内部分摊
     */
    private String sharetype;

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
     * @return oa编号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * @param oaid 
	 *            oa编号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
    }

    /**
     * @return 收款户名
     */
    public String getCollectionname() {
        return collectionname;
    }

    /**
     * @param collectionname 
	 *            收款户名
     */
    public void setCollectionname(String collectionname) {
        this.collectionname = collectionname == null ? null : collectionname.trim();
    }

    /**
     * @return 开户银行名称
     */
    public String getCollectionbank() {
        return collectionbank;
    }

    /**
     * @param collectionbank 
	 *            开户银行名称
     */
    public void setCollectionbank(String collectionbank) {
        this.collectionbank = collectionbank == null ? null : collectionbank.trim();
    }

    /**
     * @return 开户银行账号
     */
    public String getCollectionbankno() {
        return collectionbankno;
    }

    /**
     * @param collectionbankno 
	 *            开户银行账号
     */
    public void setCollectionbankno(String collectionbankno) {
        this.collectionbankno = collectionbankno == null ? null : collectionbankno.trim();
    }

    /**
     * @return 付款银行（银行档案关联）
     */
    public String getPaybank() {
        return paybank;
    }

    /**
     * @param paybank 
	 *            付款银行（银行档案关联）
     */
    public void setPaybank(String paybank) {
        this.paybank = paybank == null ? null : paybank.trim();
    }

    /**
     * @return 付款日期
     */
    public String getPaydate() {
        return paydate;
    }

    /**
     * @param paydate 
	 *            付款日期
     */
    public void setPaydate(String paydate) {
        this.paydate = paydate == null ? null : paydate.trim();
    }

    /**
     * @return 付款金额
     */
    public BigDecimal getPayamount() {
        return payamount;
    }

    /**
     * @param payamount 
	 *            付款金额
     */
    public void setPayamount(BigDecimal payamount) {
        this.payamount = payamount;
    }

    /**
     * @return 大写金额
     */
    public String getUpperpayamount() {
        return upperpayamount;
    }

    /**
     * @param upperpayamount 
	 *            大写金额
     */
    public void setUpperpayamount(String upperpayamount) {
        this.upperpayamount = upperpayamount == null ? null : upperpayamount.trim();
    }

    /**
     * @return 付款用途
     */
    public String getPayuse() {
        return payuse;
    }

    /**
     * @param payuse 
	 *            付款用途
     */
    public void setPayuse(String payuse) {
        this.payuse = payuse == null ? null : payuse.trim();
    }

    /**
     * @return 费用科目
     */
    public String getCostsort() {
        return costsort;
    }

    /**
     * @param costsort 
	 *            费用科目
     */
    public void setCostsort(String costsort) {
        this.costsort = costsort == null ? null : costsort.trim();
    }

    /**
     * @return 发票金额
     */
    public BigDecimal getBillamount() {
        return billamount;
    }

    /**
     * @param billamount 
	 *            发票金额
     */
    public void setBillamount(BigDecimal billamount) {
        this.billamount = billamount;
    }

    /**
     * @return 报销人编号
     */
    public String getApplyuserid() {
        return applyuserid;
    }

    /**
     * @param applyuserid 
	 *            报销人编号
     */
    public void setApplyuserid(String applyuserid) {
        this.applyuserid = applyuserid == null ? null : applyuserid.trim();
    }

    /**
     * @return 报销人名称
     */
    public String getApplyusername() {
        return applyusername;
    }

    /**
     * @param applyusername 
	 *            报销人名称
     */
    public void setApplyusername(String applyusername) {
        this.applyusername = applyusername == null ? null : applyusername.trim();
    }

    /**
     * @return 报销时间
     */
    public Date getApplytime() {
        return applytime;
    }

    /**
     * @param applytime 
	 *            报销时间
     */
    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }

    /**
     * @return 报销类型
     */
    public String getApplytype() {
        return applytype;
    }

    /**
     * @param applytype 
	 *            报销类型
     */
    public void setApplytype(String applytype) {
        this.applytype = applytype == null ? null : applytype.trim();
    }

    /**
     * @return 报销人所属部门
     */
    public String getApplydeptid() {
        return applydeptid;
    }

    /**
     * @param applydeptid 
	 *            报销人所属部门
     */
    public void setApplydeptid(String applydeptid) {
        this.applydeptid = applydeptid == null ? null : applydeptid.trim();
    }

    /**
     * @return 分摊方式：1直接分摊2内部分摊
     */
    public String getSharetype() {
        return sharetype;
    }

    /**
     * @param sharetype 
	 *            分摊方式：1直接分摊2内部分摊
     */
    public void setSharetype(String sharetype) {
        this.sharetype = sharetype == null ? null : sharetype.trim();
    }
}