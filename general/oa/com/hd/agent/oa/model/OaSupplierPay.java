package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OaSupplierPay implements Serializable {
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
     * 供应商编号
     */
    private String supplierid;

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
     * 付款差额
     */
    private BigDecimal paymargin;

    /**
     * 到货金额
     */
    private BigDecimal arrivalamount;

    /**
     * 到货日期
     */
    private String arrivaldate;

    /**
     * 发票金额
     */
    private BigDecimal billamount;

    /**
     * 收回费用金额
     */
    private BigDecimal expenseamount;

    /**
     * 抽单金额（核销金额）
     */
    private BigDecimal writeoffamount;

    /**
     * 抽单日期（核销日期）
     */
    private String writeoffdate;

    /**
     * 订单金额
     */
    private BigDecimal orderamount;

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
     * 合计占用金额
     */
    private BigDecimal totalamount;

    /**
     * 是否预付1是0否
     */
    private String isprepay;

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
     * @return 付款差额
     */
    public BigDecimal getPaymargin() {
        return paymargin;
    }

    /**
     * @param paymargin 
	 *            付款差额
     */
    public void setPaymargin(BigDecimal paymargin) {
        this.paymargin = paymargin;
    }

    /**
     * @return 到货金额
     */
    public BigDecimal getArrivalamount() {
        return arrivalamount;
    }

    /**
     * @param arrivalamount 
	 *            到货金额
     */
    public void setArrivalamount(BigDecimal arrivalamount) {
        this.arrivalamount = arrivalamount;
    }

    /**
     * @return 到货日期
     */
    public String getArrivaldate() {
        return arrivaldate;
    }

    /**
     * @param arrivaldate 
	 *            到货日期
     */
    public void setArrivaldate(String arrivaldate) {
        this.arrivaldate = arrivaldate == null ? null : arrivaldate.trim();
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
     * @return 收回费用金额
     */
    public BigDecimal getExpenseamount() {
        return expenseamount;
    }

    /**
     * @param expenseamount 
	 *            收回费用金额
     */
    public void setExpenseamount(BigDecimal expenseamount) {
        this.expenseamount = expenseamount;
    }

    /**
     * @return 抽单金额（核销金额）
     */
    public BigDecimal getWriteoffamount() {
        return writeoffamount;
    }

    /**
     * @param writeoffamount 
	 *            抽单金额（核销金额）
     */
    public void setWriteoffamount(BigDecimal writeoffamount) {
        this.writeoffamount = writeoffamount;
    }

    /**
     * @return 抽单日期（核销日期）
     */
    public String getWriteoffdate() {
        return writeoffdate;
    }

    /**
     * @param writeoffdate 
	 *            抽单日期（核销日期）
     */
    public void setWriteoffdate(String writeoffdate) {
        this.writeoffdate = writeoffdate == null ? null : writeoffdate.trim();
    }

    /**
     * @return 订单金额
     */
    public BigDecimal getOrderamount() {
        return orderamount;
    }

    /**
     * @param orderamount 
	 *            订单金额
     */
    public void setOrderamount(BigDecimal orderamount) {
        this.orderamount = orderamount;
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
     * @return 合计占用金额
     */
    public BigDecimal getTotalamount() {
        return totalamount;
    }

    /**
     * @param totalamount 
	 *            合计占用金额
     */
    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    /**
     * @return 是否预付1是0否
     */
    public String getIsprepay() {
        return isprepay;
    }

    /**
     * @param isprepay 
	 *            是否预付1是0否
     */
    public void setIsprepay(String isprepay) {
        this.isprepay = isprepay == null ? null : isprepay.trim();
    }
}