package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OaCustomerPay implements Serializable {
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
     * 关联OA编号（通路单OA编号）
     */
    private String relateoaid;

    /**
     * 供应商编号
     */
    private String supplierid;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 所属部门编号
     */
    private String deptid;

    /**
     * 收款户名(客户名称)
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
    private String expensesort;

    /**
     * 发票种类
     */
    private String billtype;

    /**
     * 发票金额
     */
    private BigDecimal billamount;

    /**
     * 到票日期
     */
    private String billdate;

    /**
     * 分摊方式
     */
    private String sharetype;

    /**
     * 分摊开始日期
     */
    private String sharebegindate;

    /**
     * 分摊结束日期
     */
    private String shareenddate;

    /**
     * 客户编码2
     */
    private String customerid2;

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
     * @return 关联OA编号（通路单OA编号）
     */
    public String getRelateoaid() {
        return relateoaid;
    }

    /**
     * @param relateoaid 
	 *            关联OA编号（通路单OA编号）
     */
    public void setRelateoaid(String relateoaid) {
        this.relateoaid = relateoaid == null ? null : relateoaid.trim();
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
     * @return 所属部门编号
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            所属部门编号
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 收款户名(客户名称)
     */
    public String getCollectionname() {
        return collectionname;
    }

    /**
     * @param collectionname 
	 *            收款户名(客户名称)
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
    public String getExpensesort() {
        return expensesort;
    }

    /**
     * @param expensesort 
	 *            费用科目
     */
    public void setExpensesort(String expensesort) {
        this.expensesort = expensesort == null ? null : expensesort.trim();
    }

    /**
     * @return 发票种类
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            发票种类
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
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
     * @return 到票日期
     */
    public String getBilldate() {
        return billdate;
    }

    /**
     * @param billdate 
	 *            到票日期
     */
    public void setBilldate(String billdate) {
        this.billdate = billdate == null ? null : billdate.trim();
    }

    /**
     * @return 分摊方式
     */
    public String getSharetype() {
        return sharetype;
    }

    /**
     * @param sharetype 
	 *            分摊方式
     */
    public void setSharetype(String sharetype) {
        this.sharetype = sharetype == null ? null : sharetype.trim();
    }

    /**
     * @return 分摊开始日期
     */
    public String getSharebegindate() {
        return sharebegindate;
    }

    /**
     * @param sharebegindate 
	 *            分摊开始日期
     */
    public void setSharebegindate(String sharebegindate) {
        this.sharebegindate = sharebegindate == null ? null : sharebegindate.trim();
    }

    /**
     * @return 分摊结束日期
     */
    public String getShareenddate() {
        return shareenddate;
    }

    /**
     * @param shareenddate 
	 *            分摊结束日期
     */
    public void setShareenddate(String shareenddate) {
        this.shareenddate = shareenddate == null ? null : shareenddate.trim();
    }

    public String getCustomerid2() {
        return customerid2;
    }

    public void setCustomerid2(String customerid2) {
        this.customerid2 = customerid2;
    }
}