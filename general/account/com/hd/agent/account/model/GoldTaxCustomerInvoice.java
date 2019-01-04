package com.hd.agent.account.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GoldTaxCustomerInvoice implements Serializable {
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
     * 发票客户
     */
    private String customerid;

    /**
     * 客户名称
     */
    private String customername;

    /**
     * 发票客户名称
     */
    private String invoicecustomername;

    /**
     * 发票类型1增值税2普通3其他
     */
    private String invoicetype;

    /**
     * 发票号
     */
    private String invoiceno;

    /**
     * 发票代码
     */
    private String invoicecode;

    /**
     * 购方税号
     */
    private String customertaxno;

    /**
     * 购方地址
     */
    private String customeraddr;

    /**
     * 购方电话
     */
    private String customerphone;
    /**
     * 购方银行卡号
     */
    private String customercardno;

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
     * 含税总金额
     */
    private BigDecimal taxamount;

    /**
     * 无税总金额
     */
    private BigDecimal notaxamount;

    /**
     * 未税金税
     */
    private BigDecimal tax;

    /**
     * 导出金税次数
     */
    private Integer jxexporttimes;

    /**
     * 金税导出者编号
     */
    private String jxexportuserid;

    /**
     * 金税导出者名称
     */
    private String jxexportusername;

    /**
     * 金税导出最新时间
     */
    private Date jxexportdatetime;

    private List<GoldTaxCustomerInvoiceDetail> detailList;

    private GoldTaxCustomerInvoiceDetail detailSum;

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
     * @return 发票客户
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid
     *            发票客户
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 客户名称
     */
    public String getCustomername() {
        return customername;
    }

    /**
     * @param customername
     *            客户名称
     */
    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    /**
     * @return 发票客户名称
     */
    public String getInvoicecustomername() {
        return invoicecustomername;
    }

    /**
     * @param invoicecustomername
     *            发票客户名称
     */
    public void setInvoicecustomername(String invoicecustomername) {
        this.invoicecustomername = invoicecustomername == null ? null : invoicecustomername.trim();
    }

    /**
     * @return 发票类型1增值税2普通3其他
     */
    public String getInvoicetype() {
        return invoicetype;
    }

    /**
     * @param invoicetype
     *            发票类型1增值税2普通3其他
     */
    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype == null ? null : invoicetype.trim();
    }

    /**
     * @return 发票号
     */
    public String getInvoiceno() {
        return invoiceno;
    }

    /**
     * @param invoiceno
     *            发票号
     */
    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    /**
     * @return 发票代码
     */
    public String getInvoicecode() {
        return invoicecode;
    }

    /**
     * @param invoicecode
     *            发票代码
     */
    public void setInvoicecode(String invoicecode) {
        this.invoicecode = invoicecode == null ? null : invoicecode.trim();
    }

    /**
     * @return 购方税号
     */
    public String getCustomertaxno() {
        return customertaxno;
    }

    /**
     * @param customertaxno
     *            购方税号
     */
    public void setCustomertaxno(String customertaxno) {
        this.customertaxno = customertaxno == null ? null : customertaxno.trim();
    }

    /**
     * @return 购方地址
     */
    public String getCustomeraddr() {
        return customeraddr;
    }

    /**
     * @param customeraddr
     *            购方地址
     */
    public void setCustomeraddr(String customeraddr) {
        this.customeraddr = customeraddr == null ? null : customeraddr.trim();
    }

    /**
     * @return 购方电话
     */
    public String getCustomerphone() {
        return customerphone;
    }

    /**
     * @param customerphone
     *            购方电话
     */
    public void setCustomerphone(String customerphone) {
        this.customerphone = customerphone == null ? null : customerphone.trim();
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
     * @return 含税总金额
     */
    public BigDecimal getTaxamount() {
        return taxamount;
    }

    /**
     * @param taxamount
     *            含税总金额
     */
    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    /**
     * @return 无税总金额
     */
    public BigDecimal getNotaxamount() {
        return notaxamount;
    }

    /**
     * @param notaxamount
     *            无税总金额
     */
    public void setNotaxamount(BigDecimal notaxamount) {
        this.notaxamount = notaxamount;
    }

    /**
     * @return 未税金税
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * @param tax
     *            未税金税
     */
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    /**
     * @return 导出金税次数
     */
    public Integer getJxexporttimes() {
        return jxexporttimes;
    }

    /**
     * @param jxexporttimes
     *            导出金税次数
     */
    public void setJxexporttimes(Integer jxexporttimes) {
        this.jxexporttimes = jxexporttimes;
    }

    /**
     * @return 金税导出者编号
     */
    public String getJxexportuserid() {
        return jxexportuserid;
    }

    /**
     * @param jxexportuserid
     *            金税导出者编号
     */
    public void setJxexportuserid(String jxexportuserid) {
        this.jxexportuserid = jxexportuserid == null ? null : jxexportuserid.trim();
    }

    /**
     * @return 金税导出者名称
     */
    public String getJxexportusername() {
        return jxexportusername;
    }

    /**
     * @param jxexportusername
     *            金税导出者名称
     */
    public void setJxexportusername(String jxexportusername) {
        this.jxexportusername = jxexportusername == null ? null : jxexportusername.trim();
    }

    /**
     * @return 金税导出最新时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getJxexportdatetime() {
        return jxexportdatetime;
    }

    /**
     * @param jxexportdatetime
     *            金税导出最新时间
     */
    public void setJxexportdatetime(Date jxexportdatetime) {
        this.jxexportdatetime = jxexportdatetime;
    }

    public List<GoldTaxCustomerInvoiceDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<GoldTaxCustomerInvoiceDetail> detailList) {
        this.detailList = detailList;
    }

    public GoldTaxCustomerInvoiceDetail getDetailSum() {
        return detailSum;
    }

    public void setDetailSum(GoldTaxCustomerInvoiceDetail detailSum) {
        this.detailSum = detailSum;
    }

    public String getCustomercardno() {
        return customercardno;
    }

    public void setCustomercardno(String customercardno) {
        this.customercardno = customercardno == null ? null : customercardno.trim();
    }
}