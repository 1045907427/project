/**
 * @(#)BranduserAssess.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 10, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class BranduserAssess implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 编码
	 */
	private String id;
	
	/**
     * 业务日期
     */
    private String businessdate;
    
    /**
     * 品牌业务员编码
     */
    private String branduser;
    
    /**
     * 品牌业务员名称
     */
    private String brandusername;
    
    /**
     * 回笼目标金额
     */
    private BigDecimal wdtargetamount;
    
    /**
     * 回笼奖金基数1:19/2:29
     */
    private String wdbonusbase;
    
    /**
     * 回笼奖金基数名称
     */
    private String wdbonusbasename;
    
    /**
     * kpi目标金额
     */
    private BigDecimal kpitargetamount;
    
    /**
     * 实绩完成金额
     */
    private BigDecimal realaccomplish;
    
    /**
     * KPI奖金金额
     */
    private BigDecimal kpibonusamount;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 添加用户编码
     */
    private String adduserid;

    /**
     * 添加用户
     */
    private String addusername;

    /**
     * 添加时间
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

	public String getBusinessdate() {
		return businessdate;
	}

	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}

	public String getBranduser() {
		return branduser;
	}

	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}

	public String getBrandusername() {
		return brandusername;
	}

	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}

	public BigDecimal getWdtargetamount() {
		return wdtargetamount;
	}

	public void setWdtargetamount(BigDecimal wdtargetamount) {
		this.wdtargetamount = wdtargetamount;
	}

	public String getWdbonusbase() {
		return wdbonusbase;
	}

	public void setWdbonusbase(String wdbonusbase) {
		this.wdbonusbase = wdbonusbase;
	}

	public BigDecimal getKpitargetamount() {
		return kpitargetamount;
	}

	public void setKpitargetamount(BigDecimal kpitargetamount) {
		this.kpitargetamount = kpitargetamount;
	}

	public BigDecimal getRealaccomplish() {
		return realaccomplish;
	}

	public void setRealaccomplish(BigDecimal realaccomplish) {
		this.realaccomplish = realaccomplish;
	}

	public BigDecimal getKpibonusamount() {
		return kpibonusamount;
	}

	public void setKpibonusamount(BigDecimal kpibonusamount) {
		this.kpibonusamount = kpibonusamount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWdbonusbasename() {
		return wdbonusbasename;
	}

	public void setWdbonusbasename(String wdbonusbasename) {
		this.wdbonusbasename = wdbonusbasename;
	}
}

