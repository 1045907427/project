package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.Customer;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 销售订单
 * @author zhengziyong
 *
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 版本序号
     */
    private String vid;
    /**
     * 版本号
     */
    private int version;
    /**
     * 编号
     */
    private String id;
    
    /**
     * 修改时记录旧ID
     */
    private String oldid;

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
     * 配货单打印次数
     */
    private Integer phprinttimes;
    
    /**
     * 客户
     */
    private Customer customerInfo;

    /**
     * 客户
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 客户地址
     */
    private String address;
    /**
     * 总店
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
     * 对方经手人名称
     */
    private String handlername;
    /**
     * 来源单据编号
     */
    private String sourceid;
    /**
     * 来源类型1手机订单0普通订单2车销订单3客户订单
     */
    private String sourcetype;
    /**
     * 是否以商品编码排序1是0否
     */
    private String isgoodsseq;
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
     * 客户业务员
     */
    private String salesuser;
    /**
     * 客户业务员名称
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
     * 是否被参照0未参照1已参照
     */
    private String isrefer;
    /**
     *	销售内勤人员编号
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
    private String branduser;
    /**
     * 品牌业务员名称
     */
    private String brandusername;
    /**
     * 订单商品明细
     */
    private OrderDetail orderDetail;
    
    /**
     * 订单商品明细列表
     */
    private List<OrderDetail> orderDetailList;
    /**
     * 缺货商品明细列表
     */
    private List<OrderDetail> lackList;

    /**
     * 订单总箱重
     */
    private BigDecimal totalboxweight;
    /**
     * 订单总箱体积
     */
    private BigDecimal totalboxvolume;

    private String detailremark;

    /**
     * 打印时间
     */
    private Date printdatetime;
    /**
     * 配货单打印时间
     */
    private Date phprintdatetime;

    /**
     * 销售类型
     */
    private String salestype;

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
        this.id = id;
    }

    /**
     * 
     * @return 修改时记录旧ID
     * @author zhengziyong 
     * @date May 10, 2013
     */
    public String getOldid() {
		return oldid;
	}

    /**
     * 
     * @param oldid 修改时记录旧ID
     * @author zhengziyong 
     * @date May 10, 2013
     */
	public void setOldid(String oldid) {
		this.oldid = oldid;
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
        this.businessdate = businessdate;
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
        this.status = status;
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
        this.remark = remark;
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
        this.adduserid = adduserid;
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
        this.addusername = addusername;
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
        this.adddeptid = adddeptid;
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
        this.adddeptname = adddeptname;
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
        this.modifyuserid = modifyuserid;
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
        this.modifyusername = modifyusername;
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
        this.audituserid = audituserid;
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
        this.auditusername = auditusername;
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
        this.stopuserid = stopuserid;
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
        this.stopusername = stopusername;
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

    public Customer getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(Customer customerInfo) {
		this.customerInfo = customerInfo;
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
        this.customerid = customerid;
    }

    public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
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
        this.handlerid = handlerid;
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
        this.salesdept = salesdept;
    }

    /**
     * @return 客户业务员
     */
    public String getSalesuser() {
        return salesuser;
    }

    /**
     * @param salesuser 
	 *            客户业务员
     */
    public void setSalesuser(String salesuser) {
        this.salesuser = salesuser;
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
        this.paytype = paytype;
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
        this.field01 = field01;
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
        this.field02 = field02;
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
        this.field03 = field03;
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
        this.field04 = field04;
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
        this.field05 = field05;
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
        this.field06 = field06;
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
        this.field07 = field07;
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
        this.field08 = field08;
    }

    /**
     * 是否被参照0未参照1已参照
     * @return
     * @author zhengziyong 
     * @date May 28, 2013
     */
    public String getIsrefer() {
		return isrefer;
	}

    /**
     * 是否被参照0未参照1已参照
     * @param isrefer
     * @author zhengziyong 
     * @date May 28, 2013
     */
	public void setIsrefer(String isrefer) {
		this.isrefer = isrefer;
	}

	/**
     * 
     * @return 订单商品明细
     * @author zhengziyong 
     * @date May 15, 2013
     */
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	/**
	 * 
	 * @param orderDetail 订单商品明细
	 * @author zhengziyong 
	 * @date May 15, 2013
	 */
	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	/**
	 * 
	 * @return 订单商品明细列表
	 * @author zhengziyong 
	 * @date May 15, 2013
	 */
	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	/**
	 * 
	 * @param orderDetailList 订单商品明细列表
	 * @author zhengziyong 
	 * @date May 15, 2013
	 */
	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	/**
	 * 来源类型1手机订单0普通订单
	 * @return 
	 * @author chenwei 
	 * @date Sep 24, 2013
	 */
	public String getSourcetype() {
		return sourcetype;
	}
	/**
	 * 来源类型1手机订单0普通订单
	 * @return 
	 * @author chenwei 
	 * @date Sep 24, 2013
	 */
	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype;
	}
	/**
	 * 是否以商品编码排序1是0否
	 * @return
	 * @author chenwei 
	 * @date Sep 24, 2013
	 */
	public String getIsgoodsseq() {
		return isgoodsseq;
	}
	/**
	 * 是否以商品编码排序1是0否
	 * @return
	 * @author chenwei 
	 * @date Sep 24, 2013
	 */
	public void setIsgoodsseq(String isgoodsseq) {
		this.isgoodsseq = isgoodsseq;
	}

	public String getSalesarea() {
		return salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getPcustomerid() {
		return pcustomerid;
	}

	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}

	public String getIndooruserid() {
		return indooruserid;
	}

	public void setIndooruserid(String indooruserid) {
		this.indooruserid = indooruserid;
	}

	public String getIndoorusername() {
		return indoorusername;
	}

	public void setIndoorusername(String indoorusername) {
		this.indoorusername = indoorusername;
	}

	/**
	 * 
	 * @return 要货时间
	 * @author zhengziyong 
	 * @date Jan 4, 2014
	 */
	public Date getDemandtime() {
		return demandtime;
	}

	/**
	 * 
	 * @param demandtime 要货时间
	 * @author zhengziyong 
	 * @date Jan 4, 2014
	 */
	public void setDemandtime(Date demandtime) {
		this.demandtime = demandtime;
	}

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
        this.sourceid = sourceid == null ? null : sourceid.trim();
	}

	public String getStorageid() {
		return storageid;
	}

	public void setStorageid(String storageid) {
		this.storageid = storageid;
	}

	public Integer getPhprinttimes() {
		return phprinttimes;
	}

	public void setPhprinttimes(Integer phprinttimes) {
		this.phprinttimes = phprinttimes;
	}
	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public List<OrderDetail> getLackList() {
		return lackList;
	}

	public void setLackList(List<OrderDetail> lackList) {
		this.lackList = lackList;
	}

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

	public String getHandlername() {
		return handlername;
	}

	public void setHandlername(String handlername) {
		this.handlername = handlername;
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

    public BigDecimal getTotalboxvolume() {
        return totalboxvolume;
    }

    public void setTotalboxvolume(BigDecimal totalboxvolume) {
        this.totalboxvolume = totalboxvolume;
    }

    public BigDecimal getTotalboxweight() {
        return totalboxweight;
    }

    public void setTotalboxweight(BigDecimal totalboxweight) {
        this.totalboxweight = totalboxweight;
    }


    public String getSettletypename() {
        return settletypename;
    }

    public void setSettletypename(String settletypename) {
        this.settletypename = settletypename == null ? null : settletypename.trim();
    }

    public String getDetailremark() {
        return detailremark;
    }

    public void setDetailremark(String detailremark) {
        this.detailremark = detailremark;
    }

    public String getBranduser() {
        return branduser;
    }

    public void setBranduser(String branduser) {
        this.branduser = branduser;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPrintdatetime() {
        return printdatetime;
    }

    public void setPrintdatetime(Date printdatetime) {
        this.printdatetime = printdatetime;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPhprintdatetime() {
        return phprintdatetime;
    }

    public void setPhprintdatetime(Date phprintdatetime) {
        this.phprintdatetime = phprintdatetime;
    }

    public String getSalestype() {
        return salestype;
    }

    public void setSalestype(String salestype) {
        this.salestype = salestype;
    }
}