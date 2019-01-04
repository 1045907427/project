package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.Customer;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderGoods implements Serializable {
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
     * 状态名称
     */
    private String statusname;

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
     * 打印时间
     */
    private Date printdatetime;

    /**
     * 客户
     */
    private String customerid;

    private Customer customerInfo;

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
     * 销售区域
     */
    private String salesarea;

    /**
     * 销售部门
     */
    private String salesdept;

    /**
     * 销售部门名称
     */
    private String salesdeptname;

    /**
     * 销售员
     */
    private String salesuser;

    /**
     * 销售员名称
     */
    private String salesusername;

    /**
     * 结算方式
     */
    private String settletype;

    /**
     * 结算方式名称
     */
    private String settletypename;

    /**
     * 支付方式
     */
    private String paytype;

    /**
     * 仓库编号
     */
    private String storageid;

    /**
     * 是否被参照0未参照1已参照
     */
    private String isrefer;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 销售内勤人员编号
     */
    private String indooruserid;

    /**
     * 销售内勤人员名称
     */
    private String indoorusername;

    /**
     * 要货时间
     */
    private Date demandtime;

    /**
     * 配货单打印次数
     */
    private Integer phprinttimes;

    /**
     * 配送单打印时间
     */
    private Date phprintdatetime;

    private String field01;

    private String field02;

    private String field03;

    private String field04;

    private String branduser;

    private String brandusername;

    /**
     * 地址
     */
    private String address;

    /**
     * 订单总箱重
     */
    private BigDecimal totalboxweight;
    /**
     * 订单总箱体积
     */
    private BigDecimal totalboxvolume;

    private List orderDetailList;

    /**
     * 缺货商品明细列表
     */
    private List<OrderGoodsDetail> lackList;

    /**
     * 生成订单状态
     */
    private String orderstatus;

    /**
     * 已生成含税订单金额
     */
    private BigDecimal ordertaxamount;

    /**
     * 已生成未税订单金额
     */
    private BigDecimal ordernotaxamount;

    /**
     * 未生成含税订单金额
     */
    private BigDecimal notordertaxamount;

    /**
     * 未生成未税订单金额
     */
    private BigDecimal notordernotaxamount;

    private String handlername;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 提货券编号
     */
    private String ladingbill;

    /**
     * 品牌编码
     */
    private String brandid;

    /**
     * 品牌编码
     */
    private String brandname;

    /**
     * 已生成订单数量
     */
    private String orderunitnum;

    /**
     * 未生成订单数量
     */
    private String notorderunitnum;

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
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 打印时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPrintdatetime() {
        return printdatetime;
    }

    /**
     * @param printdatetime 
	 *            打印时间
     */
    public void setPrintdatetime(Date printdatetime) {
        this.printdatetime = printdatetime;
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
     * @return 结算方式
     */
    public String getSettletype() {
        return settletype;
    }

    /**
     * @param settletype 
	 *            结算方式
     */
    public void setSettletype(String settletype) {
        this.settletype = settletype == null ? null : settletype.trim();
    }

    /**
     * @return 支付方式
     */
    public String getPaytype() {
        return paytype;
    }

    /**
     * @param paytype 
	 *            支付方式
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    /**
     * @return 仓库编号
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            仓库编号
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 是否被参照0未参照1已参照
     */
    public String getIsrefer() {
        return isrefer;
    }

    /**
     * @param isrefer 
	 *            是否被参照0未参照1已参照
     */
    public void setIsrefer(String isrefer) {
        this.isrefer = isrefer == null ? null : isrefer.trim();
    }

    /**
     * @return 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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

    /**
     * @return 要货时间
     */
    public Date getDemandtime() {
        return demandtime;
    }

    /**
     * @param demandtime 
	 *            要货时间
     */
    public void setDemandtime(Date demandtime) {
        this.demandtime = demandtime;
    }

    /**
     * @return 配货单打印次数
     */
    public Integer getPhprinttimes() {
        return phprinttimes;
    }

    /**
     * @param phprinttimes 
	 *            配货单打印次数
     */
    public void setPhprinttimes(Integer phprinttimes) {
        this.phprinttimes = phprinttimes;
    }

    /**
     * @return 配送单打印时间
     */
    public Date getPhprintdatetime() {
        return phprintdatetime;
    }

    /**
     * @param phprintdatetime 
	 *            配送单打印时间
     */
    public void setPhprintdatetime(Date phprintdatetime) {
        this.phprintdatetime = phprintdatetime;
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

    public BigDecimal getTotalboxweight() {
        return totalboxweight;
    }

    public void setTotalboxweight(BigDecimal totalboxweight) {
        this.totalboxweight = totalboxweight;
    }

    public BigDecimal getTotalboxvolume() {
        return totalboxvolume;
    }

    public void setTotalboxvolume(BigDecimal totalboxvolume) {
        this.totalboxvolume = totalboxvolume;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getBranduser() {
        return branduser;
    }

    public void setBranduser(String branduser) {
        this.branduser = branduser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrandusername() {
        return brandusername;
    }

    public void setBrandusername(String brandusername) {
        this.brandusername = brandusername;
    }

    public String getIndoorusername() {
        return indoorusername;
    }

    public void setIndoorusername(String indoorusername) {
        this.indoorusername = indoorusername;
    }

    public List getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public List<OrderGoodsDetail> getLackList() {
        return lackList;
    }

    public void setLackList(List<OrderGoodsDetail> lackList) {
        this.lackList = lackList;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public BigDecimal getOrdertaxamount() {
        return ordertaxamount;
    }

    public void setOrdertaxamount(BigDecimal ordertaxamount) {
        this.ordertaxamount = ordertaxamount;
    }

    public BigDecimal getOrdernotaxamount() {
        return ordernotaxamount;
    }

    public void setOrdernotaxamount(BigDecimal ordernotaxamount) {
        this.ordernotaxamount = ordernotaxamount;
    }

    public BigDecimal getNotordertaxamount() {
        return notordertaxamount;
    }

    public void setNotordertaxamount(BigDecimal notordertaxamount) {
        this.notordertaxamount = notordertaxamount;
    }

    public BigDecimal getNotordernotaxamount() {
        return notordernotaxamount;
    }

    public void setNotordernotaxamount(BigDecimal notordernotaxamount) {
        this.notordernotaxamount = notordernotaxamount;
    }

    public String getSalesdeptname() {
        return salesdeptname;
    }

    public void setSalesdeptname(String salesdeptname) {
        this.salesdeptname = salesdeptname;
    }

    public String getSalesusername() {
        return salesusername;
    }

    public void setSalesusername(String salesusername) {
        this.salesusername = salesusername;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getSettletypename() {
        return settletypename;
    }

    public void setSettletypename(String settletypename) {
        this.settletypename = settletypename;
    }

    public String getHandlername() {
        return handlername;
    }

    public void setHandlername(String handlername) {
        this.handlername = handlername;
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

    public String getLadingbill() {
        return ladingbill;
    }

    public void setLadingbill(String ladingbill) {
        this.ladingbill = ladingbill;
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

    public String getOrderunitnum() {
        return orderunitnum;
    }

    public void setOrderunitnum(String orderunitnum) {
        this.orderunitnum = orderunitnum;
    }

    public String getNotorderunitnum() {
        return notorderunitnum;
    }

    public void setNotorderunitnum(String notorderunitnum) {
        this.notorderunitnum = notorderunitnum;
    }
}
