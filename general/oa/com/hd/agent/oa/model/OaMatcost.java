package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OaMatcost implements Serializable {
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
     * 供应商编码
     */
    private String supplierid;

    /**
     * 供应商确认单号
     */
    private String supplierbillid;

    /**
     * 部门编码
     */
    private String deptid;

    /**
     * 付款银行
     */
    private String paybank;

    /**
     * 费用金额
     */
    private BigDecimal feeamount;

    /**
     * 归还日期
     */
    private String returndate;

    /**
     * 归还方式
     */
    private String returnway;

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
     * @return 供应商确认单号
     */
    public String getSupplierbillid() {
        return supplierbillid;
    }

    /**
     * @param supplierbillid 
	 *            供应商确认单号
     */
    public void setSupplierbillid(String supplierbillid) {
        this.supplierbillid = supplierbillid == null ? null : supplierbillid.trim();
    }

    /**
     * @return 部门编码
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            部门编码
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 付款银行
     */
    public String getPaybank() {
        return paybank;
    }

    /**
     * @param paybank 
	 *            付款银行
     */
    public void setPaybank(String paybank) {
        this.paybank = paybank == null ? null : paybank.trim();
    }

    /**
     * @return 费用金额
     */
    public BigDecimal getFeeamount() {
        return feeamount;
    }

    /**
     * @param feeamount 
	 *            费用金额
     */
    public void setFeeamount(BigDecimal feeamount) {
        this.feeamount = feeamount;
    }

    /**
     * @return 归还日期
     */
    public String getReturndate() {
        return returndate;
    }

    /**
     * @param returndate 
	 *            归还日期
     */
    public void setReturndate(String returndate) {
        this.returndate = returndate == null ? null : returndate.trim();
    }

    /**
     * @return 归还方式
     */
    public String getReturnway() {
        return returnway;
    }

    /**
     * @param returnway 
	 *            归还方式
     */
    public void setReturnway(String returnway) {
        this.returnway = returnway == null ? null : returnway.trim();
    }
}