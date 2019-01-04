package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hd.agent.basefiles.model.GoodsInfo;

public class StorageSummaryLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 批次现存量编号
     */
    private String summarybatchid;
    
    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 商品档案
     */
    private GoodsInfo goodsInfo;
    /**
     * 仓库编码
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 库位编码
     */
    private String storagelocationid;
    /**
     * 库位名称
     */
    private String storagelocationname;
    /**
     * 批次号
     */
    private String batchno;

    /**
     * 业务单据类型
     */
    private String billmodel;
    
    /**
     * 业务单据类型名称
     */
    private String billmodelname;
    
    /**
     * 业务单据编号
     */
    private String billid;

    /**
     * 主单位编码
     */
    private String unitid;

    /**
     * 主单位名称
     */
    private String unitname;

    /**
     * 主单位收货数量
     */
    private BigDecimal receivenum;

    /**
     * 主单位发货数量
     */
    private BigDecimal sendnum;

    /**
     * 期初库存总量
     */
    private BigDecimal begintotalnum;

    /**
     * 期末库存总量
     */
    private BigDecimal endtotalnum;

    /**
     * 期初仓库数量
     */
    private BigDecimal beginstoragenum;

    /**
     * 期末仓库数量
     */
    private BigDecimal endstoragenum;

    /**
     * 期初批次数量（库位数量）
     */
    private BigDecimal beginbatchnum;

    /**
     * 期末批次数量
     */
    private BigDecimal endbatchnum;

    /**
     * 辅单位编码
     */
    private String auxunitid;

    /**
     * 辅单位名称
     */
    private String auxunitname;

    /**
     * 辅单位收获数量
     */
    private BigDecimal auxreceivenum;

    /**
     * 辅单位数量描述
     */
    private String auxreceivenumdetail;

    /**
     * 辅单位发货数量
     */
    private BigDecimal auxsendnum;

    /**
     * 辅单位发货数量描述
     */
    private String auxsendnumdetail;

    /**
     * 辅单位期初总量
     */
    private BigDecimal auxbegintotalnum;

    /**
     * 辅单位期初总量描述
     */
    private String auxbegintotalnumdetail;

    /**
     * 辅单位期末总量
     */
    private BigDecimal auxendtotalnum;

    /**
     * 辅单位期末总量描述
     */
    private String auxendtotalnumdetail;

    /**
     * 辅单位期初仓库量
     */
    private BigDecimal auxbeginstoragenum;

    /**
     * 辅单位期初仓库数量描述
     */
    private String auxbeginstoragenumdetail;

    /**
     * 辅单位期末仓库数量
     */
    private BigDecimal auxendstoragenum;

    /**
     * 辅单位期末仓库数量描述
     */
    private String auxendstoragenumdetail;

    /**
     * 辅单位期初批次量（库位量）
     */
    private BigDecimal auxbeginbatchnum;

    /**
     * 辅单位期初批次数量描述
     */
    private String auxbeginbatchnumdetail;

    /**
     * 辅单位期末批次数量
     */
    private BigDecimal auxendbatchnum;

    /**
     * 辅单位期末批次数量描述
     */
    private String auxendbatchnumdetail;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加人用户编号
     */
    private String addusrid;

    /**
     * 添加人姓名
     */
    private String addusername;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 期初数量
     */
    private BigDecimal beginnum;
    
    /**
     * 期初辅数量
     */
    private String auxbeginnumdetail;
    
    /**
     * 期末数量
     */
    private BigDecimal endnum;
    
    /**
     * 期末辅数量
     */
    private String auxendnumdetail;
    
    
    
    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 批次现存量编号
     */
    public String getSummarybatchid() {
        return summarybatchid;
    }

    /**
     * @param summarybatchid 
	 *            批次现存量编号
     */
    public void setSummarybatchid(String summarybatchid) {
        this.summarybatchid = summarybatchid == null ? null : summarybatchid.trim();
    }

    /**
     * @return 商品编码
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid 
	 *            商品编码
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    /**
     * @return 仓库编码
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            仓库编码
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 库位编码
     */
    public String getStoragelocationid() {
        return storagelocationid;
    }

    /**
     * @param storagelocationid 
	 *            库位编码
     */
    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid == null ? null : storagelocationid.trim();
    }

    /**
     * @return 批次号
     */
    public String getBatchno() {
        return batchno;
    }

    /**
     * @param batchno 
	 *            批次号
     */
    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    /**
     * @return 业务单据类型
     */
    public String getBillmodel() {
        return billmodel;
    }

    /**
     * @param billmodel 
	 *            业务单据类型
     */
    public void setBillmodel(String billmodel) {
        this.billmodel = billmodel == null ? null : billmodel.trim();
    }

    /**
     * @return 业务单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            业务单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 主单位编码
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid 
	 *            主单位编码
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 主单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname 
	 *            主单位名称
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    /**
     * @return 主单位收货数量
     */
    public BigDecimal getReceivenum() {
        return receivenum;
    }

    /**
     * @param receivenum 
	 *            主单位收货数量
     */
    public void setReceivenum(BigDecimal receivenum) {
        this.receivenum = receivenum;
    }

    /**
     * @return 主单位发货数量
     */
    public BigDecimal getSendnum() {
        return sendnum;
    }

    /**
     * @param sendnum 
	 *            主单位发货数量
     */
    public void setSendnum(BigDecimal sendnum) {
        this.sendnum = sendnum;
    }

    /**
     * @return 期初库存总量
     */
    public BigDecimal getBegintotalnum() {
        return begintotalnum;
    }

    /**
     * @param begintotalnum 
	 *            期初库存总量
     */
    public void setBegintotalnum(BigDecimal begintotalnum) {
        this.begintotalnum = begintotalnum;
    }

    /**
     * @return 期末库存总量
     */
    public BigDecimal getEndtotalnum() {
        return endtotalnum;
    }

    /**
     * @param endtotalnum 
	 *            期末库存总量
     */
    public void setEndtotalnum(BigDecimal endtotalnum) {
        this.endtotalnum = endtotalnum;
    }

    /**
     * @return 期初仓库数量
     */
    public BigDecimal getBeginstoragenum() {
        return beginstoragenum;
    }

    /**
     * @param beginstoragenum 
	 *            期初仓库数量
     */
    public void setBeginstoragenum(BigDecimal beginstoragenum) {
        this.beginstoragenum = beginstoragenum;
    }

    /**
     * @return 期末仓库数量
     */
    public BigDecimal getEndstoragenum() {
        return endstoragenum;
    }

    /**
     * @param endstoragenum 
	 *            期末仓库数量
     */
    public void setEndstoragenum(BigDecimal endstoragenum) {
        this.endstoragenum = endstoragenum;
    }

    /**
     * @return 期初批次数量（库位数量）
     */
    public BigDecimal getBeginbatchnum() {
        return beginbatchnum;
    }

    /**
     * @param beginbatchnum 
	 *            期初批次数量（库位数量）
     */
    public void setBeginbatchnum(BigDecimal beginbatchnum) {
        this.beginbatchnum = beginbatchnum;
    }

    /**
     * @return 期末批次数量
     */
    public BigDecimal getEndbatchnum() {
        return endbatchnum;
    }

    /**
     * @param endbatchnum 
	 *            期末批次数量
     */
    public void setEndbatchnum(BigDecimal endbatchnum) {
        this.endbatchnum = endbatchnum;
    }

    /**
     * @return 辅单位编码
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid 
	 *            辅单位编码
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid == null ? null : auxunitid.trim();
    }

    /**
     * @return 辅单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname 
	 *            辅单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname == null ? null : auxunitname.trim();
    }

    /**
     * @return 辅单位收获数量
     */
    public BigDecimal getAuxreceivenum() {
        return auxreceivenum;
    }

    /**
     * @param auxreceivenum 
	 *            辅单位收获数量
     */
    public void setAuxreceivenum(BigDecimal auxreceivenum) {
        this.auxreceivenum = auxreceivenum;
    }

    /**
     * @return 辅单位数量描述
     */
    public String getAuxreceivenumdetail() {
        return auxreceivenumdetail;
    }

    /**
     * @param auxreceivenumdetail 
	 *            辅单位数量描述
     */
    public void setAuxreceivenumdetail(String auxreceivenumdetail) {
        this.auxreceivenumdetail = auxreceivenumdetail == null ? null : auxreceivenumdetail.trim();
    }

    /**
     * @return 辅单位发货数量
     */
    public BigDecimal getAuxsendnum() {
        return auxsendnum;
    }

    /**
     * @param auxsendnum 
	 *            辅单位发货数量
     */
    public void setAuxsendnum(BigDecimal auxsendnum) {
        this.auxsendnum = auxsendnum;
    }

    /**
     * @return 辅单位发货数量描述
     */
    public String getAuxsendnumdetail() {
        return auxsendnumdetail;
    }

    /**
     * @param auxsendnumdetail 
	 *            辅单位发货数量描述
     */
    public void setAuxsendnumdetail(String auxsendnumdetail) {
        this.auxsendnumdetail = auxsendnumdetail == null ? null : auxsendnumdetail.trim();
    }

    /**
     * @return 辅单位期初总量
     */
    public BigDecimal getAuxbegintotalnum() {
        return auxbegintotalnum;
    }

    /**
     * @param auxbegintotalnum 
	 *            辅单位期初总量
     */
    public void setAuxbegintotalnum(BigDecimal auxbegintotalnum) {
        this.auxbegintotalnum = auxbegintotalnum;
    }

    /**
     * @return 辅单位期初总量描述
     */
    public String getAuxbegintotalnumdetail() {
        return auxbegintotalnumdetail;
    }

    /**
     * @param auxbegintotalnumdetail 
	 *            辅单位期初总量描述
     */
    public void setAuxbegintotalnumdetail(String auxbegintotalnumdetail) {
        this.auxbegintotalnumdetail = auxbegintotalnumdetail == null ? null : auxbegintotalnumdetail.trim();
    }

    /**
     * @return 辅单位期末总量
     */
    public BigDecimal getAuxendtotalnum() {
        return auxendtotalnum;
    }

    /**
     * @param auxendtotalnum 
	 *            辅单位期末总量
     */
    public void setAuxendtotalnum(BigDecimal auxendtotalnum) {
        this.auxendtotalnum = auxendtotalnum;
    }

    /**
     * @return 辅单位期末总量描述
     */
    public String getAuxendtotalnumdetail() {
        return auxendtotalnumdetail;
    }

    /**
     * @param auxendtotalnumdetail 
	 *            辅单位期末总量描述
     */
    public void setAuxendtotalnumdetail(String auxendtotalnumdetail) {
        this.auxendtotalnumdetail = auxendtotalnumdetail == null ? null : auxendtotalnumdetail.trim();
    }

    /**
     * @return 辅单位期初仓库量
     */
    public BigDecimal getAuxbeginstoragenum() {
        return auxbeginstoragenum;
    }

    /**
     * @param auxbeginstoragenum 
	 *            辅单位期初仓库量
     */
    public void setAuxbeginstoragenum(BigDecimal auxbeginstoragenum) {
        this.auxbeginstoragenum = auxbeginstoragenum;
    }

    /**
     * @return 辅单位期初仓库数量描述
     */
    public String getAuxbeginstoragenumdetail() {
        return auxbeginstoragenumdetail;
    }

    /**
     * @param auxbeginstoragenumdetail 
	 *            辅单位期初仓库数量描述
     */
    public void setAuxbeginstoragenumdetail(String auxbeginstoragenumdetail) {
        this.auxbeginstoragenumdetail = auxbeginstoragenumdetail == null ? null : auxbeginstoragenumdetail.trim();
    }

    /**
     * @return 辅单位期末仓库数量
     */
    public BigDecimal getAuxendstoragenum() {
        return auxendstoragenum;
    }

    /**
     * @param auxendstoragenum 
	 *            辅单位期末仓库数量
     */
    public void setAuxendstoragenum(BigDecimal auxendstoragenum) {
        this.auxendstoragenum = auxendstoragenum;
    }

    /**
     * @return 辅单位期末仓库数量描述
     */
    public String getAuxendstoragenumdetail() {
        return auxendstoragenumdetail;
    }

    /**
     * @param auxendstoragenumdetail 
	 *            辅单位期末仓库数量描述
     */
    public void setAuxendstoragenumdetail(String auxendstoragenumdetail) {
        this.auxendstoragenumdetail = auxendstoragenumdetail == null ? null : auxendstoragenumdetail.trim();
    }

    /**
     * @return 辅单位期初批次量（库位量）
     */
    public BigDecimal getAuxbeginbatchnum() {
        return auxbeginbatchnum;
    }

    /**
     * @param auxbeginbatchnum 
	 *            辅单位期初批次量（库位量）
     */
    public void setAuxbeginbatchnum(BigDecimal auxbeginbatchnum) {
        this.auxbeginbatchnum = auxbeginbatchnum;
    }

    /**
     * @return 辅单位期初批次数量描述
     */
    public String getAuxbeginbatchnumdetail() {
        return auxbeginbatchnumdetail;
    }

    /**
     * @param auxbeginbatchnumdetail 
	 *            辅单位期初批次数量描述
     */
    public void setAuxbeginbatchnumdetail(String auxbeginbatchnumdetail) {
        this.auxbeginbatchnumdetail = auxbeginbatchnumdetail == null ? null : auxbeginbatchnumdetail.trim();
    }

    /**
     * @return 辅单位期末批次数量
     */
    public BigDecimal getAuxendbatchnum() {
        return auxendbatchnum;
    }

    /**
     * @param auxendbatchnum 
	 *            辅单位期末批次数量
     */
    public void setAuxendbatchnum(BigDecimal auxendbatchnum) {
        this.auxendbatchnum = auxendbatchnum;
    }

    /**
     * @return 辅单位期末批次数量描述
     */
    public String getAuxendbatchnumdetail() {
        return auxendbatchnumdetail;
    }

    /**
     * @param auxendbatchnumdetail 
	 *            辅单位期末批次数量描述
     */
    public void setAuxendbatchnumdetail(String auxendbatchnumdetail) {
        this.auxendbatchnumdetail = auxendbatchnumdetail == null ? null : auxendbatchnumdetail.trim();
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
     * @return 添加人用户编号
     */
    public String getAddusrid() {
        return addusrid;
    }

    /**
     * @param addusrid 
	 *            添加人用户编号
     */
    public void setAddusrid(String addusrid) {
        this.addusrid = addusrid == null ? null : addusrid.trim();
    }

    /**
     * @return 添加人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public String getStoragelocationname() {
		return storagelocationname;
	}

	public void setStoragelocationname(String storagelocationname) {
		this.storagelocationname = storagelocationname;
	}

	public String getBillmodelname() {
		return billmodelname;
	}

	public void setBillmodelname(String billmodelname) {
		this.billmodelname = billmodelname;
	}

	public BigDecimal getBeginnum() {
		return beginnum;
	}

	public void setBeginnum(BigDecimal beginnum) {
		this.beginnum = beginnum;
	}

	public String getAuxbeginnumdetail() {
		return auxbeginnumdetail;
	}

	public void setAuxbeginnumdetail(String auxbeginnumdetail) {
		this.auxbeginnumdetail = auxbeginnumdetail;
	}

	public BigDecimal getEndnum() {
		return endnum;
	}

	public void setEndnum(BigDecimal endnum) {
		this.endnum = endnum;
	}

	public String getAuxendnumdetail() {
		return auxendnumdetail;
	}

	public void setAuxendnumdetail(String auxendnumdetail) {
		this.auxendnumdetail = auxendnumdetail;
	}

	
    
	
}