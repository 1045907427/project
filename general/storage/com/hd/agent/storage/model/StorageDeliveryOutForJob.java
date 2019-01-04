/**
 * @(#)StorageDeliveryOutForJob.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月30日 huangzhiqian 创建版本
 */
package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class StorageDeliveryOutForJob implements Serializable{
	private static final long serialVersionUID = 1L;
	//主表信息
	private String pid;
	private String businessdate;
	private String premark;
	private String adduserid;
	private String addusername;
	private String adddeptid;
	private String adddeptname;
	private Date addtime;
	private String storageid;
	private String modifyuserid;
	private String modifyusername;
	private Date modifytime;
	private String audituserid;
	private String auditusername;
	private Date audittime;
	private String stopuserid;
	private String stopusername;
	private Date stoptime;
	private Date closetime;
	private Date checktime;
	
	private String billtype;
	private String sourcetype;
	private String sourceid;
	private String supplierid;
	//详情表信息
	
	private String  id;
	private String  goodsid;
	private String  brandid;
	private String  unitid; //主计量单位
	private String  unitname; //主计量单位名称
	private BigDecimal  unitnum; //数量
	private String  auxunitid; //辅计量单位
	private String  auxunitname; //辅计量单位名称
//	private String  auxnum;  //数量(辅计量)
//	private String  auxnumdetail;  //'辅单位数量+辅单位+主单位余数+主单位',
//	private String  overnum; //主单位余数
	private BigDecimal  totalbox;  //合计箱数
    private BigDecimal addcostprice;
	private int seq;
	private String remark;
	
	
	
	
	public String getStorageid() {
		return storageid;
	}
	public void setStorageid(String storageid) {
		this.storageid = storageid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBusinessdate() {
		return businessdate;
	}
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}
	public String getPremark() {
		return premark;
	}
	public void setPremark(String premark) {
		this.premark = premark;
	}
	public String getAdduserid() {
		return adduserid;
	}
	public void setAdduserid(String adduserid) {
		this.adduserid = adduserid;
	}
	public String getAddusername() {
		return addusername;
	}
	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}
	public String getAdddeptid() {
		return adddeptid;
	}
	public void setAdddeptid(String adddeptid) {
		this.adddeptid = adddeptid;
	}
	public String getAdddeptname() {
		return adddeptname;
	}
	public void setAdddeptname(String adddeptname) {
		this.adddeptname = adddeptname;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public String getModifyuserid() {
		return modifyuserid;
	}
	public void setModifyuserid(String modifyuserid) {
		this.modifyuserid = modifyuserid;
	}
	public String getModifyusername() {
		return modifyusername;
	}
	public void setModifyusername(String modifyusername) {
		this.modifyusername = modifyusername;
	}
	public Date getModifytime() {
		return modifytime;
	}
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	public String getAudituserid() {
		return audituserid;
	}
	public void setAudituserid(String audituserid) {
		this.audituserid = audituserid;
	}
	public String getAuditusername() {
		return auditusername;
	}
	public void setAuditusername(String auditusername) {
		this.auditusername = auditusername;
	}
	public Date getAudittime() {
		return audittime;
	}
	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}
	public String getStopuserid() {
		return stopuserid;
	}
	public void setStopuserid(String stopuserid) {
		this.stopuserid = stopuserid;
	}
	public String getStopusername() {
		return stopusername;
	}
	public void setStopusername(String stopusername) {
		this.stopusername = stopusername;
	}
	public Date getStoptime() {
		return stoptime;
	}
	public void setStoptime(Date stoptime) {
		this.stoptime = stoptime;
	}
	public Date getClosetime() {
		return closetime;
	}
	public void setClosetime(Date closetime) {
		this.closetime = closetime;
	}
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}
	public String getSourcetype() {
		return sourcetype;
	}
	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype;
	}
	public String getSourceid() {
		return sourceid;
	}
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getBrandid() {
		return brandid;
	}
	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
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
	public String getAuxunitid() {
		return auxunitid;
	}
	public void setAuxunitid(String auxunitid) {
		this.auxunitid = auxunitid;
	}
	public String getAuxunitname() {
		return auxunitname;
	}
	public void setAuxunitname(String auxunitname) {
		this.auxunitname = auxunitname;
	}
	public BigDecimal getTotalbox() {
		return totalbox;
	}
	public void setTotalbox(BigDecimal totalbox) {
		this.totalbox = totalbox;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public Date getChecktime() {
		return checktime;
	}
	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}
	@Override
	public String toString() {
		return "StorageDeliveryOutForJob [pid=" + pid + ", businessdate=" + businessdate + ", premark=" + premark + ", adduserid=" + adduserid + ", addusername=" + addusername + ", adddeptid="
				+ adddeptid + ", adddeptname=" + adddeptname + ", addtime=" + addtime + ", modifyuserid=" + modifyuserid + ", modifyusername=" + modifyusername + ", modifytime=" + modifytime
				+ ", audituserid=" + audituserid + ", auditusername=" + auditusername + ", audittime=" + audittime + ", stopuserid=" + stopuserid + ", stopusername=" + stopusername + ", stoptime="
				+ stoptime + ", closetime=" + closetime + ", checktime=" + checktime + ", billtype=" + billtype + ", sourcetype=" + sourcetype + ", sourceid=" + sourceid + ", supplierid="
				+ supplierid + ", id=" + id + ", goodsid=" + goodsid + ", brandid=" + brandid + ", unitid=" + unitid + ", unitname=" + unitname + ", unitnum=" + unitnum + ", auxunitid=" + auxunitid
				+ ", auxunitname=" + auxunitname + ", totalbox=" + totalbox + ", seq=" + seq + ", remark=" + remark + "]";
	}
    public BigDecimal getAddcostprice() {
        return addcostprice;
    }

    public void setAddcostprice(BigDecimal addcostprice) {
        this.addcostprice = addcostprice;
    }
}

