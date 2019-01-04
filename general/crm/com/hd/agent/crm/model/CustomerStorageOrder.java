package com.hd.agent.crm.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CustomerStorageOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 手机提交的唯一校验码
     */
    private String keyid;

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
     * 客户
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
     * 客户分类
     */
    private String customersort;

    /**
     * 对方经手人
     */
    private String handlerid;

    /**
     * 来源单据编号
     */
    private String sourceid;

    /**
     * 来源类型0普通1手机2导入
     */
    private String sourcetype;

    /**
     * 销售区域
     */
    private String salesarea;

    /**
     * 销售部门
     */
    private String salesdept;

    /**
     * 销售员
     */
    private String salesuser;

    /**
     * 销售内勤人员编号
     */
    private String indooruserid;


    private String indoorusername;
    /**
     * 客户库存商品明细数据
     */
    private List<CustomerStorageOrderDetail> CustomerStorageOrderDetailList;

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
     *成本未税金额
     */
    private BigDecimal costtaxamount;
    /**
     * 零售金额
     */
    private BigDecimal taxamount;

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
     * @return 客户
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 总店客户
     */
    public String getPcustomerid() {
        return pcustomerid;
    }

    /**
     * @param pcustomerid 
	 *            总店客户
     */
    public void setPcustomerid(String pcustomerid) {
        this.pcustomerid = pcustomerid == null ? null : pcustomerid.trim();
    }

    /**
     * @return 客户分类
     */
    public String getCustomersort() {
        return customersort;
    }

    /**
     * @param customersort 
	 *            客户分类
     */
    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
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
     * @return 来源单据编号
     */
    public String getSourceid() {
        return sourceid;
    }

    /**
     * @param sourceid 
	 *            来源单据编号
     */
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid == null ? null : sourceid.trim();
    }

    /**
     * @return 来源类型0普通1手机2导入
     */
    public String getSourcetype() {
        return sourcetype;
    }

    /**
     * @param sourcetype 
	 *            来源类型0普通1手机2导入
     */
    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
    }

    /**
     * @return 销售区域
     */
    public String getSalesarea() {
        return salesarea;
    }

    /**
     * @param salesarea 
	 *            销售区域
     */
    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea == null ? null : salesarea.trim();
    }

    /**
     * @return 销售部门
     */
    public String getSalesdept() {
        return salesdept;
    }

    /**
     * @param salesdept 
	 *            销售部门
     */
    public void setSalesdept(String salesdept) {
        this.salesdept = salesdept == null ? null : salesdept.trim();
    }

    /**
     * @return 销售员
     */
    public String getSalesuser() {
        return salesuser;
    }

    /**
     * @param salesuser 
	 *            销售员
     */
    public void setSalesuser(String salesuser) {
        this.salesuser = salesuser == null ? null : salesuser.trim();
    }

    /**
     * @return 销售内勤人员编号
     */
    public String getIndooruserid() {
        return indooruserid;
    }

    /**
     * @param indooruserid 
	 *            销售内勤人员编号
     */
    public void setIndooruserid(String indooruserid) {
        this.indooruserid = indooruserid == null ? null : indooruserid.trim();
    }

    public String getIndoorusername() {
        return indoorusername;
    }

    public void setIndoorusername(String indoorusername) {
        this.indoorusername = indoorusername;
    }

    public String getField08() {
        return field08;
    }

    public void setField08(String field08) {
        this.field08 = field08;
    }

    public List<CustomerStorageOrderDetail> getCustomerStorageOrderDetailList() {
        return CustomerStorageOrderDetailList;
    }

    public void setCustomerStorageOrderDetailList(List<CustomerStorageOrderDetail> customerStorageOrderDetailList) {
        CustomerStorageOrderDetailList = customerStorageOrderDetailList;
    }

    public String getField01() {
        return field01;
    }

    public void setField01(String field01) {
        this.field01 = field01;
    }

    public String getField02() {
        return field02;
    }

    public void setField02(String field02) {
        this.field02 = field02;
    }

    public String getField03() {
        return field03;
    }

    public void setField03(String field03) {
        this.field03 = field03;
    }

    public String getField04() {
        return field04;
    }

    public void setField04(String field04) {
        this.field04 = field04;
    }

    public String getField05() {
        return field05;
    }

    public void setField05(String field05) {
        this.field05 = field05;
    }

    public String getField06() {
        return field06;
    }

    public void setField06(String field06) {
        this.field06 = field06;
    }

    public String getField07() {
        return field07;
    }

    public void setField07(String field07) {
        this.field07 = field07;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public BigDecimal getCosttaxamount() {
        return costtaxamount;
    }

    public void setCosttaxamount(BigDecimal costtaxamount) {
        this.costtaxamount = costtaxamount;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }
}