/**
 * @(#)ArrivalOrder.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-21 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hd.agent.basefiles.model.BuySupplier;
import org.apache.struts2.json.annotations.JSON;

/**
 * 采购退货通知单
 * 
 * @author zhanghonghui
 */
public class ReturnOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531535968640561726L;

	private String ordertype;
	
	private String ordertypename;
	/**
     * 编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 状态:0新增1暂存2保存3审核4关闭5中止
     */
    private String status;
    
    /**
     * 状态名称
     */
    private String statusname;

    /**
     * 备注(表头)
     */
    private String remark;

    /**
     * 制单人用户编号
     */
    private String adduserid;

    /**
     * 制单人用户名称
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
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * 修改人姓名
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 审核人用户编号
     */
    private String audituserid;

    /**
     * 审核人用户名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 中止人用户编号
     */
    private String stopuserid;

    /**
     * 中止人用户姓名
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
     * 供应商编号
     */
    private String supplierid;

    /**
     * 供应商名称
     */
    private String suppliername;
    /**
     * 供应商信息
     */
    private BuySupplier buySupplierInfo;

    /**
     * 对方经手人
     */
    private String handlerid;
    /**
     * 对方经手人名称
     */
    private String handlername;

    /**
     * 来源类型,1上游单据，0无
     */
    private String source;

    /**
     * 采购部门编号
     */
    private String buydeptid;
    /**
     * 采购部门名称
     */
    private String buydeptname;

    /**
     * 采购人员编号
     */
    private String buyuserid;
    /**
     * 采购人员名称
     */
    private String buyusername;

    /**
     * 结算方式
     */
    private String settletype;

    /**
     * 支付方式
     */
    private String paytype;

    /**
     * 入货仓库
     */
    private String storageid;
    /**
     * 入货仓库名称
     */
    private String storagename;

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
     * 表头自定义项9
     */
    private String field08;

    /**
     * 来源单据编号
     */
    private String billno;
    /**
     * 是否被参照0未参照1已参照
     */
    private String isrefer;
    /**
     * 1生成发票2核销
     */
    private String isinvoice;
    
    /**
     * 发票状态名称
     */
    private String isinvoicename;
    /**
     * 核销日期
     */
    private String canceldate; 
    /**
     * 采购到货单明细列表
     */
    private List<ReturnOrderDetail> returnOrderDetailList;
    /**
     * 出库状态，1已出库，0未出库
     */
    private String ckstatus;
    
    /**
     * 出库状态名称
     */
    private String ckstatusname;
    /**
     * 是否验收1是0否
     */
    private String ischeck;

    /**
     * 验收人编号
     */
    private String checkuserid;
    /**
     * 验收人姓名
     */
    private String checkusername;
    /**
     * 回单验收日期
     */
    private String checkdate;
    
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
     * @return 状态:0新增1暂存2保存3审核4关闭5中止
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态:0新增1暂存2保存3审核4关闭5中止
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 备注(表头)
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注(表头)
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return 制单人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人用户名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人用户名称
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
     * @return 修改人用户编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人用户编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 修改人姓名
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            修改人姓名
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 审核人用户编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核人用户编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核人用户名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核人用户名称
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
     * @return 中止人用户编号
     */
    public String getStopuserid() {
        return stopuserid;
    }

    /**
     * @param stopuserid 
	 *            中止人用户编号
     */
    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid == null ? null : stopuserid.trim();
    }

    /**
     * @return 中止人用户姓名
     */
    public String getStopusername() {
        return stopusername;
    }

    /**
     * @param stopusername 
	 *            中止人用户姓名
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
     * @return 供应商名称
     */
    public String getSuppliername() {
        return suppliername;
    }

    /**
     * @param suppliername 
	 *            供应商名称
     */
    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername == null ? null : suppliername.trim();
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

    public String getHandlername() {
		return handlername;
	}

	public void setHandlername(String handlername) {
		this.handlername = handlername;
	}

	/**
     * @return 来源类型,1上游单据，0无
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source 
	 *            来源类型,1上游单据，0无
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * @return 采购部门编号
     */
    public String getBuydeptid() {
        return buydeptid;
    }

    /**
     * @param buydeptid 
	 *            采购部门编号
     */
    public void setBuydeptid(String buydeptid) {
        this.buydeptid = buydeptid == null ? null : buydeptid.trim();
    }

    public String getBuydeptname() {
		return buydeptname;
	}

	public void setBuydeptname(String buydeptname) {
		this.buydeptname = buydeptname;
	}

	/**
     * @return 采购人员编号
     */
    public String getBuyuserid() {
        return buyuserid;
    }

    /**
     * @param buyuserid 
	 *            采购人员编号
     */
    public void setBuyuserid(String buyuserid) {
        this.buyuserid = buyuserid == null ? null : buyuserid.trim();
    }

    public String getBuyusername() {
		return buyusername;
	}

	public void setBuyusername(String buyusername) {
		this.buyusername = buyusername;
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
     * @return 入货仓库
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            入货仓库
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
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
     * @return 表头自定义项9
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08 
	 *            表头自定义项9
     */
    public void setField08(String field08) {
        this.field08 = field08 == null ? null : field08.trim();
    }

	public List<ReturnOrderDetail> getReturnOrderDetailList() {
		return returnOrderDetailList;
	}

	public void setReturnOrderDetailList(List<ReturnOrderDetail> returnOrderDetailList) {
		this.returnOrderDetailList = returnOrderDetailList;
	}

    /**
     * @return 来源单据编号
     */
    public String getBillno() {
        return billno;
    }

    /**
     * @param billno 
	 *            来源单据编号
     */
    public void setBillno(String billno) {
		this.billno = billno == null ? null : billno.trim();
    }
    /**
     * 是否被参照0未参照1已参照
     * @return
     * @author zhanghonghui 
     * @date 2013-5-29
     */
	public String getIsrefer() {
		return isrefer;
	}
	/**
	 * 是否被参照0未参照1已参照
	 * @param isrefer
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public void setIsrefer(String isrefer) {
		this.isrefer = isrefer == null ? null : isrefer.trim();
	}

	/**
	 * 1开票，0未票,2核销，3开票中,4可以生成发票
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-13
	 */
	public String getIsinvoice() {
		return isinvoice;
	}

	/**
	 * 1开票，0未票,2核销，3开票中,4可以生成发票
	 * @param isinvoice
	 * @author zhanghonghui 
	 * @date 2013-7-13
	 */
	public void setIsinvoice(String isinvoice) {
		this.isinvoice = isinvoice == null ? null : isinvoice.trim();
	}

	/**
	 * 核销日期
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-13
	 */
	public String getCanceldate() {
		return canceldate;
	}

	/**
	 * 核销日期
	 * @param canceldate
	 * @author zhanghonghui 
	 * @date 2013-7-13
	 */
	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate == null ? null : canceldate.trim();
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype == null ? null : ordertype.trim();
	}

	public String getOrdertypename() {
		return ordertypename;
	}

	public void setOrdertypename(String ordertypename) {
		this.ordertypename = ordertypename == null ? null : ordertypename.trim();
	}

	/**
	 * 出库状态，1已出库，0未出库
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-3-5
	 */
	public String getCkstatus() {
		return ckstatus;
	}

	/**
	 * 出库状态，1已出库，0未出库
	 * @param ckstatus
	 * @author zhanghonghui 
	 * @date 2014-3-5
	 */
	public void setCkstatus(String ckstatus) {
		this.ckstatus = ckstatus == null ? null : ckstatus.trim();
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname == null ? null : statusname.trim();
	}

	public String getIsinvoicename() {
		return isinvoicename;
	}

	public void setIsinvoicename(String isinvoicename) {
		this.isinvoicename = isinvoicename == null ? null : isinvoicename.trim();
	}

	public String getCkstatusname() {
		return ckstatusname;
	}

	public void setCkstatusname(String ckstatusname) {
		this.ckstatusname = ckstatusname == null ? null : ckstatusname.trim();
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename == null ? null : storagename.trim();
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck == null ? null : ischeck.trim();
	}

	public String getCheckuserid() {
		return checkuserid;
	}

	public void setCheckuserid(String checkuserid) {
		this.checkuserid = checkuserid == null ? null : checkuserid.trim();
	}

	public String getCheckusername() {
		return checkusername;
	}

	public void setCheckusername(String checkusername) {
		this.checkusername = checkusername == null ? null : checkusername.trim();
	}

	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate == null ? null : checkdate.trim();
	}

    //-----------------------------导出明细时使用的字段---------------------------------------------//

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 条形码
     */
    private String barcode;
    /**
     * 箱装量
     */
    private BigDecimal boxnum;
    /**
     * 主计量单位名称
     */
    private String unitname;
    /**
     * 数量
     */
    private BigDecimal unitnum;
    /**
     * 数量(辅计量显示)
     */
    private String auxnumdetail;

    /**
     * 含税单价
     */
    private BigDecimal taxprice;

    /**
     * 含税金额
     */
    private BigDecimal goodstaxamount;
    /**
     *含税箱价
     */
    private BigDecimal boxprice;
    /**
     *未税箱价
     */
    private BigDecimal notaxboxprice;
    /**
     * 含税单价
     */
    private BigDecimal notaxprice;

    /**
     * 含税金额
     */
    private BigDecimal goodsnotaxamount;

    /**
     * 税额
     */
    private BigDecimal tax ;
    /**
     * 所属库位
     */
    private String storagelocationname;
    /**
     * 批次号
     */
    private String batchno;
    /**
     * 生产日期
     */
    private String produceddate;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(BigDecimal boxnum) {
        this.boxnum = boxnum;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail;
    }

    public BigDecimal getTaxprice() {
        return taxprice;
    }

    public void setTaxprice(BigDecimal taxprice) {
        this.taxprice = taxprice;
    }

    public BigDecimal getGoodstaxamount() {
        return goodstaxamount;
    }

    public void setGoodstaxamount(BigDecimal goodstaxamount) {
        this.goodstaxamount = goodstaxamount;
    }

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }

    public BigDecimal getNotaxboxprice() {
        return notaxboxprice;
    }

    public void setNotaxboxprice(BigDecimal notaxboxprice) {
        this.notaxboxprice = notaxboxprice;
    }

    public BigDecimal getNotaxprice() {
        return notaxprice;
    }

    public void setNotaxprice(BigDecimal notaxprice) {
        this.notaxprice = notaxprice;
    }

    public BigDecimal getGoodsnotaxamount() {
        return goodsnotaxamount;
    }

    public void setGoodsnotaxamount(BigDecimal goodsnotaxamount) {
        this.goodsnotaxamount = goodsnotaxamount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getStoragelocationname() {
        return storagelocationname;
    }

    public void setStoragelocationname(String storagelocationname) {
        this.storagelocationname = storagelocationname;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getProduceddate() {
        return produceddate;
    }

    public void setProduceddate(String produceddate) {
        this.produceddate = produceddate;
    }

    public BuySupplier getBuySupplierInfo() {
        return buySupplierInfo;
    }

    public void setBuySupplierInfo(BuySupplier buySupplierInfo) {
        this.buySupplierInfo = buySupplierInfo;
    }
}

