package com.hd.agent.report.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 盘点报表
 * @author chenwei
 */
public class StorageCheckListReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 首次盘点的盘点单编号
     */
    private String billid;

    /**
     * 盘点单编号
     */
    private String checklistid;
    /**
     * 盘点仓库
     */
    private String storageid;
    /**
     * 第几次盘点
     */
    private Integer checkno;

    /**
     * 盘点人编号
     */
    private String checkuserid;

    /**
     * 盘点条数
     */
    private Integer checknum;

    /**
     * 盘点成功条数
     */
    private Integer truenum;

    /**
     * 添加时间
     */
    private Date addtime;

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
     * @return 首次盘点的盘点单编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            首次盘点的盘点单编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 盘点单编号
     */
    public String getChecklistid() {
        return checklistid;
    }

    /**
     * @param checklistid 
	 *            盘点单编号
     */
    public void setChecklistid(String checklistid) {
        this.checklistid = checklistid == null ? null : checklistid.trim();
    }

    /**
     * @return 第几次盘点
     */
    public Integer getCheckno() {
        return checkno;
    }

    /**
     * @param checkno 
	 *            第几次盘点
     */
    public void setCheckno(Integer checkno) {
        this.checkno = checkno;
    }

    /**
     * @return 盘点人编号
     */
    public String getCheckuserid() {
        return checkuserid;
    }

    /**
     * @param checkuserid 
	 *            盘点人编号
     */
    public void setCheckuserid(String checkuserid) {
        this.checkuserid = checkuserid == null ? null : checkuserid.trim();
    }

    /**
     * @return 盘点条数
     */
    public Integer getChecknum() {
        return checknum;
    }

    /**
     * @param checknum 
	 *            盘点条数
     */
    public void setChecknum(Integer checknum) {
        this.checknum = checknum;
    }

    /**
     * @return 盘点成功条数
     */
    public Integer getTruenum() {
        return truenum;
    }

    /**
     * @param truenum 
	 *            盘点成功条数
     */
    public void setTruenum(Integer truenum) {
        this.truenum = truenum;
    }

    /**
     * @return 添加时间
     */
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

	public String getStorageid() {
		return storageid;
	}

	public void setStorageid(String storageid) {
		this.storageid = storageid;
	}
    
}